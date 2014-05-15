/*
 * Programmer: Jonathan Caine
 * Date Created: 26/04/2014
 * Description: Pops up a new window containing the string message passed as a parameter.
 * 				The window size is defined by the length of the string message. Clicking 
 * 				the OK button closes the pop-up window and terminates the eCook application.
 */

package errorhandler;

import javafx.application.Platform;
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
	
	public ErrorHandler(String errorMessage) {
	
		// Create a new stage and visible group
		stage = new Stage();
		Group root = new Group();
		
		// Create JFx text from the error message provided with font size 20
	    text = new Text(errorMessage);
	    Font font = new Font(20);
	    text.setFont(font);
	    
	    // Get the height and width of the text that has been created
	    double textWidth = text.getLayoutBounds().getWidth();
	    double textHeight = text.getLayoutBounds().getHeight();
	    
	    // Create a new scene whose size is based around the text which it has to display
	    Scene scene = new Scene(root, textWidth + 100, textHeight + 80);
	    
	    // Set the location of the text within the new scene
	    text.setX((scene.getWidth() / 2) - (textWidth / 2));
	    text.setY(30);
    
	    // Create a OK button for the user to press and set its location
	    Button okButton = new Button("ok");
	    okButton.setAlignment(Pos.CENTER);
		okButton.setLayoutX((scene.getWidth() / 2) - (okButton.getPrefWidth() / 2) - 10);
		okButton.setLayoutY((scene.getHeight()) - 35);
        
	    // Action handler for the OK button
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
            public void handle(ActionEvent event) {
				// Close the stage for pop-up window and terminate the eCook application
				stage.close();
				Platform.exit();
            }
        });
		
		// Add the OK button and text to the group to be displayed
		root.getChildren().add(okButton);
		root.getChildren().add(text);
	    
		// Set parameters for the stage before making it visible
	    stage.setTitle("eCook - Error");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setResizable(false);
		stage.show();
	}
}
