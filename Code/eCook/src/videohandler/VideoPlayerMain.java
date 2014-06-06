/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * Main class which adds the video player to a stage
 */

package videohandler;

import eCook.SlideShow;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VideoPlayerMain extends Application {
	
	private SlideShow parent;
	
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(final Stage primaryStage) {
    	/* Get the screen's size */
    	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    	
    	/* Set the stage title */
        primaryStage.setTitle("Video Player");
        Group root = new Group();
        
        /* Set the root to the scene */
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        
        /* Set the background to be black in colour */
        scene.setFill(Color.BLACK);
        
        /* Call the videoHandler class to create a videoplayer based on certain attributes */
        VideoHandler videoPlayerHandler = new VideoHandler(parent,"http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 800, 400, true, null, 10);
        
        /* Add the videoplayer to the stage */
        root.getChildren().add(videoPlayerHandler.mediaControl.overallBox);
        
        /* Set the stage*/
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();       
    }
 
}
