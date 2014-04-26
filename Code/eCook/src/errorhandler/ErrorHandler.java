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
	
	public ErrorHandler(String errorMessage) {
	
		stage = new Stage();
		Group root = new Group();
		
	    
		
	    //errorMessage = "Testing error handler";
		
	    text = new Text(errorMessage);
	    Font font = new Font(20);
	    text.setFont(font);
	    
	    double textWidth = text.getLayoutBounds().getWidth();
	    double textHeight = text.getLayoutBounds().getHeight();
	    
	    Scene scene = new Scene(root, textWidth + 100, textHeight + 80);
	    
	    text.setX((scene.getWidth() / 2) - (textWidth / 2));
	    text.setY(30);
    
	    
	    Button okButton = new Button("ok");
	    okButton.setAlignment(Pos.CENTER);
		okButton.setLayoutX((scene.getWidth() / 2) - (okButton.getPrefWidth() / 2) - 10);
		okButton.setLayoutY((scene.getHeight()) - 35);
        
	    
		okButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
            public void handle(ActionEvent event) {
				stage.close();
            }
        });
		
		root.getChildren().add(okButton);
		root.getChildren().add(text);
	    
	    stage.setTitle("eCook - Error");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setResizable(false);
		stage.show();
	}
}
