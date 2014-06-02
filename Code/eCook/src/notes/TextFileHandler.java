package notes;

/* Title: TextFileHandler
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 09/05/14
 * 
 * Description: Basic file write and reader which accepts a string and saves it to a text file. 
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class TextFileHandler {
	// use an error flag to report issues to the testing class
	boolean error;

	// constructor
	public TextFileHandler() {
		error = false;
	}

	// method to write String to text file
	public void writeTextFile(String notes, Integer slideID, String recipeTitle) {
		if (notes != null && slideID != null && recipeTitle != null) {
			try {
				// create file 
				FileWriter notesStream = new FileWriter("notes/" + recipeTitle + "_" + slideID.toString() + ".txt");
				BufferedWriter output = new BufferedWriter(notesStream);
				output.write(notes);
				System.out.println("Your file has been written");
				error = false;
				// close the output stream
				output.close();
			} 
			catch (IOException e) {
				System.out.println("Error when writing text file");
				error = true;
			}
		}
		else {
			System.out.println("Error: either there are no notes or the SlideID/recipeTitle is invalid");
			error = true;
		}	
	}
	
	// method to read String from text file
	public String readTextFile(String filename) {
		File fileToRead = new File("notes/" + filename);
		
		if (filename != null && fileToRead.exists()) {
			StringBuilder contents = new StringBuilder();
			 
		    try {
		       	FileReader fileStream = new FileReader("notes/" + filename);
		        BufferedReader input =  new BufferedReader(fileStream);
		        String line = input.readLine();
		        
		        while (line != null) {
		        	contents.append(line);
		        	line = input.readLine();
		        	if (line != null) {
		        		contents.append(System.getProperty("line.separator"));
		        	}
		        }
		        
		        error = false;
		        // close the input stream
		        input.close();
		    }
		    catch (IOException ex) {
		    	System.out.println("Error when reading text file");
		        error = true;
		    }
		    
		    return contents.toString();
		}
		// return null if the filename is invalid
		else {
			System.out.println("cannot read file: filename is invalid or file does not exist");
			error = true;
			return null;
		}  
	}
}
