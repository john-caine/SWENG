package audiohandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;


public class AudioHandlerTest {
	
	AudioHandler audioHandler;
	private SlideShow parent;
	
	@Before
	public void setup(){
		//play audio for 2 seconds after 1 second has passed
		audioHandler = new AudioHandler(parent, "file:/C:/Users/PM/Music/assets/assets/positive_force.mp3", 1, false, 3);
	}
	
	@Test
	public void AudioIsPlaying() throws InterruptedException{
		audioHandler.playAudio();
		//wait 0.8 seconds, audio should not have started yet
		Thread.sleep(800);
		assertTrue(audioHandler.audio.isPlaying());
	}
	
	@Test
	public void urlname() throws InterruptedException{
		audioHandler.audio.play();
		//wait 1.2 seconds, audio should have started
		Thread.sleep(1200);
		equals(audioHandler.audio.isPlaying());
	}
	
	@Test
	public void testDuration() throws InterruptedException{
		//wait 3.2 seconds, audio should have finished
		Thread.sleep(4000);
		assertFalse(audioHandler.audio.isPlaying());
		
	}

}
