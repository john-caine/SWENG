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

public abstract class SlideMediaPlayer extends SlideMedia{

	private String pathLocation;
	private Integer duration;
	private Logger logger;
	private Media media;
	protected MediaPlayer mediaPlayer;
	private Duration currentTime;

	public SlideMediaPlayer(SlideShow parent, String pathLocation,  Integer xStart, Integer yStart, Integer startTime, Integer duration, Boolean loop) {
		super(xStart, yStart, startTime);
		
		this.pathLocation = pathLocation;
		this.duration = duration;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		media = new Media(pathLocation);
		mediaPlayer = new MediaPlayer(media);
		
		if(loop == true){
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
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
		
		setTimeLineOnFinish();
		
	}
	
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
	
	protected void setTimeLineOnFinish(){
		startTimeLine.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				mediaPlayer.play();	
			}	
		});
	}
	
	public void setVolume(double volume){
		if(volume >= 0 && volume <= 1.0){
			mediaPlayer.setVolume(volume);
		}
	}
	
	public void pauseMedia(){
		mediaPlayer.pause();
		logger.log(Level.INFO, "Paused");
	}
	
	public void stopMedia(){
		mediaPlayer.stop();
		logger.log(Level.INFO, "Stopped");
	}
	
	public void resumeMedia(){
		mediaPlayer.play();
		logger.log(Level.INFO, "Resumed");
		
	}
	
	public double getDuration(){
		if(duration != null){
			return (double)duration*1000;
		}
		else {
			
			return mediaPlayer.getMedia().getDuration().toMillis();
		}	
	}
	
	public Duration getCurrentTime(){
		return mediaPlayer.getCurrentTime();
	}

	public String getFilePath(){
		return pathLocation;
	}
	
	public void tearDown() {
		stopStartTimeTimeLine();
		mediaPlayer.stop();
		mediaPlayer.dispose();
	}
	
	public MediaPlayer getMediaPlayer(){
		return mediaPlayer;
	}
	

}
