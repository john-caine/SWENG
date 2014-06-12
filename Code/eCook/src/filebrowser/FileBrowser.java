/* 
 * Programmers: James, Prakruti
 * Date Created: 18/03/14
 * Description: Opens up a file browser and allows the user to select a file If an invalid file type has been selected 
 *				 it returns null. If a .xml file has been selected it will return the files path.
 */

package filebrowser;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileBrowser {
	final private FileChooser fileChooser;
	private String filepath;
	private File slideshow;

	/**
	 * Instantiate a FileChooser object with capabilities to show a file browser
	 * Set the title of the FileChooser object
	 * Display XML Document only
	 * 
	 */
	public FileBrowser() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Open Slideshow File");
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Document (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
	}

	/**
	 * Opens up a file browser allowing the user to select a file
	 * Returns a string containing the files path if the file is valid
	 * A file is valid if it is not equal to null and contains a ".xml" extension
	 * If a file is not valid returns null
	 * 
	 * @param stage The current stage in use
	 * @return filePath or null The file path to the XMl file, or null 
	 * 
	 */
	public String openFile(Stage stage) {
		slideshow = fileChooser.showOpenDialog(stage);
		if (slideshow != null) {
			filepath = slideshow.getAbsolutePath();
			fileChooser.setInitialDirectory(slideshow.getParentFile());
			if (filepath.endsWith(".xml")) {
				return filepath;
			}
		}
		return null;
	}
}