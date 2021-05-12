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
    

   
   
   public  ArrayList<Uber> getSearch(String column, String entry) throws Exception {
       String ip = "http://localhost:3000/search/";
       ArrayList<Uber> ret = new ArrayList();
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
        
        try{
        for (int i = 0; i < jsonarray.length(); i++) {
            JSONObject jsonobject = jsonarray.getJSONObject(i);
         
            Uber u = new Uber(jsonobject.getString("date"), jsonobject.getString("time"), jsonobject.getString("state"), 
                    jsonobject.getString("pickup"), jsonobject.getString("address"), jsonobject.getString("street"), jsonobject.getString("id"));
       
          
            ret.add(u);
        }
        
           
       
        }
        catch(Exception e){
                System.out.print(e.getMessage());
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
 
     public  Uber postInsert(Uber u) throws Exception {
       String ip = "http://localhost:3000/insert/";

       URL url = new URL(ip);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("POST");
       con.setRequestProperty("date", u.getDate());
       con.setRequestProperty("time", u.getTime());
       con.setRequestProperty("state", u.getState());
       con.setRequestProperty("pickup", u.getPickup());
       con.setRequestProperty("address", u.getAddress());
       con.setRequestProperty("street", u.getStreet());
       con.setDoOutput(true);
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
        String i = response.toString();
        Uber temp = new Uber(u);
        temp.setID(i);
        
        return temp;
 
        
    }
     public  void postEdit(Uber u) throws Exception {
       String ip = "http://localhost:3000/edit/";
      
       URL url = new URL(ip);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("POST");
       con.setRequestProperty("date", u.getDate());
       con.setRequestProperty("time", u.getTime());
       con.setRequestProperty("state", u.getState());
       con.setRequestProperty("pickup", u.getPickup());
       con.setRequestProperty("address", u.getAddress());
       con.setRequestProperty("street", u.getStreet());
       con.setRequestProperty("id", u.getID());
       con.setDoOutput(true);
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
        String i = response.toString();
        
        
        return;
 
        
    }
     
      public  void postRemove(String i) throws Exception {
       String ip = "http://localhost:3000/remove/";
     
       URL url = new URL(ip);
       HttpURLConnection con = (HttpURLConnection) url.openConnection();
       con.setRequestMethod("POST");
       con.setRequestProperty("index", i);
       con.setDoOutput(true);
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
        String s = response.toString();
        
        
        return;
 
        
    }
    
}
