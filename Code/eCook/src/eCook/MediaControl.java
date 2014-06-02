/*
 * Programmer: Roger Tan & Zayyad Tagwai
 * 
 * Date Created: 01/03/2014
 * 
 * Description: Class that creates a control bar that is overlaid on the video. Functionality
 * 				is play/pause, stop, scrubbing bar, volume slider, and fullscreen mode with same.
 */

package eCook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public abstract class MediaControl {
	
	public MediaPlayer mp;
    public boolean stopRequested = false;
    public boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider, timeSliderFS;
    protected Label playTime, playTimeFS;
    protected Slider volumeSlider;
    public VBox overallBox;
	private InputStream inputStream;
	private Image playImage, pauseImage, stopImage, fullscreenImage;
	private Stage stage, stageFS;
	protected int mpWidth;
	protected int mpHeight;
	private Rectangle2D bounds;
	public MediaView mediaView;
	private HBox fullscreenMediaBar;
	public Button playButton, playButtonFS;
	private FadeTransition fadeTransition;
	private Integer startTime;
	private final Integer playDuration;
	private Boolean mpLoop;
	public HBox mediaBar;
	protected boolean continuePlaying = true;
	private Timeline timeLineStart;
	private Animation.Status stopped = Animation.Status.STOPPED;
	private Status status;
	public Button stopButton;
	boolean controlPanelVisible = false;
	
	/* 
	 * Constructor for the MediaControl class. Accepts optional parameters from PWS.
	 * Creates a visual control bar with a play/pause button, a stop button, and a 
	 * fullscreen button, which is overlayed onto the MediaPlayer. Also handles 
	 * entering into the fullscreen viewing mode.
	 * 
	 * @param mp The MediaPlayer object instantiated by the VideoHandler class
	 * @param width The PWS optional width for the MediaPlayer
	 * @param height The PWS optional height for the MediaPlayer
	 * @param loop The PWS optional loop value for the video
	 * @param startTime The PWS optional startTime to delay the video starting to play
	 * @param playDuration The PWS optional duration to play the video for
	 */
	public MediaControl(final MediaPlayer mp, Integer width, Integer height, Boolean loop, Integer startTime, final Integer playDuration){
		
		this.mp = mp;
		this.startTime = startTime;
		this.playDuration = playDuration;
		mediaView = new MediaView(mp);
		
		// Retrieve the size of the Screen
		bounds = Screen.getPrimary().getVisualBounds();
		
		// Assign loop variable as necessary
		if (loop == null) {
			this.mpLoop = false;
		} else {
			this.mpLoop = loop;
		}
		
		// Set the MediaPlayer cycle count based on the value of loop
		setLoop(mpLoop);
		
		if (width != null && height != null) {
			// Set the height and width of the MediaPlayer based on the values
			this.mpWidth = width;
			this.mpHeight = height;
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(mpWidth);
            mediaView.setFitHeight(mpHeight-35);
		} else {
			// Set a default size of the MediaPlayer when no height and width are being indicated
			this.mpWidth = (int) (bounds.getWidth()/2);
			this.mpHeight = (int) (bounds.getHeight()/3);
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(mpWidth);
            mediaView.setFitHeight(mpHeight-35);
		}
		
	
		
		// A VBox that contains the MediaView and Control Panel of the MediaPlayer
		overallBox = new VBox();
		overallBox.setMaxSize(mpWidth, mpHeight);
		overallBox.getChildren().add(mediaView);
		
		// A HBox that contains all the Controls of the MediaPlayer
		mediaBar = new HBox(5);
		mediaBar.setAlignment(Pos.CENTER);
		mediaBar.setStyle("-fx-background-color: grey;");
		mediaBar.setMaxWidth(mpWidth);
	    mediaBar.setPadding(new Insets(5, 10, 5, 10));
			
	    try {
	    	// Get and load images for buttons on MediaControl bar.
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
		
		// Handle the play button
		setPlayButton();
        
		// Handle the stop button
        setStopButton();
        
        // Handle the fullscreen button
        setFullScreenButton();

        // Label to show the length of the video
        Label timeLabel = new Label(" Time: ");
        timeLabel.setId("timeLabel");
        timeLabel.getStylesheets().add("file:../Resources/css.css");
        timeLabel.setTextAlignment(TextAlignment.CENTER);
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(timeLabel);
             
        // Handle the time slider
        setTimeSlider();
        
        // Label to show the current time position of the video
        playTime = new Label();
        playTime.setId("playTimeLabel");
        playTime.getStylesheets().add("file:../Resources/css.css");
        playTime.setTextAlignment(TextAlignment.CENTER);
        playTime.setTextFill(Color.WHITE);
        playTime.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(playTime);

        // Label to show the current volume of the media
        Label volumeLabel = new Label(" Vol: ");
        volumeLabel.setId("volumeLabel");
        volumeLabel.getStylesheets().add("file:../Resources/css.css");
        volumeLabel.setTextFill(Color.WHITE);
        volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
        mediaBar.getChildren().add(volumeLabel);

        // Handle the volume slider
        setVolumeSlider();
        
        // Label for play time in full screen mode
        playTimeFS = new Label();
        playTimeFS.setId("playTimeLabel");
        playTimeFS.getStylesheets().add("file:../Resources/css.css");
        playTimeFS.setMinWidth(Control.USE_PREF_SIZE);
        playTimeFS.setTextFill(Color.WHITE);

        // Add components to Control Panel during fullscreen mode
        fullscreenMediaBar = new HBox(5); 
        fullscreenMediaBar.setAlignment(Pos.CENTER);
        fullscreenMediaBar.setStyle("-fx-background-color: grey;");
        fullscreenMediaBar.getChildren().add(playButtonFS);
        fullscreenMediaBar.getChildren().add(timeSliderFS);
        fullscreenMediaBar.getChildren().add(playTimeFS);
        fullscreenMediaBar.setLayoutY(bounds.getHeight()+15);
        fullscreenMediaBar.setMaxWidth(bounds.getWidth());
        
        // Add the mediaBar "box" to the overall MediaControl "bar"
        overallBox.getChildren().add(mediaBar);
        
        //Create the startTime timeline
  		timeLineStart = new Timeline();
  		
  		//When startTime timeline has finished show the image and if there is a duration begin the duration timeline
  		timeLineStart.setOnFinished(new EventHandler<ActionEvent>(){

  			@Override
  			public void handle(ActionEvent arg0) {
  					
  				
  				if (continuePlaying == true){
  					Platform.runLater (new Runnable() {
  						public void run(){
  							mp.play();
  						}
  					});
  					
  					// If the duration to play is not invalid set a stop time
  					if (playDuration != null && playDuration != 0){
  						mp.setStopTime(Duration.seconds(playDuration));
  					}
  			}	
  		}});
  		createKeyFrame();
  		
  		//If a start time has been set, start the startTime timeline, if not play the media
  		if(startTime != null){
  			System.out.println("Starttime media timeline running");
  			timeLineStart.setCycleCount(this.startTime);
  			timeLineStart.playFromStart();
  		}
  		else{
  			Platform.runLater (new Runnable() {
				public void run(){
					System.out.println("Media time line finished");
					mp.play();
				}
			});
  			
  			if (playDuration != null && playDuration != 0){
					mp.setStopTime(Duration.seconds(playDuration));
				}
  		}
    }
	
	/* 
	 * Function that creates a volume slider, adds the relevant event handlers to it, and
	 * adds it to the MediaControl bar.
	 */
	void setVolumeSlider() {
		// Create a new volume slider
		volumeSlider = new Slider();
		volumeSlider.getStylesheets().add("file:../Resources/css.css");
	    volumeSlider.setMaxWidth(mpWidth/5);
	    // Detect the change in volume slider bar and sets the MediaPlayer's volume accordingly
	    volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
	        
	    	@Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	    		if (volumeSlider.isValueChanging()) {
	    			// If the slider is being dragged set volume to that requested
	    			mp.setVolume(volumeSlider.getValue() / 100.0);
	            }
	            
	    		// Update the text labels to display
	    		updateValues();
	        }
	    });
	    
	    // Allow the user to click on the slider to jump to the desired volume
	    volumeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	mp.setVolume(volumeSlider.getValue() / 100.0);
            }
        });
	        
	    // Add the volume slider to MediaControl bar
	    mediaBar.getChildren().add(volumeSlider);
	}
	
	/*
	 * Creates a time slider, adds the relevant event handlers for it, and adds it to
	 * the appropiate MediaControl bar. Handles both normal viewing mode, and fullscreen
	 * mode.
	 */
	void setTimeSlider() {
		// Create a new time slide
        timeSlider = new Slider();
        timeSlider.getStylesheets().add("file:../Resources/css.css");
        timeSlider.setMaxWidth((3*mpWidth)/5);
        HBox.setHgrow(timeSlider, Priority.ALWAYS);
        
        // Allow the user to drag and position the slider
        timeSlider.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
            	// If the value is changing perform an action
                if (timeSlider.isValueChanging()) {
                	// If the duration of video is not null, move to requested time
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
        
        // Create a new time slider for fullscreen mode
        timeSliderFS = new Slider();
        timeSliderFS.getStylesheets().add("file:../Resources/css.css");
    	timeSliderFS.setPrefWidth(bounds.getWidth()-90);
    	timeSliderFS.valueProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable ov) {
            	// If the value is changing perform an action
                if (timeSliderFS.isValueChanging()) {
                	// If the duration of video is not null, move to requested time
                    if (duration != null) {
                    	mp.seek(duration.multiply(timeSliderFS.getValue()/ 100.0));
                    }
                }
            }
        });
        
    	// If mouse enters time slider in fullscreen mode handle it
        timeSliderFS.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	// Stop the fade transition and move to requested time
            	fadeTransition.stop();
            	mp.seek(duration.multiply(timeSliderFS.getValue()/ 100.0));
            }
        });
        
        // Add the time slider to MediaControl bar
        mediaBar.getChildren().add(timeSlider);
	}
	
	/*
	 * Function that creates a fullscreen button, adds the relevant event handlers, and
	 * adds it to the MediaControl bar. Also handles nice fade transitions when the mouse
	 * is moved/clicked.
	 */
	void setFullScreenButton() {
		// Create a fullscreen button
		Button fullscreenButton = new Button();
		fullscreenButton.setId("fullscreenButton");
		fullscreenButton.getStylesheets().add("file:../Resources/css.css");
        fullscreenButton.setGraphic(new ImageView(fullscreenImage));
        fullscreenButton.setMinWidth(mpWidth/25);
        
        // Create a new stage for the fullscreen mode
        stageFS = new Stage();
        fullscreenButton.setOnAction(new EventHandler<ActionEvent>() {
        
        	// ActionHandler for play button.
            public void handle(ActionEvent e) {
            	// Hide the current MediaView object
            	mediaView.setVisible(false);
            	
            	// Create a new group in the active window
            	Node  source = (Node)  e.getSource();
            	stage  = (Stage) source.getScene().getWindow();
            	final Group root = new Group();
            	
            	// Create a new MediaView based on the same Media settings
            	MediaView mediaViewFS = new MediaView(mp);
            	mediaViewFS.setPreserveRatio(false);
            	mediaViewFS.setFitWidth(bounds.getWidth());
            	mediaViewFS.setFitHeight(bounds.getHeight()+35);
            	//mediaViewFS.setLayoutY((bounds.getHeight() - mediaViewFS.getFitHeight())/7);
            
            	// Add the new MediaView to the new group
            	root.getChildren().add(mediaViewFS);
            	
            	// Add the Play button for fullscreen and the Slider bar
            	root.getChildren().add(fullscreenMediaBar);

            	// Create a new scene for fullscreen mode
            	final Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
  
            	// Set up the stage for fullscreen mode
            	stageFS.setScene(scene);
            	stageFS.setFullScreen(true);
            	stageFS.show();
            	
            	// Toggle Play and Pause when the user click on the window
                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mp.getStatus() == Status.PLAYING){
                        	// If already playing pause the video
                        	mp.pause();
                        } else {
                        	// If already paused play the video
                        	mp.play();
                        }
                    }
                });
                
                // Animation for the Control Panel in fullscreen mode
                fadeTransition = new FadeTransition(Duration.millis(3000), fullscreenMediaBar);
                fadeTransition.setFromValue(1.0);
                fadeTransition.setToValue(0.0);
                
                // Play the fade transition if the mouse is moved on the screen & show control bar
                scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
                	@Override
    	            public void handle(MouseEvent mouseEvent){
                		fullscreenMediaBar.setDisable(false);
                		scene.setCursor(Cursor.DEFAULT);
                		fadeTransition.play();
    	            }
    	        });
                
                // Hide the control bar when fade transition finishes
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    	fullscreenMediaBar.setDisable(true);
                        scene.setCursor(Cursor.NONE);
                    }
                }); 
                
                // If the mouse moves into the timeslider bar stop the fade transition
                timeSliderFS.setOnMouseEntered(new EventHandler<MouseEvent>(){
                	@Override
    	            public void handle(MouseEvent mouseEvent){
                		fadeTransition.stop();
    	            }
    	        });
                
                // If the mouse moves into the timeslider bar stop the fade transition
                playButtonFS.setOnMouseEntered(new EventHandler<MouseEvent>(){
                	@Override
    	            public void handle(MouseEvent mouseEvent){
                		fadeTransition.stop();
    	            }
    	        });
                
                // Exit Fullscreen mode and return to the main Window is ESC key pressed
                stageFS.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
        		    @Override
        		    public void handle(KeyEvent event){
        		    	// Check for ESC key only!
        			    if(event.getCode() == KeyCode.ESCAPE){
        			    	// Close fullscreen mode and return to previous view
        	            	stageFS.close();
        	            	mediaView.setVisible(true);
        	            	stage.setFullScreen(true);
        	            	stage.getScene().setCursor(Cursor.DEFAULT);
        	            	stage.show();
        			    }	                                 
        		    }
                });                  
            }
        });
     	
        // Add the full screen button to the MediaControl bar
        mediaBar.getChildren().add(fullscreenButton);
	}
	
	/*
	 *  Function that creates a stop button, adds the relevant event handlers for it,
	 *  and adds the button to the MediaControl bar.
	 */
	void setStopButton() {
		// Create a stop button
		stopButton = new Button();
		stopButton.setId("stopButton");
		stopButton.setGraphic(new ImageView(stopImage));
		stopButton.getStylesheets().add("file:../Resources/css.css");
        stopButton.setMinWidth(mpWidth/25);
        stopButton.setOnAction(new EventHandler<ActionEvent>() {

        	// ActionHandler for play button.
            public void handle(ActionEvent e) {
            	// Media is stopped so set the play image for the "stop" button
                mp.stop();
                atEndOfMedia = true;
                playButton.setGraphic(new ImageView(playImage));
                playButtonFS.setGraphic(new ImageView(playImage));
            }
        });
        
        // Add the stop button to the MediaControl bar
        mediaBar.getChildren().add(stopButton);
	}
	
	/*
	 * Function that creates a play button, adds the relevant event handlers for it,
	 * and adds it to the appropiate media control bar. Done for both normal view mode
	 * and fullscreen mode.
	 */
	void setPlayButton() {
		// Create a play button.
        playButton = new Button();
        playButton.setId("playButton");
        playButton.setGraphic(new ImageView(playImage));
        playButton.getStylesheets().add("file:../Resources/css.css");
        playButton.setMinWidth(mpWidth/25);
        playButton.setOnAction(new EventHandler<ActionEvent>() {

	        // ActionHandler for play button.
	        public void handle(ActionEvent e) {
	            status = mp.getStatus();
	
	            // Check for bad status's.
	            if (status == Status.UNKNOWN || status == Status.HALTED) {
	                System.out.println("Player is in a bad or unknown state, can't play.");
	                return;
	            }
	            
	            // Check for accepted status's.
	            if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
	                // Rewind the video if it's at the end
	                if (atEndOfMedia) {
	                    mp.seek(mp.getStartTime());
	                    atEndOfMedia = false;
	                    playButton.setGraphic(new ImageView(playImage));
	                    updateValues();
	                }
	                // Set video to play again and set pause image for button
	                mp.play();
	                playButton.setGraphic(new ImageView(pauseImage));
	            } else {
	            	// Pause the media and set play image for button
	                mp.pause();
	                playButton.setGraphic(new ImageView(playImage));
	            }
	        }
        });
	    
        // Play/Pause Button in fullscreen mode
        playButtonFS = new Button();
        playButtonFS.setId("playButtonFS");
        playButtonFS.setGraphic(new ImageView(playImage));
        playButtonFS.getStylesheets().add("file:../Resources/css.css");
        playButtonFS.setMinWidth(mpWidth/25);
        playButtonFS.setOnAction(new EventHandler<ActionEvent>() {

        	// ActionHandler for play button in fullscreen mode.
            public void handle(ActionEvent e) {
                status = mp.getStatus();
                
                // Check for bad status's
                if (status == Status.UNKNOWN || status == Status.HALTED) {
                    System.out.println("Player is in a bad or unknown state, can't play.");
                    return;
                }

                // Check for acceptable status's
                if (status == Status.PAUSED || status == Status.READY || status == Status.STOPPED) {
                    // rewind the movie if we're sitting at the end
                    if (atEndOfMedia) {
                        mp.seek(mp.getStartTime());
                        atEndOfMedia = false;
                        playButtonFS.setGraphic(new ImageView(playImage));
                        updateValues();
                    }
                 // Set video to play again and set pause image for button
                    mp.play();
                    playButtonFS.setGraphic(new ImageView(pauseImage));
                } else {
                	// Pause the media and set play image for button
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
        
        // Media is playing so set appropiate playButton image
        mp.setOnPlaying(new Runnable() {

            public void run() {
                if (stopRequested) {
                	// If a media stop has been requested in the meantime, pause the media.
                    mp.pause();
                    stopRequested = false;
                } else {
                	// Otherwise set the pause image for the "play" button
                	playButton.setGraphic(new ImageView(pauseImage));
                	playButtonFS.setGraphic(new ImageView(pauseImage));
                }
            }
        });
        
        // Media is paused so set appropiate playButton image
        mp.setOnPaused(new Runnable() {
        	
            public void run() {
            	// Set the play image for the "play" button
            	playButton.setGraphic(new ImageView(playImage));
            	playButtonFS.setGraphic(new ImageView(playImage));
            }
        });
    
        // Media is stopped so set appropiate playButton image
        mp.setOnStopped(new Runnable() {
        	 
        	public void run() {
        		// Media has stopped so display the play image for the "play" button
        		//mp.setStopTime(Duration.INDEFINITE);
        		atEndOfMedia = true; 
	            playButton.setGraphic(new ImageView(playImage));
	            playButtonFS.setGraphic(new ImageView(playImage));
	       }
        });
        
        // Media is ready so get the total duration of the Media and update the Time Label
        mp.setOnReady(new Runnable() {
        	
            public void run() {
            	if(playDuration != null && playDuration != 0){
            		duration = Duration.seconds(playDuration);
            	}
            	else{
            		duration = mp.getMedia().getDuration();
            	}
                updateValues();
            }
        });
        
        // Media has finished playing so set appropiate playButton image & handle loops
        mp.setOnEndOfMedia(new Runnable() {

            public void run() {
            	if (!mpLoop){ 
            		// If loop not set then stop media and display play image for "play" button
                	playButton.setGraphic(new ImageView(playImage));
                    playButtonFS.setGraphic(new ImageView(playImage));
                    atEndOfMedia = true;
                    mp.stop();
                }
            	
            	// Otherwise go back to the requested start time of the media
            	mp.seek(mp.getStartTime());
            }
        });
        
        // Media is repeating so set appropiate playButton image
        mp.setOnRepeat(new Runnable() {
        	 
        	public void run() {
        		// Display the pause image for the "play" button
        		atEndOfMedia = false;
        		playButton.setGraphic(new ImageView(pauseImage));
                playButtonFS.setGraphic(new ImageView(pauseImage));         
            }
        });

        // Add the play button to the MediaControl bar.
        mediaBar.getChildren().add(playButton);
	}
	
	/*
	 * Function that sets the appropriate values for the Time label, position of the Time 
	 * Slider and Volume 
	 */ 
    protected void updateValues() {
        if (playTime != null && timeSlider != null && volumeSlider != null)  {
        	// If the video hasn't been played and slider values aren't null, spawn a new thread
            Platform.runLater(new Runnable() {

                public void run() {
                	// set the current play time
                    Duration currentTime = mp.getCurrentTime();
                    
                    if(playDuration != null && playDuration != 0){
                    	 playTime.setText(formatTime(currentTime, Duration.seconds(playDuration)));
   	                     playTimeFS.setText(formatTime(currentTime, Duration.seconds(playDuration)));      	
                    }
                    else{
                    	// Set the text for the time sliders
	                    playTime.setText(formatTime(currentTime, mp.getMedia().getDuration()));
	                    playTimeFS.setText(formatTime(currentTime, mp.getMedia().getDuration()));
                    }
                    
                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
                        // Set the time slider value if we're in normal view mode
                    	timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
                    }
                   
                    if (!timeSliderFS.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSliderFS.isValueChanging()) {
                        // Set the time slider value when we're in fullscreen mode
                    	timeSliderFS.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
                    }
                    
                    if (!volumeSlider.isValueChanging()) {
                    	// If the volume slider value isn't chnaging display its current value
                        volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
                    }
                }
            });
        }
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
                return String.format("%d:%02d:%02d/%d:%02d:%02d", elapsedHours, elapsedMinutes, elapsedSeconds, durationHours, durationMinutes, durationSeconds);
            } else {
                return String.format("%02d:%02d/%02d:%02d", elapsedMinutes, elapsedSeconds, durationMinutes, durationSeconds);
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
    
    
    /*
     * Thread to play the MediaPlayer for duration amount of time before stopping the 
     * MediaPlayer (not in use at the moment)
     * 
     * @return null
     */ 
	 protected Task<Object> durationTimerThread = new Task<Object>() {
		
		 @Override
		protected Object call() throws Exception {
			int count=0;
			
			// While the play duration hasn't yet expired sleep for a second
			while (count <= playDuration) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
					count++;
				}
					 
				// Spawn a new thread to handle looping the video
				Platform.runLater( new Runnable(){
					public void run(){
						mp.stop();
						if(mpLoop){
							mp.play();
						}
					}
				});	 
				
				// Return null to keep Java happy.
				return null;
			}
		};
	
	/* 
	 * Function to set the cycle count of the MediaPlayer
	 * 
	 * @param loop Whether the media should loop or not
	 */
	public void setLoop(boolean loop) {
		if (loop){
			// Set to play indefinitely if loop is true
			mp.setCycleCount(MediaPlayer.INDEFINITE);
		} else {
			// Otherwise only play it once
			mp.setCycleCount(1);
		}
	}
	
	/*
	 * Set the continuePlaying variable to tell the MediaPlayer object whether the 
	 * media should start playing after the startTime duration has elapsed. Set to false 
	 * to prevent from playing
	 * 
	 * @param newState Condition for the media continuing to play or not
	 */
	public void setContinuePlaying(boolean newState) {
		continuePlaying = newState;
	}
	
	/*
	  * Adds 1 second keyframes to timeLineStart 
	  */
	 private void createKeyFrame(){
			
			timeLineStart.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					startTime--;	
				}
			} ));
				
	}
	 
	 //Pauses the start time timeline (does not pause audio already playing)
	 public void pauseStartTime(){
		 timeLineStart.pause();
		
	 }
	 // Resumes the start time timeline (does not pause audio already playing)
	 public void resumeStartTime(){
		 if(timeLineStart.getStatus() != stopped){
			 timeLineStart.play();
		 }
	 }
	 
	 public void stopStartTime() {
		 timeLineStart.stop();
			
		}
}
