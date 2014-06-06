/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * Junit test for the VideoHandler 
 */

package videohandler;


import static org.junit.Assert.*;

import java.awt.AWTException;
import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Video;
import xmlparser.XMLReader;
import eCook.SlideShow;

public class VideoPlayerMainTest {
	
	private VideoHandler videoPlayerHandler;
	private XMLReader reader;
	private SlideShow parent;
	private List<Video> videoList;
	private double width, height;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setup() throws AWTException {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_4.xml");
		videoList = reader.getRecipe().getSlide(4).getContent().getVideos();
		/* Call the videoHandler class to create a videoplayer based on certain attributes */
		videoPlayerHandler = new VideoHandler(parent,videoList.get(0).getUrlName(), videoList.get(0).getXStart(), 
				videoList.get(0).getYStart(), videoList.get(0).getWidth(),
				videoList.get(0).getHeight(), videoList.get(0).getLoop(),
				videoList.get(0).getStartTime(), videoList.get(0).getDuration());
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		width = (int) (bounds.getWidth()/2);
		height = (int) (bounds.getHeight()/3);
	}
	
	@Test
	public void videoPlayerHandlerTest() throws InterruptedException{
		
		/* VideoPlayer's X and Y Location (setMediaPlayerLocation Method)*/
		assertEquals(10, videoPlayerHandler.mediaControl.overallBox.getLayoutX(), 0.01);
		assertEquals(10, videoPlayerHandler.mediaControl.overallBox.getLayoutY(), 0.01);
		
		/* set Media to be the provided Path */
		assertEquals("http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4",
					 videoPlayerHandler.media.getSource());
		
		/* VideoPlayer's Width and Height */
		assertEquals(width, videoPlayerHandler.mediaControl.overallBox.getMaxWidth(), 0.01);
		assertEquals(height, videoPlayerHandler.mediaControl.overallBox.getMaxHeight(), 0.01);
		
		/* VideoPlayer's MediaView and Control Panel are visible */
		assertTrue(videoPlayerHandler.mediaControl.mediaView.isVisible());
		assertTrue(videoPlayerHandler.mediaControl.mediaBar.isVisible());
	}
	
}