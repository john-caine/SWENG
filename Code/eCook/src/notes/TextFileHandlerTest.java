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
import eCook.JavaFXThreadingRule;

public class TextFileHandlerTest {
	static TextFileHandler handler;
	
	// instantiate the text file handler class before the tests
	@BeforeClass
	public static void setup() throws FileNotFoundException {
		// remove any previous example notes files from previous tests
		File exampleFile1 = new File(System.getenv("localappdata") + "/eCook/Recipes" + "Example title" + "_" + "7" + ".txt");
		if (exampleFile1.exists()) {
			exampleFile1.delete();
		}
		File exampleFile2 = new File(System.getenv("localappdata") + "/eCook/Recipes" + "notesTestTitle" + "_" + "8" + ".txt");
		if (exampleFile2.exists()) {
			exampleFile2.delete();
		}
		File exampleFile3 = new File(System.getenv("localappdata") + "/eCook/Recipes" + "exampleRecipeTitle0" + "_" + "0" + ".txt");
		if (exampleFile3.exists()) {
			exampleFile3.delete();
		}
		
		// create an instance of the textfilehandler
		handler = new TextFileHandler();
	}
	
	// check that no text file is created when null notes String is parsed
	@Test
	public void nullNotesTextFileNotCreated() {
		handler.writeTextFile(null, 5, "Example Recipe Title");
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "Example Recipe Title" + "_" + "5" + ".txt");
		assertFalse(file.exists());
		assertTrue(handler.error);
	}
	
	// check that no text file is created when null SlideID String is parsed
	@Test
	public void nullSlideIDTextFileNotCreated() {
		handler.writeTextFile("Example notes", null, "Example Recipe Title");
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "Example Recipe Title" + "_" + ".txt");
		assertFalse(file.exists());
		assertTrue(handler.error);
	}
	
	// check that no text file is created when null recipe title String is parsed
	@Test
	public void nullRcipeTitleTextFileNotCreated() {
		handler.writeTextFile("Example notes", 1, null);
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "_" + "1" + ".txt");
		assertFalse(file.exists());
		assertTrue(handler.error);
	}
	
	// check that text file is created when valid Strings are parsed
	@Test
	public void validArgumentsTextFileCreated() {
		handler.writeTextFile("here are some example notes.", 7, "Example title");
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "Example title" + "_" + "7" + ".txt");
		assertTrue(file.exists());
		assertFalse(handler.error);
	}
	
	// check that the contents of the text file is the same as the notes input String
	// ensure that any special formatting (\n, \t) is preserved
	@Test
	public void textFileContainsCorrectString() {
		handler.writeTextFile("This is what should be stored in the txt file\nNew Line\ttabbed\nReturn", 8, "notesTestTitle");
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "notesTestTitle" + "_" + "8" + ".txt");
		assertTrue(file.exists());
		assertEquals("This is what should be stored in the txt file\nNew Line\ttabbed\nReturn", handler.readTextFile("notesTestTitle" + "_" + "8" + ".txt"));
		assertFalse(handler.error);
	}
	
	// further test the read text file method with a null string for a filename
	@Test
	public void nullFileNameReturnsErrorFromReader() {
		handler.writeTextFile("example notes", 0, "exampleRecipeTitle0");
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "/exampleRecipeTitle0_0.txt");
		assertTrue(file.exists());
		assertEquals(null, handler.readTextFile(null));
		assertTrue(handler.error);
	}
	
	// check that an error is thrown and nothing is returned when trying to read a file that doesn't exist
	@Test
	public void nonExistentFileReturnsErrorFromReader() {
		File file = new File(System.getenv("localappdata") + "/eCook/Recipes" + "/nothingHere_1.txt");
		assertFalse(file.exists());
		assertEquals(null, handler.readTextFile("nothingHere_1.txt"));
		assertTrue(handler.error);
	}
}