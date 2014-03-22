/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a Main Menu
 Version : 1.0 27/2/2014
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
	private MainMenuButton mainMenuButton;
	
	public MainMenu(Stage stage) {

		//Get the visual bounds of the screen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getVisualBounds();
		
		// Create a new group for the main menu so that the stage doesn't require changing
        menuGroup = new Group();     
        
        // Add the main menu buttons to the view.
        mainMenuButton = new MainMenuButton(stage);       
        
        //Add creates slide button to menu group
        menuGroup.getChildren().add(mainMenuButton.mainMenuButtonHbox);
        
        //Creates the main menu scene, adds menugroup and sets the size of the scene to the size of the screen
        scene =  new Scene(menuGroup, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setFullScreen(true);
        
		
	}



}
