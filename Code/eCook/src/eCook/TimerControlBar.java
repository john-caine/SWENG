package eCook;

import java.awt.MouseInfo;
import java.awt.Point;

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
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Duration;

public class TimerControlBar {
	
	private HBox controlPanel;
    private boolean controlPanelVisible;

	public TimerControlBar(Integer slideID, Group root) {
		
		setUpControlPanel(slideID, root);
		Button timerButton = new Button("Add");
		controlPanel.getChildren().add(timerButton);
		
		
	}
	
	public void setUpControlPanel(final Integer slideID, final Group root) {   
        // get the size of the screen
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        // Set up the notes panel on the LHS of the screen
        controlPanel = new HBox();
        
        controlPanel.setPrefSize(primaryScreenBounds.getWidth(), primaryScreenBounds.getHeight()/6);
        System.out.println("TimerControl height" + controlPanel.getHeight());
    
        controlPanel.setStyle("-fx-background-color: #336699;");
     
        // clear the help text when the user begins typing
        controlPanel.setPadding(new Insets(30,10,10,30));
        controlPanel.setSpacing(20);
        controlPanel.setAlignment(Pos.TOP_CENTER);
        

        
        // create an instance of the text file handler
        
        
        // Define an event handler to trigger when the mouse leaves the notes panel
        final EventHandler<InputEvent> mouseoutcontrolPanelHandler = new EventHandler<InputEvent>() {
       

			public void handle(InputEvent event) {
            	if (controlPanelVisible) {
            		// hide panel
            		final Timeline timeline = new Timeline();
        			final KeyValue kv = new KeyValue(controlPanel.translateYProperty(), -primaryScreenBounds.getHeight()/6);
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
        final EventHandler<InputEvent> mouseoverLHSHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {            	
            	// check the position of the mouse
            	Point mousePosition = MouseInfo.getPointerInfo().getLocation();        
            	System.out.println(mousePosition);
            	// if the mouse is on the far LHS of the screen, show the notes panel
            	if (mousePosition.getY() <= (10 )) {
                    System.out.println("Mouse less than 10 y pos");
            		// add the mouselistener
                    controlPanel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutcontrolPanelHandler);
                  
            		if (!controlPanelVisible) {
            			// show panel
            			final Timeline timeline = new Timeline();
            			final KeyValue kv = new KeyValue(controlPanel.translateYProperty(), primaryScreenBounds.getHeight()/6);
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

	public HBox getControlPanel() {
		// TODO Auto-generated method stub
		return controlPanel;
	}
	

}
