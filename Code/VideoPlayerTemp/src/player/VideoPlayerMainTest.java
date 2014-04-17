package player;

import static org.junit.Assert.*;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class VideoPlayerMainTest {
	
	private Stage stage;
	private Rectangle2D screenBounds;
	Group root;
	Scene scene;
	
	Media media;
	MediaPlayer mediaPlayer;
	MediaControl mediaControl;
	VideoPlayerHandler videoPlayerHandler;
	String pathLocation = "http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv";
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setup() {
		stage = new Stage();
		screenBounds = Screen.getPrimary().getVisualBounds();
		root = new Group();
		scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
		scene.setFill(Color.BLACK);
		videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 400, 400, true, null, 5);
        root.getChildren().add(videoPlayerHandler.mediaControl.box);
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();       
	}
	
	@Test
	public void getVideoPlayerLocation(){
		assertEquals(300, videoPlayerHandler.mediaControl.box.getLayoutX(), 0.01);
		assertEquals(300, videoPlayerHandler.mediaControl.box.getLayoutY(), 0.01);
	}
	
	@Test
	public void mediaPlayerVisible() {
		assertTrue(videoPlayerHandler.mediaControl.box.isVisible());
	}
	
}
