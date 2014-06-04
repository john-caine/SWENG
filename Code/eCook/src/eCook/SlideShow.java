/*
 * Programmer: Steve Thorpe, Jonathan Caine
 * Date Created: 14/03/2014
 * Description: Creates new slideshow and slides from a parsed XML player with logic for setting the layer of all content and for moving between slides
 * 
 */

package eCook;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import notes.NotesGUI;
import audiohandler.AudioHandler;
import graphicshandler.GraphicsHandler;
import media.ImageHandler;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import media.TextHandler;
import timer.Timer;
import timer.TimerData;
import videohandler.VideoPlayerHandler;
import xmlRecipeScaler.RecipeScale;
import xmlValidation.XMLValidator;
import xmlparser.*;
import errorhandler.ErrorHandler;


public class SlideShow {

	// declare variables
	private Scene slideScene;
	private Group slideRoot;
	public int currentSlideID, nextSlideID, prevSlideID, numOfSlides; 
	private XMLReader reader;
	private Recipe recipe;
	private Slide slide;
	private Integer maxLayer, duration;
	private Timer timer;
	private VBox timerbox;
	private ArrayList<Timer> timerList;
	private ArrayList<TimerData> timerValues;
	private ArrayList<TextHandler> textHandlerList;
	private ArrayList<ImageHandler> imageHandlerList;
	private ArrayList<AudioHandler> audioHandlerList;
	private ArrayList<VideoPlayerHandler> videoHandlerList;
	private ArrayList<GraphicsHandler> graphicsHandlerList;
	private VBox notesPanel;
	private HBox controlPanel;
	private Timeline timeLineDuration;
	private Stage stage;
	static Logger logger;
	private RecipeCollection recipeCollection;
	private XMLValidator validator;
	String backGroundColor;
	boolean endPageReached = false;
	boolean controlPanelShowing = false;
	boolean notesPanelShowing = false;
	private int numberOfTimers = 0;
	private SlideShow slideShow = this;
	NotesGUI notesGUI;
	
	// constructor
	public SlideShow(Stage stage, String filepath, RecipeCollection recipeCollection) {
		
		// attach the recipe collection to this class
		this.recipeCollection = recipeCollection;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		this.stage = stage;
		// Create a new group for objects
		slideRoot = new Group();
		// Create a new scene for the slide show
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		slideScene =  new Scene (slideRoot, screenBounds.getWidth(), screenBounds.getHeight());

    	// Set the scene
    	stage.setScene(slideScene);
    	
    	reader = new XMLReader(filepath);
    	
    	// Check integrity of XML file, report error message if invalid
    	validator = new XMLValidator(reader);
    	if (validator.isXMLBroken()) {
    		stage.hide();
    		new ErrorHandler(validator.getErrorMsg());
		} else {
			// If there's no error with the recipe then grab it from the parser
			recipe = reader.getRecipe();
			
			// Scale the recipe for user resolution
			RecipeScale recipeScale = new RecipeScale();
			recipe = recipeScale.scaleRecipe(recipe);
			
			// Get the total number of slides without branch slides
			numOfSlides = recipe.getNumberOfSlides();

			// Call newSlide() to start displaying the side show from slide with
			// ID 0.
			// Change back to 0, 3 only for testing purposes.
			logger.log(Level.INFO, "Starting slideshow with slide index 0");
			// set fullscreen here to ensure that the stage and scene bounds are the 
			//	maximum possible when new slide is called
			stage.setFullScreen(true);
			stage.setFullScreenExitHint("Press ESC to return to the main menu.");
			newSlide(0, false, null);
			
			backGroundColor = recipe.getDefaults().getBackgroundColor();
			// Set the colour of the slide
			slideScene.setFill(Color.web(backGroundColor));
			// get started
	    	stage.show();
		}
	}
	
	public void newSlide(Integer slideID, Boolean isBranch, ArrayList<TimerData> currentTimerValues) {	
		List<Image> images;
		List<TextBody> text;
		List<Audio> audio;
		List<Shape> graphics;
		List<Video> videos;
		
		Pane layer;
		ArrayList<Pane> layers;
		
		
		int imageCount, textCount, audioCount, videoCount, graphicCount;
		int fontSize;
		String fontColor, font, lineColor, fillColor;
		
		// Clear the current objects on the slide
		slideRoot.setVisible(false);
		slideRoot.getChildren().clear();

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
		
		logger.log(Level.INFO, "newSlide() has been called with index {0}", currentSlideID);
				
		slide = recipe.getSlide(slideID);
		duration = slide.getDuration();
		
		// Get arrays containing the required objects
		images = slide.getContent().getImages();
		text = slide.getContent().getTexts();
		audio = slide.getContent().getAudios();
		videos = slide.getContent().getVideos();
		graphics = slide.getContent().getShapes();
		
		// Get how many objects of each type are required
		imageCount = images.size();
		textCount = text.size();
		audioCount = audio.size();
		videoCount = videos.size();
		graphicCount = graphics.size();
		
	
		//Get number of layers to be used on slide
		maxLayer = getMaxLayer(images, text, audio, videos, graphics);
		
		//Array list to store layers created for slide content
		layers = new ArrayList<Pane>();
		
		//Create a pane for each layer and add to the group
		for(int currentLayer = 0; currentLayer <= maxLayer; currentLayer++){
			
			layer = new Pane();
			layers.add(currentLayer, layer);
		}
		
		
		
		imageHandlerList = new ArrayList<ImageHandler>();
		// Call the ImageHandler for each image object
		if (imageCount != 0){
			for(int i = 0; i < imageCount; i++){
				ImageHandler image1 = new ImageHandler(this, images.get(i).getUrlName(), images.get(i).getXStart(), 
												images.get(i).getYStart(), images.get(i).getWidth(),
												images.get(i).getHeight(), images.get(i).getStartTime(), 
												images.get(i).getDuration(), images.get(i).getLayer(), null, null);
				imageHandlerList.add(image1);
				Integer imageLayer = images.get(i).getLayer();
				if (imageLayer == null){
					imageLayer = 0;
				}
				layers.get(imageLayer).getChildren().add(image1.getHbox());
			}
		}
		
		textHandlerList = new ArrayList<TextHandler>();
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
				
				TextHandler text1 = new TextHandler(this, text.get(i), font, text.get(i).getXStart(), 
											text.get(i).getYStart(), fontSize, fontColor, text.get(i).getXEnd(), 
											text.get(i).getYEnd(), text.get(i).getStartTime(), text.get(i).getDuration(), 
											text.get(i).getBranch(), text.get(i).getOrientation());
				
				textHandlerList.add(text1);
				
				Integer textLayer = text.get(i).getLayer();
				if (textLayer == null){
					textLayer = 0;
				}
				
				layers.get(textLayer).getChildren().add(text1.getHbox());
			}
		}
		audioHandlerList = new ArrayList<AudioHandler>();
		// Call the AudioHanlder for each audio object
		if (audioCount != 0){
			for(int i = 0; i < audioCount; i++){
				AudioHandler audio1 = new AudioHandler(this, audio.get(i).getUrlName(), audio.get(i).getStartTime(), 
														audio.get(i).getDuration(), audio.get(i).getLoop());
				
				audioHandlerList.add(audio1);
				layers.get(0).getChildren().add(audio1.mediaControl.overallBox);
			}
		}
		videoHandlerList = new ArrayList<VideoPlayerHandler>();
		// Call the VideoHandler for each video object
		if (videoCount != 0){
			for(int i = 0; i < videoCount; i++){
				VideoPlayerHandler video1 = new VideoPlayerHandler(this,videos.get(i).getUrlName(), videos.get(i).getXStart(), 
												videos.get(i).getYStart(), videos.get(i).getWidth(),
												videos.get(i).getHeight(), videos.get(i).getLoop(),
												videos.get(i).getStartTime(), videos.get(i).getDuration());
				videoHandlerList.add(video1);								
				layers.get(0).getChildren().add(video1.mediaControl.overallBox);
			}
		}
		graphicsHandlerList = new ArrayList<GraphicsHandler>();
		// Call the GraphicHandler for each graphic object
		if (graphicCount != 0){
			for(int i = 0; i < graphicCount; i++){
				
				// Retrieve any defaults that have been set
				if (graphics.get(i).getLineColor() == null) 
					lineColor = recipe.getDefaults().getLineColor();
				else 
					lineColor = graphics.get(i).getLineColor();
				
				if (graphics.get(i).getFillColor() == null)
					fillColor = recipe.getDefaults().getFillColor();
				else
					fillColor = graphics.get(i).getFillColor();
			
				GraphicsHandler graphic1 = new GraphicsHandler(this, graphics.get(i).getTotalPoints(), 
												graphics.get(i).getWidth(), graphics.get(i).getHeight(), 
												graphics.get(i).getStartTime(), graphics.get(i).getDuration(), 
												graphics.get(i).getLayer(), fillColor, lineColor,
												graphics.get(i).getBranch(), graphics.get(i).getPoints());
				graphicsHandlerList.add(graphic1);
				
				Integer graphicsLayer = graphics.get(i).getLayer();
				if (graphicsLayer == null){
					graphicsLayer = 0;
				}
				layers.get(graphicsLayer).getChildren().add(graphic1.graphicsBox);
			}
		}
		
		/*
		 * 	WELCOME TO THE PANELS AND OVERLAYS SECTION.
	 	 *
		 * 	The home of the notes panel and the controls panel.
		 */
		
		// Add all of the layers BEFORE THE PANELS AND TIMERS - These must sit on top of the slide content
	    slideRoot.getChildren().addAll(layers);
	    
	    // Add timers
	 	timerbox = new VBox();
	 		
	    // Create a notes panel each time new Slide is called.
	    notesGUI = new NotesGUI(recipe.getInfo().getTitle(), currentSlideID, slideRoot, timerbox);
	    notesPanel = notesGUI.getNotesPanel();
	    slideRoot.getChildren().add(notesPanel);
	    notesPanel.setLayoutX(-slideScene.getWidth()/5);
		notesPanel.setLayoutY(0);
		
		// create a controls panel each time new slide is called
		SlideControls slideControls = new SlideControls(slideRoot);
		controlPanel = slideControls.getControlPanel();
		slideRoot.getChildren().add(controlPanel);
	    controlPanel.setLayoutY(slideScene.getHeight());
		controlPanel.setLayoutX(0);
		// set up the control panel buttons
		configureButtons(slideControls.getButtons());
		
		/*
		 * 	THANKS FOR VISITING THE PANELS AND OVERLAYS SECTION
		 * 
		 *	We hope you had fun.
		 */
        
        //slideRoot.getChildren().add(getTimerHbox());
        timerList = new ArrayList<Timer>();
        numberOfTimers = 0;
        //If timers were present on previous slide create new timers and resume from saved position
        if(currentTimerValues != null){
        	for(int l = 0; l < currentTimerValues.size(); l++){
        		
        		final Timer continueTimer = new Timer(currentTimerValues.get(l).getHours(), currentTimerValues.get(l).getMinutes(), 
        												currentTimerValues.get(l).getSeconds(), currentTimerValues.get(l).getStartSeconds(),
        												currentTimerValues.get(l).getStartMinutes(), currentTimerValues.get(l).getStartHours(),
        												currentTimerValues.get(l).getLabel(), currentTimerValues.get(l).getTimerID(), slideShow, notesGUI.timeline);
				timerList.add(continueTimer);
				numberOfTimers ++;
				 
				continueTimer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
					
					@Override
					public void handle(WorkerStateEvent event) {		
						getTimerbox().getChildren().add(continueTimer.getTimerID(), continueTimer.getPane());
					}
				});
				new Thread(continueTimer).start();
				}
        }
        
      //Create duration timeline
      	timeLineDuration = new Timeline();
      		
      		//When duration timeline has finished remove the text. 
      	timeLineDuration.setOnFinished(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				for(int h = 0; h < audioHandlerList.size(); h++){		
            		audioHandlerList.get(h).stopAudio();
            	}
            	timerValues = new ArrayList<TimerData>();
            	for(int g = 0; g<timerList.size(); g++){          		
            		timerList.get(g).cancel();
            		 timerValues.add(timerList.get(g).getTimerValues()); 	 
            	}
            	newSlide(nextSlideID, false, timerValues);
            	event.consume();
			}
      	});
      	
      	createKeyFrame();
      	
      	if(duration != null){
      		timeLineDuration.setCycleCount(this.duration);
      		timeLineDuration.playFromStart();
      	}
      	else{
      		timeLineDuration.stop();
      	}
       
	    slideRoot.setVisible(true);  
	}	

	
	/*
	 * Gets the highest layer assigned to content on the slide
	 * 
	 * @Param images: list of image objects on slide
	 * @Param text: list of text objects on slide
	 * @Param audio: list of audio objects on slide
	 * @Param videos: list of video objects on slide
	 * @Param graphics: list of graphics objects on slide
	 */
	private Integer getMaxLayer(List<Image> images, List<TextBody> text,
			List<Audio> audio, List<Video> videos, List<Shape> graphics) {
		
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
		
		for(int currentGraphics = 0; currentGraphics < graphics.size(); currentGraphics++ ){
			
			layer = graphics.get(currentGraphics).getLayer();
			if(layer == null){
				layer = 0;
			}
			if(layer > totalLayers){
					totalLayers = layer;
			}			
		}
		return totalLayers;
		
		
	}
	
	/* Assigns event handlers to the buttons
	 * on the controls panel
	 */
	public void configureButtons(final List<Button> buttons) {
		// for buttons numbering, see SlideControls Class:
		/*
		 *	buttons[] = {play,pause,prev,next,exit,timer} 
		 */
		
        // Exit Slide
        buttons.get(3).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	timeLineDuration.stop();
            	for (int h = 0; h < audioHandlerList.size(); h++){
	    			audioHandlerList.get(h).mediaControl.mp.dispose();
            	}
            	for (int i = 0; i < videoHandlerList.size(); i++){
            		videoHandlerList.get(i).mediaControl.mp.dispose();
            	}
            	exitToMainMenu(event);
            }
        });
        
        // Next Slide
        buttons.get(2).setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				timeLineDuration.stop();
            	timerValues = new ArrayList<TimerData>();
            	for (int g = 0; g<timerList.size(); g++){
            		timerList.get(g).cancel();
            		timerValues.add(timerList.get(g).getTimerValues());            		 
            	}
            	for (int h = 0; h < audioHandlerList.size(); h++){
            		audioHandlerList.get(h).mediaControl.mp.dispose();
            	}
            	for (int i = 0; i < videoHandlerList.size(); i++){
            		videoHandlerList.get(i).mediaControl.mp.dispose();
            	}
            	for(int i = 0; i < textHandlerList.size(); i++){
            		textHandlerList.get(i).tearDown();
            	}

            	// return to the main menu if there are no more slides
            	if (nextSlideID >= numOfSlides) {
            		showEndOfSlideshowPage();
            	}
            	// if not, play the next slide
            	else {
            		newSlide(nextSlideID, false, timerValues);
            	}
            	event.consume();
            }
        });
        
        // Previous Slide
        buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {           	
            	timeLineDuration.stop();
            	timerValues = new ArrayList<TimerData>();
            	for(int g = 0; g<timerList.size(); g++){            		
            		timerList.get(g).cancel();
            		 timerValues.add(timerList.get(g).getTimerValues());             		 
            	}
            	
            	for (int h = 0; h < audioHandlerList.size(); h++){
	    			audioHandlerList.get(h).mediaControl.mp.dispose();
            	}
            	for (int i = 0; i < videoHandlerList.size(); i++){
            		videoHandlerList.get(i).mediaControl.mp.dispose();
            	}
            	// return to the main menu if the previous slide is nothing (beginning of the slideshow)
            	if (prevSlideID <= -1) {
            		exitToMainMenu(event);
            	}
            	// if not, play the previous slide
            	else {
            		newSlide(prevSlideID, false, timerValues);
            	}
            	event.consume();          	
            }
        });
        
        // Add Timer
        buttons.get(4).setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if(numberOfTimers< 4){
						timer = new Timer(null, null, null, null, null, null, null, numberOfTimers, slideShow,notesGUI.timeline);
						timerList.add(timer);
						 
						timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
							
							@Override
							public void handle(WorkerStateEvent event) {
								
								Platform.runLater( new Runnable(){
									public void run(){
										getTimerbox().getChildren().add(timer.getTimerID(), timer.getPane());								
									}
								});	 									
							}
						});
						new Thread(timer).start();				
				}
				numberOfTimers ++;
			}
        });
 
        // Pause
        buttons.get(0).setOnAction( new EventHandler<ActionEvent>(){
			private boolean paused;
			@Override
			//Pause all content on the slide
			public void handle(ActionEvent event) {
				if(paused == false){
					timeLineDuration.pause();
					for(int r = 0; r < textHandlerList.size(); r++){
						textHandlerList.get(r).pauseStartTimeTimeLine();
						textHandlerList.get(r).pauseDurationTimeLine();
						
					}
					for(int r = 0; r < imageHandlerList.size(); r++){
						imageHandlerList.get(r).pauseStartTimeTimeLine();
						imageHandlerList.get(r).pauseDurationTimeLine();
					}
					for(int r= 0; r < graphicsHandlerList.size(); r++){
						graphicsHandlerList.get(r).pause();
					}
					for(int r = 0; r< audioHandlerList.size(); r++){
						audioHandlerList.get(r).mediaControl.pauseStartTime();
					}
					for(int r= 0; r< videoHandlerList.size(); r++){
						videoHandlerList.get(r).mediaControl.pauseStartTime();
					}
					buttons.get(0).setId("SlidePlayBtn");
					paused = true;
				}
				//Resume all content on the slide
				else{
					timeLineDuration.play();
					for(int r = 0; r < textHandlerList.size(); r++){
						textHandlerList.get(r).resumeStartTimeTimeLine();
						textHandlerList.get(r).resumeDurationTimeLine();
					}
					for(int r =0; r < imageHandlerList.size(); r++){
						imageHandlerList.get(r).resumeStartTimeTimeLine();
						imageHandlerList.get(r).resumeDurationTimeLine();
					}
					for(int r= 0; r < graphicsHandlerList.size(); r++){
						graphicsHandlerList.get(r).resume();
					}
					for(int r = 0; r< audioHandlerList.size(); r++){
						audioHandlerList.get(r).mediaControl.resumeStartTime();
					}
					for(int r= 0; r< videoHandlerList.size(); r++){
						videoHandlerList.get(r).mediaControl.resumeStartTime();
					}
					buttons.get(0).setId("SlidePauseBtn");			        
					paused = false;
				}				
			}			
		});
        
		slideScene.setOnKeyPressed(new EventHandler<KeyEvent>() {  
		    @Override
		    public void handle(KeyEvent event) {
		    	if(event.getCode() == KeyCode.RIGHT) {
		    		timeLineDuration.stop();
		    		for (int h = 0; h < audioHandlerList.size(); h++){
		    			audioHandlerList.get(h).mediaControl.mp.dispose();
	            	}
	            	for (int i = 0; i < videoHandlerList.size(); i++){
	            		videoHandlerList.get(i).mediaControl.mp.dispose();
	            	}
		    		System.out.println("Next slide");
		    		// return to the main menu if there are no more slides
	            	if (nextSlideID >= numOfSlides) {
	            		showEndOfSlideshowPage();
	            	}
	            	// if not, play the next slide
	            	else {
	            		newSlide(nextSlideID, false, timerValues);
	            	}
	            	event.consume();
		    	}
		    	else if (event.getCode() == KeyCode.LEFT) {
		    		timeLineDuration.stop();
		    		for (int h = 0; h < audioHandlerList.size(); h++){
		    			audioHandlerList.get(h).mediaControl.mp.dispose();
	            	}
	            	for (int i = 0; i < videoHandlerList.size(); i++){
	            		videoHandlerList.get(i).mediaControl.mp.dispose();
	            	}
		    		System.out.println("Previous slide");
		    		// return to the main menu if the previous slide is nothing (beginning of the slideshow)
	            	if (prevSlideID <= -1) {
	            		new MainMenu(stage, recipeCollection);
	            	}
	            	// if not, play the previous slide (or the last slide if end page reached)
	            	else {
	            		if (endPageReached) {
	            			// reset the background colour to default
	            			slideScene.setFill(Color.web(backGroundColor));
	            			newSlide(currentSlideID, false, timerValues);
	            		}
	            		else {
	            			newSlide(prevSlideID, false, timerValues);
	            		}
	            	}
	            	event.consume();
		    	}
		    	else if (event.getCode() == KeyCode.ESCAPE) {
		    		timeLineDuration.stop();
		    		for (int h = 0; h < audioHandlerList.size(); h++){
		    			audioHandlerList.get(h).mediaControl.mp.dispose();
	            	}
	            	for (int i = 0; i < videoHandlerList.size(); i++){
	            		videoHandlerList.get(i).mediaControl.mp.dispose();
	            	}
	            	new MainMenu(stage, recipeCollection);
		    	}
		    }
		});
	}
	
	 private void createKeyFrame(){
			timeLineDuration.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){	
				@Override
				public void handle(ActionEvent event) {
						
				}
			} ));	
	}
	 
	 // show a message at the end of the slideshow.
	 public void showEndOfSlideshowPage() {
		slideRoot.getChildren().clear();
		slideScene.setFill(Color.BLACK);
		Label endOfShowLabel = new Label("End of slideshow. Press 'esc' to return to the main menu.");
		endOfShowLabel.setTextFill(Color.WHITE);
		slideRoot.getChildren().addAll(endOfShowLabel);
		endPageReached = true;
	 }
	 
	 // exit slideshow to main menu
	 public void exitToMainMenu(ActionEvent event) {
		Node  source = (Node)  event.getSource();
     	Stage stage  = (Stage) source.getScene().getWindow();
     	Group root = (Group) stage.getScene().getRoot();
     	root.getChildren().clear();
     	new MainMenu(stage, recipeCollection);
     }

	public void decrementNumberOfTimers() {
		numberOfTimers--;
		
	}

	public VBox getTimerbox() {
		return timerbox;
	}
	
	public ArrayList<TimerData> getTimerData(){
		return timerValues;
	}

	
}
