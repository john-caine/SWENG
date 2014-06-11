/*
 * Programmer: Steve Thorpe & Jonathan Caine
 * Date Created: 04/06/2014
 * Description: Abstract SlideMedia class, creates the HBox and startTime Timeline. Provides methods for setting the location of the HBox 
 * 				and for controlling the startTime Timeline.
 */

package media;

import java.util.logging.Level;
import java.util.logging.Logger;
import eCook.eCook;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public abstract class SlideMedia {
	
	protected Timeline startTimeLine;
	protected int xStart;
	protected int yStart;
	protected Integer startTime;
	private Logger logger;
	protected HBox hbox;
	
	/**
	 * SlideMedia Constructor
	 * @Param xStart: The x co-ordinate of the HBox
	 * @Param yStart: The y co-ordinate of the HBox
	 * @Param startTime: The value the start time timeline is to count from.
	 */
	public SlideMedia (int xStart, int yStart, Integer startTime) {
		this.xStart = xStart;
		this.yStart = yStart;
		this.startTime = startTime;
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		startTimeLine = new Timeline();
		createKeyFrame();
		hbox = new HBox();
		hbox.setVisible(false);
		setLocation();
		
	}
	/**
	 * Sets the XY co-ordinates of the HBox
	 */
	private void setLocation() {
		hbox.setLayoutX(xStart);
    	hbox.setLayoutY(yStart);
	}
	
	/**
	 * Adds a 1 second keyframe to the Timeline, which decrements the startTime value
	 */
	private void createKeyFrame(){	
		startTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			 @Override
			 public void handle(ActionEvent event) {
				 startTime--;	
			 }
		 } ));
	}
	 
	/**
	 * Pauses the startTimeline
	 */
	 public void pauseStartTimeTimeLine(){			
		 startTimeLine.pause();
		 logger.log(Level.INFO, "Start Time Timeline Paused ");
	 }
	 
	/**
	 * Stops the startTimeline
	 */
	 protected void stopStartTimeTimeLine() {
		startTimeLine.stop();
		logger.log(Level.INFO, "Start Time Timeline Stopped ");
	}
	 
	/**
	 * Resumes the startTimeline if startTimeline has not finished 
	 */
	 public void resumeStartTimeTimeLine(){
		 if (hbox.isVisible() == false && startTimeLine.getStatus() != Status.STOPPED){
			 startTimeLine.play();
			 logger.log(Level.INFO, "Start Time Timeline Resumed ");
		 }
	 }
	 
	 /**
	  * Displays the hbox when added on a slide
	  */
	 protected void showObject(){
		 hbox.setVisible(true);
	 }
	 
	 /**
	  * Hides the hbox when added on a slide
	  */
	 protected void hideObject(){
		 hbox.setVisible(false);	 
	 }
	 
	 /**
	  * Gets the hbox
	  * @Return hbox: The main hbox used to contain all handler content on a slide
	  */
	 public HBox getHbox(){
		 return hbox;
	 }

	 /**
	  * To to be used to set what happens the startTimeline Timeline has finished
	  */
	 protected abstract void setTimeLineOnFinish();
	 
	 /**
	  * To be used to tidy up a handler for when the slideshow changes to the next slide
	  */
	 protected abstract void tearDown();
	 
	 /**
	  * To be used to set and start any Timelines used within the inheriting class.
	  */
	 protected abstract void setTimingValues();
}