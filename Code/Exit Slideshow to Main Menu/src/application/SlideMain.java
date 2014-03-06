/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the Slide scene
 Version : 1.0 27/2/2014
 */

package application;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
	
public class SlideMain {
	
	public Stage stage;
	
	public SlideMain(){
		BorderPane border = new BorderPane();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		stage = new Stage();
		Scene scene =  new Scene(border);
		
	    HBox hbox = new HBox();
        hbox.getChildren().add(new SlideButton().exitSlide);
        hbox.setAlignment(Pos.CENTER);
        border.setBottom(hbox);
		
		stage.setScene(scene);
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
        stage.setFullScreen(true);
        stage.show();
		
        /*Exit Slide when ESC key is pressed*/
		stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			    @Override
			    public void handle(KeyEvent event){
				    if(event.getCode() == KeyCode.ESCAPE){
					    stage.close();
					    event.consume();    
				    }	                                 
			    }
		});
	}

}
