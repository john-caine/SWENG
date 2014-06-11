/*
 *Programmers : Roger, Prakruti & Zayyad
 *Date created: 27/2/2014
 *Description: JUnit test class for eCook's main menu.
 *
 *Program Description : This program is designed to create a slide from the main menu. 
 *					    The program is capable of exiting the slide and return to the main menu.
 *					   
 *Program Setup : This program is designed to create a fullscreen slide whenever the "Create Slide" button
 *				  is being pressed. In the slide, there's a "Exit Slide" button to close the slide and return
 *			      to main menu. This could also be done by pressing the "ESC" key.
 *				
 *Test Results :When "Create Slide" button is pressed, a separate fullscreen window (slide) pops up.
 *			    When "Exit Slide" button is pressed, the slide window closes and return to main menu.
 *			    When "ESC" key is pressed, the slide window closes and return to main menu.
 */

package eCook;

import static org.junit.Assert.*;
import java.awt.AWTException;
import java.io.File;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import xmlparser.Recipe;
import xmlparser.XMLReader;

public class MainMenuTest{

	private Stage stage;
	private Rectangle2D screenBounds;
	private RecipeCollection recipeCollection;
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
		
	@Before
	public void setup() throws AWTException{
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
		new MainMenu(stage, recipeCollection);
		screenBounds = Screen.getPrimary().getBounds();
	}
		
	/**
	 * Tests that a full screen stage is created, that it is the right size, and it has no exit hint.
	 */
	@Test
	public void fullScreenStage() {
		stage.show();
		//asserts that the size of the main menu scene is the same as the size of the screen.
		assertEquals(screenBounds.getWidth(), stage.getScene().getWidth(), 0.1);
		assertEquals(screenBounds.getHeight() , stage.getScene().getHeight() , 0.1);
		
		//asserts that the size of the main menu stage is the same as the size of the screen.
		assertEquals(screenBounds.getWidth(), stage.getWidth(), 0.1);
		assertEquals(screenBounds.getHeight() , stage.getHeight() , 0.1);
		
		//asserts that the stage is set to fullscreen
		assertTrue(stage.isFullScreen());
		
		//asserts that the stage's fullscreen exit hint is empty
		assertEquals("",stage.getFullScreenExitHint());
		
		//asserts that the stage's fullscreen exit key combination is nothing
		assertEquals(KeyCombination.NO_MATCH,stage.getFullScreenExitKeyCombination());
	}
	
	/**
	 * Tests that the MainMenu contains a stage, scene, and group with visible content.
	 */
	@Test
	public void stageContent() {
		//Checks that the Main Menu stage has a scene added to it
		assertNotNull(stage.getScene());
		
		//Assert that Scene contains a group
		assertNotNull(stage.getScene().getRoot());
		
		//Check that the scene group is not null
		assertNotNull(stage.getScene().getRoot().getChildrenUnmodifiable());
		
		//Checks that the scene group contains an VBox which corresponds to the MainMenuContent Class
		assertTrue(stage.getScene().getRoot().getChildrenUnmodifiable().get(0) instanceof VBox);
	}
}