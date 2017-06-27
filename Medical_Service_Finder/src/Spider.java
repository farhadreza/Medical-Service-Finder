//Author: Prof. Raymond J. Mooney 
//Modified by: Md Farhadur Reza, Romas James Hada

import java.awt.FlowLayout;
import java.io.File;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.*;

import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
//import com.google.gson.Gson;





/**
 * Spider defines a framework for writing a web crawler.  Users can
 * change the behavior of the spider by overriding methods.
 * Default spider does a breadth first crawl starting from a
 * given URL up to a specified maximum number of pages, saving (caching)
 * the pages in a given directory.  Also adds a "BASE" HTML command to
 * cached pages so links can be followed from the cached version.
 *
 * 
 */

public class Spider extends javax.swing.JFrame{

	
    public HashMap<String,String> url_map_spider = new HashMap <String,String>();

    public HashMap<String,String> url_map_spider_select = new HashMap <String,String>();
	public int count4=0;
	  
    JButton select_buttton;
	  
	String dirName = "C:\\java_workspace\\Medical_Service_Finder\\corpus\\";
		
	File directory = new File(dirName);
	  
	static URL firstURL;
	
  /**
   * The queue of links maintained by the spider
   */
  protected List<Link> linksToVisit = new LinkedList<Link>();

  /**
   * Flag to purposely slow the crawl for debugging purposes
   */
  protected boolean slow = false;

  /**
   * The object to be used to retrieve pages
   */
  protected HTMLPageRetriever retriever = new HTMLPageRetriever();

  /**
   * The directory to save the downloaded files to.
   */
 // protected File saveDir;

  /**
   * The number of pages indexed.  In the default implementation
   * a page is considered to be indexed only if it is written to a
   * file.
   */
  protected int count = 0;

  /**
   * The maximum number of pages to be indexed.
   */
  protected int maxCount = 50;

  /**
   * The URLs that have already been visited.
   */
  protected HashSet<Link> visited;
  
  
  
  /**
   * Performs the crawl.  
   */
  
  public void go() {
  
    doCrawl();
  }

  
  
  /**
   * Performs the crawl.  
   */
  public void doCrawl() {
	 
	int count5=0;
    if (linksToVisit.size() == 0) {
      System.err.println("Exiting: No pages to visit.");
      System.exit(0);
    }
    visited = new HashSet<Link>();
    while (linksToVisit.size() > 0 && count < maxCount) {
      // Pause if in slow mode
      if (slow) {
        synchronized (this) {
          try {
            wait(1);
          }
          catch (InterruptedException e) {
          }
        }
      }
      // Take the top link off the queue
      Link link = linksToVisit.remove(0);
      System.out.println("Trying: " + link);
      // Skip if already visited this page
      if (!visited.add(link)) {
        //System.out.println("Already visited");
        continue;
      }
      if (!linkToHTMLPage(link)) {
        //System.out.println("Not HTML Page");
        continue;
      }
      HTMLPage currentPage = null;
      // Use the page retriever to get the page
      try {
        currentPage = retriever.getHTMLPage(link);
       
        
       //count5++;
      }
      catch (PathDisallowedException e) {
        //System.out.println(e);
        continue;
      }
      if (currentPage.empty()) {
        //System.out.println("No Page Found");
        continue;
      }
      if (currentPage.indexAllowed()) {
    	  count++;
    	  url_map_spider.put("P"+MoreString.padWithZeros(count,2),link.getURL().toString());
        
        System.out.println("Indexing" + "(" + count + "): " + link);
        indexPage(currentPage);
        //indexPage(currentPage);
        //count4++;
      }
      if (count < maxCount) {
        List<Link> newLinks = getNewLinks(currentPage);
        // System.out.println("Adding the following links" + newLinks);
        // Add new links to end of queue
        linksToVisit.addAll(newLinks);
      }
    }
  }

  /**
   * Check if this is a link to an HTML page.
   *
   * @return true if a directory or clearly an HTML page
   */
  protected boolean linkToHTMLPage(Link link) {
    String extension = MoreString.fileExtension(link.getURL().getPath());
    if (extension.equals("") || extension.equalsIgnoreCase("html") ||
        extension.equalsIgnoreCase("htm") || extension.equalsIgnoreCase("shtml"))
      return true;
    return false;
  }

  
  /* Get the directory to starts with*/
  private String getDirectory(URL u) {
	    String directory = u.getPath();
	    if (directory.indexOf(".") != -1)
	      directory = directory.substring(0, directory.lastIndexOf("/"));
	    return directory;
	  }
  
  /**
   * Gets links from the page that are in or below the starting
   * directory.
   *
   * @return The links on <code>page</code> that are in or below the
   *         directory of the first page.
   */
  
  public List<Link> getNewLinks(HTMLPage page) {
	    List<Link> links = new LinkExtractor(page).extractLinks();
	    URL url = page.getLink().getURL();

	    ListIterator<Link> iterator = links.listIterator();
	    while (iterator.hasNext()) {
	      Link link = iterator.next();
	      if (!url.getHost().equals(link.getURL().getHost()))
	        iterator.remove();
	      else if (!link.getURL().getPath().startsWith(getDirectory(firstURL)))
	        iterator.remove();
	    }
	    return links;
	  }

  /**
   * "Indexes" a <code>HTMLpage</code>.  This just writes it
   * out to a file in the specified directory with a "P<count>.html" file name.
   *
   * @param page An <code>HTMLPage</code> that contains the page to
   *             index.
   */
  protected void indexPage(HTMLPage currentPage,int count2) {
	
	  
    if(currentPage.indexAllowed()){
  
	currentPage.write(directory,"P"+MoreString.padWithZeros(count2,2));

    }
    
  }

  /**
   * "Indexes" a <code>HTMLpage</code>.  This just writes it
   * out to a file in the specified directory with a "P<count>.html" file name.
   *
   * @param page An <code>HTMLPage</code> that contains the page to
   *             index.
   */
  protected void indexPage(HTMLPage currentPage) {
		
	    if(currentPage.indexAllowed()){
	  
		currentPage.write(directory,"P"+MoreString.padWithZeros(count,2));
	
	    }
	    
	  }

  
  
  /**
   * Spider the web
   */
  public static void main(String args[]) {
	 // String url ="https://www.google.com/#q=laptop";
	 
	  String dirName = "C:\\java_workspace\\Web_Crawler\\corpus\\";
	  //String url2 ="http://www.amazon.com/s/ref=nb_sb_noss_1?url=search-alias%3Daps&field-keywords=TV";
	 // String url2 ="http://www.ebay.com/sch/i.html?_trksid=p2050601.m570.l1313.TR0.TRC0.Xlaptop&_nkw=laptop&_sacat=0&_from=R40";
	  String url2="http://www.ebay.com";
	  String query="laptop";
	
	  Link lp2 = new Link(url2);
	  Spider sp=new Spider();  
	 
	  
	  sp.linksToVisit.add(lp2);
	  
	  Spider.firstURL=lp2.getURL();
     
	  sp.go();
	  
	 
	  short docType = DocumentIterator.TYPE_TEXT;
      String flag = "-html";
      InvertedIndex index = new InvertedIndex(new File(dirName), docType);
      index.print();
      // Interactively process queries to this index.
      index.processQueries(query);
     
      
      for(String s: index.url_name_list)
      	   System.out.println(s);   
   	  
      
      Iterator<String> iterator6 = sp.url_map_spider.keySet().iterator();  
      
      while (iterator6.hasNext()) {  
          String key = iterator6.next().toString();  
          String value = sp.url_map_spider.get(key).toString();  
          
          System.out.println(key + " " + value);  
       }  
      
        
      for(String s: index.url_name_list)
      {
      	String s2= s.substring(0, s.length()-5);
      	Iterator<String> iterator = sp.url_map_spider.keySet().iterator();  
	        
   	        while (iterator.hasNext()) 
   	        {  
   	           String key = iterator.next().toString();  
   	          String value2 = sp.url_map_spider.get(key).toString();
   	          
   	           
   	           if(s2.equalsIgnoreCase(key))
   	           {
   	            
   	           
   	           System.out.println(key + " " + value2); 
   	        	   
   	           sp.url_map_spider_select.put(key, value2);	  
   	           
   	          	
   	           }
   	        }  
      	
      	
      }
      
      Iterator<String> iterator7 = sp.url_map_spider_select.keySet().iterator();  
      
      while (iterator7.hasNext()) {  
          String key = iterator7.next().toString();  
          String value = sp.url_map_spider_select.get(key).toString();  
          String key2= value.split("\\.")[1];
          
          System.out.println(key + " " + value); 
          
          //sp.url_map_spider_website.put(key2, value);
          //final_list.add(value);
   
      }
      
  
  }


}



