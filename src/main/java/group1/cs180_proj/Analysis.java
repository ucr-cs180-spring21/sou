/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.util.Pair;

/**
 *
 * @author patri
 */
public class Analysis {
    ArrayList<Uber> data;
    
    
    

    
    public Analysis(ArrayList<Uber> d){
        data = d;
    }
    
    public ArrayList<Uber> searchDates(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getDate().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchTimes(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getTime().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchStates(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getState().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchPickups(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getPickup().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchAddresses(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getAddress().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchStreets(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getStreet().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
    
    public ArrayList<Uber> searchFullAddresses(String d){
        ArrayList<Uber> ret = new ArrayList<>();
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getFullAddress().equals(d)){
                ret.add(data.get(i));
            }
        }
        
        return ret;
    }
   
    Pair<Uber, Integer> findMaxOccurrenceDate(){
        ArrayList<Uber> temp = new ArrayList<>(data);
        Pair<Uber,Integer> ret = null;
        Map<String,Integer> map = new HashMap<>();
        String maxS;
        int maxI;
        for(int i = 0; i < temp.size(); i++){
            if(!map.containsKey(temp.get(i).getDate())){
                map.put(temp.get(i).getDate(), 1);
            }
            
            for(int j = i+1; j < temp.size(); j++){
                if(map.containsKey(temp.get(j).getDate())){
                    map.replace(temp.get(j).getDate(), map.get(temp.get(j).getDate()) +1);
                    temp.remove(j);
                    j--;
                }
                
            }
            temp.remove(i);
            i--;
        }
        
        maxS = data.get(0).getDate();
        maxI = map.get(maxS);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = entry.getValue();
            if(i > maxI){
                maxI = i;
                maxS = entry.getKey();
            }
        }
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getDate().equals(maxS)){
                ret = new Pair<>(data.get(i), maxI);
            }
        }
        return ret;
    }
    
    Pair<Uber, Integer> findMaxOccurrenceState(){
        ArrayList<Uber> temp = new ArrayList<>(data);
        Pair<Uber,Integer> ret = null;
        Map<String,Integer> map = new HashMap<>();
        String maxS;
        int maxI;
        for(int i = 0; i < temp.size(); i++){
            
            if(!map.containsKey(temp.get(i).getState())){
                map.put(temp.get(i).getState(), 1);
            }
            
            for(int j = i+1; j < temp.size(); j++){
                if(map.containsKey(temp.get(j).getState())){
                    map.replace(temp.get(j).getState(), map.get(temp.get(j).getState()) +1);
                    temp.remove(j);
                    j--;
                }
                
                
            }
            temp.remove(i);
            i--;
            
        }
        
        maxS = data.get(0).getState();
        maxI = map.get(maxS);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = entry.getValue();
            if(i > maxI){
                maxI = i;
                maxS = entry.getKey();
            }
        }
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getState().equals(maxS)){
                ret = new Pair<>(data.get(i), maxI);
            }
        }
        return ret;
    }
    
    Pair<Uber, Integer> findMaxOccurrenceStreet(){
        ArrayList<Uber> temp = new ArrayList<>(data);
        Pair<Uber,Integer> ret = null;
        Map<String,Integer> map = new HashMap<>();
        String maxS;
        int maxI;
        for(int i = 0; i < temp.size(); i++){
            if(temp.get(i).getStreet().equals("N/A")) {
                temp.remove(i);
                i--;
                continue;
            }
            if(!map.containsKey(temp.get(i).getStreet())){
                map.put(temp.get(i).getStreet(), 1);
            }
     
            for(int j = i+1; j < temp.size(); j++){
                if(map.containsKey(temp.get(j).getStreet())){
                    map.replace(temp.get(j).getStreet(), map.get(temp.get(j).getStreet()) +1);
                    temp.remove(j);
                    j--;
                }
             
            }
            temp.remove(i);
            i--;
        }
        
        maxS = data.get(0).getStreet();
        maxI = map.get(maxS);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = entry.getValue();
            if(i > maxI){
                maxI = i;
                maxS = entry.getKey();
            }
        }
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getStreet().equals(maxS)){
                ret = new Pair<>(data.get(i), maxI);
            }
        }
        return ret;
    }
    
    Pair<Uber, Integer> findMaxOccurrenceTime(){
        ArrayList<Uber> temp = new ArrayList<>(data);
        Pair<Uber,Integer> ret = null;
        Map<String,Integer> map = new HashMap<>();
        String maxS;
        int maxI;
        for(int i = 0; i < temp.size(); i++){
            
            if(!map.containsKey(temp.get(i).getTime())){
                map.put(temp.get(i).getTime(), 1);
            }
          
            for(int j = i+1; j < temp.size(); j++){
                if(map.containsKey(temp.get(j).getTime())){
                    map.replace(temp.get(j).getTime(), map.get(temp.get(j).getTime()) +1);
                    temp.remove(j);
                    j--;
                }
                
            }
            temp.remove(i);
            i--;
        }
        
        maxS = data.get(0).getTime();
        maxI = map.get(maxS);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = entry.getValue();
            if(i > maxI){
                maxI = i;
                maxS = entry.getKey();
            }
        }
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getTime().equals(maxS)){
                ret = new Pair<>(data.get(i), maxI);
            }
        }
        return ret;
    }
    
    Pair<Uber, Integer> findMaxOccurrencePickup(){
        ArrayList<Uber> temp = new ArrayList<>(data);
        Pair<Uber,Integer> ret = null;
        Map<String,Integer> map = new HashMap<>();
        String maxS;
        int maxI;
        for(int i = 0; i < temp.size(); i++){
            
            if(!map.containsKey(temp.get(i).getPickup())){
                map.put(temp.get(i).getPickup(), 1);
            }
            
            for(int j = i+1; j < temp.size(); j++){
                if(map.containsKey(temp.get(j).getPickup())){
                    map.replace(temp.get(j).getPickup(), map.get(temp.get(j).getPickup()) +1);
                    temp.remove(j);
                    j--;
                }
               
            }
            temp.remove(i);
            i--;
        }
        
        maxS = data.get(0).getPickup();
        maxI = map.get(maxS);
        
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int i = entry.getValue();
            if(i > maxI){
                maxI = i;
                maxS = entry.getKey();
            }
        }
        
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getPickup().equals(maxS)){
                ret = new Pair<>(data.get(i), maxI);
            }
        }
        return ret;
    }
    
    double earliestTimeHelper(String s){
        s = s.replace(':','.');
        return Double.parseDouble(s);
    }
    
    String findEarliestTime(){
        String ret = data.get(0).getTime();
        
        for(int i = 0; i < data.size(); i++){
            if( earliestTimeHelper(data.get(i).getTime()) < earliestTimeHelper(ret)){
                ret = data.get(i).getTime();
            }
        }
        
        return ret;
    }
}
