/*
 * Programmers: Roger Tan, Jonathan Caine, Zayyad Tagwai
 * Date: 06/03/2014
 * Version: 1.4
 * Description: A class that instantiates a mock "slide" in the form of a scene and stage, and provides the
 * 				ability to launch a Java application which creates an ImageHandler object for testing.
 */

package imageDisplay;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExampleMain extends Application {
	 
	/*
	 * Start method as required due to class extending Application. Creates a new window called "ImageView"
	 * and returns an ImageHandler object into the window.
	 */
	@Override 
	public void start(Stage stage) {
		Group root;
		Scene scene;
		
		root = new Group();
	    scene = new Scene(root);
	    scene.setFill(Color.BLACK);
	    ImageHandler image = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 200, 200, 300, 500);
	    ImageHandler image1 = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 200, 200, 300, null);
	    ImageHandler image2 = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 600, 300, null, 500);
	    root.getChildren().add(image.returnImage());
	    root.getChildren().add(image1.returnImage());
	    root.getChildren().add(image2.returnImage());
	    stage.setTitle("ImageView");
	    stage.setFullScreen(true);
	    stage.setScene(scene); 
	    stage.sizeToScene(); 
	    stage.show(); 
	 }

	 /*
	  * Main method to launch the Java application.
	  */
	  public static void main(String[] args) {
	        Application.launch(args);
	    }

}
