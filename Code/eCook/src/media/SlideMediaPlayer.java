/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: Abstract slide media player class which creates a media player to play media.
 */
package media;


import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import eCook.SlideShow;
import eCook.eCook;
import errorhandler.ErrorHandler;

public abstract class SlideMediaPlayer extends SlideMedia{

	private String pathLocation;
	private Integer duration;
	private Logger logger;
	private Media media = null;
	protected MediaPlayer mediaPlayer;
	private Duration currentTime;
	
	/*
	 * Slide Media Player Constructor
	 * @Param parent: Reference to the slideshow which called the handler 
	 * @Param pathLocation: The file location of the media
	 * @Param xStart:The x co ordinate of the top left of the hBox
	 * @Param yStart:The y co ordinate of the top left of the hBox
	 * @Param startTime: The time the media is to start playing from
	 * @Param duration: The time the media is to play for
	 * @Param loop:  Sets if the media is to loop or not
	 */

	public SlideMediaPlayer(SlideShow parent, String pathLocation,  Integer xStart, Integer yStart, Integer startTime, Integer duration, Boolean loop) {
		super(xStart, yStart, startTime);
		
		this.pathLocation = pathLocation;
		this.duration = duration;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		//Catches if the media can't be found or if the file path is incorrect
		try {
			media = new Media(pathLocation);
		}
		catch (NullPointerException | IllegalArgumentException e) {
			new ErrorHandler("Fatal error displaying media");
		}
		//Creates the media player with the media
		mediaPlayer = new MediaPlayer(media);
		
		//If loop is true set the media to loop forever, if not set to play once
		if(loop == true){
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		} else{
			mediaPlayer.setCycleCount(1);
		}
		
		//Set the stop time of the media to the duration 
		if (duration != null && duration != 0){
			mediaPlayer.setStopTime(Duration.seconds(duration));
		}
		
		//Set the media player volume
		mediaPlayer.setVolume(1.0);
		
		//Add a listener to the media player to update the current time each time the time changes
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			@Override
			public void changed(ObservableValue<? extends Duration> observableValue,
					Duration duration, Duration current) {
				currentTime = mediaPlayer.getCurrentTime();
				
			}
			
		});
		//Set the Time lines
		setTimeLineOnFinish();
		
	}
	
	/*
	 * Set the startTime Timeline if not null else play the media player
	 */
	protected void setTimingValues(){
		if(startTime != null) {
  			startTimeLine.setCycleCount(startTime);
  			startTimeLine.playFromStart();
  			logger.log(Level.INFO, "Starting StartTime time line ");
  		} 
		else {
  			mediaPlayer.play();
  		}	
	}
	
	/*
	 * When the startTime Timeline finishes play mediaPlayer
	 */
	protected void setTimeLineOnFinish(){
		startTimeLine.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				mediaPlayer.play();	
			}	
		});
	}
	/*
	 * Set the volume of the media player if it is between 0 and 1
	 */
	public void setVolume(double volume){
		if(volume >= 0 && volume <= 1.0){
			mediaPlayer.setVolume(volume);
		}
	}
	
	/*
	 * Pauses the media and logs the pause
	 */
	public void pauseMedia(){
		mediaPlayer.pause();
		logger.log(Level.INFO, "Paused");
	}
	/*
	 * Stops the media and logs the stop
	 */
	public void stopMedia(){
		mediaPlayer.stop();
		logger.log(Level.INFO, "Stopped");
	}
	/*
	 * Resumes the media and logsthe resume
	 */
	public void resumeMedia(){
		mediaPlayer.play();
		logger.log(Level.INFO, "Resumed");
		
	}
	
	/*
	 * Gets the duration of the media file or the duration that has been set
	 * 
	 *@Return duration: The duration set by the user given in milliseconds 
	 *@Return mediaDuration: The duration of the media file in milliseconds
	 */
	public double getDuration(){
		if(duration != null){
			return (double)duration*1000;
		}
		else {
			double mediaDuration = mediaPlayer.getMedia().getDuration().toMillis();
			return mediaDuration;
		}	
	}
	
	/*
	 * Gets the current time of the media in the media player
	 * @Return The current time of the media player
	 */
	public Duration getCurrentTime(){
		return mediaPlayer.getCurrentTime();
	}

	/*
	 * Get the file path location
	 * @Return File path location string
	 */
	public String getFilePath(){
		return pathLocation;
	}
	
	/*
	 * Stops the startTime Timeline and the mediaPlayer the disposes the media player.
	 */
	public void tearDown() {
		stopStartTimeTimeLine();
		mediaPlayer.stop();
		mediaPlayer.dispose();
	}
	
	/* 
	 * Gets the media player
	 * @Return mediaPLayer: The media player playing the media
	 */
	public MediaPlayer getMediaPlayer(){
		return mediaPlayer;
	}
	

}
