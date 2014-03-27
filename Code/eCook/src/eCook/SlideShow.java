package eCook;

import imagehandler.ImageHandler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import texthandler.TextHandler;
import xmlparser.TextString;
import xmlparser.XMLReader;

public class SlideShow {

	private Scene slideScene;
	private Group slideRoot;
	public int currentSlideID = 0, nextSlideID = 1, prevSlideID = -1;
	private Button exitSlide, previousSlide, nextSlide;
	private TextString textString;
	private TextString textString2;
	

	
	public SlideShow(Stage stage) {
		
		
		
		//Create 2 temporary text String objects to populate textHandlers 
		//REMOVE ME WHEN XML Parser Implementation is complete!
		textString = new TextString();
		textString2 = new TextString();
		textString.setText("I am some text");
		textString2.setText("I am some other text");
		
		
		// Create a new group for objects
		slideRoot = new Group();
		
		// Create a new scene for the slide show
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		slideScene =  new Scene (slideRoot, screenBounds.getWidth(), screenBounds.getHeight());
		
    	// Set window properties
    	stage.setScene(slideScene);
    	
    	//This code doesn't appear to be necessary, have commented it out for now incase there is 
    	// is something I have missed.
    	
    	//stage.sizeToScene();
    	//stage.setFullScreen(false);
    	//stage.setFullScreen(true);
    	//stage.show();
		
		// Call XML parser
		new XMLReader();
		
		// Call newSlide() to start displaying the side show from slide with ID 0.
		newSlide(0, false);
	}
	
	public void newSlide(Integer slideID, Boolean isBranch) {
		// Call out to the logic method to determine what should be on the slide
		//TODO write slide logic method.
		
		// Hide the current group of objects
		slideRoot.setVisible(false);
		slideRoot.getChildren().clear();
		
		// If slideID is 0 exit to main menu
		if (slideID == -1)
			// TODO exit  to  main menu somehow???
		
		// If branched slide set relevant globals
		if (isBranch == true)
		{
			nextSlideID = currentSlideID;
			prevSlideID = currentSlideID;
			currentSlideID = slideID;
		} 
		else 
		{
			currentSlideID = slideID;
			nextSlideID = currentSlideID + 1;
			prevSlideID = currentSlideID - 1;
		}
    	
    	// Create the buttons for the slide.
	    HBox hbox = new HBox();
	    SlideButton();
	    hbox.getChildren().add(previousSlide);
        hbox.getChildren().add(exitSlide);
        hbox.getChildren().add(nextSlide);
       
        // Put the buttons in the bottom centre of the slide
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        hbox.setAlignment(Pos.CENTER);
        hbox.setLayoutX((screenBounds.getWidth()- exitSlide.getPrefWidth())/2);
        hbox.setLayoutY(screenBounds.getHeight());
        
   
        
        // Itterate over the objects for the slide calling relevant handlers
        // Temp - just add some objects 
        ImageHandler image1 = new ImageHandler(this, "../resources/bike.jpg", 300, 300, 500, 500, null, null, null, 3, null);
	    ImageHandler image2 = new ImageHandler(this, "../resources/bike2.jpg", 50, 50, 100, 100, 5, 5, null, null, 90);
	    TextHandler text1 = new TextHandler(this,textString, "Times New Roman", 100, 600, 20, "#00FF00", "#0000FF", 40, 5, 10, null, null, null);
	    TextHandler text2 = new TextHandler(this, textString2, "Helvetica", 800, 500, 40, "#00F600", "#0050FF", 40, 8, 30, null, null, null);
	    slideRoot.getChildren().add(image1.box);
	    slideRoot.getChildren().add(image2.box);
	    slideRoot.getChildren().add(text1.textBox);
	    slideRoot.getChildren().add(text2.textBox);
	    
	     // Add the buttons to the slide
        slideRoot.getChildren().add(hbox);
	    
	    slideRoot.setVisible(true);
	    
	    
	}	

	public void SlideButton() {
        
        exitSlide = new Button("Exit SlideShow");
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
            	newSlide(nextSlideID, false);
            }
        });
        
        previousSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	newSlide(prevSlideID, false);
            	
            }
        });    
	}
}
