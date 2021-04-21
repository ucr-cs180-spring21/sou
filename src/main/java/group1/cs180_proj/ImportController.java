/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author patri
 */
public class ImportController  {

   @FXML
   private ListView listview_import;
   
   private FileIO fio;
   private ArrayList<Uber> data;
   private PrimaryController pc;
   
   public ImportController(ArrayList<Uber> d, PrimaryController p){
       data = d;
       pc = p;
   }
   
   public void populate(){
       fio = new FileIO();
       
       ArrayList<String> files = fio.getFiles();
       
       for(String f: files){
           listview_import.getItems().add(f);
       }
   }
   @FXML
   private void handleImportBtn(){
       String selection = listview_import.getSelectionModel().getSelectedItem().toString();
       System.out.println(selection);
       data = fio.importData(selection);
       pc.setData(data);
       Stage stage = (Stage) listview_import.getScene().getWindow();
       stage.close();
   }
   
   
    
}
