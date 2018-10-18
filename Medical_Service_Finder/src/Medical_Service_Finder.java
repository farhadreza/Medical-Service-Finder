
//Medical Service Finder


import java.awt.FlowLayout;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;


/*
 Medical Service Finder creates the GUI interface for taking user inputs 
 */

public class Medical_Service_Finder extends javax.swing.JFrame implements Runnable {
	
	  /* Separate thread running in the background to process the user queries while maintaining
	   * the gui interface*/
	  public Thread backgroundThread;
	 
	  /* labeling of the gui interface*/
	  JLabel label0 = new JLabel();
	  JLabel label31 = new JLabel();	
	  JLabel label1 = new JLabel();
	  JLabel label2 = new JLabel();
	  JLabel label3 = new JLabel();
	  JLabel label4 = new JLabel();	  
	  JLabel label5 = new JLabel();
	  JLabel label6 = new JLabel();
	  JLabel label7 = new JLabel();
	  JLabel label8 = new JLabel();
	  JLabel label9 = new JLabel();
	  JLabel label10 = new JLabel();	  
	  JLabel label11 = new JLabel();
	  JLabel label12 = new JLabel();
	  JLabel label13 = new JLabel();	  
	  JLabel label30 = new JLabel();	  	  
	  JLabel label14 = new JLabel();
	  JLabel label15 = new JLabel();
	  JLabel label16 = new JLabel();	  
	  JLabel label17 = new JLabel();
	  JLabel label18 = new JLabel();
	  JLabel label19 = new JLabel();	  
	  JLabel label20 = new JLabel();
	  JLabel label21 = new JLabel();
	  JLabel label22 = new JLabel();	  
	  JLabel label23 = new JLabel();
	  JLabel label24 = new JLabel();
	  JLabel label25 = new JLabel();	  
	  JLabel label26 = new JLabel();	
	  JLabel label27 = new JLabel();	  
	  JLabel label28 = new JLabel();
	  JLabel label29 = new JLabel();
	  JLabel label35 = new JLabel();
	  
	  JLabel label36 = new JLabel();
	  JLabel label37 = new JLabel();
	  
	  JLabel final_label = new JLabel();
	  JLabel personalized_label = new JLabel();
	  JLabel label40= new JLabel();
	  	  
	  //button to start search and select urls
	  JButton begin = new JButton();
	  JButton select_button;	  
	  JButton personalized_button;
	  
	/* medical information*/
	  public JTextField doctor_info;
	  public JTextField healthcare_info;
	  public JTextField emergency_info;
	  public JTextField medication_store_info;
	  
	  /* convert the user input on priority list into the int variables*/
	  public int shipping_value;
	  public int product_cost_value;
	  public int portal_value;
	  public int product_state_value;
	  
	  
	  public int doctor_value;
	  public int healthcare_value;
	  public int emergency_value;
	  public int medication_store_value;
	 
	/* calculate the percentage of priority from the user inputs and save them for later user
	  //in personalized ranking algorithm*/
	  public double shipping_percentage;
	  public double product_cost_percentage;
	  public double portal_percentage;
	  public double product_state_percentage;	
	  
	  public double doctor_value_percentage;
	  public double healthcare_value_percentage;
	  public double emergency_value_percentage;
	  public double medication_store_percentage;
	 
	  
	  // for selection of Crawling or API
	  public JTextField crawling_api;	
	  //if crawling url is selected , give also the url link
	  public JTextField crawling_url= new JTextField();
	  
	// track the selection of Crawling or API
	  public String crawling_api_select;	 
	  
	  
	  /*select url from the list of urls outputted after applying vector space model*/
	  public JTextField final_select_url;
	  /*select url from the list of urls outputted after applying personalized ranking algorithm*/
	  public JTextField personalized_select_url;
	  
	 
	  //track the user input query and save it to pass to the vector space model calculation
	  public JTextField query2 = new JTextField();
	  public String input_query;
	 
	
	  
	  /* Hashmap to store the urls, and corresponding file name or websites name
	   */
	  public HashMap<String,String> url_map = new HashMap <String,String>();
	  
	  /* Hashmap to store the urls, and corresponding file name or websites name
	   when Google API is selected*/
	  public HashMap<String,String> url_map_select = new HashMap <String,String>();
	  public HashMap<String,String> url_map_website = new HashMap <String,String>();
	  
	  /* Hashmap to store the urls, and corresponding file name or websites name
	   when web crawler is selected*/
	  public HashMap<String,String> url_map_spider_website = new HashMap <String,String>();
	  public HashMap<String,String> url_map_spider_select = new HashMap <String,String>();
	  
	  
	  public String url_website;
	  public int count2=0;
	  public int count3=0;
	  
	  
	  /*list of urls output by vector space model and input into the personalized 
	   * ranking algorithm
	   */
	  public ArrayList<String> final_list= new ArrayList<String>();
	  public ArrayList<String> doctor_url_list= new ArrayList<String>();
	  public ArrayList<String> doctor_url_list2= new ArrayList<String>();
	  
	  public ArrayList<String> doctor_url_list3= new ArrayList<String>();
	  public ArrayList<String> doctor_url_list4= new ArrayList<String>();
	  
	  public ArrayList<String> display_line= new ArrayList<String>();
	  /* Final list of urls shown to the user after applying personalized 
	   * ranking algorithm
	   */	  
	 
	  public ArrayList<String> personalized_list= new ArrayList<String>();
	  
	  /* Checlists for user to select from 0 to 10 for user priority list and 
	   * website reliability list
	   */
	  public String [] names ={"0", "1","2","3", "4", "5","6","7","8", "9", "10"};	
	  
	  public String [] names2 ={"Google_API","Web_Crawler"};
	  
	  String[] columnNames = {"npi", "first_name",
              "name",
              "address",
              "city",
              "state","zip","tax_code","tax_description","tax_url"};
	  
	  
	  
	  
	  /* Combobox for user to select crawling or API options
	   */
	  public JComboBox check_list_crawling_api = new JComboBox(names2);
	  
	  /* Checlists for user to select from 0 to 10 for user priority list and 
	   * website reliability list
	   */
	  public JList check_list= new JList(names);
	  JList check_list2= new JList(names);
	  JList check_list3= new JList(names);
	  JList check_list4= new JList(names);
	  JList check_list5= new JList(names);
	  JList check_list6= new JList(names);
	  JList check_list7= new JList(names);
	  JList check_list8= new JList(names);
	  
	  //track the selection of user priority
	  JList selection_list;
	  JList select_product;
	  JList select_portal;
	  JList product_state;

	  //track the selection of website trustworthiness
	  JList select_amazon;
	  JList select_ebay;
	  JList select_groupon;
	  JList select_buy;
	  
	 
	  /*Checklist and Scroll panes to select the urls from vectoe space mode*/
      public JList final_check_list;  
      public JScrollPane final_check_Scroll; 
      
      /*Checklist and Scroll panes to select the urls from personalized url list*/
	  public JList personalized_check_list;	  	  
	  public JScrollPane personalized_check_Scroll;
	  
	  
	 /* Click Cancel to stop the searching*/
	  public boolean cancel = false;
	  
	  
	  /* Show the texts in the user gui interface*/
	   JTextArea textArea = new JTextArea(
	    	    "Doctors Info"
	    	);
	    
	    
	    JTextArea textArea2 = new JTextArea(
	    	    "HealthCare Centers"
	    	);
	    
	    JTextArea textArea3 = new JTextArea(
	    	    "Emergency Services"
	    	);
	  
	    JTextArea textArea4 = new JTextArea(
	    	    "Medication Stores"
	    	);
	    
	    
	    JTextArea textArea5 = new JTextArea(
	    	    "Doctors Info" 
	    	);
	    
	    
	    JTextArea textArea6 = new JTextArea(
	    	    "HealthCare Centers"
	    	    
	    	);
	    
	    JTextArea textArea7 = new JTextArea(
	    	    "Emergency Services" 
	    	    
	    	);
	  
	    JTextArea textArea8 = new JTextArea(
	    	    "Medication Stores"
	    	 
	    	);
	    
	  //Scrollpane to scroll the check lists  
	  JScrollPane check_Scroll0;
	  JScrollPane check_Scroll;
	  JScrollPane select_Scroll;
	  
	  JScrollPane check_Scroll2;
	  JScrollPane select_Scroll2;
	  
	  JScrollPane check_Scroll3;
	  JScrollPane select_Scroll3;
	  
	  JScrollPane check_Scroll4;
	  JScrollPane select_Scroll4;
	  	  
	  JScrollPane check_Scroll5;
	  JScrollPane select_Scroll5;
	  
	  JScrollPane check_Scroll6;
	  JScrollPane select_Scroll6;
	  
	  JScrollPane check_Scroll7;
	  JScrollPane select_Scroll7;
	  
	  JScrollPane check_Scroll8;
	  JScrollPane select_Scroll8;
	  
	  
	  // This method shows the GUI to the user for inputs and selections
	  public void Gui3()
	  {
		  	setLayout(new FlowLayout());
	     	setTitle("Medical Service Finder");
	     	getContentPane().setLayout(null);
	    
	    	setSize(1300,750);
	    	setVisible(false);
	    
	    	begin.setText("Begin Search");
	    	begin.setActionCommand("Begin Search");
	        getContentPane().add(begin);
	        begin.setBounds(500,100,200,24);
	        
	        
	        label35.setText("MEDICAL SERVICE FINDER");
		    getContentPane().add(label35);
		    label35.setBounds(500,10,300,50);
		    
		    label35.setFont(new Font("Serif", Font.BOLD, 16));
	    
	    
	    label26.setText("Enter Your Query");
	    getContentPane().add(label26);
	    label26.setBounds(200,150,100,24);
	    
	    getContentPane().add(query2);
	    query2.setBounds(315,150,200,24);
	    
	    
	  
	    
	    label27.setText("Enter Your City");
	    getContentPane().add(label27);
	    label27.setBounds(200,200,100,24);
	    
	   user_city = new JTextField();
	   getContentPane().add(user_city);
	   user_city.setBounds(315,200,100,24);
	   
	   label28.setText("Enter Your State");
	    getContentPane().add(label28);
	    label28.setBounds(200,250,100,24);
	   
	   user_state = new JTextField();
	   getContentPane().add(user_state);
	   user_state.setBounds(315,250,100,24);
	   
	   
	   
	   label29.setText("Enter Your Zip Code");
	    getContentPane().add(label29);
	    label29.setBounds(200,300,115,24);
	   
	   user_zip_code = new JTextField();
	   getContentPane().add(user_zip_code);
	   user_zip_code.setBounds(315,300,100,24);
	    
	 
	   	
	   	final_label.setText("Initial Search Results");
	    getContentPane().add(final_label);
	    final_label.setBounds(700,150,150,30);
	    
	    //final_label.setFont(new Font("Serif", Font.BOLD, 12));
	    
	    
		
	    
	   /* label40.setText("Selected URL");
	    getContentPane().add(label40);
	    label40.setBounds(900, 260, 100, 30);*/
	    
	    personalized_label.setText("Personalized Search Results");
	    getContentPane().add(personalized_label);
	    personalized_label.setBounds(700,400,200,30);
	    //personalized_label.setFont(new Font("Serif", Font.BOLD, 12));
	    
	   	
	    JLabel label43= new JLabel();
	    
	   /* label43.setText("Selected URL");
	    getContentPane().add(label43);
	    label43.setBounds(900, 600, 100, 30);*/
	    
	    // Select URL (Vector space model output and go to that webpage
	    
	   /* select_button= new JButton("Go to the Webpage");
	    
	    select_button.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent event){
	    				try{
	    			    	java.awt.Desktop.getDesktop().browse(java.net.URI.create(final_select_url.getText().toString()));
	    			    }
	    			    catch(Exception e){
	    			    	
	    			    }
	    			}
	    		}
	    		
	    		);
	    
	    getContentPane().add(select_button);
	    
	    select_button.setBounds(900,350,200,30);*/
	    
	    /* Select URL (personalized search output and go to that webpage*/
    /*   personalized_button= new JButton("Go to the Webpage");
	    
	    personalized_button.addActionListener(
	    		new ActionListener(){
	    			public void actionPerformed(ActionEvent event){
	    				try{
	    			    	java.awt.Desktop.getDesktop().browse(java.net.URI.create(personalized_select_url.getText().toString()));
	    			    }
	    			    catch(Exception e){
	    			    	
	    			    }
	    			}
	    		}
	    		
	    		);
	    
	    getContentPane().add(personalized_button);
	    
	    personalized_button.setBounds(900,670,200,30);*/
	    
	    /* start searching when begin button is clicked*/	    
	    StartAction start_action = new StartAction();
	    begin.addActionListener(start_action);
	    
	    
	  }
	  
	//Internal class used to dispatch events
   class StartAction implements java.awt.event.ActionListener {
		    public void actionPerformed(java.awt.event.ActionEvent event)
		    {
		      Object object = event.getSource();
		      if ( object == begin )
		        start_actionPerformed(event);
		    }
	 }

	//Called when the begin or cancel buttons are clicked

    void start_actionPerformed(java.awt.event.ActionEvent event)
	{
		    if ( backgroundThread==null ) {
		      begin.setLabel("Cancel");
		      //begin.setLabel("begin");
		      backgroundThread = new Thread(this);
		      backgroundThread.start();
		     
		    } else {
		    	cancel();
		    }

	  }
	  
	/* Cancel the search and stop it*/  
     public void cancel()
	{
		    cancel = true;
    }
		  
	//This method starts the background thread.  
	 public void run()
	 {
		 
		 Runnable doLater = new Runnable()
	      {
	        public void run()
	        {
	          begin.setText("Begin Search");
	          
	          HashMap<String, Integer> website_reliability = new HashMap<String, Integer>(); 
	          
	          HashMap<String, String> doctor_url_map = new HashMap<String, String>(); 
	          
	          HashMap<String, String> doctor_url_map2 = new HashMap<String, String>();
	          HashMap<String, String> doctor_url_map3 = new HashMap<String, String>();
	          
	          /* Convert the user input to string*/
	        /*  doctor_value= Integer.parseInt(doctor_info.getText().toString());
	          healthcare_value= Integer.parseInt(healthcare_info.getText().toString());
	          emergency_value= Integer.parseInt(emergency_info.getText().toString());
	          medication_store_value= Integer.parseInt(medication_store_info.getText().toString());*/
	          
	          
              
              
	          doctor_value_percentage=0.5;
	          healthcare_value_percentage=0.2;
	          emergency_value_percentage=0.2;
	          medication_store_percentage=0.1;
	          
	         
              System.out.println();
  	
	          
	          /*Directory to write the webpages and use those pages for inverted index preparation*/
	          //String dirName = "C:\\java_workspace\\Medical_Service_Finder\\corpus\\";
  	        
  	      String dirName = "C:\\java_workspace\\Medical_Service_Finder\\corpus\\";
  		
  		   File directory = new File(dirName);
	          
	          String dirName3 = "C:\\java_workspace\\Medical_Service_Finder\\npi.csv";
	          BufferedReader br = null;
	      	  String line = "";
	      	  String cvsSplitBy = "\",\"";
	         
	          /* Get the crawling url from user */
	         // String url2 =crawling_url.getText().toString();
	         
	      	  int line_char;
	          
	      	try {
	      		 
	    		br = new BufferedReader(new FileReader(dirName3));
	    		while ((line = br.readLine()) != null) {
	     
	    		        // use comma as separator
	    			String[] doctor = line.split(cvsSplitBy);
	    			//doctor.
	    			
	    			line_char=0;
	    			
	    			for (String s : doctor)
	    			{
	    				line_char++;
	    			}
	    			
	    			
	    			if(doctor.length>=9){
   			 
	    				
	    				//doctor[]= doctor_url_list.get(i).substring(0, doctor_url_list.get(i).length()-1);
	    				
	    				doctor_url_list.add(doctor[9].substring(0, doctor[9].length()-1));
	    				doctor_url_map.put("P"+MoreString.padWithZeros(count3,2),doctor[9].substring(0, doctor[9].length()-1));
	    				doctor_url_map2.put(doctor[9].substring(0, doctor[9].length()-1), doctor[0]+"_"+doctor[8]);
	    			
	    				count3++;
	    				//System.out.println("Count: "+count2); 
	    				
	    				//URL url = new URL(doctor[9]);
	     
	    				//System.out.println("Doctor Link: " + doctor[9] 
	                      //              );
	    			}
	    		}	
	    			
	    		} catch (FileNotFoundException e) {
		    		e.printStackTrace();
		    	} catch (IOException e) {
		    		e.printStackTrace();
		    	} 
	      	
	      		catch (NullPointerException nullPointer)  
	      		{  
	  		 
	      		}
	      	    finally {
		    		if (br != null) {
		    			try {
		    				br.close();
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
		    		}
		    	}
	    			
	      	
	      	 for(int i=0;i<doctor_url_list.size();i++)
   		  {
	      		 String doctor_url2= doctor_url_list.get(i).substring(0, doctor_url_list.get(i).length()-1);
	      		doctor_url_list2.add(doctor_url2);
	      		
	      		// System.out.println("Doctor Link: " + doctor_url2);
	      		 
   		  }
	    			
	    		 // for(int i=0;i<doctor_url_list2.size();i++)
	      	for(int i=0;i<100;i++)
	    		  {
	    			  
	    			  
	    			 HTMLPageRetriever ht= new HTMLPageRetriever();	  		
	    	  		 HTMLPage currentPage = null;
	    	  		 Link lp;
	     
	    	  		 
	    	  
	    	  		 
	    			//try{
	    	  			
	    	  		 /* create Link object from URL*/
	    	  			lp= new Link(doctor_url_list.get(i));
	    	  			
	    	  			System.out.println("Doctor Link: " + doctor_url_list.get(i));
	    	  			 Spider sp=new Spider();
	    	  			String test_page="http://codelists.wpc-edi.com/";
	    	  			//lp= new Link(test_page);
	    	  			
	    	  			try {
	    	  				/* Retrive the page using HTMLPageRetriever*/
	    	  		        currentPage = ht.getHTMLPage(lp);
	    	  		      	
	  	    	  		  sp.indexPage(currentPage,count2);
	  	    	  			count2++;
	  	    	  			
	  	    	  			//System.out.println("count: "+count2);
	    	  		       
	    	  		      }
	    	  		      catch (PathDisallowedException e) {
	    	  		        System.out.println(e);
	    	  		        continue;
	    	  		      }
	    	  			
	    			//}
	    			/*catch(MalformedURLException k)
	    	  		{
	    	  			
	    	  		}
	    	  		
	    	  		catch(IOException z)
	    	  		{
	    	  			
	    	  		}
	    	  		
	    	  		catch (NullPointerException nullPointer)  
	    	  		{  
	    	  		 
	    	  		}*/
	    	  		
	    	  			
	    	        
	    	  		
	    		  }	
	    		        
	    			
	     /*
	      	Iterator<String> iterator3 = doctor_url_map.keySet().iterator();  
	        
		       while (iterator3.hasNext()) {  
		           String key = iterator3.next().toString();  
		           String value = doctor_url_map.get(key).toString();  
		           //String key2= value.split("\\.")[1];
		           
		           System.out.println(key + " " + value); 
		           
		          // url_map_website.put(key2, value);
	        
		       }
	     
	      	  	*/
	      	  
	       
	          /* Create object for Spider/wb Crawler*/
	          //Spider sp=new Spider(); 
	         
	          
	          
	         /* If the Google API is selected, then start searching with API*/   
	   //    if(crawling_api.getText().toString().equals("Google_API")){
	       
	        /* Create Link Object*/
	    	/*
	    	 * Link is a class that contains a URL.  Subclasses of link may keep
	    	 * additional information (such as anchor text & other attributes)	    	    
	    	 */	    	   
	        	   
	    
	   	  //  Link lp;
	   	    
	   	   /* Create HTMLPageRetriever object. */
	   	    
	   	   /*
	   	   * HTMLPageRetriever allows clients to download web pages from URLs.
	   	   * This is the default implementation, which performs no processing	   	 
	   	   */
	   	    
	       // HTMLPageRetriever ht= new HTMLPageRetriever();	  		
	  		// HTMLPage currentPage = null;
	  		
	  		//int count=0;
	  		
	  		/*for (int m = 0; m < 20; m = m + 4) 
	  		{
	  		String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="+m+"&q=";
	  		//String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
	  		
	  		//String query = input_query;
	  		String query = query2.getText();
	  		
	  		String charset = "UTF-8";

	  		try{
	  		URL url = new URL(address + URLEncoder.encode(query, charset));
	  		Reader reader = new InputStreamReader(url.openStream(), charset);
	  		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);

	  		int total = results.getResponseData().getResults().size();
	  		System.out.println("total: "+total);

	  		
	  		for(int i=0; i<=3; i++)
	  		{
	  			
	  			
	  			try{*/
	  			/* create Link object from URL*/
	  		//	lp= new Link(results.getResponseData().getResults().get(i).getUrl());
	  			
	  			
	  			//try {
	  				/* Retrive the page using HTMLPageRetriever*/
	  		       // currentPage = ht.getHTMLPage(lp);
	  		       
	  		      //}
	  		     /* catch (PathDisallowedException e) {
	  		        System.out.println(e);
	  		        continue;
	  		      }*/
	  			
	  			 /* Get the website name from the URL*/
	  			// url_website = results.getResponseData().getResults().get(i).getUrl().toString().split("\\.")[1];
	  		     
	  			 /* create hashmap using filename as key and URL as value*/
	  		     //url_map.put("P"+MoreString.padWithZeros(count2,2), results.getResponseData().getResults().get(i).getUrl().toString());
	  		      
	  		        
	  			/*write the page into the directory*/
	  			//sp.indexPage(currentPage,count2);
	  			//count2++;
	  			
	  		
	  			
	  		//}
	  		//	finally
	  			//{
	  				
	  			//}
	  			
	  		//}
	  		
	  		
	  	/*	}
	  		catch(MalformedURLException k)
	  		{
	  			
	  		}
	  		
	  		catch(IOException z)
	  		{
	  			
	  		}
	  		
	  		catch (NullPointerException nullPointer)  
	  		{  
	  		 
	  		}
	  		
	  		
	  		
	  		}
	  		
	        }*/
	          
	       /* If the Web Crawler is selected, then start searching with Spider*/
	    //   else if(crawling_api.getText().toString().equals("Web_Crawler")){
	    	   
	    	/*Create Link object from user input url*/
	    //	Link lp2= new Link(url2);
	    	/* Add the link into the linked list of URLs to go through*/
	    	
		  //  sp.linksToVisit.add(lp2);
		    				  		
		    /* Set the first URL to start searching as a root URL*/
		   // Spider.firstURL=lp2.getURL();
		    
		    /*Start Crawling the website*/
		    
	  		//sp.go();
	  		
	       //}
	          
	       /* make a clone of API hashmap when webcrawler is selected and vice versa*/
	   /*    if(crawling_api.getText().toString().equals("Web_Crawler"))
	       {
	    	   url_map=(HashMap) sp.url_map_spider.clone();
	       }
	       
	       else if(crawling_api.getText().toString().equals("Google_API"))
	       {
	    	   sp.url_map_spider=(HashMap) url_map.clone();
	       }
	       */
	    	       
	        short docType = DocumentIterator.TYPE_TEXT;
	        //boolean stem = false, feedback = true;
	        String flag = "-html";
	        
	        /* Create InvertedIndex object*/ 
	        
	        /*
	         * An inverted index for vector-space information retrieval. Contains
	         * methods for creating an inverted index from a set of documents
	         * and retrieving ranked matches to queries using standard TF/IDF
	         * weighting and cosine similarity.
	         *
	         * 
	         */ 
	        
	        
	       InvertedIndex index = new InvertedIndex(new File(dirName), docType);
	       

	       /*
	        * Accepting queries and showing the retrieved
	        * documents in ranked order.
	        */
	       
	       index.processQueries(query2.getText());
	   
	        
	       for(String s: index.url_name_list)
	    	   System.out.println(s);   
	       
	         
	       /* Create the HashMap from the inverted index output by matching the file name with
	        * initial hashmap of all the urls and file names for Google API
	        * 
	        */
	       
	  /*     for(String s: index.url_name_list)
	        {
	        	String s2= s.substring(0, s.length()-5);
	        	Iterator<String> iterator = url_map.keySet().iterator();  
		        
	     	        while (iterator.hasNext()) 
	     	        {  
	     	           String key = iterator.next().toString();  
	     	          String value2 = url_map.get(key).toString();
	     	          
	     	           
	     	         //  if(s2.equalsIgnoreCase(key))
	     	           //{
	     	            
	     	           
	     	           //System.out.println(key + " " + value2); 
	     	        	   
	     	           url_map_select.put(key, value2);	  
	     	           
	     	          	
	     	          // }
	     	        }  
	        	
	        	
	        }*/
	       
	       
	       
        
	       
	       
	        for(String s: index.url_name_list)
	        {
	        	String s2= s.substring(0, s.length()-5);
	        	Iterator<String> iterator = doctor_url_map.keySet().iterator();
	        	System.out.println("String: "+s2);
		        
	     	        while (iterator.hasNext()) 
	     	        {  
	     	           String key = iterator.next().toString();  
	     	          String value2 = doctor_url_map.get(key).toString();
	     	        
	     	           
	     	           if(s2.equalsIgnoreCase(key))
	     	           {
	     	        	  System.out.println("Key: "+key);
	     	           
	     	        	  System.out.println(key + " " + value2); 
	     	        	   
	     	          // url_map_select.put(value2, key);	
	     	           
	     	             doctor_url_list3.add(value2);
	     	           
	     	           
	     	          	
	     	            }
	     	        }  
	        	
	        	
	        }
	        
	        
	        for(String s: doctor_url_list3)
	        {
	        	//String s2= s.substring(0, s.length()-5);
	        	Iterator<String> iterator = doctor_url_map2.keySet().iterator();  
		        
	     	        while (iterator.hasNext()) 
	     	        {  
	     	        	//key=URL
	     	        	//value2= ID
	     	           String key = iterator.next().toString();  
	     	          String value2 = doctor_url_map2.get(key).toString();
	     	          
	     	           //s =URL
	     	           if(s.equalsIgnoreCase(key))
	     	           {
	     	            
	     	        	   
	     	          String ID= value2.substring(1, 11);
	     	         String doctor_type= value2.substring(12, value2.length());
	     	           
	     	          // System.out.println(key + " " + value2); 
	     	        	   
	     	          // url_map_select.put(value2, key);	
	     	           
	     	           //doctor_url_list4.add(value2);
	     	           
	     	           
	     	    	try {
	   	      		 
	    	    		br = new BufferedReader(new FileReader(dirName3));
	    	    		while ((line = br.readLine()) != null) {
	    	     
	    	    		        // use comma as separator
	    	    			String[] doctor = line.split(cvsSplitBy);
	    	    			
	    	    			if(doctor.length>=9){
	    	    				
	    	    				//System.out.println("ID:"+ ID ); 
	    	    			//if( doctor[0].equalsIgnoreCase(ID) && (query2.getText().substring(0, 3)).equalsIgnoreCase(doctor[0].substring(0, 3))
	    	    				//	  && value2.equalsIgnoreCase(doctor[9]))
	    	    				//System.out.println("Doctor ID:"+ doctor[0].substring(1, 11) ); 
	    	    				// System.out.println("Display line"+line);
	    	    				
	    	    				System.out.println("Display Value"+doctor_type);
	    	    				System.out.println("doctor"+doctor[8]);
	    	    				 
	    	    				//if( (doctor[0].substring(1, 11)).equalsIgnoreCase(ID) && doctor[8].equalsIgnoreCase(doctor_type) && !display_line.contains(line))
	    	    					if( (doctor[0].substring(1, 11)).equalsIgnoreCase(ID) && doctor[8].equalsIgnoreCase(doctor_type) && !display_line.contains(line))
	    	    					  
	    	    					  {
	    	    				       display_line.add(line);
	    	    				      
	    	    					  }
	    	    			}
	    	    		}
	     	         
	     	         
	     	          	
	     	           
	     	    	
	     	    	
	     	          } catch (FileNotFoundException e) {
	  		    		e.printStackTrace();
	  		    	} catch (IOException e) {
	  		    		e.printStackTrace();
	  		    	} 
	  	      	
	  	      		catch (NullPointerException nullPointer)  
	  	      		{  
	  	  		 
	  	      		}
	  	      	    finally {
	  		    		if (br != null) {
	  		    			try {
	  		    				br.close();
	  		    			} catch (IOException e) {
	  		    				e.printStackTrace();
	  		    			}
	  		    		}
	  		    	}
	  	    			
	     	        }  
	        	
	     	     }
	        }
	        
	        System.out.println ("Results are: ");
	        
	        for(int i=0;i<display_line.size();i++)
	        {
	            System.out.println (display_line.get(i));
	        }
	        
	     /*   Iterator<String> iterator13 = url_map_select.keySet().iterator();  
	        
		       while (iterator13.hasNext()) {  
		           String key = iterator13.next().toString();  
		           String value = url_map_select.get(key).toString();  
		           //String key2= value.split("\\.")[1];
		           
		           System.out.println(key + " " + value); 
		           
		          // url_map_website.put(key2, value);
	        
		       }*/
	        
	        
	        /* Create the HashMap from the inverted index output by matching the file name with
		        * initial hashmap of all the urls and file names for Web Crawler
		        * 
		        */

	      /*  for(String s: index.url_name_list)
	        {
	        	String s2= s.substring(0, s.length()-5);
	        	Iterator<String> iterator = sp.url_map_spider.keySet().iterator();  
		        
	     	        while (iterator.hasNext()) 
	     	        {  
	     	           String key = iterator.next().toString();  
	     	          String value2 = sp.url_map_spider.get(key).toString();
	     	          
	     	           
	     	           if(s2.equalsIgnoreCase(key))
	     	           {
	     	            	     	           	     	           
	     	           url_map_spider_select.put(key, value2);	  
	     	           
	     	          	
	     	           }
	     	        }  
	        	
	        	
	        }*/
	        
	        
	        /*Create website name from URLs and create another HashMap for Google API*/
	     /*   Iterator<String> iterator3 = url_map_select.keySet().iterator();  
	        
		       while (iterator3.hasNext()) {  
		           String key = iterator3.next().toString();  
		           String value = url_map_select.get(key).toString();  
		           String key2= value.split("\\.")[1];
		           
		           System.out.println(key + " " + value); 
		           
		           url_map_website.put(key2, value);
	        
		       }
	        */
		       /*Create website name from URLs and create another HashMap for Wb Crawler*/
		    /*   Iterator<String> iterator7 = url_map_spider_select.keySet().iterator();  
		        
		       while (iterator7.hasNext()) {  
		           String key = iterator7.next().toString();  
		           String value = url_map_spider_select.get(key).toString();  
		           String key2= value.split("\\.")[1];
		           
		           System.out.println(key + " " + value); 
		           
		           url_map_spider_website.put(key2, value);
		           
	        
		       }
	        */
		       /*Create the final list of filtered URLs for input to Personalized 
		        * Ranking algorithm while using Google API
		        */
		       
		    /*   Iterator<String> iterator4 = url_map_website.keySet().iterator();  
		        
		       while (iterator4.hasNext()) {  
		           String key = iterator4.next().toString();  
		           String value = url_map_website.get(key).toString();  
		           
		           System.out.println(key + " " + value); 
		           final_list.add(value);
	        
		       }*/
	        
		       /*Create the final list of filtered URLs for input to Personalized 
		        * Ranking algorithm while using Web Crawler
		        */
		     /*  Iterator<String> iterator8 = url_map_spider_website.keySet().iterator();  
		        
		       while (iterator8.hasNext()) {  
		           String key = iterator8.next().toString();  
		           String value = url_map_spider_website.get(key).toString();  
		           
		           System.out.println(key + " " + value); 
		           final_list.add(value);
	        
		       }
		       */
		       
		       for(String s: index.url_name_list)
		        {
		    	   final_list.add(s);
		        }
		       
		   /*    for(String s:final_list)
		    	   System.out.println(s);*/
	        
		       
		       /*
		        * Show the list (output by VSM) in the GUI, User can select the URL from
		        * that list, and User can go to that web page by clicking "Go to the Webpage"
		        * button
		        */
	          
		   /*    DefaultListModel model = new DefaultListModel();
		       
		       for (int i = 0; i < final_list.size(); i++) {
		            model.addElement(final_list.get(i)); // <-- Add item to model
		        }
	          
		        final_check_list= new JList(model);
		  		       			    
			    final_check_list.setVisibleRowCount(5);
			    final_check_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			    final_check_Scroll= new JScrollPane(final_check_list);
			   
			    getContentPane().add(final_check_Scroll); 
			   
			    final_check_Scroll.setBounds(900,100,300,150);
			    
			    final_check_list.addListSelectionListener(
			    		new ListSelectionListener(){
			    			public void valueChanged(ListSelectionEvent event){
			    				//select_ebay.setListData(check_list6.getSelectedValues());
			    				final_select_url.setText(final_check_list.getSelectedValue().toString());
			    			}
			    		}
			    		
			    		);
			    			   
			    
			    final_select_url= new JTextField(30);
			    getContentPane().add(final_select_url);
			    final_select_url.setBounds(900,320,300,30);
			    			    
			    String result= final_select_url.getText().toString();*/
		       
		       
               DefaultListModel model = new DefaultListModel();
		       
		       for (int i = 0; i < display_line.size(); i++) {
		            model.addElement(display_line.get(i)); // <-- Add item to model
		        }
	          
		        final_check_list= new JList(model);
		  		       			    
			    final_check_list.setVisibleRowCount(5);
			    final_check_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			    final_check_Scroll= new JScrollPane(final_check_list);
			   
			    getContentPane().add(final_check_Scroll); 
			   
			    final_check_Scroll.setBounds(700,200,400,150);
			    
			   /* final_check_list.addListSelectionListener(
			    		new ListSelectionListener(){
			    			public void valueChanged(ListSelectionEvent event){
			    				//select_ebay.setListData(check_list6.getSelectedValues());
			    				final_select_url.setText(final_check_list.getSelectedValue().toString());
			    			}
			    		}
			    		
			    		);
			    			 */  
			    
			  /*  final_select_url= new JTextField(30);
			    getContentPane().add(final_select_url);
			    final_select_url.setBounds(700,420,300,30);*/
			    			    
			   // String result= final_select_url.getText().toString();
		       
			    
			   /* Call the Personalized searching algorithm using the list of URLs
			    * output by VSM, and using the percentage calculated from the user inputs
			    */
			    
			    /**************************************/
			    Medical_Guide myPersonalizedSearch = new Medical_Guide(display_line, 
			    		doctor_value_percentage, 
			    		healthcare_value_percentage,
			    		emergency_value_percentage, 
			    		medication_store_percentage
			    		);
			    
			    
			    for(String s:display_line)
			    {
			    	String[] doctor = s.split(cvsSplitBy);
			    	
			    	String city= doctor[4].substring(0, doctor[4].length());
			    	String state= doctor[5].substring(0, doctor[5].length());
			    	String zip_code= doctor[6].substring(0, doctor[6].length());
			    	//String state= display_line.get(5).substring(1, display_line.get(5).length());
			    	
			    	System.out.println("City: "+city);
			    	System.out.println("State: "+state);
			    	
			    	
			    	if((user_city.getText().equalsIgnoreCase(city) && user_state.getText().equalsIgnoreCase(state)) || user_zip_code.getText().equalsIgnoreCase(zip_code))
			    	{
			    		personalized_list.add(s);
			    		
			    	}
			    }
			    
			     
			    
			    /* Calculate RSV and sort the products and show the final personalized
		    	 * searching list to user based on user requirements
		    	 */
			  //  try {
			    	
			    	
				/*	myPersonalizedSearch.getValuableDocs();			  
			    
			     Iterator<String> myIteratorForPrinting = myPersonalizedSearch.SortedProducts.keySet().iterator();
				
				 while(myIteratorForPrinting.hasNext()){
					String URL = (String) myIteratorForPrinting.next();
				    Double ProductRSV =  myPersonalizedSearch.SortedProducts.get(URL);
				    	
				    //JOptionPane.showMessageDialog(null, "URL: " + URL + "\n" + "Product RSV:" + ProductRSV);
				    System.out.println("\nURL: " + URL + "\n" + "Product RSV:" + ProductRSV);
				   // personalized_list.add(URL);
				    
				 }*/
			
			    
			    /*Show the final personalized list in the console also*/   
			       for(String s: personalized_list)
			    	   System.out.println(s);
			       
			       /*
			        * Show the list (output by Personalized) in the GUI, User can select the URL from
			        * that list, and User can go to that web page by clicking "Go to the Webpage"
			        * button
			        */
		          
			       
			       DefaultListModel model2 = new DefaultListModel();
			       
			       for (int i = 0; i < personalized_list.size(); i++) {
			            model2.addElement(personalized_list.get(i)); // <-- Add item to model
			        }
		          
			       personalized_check_list= new JList(model2);
			       
			       
			       
			       personalized_check_list.setVisibleRowCount(5);
			       personalized_check_list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			       personalized_check_Scroll= new JScrollPane(personalized_check_list);				  
				   getContentPane().add(personalized_check_Scroll); 
				  
				   personalized_check_Scroll.setBounds(700,450,400,150);
				    
				 /*  personalized_check_list.addListSelectionListener(
				    		new ListSelectionListener(){
				    			public void valueChanged(ListSelectionEvent event){
				    				
				    				personalized_select_url.setText(personalized_check_list.getSelectedValue().toString());
				    			}
				    		}
				    		
				    		);*/
				    
				   
				    
				  /*  personalized_select_url= new JTextField(30);
				    getContentPane().add(personalized_select_url);
				    personalized_select_url.setBounds(900,630,300,30);*/
				    
			  /*  } catch (IOException e) {
					// TODO Auto-generated catch block
				  e.printStackTrace();
				}
			      */
			  
	        
	        }
	      };
	      SwingUtilities.invokeLater(doLater);
	      backgroundThread=null;
		    
		    
	 }
	  
	  
    public static void main(String[] args)
    {
       	
    	Medical_Service_Finder g= new Medical_Service_Finder();
    	
    	g.Gui3();
    	
    	
    	g.setVisible(true);
    	
    	 
    }
}
