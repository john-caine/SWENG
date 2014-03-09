package eCook;

import texthandler.*;
import imagehandler.*;
import XMLParser.*;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class eCook extends Application {

	public void start(Stage stage) {
		Group root;
		Scene scene;
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		root = new Group();
		scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight(), Color.WHITE);
		
		new XMLReader();
		ImageHandler image1 = new ImageHandler("../resources/bike.jpg", 300, 300, 500, 500, null, null, null, null, null);
	    ImageHandler image2 = new ImageHandler("../resources/bike2.jpg", 50, 50, 100, 100, 5, 50, null, null, 90);
	    TextHandler text1 = new TextHandler("I am some text.", "Times New Roman", 100, 600);
	    
	    root.getChildren().add(image1.box);
		root.getChildren().add(image2.box);
		root.getChildren().add(text1.textBox);
	    
	    stage.setTitle("ImageView");
	    stage.setScene(scene); 
	    stage.sizeToScene();
	    stage.setFullScreen(true);
		stage.show();
	    
	}
	
		public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}

}
