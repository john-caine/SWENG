package player;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	VideoPlayerHandler videoPlayerHandler;
	
	@Before
	public void setup() {
		videoPlayerHandler = new VideoPlayerHandler("C:/Users/R T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4", 200 , 300);
	}
	
	@Test
	public void getPathName(){
		System.out.println("Path Name = " + videoPlayerHandler.path);
		assertEquals("file:/C:/Users/R%20T/workspace/VideoPlayer/src/prometheus-featureukFhp.mp4", videoPlayerHandler.path);
	}
	
	@Test
	public void getVideoPlayerLocation(){
		System.out.println("Video Player's x Location = " + videoPlayerHandler.mediaControl.box.getLayoutX());
		System.out.println("Video Player's y Location = " + videoPlayerHandler.mediaControl.box.getLayoutY());
		assertEquals(200, videoPlayerHandler.mediaControl.box.getLayoutX(), 0.01);
		assertEquals(300, videoPlayerHandler.mediaControl.box.getLayoutY(), 0.01);
	}
	
}