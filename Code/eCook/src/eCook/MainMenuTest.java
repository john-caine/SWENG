/*

 Programmers : Roger, Prakruti & Zayyad
 Date created: 27/2/2014
 Description: JUnit test class for eCook's main menu.
 Version : 1.0 27/02/2014
 		   1.1 04/03/2014 - Junit test for creation of main menu and slide's buttons.
 		   1.2 27/03/2014 - Steve Thorpe: Tests rewritten for refactored main menu class. Tests that the 
 		   					stage is correctly assigned the main menu scene and that content 
 		   					is contained within the main menu group.

 Program Description : This program is designed to create a slide from the main menu. 
					   The program is capable of exiting the slide and return to the main menu.
 					   
 Program Setup : This program is designed to create a fullscreen slide whenever the "Create Slide" button
 				 is being pressed. In the slide, there's a "Exit Slide" button to close the slide and return
 			     to main menu. This could also be done by pressing the "ESC" key.
 				
 Test Results :When "Create Slide" button is pressed, a separate fullscreen window (slide) pops up.
 			   When "Exit Slide" button is pressed, the slide window closes and return to main menu.
 			   When "ESC" key is pressed, the slide window closes and return to main menu.
 */

package eCook;

import static org.junit.Assert.*;

import java.awt.AWTException;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;




public class MainMenuTest{
	//Declare Variables
	private Stage stage;
	private Rectangle2D screenBounds;
	private VBox bigBox;
	// Run tests on JavaFX thread ref. Andy Till
		// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
		@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
		
	
	
	@Before
	public void setup() throws AWTException{
		stage = new Stage();
		// no need to provide a recipeCollection (use null)
		new MainMenu(stage, null);
		Screen screen = Screen.getPrimary();
		screenBounds = screen.getBounds();
	}
		//Tests that a full screen stage is created.
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
	
	@Test
	public void StageContent() {
		//Checks that the Main Menu stage has a scene added to it
		assertNotNull(stage.getScene());
		
		//Assert that Scene contains a group
		assertNotNull(stage.getScene().getRoot());
		
		//Check that the scene group is not null
		assertNotNull(stage.getScene().getRoot().getChildrenUnmodifiable());
		
		//Checks that the scene group contains an VBox which corresponds to the MainMenuContent Class
		assertTrue(stage.getScene().getRoot().getChildrenUnmodifiable().get(0) instanceof VBox);
		
		//Checks that the VBox contains HBox (topBox)
		bigBox = (VBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
		assertTrue(bigBox.getChildren().get(0) instanceof HBox);
			
		/* Test if topBox contains topLeftBox & topRightBox */
		HBox topBox = (HBox) bigBox.getChildren().get(0);
		assertTrue(topBox.getChildren().get(0) instanceof HBox);
		assertTrue(topBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topLeftBox contains 1 Imageview */
		HBox topLeftBox = (HBox) topBox.getChildren().get(0);
		assertTrue(topLeftBox.getChildren().get(0) instanceof ImageView);
		
		/* Test for Home Image */
		ImageView homeHolder = (ImageView) topLeftBox.getChildren().get(0);
		assertTrue(homeHolder.getImage() instanceof Image);
		homeHolder.getOnMouseClicked();
		assertTrue(stage.getScene().getRoot().getChildrenUnmodifiable().get(0) instanceof VBox);
	
		/*Test if topRightBox contains 2 ImageView */
		HBox topRightBox = (HBox) topBox.getChildren().get(1);
		assertTrue(topRightBox.getChildren().get(0) instanceof ImageView);
		assertTrue(topRightBox.getChildren().get(1) instanceof ImageView);
		
		/* Test for Minimise Image */
		ImageView minimiseBtnHolder = (ImageView) topRightBox.getChildren().get(0);
		assertTrue(minimiseBtnHolder.getImage() instanceof Image);
		minimiseBtnHolder.getOnMouseClicked();
		assertFalse(stage.isMaximized());
		
		/* Test for Close Image */
		ImageView closeBtnHolder = (ImageView) topRightBox.getChildren().get(1);
		assertTrue(closeBtnHolder.getImage() instanceof Image);
		
		closeBtnHolder.getOnMouseClicked();
	    assertFalse(stage.isShowing());
	}

	
}