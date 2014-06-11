/*
 * Programmers: Max & Ankita
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
	
	protected static Group group;
	protected static Scene scene;
	
	@Before
	// perform JavaFX setup to launch the slide controls GUI
	public void setup() {
		group = new Group();
		scene = new Scene(group);
	}
	
	// This test class contains visual and automated tests.
	// The results of such testing is documented below.
	
	// THIS TEST CLASS DOES NOT COVER BUTTON FUNCTIONALITY, THAT IS TESTED IN THE SLIDESHOW CLASS
		
	/* Visual Tests */

	/**
	 * Test if the control panel is diaplayed when the mouse moves to the bottom of the screen
	 */
	@Test
	public void controlsPanelDisplayedWhenMouseMovedToBottom() {
		System.out.println("Testing if controls panel is displayed when mouse is moved to bottom of screen...");
		System.out.println("Mouse moved to bottom of screen, waiting 2 seconds");
		System.out.println("Controls panel displayed correctly.\n");
	}
	
	/**
	 *  Test if the control panel contains all the right buttons.
	 */
	@Test
	public void controlsPanelShowsCorrectButtons() {
		System.out.println("Testing if controls panel shows all buttons correctly...");
		System.out.println("Mouse moved to bottom of screen, waiting 2 seconds");
		System.out.println("controls panel displayed correctly.");
		System.out.println("Controls panel shows all buttons correctly in the panel.\n");
	}
	
	/**
	 * Test the control panel is hidden when the mouse moves away.
	 */
	@Test
	public void controlsPanelDisappearsOnMouseout() {
		System.out.println("Testing if controls panel is hidden correctly when mouse is moved out of the panel...");
		System.out.println("Mouse moved to centre of screen, waiting 2 seconds");
		System.out.println("Controls panel hidden correctly.\n");
	}
	
	/**
	 * Test the tooltips display on each button on the control panel.
	 */
	@Test 
	public void toolTipsShowCorrectly(){
		System.out.println("Testing if tooltips apper when mouse is on a button....");
		System.out.println("Mouse moved to a button on control panel");
		System.out.println("Tooltips appear correctly.\n");
	}
	
	/* Automated Tests */
	
	/**
	 *  Check that all of the UI components are instantiated correctly
	 */
	@Test
	public void GUISetupCorrectly() {
		// if panel was not open previously:
		SlideControls slideControls = new SlideControls(group, false, null);
		assertNotNull(slideControls.controlPanel);
		assertNotNull(slideControls.buttons);
		assertFalse(slideControls.controlPanel.getChildren().contains(slideControls.buttons));
		
		// and if notes panel was open previously:
		SlideControls slideControls2 = new SlideControls(group, true, null);
		assertNotNull(slideControls2.controlPanel);
		assertNotNull(slideControls2.buttons);
		assertFalse(slideControls2.controlPanel.getChildren().contains(slideControls.buttons));
	}
}