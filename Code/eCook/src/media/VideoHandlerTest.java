/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: VideoHandler test class
 */
package media;

import static org.junit.Assert.*;
import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;
import eCook.SlideShow;

public class VideoHandlerTest {
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	private VideoHandler videoHandler;
	private SlideShow parent;
	private ActionEvent e;

	@Before
	public void SetUp(){
		videoHandler = new VideoHandler(parent, "http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4",  60, 50, 100, 100, false, 10, 40);
	}

	/*
	 * Test that a MediaView object has been created
	 */
	@Test
	public void containsMediaView() {
		assertTrue(videoHandler.getMediaView() instanceof MediaView);
	}
	
	/*
	 * Test that the Media View Object has the correct size
	 */
	@Test 
	public void mediaViewSize(){
		assertEquals(100, videoHandler.getMediaView().getFitHeight(), 0.1);
		assertEquals(100, videoHandler.getMediaView().getFitWidth(), 0.1);
	}
	
	/*
	 * Test when the full screen method is called that a new stage is created and a MediaView Object which is the size of the screen.
	 */
	@Test
	public void fullScreen(){
		assertNull(videoHandler.stage);
		videoHandler.setFullScreen(e);
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		assertNotNull(videoHandler.stage);
		assertEquals(bounds.getHeight(), videoHandler.fullMediaView.getFitHeight(), 0.1);
		assertEquals(bounds.getWidth(), videoHandler.fullMediaView.getFitWidth(), 0.1);
		
	}

}
