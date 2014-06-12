/*
 *Programmers : Roger, Zayyad, James and Ankita
 *Date created: 27/2/2014
 *Description: Create a Main Menu
 */
package eCook;

import gui.MainMenuContent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenu {

	public Group menuGroup;
	private Scene scene;
	private MainMenuContent mainMenuContent;
	
	/**
	 * The main menu for eCook, sets parameters required for the stage, and populates the menu with content.
	 * 
	 * @param stage The stage to be used.
	 * @param recipeCollection The collection of recipes.
	 */
	public MainMenu(Stage stage, RecipeCollection recipeCollection) {
		//Gets the visual bounds of the screen
		Screen screen = Screen.getPrimary();
		Rectangle2D screenBounds = screen.getBounds();

		// Create a new group for the main menu so that the stage doesn't require changing
        menuGroup = new Group();
        
        // Create a new MainMenuContent object
        mainMenuContent =  new MainMenuContent(stage, recipeCollection); 
        
        // Add Main Menu Content to main menu group
        menuGroup.getChildren().add(mainMenuContent.bigBox);

        
        // Create a scene from the main menu group and update stage to the scene
        scene =  new Scene(menuGroup, screenBounds.getWidth(), screenBounds.getHeight());
        
        stage.setScene(scene);
        stage.setFullScreen(true);
        
        //Removes the exit hint from the main menu
        stage.setFullScreenExitHint("");
        
        //Removes the key combination to exit fullscreen when in main menu
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
	}
}
