package eCook;

import texthandler.*;
import imagehandler.*;
import xmlparser.*;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class eCook extends Application {
	
	Group root;
	Scene scene; 
	Stage stage;
	SlideShow slideShow;

	public void start(Stage stage) {		
		// This is the group for the main menu - DONT DELETE IT!
		root = new Group();
	    
	    stage.setTitle("eCook");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setFullScreen(true);
		stage.show();
			
		 slideShow = new SlideShow(stage);
	    
	}
	
		public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}
}
