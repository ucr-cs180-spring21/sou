package group1.cs180_proj;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public class PrimaryController {

    @FXML
    private Button hello_btn, connect_btn;
    @FXML
    private TextField textfield;
    
    private Http http;
    
    @FXML
    private void handleConnectBtn() throws IOException{
        //Add function to send "hello" to server here
      
            http = new Http();
        
        
    }
    
    @FXML
    private void handleHelloBtn() throws IOException{
        //Add function to send "hello" to server here
        
            http.sendMessage("Hello");
            while(http.getReturnString() == "");
            setText(http.getReturnString());
        
        
    }
    
    @FXML
    public void setText(String v){
        textfield.setText(v);
    }
    
    @FXML
    public void clearText(){
        textfield.clear();
    }
    
    
}
