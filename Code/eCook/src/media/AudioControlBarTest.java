/*
 * Programmer: Max
 * Date Created: 05/06/14
 * Description: Unit tests for audio control bar.
 */
package media;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import media.AudioHandler;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;

public class AudioControlBarTest {
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	ArrayList<AudioHandler> audioHandlers;
	AudioControlBar audioBar;
	SlideShow slideShow;
	Group root;
	Scene scene;
	
	@Before
	public void setup() {
		// prepare objects required for instantiation of audio bar
		root = new Group();
		scene = new Scene(root);
		audioHandlers = new ArrayList<AudioHandler>();
	}
	
	/* Automated tests /*
	
	/*
	 * Test to see that all GUI objects are instantiated correctly
	 */
	@Test
	public void setupControlBarSetsUpGUICorrectly() {
		// add one audio handler to the list
		audioHandlers.add(new AudioHandler(slideShow, "http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", 0, 10, false));
		audioBar = new AudioControlBar(audioHandlers, root);
		
		// confrim that no GUI is displayed
		assertNotNull(audioBar.getControlBar());
		assertNotNull(audioBar.buttons);
		assertEquals(4, audioBar.buttons.size());
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());
		assertNotNull(audioBar.trackBar);
		assertEquals(0, (int) audioBar.trackBar.getMin());
		assertEquals(10000, (int) audioBar.trackBar.getMax());
		assertNotNull(audioBar.volBar);
		assertEquals(0, (int) audioBar.volBar.getMin());
		assertEquals(1, (int) audioBar.volBar.getMax());
		assertEquals(1, (int) audioBar.volBar.getValue());
		assertNotNull(audioBar.fileLbl);
		assertEquals("sample1", audioBar.fileLbl.getText());
		assertEquals("00:00/00:00", audioBar.timeLbl.getText());
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.buttons.get(0)));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.buttons.get(1)));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.buttons.get(2)));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.buttons.get(3)));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.trackBar));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.timeLbl));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.fileLbl));
		assertTrue(audioBar.getControlBar().getChildren().contains(audioBar.volBar));
	}
	
	/*
	 * check that the setup buttons method works correctly
	 */
	@Test
	public void buttonsSetupCorrectly() {
		// add one audio handler to the list
		audioHandlers.add(new AudioHandler(slideShow, "http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", 0, 10, false));
		audioBar = new AudioControlBar(audioHandlers, root);
		
		// check the play/pause transition
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());
		audioBar.buttons.get(0).fire();
		assertEquals("audioBarPause", audioBar.buttons.get(0).getId());
		audioBar.buttons.get(0).fire();
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());
		
		// check that the stop button resets the play/pause to play
		audioBar.buttons.get(0).fire();
		assertEquals("audioBarPause", audioBar.buttons.get(0).getId());
		audioBar.buttons.get(1).fire();
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());
		
		// test that the current handler index changes with the next/prev buttons
		assertEquals(0, audioBar.currentHandlerIndex);
		assertTrue(audioBar.buttons.get(2).isDisabled());
		assertTrue(audioBar.buttons.get(3).isDisabled());
		
		// but we'll need to add another audioHandler object first!
		audioHandlers.add(new AudioHandler(slideShow, "http://ystv.co.uk/~john.caine/swengrepo/sample2.mp3", 0, 10, false));
		audioBar = new AudioControlBar(audioHandlers, root);
		assertEquals(2, audioBar.audioHandlerObjects.size());
		
		// try the next button
		assertEquals(0, audioBar.currentHandlerIndex);
		audioBar.buttons.get(3).fire();
		assertEquals(1, audioBar.currentHandlerIndex);
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());

		// try the prev button
		assertEquals(1, audioBar.currentHandlerIndex);
		audioBar.buttons.get(2).fire();
		assertEquals(0, audioBar.currentHandlerIndex);
		assertEquals("audioBarPlay", audioBar.buttons.get(0).getId());
	}
	
	/*
	 * check that the sliders are setup and behave correctly
	 */
	@Test
	public void slidersSetupCorrectly() {
		// add one audio handler to the list
		audioHandlers.add(new AudioHandler(slideShow, "http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", 0, 10, false));
		audioBar = new AudioControlBar(audioHandlers, root);
		
		// test the volume bar
		assertEquals(1.0, audioBar.currentHandler.getMediaPlayer().getVolume(), 0.001);
		audioBar.volBar.setValue(0.4);
		assertEquals(0.4, audioBar.currentHandler.getMediaPlayer().getVolume(), 0.001);
		audioBar.volBar.setValue(0.8);
		assertEquals(0.8, audioBar.currentHandler.getMediaPlayer().getVolume(), 0.001);
		
		// test the tracking bar
		assertEquals(new Duration(0), audioBar.currentHandler.getMediaPlayer().getCurrentTime());
	}
	
	/* Visual tests */
	
	/*
	 * General audio bar test
	 */
	@Test
	public void audioBarPerformsCorrectly() {
		System.out.println("Launching slideshow with example audio slide (contains two audio objects)...");
		System.out.println("Hovering over bottom of screen...");
		System.out.println("Audio Control Bar appears correctly! Previous button is disabled as expected.");
		System.out.println("Track bar is at 0 and vol bar is at 1.");
		System.out.println("Correct filename 'sample1' is shown next to the track bar.");
		System.out.println("Pressing play...");
		System.out.println("Audio file starts playing and trackbar moves along. Play button becomes pause.");
		System.out.println("Pressing pause...");
		System.out.println("Audio stops and pause button becomes play again.");
		System.out.println("Pressing play again...");
		System.out.println("Audio resumes position. Now adjusting volume slider...");
		System.out.println("Volume correctly adjusts with slider (left-most is 0).");
		System.out.println("Audio file finished, trackbar returns to 0 and pause button becomes play again.");
		System.out.println("Pressing next...");
		System.out.println("Next filename shows 'sample2'. Next button disabled, prev button enabled. Pressing play...");
		System.out.println("New audio file plays as expected. Trackbar updates correctly.");
		System.out.println("Dragging the trackbar...");
		System.out.println("Audio seeks as expected.");
		System.out.println("Clicking in a certain part of the trackbar...");
		System.out.println("Audio continues playing from that position.");
		System.out.println("Pressing previous...");
		System.out.println("'sample1' displayed, previous button diabled, next button enabled.");
		System.out.println("Moving mouse away from panel...");
		System.out.println("Panel hides as expected.\n\n");
		System.out.println("Test concluded: PASS");
	}
}
