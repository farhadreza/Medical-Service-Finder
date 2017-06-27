//Author: Prof. Raymond J. Mooney 
//Modified by: Md Farhadur Reza, Romas James Hada

import java.io.*;
import java.util.*;
import java.lang.*;


/**
 * An inverted index for vector-space information retrieval. Contains
 * methods for creating an inverted index from a set of documents
 * and retrieving ranked matches to queries using standard TF/IDF
 * weighting and cosine similarity.
 *
 * 
 */
public class InvertedIndex {

  /**
   * The maximum number of retrieved documents for a query to present to the user
   * at a time
   */
  public static final int MAX_RETRIEVALS = 20;

  /**
   * A HashMap where tokens are indexed. Each indexed token maps
   * to a TokenInfo.
   */
  public Map<String, TokenInfo> tokenHash = null;

  /**
   * A list of all indexed documents.  Elements are DocumentReference's.
   */
  public List<DocumentReference> docRefs = null;

  /**
   * The directory from which the indexed documents come.
   */
  public File dirFile = null;

  /**
   * The type of Documents (text, HTML). See docType in DocumentIterator.
   */
  public short docType = DocumentIterator.TYPE_TEXT;

  /**
   * Whether tokens should be stemmed with Porter stemmer
   */
  public boolean stem = false;

  
 // Selected Documents after applying VSM Model
  
  public ArrayList<String> url_name_list= new ArrayList<String>();
  public ArrayList<String> url_name_list2= new ArrayList<String>();

  /**
   * Create an inverted index of the documents in a directory.
   *
   * @param dirFile  The directory of files to index.
   * @param docType  The type of documents to index (See docType in DocumentIterator)
   */
  public InvertedIndex(File dirFile, short docType) {
    this.dirFile = dirFile;
    this.docType = docType;
    tokenHash = new HashMap<String, TokenInfo>();
    docRefs = new ArrayList<DocumentReference>();
    indexDocuments();
  }

  /**
   * Create an inverted index of the documents in a List of Example objects of documents
   * for text categorization.
   *
   * @param examples A List containing the Example objects for text categorization to index
   */
  public InvertedIndex(List<Example> examples) {
    tokenHash = new HashMap<String, TokenInfo>();
    docRefs = new ArrayList<DocumentReference>();
    indexDocuments(examples);
  }


  /**
   * Index the documents in dirFile.
   */
  protected void indexDocuments() {
    if (!tokenHash.isEmpty() || !docRefs.isEmpty()) {
      // Currently can only index one set of documents when an index is created
      throw new IllegalStateException("Cannot indexDocuments more than once in the same InvertedIndex");
    }
    // Get an iterator for the documents
    DocumentIterator docIter = new DocumentIterator(dirFile, docType, stem);
    System.out.println("Indexing documents in " + dirFile);
    // Loop, processing each of the documents

    while (docIter.hasMoreDocuments()) {
      FileDocument doc = docIter.nextDocument();
      // Create a document vector for this document
      System.out.print(doc.file.getName() + ",");
      HashMapVector vector = doc.hashMapVector();
      indexDocument(doc, vector);
    }
    // Now that all documents have been processed, we can calculate the IDF weights for
    // all tokens and the resulting lengths of all weighted document vectors.
    computeIDFandDocumentLengths();
    System.out.println("\nIndexed " + docRefs.size() + " documents with " + size() + " unique terms.");
  }


  /**
   * Index the documents in the List of Examples for text categorization.
   */
  public void indexDocuments(List<Example> examples) {
    if (!tokenHash.isEmpty() || !docRefs.isEmpty()) {
      // Currently can only index one set of documents when an index is created
      throw new IllegalStateException("Cannot indexDocuments more than once in the same InvertedIndex");
    }
    // Loop, processing each of the examples
    for (Example example : examples) {
      FileDocument doc = example.getDocument();
      // Create a document vector for this document
      HashMapVector vector = example.getHashMapVector();
      indexDocument(doc, vector);
    }
    // Now that all documents have been processed, we can calculate the IDF weights for
    // all tokens and the resulting lengths of all weighted document vectors.
    computeIDFandDocumentLengths();
    System.out.println("Indexed " + docRefs.size() + " documents with " + size() + " unique terms.");
  }

  /**
   * Index the given document using its corresponding vector
   */
  protected void indexDocument(FileDocument doc, HashMapVector vector) {
    // Create a reference to this document
    DocumentReference docRef = new DocumentReference(doc);
    // Add this document to the list of documents indexed
    docRefs.add(docRef);
    // Iterate through each of the tokens in the document
    for (Map.Entry<String, Weight> entry : vector.entrySet()) {
      // An entry in the HashMap maps a token to a Weight
      String token = entry.getKey();
      // The count for the token is in the value of the Weight
      int count = (int) entry.getValue().getValue();
      // Add an occurrence of this token to the inverted index pointing to this document
      indexToken(token, count, docRef);
    }
  }

  /**
   * Add a token occurrence to the index.
   *
   * @param token  The token to index.
   * @param count  The number of times it occurs in the document.
   * @param docRef A reference to the Document it occurs in.
   */
  protected void indexToken(String token, int count, DocumentReference docRef) {
    // Find this token in the index
    TokenInfo tokenInfo = tokenHash.get(token);
    if (tokenInfo == null) {
      // If this is a new token, create info for it to put in the hashtable
      tokenInfo = new TokenInfo();
      tokenHash.put(token, tokenInfo);
    }
    // Add a new occurrence for this token to its info
    tokenInfo.occList.add(new TokenOccurrence(docRef, count));
  }

  /**
   * Compute the IDF factor for every token in the index and the length
   * of the document vector for every document referenced in the index.
   */
  protected void computeIDFandDocumentLengths() {
    // Let N be the total number of documents indexed
    double N = docRefs.size();
    // Iterate through each of the tokens in the index
    Iterator<Map.Entry<String, TokenInfo>> mapEntries = tokenHash.entrySet().iterator();
    while (mapEntries.hasNext()) {
      // Get the token and the tokenInfo for each entry in the HashMap
      Map.Entry<String, TokenInfo> entry = mapEntries.next();

      TokenInfo tokenInfo = entry.getValue();
      // Get the total number of documents in which this token occurs
      double numDocRefs = tokenInfo.occList.size();
      // Calculate the IDF factor for this token
      double idf = Math.log(N / numDocRefs);
      //  System.out.println(token + " occurs in " + Math.round(numDocRefs) + " docs so IDF=" + idf);
      if (idf == 0.0)
        // If IDF is 0, then just remove this inconsequential token from the index
        mapEntries.remove();
      else {
        tokenInfo.idf = idf;
        // In order to compute document vector lengths,  sum the
        // square of the weights (IDF * occurrence count) across
        // every token occurrence for each document and store sum in docRef.length.
        for (TokenOccurrence occ : tokenInfo.occList) {
          occ.docRef.length = occ.docRef.length + Math.pow(idf * occ.count, 2);
        }
      }
    }
    // At this point, every document length should be the sum of the squares of
    // its token weights.  In order to calculate final lengths, just need to
    // set the length of every document reference to the square-root of this sum.
    for (DocumentReference docRef : docRefs) {
      docRef.length = Math.sqrt(docRef.length);
    }
  }

  /**
   * Print out an inverted index by listing each token and the documents it occurs in.
   * Include info on IDF factors, occurrence counts, and document vector lengths.
   */
  public void print() {
    // Iterate through each token in the index
    for (Map.Entry<String, TokenInfo> entry : tokenHash.entrySet()) {
      String token = entry.getKey();
      // Print the token and its IDF factor
      System.out.println(token + " (IDF=" + entry.getValue().idf + ") occurs in:");
      // For each document referenced, print its name, occurrence count for this token, and
      // document vector length (|D|).
      for (TokenOccurrence occ : entry.getValue().occList) {
        System.out.println("   " + occ.docRef.file.getName() + " " + occ.count +
            " times; |D|=" + occ.docRef.length);
      }
    }
  }

  /**
   * Return the number of tokens indexed.
   */
  public int size() {
    return tokenHash.size();
  }

  /**
   * Clear all documents from the inverted index
   */
  public void clear() {
    docRefs.clear();
    tokenHash.clear();
  }

  /**
   * Perform ranked retrieval on this input query.
   */
  public Retrieval[] retrieve(String input) {
    return retrieve(new TextStringDocument(input, stem));
  }

  /**
   * Perform ranked retrieval on this input query Document.
   */
  public Retrieval[] retrieve(Document doc) {
    return retrieve(doc.hashMapVector());
  }

  /**
   * Perform ranked retrieval on this input query Document vector.
   */
  public Retrieval[] retrieve(HashMapVector vector) {
    // Create a hashtable to store the retrieved documents.  Keys
    // are docRefs and values are DoubleValues which indicate the
    // partial score accumulated for this document so far.
    // As each token in the query is processed, each document
    // it indexes is added to this hashtable and its retrieval
    // score (similarity to the query) is appropriately updated.
    Map<DocumentReference, DoubleValue> retrievalHash =
        new HashMap<DocumentReference, DoubleValue>();
    // Initialize a variable to store the length of the query vector
    double queryLength = 0.0;
    // Iterate through each token in the query input Document
    for (Map.Entry<String, Weight> entry : vector.entrySet()) {
      String token = entry.getKey();
      double count = entry.getValue().getValue();
      // Determine the score added to the similarity of each document
      // indexed under this token and update the length of the
      // query vector with the square of the weight for this token.
      queryLength = queryLength + incorporateToken(token, count, retrievalHash);
    }
    // Finalize the length of the query vector by taking the square-root of the
    // final sum of squares of its token weights.
    queryLength = Math.sqrt(queryLength);
    // Make an array to store the final ranked Retrievals.
    Retrieval[] retrievals = new Retrieval[retrievalHash.size()];
    // Iterate through each of the retrieved documents stored in
    // the final retrievalHash.
    int retrievalCount = 0;
    for (Map.Entry<DocumentReference, DoubleValue> entry : retrievalHash.entrySet()) {
      DocumentReference docRef = entry.getKey();
      double score = entry.getValue().value;
      retrievals[retrievalCount++] = getRetrieval(queryLength, docRef, score);
      //System.out.println(getRetrieval(queryLength, docRef, score));
    }
    // Sort the retrievals to produce a final ranked list using the
    // Comparator for retrievals that produces a best to worst ordering.
    Arrays.sort(retrievals);
    return retrievals;
  }

  /**
   * Calculate the final score for a retrieval and return a Retrieval object representing
   * the retrieval with its final score.
   *
   * @param queryLength The length of the query vector, incorporated into the final score
   * @param docRef The document reference for the document concerned
   * @param score The partially computed score 
   * @return The retrieval object for the document described by docRef
   *     and score under the query with length queryLength
   */
  protected Retrieval getRetrieval(double queryLength, DocumentReference docRef, double score) {
    // Normalize score for the lengths of the two document vectors
    score = score / (queryLength * docRef.length);
    // Add a Retrieval for this document to the result array
    return new Retrieval(docRef, score);
  }


  /**
   * Retrieve the documents indexed by this token in the inverted index,
   * add it to the retrievalHash if needed, and update its running total score.
   *
   * @param token         The token in the query to incorporate.
   * @param count         The count of this token in the query.
   * @param retrievalHash The hash table of retrieved DocumentReferences and current
   *                      scores.
   * @return The square of the weight of this token in the query vector for use
   *         in calculating the length of the query vector.
   */
  public double incorporateToken(String token, double count,
                                 Map<DocumentReference, DoubleValue> retrievalHash) {
    TokenInfo tokenInfo = tokenHash.get(token);
    // If token is not in the index, it adds nothing and its squared weight is 0
    if (tokenInfo == null) return 0.0;
    // The weight of a token in the query is is IDF factor times the number
    // of times it occurs in the query.
    double weight = tokenInfo.idf * count;
    // For each document occurrence indexed for this token...
    for (TokenOccurrence occ : tokenInfo.occList) {
      // Get the current score for this document in the retrievalHash.
      DoubleValue val = retrievalHash.get(occ.docRef);
      if (val == null) {
        // If this is a new retrieved document, create an initial score
        // for it and store in the retrievalHash
        val = new DoubleValue(0.0);
        retrievalHash.put(occ.docRef, val);
      }
      // Update the score for this document by adding the product
      // of the weight of this token in the query and its weight
      // in the retrieved document (IDF * occurrence count)
      val.value = val.value + weight * tokenInfo.idf * occ.count;
    }
    // Return the square of the weight of this token in the query
    return weight * weight;
  }


  /**
   * Accepting queries and showing the retrieved
   * documents in ranked order.
   */
  public void processQueries(String query) {

      HashMapVector queryVector = (new TextStringDocument(query, stem)).hashMapVector();
      Retrieval[] retrievals = retrieve(queryVector);
   //  presentRetrievals(queryVector, retrievals);
      //showRetrievals(retrievals);
      printRetrievals(retrievals, 0);
   // }
   // while (true);
  }

  
  /**
   * Show the top retrievals to the user if there are any.
   *
   * @return true if retrievals are non-empty.
   */
  public boolean showRetrievals(Retrieval[] retrievals) {
    if (retrievals.length == 0) {
      System.out.println("\nNo matching documents found.");
      return false;
    } else {
      System.out.println("\nTop " + MAX_RETRIEVALS + " matching Documents from most to least relevant:");
      printRetrievals(retrievals, 0);
      System.out.println("\nEnter `m' to see more, a number to show the nth document, nothing to exit.");
      
      return true;
    }
  }


  /**
   * Print out at most MAX_RETRIEVALS ranked retrievals starting at given starting rank number.
   * Include the rank number and the score.
   */
  public void printRetrievals(Retrieval[] retrievals, int start) {
    System.out.println("");
    if (start >= retrievals.length)
      System.out.println("No more retrievals.");
    for (int i = start; i < Math.min(retrievals.length, start + MAX_RETRIEVALS); i++) {
      System.out.println(MoreString.padTo((i + 1) + ". ", 4) +
          MoreString.padTo(retrievals[i].docRef.file.getName(), 20) +
          " Score: " +
          MoreMath.roundTo(retrievals[i].score, 5));
      
       url_name_list.add(retrievals[i].docRef.file.getName());
       //url_name_list2.add(retrievals[i].docRef.file.);
    }
  }

  /**
   * Inverted Index creation and Ranked Retrievals 
   */
  public static void main(String[] args) {
	  
	  /*Directory to write the webpages and use those pages for inverted index preparation*/
      String dirName = "C:\\java_workspace\\Web_Crawler\\corpus\\";  
    
    short docType = DocumentIterator.TYPE_TEXT;
    boolean stem = false, feedback = false;
    for (int i = 0; i < args.length - 1; i++) {
      //String flag = args[i];
    	String flag = "-html";
      if (flag.equals("-html"))
        // Create HTMLFileDocuments to filter HTML tags
        docType = DocumentIterator.TYPE_HTML;
     
      else {
        throw new IllegalArgumentException("Unknown flag: "+ flag);
      }
    }


    // Create an inverted index for the files in the given directory.
    InvertedIndex index = new InvertedIndex(new File(dirName), docType);
    index.print();
    
    //index.processQueries(query);
  }


}

   
