/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author patri
 */
public class FileIO {
    ArrayList<Uber> data;
    private final String projDir = System.getProperty("user.dir");
    
    public FileIO(ArrayList<Uber> d){
        data = d;
    }
    
    public FileIO(){
        
    }

    
    
    public void backupData(String name){
        try{
            FileOutputStream fo = new FileOutputStream(name + ".ser");
            ObjectOutputStream oo = new ObjectOutputStream(fo);

            oo.writeObject(data);
            oo.flush();
            oo.close();

        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    

    private class FileTest implements FileFilter{

        @Override
        public boolean accept(File pathname) {
            
        if (pathname.getName().toLowerCase().endsWith(".ser"))
        {
          return true;
        }
            return false;
        }
        
    }
    public ArrayList<String> getFiles()
    {
        ArrayList<String> ret = new ArrayList<>();
        File dir = new File(projDir);
        
        File[] files = dir.listFiles(new FileTest());
        
        for(File f : files){
            ret.add(f.toString());
        }
        
      
        return ret;
    }
  
    public ArrayList<Uber> importData(String file){
        ArrayList<Uber> ret = new ArrayList();
        
        try{
            FileInputStream fi = new FileInputStream(file);
            ObjectInputStream oi = new ObjectInputStream(fi);

            ret = (ArrayList<Uber>) oi.readObject();
            oi.close();
           
        }catch (Exception e) {
            e.printStackTrace();
        }

        
        return ret;
        
    }
    
   
}
