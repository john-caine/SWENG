package eCook;

import java.util.ArrayList;
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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
	private Button  previousSlide, nextSlide, exitSlide1;
	private Recipe recipe;
	private Slide slide;
	private int maxLayer;
	private Stage stage;
	

	public SlideShow(Stage stage) {
		
		this.stage = stage;
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
		
		Pane layer;
		ArrayList<Pane> layers;
		
		int imageCount, textCount, audioCount, videoCount; //, graphicCount;
		int fontSize;
		String fontColor, font; //, lineColor, fillColor;
		
		// Clear the current objects on the slide
		slideRoot.setVisible(false);
		slideRoot.getChildren().clear();
		
		layers = new ArrayList<Pane>();
		
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
		
	
		//Get number of layers to be used on slide
		maxLayer = getMaxLayer(images, text, audio,videos);
		
		
		//Create a pane for each layer and add to the group
		for(int currentLayer = 0; currentLayer < maxLayer; currentLayer++){
			
			layer = new Pane();
			layers.add(currentLayer, layer);
			
			
		}
		
		
		// Call the ImageHandler for each image object
		if (imageCount != 0){
			for(int i = 0; i < imageCount; i++){
				ImageHandler image1 = new ImageHandler(this, images.get(i).getUrlName(), images.get(i).getXStart(), 
												images.get(i).getYStart(), images.get(i).getWidth(),
												images.get(i).getHeight(), images.get(i).getStartTime(), 
												images.get(i).getDuration(), images.get(i).getLayer(), null, null);
				layers.get(images.get(i).getLayer()).getChildren().add(image1.box);
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
				layers.get(text.get(i).getLayer()).getChildren().add(text1.textBox);
			}
		}
		
		// Call the AudioHanlder for each audio object
		if (audioCount != 0){
			for(int i = 0; i < audioCount; i++){
				AudioHandler audio1 = new AudioHandler(this, audio.get(i).getUrlName(), audio.get(i).getStartTime(), 
														audio.get(i).getDuration(), audio.get(i).getLoop());
				layers.get(0).getChildren().add(audio1.mediaControl.box);
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
				layers.get(0).getChildren().add(video1.mediaControl.box);
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
        hbox.getChildren().add(exitSlide1);
        hbox.getChildren().add(nextSlide);
       
        // Put the buttons in the bottom centre of the slide
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        hbox.setAlignment(Pos.CENTER);
        hbox.setLayoutX((screenBounds.getWidth()- exitSlide1.getPrefWidth())/2);
        hbox.setLayoutY(screenBounds.getHeight());
	    slideRoot.getChildren().addAll(layers);
	    // Add the buttons to the slide
        slideRoot.getChildren().add(hbox);
	    
        // Show the new set of objects on the slide
	    slideRoot.setVisible(true);  
	}	

	private Integer getMaxLayer(List<Image> images, List<TextBody> text,
			List<Audio> audio, List<Video> videos) {
		
		Integer totalLayers = 0;
	
		for(int currentImage = 0; currentImage < images.size(); currentImage++ ){
			
			
			if(images.get(currentImage).getLayer() > totalLayers){
				totalLayers = images.get(currentImage).getLayer();
			}
			
		}
			
		for(int currentText = 0; currentText < text.size(); currentText++ ){
				
				
			if(text.get(currentText).getLayer() > totalLayers){
					totalLayers = text.get(currentText).getLayer();
			}
						
		}
		
		for(int currentAudio = 0; currentAudio < audio.size(); currentAudio++ ){
			
			
			if(audio.get(currentAudio).getLayer() > totalLayers){
					totalLayers = audio.get(currentAudio).getLayer();
			}
						
		}
		
		for(int currentVideo = 0; currentVideo < text.size(); currentVideo++ ){
			
			
			if(videos.get(currentVideo).getLayer() > totalLayers){
					totalLayers = videos.get(currentVideo).getLayer();
			}
						
		}
		
	/*	for(int currentGraphics = 0; currentGraphics < graphics.size(); currentGraphics++ ){
			
			
			if(graphics.get(currentGraphics).getLayer() > totalLayers){
					totalLayers = graphics.get(currentGraphics).getLayer();
			}
						
		}*/
		
		return totalLayers;
		
		
	}

	public void SlideButton() {
        
        exitSlide1 = new Button("Exit SlideShow");
        exitSlide1.setPrefWidth(80);
        exitSlide1.setPrefHeight(40); 
        
        nextSlide = new Button("Next Slide");
        nextSlide.setPrefWidth(80);
        nextSlide.setPrefHeight(40);
        
        previousSlide = new Button("Previous Slide");
        previousSlide.setWrapText(true);
        previousSlide.setPrefWidth(80);
        previousSlide.setPrefHeight(40);
        previousSlide.setTextAlignment(TextAlignment.CENTER);
		    
        /*Exit Slide when exit slide button is pressed*/
       
        exitSlide1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	/*
            	Node  source = (Node)  event.getSource();
            	Stage stage  = (Stage) source.getScene().getWindow();
            	Group root = (Group) stage.getScene().getRoot();
            	root.getChildren().clear();
            	new MainMenu(stage);
            	*/
            	newSlide(nextSlideID, false);
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
