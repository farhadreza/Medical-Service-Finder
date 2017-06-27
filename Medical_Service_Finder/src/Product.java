//package EconomicOnlineShopping;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

//import javax.swing.JOptionPane;


public class Product {
	
	// Properties of a product
	 String ProductTitle; // Title of a Product
	 String ProductURL; // Link of the Product, example: www.amazon.com/...
	 String ProductSeller; // Like Amazon, eBay, or Walmart ...
	 // String ProductQuality; // Quality of the product, (New = 0.10, Used = 0.5, Refurbished = 0.9)
	
	 
	 String MarkedPrice; // Marked Price of the product
	 
	 double ProductRSV; // The user value of the product
	 double NormalizedTotalCost; // LowestCost in the list / Total Cost of the Product
	 double NormalizedShippingTime; // LowestShippingTime / ShippingTime of the Product
	 double ShippingCost; // Shipping cost
	 double ProductQualityValue; // Converting product quality to double
	 double TotalCost; // Converted to Double
	 double ShippingCostValue; // Shipping cost converted to String
	 double ShippingTime; // In days
	 	

	 public Product(String _ProductURL, String _ProductSeller, String _ProductTitle, String _MarkedPrice, String _ShippingCost, String _ShippingTime, String _ProductQuality){
		 ProductURL = _ProductURL;
		 ProductSeller = _ProductSeller;
		 ProductTitle = _ProductTitle;
		 MarkedPrice = _MarkedPrice;
		 
		 ShippingCost = ExtractShippingCost(_ShippingCost);		 
		 ShippingTime = ExtractShippingTime(_ShippingTime);
		 ProductQualityValue = changeProductQualityToValue(_ProductQuality);
		 TotalCost = ExtractPriceFromString(MarkedPrice) + ShippingCost;
		 
	 }
	 
	 public Product(){
		 
	 }
	 
	 public double ExtractShippingCost(String myShippingCost){
		 double ShippingCost = 0.0;
		 if(myShippingCost.contains("free") || myShippingCost.contains("FREE") || myShippingCost.contains("Free")){
		   	    
				return 0.0;
		 }
		 else{
			 Pattern myPricePattern = Pattern.compile("-?[\\d\\.]+");
			 Matcher MyMatcher = myPricePattern.matcher(myShippingCost);
			 while (MyMatcher.find()) {
				 ShippingCost = Math.abs(Double.parseDouble(MyMatcher.group())); 
			 }
				
			
		 }
		 
		 JOptionPane.showMessageDialog(null,"ShippingCost :" + ShippingCost);
		 return ShippingCost;
	 }
	 
	 public double ExtractShippingTime(String ShippingTime){
		 double myShippingTime = 9.0;
			 
		 Pattern myPricePattern = Pattern.compile("-?[\\d\\.]+");
		 Matcher MyMatcher = myPricePattern.matcher(ShippingTime);
			while (MyMatcher.find()) {
				myShippingTime = Math.abs(Double.parseDouble(MyMatcher.group())); // 3-4 Days will give 3.0 and 4.0
			}
		
		if(ShippingTime.contains("Delivery") || ShippingTime.contains("Shipped")){
			Calendar cal = Calendar.getInstance();  
			int day = cal.get(Calendar.DAY_OF_MONTH);
			myShippingTime = myShippingTime - (double) day;
			
		}

		 if(myShippingTime == 0.0){
			 myShippingTime = 9.0;
			
		}
		 
		JOptionPane.showMessageDialog(null,"myShippingTime :" + myShippingTime);
		
		return myShippingTime;
	 }
	 
	 public double ExtractPriceFromString(String myString){
		double myNumber = 0.0;
				
		Pattern myPricePattern = Pattern.compile("-?[\\d\\.]+");
		Matcher MyMatcher = myPricePattern.matcher(myString);
		while (MyMatcher.find()) {
			myNumber = Math.abs(Double.parseDouble(MyMatcher.group())); 
		}
		
		//JOptionPane.showMessageDialog(null,"Number :" + myNumber);
		
		if(myNumber == 0.0){
			myNumber = 99999.0;
		}
		return myNumber;
	}
		
	 
	 public double changeProductQualityToValue(String ProductQuality){
		 
		 //JOptionPane.showMessageDialog(null, "Product Quality: " + ProductQuality);
		 if(ProductQuality.contains("New") || ProductQuality.contains("new"))
		 {
			JOptionPane.showMessageDialog(null, "Product Quality: " + ProductQuality);
			return 1.0;
		 } 
		 else if (ProductQuality.contains("Refurbished") || ProductQuality.contains("refurbished")){
			 return 0.9;
		 }
		 else if(ProductQuality.contains("Good") || ProductQuality.contains("good")){
			
			 JOptionPane.showMessageDialog(null, "Product Quality: " + ProductQuality);
			 return 0.7;
		 }
		 else if(ProductQuality.contains("Used") || ProductQuality.contains("used")){
			 JOptionPane.showMessageDialog(null, "Product Quality: " + ProductQuality);
			 return 0.5;
		 }
		 
		 else 
			 return 1.0; // Assuming New
		 
	 }
	 
	 // Set Data
	 
	 public void setProductQualityValue(double ProductQuality){
		 this.ProductQualityValue = ProductQuality;
	 }
	 
	 public void setProductURL(String _ProductURL){
		 ProductURL = _ProductURL;
	 }
	 
	 public void setProductSeller(String _ProductSeller){
		 ProductSeller = _ProductSeller;
	 }
	 
	 public void setProductTitle(String _ProductTitle){
		 ProductTitle = _ProductTitle;
	 }
	 
	 public void setMarkedPrice(String _MarkedPrice){
		 MarkedPrice = _MarkedPrice;
	 }
	 
	 public void setShippingCost(double _ShippingCost){
		 ShippingCost = _ShippingCost;
	 }
	 
	 public void setShippingTime(double _ShippingTime){
		 ShippingTime = _ShippingTime;
	 }
	 
	 public void setTotalCost(int _TotalCost){
		 TotalCost = _TotalCost;
	 }
	 
	 public void setNormalizedTotalCost(double _NormalizedTotalCost){
		 NormalizedTotalCost = _NormalizedTotalCost;
	 }
	 
	 public void setNormalizedShippingTime(double _NormalizedShippingTime){
		 NormalizedShippingTime = _NormalizedShippingTime;
	 }
	 
	 public void setProductRSV(double _ProductRSV){
		 
		 ProductRSV = _ProductRSV;
	 }
	 
	 // Get Data
	 public String getProductURL(){
		 return ProductURL;
	 }
	 
	 public String getProductSeller(){
		 return ProductSeller;
	 }
	 
	 public String geProductTitlet(){
		 return ProductTitle;
	 }
	 
	 public String getMarkedPrice(){
		 return MarkedPrice ;
	 }
	
	 public double getShippingCost(){
		 return ShippingCost;
	 }
	 
	 public double getShippingTime(){
		 return ShippingTime;
	 }
	
	 public double getTotalCost(){
		 return TotalCost;
	 }
	 
	 public double getNormalizedTotalCost(){
		 return NormalizedTotalCost;
	 }
	 
	 public double getNormalizedShippingTime(){
		 return NormalizedShippingTime;
	 }
	 
	 public double getProductRSV(){
		 return ProductRSV;
	 }
	 
	 public double getProductQualityValue(){
		 
		 return ProductQualityValue;
	 }
}
