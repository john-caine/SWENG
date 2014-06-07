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

	public SubSlideMedia(SlideShow parent, int xStart, int yStart, Integer startTime, Integer duration, Integer branchID, Integer orientation){
		super(xStart, yStart, startTime);
		logger = Logger.getLogger(eCook.class.getName());
		this.duration = duration;
		this.parent = parent;
		this.branchID = branchID;
		this.orientation = orientation;

		durationTimeLine = new Timeline();
		createDurationKeyFrame();
		
		setDurationTimeLineOnFinish();
		setTimeLineOnFinish();
		if(orientation != null){
			setOrientation();
		}

		if(branchID != null){
			doBranch();
		}


	}

	private void setDurationTimeLineOnFinish(){
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
					durationTimeLine.setCycleCount(duration);
					durationTimeLine.playFromStart();
			}	
		});
	}

	private void createDurationKeyFrame(){
		durationTimeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {

			}
		} ));

	}

	protected void doBranch() {
		logger.log(Level.INFO, "Branch added: " + branchID);
		hbox.setOnMouseClicked(new EventHandler<MouseEvent> (){
			public void handle(MouseEvent e) {
				parent.tearDownHandlers();
				parent.newSlide(branchID, true, parent.getTimerData());
			}
		});
		
		hbox.setOnMouseEntered(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				hbox.setEffect(new Glow());
				
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

	private void stopDurationTimeLine() {
		durationTimeLine.stop();
		logger.log(Level.INFO, "Duration Time Timeline Stopped ");
	}
	
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
	
	public void setOrientation() {
		hbox.setRotate(orientation);
	}
	
	public void tearDown() {
		stopStartTimeTimeLine();
		stopDurationTimeLine();	
	}
}
