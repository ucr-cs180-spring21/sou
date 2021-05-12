package group1.cs180_proj;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;


public class PrimaryController {

    @FXML
    private ChoiceBox column_choice;
    @FXML
    private ListView listview_results;
    @FXML
    private TextField search_textfield, occurrences_textfield;
    @FXML
    private TextArea analyze_textarea;
    private InsertController ic;
    private EditController ec;
    private ImportController imc;
    private BackupController bc;
    private Http http;
    private final String projDir = System.getProperty("user.dir");
    
    private final String dataset = "dial7.csv";
    ArrayList<Uber> data;
    
    public PrimaryController() throws IOException{
        ic = null;
        ec = null;
       
    }
    
    public void setData(ArrayList<Uber> r){
        data = r;
    }
    
    public ArrayList<Uber> getData(){
        return data;
    }
    
    public void addData(Uber u){
        data.add(u);
    }
    
    public void updateData(Uber u, int i){
        data.set(i, u);
    }
    @FXML
    private void handleLoadBtn() throws IOException{
        String[] columns = {"Date", "Time", "State", "Pickup", "Address", "Street", "Full Address", "All"}; 
        column_choice.getItems().addAll((Object[]) columns);
        http = new Http();
    }
    

    
    @FXML
    private void handleSearchBtn() throws Exception {
        String column = column_choice.getSelectionModel().getSelectedItem().toString().toLowerCase();
        String search = search_textfield.getText().toUpperCase();
        
        
        try{
            data = http.getSearch(column, search);
            
            for(int i = 0; i < data.size(); i++){
                listview_results.getItems().add(data.get(i).toString());
            }
        }
        catch(Exception e){
            System.out.print(e.getStackTrace());
        }
        
        occurrences_textfield.setText(String.valueOf(listview_results.getItems().size()));
   
        
    }
    @FXML
    private void handleAnalysisBtn() throws Exception{
        String res = http.getAnalysis();
        analyze_textarea.setText(res);
    }
    
    @FXML
    private void handleClearBtn(){
        listview_results.getItems().clear();
        occurrences_textfield.clear();
        analyze_textarea.clear();
    }
    
    @FXML
    private void handleInsertBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            ic = new InsertController(this);
            //ic.setData(data);
            fxmlLoader.setController(ic);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Insert");
            stage.setScene(new Scene(root1));  
            
            stage.show();
        }
        catch(Exception e){
            System.out.println();
        }
    }
    
    @FXML
    private void handleUpdateBtn(){
        
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            ec = new EditController(this, listview_results.getSelectionModel().getSelectedIndex() + 1);
   
            fxmlLoader.setController(ec);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Update");
            
            stage.setScene(new Scene(root1));  
            ec.populate();
            
            stage.show();
        }
        catch(Exception e){
            System.out.println();
        }
    }
    
    @FXML
    public void handleReloadbtn(){
        
        
        handleClearBtn();
        
        for(int i = 1; i < data.size(); i++){
             listview_results.getItems().add(data.get(i).toString());
        }
        
         occurrences_textfield.setText(String.valueOf(listview_results.getItems().size()));
        
    }
    
    @FXML
    private void handleRemoveBtn() throws Exception{
        int index = listview_results.getSelectionModel().getSelectedIndex()+1;
        http.postRemove(data.get(index).getID());
        data.remove(index);
        
        handleReloadbtn();
    }
    
    @FXML
    private void handleImportBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Import.fxml"));
            imc = new ImportController(this);
   
            fxmlLoader.setController(imc);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Import");
            imc.populate();
            stage.setScene(new Scene(root1));  
           
            
            stage.show();
        }
        catch(Exception e){
            System.out.println();
        }
    }

    @FXML
    private void handleBackupBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Backup.fxml"));
            bc = new BackupController(this);
   
            fxmlLoader.setController(bc);
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Backup");
            
            stage.setScene(new Scene(root1));  
           
            
            stage.show();
        }
        catch(Exception e){
            System.out.println();
        }
    }
    
    @FXML
    private void quit() throws IOException{
        //Add function to send "hello" to server here
      
            System.exit(0);
        
        
    }
    
    
  
    
   
   
    
}
