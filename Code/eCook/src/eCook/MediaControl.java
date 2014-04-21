package eCook;


import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.util.Duration;

public abstract class MediaControl {
	
	public MediaPlayer mp;
    protected Duration duration;
    protected Slider timeSlider, timeSlider1;
    protected Label playTime, playTime1;
    protected Slider volumeSlider;
	public VBox box;
	protected int mpWidth;
	public int mpHeight;
	protected Rectangle2D bounds;
	public MediaView mediaView;
	protected Integer startTime;
	protected Integer playDuration;
	protected Boolean loop1;
	public HBox mediaBar;
	
	protected abstract void additionalSetup();
	
	public MediaControl( MediaPlayer mp, Integer width, Integer height, Boolean loop, Integer startTime, Integer playDuration){
		
		this.mp = mp;
		this.startTime = startTime;
		this.playDuration = playDuration;
		mediaView = new MediaView(mp);
		
		bounds = Screen.getPrimary().getVisualBounds();
		
		if (loop == null)
			this.loop1 = false;
		else
			this.loop1 = loop;
		
		setLoop(loop1);
		
		if (width != null && height != null){
			this.mpWidth = width;
			this.mpHeight = height;
			mediaView.setPreserveRatio(false);
            mediaView.setFitWidth(mpWidth);
            mediaView.setFitHeight(mpHeight-35);
		}
		else{
			this.mpWidth = (int) (bounds.getWidth()/2);
			this.mpHeight = (int) (bounds.getHeight()/4);
			mediaView.setPreserveRatio(true);
            mediaView.setFitWidth(mpWidth);	
		}
		
		if (startTime == null) {
	        this.startTime = 0;
	        new Thread(startTimerThread).start();
	    }
	    else 
	        new Thread(startTimerThread).start();
		 
		box = new VBox();
		HBox viewBox = new HBox();
		viewBox.getChildren().add(mediaView);
		box.getChildren().add(viewBox);
			
		mediaBar = new HBox();
		mediaBar.setMaxWidth(mpWidth);
	    mediaBar.setPadding(new Insets(5, 10, 5, 10));
	    
	    additionalSetup();
	}
	

	
	    protected void updateValues() {
	        if (playTime != null && timeSlider != null && volumeSlider != null)  {
	            Platform.runLater(new Runnable() {

	                public void run() {
	                    Duration currentTime = mp.getCurrentTime();
	                    playTime.setText(formatTime(currentTime, mp.getMedia().getDuration()));
	                    playTime1.setText(formatTime(currentTime, mp.getMedia().getDuration()));
	                    if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
	                        timeSlider.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
	                    }
	                    if (!timeSlider1.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider1.isValueChanging()) {
	                        timeSlider1.setValue(currentTime.divide(duration.toMillis()).toMillis()*100);
	                    }
	                    if (!volumeSlider.isValueChanging()) {
	                        volumeSlider.setValue((int) Math.round(mp.getVolume() * 100));
	                    }
	                }
	            });
	        }
	    }

	    private static String formatTime(Duration elapsed, Duration duration) {
	        int intElapsed = (int) Math.floor(elapsed.toSeconds());
	        int elapsedHours = intElapsed / (60 * 60);
	        if (elapsedHours > 0) {
	            intElapsed -= elapsedHours * 60 * 60;
	        }
	        int elapsedMinutes = intElapsed / 60;
	        int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 - elapsedMinutes * 60;

	        if (duration.greaterThan(Duration.ZERO)) {
	            int intDuration = (int) Math.floor(duration.toSeconds());
	            int durationHours = intDuration / (60 * 60);
	            if (durationHours > 0) {
	                intDuration -= durationHours * 60 * 60;
	            }
	            int durationMinutes = intDuration / 60;
	            int durationSeconds = intDuration - durationHours * 60 * 60 - durationMinutes * 60;

	            if (durationHours > 0) {
	                return String.format("%d:%02d:%02d/%d:%02d:%02d",
	                        elapsedHours, elapsedMinutes, elapsedSeconds,
	                        durationHours, durationMinutes, durationSeconds);
	            } else {
	                return String.format("%02d:%02d/%02d:%02d",
	                        elapsedMinutes, elapsedSeconds,
	                        durationMinutes, durationSeconds);
	            }
	        } else {
	            if (elapsedHours > 0) {
	                return String.format("%d:%02d:%02d",
	                        elapsedHours, elapsedMinutes, elapsedSeconds);
	            } else {
	                return String.format("%02d:%02d",
	                        elapsedMinutes, elapsedSeconds);
	            }
	        }
	    }
	    
	   public Task<Object> startTimerThread = new Task<Object>() {
 	    	
 			@Override
 			protected Object call() throws Exception {
 				TimeUnit.SECONDS.sleep(startTime);
 			 
 				Platform.runLater (new Runnable() {
 					public void run(){
 						mp.play();
 					}
 				});
 				if (playDuration != null && playDuration != 0)
 					mp.setStopTime(Duration.seconds(playDuration));
 				return null;
 			}
   	    };
   	    
   	 Task<Object> durationTimerThread = new Task<Object>() {
 		
		 @Override
		protected Object call() throws Exception {
			int count=0;
			while (count <= playDuration) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
			}
				 
			Platform.runLater( new Runnable(){
				public void run(){
					mp.stop();
					if(loop1){
						mp.play();
					}
				}
			});	 
			return null;
		}
	};
	
	public void setLoop(boolean loop) {
		if (loop){
			mp.setCycleCount(MediaPlayer.INDEFINITE);
		}else
			mp.setCycleCount(1);
	}

}
