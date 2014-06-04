/*
 * Programmer: Steve Thorpe & Jonathan Caine
 * Date Created: 04/06/2014
 * Description: Tests the SlideMedia abstract class methods, uses the mock SlideMedia class SlideMediaMock, to call the necessary methods.
 */

package media;

import static org.junit.Assert.*;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.scene.layout.HBox;

import org.junit.Before;
import org.junit.Test;

public class SlideMediaTest {

	private SlideMediaMock slideMedia;

	/*
	 * Creates a mock slide media object of type SlideMediaMock
	 */
	@Before
	public void SetUp(){
		slideMedia = new SlideMediaMock(20, 20, 10);
	}
	
	/*
	 * Test the Slide Media constructor 
	 */
	@Test
	public void requiredObjectsCreated(){
		assertTrue(slideMedia.hbox instanceof HBox);
		assertFalse(slideMedia.hbox.isVisible());
		assertTrue(slideMedia.startTimeLine instanceof Timeline);
	}
	
	/*
	 * Test the XY coordinates of the HBox.
	 */
	@Test
	public void hBoxXYCoordinates() {
		assertEquals(20, slideMedia.hbox.getLayoutX(),0.1);
		assertEquals(20, slideMedia.hbox.getLayoutY(), 0.1);
	}
	
	/*
	 * Tests the startTimeline timeline has been given the correct key frame.
	 */
	@Test
	public void timeLineCreateKeyFrame(){
		//Assert that the startTime time line has a single key frame added
		assertEquals(1, slideMedia.startTimeLine.getKeyFrames().size(), 0.1);
		//Assert that the startTime time line has a key frame duration of 1 second;
		assertEquals(1, slideMedia.startTimeLine.getKeyFrames().get(0).getTime().toSeconds(), 0.1);
	}
	
	/*
	 * Tests that the visibility of the HBox can be set using the methods provided.
	 */
	@Test 
	public void testHboxVisibilityMethods(){
		slideMedia.showObject();
		assertTrue(slideMedia.hbox.isVisible());
		slideMedia.hideObject();
		assertFalse(slideMedia.hbox.isVisible());
	}
	
	/*
	 * Test a running Timeline can be paused using the SlideMedia pauseStartTimeTimeLine() method.
	 */
	@Test 
	public void timeLinePause(){
		slideMedia.startTimeLine.play();
		slideMedia.pauseStartTimeTimeLine();
		assertEquals(Animation.Status.PAUSED, slideMedia.startTimeLine.getStatus());
	}
	
	/*
	 * Test a paused Timeline can be paused using the SlideMedia resumeStartTimeTimeLine() method.
	 */
	@Test 
	public void timeLineResume(){
		slideMedia.startTimeLine.play();
		slideMedia.pauseStartTimeTimeLine();
		slideMedia.resumeStartTimeTimeLine();
		assertEquals(Animation.Status.RUNNING, slideMedia.startTimeLine.getStatus());
	}
	
	/*
	 * Tests that startTimeline does not resume if it has finished(When a Timeline finishes it transitions to Animation.Status.Stopped
	 *	therefore stopping startTimeline is an adequate simulation of a finished Timeline for this test)
	 */
	@Test 
	public void timeLineResumeVisibleHBox(){
		slideMedia.startTimeLine.play();
		slideMedia.stopStartTimeTimeLine();
		slideMedia.resumeStartTimeTimeLine();
		assertEquals(Animation.Status.STOPPED, slideMedia.startTimeLine.getStatus());
		
	}
	
	/*
	 * Tests that startTimeline does not resume if the HBox is visible
	 */
		@Test 
		public void timeLineResumeFinishedTimeline(){
			slideMedia.startTimeLine.play();
			slideMedia.showObject();
			slideMedia.pauseStartTimeTimeLine();
			slideMedia.resumeStartTimeTimeLine();
			assertEquals(Animation.Status.PAUSED, slideMedia.startTimeLine.getStatus());
			
		}
	
	/*
	 * Test a running Timeline can be stopped using the SlideMedia stopStartTimeTimeLine() method.
	 */
	@Test
	public void timeLineStop(){
		slideMedia.startTimeLine.play();
		slideMedia.stopStartTimeTimeLine();
		assertEquals(Animation.Status.STOPPED, slideMedia.startTimeLine.getStatus());
	}
	


}
