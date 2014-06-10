package eCook;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import xmlparser.Recipe;
import xmlparser.XMLReader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import errorhandler.ErrorHandler;
import javafx.stage.StageStyle;

import org.apache.commons.io.*;

public class eCook extends Application {
	
	Group root;
	Stage stage;
	SlideShow slideShow;
	RecipeCollection recipeCollection;
	static Logger logger;
	
	@Override
	public void start(Stage stage) {
  
		stage.getIcons().add(new Image("eCookIcon.png"));
		try {
			// Load external font
			Font.loadFont(eCook.class.getResource("BuxtonSketch.ttf").toExternalForm(), 10);
		} 
		catch(NullPointerException e) {
			logger.log(Level.WARNING, "Failed to import BoxtonSketch font");
		}
			
		/* This is where the parser is called to populate the 	 *
		 * list of recipes available in the defaultRecipe folder */
		recipeCollection = new RecipeCollection();
		
		// get number of files in the defaultRecipe folder
		URL defaultDirectory = getClass().getResource("/defaultRecipes_new");
		File filePath = new File(defaultDirectory.getPath());

		// Make a recipes folder in %localappdata%
		File recipeDirectory = new File(System.getenv("localappdata") + "/eCook/Recipes");			
		recipeDirectory.mkdirs();
		
		// Make a shoppingList folder in %localappdata%
		File shoppingListDirectory = new File(System.getenv("localappdata") + "/eCook/ShoppingLists");
		shoppingListDirectory.mkdirs();
		
		// Copy the defaultRecipes folder to our %localappdata% folder
		try {
			FileUtils.copyDirectory(filePath, recipeDirectory);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Unable to copy defaultRecipes folder to Local Application Data folder");
			new ErrorHandler("Unable to copy defaultRecipes folder to Local Application Data folder");
		}
		
		// parse all files in folder, adding recipes to collection
		for (int i=0; i<recipeDirectory.list().length; i++) {
			// only read XML files if for some reason other files exist
			if (recipeDirectory.list()[i].endsWith(".xml")) {
				logger.log(Level.INFO, "Calling XML parser");
				XMLReader reader = new XMLReader(recipeDirectory + "/" + recipeDirectory.list()[i]);
				Recipe currentRecipe = reader.getRecipe();
				currentRecipe.setFileName(recipeDirectory.list()[i]);
				recipeCollection.addRecipe(currentRecipe);
				logger.log(Level.INFO, "Logged Recipe" + recipeDirectory.list()[i]);
			}
		}


		// This is the group for the main menu - DONT DELETE IT!
		root = new Group();
	    // Set the title of the window
	    stage.setTitle("eCook");
	    stage.initStyle(StageStyle.UNDECORATED);
	    // Add main menu to the stage
		@SuppressWarnings("unused")
		MainMenu mainMenu = new MainMenu(stage, recipeCollection);
		// Show the stage when ready
	    stage.show();
	}
	
	public static void main(String[] args) {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		/*
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
		logger.setLevel(Level.INFO);
		*/
		
		// Launch the JFx Application thread
		Application.launch(args);
	}
}
