/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: SubSlideMedia Test Class
 */

package media;

import static org.junit.Assert.*;
import javafx.animation.Timeline;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import eCook.JavaFXThreadingRule;
import eCook.SlideShow;

public class SubSlideMediaTest {
	
	

	private SubSlideMediaMock subSlideMedia;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	/*
	 * Instantiates a stage and SlideShow to provide to the subSlideMedia constructor and creates a subSlideMediaMock object.
	 */
	@Before
	public void Setup(){
		Stage stage = new Stage();
		SlideShow slideShow = new SlideShow(stage, "../Resources/PWSExamplePlaylist_4.xml", null );
		subSlideMedia = new SubSlideMediaMock(slideShow, 20, 20, 10, 10, null, 0);
	}
	/*
	 * Tests the durationTimeline Timeline has been created.
	 */
	@Test
	public void durationTimelineCreated() {
		assertTrue(subSlideMedia.durationTimeLine instanceof Timeline);
	}
	
	/*
	 * Tests that durationTimline has added the correct keyframe
	 */
	@Test
	public void durationtimeLineCreateKeyFrame(){
		//Assert that the startTime time line has a single key frame added
		assertEquals(1, subSlideMedia.startTimeLine.getKeyFrames().size(), 0.1);
		//Assert that the startTime time line has a key frame duration of 1 second;
		assertEquals(1, subSlideMedia.startTimeLine.getKeyFrames().get(0).getTime().toSeconds(), 0.1);
	}
	
	/*
	 * Tests that hbox orientation has been correctly set.
	 */
	@Test
	public void hboxOrientation(){
		assertEquals(0, subSlideMedia.hbox.getRotate(), 0.1);
	}
	
	/*
	 * Tests when given a valid branch ID that the current slide ID of the slideShow class will change to the branchID
	 */
	@Test
	public void hboxBranch(){
		
		
	}

}
