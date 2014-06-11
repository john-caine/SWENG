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
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;

public class TextFileHandler {
	// use an error flag to report issues to the testing class
	boolean error;
	private Logger logger;

	// constructor
	public TextFileHandler() {
		error = false;
		logger = Logger.getLogger(eCook.class.getName());
	}

	/*
	 *  method to write String to text file
	 */
	public void writeTextFile(String notes, Integer slideID, String recipeTitle) {
		if (notes != null && slideID != null && recipeTitle != null) {
			try {
				// create file 
				FileWriter notesStream = new FileWriter(System.getenv("localappdata") + "/eCook/Recipes/" + recipeTitle + "_" + slideID.toString() + ".txt");
				BufferedWriter output = new BufferedWriter(notesStream);
				output.write(notes);
				logger.log(Level.INFO, "Notes text file written");
				error = false;
				// close the output stream
				output.close();
			} 
			catch (IOException e) {
				logger.log(Level.WARNING, "Error when writing notes text file");
				error = true;
			}
		}
		else {
			logger.log(Level.WARNING, "Error: either there are no notes or the SlideID/recipeTitle is invalid");
			error = true;
		}	
	}
	
	/*
	 *  method to read String from text file
	 */
	public String readTextFile(String filename) {
		File fileToRead = new File(System.getenv("localappdata") + "/eCook/Recipes/" + filename);
		
		if (filename != null && fileToRead.exists()) {
			StringBuilder contents = new StringBuilder();
			 
		    try {
		       	FileReader fileStream = new FileReader(System.getenv("localappdata") + "/eCook/Recipes/" + filename);
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
		    	logger.log(Level.WARNING, "Error reading text file");
		        error = true;
		    }
		    
		    return contents.toString();
		}
		// return null if the filename is invalid
		else {
			error = true;
			return null;
		}  
	}
}