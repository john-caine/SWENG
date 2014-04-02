/*
 * Programmer: Steve
 * Date Created: 27/03/2014
 * Description: JUnit test case for SlideShow class
 * Version 1.0
 * 
 * 
 */

package eCook;

import static org.junit.Assert.*;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.Rule;
import org.junit.Before;

import org.junit.Test;

public class SlideShowTest {
	
	private SlideShow slideShow;
	private Stage stage;
	
	// Run tests on JavaFX thread ref. Andy Till
			// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
			@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
			private ObservableList<Node> childList;
			private HBox buttonBox;
			private Button nextSlide;
			private Button previousSlide;
			private Button exitSlide;

	@Before
	public void setUp() throws Exception {
		
		stage = new Stage();
		slideShow = new SlideShow(stage);
		
	}
	
	@Test
	public void createSlideShow(){
		
		//Test a new scene is created and added to the stage
		assertTrue(stage.getScene() instanceof Scene);
		
		//Test that a group has been added to the root of the scene
		assertTrue(stage.getScene().getRoot() instanceof Group);
		
		
	
	}

	@Test
	public void createSlide() {
		
		
		slideShow.newSlide(0, false);
		
		childList = stage.getScene().getRoot().getChildrenUnmodifiable();
		
		
		//Tests that the correct buttons are created and add to the scene
		
		buttonBox = (HBox) childList.get(4);
	
		Screen screen = Screen.getPrimary();
		
		Rectangle2D screenBounds = screen.getVisualBounds();
		
		System.out.println(screenBounds.getWidth());
		System.out.println(screenBounds.getHeight());
		//Tests that the button HBox is in the centre at the bottom of the screen.
	
		
		previousSlide = (Button) buttonBox.getChildren().get(0);
		exitSlide = (Button) buttonBox.getChildren().get(1);
		nextSlide = (Button) buttonBox.getChildren().get(2);
		
		assertEquals((screenBounds.getWidth()- exitSlide.getPrefWidth())/2, buttonBox.getLayoutX(), 0.1);
		assertEquals(screenBounds.getHeight(), buttonBox.getLayoutY(), 0.1);
		
		assertEquals ("Previous Slide", previousSlide.getText());
		assertEquals("Exit SlideShow", exitSlide.getText());
		assertEquals("Next Slide", nextSlide.getText());
		
		
		
		//assertTrue (stage.getScene().getRoot().getChildrenUnmodifiable())
		
	}
		
		

}
