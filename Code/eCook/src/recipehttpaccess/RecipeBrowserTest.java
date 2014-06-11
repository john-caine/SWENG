package recipehttpaccess;
/* Title: RecipeBrowserTest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 20/03/14
 * 
 * Description: Test documentation for Recipe Browser. Test specifics are supplied in comments below.
 */
import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;
import eCook.RecipeCollection;

public class RecipeBrowserTest {
	// declare variables
	RecipeBrowser recipeBrowser;
	Stage testStage;
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before
	public void setUp() {
		testStage = new Stage();
		recipeBrowser = new RecipeBrowser(testStage, new RecipeCollection(), true, new Label());
	}
	
	/* Visual Tests */
	
	@Test
	public void noInternetConnectionOrUnreachableServerReportsError() {
		System.out.println("Checking internet connection upon app load up...");
		System.out.println("Case 'no connection': reports 'Problem accessing recipe files on server.' and application termination");
		System.out.println("Case 'connection OK': application launched successfully. GUI visible as expected.\n");
	}
	
	@Test
	public void listOfAvailableRecipeFilesReadAndDisplayedCorrectly() {
		System.out.println("Checking that list of available recipe files is displayed...");
		System.out.println("Case '3 files on server': reports 3 filenames displayed in list on GUI");
		System.out.println("Case 'no files on server': message displayed on GUI 'Sorry. No recipes available to download.'\n");
	}
	
	@Test
	public void canDownloadSelectedRecipeFiles() {
		System.out.println("Checking that 'Download Selected Recipes' button works correctly...");
		System.out.println("Case 'yes': button displayed correctly and enabled when at least one file is selected.");
		System.out.println(" 			clicking button downloads files to defaultRecipes folder and application shows confirmation text.");
		System.out.println("Case 'yes': button displayed but not enabled when no files selected.");
		System.out.println("Case 'yes': correct files downloaded to the correct location. Functionality as expected.");
	}
	
	/* Automated Tests */
	
	// check that the correct list of recipes is retrieved from the server
	@Test
	public void correctRecipeListRetrievedFromServer() {
		assertEquals("PWSExamplePlaylist_4.xml", recipeBrowser.availableRecipeFiles.get(0));
		assertEquals("example1.xml", recipeBrowser.availableRecipeFiles.get(1));
		assertEquals("example2.xml", recipeBrowser.availableRecipeFiles.get(2));
		assertEquals("example3.xml", recipeBrowser.availableRecipeFiles.get(3));
		assertEquals("exampleSoups.xml", recipeBrowser.availableRecipeFiles.get(4));
		assertEquals(5, recipeBrowser.availableRecipeFiles.size());
	}
	
	// check that the download button is enabled at the correct times
	@Test
	public void downloadButtonIsEnabledCorrectly() {
		recipeBrowser.listOfRecipeFiles.getSelectionModel().select(0);
		assertFalse(recipeBrowser.downloadButton.isDisabled());
	}
	
	// check that the download procedure executes correctly
	@Test
	public void downloadRecipesWorksCorrectly() {
		recipeBrowser.listOfRecipeFiles.getSelectionModel().select(0);
		assertFalse(recipeBrowser.downloaded);
		assertEquals(1, recipeBrowser.listOfRecipeFiles.getSelectionModel().getSelectedItems().size());
		assertEquals("PWSExamplePlaylist_4.xml", recipeBrowser.listOfRecipeFiles.getSelectionModel().getSelectedItem());
		recipeBrowser.downloadButton.fireEvent(new ActionEvent());
		// point to the defaults folder
		URL defaultDirectory = getClass().getResource("/defaultRecipes_new");
		File file = new File(defaultDirectory.getPath() + "/PWSExamplePlaylist_4.xml");
		assertTrue(file != null);
		assertTrue(file.exists());
		assertTrue(recipeBrowser.downloaded);
	}
}