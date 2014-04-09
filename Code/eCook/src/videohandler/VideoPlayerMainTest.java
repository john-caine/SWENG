package videohandler;

import static org.junit.Assert.*;
import javafx.geometry.Rectangle2D;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	private Stage stage;
	private Rectangle2D screenBounds;
	VideoPlayerHandler videoPlayerHandler;
	
	@Before
	public void setup() {
		videoPlayerHandler = new VideoPlayerHandler("C:/Users/R T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4", 200 , 300, true);
	}
	
	@Test
	public void getPathName(){
		System.out.println("Path Name = " + videoPlayerHandler.retrieveImage("C:/Users/R T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4"));
		assertEquals("file:/C:/Users/R%20T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4", videoPlayerHandler.retrieveImage("C:/Users/R T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4"));
	}
	
	@Test
	public void getVideoPlayerLocation(){
		System.out.println("Video Player's x Location = " + videoPlayerHandler.mediaControl.box.getLayoutX());
		System.out.println("Video Player's y Location = " + videoPlayerHandler.mediaControl.box.getLayoutY());
		assertEquals(200, videoPlayerHandler.mediaControl.box.getLayoutX(), 0.01);
		assertEquals(300, videoPlayerHandler.mediaControl.box.getLayoutY(), 0.01);
	}
	
}
