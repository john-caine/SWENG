package media;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import eCook.SlideShow;
import eCook.eCook;

public abstract class SubMedia extends Media {

	private Timeline durationTimeLine;
	private Integer duration;
	private SlideShow parent;
	private Integer branchID;
	private Logger logger;
	private Integer orientation;

	public SubMedia(SlideShow parent, int xStart, int yStart, Integer startTime, Integer duration, Integer branchID, Integer orientation){
		super(xStart, yStart, startTime);
		logger = Logger.getLogger(eCook.class.getName());
		this.duration = duration;
		this.parent = parent;
		this.branchID = branchID;
		this.orientation = orientation;

		durationTimeLine = new Timeline();
		createDurationKeyFrame();
		
		if(orientation != null){
			setOrientation();
		}
		
		if(branchID != null){
			doBranch();
		}


	}

	public void setDurationTimeLineOnFinish(){
		durationTimeLine.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				hideObject();	
			}	
		});
	}

	public void setTimeLineOnFinish(){

		startTimeLine.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				showObject();	

				if(duration != null)
					durationTimeLine.playFromStart();
			}	
		});
	}

	public void createDurationKeyFrame(){
		durationTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				duration--;	
			}
		} ));

	}

	public void doBranch() {
		hbox.setOnMouseClicked(new EventHandler<MouseEvent> (){
			public void handle(MouseEvent e) {
				parent.newSlide(branchID, true, parent.getTimerData());
			}
		});
	}

	public void pauseDurationTimeLine(){
		durationTimeLine.pause();
		logger.log(Level.INFO, "Duration Time Timeline Paused ");
	}

	public void resumeDurationTimeLine(){
		if (hbox.isVisible() == true && durationTimeLine.getStatus() != Status.STOPPED){
			durationTimeLine.play();
			logger.log(Level.INFO, "Duration Time Timeline Resumed ");
		}
	}

	public void stopDurationTimeLine() {
		durationTimeLine.stop();
		logger.log(Level.INFO, "Duration Time Timeline Stopped ");
	}
	
	public void setTimeLines(){
		if(startTime != null){
  			startTimeLine.setCycleCount(this.startTime);
  			if(this.duration == null){
  				this.duration = 0;
  			}
  			durationTimeLine.setCycleCount(this.duration);
  			startTimeLine.playFromStart();
  		}
  		//Show image and begin duration timeline 
  		else if(duration != null){
  			showObject();
  			durationTimeLine.setCycleCount(this.duration);
  			durationTimeLine.playFromStart();
  		}
  		else{
  			showObject();
  		}  
		
	}
	
	public void setOrientation() {
		hbox.setRotate(orientation);
	}
}
