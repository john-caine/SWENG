/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the Slide scene
 Version : 1.0 27/2/2014
 Version : 1.1 10/3/2014 Stage being removed.
 */

package mainmenu;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;

public class SlideMain {
	
	public Scene scene;
	
	public SlideMain(Group root){
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		scene =  new Scene (root, screenBounds.getWidth(), screenBounds.getHeight());
		
	    HBox hbox = new HBox();
	    hbox.getChildren().add(new SlideButton().previousSlide);
        hbox.getChildren().add(new SlideButton().exitSlide);
        hbox.getChildren().add(new SlideButton().nextSlide);
        hbox.setAlignment(Pos.CENTER);
        hbox.setLayoutX((screenBounds.getWidth()- new SlideButton().exitSlide.getPrefWidth())/2);
        hbox.setLayoutY(screenBounds.getHeight());
        root.getChildren().add(hbox);
        
	}

}
