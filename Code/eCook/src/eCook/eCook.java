package eCook;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class eCook extends Application {
	
	Group root;
	Scene scene; 
	Stage stage;
	SlideShow slideShow;

	public void start(Stage stage) {				
		// This is the group for the main menu - DONT DELETE IT!
		root = new Group();
	    
	    stage.setTitle("eCook");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setFullScreen(true);
		stage.show();
		
		@SuppressWarnings("unused")
		MainMenu mainMenu = new MainMenu(stage);	
	}
	
		public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}
}
