package group1.cs180_proj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Http {
    
   private Socket sock;
   private String returnString;
   
   public Http() throws IOException{
       returnString = "No return string yet.";
       connect();
       
   }
   
   public void connect() throws IOException{
       
    String ip = "127.0.0.1";
    int port = 3000;
    sock = new Socket(ip,port);
   }
    
   public void sendMessage(String msg) throws IOException{
       PrintWriter out = new PrintWriter(sock.getOutputStream(), true);
       BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
       out.println(msg);
           
       returnString = in.readLine();
       out.close();
       
       
   }
   
   public String getReturnString() {
       return returnString;
   }
    
}
