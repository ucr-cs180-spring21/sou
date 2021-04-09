package group1.cs180_proj;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private Button hello_btn;
    @FXML
    private TextField textfield;
    

    
    @FXML
    private void handleButtonAction(){
        //Add function to send "hello" to server here
    }
    
    @FXML
    public void setText(String v){
        textfield.setText(v);
    }
    
    @FXML
    public void clearText(){
        textfield.clear();
    }
    
    
    @FXML
    public void HelloText(){
        textfield.setText("Hello");
    }
}
