package eCook;

import java.util.List;

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
import xmlparser.Image;
import xmlparser.Recipe;
import xmlparser.Slide;
import xmlparser.TextString;
import xmlparser.XMLReader;
import xmlparser.TextBody;

public class SlideShow {

	private Scene slideScene;
	private Group slideRoot;
	public int currentSlideID = 0, nextSlideID = 1, prevSlideID = -1;
	private Button exitSlide, previousSlide, nextSlide;


	private XMLReader reader;
	private Recipe recipe;
	private Slide slide;
	private List<Image> images;
	private List<TextBody> text;


	
	public SlideShow(Stage stage) {
		
		//Create 2 temporary text String objects to populate textHandlers 
		//REMOVE ME WHEN XML Parser Implementation is complete!
		
		
		
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
    	stage.setFullScreen(true);
    	//stage.show();

    	stage.sizeToScene();
    	stage.setFullScreen(false);
    	stage.setFullScreen(true);
    	stage.show();
		
		// Call XML parser
		 reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
		
		recipe = reader.getRecipe();
		
		
		
		// Call newSlide() to start displaying the side show from slide with ID 0.
		//Change back to 0, 3 only for testing purposes.
		newSlide(0, false);
	}
	
	public void newSlide(Integer slideID, Boolean isBranch) {
		int imageCount;
		int textCount;
		
		String font;
		int fontSize;
		String fontColor;
		
		
		
		// Call out to the logic method to determine what should be on the slide
		//TODO write slide logic method.
		System.out.println(slideID);
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
		
		slide = recipe.getSlide(slideID);
		
		images = slide.getContent().getImages();
		text = slide.getContent().getTexts();
		imageCount = images.size();
		textCount = text.size();
		
		
		
		
		if (imageCount != 0){
			for(int i = 0; i < imageCount; i++){
			
			
				ImageHandler image1 = new ImageHandler(this, images.get(i).getUrlName(), images.get(i).getXStart(), images.get(i).getYStart(), images.get(i).getWidth(),
														images.get(i).getHeight(), images.get(i).getStartTime(), images.get(i).getDuration(), images.get(i).getLayer(), null, null);
				
	
				slideRoot.getChildren().add(image1.box);
				System.out.println("Image Handler created!");
			}
		}
		
		
		if (textCount != 0){
			for(int i = 0; i < textCount; i++){

				if (text.get(i).getFont() == null) 
					font = recipe.getDefaults().getFont();
				else 
					font = text.get(i).getFont();
				
				if (text.get(i).getFontSize() == null)
					fontSize = recipe.getDefaults().getFontSize();
				else
					fontSize = text.get(i).getFontSize();
				
				if (text.get(i).getFontColor() == null)
					fontColor = recipe.getDefaults().getFontColor();
				else 
					fontColor = text.get(i).getFontColor();
				
				//			  text.get(i);
//				System.out.println(text.get(i).getFont());
//				System.out.println(text.get(i).getXStart());
//				System.out.println(text.get(i).getYStart());
//				System.out.println(text.get(i).getFontSize());
//				System.out.println(text.get(i).getFontColor());
//				System.out.println(text.get(i).getXEnd());
//				System.out.println(text.get(i).getStartTime());
//				System.out.println(text.get(i).getDuration());
//				System.out.println(text.get(i).getLayer());
				
				TextHandler text1 = new TextHandler(this,  text.get(i) , font, text.get(i).getXStart(), text.get(i).getYStart(), fontSize, fontColor, text.get(i).getXEnd(), text.get(i).getYEnd(), text.get(i).getStartTime(), text.get(i).getDuration(), text.get(i).getLayer(), null, null);
				
	
				slideRoot.getChildren().add(text1.textBox);
				System.out.println("Text Handler created!");
			}
		}
		
		
		
		// Hide the current group of objects
		
		
		
		
	
    	
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
        
	   
	    
	   // slideRoot.getChildren().add(image2.box);
	   // slideRoot.getChildren().add(text1.textBox);
	   // slideRoot.getChildren().add(text2.textBox);
	    
	     // Add the buttons to the slide
        slideRoot.getChildren().add(hbox);
	    
	    slideRoot.setVisible(true);
	    
	    
	}	

	public void SlideButton() {
        
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
