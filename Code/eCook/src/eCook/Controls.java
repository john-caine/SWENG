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

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Controls {
	
	// declare variables
	boolean controlPanelVisible = false;
	
	
	private Button nextButton;
	private Button exitButton;
	private HBox controlPanel;
	private Button playButton;
	private Button pauseButton;
	private Button previousButton;
	private Rectangle2D primaryScreenBounds;
	
	// constructor
	public Controls(Integer slideID, Group root) {
		setupcontrolPanel(slideID, root);
	}
	
	// method to return the controlPanel VBox object
	public HBox getControlPanel() {
		return controlPanel;
	}
	
	// method to indicate when the notes panel is onscreen
	public boolean getcontrolPanelVisible() {
		return controlPanelVisible;
	}
	
	// method to access the content of the notesBox
	
	public void playButton() {    
	       
        // ADD PlayButton to the Panel
        
           playButton = new Button();
           playButton.setText("'Play'");
        // Add playbutton's event handler and listener   
           controlPanel.getChildren().add(playButton);
                   playButton.setOnAction(new EventHandler<ActionEvent>() {
                       public void handle(ActionEvent e) {
                           
                  }
              });
    }

  
    public void pauseButton() {         
         // Add PauseButton to the Panel  	
            pauseButton = new Button();
            pauseButton.setText("'Pause'");
         // Add pausebutton's event handler and listener      
            controlPanel.getChildren().add(pauseButton);
                    
               
    }
             
    public void previousButton() {
         // Add previousButton to the Panel        
            previousButton = new Button();
            previousButton.setText("'Pause'");
         // Add previousbutton's event handler and listener               
            controlPanel.getChildren().add(previousButton);
                   
        }
    
    public void nextButton() {                  
        // Add nextButton to the Panel
           nextButton = new Button();
           nextButton.setText("'Next'");
        // Add nextbutton's event handler and listener                  
           controlPanel.getChildren().add(nextButton);         
    }
    
    public void exitButton() {
                   
        // Add exitButton to the Panel           
            exitButton = new Button();
           exitButton.setText("'Exit'");
        // Add exitbutton's event handler and listener                  
           controlPanel.getChildren().add(exitButton);
                    exitButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                             
                     }
             });
    }


	// set up the example slide and add the notes panel
    public void setupcontrolPanel(final Integer slideID, final Group root) {   
        // get the size of the screen
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        // Set up the notes panel on the LHS of the screen
        controlPanel = new HBox();
        controlPanel.setLayoutY(primaryScreenBounds.getHeight());
        controlPanel.setPrefSize(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight()/5);
        controlPanel.setStyle("-fx-background-color: #336699;");
        
        nextButton();
        previousButton();
        exitButton();
        pauseButton();
        
        // clear the help text when the user begins typing
      
        
        final Label timersLabel = new Label("The timers and other stuff could go here");
        timersLabel.setPadding(new Insets(50,0,0,0));
        
        controlPanel.setPadding(new Insets(10,10,10,10));
        controlPanel.setSpacing(20);
        controlPanel.setAlignment(Pos.TOP_CENTER);
        

        
        // create an instance of the text file handler
        
        
        // Define an event handler to trigger when the mouse leaves the notes panel
        final EventHandler<InputEvent> mouseoutcontrolPanelHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
            	if (controlPanelVisible) {
            		// hide panel
            		final Timeline timeline = new Timeline();
        			final KeyValue kv = new KeyValue(controlPanel.translateXProperty(), -primaryScreenBounds.getHeight()/5);
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
        EventHandler<InputEvent> mouseoverLHSHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {            	
            	// check the position of the mouse
            	Point mousePosition = MouseInfo.getPointerInfo().getLocation();           	
            	// if the mouse is on the far LHS of the screen, show the notes panel
            	if (mousePosition.getY() >= (primaryScreenBounds.getHeight()-10 )) {
            		// add the mouselistener
                    controlPanel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutcontrolPanelHandler);
                    
                   
                 
            		if (!controlPanelVisible) {
            			// show panel
            			final Timeline timeline = new Timeline();
            			final KeyValue kv = new KeyValue(controlPanel.translateYProperty(), primaryScreenBounds.getHeight()/5);
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

        // check to see if the mouse is at the LHS of the screen every time it is moved
        root.addEventHandler(MouseEvent.MOUSE_MOVED, mouseoverLHSHandler);
    }
}