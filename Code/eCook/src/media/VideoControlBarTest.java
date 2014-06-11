package media;

import static org.junit.Assert.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eCook.SlideShow;

public class VideoControlBarTest {
	
	private VideoControlBar videoControlBar;
	private VideoHandler videoHandler;
	private SlideShow parent;
	
	@Before
	public void SetUp(){
		videoHandler = new VideoHandler(parent, "http://ystv.co.uk/~john.caine/swengrepo" +
				"/14_Fusion_Promo_spr08.mp4",  60, 50, 100, 100, false, 10, 40);
		videoControlBar = new VideoControlBar(videoHandler);
		 Button playPauseButton = new Button("");
	}
	
    /*Test that controlBar has been setup*/
    
	@Test
	public void setupControlBar() {
		@SuppressWarnings("unused")
		HBox controlBar = new HBox();
		assertNotNull(videoControlBar.playPauseButton);
		
	}

}
