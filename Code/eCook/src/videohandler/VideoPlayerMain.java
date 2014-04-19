package videohandler;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VideoPlayerMain extends Application {
	
	
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(final Stage primaryStage) {
    	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Video Player");
        Group root = new Group();
        
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        scene.setFill(Color.BLACK);
            
        VideoPlayerHandler videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 400, 400, true, null, 5);
        root.getChildren().add(videoPlayerHandler.mediaControl.box);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();       
    }
 
}
