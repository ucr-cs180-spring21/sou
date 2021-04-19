package group1.cs180_proj;

import java.util.ArrayList;
import java.util.Scanner;

public class Search {

	public static void main(String[] args) {
		//search will consist of a text box
		
		Scanner searchPhrases = new Scanner(System.in);
		String phrase = searchPhrases.nextLine();
		
		
		
		if(phrase == " " || phrase == null)
		{
			//do nothing
		}
		else if (phrase.contains("Highest pickup in state") || phrase.contains("highest state pickups"))
		{
			searchHighestPickupState();
		}
		else if (phrase.contains("least to highest pickups in a state") || phrase.contains("least to most pickups in a state"))
		{
			LeastToHighestPickUpState();
		}
		
	}
	
	public static void LeastToHighestPickUpState()
	{
		ArrayList<String> LTH = DATABASE.getStateArray();
		
	}
	
}
