/*
 * Programmer: Roger Tan & Zayyad Tagwai
 * 
 * Date Created: 01/03/2014
 * 
 * Description: Main class to run the VideoHandler class for demonstration purposes.
 */

package player;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VideoPlayerMain extends Application {
	
	/* 
	 * Launch the application
	 * 
	 * @param args The string of arguments to be passed to the launch method
	 */
    public static void main(String[] args) {
        launch(args);
    }
     
    /*
     * Call a new VideoHandler instance and add this to the primary stage
     * 
     * @param primaryStage The stage for handlers to be added to.
     */
    @Override
    public void start(final Stage primaryStage) {
        
        Group root = new Group();
        
        // Get the screen bounds and set the size of scene to full screen.
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        scene.setFill(Color.BLACK);
        
        // Create a videoHandler.
        VideoPlayerHandler videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 600, 400, true, 0, 5);
        
        // Add the VideoHandler to the scene.
        root.getChildren().add(videoPlayerHandler.mediaControl.overallBox);
        
        // Configure the stage appropriately.
        primaryStage.setTitle("Video Player");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show(); 
    }
}