/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group1.cs180_proj;

import java.util.ArrayList;

/**
 *
 * @author patri
 */
public abstract class FileIO {


    public abstract void backupData(String name);
    public abstract ArrayList<String> getFiles();
    public abstract ArrayList<Uber> importData(String file);


}
