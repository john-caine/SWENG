/* Programmer: Max
 * Date Created: 17/05/14
 * Description: ShoppingList Class to hold current user Shopping List in the form of a list of Strings
 */
package shoppingList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShoppingList {
	List<String> shoppingList;
	
	// constructor
	public ShoppingList() {
		// get the current shopping list if it exists, create one if not
		shoppingList = readFromTextFile();
	}
	
	// method to get number of items in shopping list
	public int getNumberOfItems() {
		return shoppingList.size();
	}
	
	// method to add item to shopping list
	public void addItem(String item) {
		if (item != null | !item.equals("")) {
			shoppingList.add(item);
		}
		else {
			System.out.println("Cannot add to shopping list: invalid item");
		}
		saveToTextFile();
	}
	
	// method to remove an item from the shopping list
	public void removeItem(String itemName) {
		if (itemName != null | !itemName.equals("")) {
			if (shoppingList.contains(itemName)) {
				shoppingList.remove(itemName);
			}
		}
		else {
			System.out.println("Cannot remove from shopping list: invalid item");
		}
		saveToTextFile();
	}
	
	// method to save the shopping list as a text file
	private void saveToTextFile() {
		if (shoppingList != null) {
			try {
				// create file 
				FileWriter notesStream = new FileWriter("shoppingList.txt");
				BufferedWriter output = new BufferedWriter(notesStream);
				// write String items to file
				for (int i=0; i<shoppingList.size(); i++) {
					output.write(shoppingList.get(i));
				}
				// close the output stream
				output.close();
			} 
			catch (IOException e) {
				System.out.println("Error when updating shopping list file");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Cannot update shopping list file: shopping list is null");
		}
	}
	
	// method to read out strings from a text file
	public ArrayList<String> readFromTextFile() {
		File fileToRead = new File("shoppingList.txt");
		
		if (fileToRead.exists()) {
			// create array list to populate
			ArrayList<String> shoppingList = new ArrayList<String>();
			
		    try {
		       	FileReader fileStream = new FileReader("shoppingList.txt");
		        BufferedReader input =  new BufferedReader(fileStream);
		        String line = input.readLine();
		        
		        // loop through each line, adding new strings
		        while (line != null) {
		        	shoppingList.add(line);
		        	line = input.readLine();
		        }
		        
		        // close the input stream
		        input.close();
		    }
		    catch (IOException ex) {
		    	System.out.println("Error when reading shopping list file");
		        ex.printStackTrace();
		    }
		    
		    return shoppingList;
		}
		// return a new list if the file doesn't exist
		else {
			return new ArrayList<String>();
		}  
	}
}