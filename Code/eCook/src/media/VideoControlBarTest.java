package media;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;
import eCook.SlideShow;

public class VideoControlBarTest  {
	
	private VideoControlBar videoControlBar;
	private VideoHandler videoHandler;
	private SlideShow parent;
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
		
	@Before
	public void SetUp(){
		videoHandler = new VideoHandler(parent, "http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4",  60, 50, 100, 100, false, 10, 40);
		videoControlBar = new VideoControlBar(videoHandler);
		 
	}
	
    /*
     * Test that controlBar has been setup 
     */
    
	@Test
	public void setupControlBar()  {
	
		assertNotNull(videoControlBar.getVideoControlBar());
		assertNotNull(videoControlBar.playPauseButton);
		assertNotNull(videoControlBar.stopButton);
		assertNotNull(videoControlBar.timeLbl);
		assertNotNull(videoControlBar.trackBar);
		assertNotNull(videoControlBar.volBar);
	}
	
	/*
	 * Test slider bars have the correct numbers
	 */
	@Test
	public void slidersAreCorrect()  {
		assertEquals(1, videoControlBar.volBar.getValue(), 0.1);
		assertEquals(1, videoControlBar.volBar.getMax(), 0.1);
		assertEquals(0, videoControlBar.volBar.getMin(), 0.1);
		assertEquals(40000, videoControlBar.trackBar.getMax(),0.1);
		assertEquals(0, videoControlBar.trackBar.getMin(), 0.1);
		assertEquals(0, videoControlBar.trackBar.getValue(), 0.1);
	}
	
	/*
	 * Test button images are correct
	 */
	@Test
	public void buttonsAreCorrect() {
		
		assertEquals("Play Image", videoControlBar.playPauseButton.getGraphic().getId());
		assertEquals("Stop Image", videoControlBar.stopButton.getGraphic().getId());
		
	}

}
