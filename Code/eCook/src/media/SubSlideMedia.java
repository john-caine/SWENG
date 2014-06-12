/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: SubSlideMedia class which constructs the duration timeline and provides orientation for the Images
 * Graphics and Text Handlers.
 */

package media;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.effect.Glow;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import eCook.SlideShow;
import eCook.eCook;

public abstract class SubSlideMedia extends SlideMedia {

	protected Timeline durationTimeLine;
	private Integer duration;
	private SlideShow parent;
	private Integer branchID;
	private Logger logger;
	private Integer orientation;
	
	/**
	 * Sub Slide Media Constructor
	 * @Param parent: The reference to the slideshow which called the handler
	 * @Param xStart: The x co ordinate of the top left corner of the hBox
	 * @Param yStart: The y co ordinate of the top left corner of the hBox
	 * @Param startTime: The time the media is to appear on the screen
	 * @Param duration: The time the media is to disappear from the screen
	 * @Param branchID: The slide the media is to branch to
	 * @Param orientation: The orientation of the media in degrees
	 */

	public SubSlideMedia(SlideShow parent, int xStart, int yStart, Integer startTime, Integer duration, Integer branchID, Integer orientation){
		super(xStart, yStart, startTime);
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		this.duration = duration;
		this.parent = parent;
		this.branchID = branchID;
		this.orientation = orientation;
		
		//Create the duration Timeline
		durationTimeLine = new Timeline();
		
		//Create the duration key frame
		createDurationKeyFrame();
		
		//Set the duration Timeline on finish
		setDurationTimeLineOnFinish();
		
		//Set the startTime Timeline on finish
		setTimeLineOnFinish();
		
		//If there is an orientation value set the orientation
		if(orientation != null){
			setOrientation();
		}
		
		//If the branchID is set, set braching
		if(this.branchID != null){
			setOnBranch();
		}
	}

	/**
	 * When the duration Timeline is finished hide the media object
	 */
	private void setDurationTimeLineOnFinish(){
		durationTimeLine.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				hideObject();	
			}	
		});
	}

	/**
	 * When the startTime Timeline is finished show the object and if the duration is set set start 
	 * the duration Timeline
	 */
	public void setTimeLineOnFinish(){

		startTimeLine.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showObject();	

				if(duration != null)
					durationTimeLine.setCycleCount(duration);
					durationTimeLine.playFromStart();
			}	
		});
	}

	/**
	 * Add a keyframe with a duration of 1 second to the Duration Timeline
	 */
	private void createDurationKeyFrame(){
		durationTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {

			}
		} ));

	}

	/**
	 *Log the Branch ID and then set the hBox mouse event handler
	 */
	protected void setOnBranch() {
		logger.log(Level.INFO, "Branch added: " + branchID);
		//When mouse clicked branch to the branch slide
		hbox.setOnMouseClicked(new EventHandler<MouseEvent> (){
			public void handle(MouseEvent e) {
				doBranch();
			}
		});
		// When the mouse enters set the media to glow
		hbox.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				hbox.setEffect(new Glow());
				
			}
			
		});
	}

	/**
	 * Pause the duration Timeline and log the event
	 */
	public void pauseDurationTimeLine(){
		durationTimeLine.pause();
		logger.log(Level.INFO, "Duration Time Timeline Paused ");
	}

	/**
	 * Resume the Duration Timeline and log the event
	 */
	public void resumeDurationTimeLine(){
		if (hbox.isVisible() == true && durationTimeLine.getStatus() != Status.STOPPED){
			durationTimeLine.play();
			logger.log(Level.INFO, "Duration Time Timeline Resumed ");
		}
	}

	/**
	 * Stop the duration Timeline and log the event
	 */
	private void stopDurationTimeLine() {
		durationTimeLine.stop();
		logger.log(Level.INFO, "Duration Time Timeline Stopped ");
	}
	
	/**
	 * Set the startTime Timeline if the value is not null
	 * If duration is not null show the hBox and then start the duration time line
	 * If both are null just show the hBox
	 */
	protected void setTimingValues(){
		if(startTime != null){
  			startTimeLine.setCycleCount(startTime);
  			startTimeLine.playFromStart();
  			logger.log(Level.INFO, "Starting StartTime time line ");
  		}
  		//Show image and begin duration timeline 
  		else if(duration != null){
  			showObject();
  			durationTimeLine.setCycleCount(duration);
  			durationTimeLine.playFromStart();
  			logger.log(Level.INFO, "Starting Duration time line ");
  		}
  		else{
  			showObject();
  		}  
		
	}
	
	/**
	 * Set the orientation of the hbox
	 */
	public void setOrientation() {
		hbox.setRotate(orientation);
	}
	
	/**
	 * Stop all of the handlers currently running on the slide, call the new slide with the branchID and log the event
	 */
	protected void doBranch(){
		parent.tearDownHandlers();
		parent.newSlide(branchID, true, parent.getTimerData());
		logger.log(Level.INFO, "Branched to Slide:"  + branchID);
	}
	
	/**
	 *Stop the startTime and duration Timelines
	 */
	public void tearDown() {
		stopStartTimeTimeLine();
		stopDurationTimeLine();	
	}
}
