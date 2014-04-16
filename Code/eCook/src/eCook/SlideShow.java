package eCook;

import java.util.List;

import audiohandler.AudioHandler;

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
import videohandler.VideoPlayerHandler;
import xmlparser.*;

public class SlideShow {

	private Scene slideScene;
	private Group slideRoot;
	public int currentSlideID, nextSlideID, prevSlideID, numOfSlides;
	private Button exitSlide, previousSlide, nextSlide;
	private Recipe recipe;
	private Slide slide;

	public SlideShow(Stage stage) {
		
		XMLReader reader;
		
		// Create a new group for objects
		slideRoot = new Group();
		
		// Create a new scene for the slide show
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		slideScene =  new Scene (slideRoot, screenBounds.getWidth(), screenBounds.getHeight());
		
    	// Set window properties
    	stage.setScene(slideScene);
    	stage.setFullScreen(true);
    	//stage.show();

    	stage.sizeToScene();
    	stage.setFullScreen(false);
    	stage.setFullScreen(true);
    	stage.show();
		
		// Call XML parser
		 reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
		
		recipe = reader.getRecipe();
		
		// Get the total number of slides without branch slides
		numOfSlides = recipe.getNumberOfSlides();
		
		// Call newSlide() to start displaying the side show from slide with ID 0.
		//Change back to 0, 3 only for testing purposes.
		newSlide(0, false);
	}
	
	public void newSlide(Integer slideID, Boolean isBranch) {
		List<Image> images;
		List<TextBody> text;
		List<Audio> audio;
		//List<Graphic> graphics;
		List<Video> videos;
		
		int imageCount, textCount, audioCount, videoCount; //, graphicCount;
		int fontSize;
		String fontColor, font; //, lineColor, fillColor;
		
		// Clear the current objects on the slide
		slideRoot.setVisible(false);
		slideRoot.getChildren().clear();
		
		// If slideID is 0 or has exceeded the number of slides to display, exit to main menu
		//if (slideID == -1 || slideID >= numOfSlides)
			// TODO exit  to  main menu somehow???
			// new MainMenu()

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
			nextSlideID = (slideID + 1);
			prevSlideID = (slideID - 1);
		}
		
		slide = recipe.getSlide(slideID);
		
		// Get arrays containing the required objects
		images = slide.getContent().getImages();
		text = slide.getContent().getTexts();
		audio = slide.getContent().getAudios();
		videos = slide.getContent().getVideos();
		//graphics = slide.getContent.getGraphics();
		
		// Get how many objects of each type are required
		imageCount = images.size();
		textCount = text.size();
		audioCount = audio.size();
		videoCount = videos.size();
		//graphicCount = graphics.size();
		
		// Call the ImageHandler for each image object
		if (imageCount != 0){
			for(int i = 0; i < imageCount; i++){
				ImageHandler image1 = new ImageHandler(this, images.get(i).getUrlName(), images.get(i).getXStart(), 
												images.get(i).getYStart(), images.get(i).getWidth(),
												images.get(i).getHeight(), images.get(i).getStartTime(), 
												images.get(i).getDuration(), images.get(i).getLayer(), null, null);
				slideRoot.getChildren().add(image1.box);
			}
		}
		
		// Call the TextHanlder for each text object
		if (textCount != 0){
			for(int i = 0; i < textCount; i++){

				// Retrieve any defaults that have been set
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
				
				TextHandler text1 = new TextHandler(this,  text.get(i), font, text.get(i).getXStart(), 
											text.get(i).getYStart(), fontSize, fontColor, text.get(i).getXEnd(), 
											text.get(i).getYEnd(), text.get(i).getStartTime(), text.get(i).getDuration(), 
											text.get(i).getLayer(), null, null);
				slideRoot.getChildren().add(text1.textBox);
			}
		}
		
		// Call the AudioHanlder for each audio object
		if (audioCount != 0){
			for(int i = 0; i < audioCount; i++){
				AudioHandler audio1 = new AudioHandler(this, audio.get(i).getUrlName(), audio.get(i).getStartTime(), 
														audio.get(i).getLoop(), audio.get(i).getDuration());
				//slideRoot.getChildren().add(audio1.box);
			}
		}
		
		// Call the VideoHandler for each video object
		if (videoCount != 0){
			for(int i = 0; i < videoCount; i++){
				VideoPlayerHandler video1 = new VideoPlayerHandler(videos.get(i).getUrlName(), videos.get(i).getXStart(), 
												videos.get(i).getYStart(), videos.get(i).getWidth(),
												videos.get(i).getHeight(), videos.get(i).getLoop(),
												videos.get(i).getStartTime(), videos.get(i).getDuration());
												/*videos.get(i).getLayer()*/
				slideRoot.getChildren().add(video1.mediaControl.box);
			}
		}
		
		// Call the GraphicHandler for each graphic object
//		if (graphicCount != 0){
//			for(int i = 0; i < graphicCount; i++){
//				
//				// Retrieve any defaults that have been set
//				if (graphics.get(i).getLineColor() == null) 
//					lineColor = recipe.getDefaults().getLineColor();
//				else 
//					lineColor = graphics.get(i).getLineColor();
//				
//				if (graphics.get(i).getFillColor() == null)
//					fillColor = recipe.getDefaults().getFillColor();
//				else
//					fillColor = graphics.get(i).getFillColor();
//			
//			
//				GraphicHandler graphic1 = new GraphicHandler(this, graphics.get(i).getTotalPoints(), 
//												graphics.get(i).getWidth(), graphics.get(i).getHeight(), 
//												graphics.get(i).getStartTime(), graphics.get(i).getDuration(), 
//												graphics.get(i).getLayer(), fillColor, lineColor,
//												graphics.get(i).getBranch(), graphics.get(i).getPoints());
//				slideRoot.getChildren().add(graphic1.box);
//			}
//		}
    	
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
	    
	    // Add the buttons to the slide
        slideRoot.getChildren().add(hbox);
	    
        // Show the new set of objects on the slide
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
            	event.consume();
            }
        });
        
        previousSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	newSlide(prevSlideID, false);
            	event.consume();
            	
            }
        });    
	}
}
