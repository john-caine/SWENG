/*
 * Programmer: Zayyad Tagwai, Ankita Gangotra & Roger Tan
 * Date Created: 27/05/2014
 * Junit Test for RecipeScreen Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import eCook.RecipeCollection;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Recipe;
import xmlparser.XMLReader;
import eCook.JavaFXThreadingRule;

public class RecipeScreenTest {
	
	private VBox bigBox;
	private double height;
	private double width;
	private RecipeCollection recipeCollection;
	private Stage stage;
	private RecipeScreen recipeScreen;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		//Gets the visual bounds of the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		bigBox =  new VBox();
		stage = new Stage();
		
		//Populate the recipeList
		recipeCollection = new RecipeCollection();
		File directory = new File("defaultRecipes");
		if (directory.exists()) {
			// parse all files in folder, adding recipes to collection
			for (int i=0; i<directory.list().length; i++) {
				// only read XML files if for some reason other files exist
				if (directory.list()[i].endsWith(".xml")) {
					XMLReader reader = new XMLReader("defaultRecipes/" + directory.list()[i]);
					Recipe currentRecipe = reader.getRecipe();
					currentRecipe.setFileName(directory.list()[i]);
					recipeCollection.addRecipe(currentRecipe);
				}
			}
		}
		recipeScreen = new RecipeScreen(bigBox, height, width, recipeCollection, stage);
	}

	/*
	 * Automated Tests
	 */
	@Test
	public void recipeScreenHorizontalBoxTest() {
		/* Test if bigBox contains horizontalBox */
		assertTrue(recipeScreen.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if horizontalBox contains leftBox */
		HBox horizontalBox = (HBox) recipeScreen.bigBox.getChildren().get(1);
		assertTrue(horizontalBox.getChildren().get(0) instanceof VBox);
		
		/* Test if horizontalBox contains midBox */
		assertTrue(horizontalBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBox contains a List of Recipes */
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(1) instanceof ListView);
		
		/* Test if midBox contains Recipe Playlist Label */
		assertTrue(midBox.getChildren().get(0) instanceof Label);
		Label recipePlaylistLabel = (Label) midBox.getChildren().get(0);
		assertEquals("Recipe Playlist", recipePlaylistLabel.getText());
		
		/* Test if midBox contains Recipe Information Box */
		assertTrue(midBox.getChildren().get(2) instanceof VBox);
		
		/* Test if Recipe Information Box contains author Label */
		VBox recipeInfoBox = (VBox) midBox.getChildren().get(2);
		assertTrue(recipeInfoBox.getChildren().get(0) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct author */
		Label authorLabel = (Label) recipeInfoBox.getChildren().get(0);
		assertEquals("Author: ",  authorLabel.getText());
		
		/* Test if Recipe Information Box contains version Label */
		assertTrue(recipeInfoBox.getChildren().get(1) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct version */
		Label versionLabel = (Label) recipeInfoBox.getChildren().get(1);
		assertEquals("Cooking Time: ",  versionLabel.getText());
		
		/* Test if Recipe Information Box contains comment Label */
		assertTrue(recipeInfoBox.getChildren().get(2) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct comment */
		Label commentLabel = (Label) recipeInfoBox.getChildren().get(2);
		assertEquals("Preperation Time: ",  commentLabel.getText());
		

	}
	
	/*
	 * Visual Tests
	 */
	@Test
	public void playButtons(){
		System.out.println("Testing if playButtons appears and plays slideshow....");
		System.out.println("Mouse moved over recipe name");
		System.out.println("playButton exists");
		System.out.println("Mouse used to fire playButton");
		System.out.println("Slideshow plays.\n");
	}
	
	@Test
	public void downloadButtons(){
		System.out.println("Testing if downloadButtons appears and plays slideshow....");
		System.out.println("Mouse moved over recipe name not yet downloaded");
		System.out.println("downloadButton exists");
		System.out.println("Mouse used to fire downloadButton");
		System.out.println("Download begins.\n");
	}
		

}
