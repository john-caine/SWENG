/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a Main Menu
 Version : 1.0 27/2/2014
 */
package eCook;


import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenu {


	private Group menuGroup;
	private Scene scene;
	private HBox hbox;
	private MainMenuButton mainMenuButton;
	public MainMenu(Stage stage){
		
		
		
		
		Screen screen = Screen.getPrimary();
		//Get the visual bounds of the screen
		Rectangle2D screenBounds = screen.getVisualBounds();
		
        menuGroup = new Group();
        
        
        mainMenuButton = new MainMenuButton(stage);
//        hbox = new HBox();
//        
        
        //Add creates slide button to menu group
        menuGroup.getChildren().add(mainMenuButton.mainMenuButtonHbox);
        
        //Creates the main menu scene, adds menugroup and sets the size of the scene to the size of the screen
        scene =  new Scene(menuGroup, screenBounds.getWidth(), screenBounds.getHeight());
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setFullScreen(true);
        
		
	}



}
