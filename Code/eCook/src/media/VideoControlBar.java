package media;

import java.util.ArrayList;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class VideoControlBar {

	private VideoHandler videoHandler;
	private HBox controlBar;
	private Slider trackBar;
	private Slider volBar;
	private Label timeLbl;
	private Button playPauseButton;
	private Button stopButton;
	private ImageView playImage;
	private ImageView pauseImage;

	public VideoControlBar(VideoHandler videoHandler){
		this.videoHandler = videoHandler;
		setupControlBar();
		setupButtons();
		setupSliders();
		
	}
	
	public void setupControlBar() {
		controlBar = new HBox();
		videoHandler.getMediaView().getFitHeight();
		videoHandler.getMediaView().getFitWidth();
		
        controlBar.setPrefSize(videoHandler.getMediaView().getFitHeight(), videoHandler.getMediaView().getFitWidth() );
        controlBar.setAlignment(Pos.CENTER); 
        controlBar.setStyle("-fx-background-color: rgba(255,255,255,0.9);");
		
		// declare buttons and set up images
		
        playPauseButton = new Button("");
		playImage = new ImageView(new Image("audioBarPlay.png"));
		pauseImage = new ImageView(new Image("audioBarPause.png"));
		playPauseButton.setGraphic(playImage);
      
		stopButton = new Button ("");
		ImageView stopImage = new ImageView(new Image("audioBarStop.png"));
		stopButton.setGraphic(stopImage);
       
		
        // set up sliders
        trackBar = new Slider();
        trackBar.setMin(0);
        trackBar.setMax(videoHandler.getDuration());
        //trackBar.setValue(0);
        volBar = new Slider();
        volBar.setMin(0);
        volBar.setMax(1.0);
        volBar.setValue(1.0);
        volBar.setPrefWidth(controlBar.getWidth()/5);
        
        // set up filename and time labels
       
        timeLbl = new Label("");
        timeLbl.setText(formatTime(videoHandler.getMediaPlayer().getCurrentTime(), Duration.seconds(videoHandler.getDuration()) ));
        timeLbl.setStyle("-fx-text-fill: black;");
        Label volumeLabel = new Label();
        ImageView volumeImage = new ImageView(new Image("audioBarVol.png"));
		volumeLabel.setGraphic(volumeImage);
        
        
        
        // populate the controlBar
        controlBar.getChildren().addAll(playPauseButton, stopButton,  trackBar, timeLbl, volumeLabel,  volBar);
        
        videoHandler.getMediaPlayer().setOnPlaying(new Runnable(){
			@Override
			public void run() {
				playPauseButton.setGraphic(pauseImage);
				
			}   	
        });
        
        videoHandler.getMediaPlayer().setOnEndOfMedia(new Runnable(){
			@Override
			public void run() {
				playPauseButton.setGraphic(playImage);	
			}
        });
        
        videoHandler.getMediaPlayer().setOnRepeat(new Runnable(){
        	@Override
        	public void run(){
        		playPauseButton.setGraphic(pauseImage);
        	}
        });
	}
	
	// set up event handlers for the buttons
	public void setupButtons() {
		// play/pause button
		playPauseButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// toggle play/pause
				if (videoHandler.getMediaPlayer().getStatus() == MediaPlayer.Status.PLAYING) {
					videoHandler.pauseMedia();
					playPauseButton.setGraphic(playImage);
				}
				else if (videoHandler.getMediaPlayer().getStatus() == MediaPlayer.Status.PAUSED || videoHandler.getMediaPlayer().getStatus() == MediaPlayer.Status.STOPPED) {
					videoHandler.resumeMedia();
					playPauseButton.setGraphic(pauseImage);
				}
			}
		});
		
		// stop button
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// call stop
				videoHandler.stopMedia();
				// set the pause button to play if not already
				playPauseButton.setGraphic(playImage);
			}
		});
		
	}
	
	public void setupSliders() {
		// volume bar
		volBar.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				videoHandler.setVolume(new_val.doubleValue());
			}
		});
	
		// tracking bar
		
		// Whenever there's a change in duration of the MediaPlayer, update the Time Label and Slider Position
        videoHandler.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
            	
            	
            	timeLbl.setText(formatTime(videoHandler.getMediaPlayer().getCurrentTime(), Duration.seconds(videoHandler.getDuration()) ));
            	trackBar.setValue(videoHandler.getMediaPlayer().getCurrentTime().toMillis());
            }
        });
        trackBar.setMax(videoHandler.getDuration());
        
       trackBar.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
            	// If the value is changing perform an action
                if (trackBar.isValueChanging()) {
                	// If the duration of video is not null, move to requested time
                		Duration duration = new Duration(trackBar.getValue());
                    	videoHandler.getMediaPlayer().seek(duration);
                    }
                }
            
        });    
	}
	
	protected HBox getVideoControlBar(){
		return controlBar;
	}
	
	 /*
     * Method to count and formulate the Timing of the MediaPlayer
     * 
     * @param elapsed The time elapsed so far
     * @param duration The total duration of the video
     */
    private static String formatTime(Duration elapsed, Duration duration) {
        // Calculate the total elapsed time in seconds
    	int intElapsed = (int) Math.floor(elapsed.toSeconds());
        
    	// Get the elapsed time in hours
    	int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        
        // Get the elapsed time in minutes and remaining seconds
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        // Check the duration isn't 0
        if (duration.greaterThan(Duration.ZERO)) {
        	// Get the total duration in seconds
            int intDuration = (int) Math.floor(duration.toSeconds());
            
            // Get the duration in hours
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            
            // Get the duration in minutes & remaining seconds
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            // Check if the duration contains hours then format appropriately
            if (durationHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        } else {
        	// If the duration is 0, return the times formatted appropriately
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d", elapsedMinutes, elapsedSeconds);
            }
        }
    }
}
