package notes;
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
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

public class NotesGUI {
	
	// declare variables
	boolean notesPanelVisible = false;
	TextFileHandler handler;
	TextArea notesBox;
	VBox notesPanel;
	
	// constructor
	public NotesGUI(Integer slideID, Group root) {
		setupNotesPanel(slideID, root);
	}
	
	// method to return the notesPanel VBox object
	public VBox getNotesPanel() {
		return notesPanel;
	}
	
	// method to indicate when the notes panel is onscreen
	public boolean getNotesPanelVisible() {
		return notesPanelVisible;
	}
	
	// method to access the content of the notesBox
	public String getContentOfNotesBox() {
		if (notesBox != null) {
			return notesBox.getText();
		}
		else {
			return null;
		}
	}

	// set up the example slide and add the notes panel
    public void setupNotesPanel(final Integer slideID, final Group root) {   
        // get the size of the screen
        final Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        
        // Set up the notes panel on the LHS of the screen
        notesPanel = new VBox();
        notesPanel.setPrefSize(primaryScreenBounds.getWidth()/5, primaryScreenBounds.getHeight());
        notesPanel.setStyle("-fx-background-color: #336699;");
        final Label notesTitle = new Label("Notes");
        final Label searching = new Label("Searching for Notes...");
        final ProgressIndicator pinwheel = new ProgressIndicator();
        notesBox = new TextArea();
        notesBox.setMaxWidth(4*primaryScreenBounds.getWidth()/25);
        notesBox.setPrefSize(4*primaryScreenBounds.getWidth()/25, primaryScreenBounds.getHeight()/4);
        notesBox.setText("Write your notes here");
        
        // clear the help text when the user begins typing
        notesBox.setOnKeyPressed(new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
            	if (notesBox.getText().equals("Write your notes here")) {
            		notesBox.setText("");
            	}
            }
        });
        
        final Label timersLabel = new Label("The timers and other stuff could go here");
        timersLabel.setPadding(new Insets(50,0,0,0));
        
        notesPanel.setPadding(new Insets(10,10,10,10));
        notesPanel.setSpacing(20);
        notesPanel.setAlignment(Pos.TOP_CENTER);
        
        notesPanel.getChildren().addAll(searching, pinwheel, timersLabel);
        
        // create an instance of the text file handler
        handler = new TextFileHandler();
        
        // Define an event handler to trigger when the mouse leaves the notes panel
        final EventHandler<InputEvent> mouseoutNotesPanelHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {
            	if (notesPanelVisible) {
            		// hide panel
            		final Timeline timeline = new Timeline();
        			final KeyValue kv = new KeyValue(notesPanel.translateXProperty(), -primaryScreenBounds.getWidth()/5);
        			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
        			timeline.getKeyFrames().add(kf);
        			timeline.play();
                  	notesPanelVisible = false;
                  	notesPanel.setDisable(true);
                  	saveNotes();
            	}
            	event.consume();
            }

            // if notes have been written, save them to a text file when the panel is hidden
			private void saveNotes() {
				// if a user has written some notes
				if (!(notesBox.getText().equals("") | (notesBox.getText().equals("Write your notes here")))) {
					// if they have changed the notes since the last save
					if (!notesBox.getText().equals(handler.readTextFile(slideID.toString() + "_notes.txt"))) {
						// call the create text file class
						handler.writeTextFile(notesBox.getText(), slideID);
					}
				}
			}
        };

        // Define an event handler to trigger when the user moves the mouse
        EventHandler<InputEvent> mouseoverLHSHandler = new EventHandler<InputEvent>() {
            public void handle(InputEvent event) {            	
            	// check the position of the mouse
            	Point mousePosition = MouseInfo.getPointerInfo().getLocation();           	
            	// if the mouse is on the far LHS of the screen, show the notes panel
            	if (mousePosition.getX() <= 10) {
            		// add the mouselistener
                    notesPanel.addEventHandler(MouseEvent.MOUSE_EXITED, mouseoutNotesPanelHandler);
                    
                    // search for pre-made notes
                    String existingNotes = handler.readTextFile(slideID.toString() + "_notes.txt");
                    // if notes have been made for this slide, stop loading and display them in the textarea
                    if (existingNotes != null) {
                    	notesBox.setText(existingNotes);
                    	notesPanel.getChildren().clear();
                    	notesPanel.getChildren().addAll(notesTitle, notesBox, timersLabel);
                    }
                    // else show the empty notes panel
                    else {
                    	notesPanel.getChildren().clear();
                    	notesPanel.getChildren().addAll(notesTitle, notesBox, timersLabel);
                    }
            		if (!notesPanelVisible) {
            			// show panel
            			final Timeline timeline = new Timeline();
            			final KeyValue kv = new KeyValue(notesPanel.translateXProperty(), primaryScreenBounds.getWidth()/5);
            			final KeyFrame kf = new KeyFrame(Duration.millis(500), kv);
            			timeline.getKeyFrames().add(kf);
            			timeline.play();
                    	notesPanelVisible = true;
                    	notesPanel.setDisable(false);
            		}
            	}
                event.consume();
            }
        };

        // check to see if the mouse is at the LHS of the screen every time it is moved
        root.addEventHandler(MouseEvent.MOUSE_MOVED, mouseoverLHSHandler);
    }
}