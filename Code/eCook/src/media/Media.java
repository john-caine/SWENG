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

public abstract class Media {
	
	protected Timeline startTimeLine;
	protected int xStart;
	protected int yStart;
	protected Integer startTime;
	private Logger logger;
	protected HBox hbox;

	public Media (int xStart, int yStart, Integer startTime) {
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
	
	private void setLocation() {
		hbox.setLayoutX(xStart);
    	hbox.setLayoutY(yStart);
	}

	private void createKeyFrame(){	
		startTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			 @Override
			 public void handle(ActionEvent event) {
				 startTime--;	
			 }
		 } ));
	}
	 
	 public void pauseStartTimeTimeLine(){			
		 startTimeLine.pause();
		 logger.log(Level.INFO, "Start Time Timeline Paused ");
	 }
	 
	 public void stopStartTimeTimeLine() {
		startTimeLine.stop();
		logger.log(Level.INFO, "Start Time Timeline Stopped ");
	}
	 
	 public void resumeStartTimeTimeLine(){
		 if (hbox.isVisible() == false && startTimeLine.getStatus() != Status.STOPPED){
			 startTimeLine.play();
			 logger.log(Level.INFO, "Start Time Timeline Resumed ");
		 }
	 }
	 
	 public void showObject(){
		 hbox.setVisible(true);
	 }
	 
	 public void hideObject(){
		 hbox.setVisible(false);	 
	 }

	 public abstract void setTimeLineOnFinish();
	 
	 public abstract void tearDown();
}