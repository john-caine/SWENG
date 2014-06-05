package media;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import eCook.SlideShow;
import eCook.eCook;

public class AudioHandler extends SlideMediaPlayer{
	
	private Media audio;
	private MediaPlayer mediaPlayer;
	private Logger logger;
	private String pathLocation;
	private Duration currentTime;
	private Integer duration;
	private MediaView mediaView;

	public AudioHandler(SlideShow parent, String pathLocation, Integer startTime, Integer duration, Boolean loop){
		super(parent, pathLocation, 0, 0, startTime, duration, loop);
		this.pathLocation = pathLocation;
		this.duration = duration;
		
		mediaView = new MediaView(mediaPlayer);
		hbox.getChildren().add(mediaView);
		
	}
}
