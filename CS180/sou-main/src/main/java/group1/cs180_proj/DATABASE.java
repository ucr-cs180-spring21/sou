package group1.cs180_proj;

import java.io.*;
import java.io.BufferedReader;  
import java.io.FileNotFoundException;  
import java.io.FileReader;  
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List; 
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.lang.String;

public class DATABASE {

	
	public static ArrayList<String> date = new ArrayList<String>();
	public static ArrayList<String> time = new ArrayList<String>();
	public static ArrayList<String> state = new ArrayList<String>();
	public static ArrayList<String> PickedUpFrom = new ArrayList<String>();
	public static ArrayList<String> address = new ArrayList<String>();
	public static ArrayList<String> street = new ArrayList<String>();
	
	public static void dataInArray() throws FileNotFoundException {
		
		String dial7Data = "other-Dial7_B00887.csv";
		Scanner scan = new Scanner(new File(dial7Data));
		
		String[] st = new String[19500];
		
		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> time = new ArrayList<String>();
		ArrayList<String> state = new ArrayList<String>();
		ArrayList<String> PickedUpFrom = new ArrayList<String>();
		ArrayList<String> address = new ArrayList<String>();
		ArrayList<String> street = new ArrayList<String>();
		
		while(scan.hasNext())
		{
			st = scan.nextLine().split(",");
			date.add(st[0]);
			time.add(st[1]);
			state.add(st[2]);
			PickedUpFrom.add(st[3]);
			address.add(st[4]);
			street.add(st[5]);
			
		}
		
		
		
	}
	
	public static ArrayList<String> getDateArray() {
		return date;
	}
	
	public static ArrayList<String> getTimeArray() {
		return time;
	}
	
	public static ArrayList<String> getStateArray() {
		return state;
	}
	
	public static ArrayList<String> getPickedUpFromArray() {
		return PickedUpFrom;
	}
	
	public static ArrayList<String> getaddress() {
		return address;
	}
	
	public static ArrayList<String> getStreetArray() {
		return street;
	}

}
