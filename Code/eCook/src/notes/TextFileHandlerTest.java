package notes;

/* Title: TextFileHandlerTest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 09/05/14
 * 
 * Description: Testing for CreateTextFile Class. Test specifics are detailed below.
 */

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.BeforeClass;
import org.junit.Test;

public class TextFileHandlerTest {
	
	// delete any previous "notes.txt" file before running each test
	@BeforeClass
	public static void setup() throws FileNotFoundException {
		File file1 = new File("Slide07_notes.txt");
		if (file1.exists()) {
			file1.delete();
		}
		File file2 = new File("Slide501_notes.txt");
		if (file2.exists()) {
			file2.delete();
		}
	}
	
	// check that no text file is created when null notes String is parsed
	@Test
	public void nullNotesTextFileNotCreated() {
		TextFileHandler handler = new TextFileHandler();
		handler.writeTextFile(null, "Slide1005");
		File file = new File("Slide1005_notes.txt");
		assertFalse(file.exists());
	}
	
	// check that no text file is created when null SlideID String is parsed
	@Test
	public void nullSlideIDTextFileNotCreated() {
		TextFileHandler handler = new TextFileHandler();
		handler.writeTextFile("example notes", null);
		File file = new File("_notes.txt");
		assertFalse(file.exists());
	}
	
	// check that text file is created when valid Strings are parsed
	@Test
	public void validArgumentsTextFileCreated() {
		TextFileHandler handler = new TextFileHandler();
		handler.writeTextFile("here are some example notes.", "Slide07");
		File file = new File("Slide07_notes.txt");
		assertTrue(file.exists());
	}
	
	// check that the contents of the text file is the same as the notes input String
	// ensure that any special formatting (\n, \t) is preserved
	@Test
	public void textFileContainsCorrectString() {
		TextFileHandler handler = new TextFileHandler();
		handler.writeTextFile("This is what should be stored in the txt file\nNew Line\ttabbed\nReturn", "Slide501");
		assertEquals("This is what should be stored in the txt file\nNew Line\ttabbed\nReturn", handler.readTextFile("Slide501_notes.txt"));
	}
}