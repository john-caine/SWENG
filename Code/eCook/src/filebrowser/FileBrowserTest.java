/* Title: FileBrowserTest
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 18/03/14
 * 
 * Description: Test class for opening a file browser window and return a string representing the filepath
 * to the selected file
 * 
 */
package filebrowser;

import static org.junit.Assert.*;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;

public class FileBrowserTest {
	// Declare variables
	private Stage stage;
	private FileBrowser fileHandler;
	private String fileHandlerResult;
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	/**
	 * Setup for tests
	 * Instantiate a new fileHandler object by calling constructor
	 */
	@Before
	public void setup() {
		stage = new Stage();
		fileHandler = new FileBrowser();
	}

	/**
	 * Run a test to check that after a selection is made, it is an XML file 
	 * Run a test to check that if window is closed(no selection made, returns a null 
	 * Run a test to check that the return value is a file path by printing to console
	 */
	@Test
	public void fileHandler() {
		fileHandlerResult = fileHandler.openFile(stage);
		assertFalse(stage.isShowing());
		if ((fileHandlerResult != null)){
			assertTrue(fileHandlerResult.endsWith(".xml"));
			System.out.print("File path: " + fileHandlerResult + "\n");
		}
		else{
			assertTrue(fileHandlerResult == null);
		}
	}
}
