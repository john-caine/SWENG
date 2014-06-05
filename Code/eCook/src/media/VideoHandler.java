package media;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import eCook.SlideShow;
import eCook.eCook;

public class VideoHandler extends SlideMediaPlayer {
	private Media media;
	private MediaPlayer mediaPlayer;
	private Logger logger;
	private MediaView mediaView;

	public VideoHandler(SlideShow parent, String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
		super(parent, pathLocation, xStart, yStart, startTime, duration, loop);
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		//Create a MediaView object to display the video content
		mediaView = new MediaView(mediaPlayer);
		hbox.getChildren().add(mediaView);
		
	}

}
