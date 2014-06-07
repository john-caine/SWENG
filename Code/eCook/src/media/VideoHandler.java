package media;

import java.util.logging.Logger;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import eCook.SlideShow;
import eCook.eCook;

public class VideoHandler extends SlideMediaPlayer {
	private Logger logger;
	private MediaView mediaView;
	private Integer width;
	private Integer height;

	public VideoHandler(SlideShow parent, String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
		super(parent, pathLocation, xStart, yStart, startTime, duration, loop);
		
		this.width = width;
		this.height = height;
		
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
	

}
