package eCook;

import mainmenu.SlideMain;
import texthandler.*;
import imagehandler.*;
import XMLParser.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class eCook extends Application {

	public void start(final Stage stage) {
		Group root;
		Scene scene;
		root = new Group();
		scene = new SlideMain(root).scene;
		
		new XMLReader();
		ImageHandler image1 = new ImageHandler("../resources/bike.jpg", 300, 300, 500, 500, null, null, null, null, null);
	    ImageHandler image2 = new ImageHandler("../resources/bike2.jpg", 50, 50, 100, 100, 5, 5, null, null, 90);
	    TextHandler text1 = new TextHandler("I am some text.", "Times New Roman", 100, 600);
	    
	    root.getChildren().add(image1.box);
		root.getChildren().add(image2.box);
		root.getChildren().add(text1.textBox);
	    
	    stage.setTitle("ImageView");
	    stage.setScene(scene); 
	    stage.sizeToScene();
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
	
		public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}
