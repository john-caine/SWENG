/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the controls of the Main Menu. Able to create a slide and exit the slide with appropriate actions.
 Version : 1.0 27/2/2014
 */
package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class MainController implements Initializable{
	
	Parent root;
	Stage stage;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub		
	}
	
	public void  createSlide(ActionEvent event){
		try {
			root = FXMLLoader.load(getClass().getResource("/fxml/Slide.fxml"));
			stage = new Stage();
	        stage.setTitle("Slide");
	        stage.setScene(new Scene(root,400,400));
	        stage.setFullScreen(true);
	        stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
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
	
	/*Exit Slide when exit slide button is pressed*/
	public void exitSlide(ActionEvent event){
		Node  source = (Node)  event.getSource(); 
	    Stage stage  = (Stage) source.getScene().getWindow();
	    stage.close();
	}
}
