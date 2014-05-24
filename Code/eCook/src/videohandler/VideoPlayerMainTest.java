/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * Junit test for the VideoHandler 
 */

package videohandler;


import static org.junit.Assert.*;

import java.awt.AWTException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	private VideoPlayerHandler videoPlayerHandler;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setup() throws AWTException {
		/* Call the videoHandler class to create a videoplayer based on certain attributes */
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
		
		/* VideoPlayer's MediaView and Control Panel are visible */
		assertTrue(videoPlayerHandler.mediaControl.mediaView.isVisible());
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.isVisible());
	}
	
}