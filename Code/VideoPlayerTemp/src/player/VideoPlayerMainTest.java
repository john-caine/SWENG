package player;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.concurrent.TimeUnit;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;
import javafx.util.Duration;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	Group root;
	Scene scene;
	Robot robot;

	VideoPlayerHandler videoPlayerHandler;
	String pathLocation = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setup() throws AWTException {
		robot =  new Robot();
		videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 400, 400, false, 2, 5);    
	}
	
	@Test
	public void VideoPlayerHandlerTests() throws InterruptedException{
		
		/* VideoPlayer's X and Y Location (setMediaPlayerLocation Method)*/
		assertEquals(300, videoPlayerHandler.mediaControl.overallBox.getLayoutX(), 0.01);
		assertEquals(300, videoPlayerHandler.mediaControl.overallBox.getLayoutY(), 0.01);
		
		/* set Media to be the provided Path */
		assertEquals("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv",
					 videoPlayerHandler.media.getSource());
		
		/* VideoPlayer's Width and Height */
		assertEquals(400, videoPlayerHandler.mediaControl.overallBox.getMaxWidth(), 0.01);
		assertEquals(400, videoPlayerHandler.mediaControl.overallBox.getMaxHeight(), 0.01);
		
		/* Detect if VideoPlayer is set to be on repeat. 
		 * Return -1 for Loop = true & 1 for Loop = false */
		assertEquals(1, videoPlayerHandler.mediaControl.mp.getCycleCount(), 0.01);
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
		
		/* VideoPlayer's MediaView and Control Panel are visible */
		assertTrue(videoPlayerHandler.mediaControl.mediaView.isVisible());
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.isVisible());
		
//		robot.delay(2000);
//		robot.mouseMove((int)(videoPlayerHandler.mediaControl.box.getLayoutX()+80),
//				(int)(videoPlayerHandler.mediaControl.box.getLayoutY()+ 400-20));
//		robot.mousePress(InputEvent.BUTTON1_MASK);
//        robot.mouseRelease(InputEvent.BUTTON1_MASK);
//        
//		assertTrue(videoPlayerHandler.mediaControl.stage1.isFullScreen());
		
		/* Detect if startTimerThread has finished counting for 2 seconds*/
		TimeUnit.SECONDS.sleep(2);
		assertTrue(videoPlayerHandler.mediaControl.startTimerThread.isDone());
		
		videoPlayerHandler.mediaControl.mp.stop();
		System.out.println(videoPlayerHandler.mediaControl.mp.getCurrentTime());
		
		/* Detect if the VideoPlayer is set to play for 5 seconds duration */
		assertEquals(Duration.millis(5000), videoPlayerHandler.mediaControl.mp.getStopTime());	
	}
}
