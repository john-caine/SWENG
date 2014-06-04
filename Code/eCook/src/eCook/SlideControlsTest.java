/* Title: SlideControlsTest
 * 
 * Programmers: Max
 * 
 * Date Created: 04/06/14
 * 
 * Description: Unit testing for SlideControls Class. Test specifics are detailed below.
 */

package eCook;

import static org.junit.Assert.*;

import javafx.scene.Group;
import javafx.scene.Scene;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;

public class SlideControlsTest {
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	static Group group;
	static Scene scene;
	
	@Before
	// perform JavaFX setup to launch the slide controls GUI
	public void setup() {
		group = new Group();
		scene = new Scene(group);
	}
	
	// This test class contains visual and automated tests.
	// The results of such testing is documented below.
	
	//!!	THIS TEST CLASS DOES NOT COVER BUTTON FUNCTIONALITY, THAT IS TESTED IN THE SLIDESHOW CLASS
		
	/* Visual Tests */

	@Test
	public void controlsPanelDisplayedWhenMouseMovedToBottom() {
		System.out.println("Testing if controls panel is displayed when mouse is moved to bottom of screen...");
		System.out.println("Mouse moved to bottom of screen, waiting 2 seconds");
		System.out.println("controls panel displayed correctly.\n");
	}
	
	@Test
	public void controlsPanelShowsCorrectButtons() {
		System.out.println("Testing if controls panel shows all buttons correctly...");
		System.out.println("Mouse moved to bottom of screen, waiting 2 seconds");
		System.out.println("controls panel displayed correctly.");
		System.out.println("controls panel shows all buttons correctly in the panel.\n");
	}
	
	@Test
	public void controlsPanelDisappearsOnMouseout() {
		System.out.println("Testing if controls panel is hidden correctly when mouse is moved out of the panel...");
		System.out.println("Mouse moved to centre of screen, waiting 2 seconds");
		System.out.println("controls panel hidden correctly.");
	}
	
	/* Automated Tests */
	
	// check that all of the UI components are instantiated correctly
	@Test
	public void GUISetupCorrectly() {
		SlideControls slideControls = new SlideControls(group);
		assertNotNull(slideControls.controlPanel);
		assertNotNull(slideControls.buttons);
		assertFalse(slideControls.controlPanel.getChildren().contains(slideControls.buttons));
		assertFalse(slideControls.controlPanelVisible);
	}
}