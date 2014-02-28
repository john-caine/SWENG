package imagehandler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageMain extends Application {

	public Group root;
	public Scene scene;
	 @Override 
	 public void start(Stage stage) {
		 root = new Group();
	     scene = new Scene(root);
	     scene.setFill(Color.BLACK);
	     ImageHandler image = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 200, 200, 300);
	     root.getChildren().add(image.box);
	     stage.setTitle("ImageView");
	     stage.setFullScreen(true);
	     stage.setScene(scene); 
	     stage.sizeToScene(); 
	     stage.show(); 
	 }

	  public static void main(String[] args) {
	        Application.launch(args);
	    }

}
