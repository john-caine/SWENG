package audiohandler;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AudioHandlerTest {
	
	AudioHandler audio;
	
	@Before
	public void setup(){
		//play audio for 2 seconds after 1 second has passed
		audio = new AudioHandler("file:assets/positive_force.mp3", 1, 2, 0.3);
	}
	
	@Test
	public void testDelay() throws InterruptedException{
		audio.begin();
		//wait 0.8 seconds, audio should not have started yet
		Thread.sleep(800);
		assertFalse(audio.isPlaying());
		audio.stop();
	}
	
	@Test
	public void testPlaying() throws InterruptedException{
		audio.begin();
		//wait 1.2 seconds, audio should have started
		Thread.sleep(1200);
		assertTrue(audio.isPlaying());
	}
	
	@Test
	public void testDuration() throws InterruptedException{
		audio.begin();
		//wait 3.2 seconds, audio should have finished
		Thread.sleep(3200);
		assertFalse(audio.isPlaying());
		audio.stop();
	}

}
