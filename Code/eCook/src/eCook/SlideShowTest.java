/*
 * Programmer: Steve Thorpe, Jonathan Caine
 * Date Created: 14/03/2014
 * Description: SlideShow test class. Tests the control panel for the slideshow and the layering of content on each slide
 * Tests of content creation are performed in the handler test classes.
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
import javafx.scene.layout.Pane;
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
		// don't need to send a recipe collection for this test (use null)
		slideShow = new SlideShow(stage, "Recipe_Example.xml", null);
		
	}
	
	/*
	 * Test that a slide Show has been created
	 */
	@Test
	public void createSlideShow(){
		
		//Test a new scene is created and added to the stage
		assertTrue(stage.getScene() instanceof Scene);
		
		//Test that a group has been added to the root of the scene
		assertTrue(stage.getScene().getRoot() instanceof Group);
		
		
	
	}

	/*
	 * Test that a slide has been created, that the control buttons appear correctly on the slide and that content is being added to the correct layer.
	 */
	@Test
	public void createSlide() throws RuntimeException  {
		
		Screen screen;
		Pane layer2;
		HBox layer2imageBox;
		
		slideShow.newSlide(2, false, null);
		
		childList = stage.getScene().getRoot().getChildrenUnmodifiable();
		
		
		//Tests that the correct buttons are created and add to the scene
		
		buttonBox = (HBox) childList.get(4);
	
		screen = Screen.getPrimary();
		
		Rectangle2D screenBounds = screen.getBounds();
		
		System.out.println(screenBounds.getWidth());
		System.out.println(screenBounds.getHeight());
		//Tests that the button HBox is in the centre at the bottom of the screen.
	
		
		//Tests the next slide exit slide and previous slide buttons are created and are placed in the correct place.
		previousSlide = (Button) buttonBox.getChildren().get(0);
		exitSlide = (Button) buttonBox.getChildren().get(1);
		nextSlide = (Button) buttonBox.getChildren().get(2);
		
		assertEquals((screenBounds.getWidth()- exitSlide.getPrefWidth())/2, buttonBox.getLayoutX(), 0.1);
		assertEquals(screenBounds.getHeight(), buttonBox.getLayoutY(), 0.1);
		
		assertEquals ("Previous Slide", previousSlide.getText());
		assertEquals("Exit SlideShow", exitSlide.getText());
		assertEquals("Next Slide", nextSlide.getText());
		
		
		
		assertTrue (childList.get(0) instanceof Pane);
		Pane layer0 = (Pane) childList.get(0);
		//Test layer 0 contains 2 objects
		assertEquals(2, layer0.getChildren().size());
		
		//Get layer 2 pane
		layer2 = (Pane) childList.get(2);
		
		//Get the image Hbox on layer 2
		layer2imageBox = (HBox)layer2.getChildren().get(0);
		
		//Test the XY position of the image on layer 2 matches the XML playlist 
		assertEquals(400, layer2imageBox.getLayoutX(), 0.1);
		assertEquals(400, layer2imageBox.getLayoutY(), 0.1);
		
		
		
		
	}
		
		

}
