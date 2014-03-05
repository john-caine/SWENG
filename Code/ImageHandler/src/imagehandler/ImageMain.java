package imagehandler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageMain extends Application {
	
	public void start(Stage stage) throws Exception {
		Group root;
		Scene scene;
		root = new Group();
		scene = new Scene(root);
	    scene.setFill(Color.BLACK); 

	    //ImageHandler image1 = new ImageHandler("D:/Pictures/bike.jpg", 300, 300, 500, 500, null, null, null, null);
	    ImageHandler image2 = new ImageHandler("D:/Pictures/bike2.jpg", 50, 50, 100, 100, 5, 15, null, null);
	    
		//root.getChildren().add(image1.box);
		root.getChildren().add(image2.box);
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
