package imagehandler;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ImageMain extends Application {
	
	public void start(Stage stage) throws Exception {
		Group root;
		Scene scene;
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root = new Group();
		scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight(), Color.BLACK);

	    ImageHandler image1 = new ImageHandler("D:/Pictures/bike.jpg", 300, 300, 500, 500, null, null, null, null, null);
	    ImageHandler image2 = new ImageHandler("D:/Pictures/bike2.jpg", 50, 50, 100, 100, 5, 50, null, null, 90);
	    
		root.getChildren().add(image1.box);
		root.getChildren().add(image2.box);
	    stage.setTitle("ImageView");

	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setFullScreen(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	    }

}
