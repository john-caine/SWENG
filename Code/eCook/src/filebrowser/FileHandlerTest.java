package filebrowser;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class FileHandlerTest {
	// Declare variables
	private FileHandler fileHandler;
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
		fileHandler = new FileHandler();
	}

	/**
	 * Run a test to check that the return value is not null (only not null if .xml type chosen)
	 * Run a test to check that the return value is a file path by printing to console
	 */
	@Test
	public void fileHandler() {
		fileHandlerResult = fileHandler.openFile();
		assertNotNull(fileHandlerResult);
		if (fileHandlerResult != null) {
			System.out.print("File path: " + fileHandlerResult + "\n");
		}
	}
}
