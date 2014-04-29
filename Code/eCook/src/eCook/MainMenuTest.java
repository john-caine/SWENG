/*

 Programmers : Roger & Prakruti
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
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;




public class MainMenuTest{
	//Declare Variables
	
	private Stage stage;
	private Rectangle2D screenBounds;
	private HBox box;
	private Button createSlideShowButton;
	// Run tests on JavaFX thread ref. Andy Till
		// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
		@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
		
	
	
	@Before
	public void setup(){
		stage = new Stage();
		new MainMenu(stage);
		Screen screen = Screen.getPrimary();
		screenBounds = screen.getVisualBounds();
		stage.show();
	}
	
	//Tests that a full screen stage is created.
	@Test
	public void fullScreenStage() {
		//Gets the size of the screen
	
		
		//asserts that the size of the main menu stage is the same as the size of the screen.
		// The plus 40 accounts is to account for the task bar, getHeight for screen bounds doesn't seem to include it
		// 
		assertEquals(screenBounds.getWidth(), stage.getWidth(), 0.1);
		assertEquals(screenBounds.getHeight() , stage.getHeight() , 0.1);
	}
	
	@Test
	public void StageContent() {
		
		//Checks that the Main Menu stage has a scene added to it
		assertNotNull(stage.getScene());
		
		//Assert that Scene contains a group
		assertNotNull(stage.getScene().getRoot());
		
		
		//Check that the scene group is not null
		assertNotNull(stage.getScene().getRoot().getChildrenUnmodifiable());
		//Checks that the scene group contains an HBox
		assertTrue(stage.getScene().getRoot().getChildrenUnmodifiable().get(0) instanceof HBox);
		box = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
		
		//Check that the HBox contains a Button
		assertTrue(box.getChildren().get(0) instanceof Button);
		
		//Test that the button has the correct text "Create SlideShow"
		createSlideShowButton = (Button) box.getChildren().get(0);
		assertEquals("Create SlideShow", createSlideShowButton.getText());
	}

	
}