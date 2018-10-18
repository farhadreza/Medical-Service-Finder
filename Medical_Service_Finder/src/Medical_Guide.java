//package Medical Service Finder;
/*****************************************************************/
//Author: Md Farhadur Reza
/*****************************************************************/

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import javax.swing.JOptionPane;
//import javax.swing.JOptionPane;
import javax.xml.ws.http.HTTPException;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Medical_Guide {
	protected ArrayList<String> SellerUrlList = new ArrayList<String>();
	protected HashMap<String, Product> ProductInfo =  new HashMap<String, Product>(); // Key URL, and Value = an instance of Product Class
	protected UserRequirements myRequirements;
	public Map<String, Double> SortedProducts = new HashMap<String, Double>(); 
	
	//protected HashMap<String, Product> OrderedProducts = new HashMap <String, Product>();
	
	public Medical_Guide(){
		

		myRequirements = new UserRequirements(1.0, 0.0, 0.0, 0.0); 
		
		
	}
	
	public Medical_Guide(ArrayList<String> myWebList, double Doctor_Priority, double HealthCare_Priority, double Emergency_Priority, double Medication_Store_Priority){
		
		
		myRequirements = new UserRequirements(Doctor_Priority, HealthCare_Priority, Emergency_Priority, Medication_Store_Priority); 
		
	}
	
	public static void main(String[] args) throws IOException {
		
		Medical_Guide myGuide = new Medical_Guide();
		
	}


}
