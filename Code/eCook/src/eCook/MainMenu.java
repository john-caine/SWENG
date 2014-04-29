/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a Main Menu
 Version : 1.0 27/2/2014
 			1.1 29/2/2014 James and Prakruti added file handler
 */
package eCook;


import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenu {

	private Group menuGroup;
	private Scene scene;
	private FileHandlerButton fileHandlerButton;
	
	public MainMenu(Stage stage) {
		//Get the visual bounds of the screen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getVisualBounds();
		
		// Create a new group for the main menu so that the stage doesn't require changing
        menuGroup = new Group();     
        // Create a new fileHandlerButton object
        fileHandlerButton = new FileHandlerButton(stage);
        // Add a Hbox containing the fileHandlerButton to the main menu group
        menuGroup.getChildren().add(fileHandlerButton.fileHandlerButtonHbox);
        // Create a scene from the main menu group and update stage to the scene
        scene =  new Scene(menuGroup, screenBounds.getWidth()-200, screenBounds.getHeight()-200);
        stage.setScene(scene);
        stage.sizeToScene();    
	}
}
