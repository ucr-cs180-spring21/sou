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
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;


public class PrimaryController {

    @FXML
    private ChoiceBox column_choice;
    @FXML
    private ListView listview_dataset, listview_results;
    @FXML
    private TextField search_textfield, occurrences_textfield, busiestState_textfield, 
            busiestStreet_textfield, busiestTime_textfield, busiestPickup_textfield,
            earliestTime_textfield;
    
    private InsertController ic;
    private EditController ec;
    private ImportController imc;
    private BackupController bc;
    private Http http;
    private final String projDir = System.getProperty("user.dir");
    public ArrayList<Uber> data;
    private final String dataset = "dial7.csv";
    
    public PrimaryController(){
        ic = null;
        ec = null;
    }
    
    public void setData(ArrayList<Uber> d){
        data = d;
    }
    
    @FXML
    private void handleDownloadBtn() throws IOException, MalformedURLException, URISyntaxException{
        Http http = new Http();
        String choice = dataset;
        http.downloadFile(choice);
    }
    
    @FXML
    private void handleLoadBtn() throws IOException, MalformedURLException, URISyntaxException{
        String[] columns = {"Date", "Time", "State", "Pickup", "Address", "Street", "Full Address"}; 
        data = Parse.parseCSV(projDir, dataset);
       
      
        for(int i = 1; i < data.size(); i++){
            listview_dataset.getItems().add(data.get(i).toString());
        }
           
        
        column_choice.getItems().addAll((Object[]) columns);
        Analysis anal = new Analysis(data);
        Pair<Uber, Integer> res =  anal.findMaxOccurrenceState();
        busiestState_textfield.setText(res.getKey().getState() + ", Occurrences: " + res.getValue());
        res = anal.findMaxOccurrenceStreet();
        busiestStreet_textfield.setText(res.getKey().getStreet() + ", Occurrences: " + res.getValue());
    }
    
    @FXML
    private void handleSearchBtn() {
        String column = column_choice.getSelectionModel().getSelectedItem().toString();
        String search = search_textfield.getText();
        ArrayList<Uber> results = null ;
        Analysis anal = new Analysis(data);
        
        search = search.toUpperCase();
        switch(column){
            case "Date":
                results = anal.searchDates(search);
                break;
            case "Time":
                results = anal.searchTimes(search);
                break;
            case "State":
                results = anal.searchStates(search);
                break;
            case "Pickup":
                results = anal.searchPickups(search);
                break;
            case "Address":
                results = anal.searchAddresses(search);
                break;
            case "Street":
                results = anal.searchStreets(search);
                break;
            case "Full Address":
                results = anal.searchFullAddresses(search);
                break;
            default:
                break;
        }
        
        try{
            
            
            for(int i = 0; i < results.size(); i++){
                listview_results.getItems().add(results.get(i).toString());
            }
        }
        catch(Exception e){
            System.out.print(e);
        }
        
        occurrences_textfield.setText(String.valueOf(listview_results.getItems().size()));
        Analysis anal2 = new Analysis(results);
        Pair<Uber,Integer> res = anal2.findMaxOccurrenceTime();
        busiestTime_textfield.setText(res.getKey().getTime() + ", Occurrences: " + res.getValue());
        res = anal2.findMaxOccurrencePickup();
        busiestPickup_textfield.setText(res.getKey().getPickup() + ", Occurrences: " + res.getValue());
        String t = anal2.findEarliestTime();
        earliestTime_textfield.setText(t);
    }
    
    @FXML
    private void handleClearBtn(){
        listview_results.getItems().clear();
    }
    
    @FXML
    private void handleInsertBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Edit.fxml"));
            ic = new InsertController();
            ic.setData(data);
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
            ec = new EditController(data, listview_dataset.getSelectionModel().getSelectedIndex() + 1);
   
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
    private void handleReloadbtn(){
        listview_dataset.getItems().clear();
        
        if(ic == null && ec == null){
            //do nothing
        }
        else if(ic == null && ec != null){
            data = ec.getData();
            ec = null;
        }
        else if(ec == null && ic != null){
            data = ic.getData();
            ic = null;
            
        }
        
        for(int i = 1; i < data.size(); i++){
             listview_dataset.getItems().add(data.get(i).toString());
        }
        
        
    }
    
    @FXML
    private void handleRemoveBtn(){
        data.remove(listview_dataset.getSelectionModel().getSelectedIndex() + 1);
    }
    
    @FXML
    private void handleImportBtn(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Import.fxml"));
            imc = new ImportController(data,this);
   
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
            bc = new BackupController(data);
   
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
