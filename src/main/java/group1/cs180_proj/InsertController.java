/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author patri
 */
public class InsertController {
    ArrayList<Uber> data;
    @FXML
    private TextField date_textfield, time_textfield, state_textfield,
            pickup_textfield, address_textfield, street_textfield;
    @FXML
    private Button save_insert_btn;
    
    public void setData(ArrayList<Uber> d){
        data = d;
    }
    public ArrayList<Uber> getData(){
        return data;
    }
    
    @FXML
        public void handleSaveInsertBtn( ) {
            Uber entry = new Uber(date_textfield.getText(), time_textfield.getText(), state_textfield.getText(), 
                    pickup_textfield.getText(), address_textfield.getText(), street_textfield.getText());
            data.add(entry);
            
            Stage stage = (Stage) save_insert_btn.getScene().getWindow();
            stage.close();
        }
}
