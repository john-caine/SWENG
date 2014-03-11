/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a create slide button
 Version : 1.0 27/2/2014
 */
package mainmenu;
	
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class MainMenuButton {
	public Button createSlide;
	public SlideMain slide;
	Stage stage;

	public MainMenuButton() {
			BorderPane border = new BorderPane();	
			createSlide = new Button("Create Slide");
			HBox hbox = new HBox();
			hbox.getChildren().add(createSlide);
			hbox.setAlignment(Pos.CENTER);
			border.setBottom(hbox);
		
		createSlide.setOnAction(new EventHandler<ActionEvent>() {

				@Override
	            public void handle(ActionEvent event) {
					stage = new Stage();
	                Group root = new Group();
	                Scene scene = new SlideMain(root).scene;
	                
	                stage.setTitle("ImageView");
	        	    stage.setScene(scene); 
	        	    stage.sizeToScene();
	        	    stage.setFullScreen(true);
	        		stage.show();
	            }
	        });
	}
	
}
