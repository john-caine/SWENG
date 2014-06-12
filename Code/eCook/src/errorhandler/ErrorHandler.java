/*
 * Programmers: Jonathan Caine
 * Date: 03/05/2014
 * Description: Displays a pop-up window to the user, containing a string error message, and closes the application
 * when the "OK" button is pressed.
 */
package errorhandler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ErrorHandler {
	
	protected Text text;
	protected Stage stage;
	protected Button okButton;
	
	/**
	 * Calls a pop-up window to display and error message to the user, then closes the application.
	 * @param errorMessage Error message to be displayed to the user
	 */
	public ErrorHandler(String errorMessage) {
		// Create a new stage and group
		stage = new Stage();
		Group root = new Group();
		
		// Set the text of the error message and its size
	    text = new Text(errorMessage);
	    Font font = new Font(20);
	    text.setFont(font);
	    
	    // Get the width and height of the text object created
	    double textWidth = text.getLayoutBounds().getWidth();
	    double textHeight = text.getLayoutBounds().getHeight();
	    
	    // Set the size of the scene based on the size of the error message to display
	    Scene scene = new Scene(root, textWidth + 100, textHeight + 80);
	    
	    // Centre the text in the scene
	    text.setX((scene.getWidth() / 2) - (textWidth / 2));
	    text.setY(30);
    
	    // Create an "OK" and centre it in the scene below the text
	    Button okButton = new Button("ok");
	    okButton.setAlignment(Pos.CENTER);
		okButton.setLayoutX((scene.getWidth() / 2) - (okButton.getPrefWidth() / 2) - 10);
		okButton.setLayoutY((scene.getHeight()) - 35);
        
	    // Close the application (eCook) when the "OK" button is pressed
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
            public void handle(ActionEvent event) {
				stage.close();
            }
        });
		
		// Add the text and button to the visible group
		root.getChildren().add(okButton);
		root.getChildren().add(text);
	    
		// Configure the parameters for the stage then show it
	    stage.setTitle("eCook - Error");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setResizable(false);
		stage.show();
	}
}
