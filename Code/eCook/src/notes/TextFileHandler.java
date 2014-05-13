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

	// constructor
	public TextFileHandler() {
	}

	// method to write String to text file
	public void writeTextFile(String notes, Integer slideID) {
		if (notes != null && slideID != null) {
			try {
				// create file 
				FileWriter notesStream = new FileWriter(slideID.toString() + "_notes.txt");
				BufferedWriter output = new BufferedWriter(notesStream);
				output.write(notes);
				System.out.println("Your file has been written");
				// close the output stream
				output.close();
			} 
			catch (IOException e) {
				System.out.println("Error when writing text file");
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Error: either there are no notes or the SlideID is invalid");
		}	
	}
	
	// method to read String from text file
	public String readTextFile(String filename) {
		File fileToRead = new File(filename);
		
		if (filename != null && fileToRead.exists()) {
			StringBuilder contents = new StringBuilder();
			 
		    try {
		       	FileReader fileStream = new FileReader(filename);
		        BufferedReader input =  new BufferedReader(fileStream);
		        String line = input.readLine();
		        
		        while (line != null) {
		        	contents.append(line);
		        	line = input.readLine();
		        	if (line != null) {
		        		contents.append(System.getProperty("line.separator"));
		        	}
		        }
		        
		        // close the input stream
		        input.close();
		    }
		    catch (IOException ex) {
		    	System.out.println("Error when reading text file");
		        ex.printStackTrace();
		    }
		    
		    return contents.toString();
		}
		// return null if the filename is invalid
		else {
			return null;
		}  
	}
}
