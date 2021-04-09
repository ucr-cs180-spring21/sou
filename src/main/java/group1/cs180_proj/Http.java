package group1.cs180_proj;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class Http {
    
   static public void sendHello() throws MalformedURLException, IOException {
       
    String url = "https://webhook.site/c7dce12f-7ef6-43e9-aa7c-918f94a0e07a";
    String charset = "UTF-8";
    String param1 = "hello";
    String query = String.format("key=%s", 
             URLEncoder.encode(param1, charset));
    
    URLConnection con = (HttpURLConnection) new URL(url + "?" + query).openConnection();
    con.setRequestProperty("Accept-Charset", charset);
    HttpURLConnection hcon = (HttpURLConnection) con;
    hcon.setRequestMethod("POST");
    hcon.setDoOutput(true);
    int responseCode = hcon.getResponseCode();
     
    
   }
    
    
    
}
