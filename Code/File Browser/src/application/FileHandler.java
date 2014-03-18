package application;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
/**
 * File handler class
 * Opens up a file browser and allows the user to select a file
 * If an invalid file type has been selected it returns null
 * If a .xml file has been selected it will return the files path
 * 
 * @author James Oatley, Prakruti Sinha
 * 
 */
public class FileHandler {
	private Stage stage;
	private FileChooser fileChooser;
	private String filepath;

	/**
	 * Constructor
	 * Instantiate a FileChooser object with capabilities to show a file browser
	 * Set the title of the FileChooser object
	 * 
	 */
	public FileHandler() {
		fileChooser = new FileChooser();
		fileChooser.setTitle("Open Slideshow File");
	}

	/**
	 * openFile() method
	 * Opens up a file browser allowing the user to select a file
	 * Returns a string containing the files path if the file is valid
	 * A file is valid if it is not equal to null and contains a ".xml" extension
	 * If a file is not valid returns null
	 * 
	 */
	public String openFile() {
		File slideshow = fileChooser.showOpenDialog(stage);
		if (slideshow != null) {
			filepath = slideshow.getAbsolutePath();
			if (filepath.endsWith(".xml")) {
				return filepath;
			}
		}
		return null;
	}
}