package eCook;
/* Title: SlideControls
 * 
 * Programmers: Max, Ankita, Steve Thorpe, Paul Mathema and James Oatley
 * 
 * Date Created: 09/05/14
 * 
 * Description: Control panel GUI and appear/disappear logic for slideshow.
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
import javafx.scene.control.Tooltip;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

public class SlideControls {
	
	// declare variables
	boolean bottomPanelVisible = false;
	private Rectangle2D screenBounds;
	private double width;
    HBox controlPanel;
    VBox bottomPanel;
	List<Button> buttons;
	public Timeline timelineIn, timelineOut;
	private boolean forceShow = false;
	double panelHeight;
	
	// constructor
	public SlideControls(Group root, boolean forceShow, HBox audioBar) {
		this.forceShow = forceShow;
		
		// set the height of the panel (make room for audio bar if neccessary)
		if (audioBar == null) {
			panelHeight = root.getScene().getHeight()/8;
		}
		else {
			panelHeight = root.getScene().getHeight()/5;
		}
		
		// setup; and show if already visible
		setupcontrolPanel(root, audioBar);
		if (forceShow) {
			showPanel(root);
		}
	}
	
	// method to return the bottom panel VBox object
	public VBox getBottomPanel() {
		return bottomPanel;
	}

	// method to return the controlPanel HBox object
	public HBox getControlBar() {
		return controlPanel;
	}
	
	// method to indicate when the bottom panel is onscreen
	public boolean getBottomPanelVisible() {
		return bottomPanelVisible;
	}

	// method to access the list of buttons in the controlsBox
	public List<Button> getButtons() {
		return buttons;
	}
	
	// set up the controls panel and add the buttons
    public void setupcontrolPanel(final Group root, HBox audioBar) {   
        // get the size of the screen
		screenBounds = Screen.getPrimary().getBounds();
		width =  screenBounds.getWidth();
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getBounds();
        
        // set up the bottom panel VBox
        bottomPanel = new VBox();
        bottomPanel.setPrefSize(root.getScene().getWidth(), panelHeight);
        bottomPanel.setStyle("-fx-background-color: rgba(255,255,255,0.9);");
        
        // Set up the controls panel at the bottom of the screen
        controlPanel = new HBox();
        controlPanel.setPrefSize(root.getScene().getWidth(), root.getScene().getHeight()/8);
        controlPanel.setBackground(Background.EMPTY);
        controlPanel.setPadding(new Insets(30,10,10,2));
        controlPanel.setSpacing(5);
        controlPanel.setAlignment(Pos.TOP_CENTER);
        
        // instantiate and add the buttons to the list
        buttons = new ArrayList<Button>();
        Button pauseBtn = new Button("");
        Button prevBtn = new Button("");
        Button nextBtn = new Button("");
        Button exitBtn = new Button("");
        Button timerBtn = new Button("");
        Button firstBtn = new Button ("");
        Button lastBtn = new Button ("");  
        
        //Defining the CSS identifier 
        pauseBtn.setId("SlidePauseBtn");
        prevBtn.setId("SlidePrevBtn");
        nextBtn.setId("SlideNextBtn");
        exitBtn.setId("SlideExitBtn");
        timerBtn.setId("SlideTimeBtn");
        firstBtn.setId("SlideFirstBtn");
        lastBtn.setId("SlideLastBtn");
        
        //Adding button size
        pauseBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        prevBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        nextBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        exitBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        timerBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        firstBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        lastBtn.setMinSize(width/7,controlPanel.getPrefHeight()-40);
        
        //Set padding to button
        pauseBtn.setPadding(new Insets(30,10,10,30));
        prevBtn.setPadding(new Insets(30,10,10,30));
        nextBtn.setPadding(new Insets(30,10,10,30));
        exitBtn.setPadding(new Insets(30,10,10,30));
        timerBtn.setPadding(new Insets(30,10,10,30));
        firstBtn.setPadding(new Insets(30,10,10,30));
        lastBtn.setPadding(new Insets(30,10,10,30));
        
        //Adding stylesheet
        pauseBtn.getStylesheets().add("css.css");
        prevBtn.getStylesheets().add("css.css");
        nextBtn.getStylesheets().add("css.css");
        exitBtn.getStylesheets().add("css.css");
        timerBtn.getStylesheets().add("css.css");
        firstBtn.getStylesheets().add("css.css");
        lastBtn.getStylesheets().add("css.css");
        
        //Adding button
        buttons.add(firstBtn);
        buttons.add(prevBtn);
        buttons.add(pauseBtn);
        buttons.add(nextBtn);
        buttons.add(lastBtn);
        buttons.add(timerBtn);
        buttons.add(exitBtn);
        
        //Add Tooltips
        firstBtn.setTooltip(new Tooltip("Click here to the first slide"));
        prevBtn.setTooltip(new Tooltip("Click here to go to the previous slide"));
        pauseBtn.setTooltip(new Tooltip("Click here to pause slide"));
        nextBtn.setTooltip(new Tooltip("Click here to go to next slide"));
        lastBtn.setTooltip(new Tooltip("Click here to go to last slide"));
        timerBtn.setTooltip(new Tooltip("Click here to add a timer"));
        exitBtn.setTooltip(new Tooltip("Click here to the exit to main menu"));
    
        
        // add the buttons to the panel
        controlPanel.getChildren().addAll(buttons);
        
        // add the panel to the bottom panel and audio panel if required
        bottomPanel.getChildren().add(controlPanel);
        if (audioBar != null) {
        	bottomPanel.getChildren().add(audioBar);
        }
        
        timelineIn = new Timeline();
        timelineOut = new Timeline();
        
        // Define an event handler to trigger when the mouse leaves the controls panel
        final EventHandler<InputEvent> mouseoutcontrolPanelHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
            	if (bottomPanelVisible) {
            		// hide panel
            		hidePanel(root);
            	}
            	event.consume();
            }			
        };
        
        bottomPanel.setOnMouseExited(new EventHandler<MouseEvent>(){
          	@Override
              public void handle(MouseEvent mouseEvent){
          		timelineIn.play();
              }
         });

        // Define an event handler to trigger when the user moves the mouse
        final EventHandler<InputEvent> mouseoverBottomHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {            	
            	// check the position of the mouse
            	Point mousePosition = MouseInfo.getPointerInfo().getLocation();           	
            	// if the mouse is at the bottom of the screen, show the notes panel
            	if (mousePosition.getY() >= (primaryScreenBounds.getHeight() - 10)) {
            		if (!bottomPanelVisible) {
            			// show panel
            			showPanel(root);
            		}
            	}
                event.consume();     
            }
        };
        
        // add the mouselistener
        bottomPanel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutcontrolPanelHandler);
        
        // check to see if the mouse is at the bottom of the screen every time it is moved
        root.getScene().addEventHandler(MouseEvent.MOUSE_MOVED, mouseoverBottomHandler);
    }
    
    // method to hide panel
    public void hidePanel(Group root) {
		final KeyValue kv = new KeyValue(bottomPanel.translateYProperty(), panelHeight);
		final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
		timelineIn.getKeyFrames().add(kf);
		timelineIn.stop();
      	bottomPanelVisible = false;
    }
    
    // method to show panel
    public void showPanel(Group root) {
    	// normal transition on mouseover
    	if (!forceShow) {
			final KeyValue kv = new KeyValue(bottomPanel.translateYProperty(), -panelHeight);
			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
			timelineOut.getKeyFrames().add(kf);
			timelineOut.play();
    	}
    	// no transition on new slide (if panel already open)
    	else {
    		bottomPanel.setTranslateY(-panelHeight);
    		forceShow = false;
    	}
    	bottomPanelVisible = true;
    }
}