package group1.cs180_proj;

import java.io.BufferedReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;


public class Http {
    

   
   
   public  ArrayList<String> getSearch(String column, String entry) throws Exception {
       String ip = "http://localhost:3000/search/";
       ArrayList<String> ret = new ArrayList();
       URL url = new URL(ip);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
//       con.setRequestMethod("GET");
       con.setRequestProperty("column", column);
       con.setRequestProperty("entry", entry);
       BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();// print status code
        //System.out.println(response.statusCode());

        // print response body
        //System.out.println(response.body());
        JSONArray jsonarray = new JSONArray(response.toString());
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
            String e = jsonobject.getString("entry");
            ret.add(e);
        }
        
        return ret;
 
        
    }
     public  String getAnalysis() throws Exception {
       String ip = "http://localhost:3000/analysis/";
       String ret;
       URL url = new URL(ip);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
//       con.setRequestMethod("GET");
       BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();// print status code
        //System.out.println(response.statusCode());

        // print response body
        //System.out.println(response.body());
       
        JSONObject jsonobject = new JSONObject(response.toString());
        String e = jsonobject.getString("entry");
        ret = e;
        
        
        return ret;
 
        
    }
 
    
}
