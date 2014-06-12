/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: VideoHandler class which takes a video file path and displays the media
 */
package media;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import eCook.SlideShow;
import eCook.eCook;

public class VideoHandler extends SlideMediaPlayer {
	private Logger logger;
	private MediaView mediaView;
	private Integer width;
	private Integer height;
	protected Stage stage;
	private SlideShow parent;
	protected MediaView fullMediaView;


	/*
	 * Video Handler constructor
	 * @Param parent: The slideshow that called the constructor
	 * @Param pathLocation: The media file path
	 * @Param xStart: The x co ordinate of the top left corner of the hBox
	 * @Param yStart: The y co ordinate of the top left corner of the hBox
	 * @Param width: The width of the media object
	 * @Param height: The height of the media object
	 * @Param loop: Sets if the media should loop when finished
	 */
	public VideoHandler(SlideShow parent, String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
		super(parent, pathLocation, xStart, yStart, startTime, duration, loop);
		
		this.width = width;
		this.height = height;
		this.parent = parent;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		//Create a MediaView object to display the video content
		mediaView = new MediaView(mediaPlayer);
		mediaView.setVisible(true);
		
		//Create the Video Control Bar
		VideoControlBar controlBar = new VideoControlBar(this);
		logger.log(Level.INFO, "Video control bar added");
		setMediaViewSize();
		showObject();
		//Create a VBox and add the media view and the control bar.
		VBox playerBox = new VBox();
		playerBox.getChildren().addAll(mediaView, controlBar.getVideoControlBar());
		
		//Add the Vbox to the output HBox
		hbox.getChildren().add(playerBox);
		
		setTimingValues();
		
	}
	
	/*
	 * Sets the size of the media view size
	 */
	private void setMediaViewSize(){
		if (width != null && height != null) {
			// Set the height and width of the MediaPlayer based on the values
			
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height);
		} else {
			mediaView.setPreserveRatio(true);
		}
		
	}
	
	/*
	 * Gets the media view object
	 */
	public MediaView getMediaView(){
		return mediaView;
	}
	
	/*
	 * Sets the video to full screen
	 */
	public void setFullScreen(ActionEvent e){
		stage = new Stage();
		
		// Create a new group
    	final Group root = new Group();
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		
		//Create a new media view and adding the playing media Player
		fullMediaView = new MediaView(mediaPlayer);
		
		//Set the size of the MediaView to the size of the screen
		fullMediaView.setPreserveRatio(false);
		fullMediaView.setFitWidth(bounds.getWidth());
		fullMediaView.setFitHeight(bounds.getHeight());
		
		//Add the MediaView to the group
    	root.getChildren().add(fullMediaView);
    	
    	final Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
    	  
    	// Set up the stage for fullscreen mode
    	stage.setScene(scene);
    	stage.setFullScreen(true);
    	stage.show();
    	
    	//Add the filter to close the new stage and return to eCook
    	 stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
 		    @Override
 		    public void handle(KeyEvent event){
 		    	// Check for ESC key only!
 			    if(event.getCode() == KeyCode.ESCAPE){
 			    	// Close fullscreen mode and return to previous view
 	            	stage.close();
 	            	
 	            	parent.getMainStage().getScene().setCursor(Cursor.DEFAULT);
 	            	parent.getMainStage().setFullScreen(true);
 	            	parent.getMainStage().show();
 	            	
 	            	
 			    }	                                 
 		    }
         });              
	}

}
