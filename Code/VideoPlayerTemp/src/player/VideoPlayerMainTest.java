package player;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	VideoPlayerHandler videoPlayerHandler;
	MediaControl mediaControl;
	
	public static class AsNonApp extends Application {
	    @Override
	    public void start(Stage primaryStage) throws Exception {
	        // noop
	    }
	}

	@BeforeClass
	public static void initJFX() {
	    Thread t = new Thread("JavaFX Init Thread") {
	        public void run() {
	            Application.launch(AsNonApp.class, new String[0]);
	        }
	    };
	    t.setDaemon(true);
	    t.start();
	}
	
	@Before
	public void setup() {
		videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 400, 600, null, null, true, 1, 4);
	}
	
	/*@Test
	public void getVideoPlayerLocation(){
		//System.out.println("Video Player's x Location = " + videoPlayerHandler.mediaControl.box.getLayoutX());
		//System.out.println("Video Player's y Location = " + videoPlayerHandler.mediaControl.box.getLayoutY());
		assertEquals(400, videoPlayerHandler.mediaControl.box.getLayoutX(), 0.01);
		assertEquals(600, videoPlayerHandler.mediaControl.box.getLayoutY(), 0.01);
	}*/
	
	@Test
	public void BlahBlah(){
		System.out.println("Video Player's y Location = " + videoPlayerHandler.mediaPlayer.isAutoPlay());
	}
}
