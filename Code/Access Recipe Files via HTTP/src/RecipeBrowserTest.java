/* Title: RecipeBrowserTest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 20/03/14
 * 
 * Description: Test documentation for Recipe Browser. Test specifics are supplied in comments below.
 */
import org.junit.BeforeClass;
import org.junit.Test;

public class RecipeBrowserTest {

	@BeforeClass
	public static void setUp() {
		System.out.println("This test class contains no automated tests.");
		System.out.println("Instead, visual tests were conducted and the results documented as follows:\n");
	}
	
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
	public void canSelectLocalDirectory() {
		System.out.println("Checking that 'Select Local Recipe File Destination' button works correctly...");
		System.out.println("Case 'yes': button displayed correctly and enabled. Clicking displays file browser window. Selecting folder and pressing 'open' closes window.\n");
	}
	
	@Test
	public void canDownloadSelectedRecipeFiles() {
		System.out.println("Checking that 'Download Selected Recipes' button works correctly...");
		System.out.println("Case 'yes': button displayed correctly and enabled when local directory has been chosen and at least one file is selected.");
		System.out.println(" 			clicking button downloads files to chosen directory and application shows confirmation text.");
		System.out.println("Case 'yes': button displayed but not enabled when no local file directory selected.");
		System.out.println("			button also displayed but not enabled when no files selected.");
		System.out.println("Case 'yes': correct files downloaded to the correct location. Functionality as expected.");
	}
}