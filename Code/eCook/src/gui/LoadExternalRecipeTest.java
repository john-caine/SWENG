/*
 * Programmer: Zayyad Tagwai, Ankita Gangotra & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for LoadExternalRecipe Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Recipe;
import xmlparser.XMLReader;
import eCook.RecipeCollection;

public class LoadExternalRecipeTest {
	
	private Stage stage;
	private RecipeCollection recipeCollection;
	private LoadExternalRecipe loadExternalRecipe;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		stage = new Stage();
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
		// pass a stage and a set of recipe collection into LoadExternalRecipe class
		loadExternalRecipe = new LoadExternalRecipe(stage, recipeCollection);
	}

	@Test
	public void loadExternalRecipeTest() {
		Scene dialogScene = loadExternalRecipe.dialog.getScene();
		/* Test if dialog stage's root is a VBox */
		assertTrue(dialogScene.getRoot() instanceof VBox);
		
		/* Test if the VBox contains topBox */
		VBox loadExtBox = (VBox) dialogScene.getRoot();
		assertTrue(loadExtBox.getChildren().get(0) instanceof HBox);
		
		/* Test if topBox contains ImageView */
		HBox topBox =  (HBox)loadExtBox.getChildren().get(0);
		ImageView loadExtWinCloseBtnHolder = (ImageView) topBox.getChildren().get(1);
		assertTrue(topBox.getChildren().get(1) instanceof ImageView);
		
		/* Test for Close Image */
	
		assertTrue(loadExtWinCloseBtnHolder.getImage() instanceof Image);
		
		
		/* Test if the VBox contains midBox */
		assertTrue(loadExtBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topBox contains HTTP Button */
		HBox midBox =  (HBox)loadExtBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof Button);
		
		Button downloadBtn = (Button) midBox.getChildren().get(0);
		
		/* Test for HTTP Button's Text */
		assertEquals("", downloadBtn.getText());
		
		/* Test for HTTP Button's ID */
		assertEquals("urlBtn", downloadBtn.getId());
		
		/* Test if HTTP Button's style is from css.css */
		assertEquals("[css.css]", downloadBtn.getStylesheets().toString());
		
		/* Test if topBox contains File Browser Button */
		assertTrue(midBox.getChildren().get(1) instanceof Button);
		
		Button fileBrowserBtn = (Button) midBox.getChildren().get(1);
		
		/* Test for HTTP Button's Text */
		assertEquals("", fileBrowserBtn.getText());
		
		/* Test for HTTP Button's ID */
		assertEquals("fileBrowserBtn", fileBrowserBtn.getId());
		
		/* Test if HTTP Button's style is from css.css */
		assertEquals("[css.css]", fileBrowserBtn.getStylesheets().toString());
	}

}
