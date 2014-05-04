
/*Programmer: P.Mathema, S.Beedell,
 * Date Created: 14/03/2014
 * Description: 

*/

package audiohandler;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;


public class AudioHandlerTest {
	MediaControl mediaControl;
	AudioHandler audioHandler;
	private SlideShow parent;
	
	@Before
	public void setup(){
		//play audio for 2 seconds after 1 second has passed
		//audioHandler = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
		mediaControl = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
	}


	  @AfterClass
	  public static void testCleanup() {
	    // Teardown for data used by the unit tests
	  }

	 
	@Test
	public void testStartTime() throws InterruptedException{
		AudioHandler tester = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
		//wait 0 seconds, audio should not have started yet
		//assertFalse(tester.mediaControl.mp.getOnPlaying());
		assertEquals(tester.mediaControl.mp.isAutoPlay());
		
	}
	
	@Test
	public void testAudioIsPlaying() throws InterruptedException{
		AudioHandler tester = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
		//wait 1.2 seconds, audio should have started
		assertFalse(tester.mediaControl.mp.isAutoPlay());
	}
	
	@Test
	public void testDuration() throws InterruptedException{
		AudioHandler tester = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
		//wait 3.2 seconds, audio should have finished. This also tests if loop is off.
		assertFalse(tester.mediaControl.mp.isAutoPlay());
		
	}
	
	@Test
	public void testUrlName() throws InterruptedException{
		AudioHandler tester = new AudioHandler(parent,"../Resources/monstersinc_high.mpg", 1,3, false);
		// audio is not playing at from 0 start time
		assertFalse(tester.mediaControl.mp.isAutoPlay());
	}

    @Test
    public void testloop() throws InterruptedException{
    	AudioHandler tester = new AudioHandler(parent,"../Resources/prometheus-featureukFhp.mp4", 1,3, false);
	    // turn the loop on and test if audio is playing after startTime+duration has ended
	    equals(tester.mediaControl.mp.cycleCountProperty());
	
}
}
