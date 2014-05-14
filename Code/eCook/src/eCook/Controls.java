/*
 * Programmer: Steve Thorpe, Paul Mathema.
 * Date Created: 12/05/2014
 * Description: Creates new Control slide with a panel of control buttons which slides in and out from the bottom.
 * 
 */


package eCook;

import imagehandler.ImageHandler;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.util.ArrayList;
import audiohandler.AudioHandler;
import texthandler.TextHandler;
import videohandler.VideoPlayerHandler;
import graphicshandler.GraphicsHandler;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Example of a sidebar that slides in and out of view
 */
public class Controls {
	
	private Rectangle2D primaryScreenBounds;
	protected HBox panel;
    protected boolean notesPanelVisible = false;
    protected Button playButton, pauseButton, nextButton, previousButton, exitButton;

    Label label = new Label();

    // set up the example slide and add the notes panel
    public void controls (ArrayList<TextHandler> textHandlerList, ArrayList<ImageHandler> imageHandlerList, 
    						ArrayList<AudioHandler> audioHandlerList, ArrayList<VideoPlayerHandler> videoHandlerList,
    						Group visibleGroup, Stage primaryStage) {
        
    	final StackPane root = new StackPane();
        final Panel root1 = new Panel();

        // Add a label with sample slide content
        Label slideText = new Label("Control Panel.");

        // get the size of the screen
        primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        // Set up the control panel from the Bottom of the screen
        panel = new HBox();
        panel.setPrefSize(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight());
        //panel.setStyle("-fx-background-color: #336699;");
        panel.setStyle("-fx-background-color: #DA70D6;");
        
        
        // PUT YOUR CODE HERE TO DECLARE GUI OBJECTS
        panel.setPadding(new Insets(30, 10, 30, 10));
        panel.setAlignment(Pos.BOTTOM_CENTER);
        panel.setSpacing(10);
        
        // add buttons to panel
        playButton();
        pauseButton();
        exitButton();
        previousButton();
        nextButton();
        
        // check to see if the mouse is at the LHS of the screen every time it is moved
        root.addEventHandler(MouseEvent.MOUSE_MOVED, mouseoverLHSHandler);
    }
    
    public void playButton() {    
       
        // ADD PlayButton to the Panel
        
           playButton = new Button();
           playButton.setText("'Play'");
        // Add playbutton's event handler and listener   
           panel.getChildren().add(playButton);
                   playButton.setOnAction(new EventHandler<ActionEvent>() {
                       public void handle(ActionEvent e) {
                           label.setText("Acc");
                  }
              });
    }

  
    public void pauseButton() {         
         // Add PauseButton to the Panel  	
            pauseButton = new Button();
            pauseButton.setText("'Pause'");
         // Add pausebutton's event handler and listener      
            panel.getChildren().add(pauseButton);
                    playButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                           // slide.pause();
                   }
              });
                    
    }
             
    public void previousButton() {
         // Add previousButton to the Panel        
            previousButton = new Button();
            previousButton.setText("'Pause'");
         // Add previousbutton's event handler and listener               
            panel.getChildren().add(previousButton);
                    previousButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                            label.setText("Acc");
                   }
              });
    }
    
    public void nextButton() {
                    
        // Add nextButton to the Panel
           nextButton = new Button();
           nextButton.setText("'Next'");
        // Add nextbutton's event handler and listener                  
           panel.getChildren().add(nextButton);
                   nextButton.setOnAction(new EventHandler<ActionEvent>() {
                       public void handle(ActionEvent e) {
                             label.setText("Acc");
                   }
              });
    }
    
    public void exitButton() {
                   
        // Add exitButton to the Panel           
           Button exitButton = new Button();
           exitButton.setText("'Exit'");
        // Add exitbutton's event handler and listener                  
           panel.getChildren().add(exitButton);
                    exitButton.setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e) {
                             label.setText("Acc");
                     }
             });
    }
            


        // Define an event handler to trigger when the mouse leaves the notes panel
        final EventHandler<InputEvent> mouseoutNotesPanelHandler = new EventHandler<InputEvent>() {

            public void handle(InputEvent event) {
                if (notesPanelVisible) {
                    // hide panel
                    final Timeline timeline = new Timeline();
                    final KeyValue kv = new KeyValue(panel.translateYProperty(), primaryScreenBounds.getHeight());
                    final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
                    timeline.getKeyFrames().add(kf);
                    timeline.play();
                    notesPanelVisible = false;
                    panel.setDisable(true);
                }
                event.consume();
            }
        };

        // Define an event handler to trigger when the user moves the mouse
        EventHandler<InputEvent> mouseoverLHSHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
                // check the position of the mouse
                java.awt.Point mousePosition = MouseInfo.getPointerInfo().getLocation();
                // if the mouse is on the far LHS of the screen, show the notes panel
                if (mousePosition.getX() <= 10) {
                    if (!panel.getChildren().contains(panel)) {
                        panel.getChildren().add(panel);
                        StackPane.setMargin(panel, new Insets(primaryScreenBounds.getHeight(),0,primaryScreenBounds.getHeight()/8,0));
                        panel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutNotesPanelHandler);
                    }
                    if (!notesPanelVisible) {
                        // show panel
                        final Timeline timeline = new Timeline();
                        final KeyValue kv = new KeyValue(panel.translateYProperty(), -primaryScreenBounds.getHeight()/100);
                        final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
                        timeline.getKeyFrames().add(kf);
                        timeline.play();
                        notesPanelVisible = true;
                        panel.setDisable(false);
                    }
                }
                event.consume();
            }
        };

    }
   