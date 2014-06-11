/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan, Paul Mathema 
 * Date Created: 04/06/2014
 * Description: Audio Handler class which plays an audio file from a given path location.
 */
 
package media;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import eCook.SlideShow;
import eCook.eCook;

public class AudioHandler extends SlideMediaPlayer{
	
	private MediaPlayer mediaPlayer;
	private Logger logger;
	private MediaView mediaView;
	
	/*
	 * AudioHandler Constructor
	 * @Param parent: The slideshow which has called the handler.
	 * @Param pathLocation: The file path of the audio media.
	 * @Param startTime: The time in seconds the audio is to begin playing.
	 * @Param duration: The time in seconds the audio is to play for.
	 * @Param loop: Sets if the audio is to be repeated.
	 */
	public AudioHandler(SlideShow parent, String pathLocation, Integer startTime, Integer duration, Boolean loop){
		super(parent, pathLocation, 0, 0, startTime, duration, loop);
		
	// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		mediaView = new MediaView(mediaPlayer);
		logger.log(Level.INFO, "AudioHandler Media Player Created");
		hbox.getChildren().add(mediaView);
		setTimingValues();
		
	}
}
