/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;


import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patri
 */
public class BackupController  {

    private PrimaryController pc;
    @FXML
    private TextField backup_textfield;
    
    BackupController(PrimaryController p){
        pc = p;
    }
    @FXML
    private void handleBackupBtn(){
        FileIO fio = new FileIO(pc.getData());
        fio.backupData(backup_textfield.getText());
        
        Stage stage = (Stage) backup_textfield.getScene().getWindow();
        stage.close();
    }
    
    
    
}
