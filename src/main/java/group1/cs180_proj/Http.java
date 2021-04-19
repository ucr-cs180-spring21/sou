package group1.cs180_proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Http {
    
   private final String ip = "http://localhost";
   private final int port = 3000;
   private Socket sock;
   private String returnString;
   private PrintWriter out;
   private BufferedReader in;
   
   public Http() throws IOException{
       returnString = "No return string yet.";
       //connect();
       
   }
   
   public void connect() throws IOException{
       
    
    sock = new Socket(ip,port);
    out = new PrintWriter(sock.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
   }
    
   public void sendMessage(String msg) throws IOException{
       
       out.println(msg);    
       returnString = in.readLine(); 
   }
   
   public String getReturnString() {
       return returnString;
   }
   
   
   public void closeConnection() throws IOException{
       in.close();
       out.close();
   }
   
   public void downloadFile(String file) throws MalformedURLException, IOException, URISyntaxException{
       URL url = new URL(ip+':'+port+"/public/" + file);
       
       InputStream inStream = url.openStream();
       Files.copy(inStream, Paths.get(file), StandardCopyOption.REPLACE_EXISTING);
   }
    
}
