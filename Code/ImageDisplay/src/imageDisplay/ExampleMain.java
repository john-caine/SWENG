package imageDisplay;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExampleMain extends Application {

	public Group root;
	public Scene scene;
	 @Override 
	 public void start(Stage stage) {
		 root = new Group();
	     scene = new Scene(root);
	     scene.setFill(Color.BLACK);
	     ExampleClass image = new ExampleClass("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 200, 200, 300, 500); 
	     root.getChildren().add(image.getImage());
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
