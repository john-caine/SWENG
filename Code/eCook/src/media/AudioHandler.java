package media;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.Duration;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import eCook.SlideShow;
import eCook.eCook;

public class AudioHandler extends SlideMedia{
	
	private Media audio;
	private MediaPlayer mediaPlayer;
	private Logger logger;
	private String pathLocation;
	private Duration currentTime;

	public AudioHandler(SlideShow parent, String pathLocation, Integer startTime, Integer duration, Boolean loop){
		super(0, 0, startTime);
		this.pathLocation = pathLocation;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		audio = new Media(pathLocation);
		mediaPlayer = new MediaPlayer(audio);
		
		if(loop == true){
			mediaPlayer.setCycleCount(AudioClip.INDEFINITE);
		} else{
			mediaPlayer.setCycleCount(1);
		}
		
		if (duration != null && duration != 0){
			mediaPlayer.setStopTime(Duration.seconds(duration));
		}
		
		mediaPlayer.setVolume(1.0);
		
		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {

			

			@Override
			public void changed(ObservableValue<? extends Duration> observableValue,
					Duration duration, Duration current) {
				currentTime = mediaPlayer.getCurrentTime();
				
			}
			
		});
		
		setTimingValues();
		
	}
	
	public void pauseAudio(){
		mediaPlayer.pause();
		logger.log(Level.INFO, "Paused Audio");
	}
	
	public void stopAudio(){
		mediaPlayer.stop();
	}
	
	public void setVolume(double volume){
		if(volume >= 0 && volume <= 1.0){
			mediaPlayer.setVolume(volume);
		}
	}
	public void resumeAudio(){
		mediaPlayer.play();
		logger.log(Level.INFO, "Resumed Audio");
		
	}
	
	public Duration getCurrentTime(){
		return currentTime;
	}
	
	public String getAudioFileName(){
		return pathLocation;
	}

	protected void setTimeLineOnFinish() {
		mediaPlayer.play();
		
	}

	protected void setTimingValues() {
		if(startTime != null) {
  			startTimeLine.setCycleCount(startTime);
  			startTimeLine.playFromStart();
  			logger.log(Level.INFO, "Starting StartTime time line ");
  		} 
		else {
  			mediaPlayer.play();
  		}	
	}
	
	
	
	public void tearDown() {
		stopStartTimeTimeLine();
		mediaPlayer.stop();
	}

}
