/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create the exit slide button
 Version : 1.0 27/2/2014
 */
package mainmenu;

import texthandler.TextHandler;
import imagehandler.ImageHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class SlideButton {
	public Button exitSlide;
	public Button nextSlide;
	public Button previousSlide;
	
	public SlideButton(){
        
        exitSlide = new Button("Exit Slide");
        exitSlide.setPrefWidth(80);
        exitSlide.setPrefHeight(40);
        
        
        nextSlide = new Button("Next Slide");
        nextSlide.setPrefWidth(80);
        nextSlide.setPrefHeight(40);
        
        previousSlide = new Button("Previous Slide");
        previousSlide.setWrapText(true);
        previousSlide.setPrefWidth(80);
        previousSlide.setPrefHeight(40);
        previousSlide.setTextAlignment(TextAlignment.CENTER);
		    
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
        
        nextSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Node  source = (Node)  event.getSource();
            	Stage stage  = (Stage) source.getScene().getWindow();
            	stage.setFullScreen(false);
            	Group root = new Group();
            	Scene scene = new SlideMain(root).scene;
            	ImageHandler image1 = new ImageHandler("../resources/bike2.jpg", 700, 300, 500, 500, null, null, null, null, null);
            	root.getChildren().add(image1.box);
            	stage.setScene(scene);
            	stage.sizeToScene();
            	stage.setFullScreen(true);
            	stage.show();
            }
        });
        
        previousSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Node  source = (Node)  event.getSource();
            	Stage stage  = (Stage) source.getScene().getWindow();
            	stage.setFullScreen(false);
            	Group root = new Group();
            	Scene scene = new SlideMain(root).scene;
            	ImageHandler image1 = new ImageHandler("../resources/bike.jpg", 300, 300, 500, 500, null, null, null, null, null);
        	    ImageHandler image2 = new ImageHandler("../resources/bike2.jpg", 50, 50, 100, 100, 5, 5, null, null, 90);
        	    TextHandler text1 = new TextHandler("I am some text.", "Times New Roman", 100, 600, 20, "#00FF00", "#0000FF", 40, 5, 10, null, null, null);
        	    TextHandler text2 = new TextHandler("I am some other text stuff.", "Helvetica", 800, 500, 40, "#00F600", "#0050FF", 40, 8, 30, null, null, null);
        	    
        	    root.getChildren().add(image1.box);
        		root.getChildren().add(image2.box);
        		root.getChildren().add(text1.textBox);
        		root.getChildren().add(text2.textBox);
            	stage.setScene(scene);
            	stage.sizeToScene();
            	stage.setFullScreen(true);
            	stage.show();
            }
        });
        
	}
}
