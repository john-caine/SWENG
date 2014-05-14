/*
 Programmers : Roger & Zayyad
 Date created: 27/2/2014
 Description: Create a Main Menu

 */
package eCook;


import gui.MainMenuContent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenu {

	public Group menuGroup;
	private Scene scene;
	public FileHandlerButton fileHandlerButton;
	private MainMenuContent mainMenuContent;
	
	public MainMenu(Stage stage) {
		//Get the visual bounds of the screen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getVisualBounds();
		
		
		// Create a new group for the main menu so that the stage doesn't require changing
        menuGroup = new Group();
        // Create a new MainMenuContent object
        mainMenuContent =  new MainMenuContent(stage);    
        // Create a new fileHandlerButton object
        fileHandlerButton = new FileHandlerButton(stage);
        // Add Main Menu Content to main menu group
        menuGroup.getChildren().add(mainMenuContent.bigBox);
        // Add a Hbox containing the fileHandlerButton to the main menu group
        menuGroup.getChildren().add(fileHandlerButton.fileHandlerButtonHbox);
        // Create a scene from the main menu group and update stage to the scene
        scene =  new Scene(menuGroup, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.setFullScreen(true);
	}
}
