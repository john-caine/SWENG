package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;



public class MediaControl {
	
	protected MediaPlayer mp;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider, timeSliderFS;
    private Label playTime, playTimeFS;
    private Slider volumeSlider;
    protected VBox overallBox;
	private InputStream inputStream;
	private Image playImage, pauseImage, stopImage, fullscreenImage;
	private Stage stage, stageFS;
	protected int mpWidth;
	protected int mpHeight;
	private Rectangle2D bounds;
	protected MediaView mediaView;
	private HBox fullscreenMediaBar;
	private Button playButton, playButtonFS;
	private FadeTransition fadeTransition;
	private Integer startTime;
	private Integer playDuration;
	private Boolean mpLoop;
	protected HBox mediaBar;
	
	public MediaControl(final MediaPlayer mp, Integer width, Integer height, Boolean loop, Integer startTime, Integer playDuration){
		
		this.mp = mp;
		this.startTime = startTime;
		this.playDuration = playDuration;
		mediaView = new MediaView(mp);
		
		// Retrieve the size of the Screen
		bounds = Screen.getPrimary().getVisualBounds();
		
		if (loop == null)
			this.mpLoop = false;
		else
			this.mpLoop = loop;
		
		// Set the MediaPlayer cycle count based on the value of loop
		setLoop(mpLoop);
		
		// Set the height and width of the MediaPLayer based on the values
		if (width != null && height != null){
			this.mpWidth = width;
			this.mpHeight = height;
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(mpWidth);
            mediaView.setFitHeight(mpHeight-35);
		}
		// Set a default size of the MediaPlayer when no height and width are being indicated
		else{
			this.mpWidth = (int) (bounds.getWidth()/2);
			this.mpHeight = (int) (bounds.getHeight()/4);
			mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(mpWidth);		
		}
		
		// Set start time to be 0 when no startTime is being indicated
		if (startTime == null) {
	        this.startTime = 0;
	        new Thread(startTimerThread).start();
	    }
		// Start the startTimerThread based on the startTime indicated
	    else 
	        new Thread(startTimerThread).start();
		
		// A VBox that contains the MediaView and Control Panel of the MediaPlayer
		overallBox = new VBox();
		overallBox.setMaxSize(mpWidth, mpHeight);
		mediaView.setFitHeight(mpHeight-35);
		overallBox.getChildren().add(mediaView);
		
		// A HBox that contains all the Controls of the MediaPlayer
		mediaBar = new HBox();
		mediaBar.setMaxWidth(mpWidth);
	    mediaBar.setPadding(new Insets(5, 10, 5, 10));
			try {
				inputStream = new FileInputStream("../Resources/play.png");
				playImage = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/pause.png");
				pauseImage = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/stop.png");
				stopImage = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/fullscreen.png");
				fullscreenImage = new Image(inputStream);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
        playButton = new Button();
        playButton.setGraphic(new ImageView(playImage));
        playButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                updateValues();
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN
                        || status == Status.HALTED) {
                    System.out.println("Player is in a bad or unknown state, can't play.");
                    return;
                }

                if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED) {
                    // Rewind the video if it's at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                        playButton.setGraphic(new ImageView(playImage));
                        updateValues();
                    }
                    mp.play();
                    playButton.setGraphic(new ImageView(pauseImage));
                } else {
                    mp.pause();
                    playButton.setGraphic(new ImageView(playImage));
                }
            }
        });
	    
        // Play / Pause Button in fullscreen mode
        playButtonFS = new Button();
        playButtonFS.setGraphic(new ImageView(playImage));
        playButtonFS.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                updateValues();
                Status status = mp.getStatus();

                if (status == Status.UNKNOWN
                        || status == Status.HALTED) {
                    System.out.println("Player is in a bad or unknown state, can't play.");
                    return;
                }

                if (status == Status.PAUSED
                        || status == Status.READY
                        || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                        playButtonFS.setGraphic(new ImageView(playImage));
                        updateValues();
                    }
                    mp.play();
                    playButtonFS.setGraphic(new ImageView(pauseImage));
                } else {
                    mp.pause();
                    playButtonFS.setGraphic(new ImageView(playImage));
                }
            }
        });
        
        // Whenever there's a change in duration of the MediaPlayer, update the Time Label and Slider Position
        mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
                updateValues();
            }
        });
        
        // Display the appropriate Image the the PlayButton based on the MediaPlayer current status
        mp.setOnPlaying(new Runnable() {

            public void run() {
                if (stopRequested) {
                    mp.pause();
                    stopRequested = false;
                } else {
                	playButton.setGraphic(new ImageView(pauseImage));
                	playButtonFS.setGraphic(new ImageView(pauseImage));
                }
            }
        });
        
        mp.setOnPaused(new Runnable() {

            public void run() {
            	playButton.setGraphic(new ImageView(playImage));
            	playButtonFS.setGraphic(new ImageView(playImage));
            }
        });
        
        mp.setOnStopped(new Runnable() {
        	 
        	public void run() {
        		mp.setStopTime(Duration.INDEFINITE);
        		atEndOfMedia = true; 
	            playButton.setGraphic(new ImageView(playImage));
	            playButtonFS.setGraphic(new ImageView(playImage));
	       }
        });
        
        // Get the total duration of the Media and update the Time Label
        mp.setOnReady(new Runnable() {
        	
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
            }
        });
        
        mp.setOnEndOfMedia(new Runnable() {

            public void run() {
            	if (!mpLoop){ 
                	playButton.setGraphic(new ImageView(playImage));
                    playButtonFS.setGraphic(new ImageView(playImage));
                    atEndOfMedia = true;
                    mp.stop();
                	}
                	mp.seek(mp.getStartTime());
                }
        });
        
        mp.setOnRepeat(new Runnable() {
        	 
        	public void run() {
        		atEndOfMedia = false;
        		playButton.setGraphic(new ImageView(pauseImage));
                playButtonFS.setGraphic(new ImageView(pauseImage));         
            }
        });

        mediaBar.getChildren().add(playButton);
        
        Button stopButton = new Button();
        stopButton.setGraphic(new ImageView(stopImage));
        stopButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                mp.stop();
                atEndOfMedia = true;
                playButton.setGraphic(new ImageView(playImage));
                playButtonFS.setGraphic(new ImageView(playImage));
            }
        });
        mediaBar.getChildren().add(stopButton);
        
        Button fullscreenButton = new Button();
        fullscreenButton.setGraphic(new ImageView(fullscreenImage));
        stageFS = new Stage();
        fullscreenButton.setOnAction(new EventHandler<ActionEvent>() {
        
            public void handle(ActionEvent e) {
            	mediaView.setVisible(false);
            	Node  source = (Node)  e.getSource();
            	stage  = (Stage) source.getScene().getWindow();
            	final Group root = new Group();
            	// Create a new MediaView based on the same Media
            	MediaView mediaViewFS = new MediaView(mp);
            	mediaViewFS.setFitWidth(bounds.getWidth());
            	mediaViewFS.setPreserveRatio(true);
            	mediaViewFS.setLayoutY((bounds.getHeight() - mediaViewFS.getFitHeight())/7);
            
            	root.getChildren().add(mediaViewFS);
            	// Add the Play button for fullscreen and the Slider bar
            	root.getChildren().add(fullscreenMediaBar);

            	final Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
       
            	stageFS.setScene(scene);
            	stageFS.setFullScreen(true);
            	stageFS.show();
            	// Toggle Play and Pause when the user click on the window
                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mp.getStatus() == Status.PLAYING){
                    	mp.pause();
                        }
                        else{
                        mp.play();
                        }
                    }
                });
                
                // Animation for the Control Panel in fullscreen mode
                fadeTransition = new FadeTransition(Duration.millis(3000), fullscreenMediaBar);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                
                scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
                	@Override
    	            public void handle(MouseEvent mouseEvent){
                		fullscreenMediaBar.setDisable(false);
                		scene.setCursor(Cursor.DEFAULT);
                		fadeTransition.play();
    	            }
    	        });
                
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    	fullscreenMediaBar.setDisable(true);
                        scene.setCursor(Cursor.NONE);
                    }
                }); 
                
                fullscreenMediaBar.setOnMouseEntered(new EventHandler<MouseEvent>(){
                	@Override
    	            public void handle(MouseEvent mouseEvent){
                		fadeTransition.stop();
    	            }
    	        });
                
                // Exit Fullscreen mode and return to the main Window
                stageFS.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
        		    @Override
        		    public void handle(KeyEvent event){
        			    if(event.getCode() == KeyCode.ESCAPE){
        	            	stageFS.close();
        	            	mediaView.setVisible(true);
        	            	stage.setFullScreen(true);
        	            	stage.getScene().setCursor(Cursor.HAND);
        	            	stage.show();
        			    }	                                 
        		    }
                });                
                
            }
        });
     	        
        mediaBar.getChildren().add(fullscreenButton);
        Label timeLabel = new Label("   Time: ");
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(timeLabel);
             
        timeSlider = new Slider();
        timeSlider.setMaxWidth((2*mpWidth)/3);
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        
        // Allow the user to drag and position the slider
        timeSlider.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
                if (timeSlider.isValueChanging()) {
                    if (duration != null) {
                    	mp.seek(duration.multiply(timeSlider.getValue()/ 100.0));
                    }
                }
            }
        });
        
        // Allow the user to click on the slider to jump to the desired timing
        timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mp.seek(duration.multiply(timeSlider.getValue()/ 100.0));
            }
        });
        
        mediaBar.getChildren().add(timeSlider);
        
        // Label to show the current time position of the video
        playTime = new Label();
        playTime.setMinWidth(50);
        playTime.setTextFill(Color.WHITE);
        mediaBar.getChildren().add(playTime);

        Label volumeLabel = new Label(" Vol: ");
        volumeLabel.setTextFill(Color.WHITE);
        volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(volumeLabel);

        volumeSlider = new Slider();
        volumeSlider.setManaged(true);
        volumeSlider.setMaxWidth(mpWidth/6);
        volumeSlider.setMinWidth(20);
        // Detect the change in volume slider bar and sets the MediaPlayer's volume accordingly
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
        
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (volumeSlider.isValueChanging()) {
                    mp.setVolume(volumeSlider.getValue() / 100.0);
                }
                updateValues();
            }
        });
        mediaBar.getChildren().add(volumeSlider);
        
        overallBox.getChildren().add(mediaBar);
        
    	timeSliderFS = new Slider();
    	timeSliderFS.setMinWidth(bounds.getWidth()-100); 	
    	timeSliderFS.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
                if (timeSliderFS.isValueChanging()) {
                    if (duration != null) {
                    	mp.seek(duration.multiply(timeSliderFS.getValue()/ 100.0));
                    }
                }
            }
        });
        
        timeSliderFS.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	fadeTransition.stop();
            	mp.seek(duration.multiply(timeSliderFS.getValue()/ 100.0));
            }
        });
        
        playTimeFS = new Label();
        playTimeFS.setMinWidth(50);
        playTimeFS.setTextFill(Color.WHITE);
        
        // Control Panel during fullscrenn mode
        fullscreenMediaBar = new HBox();  
        fullscreenMediaBar.getChildren().add(playButtonFS);
        fullscreenMediaBar.getChildren().add(timeSliderFS);
        fullscreenMediaBar.getChildren().add(playTimeFS);
        fullscreenMediaBar.setLayoutY(bounds.getHeight()-20);
    }
	
	// Function that sets the appropriate values for the Time label, position of the Time Slider and Volume 
    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null)  {
            Platform.runLater(new Runnable() {

                public void run() {
                    Duration currentTime = mp.getCurrentTime();
                    playTime.setText(formatTime(currentTime, mp.getMedia().getDuration()));
                    playTimeFS.setText(formatTime(currentTime, mp.getMedia().getDuration()));
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
                    }
                    if (!timeSliderFS.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSliderFS.isValueChanging()) {
                        timeSliderFS.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
                    }
                    if (!volumeSlider.isValueChanging()) {
                        volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                    }
                }
            });
        }
    }
    
    //Function to count and formulate the Timing of the MediaPlayer
    private static String formatTime(Duration elapsed, Duration duration) {
        int intElapsed = (int) Math.floor(elapsed.toSeconds());
        int elapsedHours = intElapsed / (60 * 60);
        if (elapsedHours > 0) {
            intElapsed -= elapsedHours * 60 * 60;
        }
        int elapsedMinutes = intElapsed / 60;
        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

        if (duration.greaterThan(Duration.ZERO)) {
            int intDuration = (int) Math.floor(duration.toSeconds());
            int durationHours = intDuration / (60 * 60);
            if (durationHours > 0) {
                intDuration -= durationHours * 60 * 60;
            }
            int durationMinutes = intDuration / 60;
            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

            if (durationHours > 0) {
                return String.format("%d:%02d:%02d/%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds,
                        durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d",
                        elapsedMinutes, elapsedSeconds,
                        durationMinutes, durationSeconds);
            }
        } else {
            if (elapsedHours > 0) {
                return String.format("%d:%02d:%02d",
                        elapsedHours, elapsedMinutes, elapsedSeconds);
            } else {
                return String.format("%02d:%02d",
                        elapsedMinutes, elapsedSeconds);
            }
        }
    }
    
    // Thread to sleep the MediaPlayer for starTime duration before MediaPlayer starts to play
    Task<Object> startTimerThread = new Task<Object>() {
    	
		@Override
		protected Object call() throws Exception {
			TimeUnit.SECONDS.sleep(startTime);
		 
			Platform.runLater (new Runnable() {
				public void run(){
					mp.play();
				}
			});
			if (playDuration != null && playDuration != 0){
				mp.setStopTime(Duration.seconds(playDuration));
			}
			return null;
		}
    };
    // Thread to play the MediaPlayer for duration amount of time before stopping the MediaPlayer (not in use at the moment)
	 Task<Object> durationTimerThread = new Task<Object>() {
		
		 @Override
		protected Object call() throws Exception {
			int count=0;
			while (count <= playDuration) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
					 
				Platform.runLater( new Runnable(){
					public void run(){
						mp.stop();
						if(mpLoop){
							mp.play();
						}
					}
				});	 
				return null;
			}
		};
	
	// Function to set the cycle count of the MediaPlayer
	public void setLoop(boolean loop) {
		if (loop){
			mp.setCycleCount(MediaPlayer.INDEFINITE);
		}else
			mp.setCycleCount(1);
	}

}