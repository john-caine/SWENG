/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * Junit test for the media control panel
 */
package videohandler;

import static org.junit.Assert.*;
import java.awt.AWTException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class VideoMediaControlTest {


	private VideoPlayerHandler videoPlayerHandler;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setup() throws AWTException {
		/* Call the videoHandler class to create a videoplayer based on certain attributes */
		videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 400, 400, false, 2, 5);    
	}
	
	@Test
	public void mediaControlTests() throws InterruptedException{
		/* mediaView contains a MediaView */
		assertTrue(videoPlayerHandler.mediaControl.mediaView instanceof MediaView);
		
		/* mediaBar contains the Play/Pause Button */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(0) instanceof Button);
		
		/* mediaBar contains the Stop Button */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(1) instanceof Button);
		
		/* mediaBar contains the Fullscreen Button */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(2) instanceof Button);
		
		/* mediaBar contains the Time Label */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(3) instanceof Label);
		
		/* mediaBar contains the Time Slider */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(4) instanceof Slider);
		
		/* mediaBar contains the Play Time Label */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(5) instanceof Label);
		
		/* mediaBar contains the Volume Label */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(6) instanceof Label);
		
		/* mediaBar contains the Volume Slider */
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.getChildren().get(7) instanceof Slider);
		
		/* MediaControl Class Contains the mediaView and mediaBar */
		assertTrue(videoPlayerHandler.mediaControl.overallBox.getChildren().get(0) instanceof MediaView);
		assertTrue(videoPlayerHandler.mediaControl.overallBox.getChildren().get(1) instanceof HBox);	
		
		/* Detect if VideoPlayer is set to be on repeat. 
		 * Return -1 for Loop = true & 1 for Loop = false */
		assertEquals(1, videoPlayerHandler.mediaControl.mp.getCycleCount(), 0.01);	
	}
}
