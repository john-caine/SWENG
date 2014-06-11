/*
 * Programmer: Max
 * Date Created: 05/06/14
 * Description: Audio Control Bar to sit under the control buttons in the slideshow control panel.
 */
package media;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AudioControlBar {
	// declare variables
	HBox controlBar;
	List<Button> buttons;
	Slider trackBar, volBar;
	Label fileLbl, timeLbl;
	AudioHandler currentHandler;
	int currentHandlerIndex = 0;
	List<AudioHandler> audioHandlerObjects;
	private Logger logger;
	
	// constructor
	public AudioControlBar(final ArrayList<AudioHandler> audioHandlerList, Group root) {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		this.audioHandlerObjects = audioHandlerList;
		currentHandler = audioHandlerList.get(currentHandlerIndex);
		setupControlBar(root);
		setupButtons();
		setupSliders();
		writeLabels();
		detectAutoPlay();
	}
	
	/*
	 *  method to get the controlBar HBox
	 */
	public HBox getControlBar() {
		if (controlBar == null) {
			logger.log(Level.WARNING, "audioControlBar has not been set yet! Will return null.");
		}
		return this.controlBar;
	}
	
	/*
	 *  method to populate the controlBar with buttons
	 */
	public void setupControlBar(Group root) {
		controlBar = new HBox();
        controlBar.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight()/10);
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setSpacing(10);
		
		// declare buttons and set up images
		buttons = new ArrayList<Button>();
		Button playPauseBtn = new Button();
		playPauseBtn.setId("audioBarPlay");
		ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
		playPauseBtn.setGraphic(playImg);
        Button prevBtn = new Button();
        ImageView prevImg = new ImageView(new Image("audioBarPrev.png"));
		prevBtn.setGraphic(prevImg);
        prevBtn.setDisable(true);
        Button nextBtn = new Button();
        ImageView nextImg = new ImageView(new Image("audioBarNext.png"));
		nextBtn.setGraphic(nextImg);
        if (audioHandlerObjects.size() == 1) {
        	nextBtn.setDisable(true);
        }
        Button stopBtn = new Button();
        ImageView stopImg = new ImageView(new Image("audioBarStop.png"));
		stopBtn.setGraphic(stopImg);
        buttons.add(playPauseBtn);
        buttons.add(stopBtn);
        buttons.add(prevBtn);
        buttons.add(nextBtn);
		
        // set up sliders
        trackBar = new Slider();
        trackBar.setMin(0);
        trackBar.setMax(currentHandler.getDuration());
        trackBar.setPrefWidth(root.getScene().getWidth()/3);
        
        volBar = new Slider();
        volBar.setMin(0);
        volBar.setMax(1.0);
        volBar.setValue(1.0);
        
        // set up filename and time labels
        fileLbl = new Label(currentHandler.getFilePath());
        fileLbl.setStyle("-fx-text-fill: black;");
        timeLbl = new Label("00:00/00:00");
        timeLbl.setStyle("-fx-text-fill: black;");
        Label volLbl = new Label();
        ImageView volImg = new ImageView(new Image("audioBarVol.png"));
		volLbl.setGraphic(volImg);
        
        
        // populate the controlBar
        controlBar.getChildren().addAll(playPauseBtn, stopBtn, prevBtn, nextBtn, trackBar, timeLbl, fileLbl, volLbl, volBar);
	}
	
	/*
	 *  set up event handlers for the buttons
	 */
	public void setupButtons() {
		// play/pause button
		buttons.get(0).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// toggle play/pause
				if (buttons.get(0).getId().equals("audioBarPlay")) {
					currentHandler.resumeMedia();
					buttons.get(0).setId("audioBarPause");
					ImageView pauseImg = new ImageView(new Image("audioBarPause.png"));
					buttons.get(0).setGraphic(pauseImg);
				}
				else {
					currentHandler.pauseMedia();
					buttons.get(0).setId("audioBarPlay");
					ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
					buttons.get(0).setGraphic(playImg);
				}
				
				detectAutoPlay();
			}
		});
		
		// stop button
		buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// call stop
				currentHandler.stopMedia();
				// set the pause button to play if not already
				buttons.get(0).setId("audioBarPlay");
				ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
				buttons.get(0).setGraphic(playImg);
			}
		});
		
		// previous button
		buttons.get(2).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// set current handler to previous instance unless currently on the first
				if (currentHandlerIndex != 0) {
					// stop anything playing first
					currentHandler.stopMedia();
					buttons.get(0).setId("audioBarPlay");
					ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
					buttons.get(0).setGraphic(playImg);
					currentHandler = audioHandlerObjects.get(currentHandlerIndex-1);
					currentHandlerIndex--;
					// validate buttons
					validateButtons();
					// update label text
					writeLabels();
				}
			}
		});
		
		// next button
		buttons.get(3).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// set current handler to next instance unless currently on the last
				if (currentHandlerIndex != audioHandlerObjects.size()-1) {
					// stop anything playing first
					currentHandler.stopMedia();
					buttons.get(0).setId("audioBarPlay");
					ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
					buttons.get(0).setGraphic(playImg);
					currentHandler = audioHandlerObjects.get(currentHandlerIndex+1);
					currentHandlerIndex++;
					// validate buttons
					validateButtons();
					// update label text
					writeLabels();
				}
			}
		});
	}
	
	/*
	 *  set up event handlers for sliders
	 */
	public void setupSliders() {
		/* volume bar */
		volBar.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				currentHandler.setVolume(new_val.doubleValue());
			}
		});
		
		/* tracking bar */
		// Allow the user to drag and position the slider
		trackBar.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable value) {
				// If the value is changing perform an action
                if (trackBar.isValueChanging()) {
                	// If the duration of the audio file is not zero, move to requested time
                    if (currentHandler.getDuration() != 0) {
                    	Duration seekPos = new Duration(trackBar.getValue());
                    	currentHandler.getMediaPlayer().seek(seekPos);
                    }
                }
			}
		});
        
        // Allow the user to click on the slider to jump to the desired timing
        trackBar.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
            	Duration newPos = new Duration(trackBar.getValue());
                currentHandler.getMediaPlayer().seek(newPos);
            }
        });
		
		// Whenever there's a change in duration of the MediaPlayer, update the Time Label and Slider Position
        currentHandler.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
            	// set up the numbers for the time display label
            	Integer currentTimeSeconds = (int) currentHandler.getMediaPlayer().getCurrentTime().toSeconds();
            	Integer currentTimeMinutes = (int) currentHandler.getMediaPlayer().getCurrentTime().toMinutes();
            	String currentTimeString = currentTimeMinutes.toString() + ":" + currentTimeSeconds.toString();
            	Double durationMilliseconds = currentHandler.getDuration();
            	Integer durationMinutes = 0;
            	// split up the milliseconds into minutes and seconds
            	if (durationMilliseconds >= 60000) {
            		durationMinutes = (int) (durationMilliseconds/60000);
            	}
            	String durationMinutesString = durationMinutes.toString();
            	Double durationSeconds = (durationMilliseconds - durationMinutes*60000);
            	String durationSecondsString = durationSeconds.toString().substring(0, 2);
            	
            	String durationString = durationMinutesString + ":" + durationSecondsString;
            	timeLbl.setText(currentTimeString + "/" + durationString);
            	trackBar.setValue(currentHandler.getMediaPlayer().getCurrentTime().toMillis());
            	
            	// fix a bug with the SlideMediaPlayer getDuration method
            	if (Double.valueOf(trackBar.getMax()).toString() == "NaN") {
            		trackBar.setMax(currentHandler.getDuration());
            	}
            }
        });
        trackBar.setMax(currentHandler.getDuration());
	}
	
	/*
	 *  method to update the button enables to prevent undefined behaviour {
	 */
	public void validateButtons() {
		// enable the next button if necessary
		if ((currentHandlerIndex < audioHandlerObjects.size()-1) && (audioHandlerObjects.size() != 1)) {
			buttons.get(3).setDisable(false);
		}
		else {
			buttons.get(3).setDisable(true);
		}
		// enable the prev button if necessary
		if ((currentHandlerIndex > 0) && (audioHandlerObjects.size() != 1)) {
			buttons.get(2).setDisable(false);
		}
		else {
			buttons.get(2).setDisable(true);
		}
		
		// update the sliders
		setupSliders();
	}
	
	/*
	 *  method to write information to the labels
	 */
	public void writeLabels() {
		File audioFile = new File(currentHandler.getFilePath());
		if (audioFile.exists()) {
			fileLbl.setText(audioFile.getName());
		}
		else {
			String fileName = currentHandler.getFilePath().substring(currentHandler.getFilePath().lastIndexOf('/')+1, currentHandler.getFilePath().length() );
			String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
			fileLbl.setText(fileNameWithoutExtn);
		}
	}
	
	/*
	 *  method to detect when audio is playing to update the GUI
	 */
	public void detectAutoPlay() {
		// set up an action listener for the current handler to detect if the audio plays automatically
        currentHandler.getMediaPlayer().setOnPlaying(new Runnable() {
			@Override
			public void run() {
				// set play button to pause
				buttons.get(0).setId("audioBarPause");
				ImageView pauseImg = new ImageView(new Image("audioBarPause.png"));
				buttons.get(0).setGraphic(pauseImg);
			}
        });
        
        // do the opposite for paused or stopped
        currentHandler.getMediaPlayer().setOnPaused(new Runnable() {
			@Override
			public void run() {
				// set pause button to play
				buttons.get(0).setId("audioBarPlay");
				ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
				buttons.get(0).setGraphic(playImg);
			}
        });
        currentHandler.getMediaPlayer().setOnStopped(new Runnable() {
			@Override
			public void run() {
				// set pause button to play
				buttons.get(0).setId("audioBarPlay");
				ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
				buttons.get(0).setGraphic(playImg);
			}
        });
        
        currentHandler.getMediaPlayer().setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				// stop
				currentHandler.getMediaPlayer().stop();
				// set pause button to play
				buttons.get(0).setId("audioBarPlay");
				ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
				buttons.get(0).setGraphic(playImg);
				// update slider
				currentHandler.getMediaPlayer().seek(new Duration(0));
			}
        });
	}
}
