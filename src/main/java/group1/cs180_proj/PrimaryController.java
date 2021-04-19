package group1.cs180_proj;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;


public class PrimaryController {

    @FXML
    private ChoiceBox dataset_choice,column_choice;
    @FXML
    private ListView listview_dataset, listview_results;
    @FXML
    private TextField search_textfield, occurrences_textfield, busiestState_textfield, 
            busiestStreet_textfield, busiestTime_textfield, busiestPickup_textfield,
            earliestTime_textfield;
    
    private Http http;
    private final String projDir = System.getProperty("user.dir");
    private ArrayList<Uber> data;
    
    @FXML
    private void handleConnectBtn() throws IOException, MalformedURLException, URISyntaxException{
        //Add function to send "hello" to server here
            final String optionsFile = "files.txt";
            http = new Http();
            
            http.downloadFile(optionsFile);
            ArrayList<String> options = Parse.textToArray(projDir, optionsFile);
            dataset_choice.getItems().addAll(options);
            
    }
    
    @FXML
    private void handleDownloadBtn() throws IOException, MalformedURLException, URISyntaxException{
        
        String choice = dataset_choice.getSelectionModel().getSelectedItem().toString();
        http.downloadFile(choice);
    }
    
    @FXML
    private void handleLoadBtn() throws IOException, MalformedURLException, URISyntaxException{
        String[] columns = {"Date", "Time", "State", "Pickup", "Address", "Street", "Full Address"}; 
        data = Parse.parseCSV(projDir, dataset_choice.getSelectionModel().getSelectedItem().toString());
       
        try{
            for(int i = 1; i < data.size(); i++){
                listview_dataset.getItems().add(data.get(i).toString());
            }
            }
        catch(Exception e){
            System.out.print(e);
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
            listview_results.getItems().clear();
            
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
    private void quit() throws IOException{
        //Add function to send "hello" to server here
      
            System.exit(0);
        
        
    }
    
    
  
    
   
   
    
}
