/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the exit slide button
 Version : 1.0 27/2/2014
 */
package mainmenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SlideButton {
	public Button exitSlide;
	
	public SlideButton(){
        
        exitSlide = new Button("Exit Slide");
        exitSlide.setPrefWidth(80);
		    
        /*Exit Slide when exit slide button is pressed*/
        exitSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Node  source = (Node)  event.getSource();
            	Stage stage  = (Stage) source.getScene().getWindow();
        	    stage.close();
            	event.consume();
            }
        });
        
	}
}
