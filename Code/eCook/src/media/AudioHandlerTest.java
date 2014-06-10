/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan, Paul Mathema 
 * Date Created: 04/06/2014
 * Description: Audio Handler Test Class
 */
package media;

import static org.junit.Assert.*;
import javafx.scene.media.MediaPlayer;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;

public class AudioHandlerTest {
	
	private AudioHandler audioHandler;
	private SlideShow parent;
	

	@Before
	public void SetUp(){
		audioHandler = new AudioHandler(parent, "http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", null, null, false );
	}
	/*
	 * Test that the media player has been created
	 */
	@Test
	public void getMediaPlayerTest(){
		assertTrue(audioHandler.getMediaPlayer() instanceof MediaPlayer);
	}

	/*
	 * Test that the correct filepath and loop value has been set 
	 */
	@Test
	public void test() throws IllegalStateException {
		
		assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", audioHandler.getMediaPlayer().getMedia().getSource());
		assertEquals(1, audioHandler.getMediaPlayer().getCycleCount(), 0.1);
	}

}
