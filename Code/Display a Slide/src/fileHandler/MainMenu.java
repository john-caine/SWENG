/*
 Programmers : Roger & James & Paul
 Date created: 26/2/2014
 Description: Create a Slide
 Version : 1.0 26/2/2014
 */
package fileHandler;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenu extends Application {
	private String filepath;
	@Override
	public void start(Stage primaryStage) {
		FileHandler aFileHandler = new FileHandler();
		filepath = aFileHandler.openFile();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
