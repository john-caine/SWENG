package eCook;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class eCook extends Application {
	
	Group root;
	Stage stage;
	SlideShow slideShow;
	
	@Override
	public void start(Stage stage) {				
		// This is the group for the main menu - DONT DELETE IT!
		root = new Group();
	    // Set the title of the window
	    stage.setTitle("eCook");
	    // Add main menu to the stage
		@SuppressWarnings("unused")
		MainMenu mainMenu = new MainMenu(stage);
		// Show the stage when ready
		//stage.initStyle(StageStyle.UNDECORATED);
	    stage.show();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}
}
