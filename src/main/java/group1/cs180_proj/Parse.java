/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;  
import java.util.List;  
import java.util.ArrayList;  
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author patri
 */
public class Parse {
    
    public static ArrayList<String> textToArray(String loc, String file) throws FileNotFoundException, IOException{
        ArrayList<String> ret = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(loc + '/' + file ))) {
        String line;
            while ((line = br.readLine()) != null) {
                ret.add(line);
            }
            br.close();
        }
        
        
        return ret;
    }
    
    public static ArrayList< Uber > parseCSV(String loc, String file) throws IOException{
        ArrayList < Uber > ret = new ArrayList<>();
        
       
        try(BufferedReader br = new BufferedReader(new FileReader(loc + '/' + file ))){
            String line = br.readLine();
            Uber entry;
            while(line != null){
                String newline = StringUtils.normalizeSpace(line);
                newline = newline.trim();
                //System.out.println(newline);
                String[] attributes = newline.split(",");
                List<String> items = Arrays.asList(attributes);
                ArrayList<String> newlist = new ArrayList<>();
                newlist.addAll(items);
                
                
                while(newlist.size() < 6){
                    newlist.add("N/A");
                }
                
                for( int i = 0; i < newlist.size(); i++){
                    newlist.set(i, newlist.get(i).trim()) ;
                    if(newlist.get(i).length() < 2 ){
                        newlist.set(i, "N/A");
                    }
                }
                entry = new Uber(newlist.get(0), newlist.get(1), newlist.get(2), newlist.get(3), newlist.get(4), newlist.get(5));
                
    
                ret.add(entry);
                line = br.readLine();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return ret;
    }
}
