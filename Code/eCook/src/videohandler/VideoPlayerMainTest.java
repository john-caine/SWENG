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
		videoPlayerHandler = new VideoPlayerHandler( "http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4", 200 , 300, null, null, true, null, null);
	}
	
	@Test
	public void getPathName(){
		System.out.println("Path Name = " + videoPlayerHandler.retrieveImage("http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4"));
		assertEquals("http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4", videoPlayerHandler.retrieveImage("http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4"));
	}
	
	@Test
	public void getVideoPlayerLocation(){
		System.out.println("Video Player's x Location = " + videoPlayerHandler.mediaControl.box.getLayoutX());
		System.out.println("Video Player's y Location = " + videoPlayerHandler.mediaControl.box.getLayoutY());
		assertEquals(200, videoPlayerHandler.mediaControl.box.getLayoutX(), 0.01);
		assertEquals(300, videoPlayerHandler.mediaControl.box.getLayoutY(), 0.01);
	}
	
}
