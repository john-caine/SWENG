package media;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
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
	private Stage stage;
	private SlideShow parent;


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
		VideoControlBar controlBar = new VideoControlBar(this);
		setMediaViewSize();
		showObject();
		VBox playerBox = new VBox();
		playerBox.getChildren().addAll(mediaView, controlBar.getVideoControlBar());
		hbox.getChildren().add(playerBox);
		setTimingValues();
		
	}
	
	private void setMediaViewSize(){
		if (width != null && height != null) {
			// Set the height and width of the MediaPlayer based on the values
			
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(width);
            mediaView.setFitHeight(height-35);
		} else {
			mediaView.setPreserveRatio(true);
		}
		
	}
	
	public MediaView getMediaView(){
		return mediaView;
	}
	
	
	public void setFullScreen(ActionEvent e){
		stage = new Stage();
		Node  source = (Node)  e.getSource();
    	stage  = (Stage) source.getScene().getWindow();
    	final Group root = new Group();
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		mediaView.setPreserveRatio(false);
    	mediaView.setFitWidth(bounds.getWidth());
    	mediaView.setFitHeight(bounds.getHeight());
    	root.getChildren().add(mediaView);
    	
    	final Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
    	  
    	// Set up the stage for fullscreen mode
    	stage.setScene(scene);
    	stage.setFullScreen(true);
    	stage.show();
    	
    	 stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
 		    @Override
 		    public void handle(KeyEvent event){
 		    	// Check for ESC key only!
 			    if(event.getCode() == KeyCode.ESCAPE){
 			    	// Close fullscreen mode and return to previous view
 	            	stage.close();
 	            	mediaView.setVisible(true);
 	            	parent.getMainStage().setFullScreen(true);
 	            	parent.getMainStage().getScene().setCursor(Cursor.DEFAULT);
 	            	parent.getMainStage().show();
 			    }	                                 
 		    }
         });              
	}

}
