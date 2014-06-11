/*
 * Programmer: Steve Thorpe, Jonathan Caine
 * Date Created: 14/03/2014
 * Description: SlideShow test class. Tests the control panel for the slideshow and the layering of content on each slide
 * Tests of content creation are performed in the handler test classes.
 * 
 */


package eCook;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Rule;
import org.junit.Before;
import org.junit.Test;

import timer.TimerData;
import xmlvalidation.XMLValidator;

public class SlideShowTest {

	private SlideShow slideShow;
	private Stage stage;

	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before
	public void setUp() throws Exception {

		stage = new Stage();
		// don't need to send a recipe collection for this test (use null)
		slideShow = new SlideShow(stage, "PWSExamplePlaylist_4.xml", null);

	}

	/*
	 * Test that a slide Show has been created
	 */
	@Test
	public void testCreateSlideShow(){

		//Test a new scene is created and added to the stage
		assertTrue(stage.getScene() instanceof Scene);

		//Test that a group has been added to the root of the scene
		assertTrue(stage.getScene().getRoot() instanceof Group);
		
		// Check the first and last slideIDs are set correctly
		assertTrue(slideShow.firstSlideID == 0);
		assertTrue(slideShow.lastSlideID == 4);
		
		//check the validator has been called
		assertTrue(slideShow.validator != null);
		
		// Check the background colour is set correctly
		assertEquals("0x00ff00ff", slideShow.slideScene.getFill().toString());
	}

	/*
	 * Test that a slide has been created, that the control buttons appear correctly on the slide and that content is being added to the correct layer.
	 */
	@Test
	public void testCreateSlide() throws RuntimeException  {
		// call new slide with index 0
		slideShow.newSlide(0, false, null);
		
		// Assert the correct slideID has been set
		assertTrue(slideShow.currentSlideID == 0);
		assertTrue(slideShow.nextSlideID == 1);
		assertTrue(slideShow.prevSlideID == -1);
		
		// Check that layering has been handled
		assertTrue(slideShow.maxLayer != null);
		
		// Check that the slide has a duration
		assertTrue(slideShow.duration != null);
		
		// Check the notesGUI is called
		assertTrue(slideShow.notesGUI != null);
		
		// check a control bar is on the screen
		assertTrue(slideShow.slideControls != null);
		
		// Check the timer array is populated
		assertTrue(slideShow.timerList != null);
	}

	@Test
	public void testBranching() {
		// call new slide with index 0
		slideShow.newSlide(0, false, null);
		
		// mimic a branch slide being called
		slideShow.newSlide(5, true, null);
		
		// Assert the correct slideIDs have been set
		assertTrue(slideShow.currentSlideID == 5);
		assertTrue(slideShow.nextSlideID == 0);
		assertTrue(slideShow.prevSlideID == 0);
	}
	
	@Test
	public void testLayering(){
		slideShow.newSlide(3, false,null);
		assertEquals(3, slideShow.maxLayer, 0.1);
		assertEquals(4, slideShow.layers.size(),0.1);
		assertEquals(1, slideShow.layers.get(0).getChildren().size());
		assertEquals(0, slideShow.layers.get(1).getChildren().size());
		assertEquals(0, slideShow.layers.get(2).getChildren().size());
		assertEquals(1, slideShow.layers.get(3).getChildren().size());	
	}


}
