/*
 * Programmer: Max
 * Date Created: 05/06/14
 * Description: Audio Control Bar to sit under the control buttons in the slideshow control panel.
 */
package eCook;

import java.util.ArrayList;
import java.util.List;
import media.AudioHandler;
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
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class AudioControlBar {
	HBox controlBar;
	List<Button> buttons;
	Slider trackBar, volBar;
	Label fileLbl, timeLbl;
	AudioHandler currentHandler;
	int currentHandlerIndex = 0;
	List<AudioHandler> audioHandlerObjects;
	
	// constructor
	public AudioControlBar(ArrayList<AudioHandler> audioHandlerList, Group root) {
		this.audioHandlerObjects = audioHandlerList;
		currentHandler = audioHandlerList.get(currentHandlerIndex);
		setupControlBar(root);
		setupButtons();
		setupSliders();
		writeLabels();
	}
	
	// method to get the controlBar HBox
	public HBox getControlBar() {
		if (controlBar == null) {
			System.out.println("audioControlBar has not been set yet! Will return null.");
		}
		return this.controlBar;
	}
	
	// method to populate the controlBar with buttons
	public void setupControlBar(Group root) {
		controlBar = new HBox();
        controlBar.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight()/10);
        controlBar.setAlignment(Pos.CENTER);
		
		// declare buttons and set up images
		buttons = new ArrayList<Button>();
		Button playPauseBtn = new Button("play");
		ImageView playImg = new ImageView(new Image("audioBarPlay.png"));
		playPauseBtn.setGraphic(playImg);
        Button prevBtn = new Button("prev");
        prevBtn.setDisable(true);
        Button nextBtn = new Button("next");
        if (audioHandlerObjects.size() == 1) {
        	nextBtn.setDisable(true);
        }
        Button stopBtn = new Button ("stop");
        buttons.add(playPauseBtn);
        buttons.add(stopBtn);
        buttons.add(prevBtn);
        buttons.add(nextBtn);
		
        // set up sliders
        trackBar = new Slider();
        trackBar.setMin(0);
        trackBar.setMax(currentHandler.getDuration());
        //trackBar.setValue(0);
        volBar = new Slider();
        volBar.setMin(0);
        volBar.setMax(1.0);
        volBar.setValue(1.0);
        
        // set up filename and time labels
        fileLbl = new Label(currentHandler.getFilePath());
        fileLbl.setStyle("-fx-text-fill: black;");
        timeLbl = new Label("00:00/00:00");
        timeLbl.setStyle("-fx-text-fill: black;");
        
        // populate the controlBar
        controlBar.getChildren().addAll(playPauseBtn, stopBtn, prevBtn, nextBtn, trackBar, timeLbl, fileLbl, volBar);
	}
	
	// set up event handlers for the buttons
	public void setupButtons() {
		// play/pause button
		buttons.get(0).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// toggle play/pause
				if (buttons.get(0).getText().equals("play")) {
					currentHandler.resumeMedia();
					buttons.get(0).setText("pause");
				}
				else {
					currentHandler.pauseMedia();
					buttons.get(0).setText("play");
				}
			}
		});
		
		// stop button
		buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// call stop
				currentHandler.stopMedia();
				// set the pause button to play if not already
				buttons.get(0).setText("play");
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
					buttons.get(0).setText("play");
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
					buttons.get(0).setText("play");
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
	
	// set up event handlers for sliders
	public void setupSliders() {
		// volume bar
		volBar.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number old_val, Number new_val) {
				currentHandler.setVolume(new_val.doubleValue());
			}
		});
		
		// tracking bar
		trackBar.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable value) {
			}
		});
		
		// Whenever there's a change in duration of the MediaPlayer, update the Time Label and Slider Position
        currentHandler.getMediaPlayer().currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
            	timeLbl.setText(currentHandler.getMediaPlayer().getCurrentTime().toString());
            	trackBar.setValue(currentHandler.getMediaPlayer().getCurrentTime().toMillis());
            }
        });
        trackBar.setMax(currentHandler.getDuration());
        
        
	}
	
	// method to update the button enables to prevent undefined behaviour {
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
		
		// set the slider
		setupSliders();
	}
	
	// method to write information to the labels
	public void writeLabels() {
		fileLbl.setText(currentHandler.getFilePath());
	}
}
