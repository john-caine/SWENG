/*
 * Programmer: Steve Thorpe, Jonathan Caine
 * Date Created: 04/06/2014
 * Description: Slide Media Player Test class
 */
package media;

import static org.junit.Assert.*;
import javafx.scene.media.MediaPlayer;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;

public class SlideMediaPlayerTest {

	private SlideMediaPlayerMock slideMediaPlayer;
	private SlideShow parent;
	@Before
	public void SetUp(){
		
		slideMediaPlayer =  new SlideMediaPlayerMock(parent, "http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4", 60, 50, 10, 40, false);
	}
	/*
	 * Tests that the slide media player has been created
	 */	
	@Test
	public void slideMediaContainsMediaPlayer() {
		assertTrue(slideMediaPlayer.getMediaPlayer() instanceof MediaPlayer);
	}
	
	/*
	 * Tests that the media player volume has been correctly set
	 */
	@Test
	public void setMediaVolume(){
		slideMediaPlayer.setVolume(0.5);
		assertEquals(0.5, slideMediaPlayer.getMediaPlayer().getVolume(),0.1);
	}
	
	/*
	 * Test that the media player has the correct content and loop value
	 */
	@Test
	public void checkMediaContent(){
		assertEquals("http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4", slideMediaPlayer.getFilePath());
		assertEquals(1, slideMediaPlayer.getMediaPlayer().getCycleCount(), 0.1);
		
	}
	
	/*
	 * Check that the media player is not in state playing when a start time duration has been set
	 */
	@Test
	public void checkStartTimeline(){
		slideMediaPlayer.setTimingValues();
		assertNotEquals(MediaPlayer.Status.PLAYING, slideMediaPlayer.getMediaPlayer().getStatus());
	}
	
	/*
	 * Test that the media player duration can be returned in millseconds
	 */
	@Test 
	public void durationReturnedInMillis(){
		assertEquals(40*1000, slideMediaPlayer.getDuration(),0.1);
	}
	
	/*
	 * Test that the media player is disposed when the tear down method is called
	 */
	@Test
	public void mediaPlayerDisposed(){
		slideMediaPlayer.tearDown();
		assertEquals(MediaPlayer.Status.DISPOSED, slideMediaPlayer.getMediaPlayer().getStatus());
	}

}
