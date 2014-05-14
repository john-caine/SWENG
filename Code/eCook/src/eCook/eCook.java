package eCook;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.stage.Stage;
<<<<<<< HEAD
import errorhandler.ErrorHandler;
=======
import javafx.stage.StageStyle;
>>>>>>> b699096362091da01052b4a7c6c60a44e4237e59

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
	    stage.initStyle(StageStyle.UNDECORATED);
	    // Add main menu to the stage
		@SuppressWarnings("unused")
		MainMenu mainMenu = new MainMenu(stage);
		// Show the stage when ready
		//stage.initStyle(StageStyle.UNDECORATED);
	    stage.show();
	    
	
	}
	
	public static void main(String[] args) {
//		Logger logger = Logger.getLogger(eCook.class.getName());
//		FileHandler handler = new FileHandler("eCook-log.%u.%g.txt", 1024*1024, 20, false);
//		logger.addHandler(handler);
//		logger.setLevel(Level.INFO);
		
		// TODO Auto-generated method stub
			Application.launch(args);
	}
}
