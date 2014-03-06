/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the exit slide button
 Version : 1.0 27/2/2014
 */
package application;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SlideButton {
	public Button exitSlide;
	
	public SlideButton(){
        
        exitSlide = new Button("Exit Slide");
		    
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
