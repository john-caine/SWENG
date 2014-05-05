/*
 * Programmer: Steve Thorpe, Jonathan Caine
 * Date Created: 14/03/2014
 * Description: Creates new slideshow and slides from a parsed XML player with logic for setting the layer of all content and for moving between slides
 * 
 */

package eCook;

import java.util.ArrayList;
import java.util.List;

import audiohandler.AudioHandler;
import imagehandler.ImageHandler;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import texthandler.TextHandler;
import timer.Timer;
import videohandler.VideoPlayerHandler;
import xmlparser.*;

public class SlideShow {

	private Scene slideScene;
	private Group slideRoot;
	public int currentSlideID; 
	public int nextSlideID; 
	public int prevSlideID; 
	public int numOfSlides;
	private Button  previousSlide;
	private Button nextSlide;
	private Button exitSlide1;
	private Recipe recipe;
	private Slide slide;
	private Defaults defaults;
	private Integer maxLayer;
	private Ingredients ingredients;
	private ArrayList<Ingredient> ingredientsList;
	private Stage stage;
	private ArrayList<AudioHandler> audioHandlerList;
	private Button createTimer;
	private Timer timer;
	private HBox timerHbox;
	private int numberOfTimers;
	private ArrayList<Timer> timerList;
	private ArrayList<List<Integer>> timerValues;

	
	public SlideShow(Stage stage, String filepath) {
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
    	//stage.setFullScreen(false);
    	stage.setFullScreen(true);
    	stage.show();
		
		
		 
		
		// Call XML parser
		reader = new XMLReader(filepath);
		recipe = reader.getRecipe();
		
		ingredientsList = new ArrayList<Ingredient>();
		ingredients = reader.getIngredients();
		
		//Test that ingredients were present within the XML file
		//if(ingredients.getIngredients().isEmpty() == false){
		//ingredientsList = ingredients.getIngredients();
		//System.out.println(ingredientsList.get(1).getName()+ingredientsList.get(1).getAmount()+ingredientsList.get(1).getUnits());
		//}
		
	
		
		// Get the defaults from the recipe
		defaults = recipe.getDefaults();
		// Get the total number of slides without branch slides
		numOfSlides = recipe.getNumberOfSlides();

		// Call newSlide() to start displaying the side show from slide with ID 0.
		//Change back to 0, 3 only for testing purposes.
		newSlide(0, false, null);

		// Set the colour of the slide
		slideScene.setFill(Color.valueOf(defaults.getBackgroundColor()));
		// These properties update the stage
		stage.hide();
		stage.setScene(slideScene);
		stage.setFullScreen(true);
		stage.show();
	}
	
	public void newSlide(Integer slideID, Boolean isBranch, ArrayList<List<Integer>> currentTimerValues) {
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
		maxLayer = getMaxLayer(images, text, audio, videos);
		
		//Array list to store layers created for slide content
		layers = new ArrayList<Pane>();
		
		//Create a pane for each layer and add to the group
		for(int currentLayer = 0; currentLayer <= maxLayer; currentLayer++){
			
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
				
				Integer imageLayer = images.get(i).getLayer();
				if (imageLayer == null){
					imageLayer = 0;
				}
				layers.get(imageLayer).getChildren().add(image1.box);
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
				
				Integer textLayer = text.get(i).getLayer();
				if (textLayer == null){
					textLayer = 0;
				}
				
				layers.get(textLayer).getChildren().add(text1.textBox);
			}
		}
		audioHandlerList = new ArrayList<AudioHandler>();
		// Call the AudioHanlder for each audio object
		if (audioCount != 0){
			for(int i = 0; i < audioCount; i++){
				AudioHandler audio1 = new AudioHandler(this, audio.get(i).getUrlName(), audio.get(i).getStartTime(), 
														audio.get(i).getDuration(), audio.get(i).getLoop());
				audioHandlerList.add(audio1);
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
	    HBox buttonBox = new HBox();
	    SlideButton();
	    buttonBox.getChildren().add(previousSlide);
        buttonBox.getChildren().add(exitSlide1);
        buttonBox.getChildren().add(nextSlide);
        buttonBox.getChildren().add(createTimer);
       
        // Put the buttons in the bottom centre of the slide
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setLayoutX((screenBounds.getWidth()- exitSlide1.getPrefWidth())/2);
        buttonBox.setLayoutY(screenBounds.getHeight()-200);
	    slideRoot.getChildren().addAll(layers);
	    
	    // Add the buttons to the slide
        slideRoot.getChildren().add(buttonBox);
        
        timerHbox = new HBox();
        timerList = new ArrayList<Timer>();
        
        //If timers were present on previous slide create new timers and resume from saved position
        if(currentTimerValues != null){
        	for(int l = 0; l < currentTimerValues.size(); l++){
        
			timer = new Timer(currentTimerValues.get(l).get(0), currentTimerValues.get(l).get(1), currentTimerValues.get(l).get(1));
        	timerList.add(timer);
        	
        	timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
				
				@Override
				public void handle(WorkerStateEvent event) {
					
					Platform.runLater( new Runnable(){
						public void run(){
							timerHbox.getChildren().add(timer.getPane());
						}
					});	 			
					numberOfTimers++;
				}
			});
			new Thread(timer).start();
			System.out.println("Timer Thread started");
        	}
        	
        	
        }
        
        slideRoot.getChildren().add(timerHbox);
	    
        // Show the new set of objects on the slide
	    slideRoot.setVisible(true);  
	}	

	/*
	 * Gets the highest layer assigned to content on the slide
	 */
	private Integer getMaxLayer(List<Image> images, List<TextBody> text,
			List<Audio> audio, List<Video> videos) {
		
		Integer totalLayers = 0;
		Integer layer;
	
		for(int currentImage = 0; currentImage < images.size(); currentImage++ ){
			layer = images.get(currentImage).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null){
				layer = 0;
			}
			
			if(layer > totalLayers){
				totalLayers = layer;
			}
			
		}
			
		for(Integer currentText = 0; currentText < text.size(); currentText++ ){
			
			layer = text.get(currentText).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null){
				layer = 0;
			}
			if(layer > totalLayers){
					totalLayers = layer;
			}
			
						
		}
		
		for(int currentAudio = 0; currentAudio < audio.size(); currentAudio++ ){
			layer = audio.get(currentAudio).getLayer();
			if(layer == null){
				layer = 0;
			}
			if(layer > totalLayers){
					totalLayers = layer;
			}
						
		}
		
		for(int currentVideo = 0; currentVideo < videos.size(); currentVideo++ ){
			
			layer = videos.get(currentVideo).getLayer();
			if(layer == null){
				layer = 0;
			}
			if(layer > totalLayers){
						totalLayers = layer;
			}
			
						
		}
		
	/*	for(int currentGraphics = 0; currentGraphics < graphics.size(); currentGraphics++ ){
			
			layer = graphics.get(currentGraphics).getLayer()
			if(layer == null){
				layer = 0;
			}
			if(layer > totalLayers){
					totalLayers = layer;
			}
						
		}*/
		
		return totalLayers;
		
		
	}
/*Creates the control buttons at the bottom of the slide and creates the event handlers for each button
 * 
 */
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
        
        createTimer = new Button("Add Timer");
        createTimer.setPrefWidth(80);
        createTimer.setPrefHeight(40);
		    
        /*Exit Slide when exit slide button is pressed*/
       
        exitSlide1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	
            	Node  source = (Node)  event.getSource();
            	Stage stage  = (Stage) source.getScene().getWindow();
            	Group root = (Group) stage.getScene().getRoot();
            	root.getChildren().clear();
            	new MainMenu(stage);
            	
            	//newSlide(nextSlideID, false, timerValues);
            	event.consume();
            	
            	
            	
            	
            }
        });      
        nextSlide.setOnAction(new EventHandler<ActionEvent>() {
            

			@Override
            public void handle(ActionEvent event) {
            	for(int h = 0; h < audioHandlerList.size(); h++){
            		
            		audioHandlerList.get(h).stopAudio();
            	}
            	timerValues = new ArrayList<List<Integer>>();
            	for(int g = 0; g<timerList.size(); g++){
            		
            		timerList.get(g).cancel();
            		 timerValues.add(timerList.get(g).getTimerValues()); 
            		 
            	}
            	newSlide(nextSlideID, false, timerValues);
            	event.consume();
            }
        });
        
        previousSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	newSlide(prevSlideID, false, timerValues);
            	event.consume();
            	
            }
        });
        
        createTimer.setOnAction(new EventHandler<ActionEvent>() {

			

			@Override
			public void handle(ActionEvent arg0) {
				timer = new Timer(null, null, null);
				timerList.add(timer);
				 
				timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
					
					@Override
					public void handle(WorkerStateEvent event) {
						
						Platform.runLater( new Runnable(){
							public void run(){
								timerHbox.getChildren().add(timer.getPane());
							}
						});	 			
						numberOfTimers++;
					}
				});
				new Thread(timer).start();
				System.out.println("Timer Thread started");
			}
        	
        });
        // Right and left buttons not working exactly as expected, something to do with the methods called
        // Same issues exist with buttons 
//		slideScene.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {  
//		    @Override
//		    public void handle(KeyEvent event) {
//		    	if(event.getCode() == KeyCode.RIGHT) {
//		    		System.out.println("Next slide");
//	            	newSlide(nextSlideID, false, timerValues);
//	            	event.consume();
//		    	}
//		    	else if (event.getCode() == KeyCode.LEFT) {
//		    		System.out.println("Previous slide");
//	            	newSlide(prevSlideID, false, timerValues);
//	            	event.consume();
//		    	}
//		    }
//		});
		slideScene.setOnKeyPressed(new EventHandler<KeyEvent>() {  
		    @Override
		    public void handle(KeyEvent event) {
		    	if(event.getCode() == KeyCode.RIGHT) {
		    		System.out.println("Next slide");
	            	newSlide(nextSlideID, false, timerValues);
	            	event.consume();
		    	}
		    	else if (event.getCode() == KeyCode.LEFT) {
		    		System.out.println("Previous slide");
	            	newSlide(prevSlideID, false, timerValues);
	            	event.consume();
		    	}
		    }
		});
	}
}
