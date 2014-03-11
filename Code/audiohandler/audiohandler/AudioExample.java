package audiohandler;

import java.util.ArrayList;

import imagehandler.ImageHandler;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AudioExample extends Application {

	private AudioHandler vvvvvv, tweet, tweetOnce;

	public void start(Stage stage) throws Exception {
		Group root;
		Scene scene;
		root = new Group();
		scene = new Scene(root, 800, 600, Color.BLACK);

		vvvvvv = new AudioHandler("file:assets/positive_force.mp3", 5, 0.8);
		tweet = new AudioHandler("file:assets/tweet.wav", 2, 5, 1.0);
		tweetOnce = new AudioHandler("file:assets/tweet.wav", 0, 0.2);

		doge(root, scene);

		tweet.setRepeat(true);

		vvvvvv.begin();
		tweet.begin();
		tweetOnce.begin();

		stage.setTitle("Audio Example");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setFullScreen(false);
		stage.show();

		// when the window is closed stop the audio
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				vvvvvv.stop();
				tweet.stop();
				tweetOnce.stop();
			}
		});
	}

	private void doge(Group root, Scene scene) {
		ArrayList<ImageHandler> images = new ArrayList<ImageHandler>();
		for (int i = 1; i < 20; i++) {
			images.add(new ImageHandler("assets/doge.png", (int) (scene.getWidth() - scene.getWidth() / i) / 2, (int) (scene.getHeight() - scene.getHeight()
					/ i) / 2, (int) scene.getWidth() / i, (int) scene.getHeight() / i, i, 10, null, null, (int) (((double) (i - 1) / 20) * 360)));
			root.getChildren().add(images.get(i - 1).box);
		}
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}