package media;

import static org.junit.Assert.*;
import javafx.scene.media.MediaView;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;

public class VideoHandlerTest {
	
	private VideoHandler videoHandler;
	private SlideShow parent;

	@Before
	public void SetUp(){
		videoHandler = new VideoHandler(parent, "http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4",  60, 50, 100, 100, false, 10, 40);
	}

	@Test
	public void containsMediaView() {
		assertTrue(videoHandler.getMediaView() instanceof MediaView);
	}

}
