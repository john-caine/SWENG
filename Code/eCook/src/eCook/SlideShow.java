/*
 * Programmer: Steve Thorpe, Jonathan Caine, Ankita Gangotra, Roger Tan and James Oatley
 * Date Created: 14/03/2014
 * Description: Creates new slideshow and slides from a parsed XML player with logic for setting the layer of all content and for moving between slides
 * 
 */

package eCook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import notes.NotesGUI;
import media.AudioControlBar;
import media.AudioHandler;
import media.GraphicHandler;
import media.ImageHandler;
import media.SlideMediaPlayer;
import media.SubSlideMedia;
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
import javafx.scene.control.Tooltip;
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
import media.VideoHandler;
import xmlfilepathhandler.XMLFilepathHandler;
import xmlparser.*;
import xmlrecipescaler.RecipeScale;
import xmlvalidation.XMLValidator;

public class SlideShow {

	protected Scene slideScene;
	protected Group slideRoot;
	public int currentSlideID; 
	public int nextSlideID; 
	public int prevSlideID;
	public int numOfSlidesIncBranch; 
	public int numOfSlidesExcBranch;
	protected int firstSlideID, lastSlideID;
	private XMLReader reader;
	private Recipe recipe;
	private Slide slide;
	protected Integer maxLayer, duration;
	private VBox timerbox;
	protected ArrayList<Timer> timerList;
	private ArrayList<TimerData> timerValues;
	private ArrayList<TextHandler> textHandlerList;
	private ArrayList<ImageHandler> imageHandlerList;
	private ArrayList<AudioHandler> audioHandlerList;
	private ArrayList<VideoHandler> videoHandlerList;
	private ArrayList<GraphicHandler> graphicsHandlerList;
	private VBox notesPanel;
	private VBox bottomPanel;
	private Timeline timeLineDuration;
	private Stage stage;
	protected static Logger logger;
	private RecipeCollection recipeCollection;
	protected XMLValidator validator;
	protected String backGroundColor;
	protected boolean onEndPage = false;
	protected boolean bottomPanelShowing = false;
	protected boolean notesPanelShowing = false;
	private int numberOfTimers = 0;
	private SlideShow slideShow = this;
	protected NotesGUI notesGUI;
	protected SlideControls slideControls;
	private ArrayList<SubSlideMedia> subSlideMediaList;
	private ArrayList<SlideMediaPlayer> slideMediaPlayerList;
	protected ArrayList<Pane> layers;
	
	/**
	 * Gets the number of slides in this slideShow, sets the background for the slides, runs the recipe through the FilePath
	 * handler so check/concert paths in recipe, scales each slide for the users unique monitor resolution, and configures
	 * parameters required for the stage.
	 * @param stage The stage to be used for the slideShow
	 * @param filepath The filepath of the recipe to be played
	 * @param recipeCollection The collection of recipes to be used
	 */
	public SlideShow(Stage stage, String filepath, RecipeCollection recipeCollection) {
		
		// Attach the recipe collection to this class
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

    	// Set the filepath required for the XML reader.
    	File recipePath = new File(System.getenv("localappdata") + "/eCook/Recipes");
    	reader = new XMLReader(recipePath + "/" + filepath);

    	// CAll the filePathHandler to convert/check paths for the recipe
    	XMLFilepathHandler filepathHandler = new XMLFilepathHandler();
    	reader = filepathHandler.setMediaPaths(reader);
    	
    	// If the paths are broken return the user to the MainMenu as we can't play this recipe
    	if (filepathHandler.mediaPathsAreBroken()) {
    		stage  = (Stage) slideScene.getWindow();
    		Group root = (Group) stage.getScene().getRoot();
    		root.getChildren().clear();
    		new MainMenu(stage, recipeCollection);
    	}
    	else {
    		// If there's no error with the recipe then grab it from the parser
    		recipe = reader.getRecipe();

    		// Scale the recipe for user resolution
    		RecipeScale recipeScale = new RecipeScale();
    		recipe = recipeScale.scaleRecipe(recipe);

    		// Get the total number of slides
    		numOfSlidesExcBranch = recipe.getNumberOfSlidesExcBranchSlides();
    		numOfSlidesIncBranch = recipe.getNumberOfSlidesIncBranchSlides();

    		// Set first & last slideIDs
    		firstSlideID  = 0;
    		lastSlideID = (numOfSlidesExcBranch - 1);

    		logger.log(Level.INFO, "Starting slideshow with slide index 0");
    		// set fullscreen here to ensure that the stage and scene bounds are the 
    		//	maximum possible when new slide is called
    		stage.setFullScreen(true);
    		stage.setFullScreenExitHint("Press ESC to return to the main menu.");
    		newSlide(0, false, null);

    		// Set the colour of the slide
    		backGroundColor = recipe.getDefaults().getBackgroundColor();
    		slideScene.setFill(Color.web(backGroundColor));
    		
    		// get started
    		stage.show();
    	}
	}
	
	/**
	 * Configures all the media handlers required for a new slide, plus the panel for the notes and timers, and the panel for the 
	 * slide control bar. Ensures that any previous media objects, timers, and notes, are dealt with appropiately, and sets a duration
	 * for the new slide.
	 * @param slideID The ID of the slide to be displayed
	 * @param isBranch Is this slide a branch slide?
	 * @param currentTimerValues ArrayList of data for any timers currently in use in the SlideShow
	 */
	public void newSlide(Integer slideID, Boolean isBranch, ArrayList<TimerData> currentTimerValues) {	
		List<Image> images;
		List<TextBody> text;
		List<Audio> audio;
		List<Shape> graphics;
		List<Video> videos;
		Pane layer;
		int imageCount, textCount, audioCount, videoCount, graphicCount;
		int fontSize;
		String fontColor, font, lineColor, fillColor;
		
		// save the current state of the panels before removing all objects
		if (notesGUI != null) {
			notesPanelShowing = notesGUI.getNotesPanelVisible();
		}
		if (slideControls != null) {
			bottomPanelShowing = slideControls.getBottomPanelVisible();
		}
		
		// Hide the slide then clear the current objects on the slide
		slideRoot.setVisible(false);
		slideRoot.getChildren().clear();
		
		// Check the slideID is valid and if not exit to MainMenu
		if (isBranch != true && (slideID > numOfSlidesExcBranch - 1 || slideID < 0)) {
			logger.log(Level.INFO, "End of SlideShow returning to main menu");
			new MainMenu(stage, recipeCollection);
		} else {
				// If branched slide set relevant globals
				if (isBranch == true) {	
					nextSlideID = currentSlideID;
					prevSlideID = currentSlideID;
					currentSlideID = slideID;
					logger.log(Level.INFO, "Branch Slide Globals set");
				} else {
					// Otherwise set them normally
					currentSlideID = slideID;
					nextSlideID = (slideID + 1);
					prevSlideID = (slideID - 1);
				}
				
				logger.log(Level.INFO, "newSlide() has been called with index {0}", currentSlideID);
				
				// Get the slide data and duration
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
				for(int currentLayer = 0; currentLayer <= maxLayer; currentLayer++) {	
					layer = new Pane();
					layers.add(currentLayer, layer);
				}
				
				// Create Arrays of the Media super class types to add the handlers to
				subSlideMediaList = new ArrayList<SubSlideMedia>();
				slideMediaPlayerList = new ArrayList<SlideMediaPlayer>();
				
				// Call the ImageHandler for each image object
				imageHandlerList = new ArrayList<ImageHandler>();
				if (imageCount != 0){
					for(int i = 0; i < imageCount; i++){
						ImageHandler image1 = new ImageHandler(this, images.get(i).getUrlName(), images.get(i).getXStart(), 
														images.get(i).getYStart(), images.get(i).getWidth(),
														images.get(i).getHeight(), images.get(i).getStartTime(), 
														images.get(i).getDuration(), images.get(i).getBranch(),
														images.get(i).getOrientation());
						imageHandlerList.add(image1);
						subSlideMediaList.add(image1);
						
						// Handle the layering aspects
						Integer imageLayer = images.get(i).getLayer();
						if (imageLayer == null) {
							imageLayer = 0;
						}
						layers.get(imageLayer).getChildren().add(image1.getHbox());
					}
				}
				
				
				// Call the TextHandler for each text object
				textHandlerList = new ArrayList<TextHandler>();
				if (textCount != 0){
					for(int i = 0; i < textCount; i++) {		
						
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
						subSlideMediaList.add(text1);
						
						// Handle the layering aspects
						Integer textLayer = text.get(i).getLayer();
						if (textLayer == null) {
							textLayer = 0;
						}		
						layers.get(textLayer).getChildren().add(text1.getHbox());
					}
				}			

				// Call the AudioHandler for each audio object
				audioHandlerList = new ArrayList<AudioHandler>();
				if (audioCount != 0){
					for(int i = 0; i < audioCount; i++){
						AudioHandler audio1 = new AudioHandler(this, audio.get(i).getUrlName(), audio.get(i).getStartTime(), 
																audio.get(i).getDuration(), audio.get(i).getLoop());
						
						audioHandlerList.add(audio1);
						slideMediaPlayerList.add(audio1);
						
						// Handle the layering aspects
						layers.get(0).getChildren().add(audio1.getHbox());
					}
				}
				
				// Call the VideoHandler for each video object
				videoHandlerList = new ArrayList<VideoHandler>();
				if (videoCount != 0){
					for(int i = 0; i < videoCount; i++){
						VideoHandler video1 = new VideoHandler(this,videos.get(i).getUrlName(), videos.get(i).getXStart(), 
														videos.get(i).getYStart(), videos.get(i).getWidth(),
														videos.get(i).getHeight(), videos.get(i).getLoop(),
														videos.get(i).getStartTime(), videos.get(i).getDuration());
						videoHandlerList.add(video1);
						slideMediaPlayerList.add(video1);
						
						// Handle the layering aspects
						layers.get(0).getChildren().add(video1.getHbox());
					}
				}
				
				// Call the GraphicHandler for each graphic object
				graphicsHandlerList = new ArrayList<GraphicHandler>();
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
					
						GraphicHandler graphic1 = new GraphicHandler(this, graphics.get(i).getTotalPoints(), 
														graphics.get(i).getWidth(), graphics.get(i).getHeight(), 
														graphics.get(i).getStartTime(), graphics.get(i).getDuration(), 
														fillColor, lineColor, graphics.get(i).getBranch(), 
														graphics.get(i).getPoints());
						graphicsHandlerList.add(graphic1);
						subSlideMediaList.add(graphic1);
						
						// Handle the layering aspects
						Integer graphicsLayer = graphics.get(i).getLayer();
						if (graphicsLayer == null){
							graphicsLayer = 0;
						}
						layers.get(graphicsLayer).getChildren().add(graphic1.getHbox());
					}
				}
				
				// Add all of the layers BEFORE THE PANELS AND TIMERS - These must sit on top of the slide content
			    slideRoot.getChildren().addAll(layers);
			    
			    // Add timers
			 	timerbox = new VBox(10);
			 		
			    // Create a notes panel each time new Slide is called
			 	notesGUI = new NotesGUI(recipe.getInfo().getTitle(), currentSlideID, slideRoot, timerbox, notesPanelShowing);
			 	notesPanel = notesGUI.getNotesPanel();
			 	slideRoot.getChildren().add(notesPanel);
			 	notesPanel.setLayoutX(-slideScene.getWidth()/5);
			 	notesPanel.setLayoutY(0);
			 	
			 	// Add the audio control bar if required
			 	 HBox audioBox = null;
			 	if (audioCount != 0) {
			 		AudioControlBar audioBar = new AudioControlBar(audioHandlerList, slideRoot);
			 		audioBox = audioBar.getControlBar();
			 	}
				
				// create a controls panel each time new slide is called
				slideControls = new SlideControls(slideRoot, bottomPanelShowing, audioBox);
				bottomPanel = slideControls.getBottomPanel();
				
				// set the size of the control panel: bigger if audioBar needs to be displayed
				if (audioCount == 0) {
					bottomPanel.setPrefSize(slideRoot.getScene().getWidth(), slideRoot.getScene().getHeight()/8);
				}
				else {
					bottomPanel.setPrefSize(slideRoot.getScene().getWidth(), slideRoot.getScene().getHeight()/5);
				}
				
				slideRoot.getChildren().add(bottomPanel);
				bottomPanel.setLayoutX(0);
				bottomPanel.setLayoutY(slideScene.getHeight());
				
				// set up the control panel buttons
				configureButtons(slideControls.getButtons());
				
		        // Add any existing timers to the notes panel, and resume them counting.
		        timerList = new ArrayList<Timer>();
		        resumeTimer(currentTimerValues);
		        
		        //Create duration timeline
		      	timeLineDuration = new Timeline();
		      		
		      	//When duration timeline has finished teardown any media objects on the slide, plus timers. 
		      	timeLineDuration.setOnFinished(new EventHandler<ActionEvent>(){
					public void handle(ActionEvent event) {
		            	timerValues = new ArrayList<TimerData>();
		            	for(int g = 0; g<timerList.size(); g++){          		
		            		timerList.get(g).cancel();
		            		timerValues.add(timerList.get(g).getTimerValues()); 	 
		            	}
		            	
		            	tearDownHandlers();
		            	
		            	// Then continue by calling the next slide
		            	newSlide(nextSlideID, false, timerValues);
		            	logger.log(Level.INFO, "New slide called by slide timeline");
		            	
		            	event.consume();
					}
		      	});
		      	
		      	// Create a 1 second keyFrame to use for the durationTimeLine
		      	createKeyFrame();
		      	
		      	// If a duration has been set for this slide then configure & start the durationTimeLine
		      	if(duration != null && duration != 0){
		      		timeLineDuration.setCycleCount(this.duration);
		      		timeLineDuration.playFromStart();
		      		logger.log(Level.INFO, "Starting Slide Timeline with duration: " + duration);
		      	}
		      	
		       // Set the new contents of the slide to be visible
			    slideRoot.setVisible(true);
			}
		

		  
	}	

	
	/**
	 * Gets the highest layer assigned to all content on the slide
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
	
		// Check for the highest layer set for Image objects
		for(int currentImage = 0; currentImage < images.size(); currentImage++ ) {
			layer = images.get(currentImage).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null) {
				layer = 0;
			}
			
			// If this layer is higher than a previous max layer we found update totalLayers
			if(layer > totalLayers) {
				totalLayers = layer;
			}
		}
			
		// Check for the highest layer set for Text objects
		for(Integer currentText = 0; currentText < text.size(); currentText++ ) {
			
			layer = text.get(currentText).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null) {
				layer = 0;
			}
			
			// If this layer is higher than a previous max layer we found update totalLayers
			if(layer > totalLayers) {
				totalLayers = layer;
			}				
		}
		
		// Check for the highest layer set for Audio objects
		for(int currentAudio = 0; currentAudio < audio.size(); currentAudio++ ) {
			layer = audio.get(currentAudio).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null) {
				layer = 0;
			}
			
			// If this layer is higher than a previous max layer we found update totalLayers
			if(layer > totalLayers) {
				totalLayers = layer;
			}				
		}
		
		// Check for the highest layer set for Video objects
		for(int currentVideo = 0; currentVideo < videos.size(); currentVideo++ ) {
			layer = videos.get(currentVideo).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null) {
				layer = 0;
			}
			
			// If this layer is higher than a previous max layer we found update totalLayers
			if(layer > totalLayers) {
				totalLayers = layer;
			}				
		}
		
		// Check for the highest layer set for Graphic objects
		for(int currentGraphics = 0; currentGraphics < graphics.size(); currentGraphics++ ) {	
			layer = graphics.get(currentGraphics).getLayer();
			
			//If layer not defined in XML, set to 0
			if(layer == null) {
				layer = 0;
			}
			
			// If this layer is higher than a previous max layer we found update totalLayers
			if(layer > totalLayers) {
				totalLayers = layer;
			}			
		}
		
		// Return the max layer number found
		return totalLayers;		
	}
	
	/**
	 * Method that adds event handlers to each of the buttons in the list.
	 * @param buttons List of buttons to be configured
	 */
	public void configureButtons(final List<Button> buttons) {
		// for buttons numbering, see SlideControls Class:
		
        // Exit Slide - stop everything, teardown, return to MainMenu
        buttons.get(6).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	timeLineDuration.stop();
            	tearDownHandlers();
            	exitToMainMenu(event);
            }
        });
        
        // First Slide - set timers, teardown, then go to first slide
        buttons.get(0).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {           	
            	
            	setTimerArray();
            	tearDownHandlers();
            	
            	//If on first slide
            	if (currentSlideID <= 0) {
            		buttons.get(0).setStyle("-fx-opacity: 0.75");
            	} else {
            		// if not, play the first slide
            		newSlide(firstSlideID, false, timerValues);
            	}
            	
            	event.consume();          	
            }
        });
        
        // Last Slide - stop, teardown, set timers, then go to last slide
        buttons.get(4).setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				timeLineDuration.stop();
				setTimerArray();
            	tearDownHandlers();

            	// If last slide
            	if (currentSlideID >= numOfSlidesIncBranch-1) {
            		buttons.get(4).setStyle("-fx-opacity: 0.75");
            	} else {
            		// if not, go to last slide
            		newSlide(lastSlideID, false, timerValues);
            	}
            	
            	event.consume();
            }
        });
        
        // Next Slide - stop, teardown, set timers, then call the next slide
        buttons.get(3).setOnAction(new EventHandler<ActionEvent>() {
			@Override
            public void handle(ActionEvent event) {
				timeLineDuration.stop();
				setTimerArray();
            	tearDownHandlers();
            		
            	newSlide(nextSlideID, false, timerValues);
            	
            	event.consume();
            }
        });
        
        // Previous Slide - stop, teardown, set timers, then call the previous slide
        buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {           	
            	timeLineDuration.stop();
            	setTimerArray();
            	tearDownHandlers();
            	
            	// return to the main menu if the previous slide is nothing (beginning of the slideshow)
            	if (prevSlideID <= -1) {
            		exitToMainMenu(event);
            	} else {
            		// if not, play the previous slide
            		newSlide(prevSlideID, recipe.getSlide(prevSlideID).getLastSlide(), timerValues);
            	}
            	
            	event.consume();          	
            }
        });
        
        // Add Timer 
        buttons.get(5).setOnAction(new EventHandler<ActionEvent>() {
        	@Override
        	public void handle(ActionEvent arg0) {
        		// Show the panel containing the timers if the timers button was pressed
        		notesGUI.showPanel(slideRoot);

        		// Stop us exceeding 4 timers as they don't fit in panel otherwise
        		if(numberOfTimers< 4){
        			// Create a new timer and add it to the list of them
        			final Timer timer = new Timer(null, null, null, null, null, null, null, numberOfTimers, slideShow, notesGUI, slideRoot);
        			timerList.add(timer);
        			timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

        				@Override
        				public void handle(WorkerStateEvent event) {

        					// Create a new thread to run the timer in
        					Platform.runLater( new Runnable(){
        						public void run(){
        							timerbox.getChildren().add(timer.getTimerID(), timer.getPane());
        							numberOfTimers ++;
        						}
        					});	 									
        				}
        			});

        			// Start the timer thread
        			new Thread(timer).start();	
        		}
        	}
        });
 
        // Pause - stop, teardown, set timers, then call the next slide
        buttons.get(2).setOnAction( new EventHandler<ActionEvent>(){
			private boolean paused;
			@Override
			//Pause all content on the slide
			public void handle(ActionEvent event) {
				if(paused == false){
					timeLineDuration.pause();
					
					// Pause the text objects
					for(int r = 0; r < textHandlerList.size(); r++){
						textHandlerList.get(r).pauseStartTimeTimeLine();
						textHandlerList.get(r).pauseDurationTimeLine();
					}
					
					// Pause the image objects
					for(int r = 0; r < imageHandlerList.size(); r++){
						imageHandlerList.get(r).pauseStartTimeTimeLine();
						imageHandlerList.get(r).pauseDurationTimeLine();
					}
					
					// Pause the graphics objects
					for(int r= 0; r < graphicsHandlerList.size(); r++){
						graphicsHandlerList.get(r).pauseStartTimeTimeLine();
						graphicsHandlerList.get(r).pauseDurationTimeLine();
					}
					
					// Pause the audio objects
					for(int r = 0; r< audioHandlerList.size(); r++){
						audioHandlerList.get(r).pauseMedia();
					}
					
					// Pause the video objects
					for(int r= 0; r< videoHandlerList.size(); r++){
						videoHandlerList.get(r).pauseMedia();
					}
					
					// Change the tooltip for the play button appropiately
					buttons.get(2).setId("SlidePlayBtn");
					buttons.get(2).setTooltip(new Tooltip("Click here to play slide"));
					paused = true;
				}
				//Resume all content on the slide
				else {
					timeLineDuration.play();
					
					// Resume the text objects
					for(int r = 0; r < textHandlerList.size(); r++){
						textHandlerList.get(r).resumeStartTimeTimeLine();
						textHandlerList.get(r).resumeDurationTimeLine();
					}
					
					// Resumed the image objects
					for(int r = 0; r < imageHandlerList.size(); r++){
						imageHandlerList.get(r).resumeStartTimeTimeLine();
						imageHandlerList.get(r).resumeDurationTimeLine();
					}
					
					// Resume the graphics objects 
					for(int r= 0; r < graphicsHandlerList.size(); r++){
						graphicsHandlerList.get(r).resumeStartTimeTimeLine();
						graphicsHandlerList.get(r).resumeDurationTimeLine();
					}
					
					// Resume the audio objects
					for(int r = 0; r< audioHandlerList.size(); r++){
						audioHandlerList.get(r).resumeStartTimeTimeLine();
						audioHandlerList.get(r).resumeMedia();
					}
					
					// Resume the video objects
					for(int r= 0; r< videoHandlerList.size(); r++){
						videoHandlerList.get(r).resumeStartTimeTimeLine();
						videoHandlerList.get(r).resumeMedia();
					}
					
					buttons.get(2).setId("SlidePauseBtn");	
					paused = false;
				}				
			}			
		});
        
        // key pressed - decide which one
		slideScene.setOnKeyPressed(new EventHandler<KeyEvent>() {  
		    @Override
		    public void handle(KeyEvent event) {
		    	
		    	// Right arrow key, so call new slide with nextSlideID
		    	if(event.getCode() == KeyCode.RIGHT) {
		    		logger.log(Level.INFO, "Right arrow pressed");
		    		setTimerArray();
	            	tearDownHandlers();
	            	newSlide(nextSlideID, false, timerValues);
	            	event.consume();
		    	} else if (event.getCode() == KeyCode.LEFT) {	    		
		    		// Left arrow key, so call new slide with prevSlideID
		    		logger.log(Level.INFO, "Left arrow pressed");
		    		setTimerArray();
		    		tearDownHandlers();
		    		newSlide(prevSlideID, false, timerValues);
	            	event.consume();
		    	} else if (event.getCode() == KeyCode.ESCAPE) {
		    		// Left arrow key, so call new slide with prevSlideID
		    		logger.log(Level.INFO, "Esc key pressed");
		    		tearDownHandlers();
	            	new MainMenu(stage, recipeCollection);
		    	}
		    }
		});
	}
	
	/**
	 * Counts 1 second intervals for the durationTimeLine
	 */
	private void createKeyFrame(){
		timeLineDuration.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {	
			@Override
			public void handle(ActionEvent event) {
				// no animation so nothing to do here
			}
		}));	
	}

	/**
	 * Method exits a slideShow by getting the current visible group, clears it, then calls a new MainMenu.
	 * @param event The event that occurred in the current stage
	 */
	 public void exitToMainMenu(ActionEvent event) {
		Node  source = (Node)  event.getSource();
     	Stage stage  = (Stage) source.getScene().getWindow();
     	Group root = (Group) stage.getScene().getRoot();
     	root.getChildren().clear();
     	new MainMenu(stage, recipeCollection);
     }

	 /**
	  * Decrease the number of timers by 1
	  */
	public void decrementNumberOfTimers() {
		numberOfTimers--;
		
	}

	/**
	 *  Method to return the timerBox
	 * @return the box containing the timers
	 */
	public VBox getTimerbox() {
		return timerbox;
	}
	
	/**
	 * Method to return the timer data
	 * @return The list of timer values
	 */
	public ArrayList<TimerData> getTimerData(){
		return timerValues;
	}
	
	/**
	 * Recreates timers for all the data contained in the currentTimerValues list, and sets them running again.
	 * @param currentTimerValues Data for any current timers in use in the slideShow
	 */
	public void resumeTimer(ArrayList<TimerData> currentTimerValues){
		   numberOfTimers = 0;
	        
		   //If timers were present on previous slide create new timers and resume from saved position
	        if(currentTimerValues != null){
	        	numberOfTimers = 0;
	        	
	        	// Loop over every timer that used to exist re-creating them
	        	for(int l = 0; l < currentTimerValues.size(); l++){
	        		
	        		// Create a new timer
	        		final Timer continueTimer = new Timer(currentTimerValues.get(l).getHours(), currentTimerValues.get(l).getMinutes(), 
	        												currentTimerValues.get(l).getSeconds(), currentTimerValues.get(l).getStartSeconds(),
	        												currentTimerValues.get(l).getStartMinutes(), currentTimerValues.get(l).getStartHours(),
	        												currentTimerValues.get(l).getLabel(), currentTimerValues.get(l).getTimerID(), slideShow, notesGUI, slideRoot);
					
	        		// Add the timer to the list, and increment the number of timers
	        		timerList.add(continueTimer);
					numberOfTimers ++;
					 
					continueTimer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
						
						// Create a new thread to to run the timers in
						@Override
						public void handle(WorkerStateEvent event) {		
							timerbox.getChildren().add(continueTimer.getTimerID(), continueTimer.getPane());
						}
					});
					
					// Start the thread
					new Thread(continueTimer).start();
				}
	        }
	}
	
	/**
	 * Call teardown methods associated with each handler object that is on the current slide.
	 */
	public void tearDownHandlers(){
		// Loop over the image, graphics, and text handlers
		for(int i = 0; i < subSlideMediaList.size(); i++){
			subSlideMediaList.get(i).tearDown();
		}
		
		// Loop over the video and audio handlers
		for(int i = 0; i < slideMediaPlayerList.size(); i++){
			slideMediaPlayerList.get(i).tearDown();
		}
		
		timeLineDuration.stop();
		logger.log(Level.INFO, "Slide timeline Stopped");
	}
	
	/**
	 * Add the timer data to the timerValues array
	 */
	private void setTimerArray(){
		timerValues = new ArrayList<TimerData>();
    	for(int g = 0; g<timerList.size(); g++){            		
    		timerList.get(g).cancel();
    		 timerValues.add(timerList.get(g).getTimerValues());             		 
    	}
	}
	
	/**
	 * Method to return the stage used in the slideShow
	 * @return stage The stage used in the slideShow
	 */
	public Stage getMainStage(){
		return stage;
	}
}
