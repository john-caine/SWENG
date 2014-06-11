package xmlfilepathhandler;
/* Title: XMLFilepathHandlerTest
 * 
 * Programmers: James

 */

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import xmlparser.XMLReader;

public class XMLFilepathHandlerTest {
	File filepath;
	XMLReader reader;
	XMLFilepathHandler filepathHandler;
	
	@Before
	public void setUp() throws Exception {
		// Read in the PWSExamplePlaylist_4 from local app data
		// May need to be downloaded through eCook first
		filepath = new File(System.getenv("localappdata") + "/eCook/Recipes");
	}
	
	/*
	 * When eCook is run firstly we run through the recipes and check that the content is all obtainable
	 * - whether local or not local
	 * Warning: If test doesn't pass try running eCook from the IDE! It will copy the test XMLs to your local app data
	 * 
	 */
	@Test
	public void assertFilepathsWork() {
		filepathHandler = new XMLFilepathHandler();
		assertTrue(filepathHandler.checkMediaPathsExistOffline("TESTOfflineContent.xml"));
		assertFalse(filepathHandler.checkMediaPathsExistOffline("TESTOfflineContent2.xml"));
	}
	
	/*
	 * Delete any downloaded content
	 * Instantiate a file path handler
	 * Run the TestDownloadContent.xml through the file path handler
	 * This XML file has online content which should be downloaded
	 * Check the downloaded content exists
	 */
	@Test
	public void downloadFilesTest() {
		File index = new File(filepath + "/TEST Download Content");
		try {
			FileUtils.deleteDirectory(index);
		} catch (IOException e) {
			System.out.println("Error with downloadFilesTest() in deleting previous offline content");
		}
		filepathHandler = new XMLFilepathHandler();
		XMLReader reader = new XMLReader(filepath + "/TestDownloadContent.xml");
		filepathHandler.downloadRecipeMedia(reader.getRecipe());
		File downloadedContent = new File(filepath + "/TEST Download Content/sample1.mp3");
		assertTrue(downloadedContent.exists());
	}
	
	/*
	 * By convention XML files should be provided with relative filepaths to their content folder
	 * (within the same directory) called <title>
	 * These filepaths should be made absolute
	 * 
	 */
	@Test
	public void updateFilepathsTest() {
		// Initially relative filepath, check the relative filepath exists
		filepathHandler = new XMLFilepathHandler();
		XMLReader reader = new XMLReader(filepath + "/TESTOfflineContent.xml");
		assertEquals("TEST Offline Content/slideBackground.png", reader.getRecipe().getSlide(0).getContent().getImage(0).getUrlName());
		// We require reader to contain absolute file paths
		filepathHandler.setMediaPaths(reader);
		// Check that an absolute filepath has been provided
		assertEquals(new File(filepath + "/TEST Offline Content/slideBackground.png").toURI().toASCIIString(), reader.getRecipe().getSlide(0).getContent().getImage(0).getUrlName());
		// Check that media paths are not broken
		assertFalse(filepathHandler.mediaPathsAreBroken());
	}
	
	/*
	 * TESTOfflineContent2 has incorrect media paths
	 * Check that the mediaPathsAreBroken() if we ever try to get the updated path for something that does not exist
	 */
	@Test
	public void tryToUpdateBrokenFilepathsTest() {
		// Initially relative filepath, check the relative filepath exists
		filepathHandler = new XMLFilepathHandler();
		XMLReader reader = new XMLReader(filepath + "/TESTOfflineContent2.xml");
		// We require reader to contain absolute file paths
		filepathHandler.setMediaPaths(reader);
		// Check that an absolute filepath has been provided
		assertTrue(filepathHandler.mediaPathsAreBroken());
	}


}