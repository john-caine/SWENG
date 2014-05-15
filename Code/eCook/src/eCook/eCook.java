package eCook;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.stage.Stage;
import errorhandler.ErrorHandler;
import javafx.stage.StageStyle;

public class eCook extends Application {
	
	Group root;
	Stage stage;
	SlideShow slideShow;
	static Logger logger;
	
	@Override
	public void start(Stage stage) {
		logger.entering(eCook.class.getName(), "start");
	
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
	    
	    logger.exiting(eCook.class.getName(), "start");
	}
	
	public static void main(String[] args) {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		// Create a file handler for the logger and catch any exceptions
		FileHandler handler = null;
		try {
			handler = new FileHandler("%t/eCook-log.%u.%g.txt", 1024*1024, 20, false);
		} catch (SecurityException e) {
			new ErrorHandler("Failed to initialise the logger.\nPlease restart eCook.");
			Platform.exit();
		} catch (IOException e) {
			new ErrorHandler("Failed to initialise the logger.\nPlease restart eCook.");
			Platform.exit();
		}
		
		// Set the logging formatter to text not XML
		handler.setFormatter(new SimpleFormatter());
		
		// Add the file handler to the logger
		logger.addHandler(handler);
		
		// The the minimum logging level to INFO
		logger.setLevel(Level.ALL);
		
		// Launch the JFx Application thread
		Application.launch(args);
	}
}
