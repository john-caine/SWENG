package eCook;
/* Title: NotesGUI
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 09/05/14
 * 
 * Description: A basic GUI to display user-made notes about a particular recipe slide
 * 				The GUI allows the user to write, read and save notes.
 */

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.util.Duration;

public class SlideControls {
	
	// declare variables
	boolean controlPanelVisible = false;
	private Rectangle2D screenBounds;
	private double width;
    HBox controlPanel;
	List<Button> buttons;
	
	// constructor
	public SlideControls(Group root) {
		setupcontrolPanel(root);

	}

	// method to return the controlPanel HBox object
	public HBox getControlPanel() {
		return controlPanel;
	}
	
	// method to indicate when the controls panel is onscreen
	public boolean getcontrolPanelVisible() {
		return controlPanelVisible;
	}

	// method to access the list of buttons in the controlsBox
	public List<Button> getButtons() {
		return buttons;
	}
	
	// set up the controls panel and add the buttons
    public void setupcontrolPanel(final Group root) {   
        // get the size of the screen
		screenBounds = Screen.getPrimary().getBounds();
		width =  screenBounds.getWidth();
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        
        // Set up the controls panel at the bottom of the screen
        controlPanel = new HBox();
        controlPanel.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight()/8);
        controlPanel.setStyle("-fx-background-color: rgba(255, 117, 25, 0.5)");
        controlPanel.setPadding(new Insets(30,10,10,30));
        controlPanel.setSpacing(50);
        controlPanel.setAlignment(Pos.TOP_CENTER);
        
        // instantiate and add the buttons to the list
        buttons = new ArrayList<Button>();
        Button pauseBtn = new Button("");
        Button prevBtn = new Button("");
        Button nextBtn = new Button("");
        Button exitBtn = new Button("");
        Button timerBtn = new Button("Timer");
             
        //Defining the CSS identifier 
        pauseBtn.setId("SlidePauseBtn");
        prevBtn.setId("SlidePrevBtn");
        nextBtn.setId("SlideNextBtn");
        exitBtn.setId("SlideExitBtn");
        timerBtn.setId("SlideTimeBtn");
        
        //Adding button size
        pauseBtn.setMinSize(width/20,controlPanel.getPrefHeight()-50);
        prevBtn.setMinSize(width/20,controlPanel.getPrefHeight()-50);
        nextBtn.setMinSize(width/20,controlPanel.getPrefHeight()-50);
        exitBtn.setMinSize(width/20,controlPanel.getPrefHeight()-50);
        timerBtn.setMinSize(width/20,controlPanel.getPrefHeight()-50);
        
        //Set padding to button
        pauseBtn.setPadding(new Insets(30,10,10,30));
        prevBtn.setPadding(new Insets(30,10,10,30));
        nextBtn.setPadding(new Insets(30,10,10,30));
        exitBtn.setPadding(new Insets(30,10,10,30));
        timerBtn.setPadding(new Insets(30,10,10,30));
        
        //Adding stylesheet
        pauseBtn.getStylesheets().add("file:../Resources/css.css");
        prevBtn.getStylesheets().add("file:../Resources/css.css");
        nextBtn.getStylesheets().add("file:../Resources/css.css");
        exitBtn.getStylesheets().add("file:../Resources/css.css");
        timerBtn.getStylesheets().add("file:../Resources/css.css");
        
        //Adding button
        buttons.add(pauseBtn);
        buttons.add(prevBtn);
        buttons.add(nextBtn);
        buttons.add(exitBtn);
        buttons.add(timerBtn);        
        
        // add the buttons to the panel
        controlPanel.getChildren().addAll(buttons);
        
        
        // Define an event handler to trigger when the mouse leaves the controls panel
        final EventHandler<InputEvent> mouseoutcontrolPanelHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
            	if (controlPanelVisible) {
            		// hide panel
            		final Timeline timeline = new Timeline();
        			final KeyValue kv = new KeyValue(controlPanel.translateYProperty(), root.getScene().getHeight()/8);
        			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        			timeline.getKeyFrames().add(kf);
        			timeline.play();
                  	controlPanelVisible = false;
                  	controlPanel.setDisable(true);
            	}
            	event.consume();
            }			
        };

        // Define an event handler to trigger when the user moves the mouse
        final EventHandler<InputEvent> mouseoverBottomHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {            	
            	// check the position of the mouse
            	Point mousePosition = MouseInfo.getPointerInfo().getLocation();           	
            	// if the mouse is at the bottom of the screen, show the notes panel
            	if (mousePosition.getY() >= (primaryScreenBounds.getHeight() - 10)) {
            		// add the mouselistener
                    controlPanel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutcontrolPanelHandler);

            		if (!controlPanelVisible) {
            			// show panel
            			final Timeline timeline = new Timeline();
            			final KeyValue kv = new KeyValue(controlPanel.translateYProperty(), -root.getScene().getHeight()/8);
            			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
            			timeline.getKeyFrames().add(kf);
            			timeline.play();
                    	controlPanelVisible = true;
                    	controlPanel.setDisable(false);
            		}
            	}
                event.consume();     
            }
        };
        
     // check to see if the mouse is at the bottom of the screen every time it is moved
        root.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, mouseoverBottomHandler);
    }
}