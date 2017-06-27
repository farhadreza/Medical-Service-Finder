//Google API

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import com.google.gson.Gson;
 
public class TestGoogleSea {
 
	public static void main(String[] args) throws IOException {
 
		//int total=0;
		String dirName = "src\\Corpus";
		
		File directory = new File(dirName);
		
		HTMLPageRetriever ht= new HTMLPageRetriever();
		Link lp;
		HTMLPage currentPage = null;
		int count=0;
		
		for (int m = 0; m < 20; m = m + 4) {
		String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&start="+m+"&q=";
		//String address = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";
		String query = "lenovo wireless";
		String charset = "UTF-8";
 
		URL url = new URL(address + URLEncoder.encode(query, charset));
		Reader reader = new InputStreamReader(url.openStream(), charset);
		GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
 
		int total = results.getResponseData().getResults().size();
		System.out.println("total: "+total);
 
		// Show title and URL of each results
		for(int i=0; i<=3; i++){
			//System.out.println("Title: " + results.getResponseData().getResults().get(i).getTitle());
			//System.out.println("URL: " + results.getResponseData().getResults().get(i).getUrl() + "\n");
			
			lp= new Link(results.getResponseData().getResults().get(i).getUrl());
			
			
			
			try {
		        currentPage = ht.getHTMLPage(lp);;
		      }
		      catch (PathDisallowedException e) {
		        System.out.println(e);
		        continue;
		      }
			
			
			//System.out.println(currentPage.link);
			//System.out.println(currentPage.text);
			
			if(currentPage.link.getURL().toString().contains("amazon")){
			        currentPage.write(directory,"Amazon"+count);
			
			}
			

			if(currentPage.link.getURL().toString().contains("ebay")){
			        currentPage.write(directory,"Ebay"+count);
			
			}

			if(currentPage.link.getURL().toString().contains("bestbuy")){
			        currentPage.write(directory,"Bestbuy"+count);
			
			}

			if(currentPage.link.getURL().toString().contains("groupon")){
			        currentPage.write(directory,"Groupon"+count);
			
			}
			
			count++;
			
 
		}
		}
	}
}
 
 
class GoogleResults{
 
    private ResponseData responseData;
    public ResponseData getResponseData() { return responseData; }
    public void setResponseData(ResponseData responseData) { this.responseData = responseData; }
    public String toString() { return "ResponseData[" + responseData + "]"; }
 
    static class ResponseData {
        private List<Result> results;
        public List<Result> getResults() { return results; }
        public void setResults(List<Result> results) { this.results = results; }
        public String toString() { return "Results[" + results + "]"; }
    }
 
    static class Result {
        private String url;
        private String title;
        public String getUrl() { return url; }
        public String getTitle() { return title; }
        public void setUrl(String url) { this.url = url; }
        public void setTitle(String title) { this.title = title; }
        public String toString() { return "Result[url:" + url +",title:" + title + "]"; }
    }
}