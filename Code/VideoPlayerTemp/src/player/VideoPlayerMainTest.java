package player;

import static org.junit.Assert.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	Media media;
	MediaPlayer mediaPlayer;
	MediaControl mediaControl;
	String pathLocation = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
	
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
		media = new Media(pathLocation);
		mediaPlayer = new MediaPlayer(media);
		mediaControl = new MediaControl(mediaPlayer, 400, 400, true, 0, 10);
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
		System.out.println(mediaControl.box.isVisible());
	}
}
