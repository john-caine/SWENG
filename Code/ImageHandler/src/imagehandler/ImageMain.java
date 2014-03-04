package imagehandler;

import java.util.concurrent.TimeUnit;

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
	     ImageHandler image = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 0, 0);
	     ImageHandler image1 = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 300, 300, 500, 500, null, null, null, null);
	     root.getChildren().add(image.box);
	     root.getChildren().add(image1.box);
	     stage.setTitle("ImageView");
	     stage.setFullScreen(true);
	     stage.setScene(scene); 
	     stage.sizeToScene(); 
	     stage.show(); 
	 }

	 Thread timerThread = new Thread("timer") {
		 public void run() {
			 int count=0;
			 while (count <= image1.duration) 
				 TimeUnit.SECONDS.sleep(1);
				 count++;
		 }
	 };
	 
	 
	  public static void main(String[] args) {
	        Application.launch(args);
	    }

}
