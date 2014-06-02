/*
 Programmers : James & Prakruti
 Date created: 29/4/2014
 Description: Create a file handler activation button on stage
 Version : 1.0 29/4/2014
 */
package eCook;
	
import filebrowser.FileHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class FileHandlerButton {
	public HBox fileHandlerButtonHbox;
	private Button openFileBrowser;
	private FileHandler fileHandler;
	private String fileHandlerResult;

	public FileHandlerButton(final Stage stage, final RecipeCollection recipeCollection) {
		fileHandler = new FileHandler();
		Screen screen = Screen.getPrimary();
		//Get the visual bounds of the screen
		Rectangle2D screenBounds = screen.getBounds();
		// Create an hbox in the middle of the screen and add the button
		openFileBrowser = new Button("Open Slideshow");
		openFileBrowser.setMaxWidth(120);
		fileHandlerButtonHbox = new HBox();
		fileHandlerButtonHbox.setAlignment(Pos.CENTER);
		fileHandlerButtonHbox.setLayoutX((screenBounds.getWidth()- openFileBrowser.getMaxWidth())/2);
		fileHandlerButtonHbox.setLayoutY((screenBounds.getHeight())/4);
		fileHandlerButtonHbox.getChildren().add(openFileBrowser);	
		// Creates a new slide show when the button is pressed		
		openFileBrowser.setOnAction(new EventHandler<ActionEvent>() {
				@Override
	            public void handle(ActionEvent event) {
					fileHandlerResult = fileHandler.openFile(stage);
					if (fileHandlerResult != null) {
						new SlideShow(stage, fileHandlerResult, recipeCollection);
					} else {
						// Report an error with the file type
					}
	            }
	        });
	}
	
}
