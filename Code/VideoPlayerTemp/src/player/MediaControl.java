package player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;



public class MediaControl {
	
	private MediaPlayer mp;
    private boolean stopRequested = false;
    private boolean atEndOfMedia = false;
    private Duration duration;
    private Slider timeSlider, timeSlider1;
    private Label playTime, playTime1;
    private Slider volumeSlider;
	public VBox box;
	private InputStream inputStream;
	private Image image, image1, image2, image3;
	private Stage stage, stage1;
	private int mpWidth;
	private int mpHeight;
	private Rectangle2D bounds;
	private MediaView mediaView;
	private HBox hbox;
	private Button playButton, playButton1;
	private FadeTransition fadeTransition;
	private Integer startTime;
	private Integer playDuration;
	private Boolean loop1;
	
	public MediaControl(final MediaPlayer mp, Integer width, Integer height, Boolean loop, Integer startTime, Integer playDuration){
		
		this.mp = mp;
		this.startTime = startTime;
		this.playDuration = playDuration;
		this.loop1 = loop;
		//setLoop(loop1);
		
		bounds = Screen.getPrimary().getVisualBounds();
		
		if(width == null)
			this.mpWidth = (int) (bounds.getWidth()/2);
		else
			this.mpWidth = width;
		
		if(height == null)
			this.mpHeight = (int) (bounds.getHeight()/4);
		else
			this.mpHeight = height;
		
		if (startTime == null) {
	        this.startTime = 0;
	        new Thread(startTimerThread).start();
	    }
	    else 
	       new Thread(startTimerThread).start();
		 
		box = new VBox();
		HBox viewBox = new HBox();
		mediaView = new MediaView(mp);
		viewBox.getChildren().add(mediaView);
		box.getChildren().add(viewBox);
			
		final HBox mediaBar = new HBox();
	    mediaBar.setPadding(new Insets(5, 10, 5, 10));
			try {
				inputStream = new FileInputStream("../Resources/play.png");
				image = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/pause.png");
				image1 = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/stop.png");
				image2 = new Image(inputStream);
				inputStream = new FileInputStream("../Resources/fullscreen.png");
				image3 = new Image(inputStream);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        playButton = new Button();
	        playButton.setGraphic(new ImageView(image));
	        playButton.setOnAction(new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent e) {
	                Status status = mp.getStatus();

	                if (status == Status.UNKNOWN
	                        || status == Status.HALTED) {
	                    System.out.println("Player is in a bad or unknown state, can't play.");
	                    return;
	                }

	                if (status == Status.PAUSED
	                        || status == Status.READY
	                        || status == Status.STOPPED) {
	                    // rewind the movie if we're sitting at the end
	                    if (atEndOfMedia) {
	                        mp.seek(mp.getStartTime());
	                        atEndOfMedia = false;
	                        playButton.setGraphic(new ImageView(image));
	                    }
	                    mp.play();
	                    playButton.setGraphic(new ImageView(image1));
	                } else {
	                    mp.pause();
	                    playButton.setGraphic(new ImageView(image));
	                }
	            }
	        });
	        
	        playButton1 = new Button();
	        playButton1.setGraphic(new ImageView(image));
	        playButton1.setOnAction(new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent e) {
	                Status status = mp.getStatus();

	                if (status == Status.UNKNOWN
	                        || status == Status.HALTED) {
	                    System.out.println("Player is in a bad or unknown state, can't play.");
	                    return;
	                }

	                if (status == Status.PAUSED
	                        || status == Status.READY
	                        || status == Status.STOPPED) {
	                    // rewind the movie if we're sitting at the end
	                    if (atEndOfMedia) {
	                        mp.seek(mp.getStartTime());
	                        atEndOfMedia = false;
	                        playButton1.setGraphic(new ImageView(image));
	                    }
	                    mp.play();
	                    playButton1.setGraphic(new ImageView(image1));
	                } else {
	                    mp.pause();
	                    playButton1.setGraphic(new ImageView(image));
	                }
	            }
	        });
	        mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
	            @Override
	            public void changed(ObservableValue<? extends Duration> observableValue, Duration duration, Duration current) {
	                updateValues();
	            }
	        });

	        mp.setOnPlaying(new Runnable() {

	            public void run() {
	                if (stopRequested) {
	                    mp.pause();
	                    stopRequested = false;
	                } else {
	                	playButton.setGraphic(new ImageView(image1));
	                	playButton1.setGraphic(new ImageView(image1));
	                }
	            }
	        });

	        mp.setOnPaused(new Runnable() {

	            public void run() {
	            	playButton.setGraphic(new ImageView(image));
	            	playButton1.setGraphic(new ImageView(image));
	            }
	        });
	        
	        mp.setOnStopped(new Runnable() {
	        	 public void run() {
	        		atEndOfMedia = true;
	        		System.out.println("stopped");
		            playButton.setGraphic(new ImageView(image));
		            playButton1.setGraphic(new ImageView(image));
		       }
	        });
	        
	        mp.setOnReady(new Runnable() {
	        	
	            public void run() {
	                duration = mp.getMedia().getDuration();
	                updateValues();
	                mediaView.setFitWidth(mpWidth);
	                mediaView.setFitHeight(mpHeight);
	                timeSlider.setMaxWidth(mpWidth/2);
	            }
	        });
	        
	        mp.setCycleCount(loop1 ? MediaPlayer.INDEFINITE : 1);
	        mp.setOnEndOfMedia(new Runnable() {

	            public void run() {
	            	if (!loop1){ 
	            	playButton.setGraphic(new ImageView(image));
	                playButton1.setGraphic(new ImageView(image));
	                stopRequested = true;
	                atEndOfMedia = true;
	                mp.stop();
	            	}
	            	else{
	            	mp.seek(mp.getStartTime());
	            	}
	            }
	        });
	        
	        mp.setOnRepeat(new Runnable() {
	        	 public void run() {
	        		//atEndOfMedia = false;
	        		playButton.setGraphic(new ImageView(image1));
	                playButton1.setGraphic(new ImageView(image1));         
	            }
	        });

	        mediaBar.getChildren().add(playButton);
	        
	        Button stopButton = new Button();
	        stopButton.setGraphic(new ImageView(image2));
	        stopButton.setOnAction(new EventHandler<ActionEvent>() {

	            public void handle(ActionEvent e) {
	                mp.stop();
	                atEndOfMedia = true;
	                playButton.setGraphic(new ImageView(image));
	                playButton1.setGraphic(new ImageView(image));
	            }
	        });
	        mediaBar.getChildren().add(stopButton);
	        
	        Button fullscreenButton = new Button();
	        fullscreenButton.setGraphic(new ImageView(image3));
	        stage1 = new Stage();
	        fullscreenButton.setOnAction(new EventHandler<ActionEvent>() {
	        
	            public void handle(ActionEvent e) {
	            	mediaView.setVisible(false);
	            	Node  source = (Node)  e.getSource();
	            	stage  = (Stage) source.getScene().getWindow();
	            	final Group root = new Group();
	            	MediaView mediaView1 = new MediaView(mp);
	            	mediaView1.setFitWidth(bounds.getWidth());
	            	mediaView1.setPreserveRatio(true);
	            	mediaView1.setLayoutY((bounds.getHeight() - mediaView1.getFitHeight())/7);
	            
	            	root.getChildren().add(mediaView1);
	            	root.getChildren().add(hbox);

	            	
	            	final Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight(), Color.BLACK);
	         
	            	stage1.setScene(scene);
	            	stage1.setFullScreen(true);
	            	stage1.show();
	                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    @Override
	                    public void handle(MouseEvent mouseEvent) {
	                        if(mp.getStatus() == Status.PLAYING){
	                    	mp.pause();
	                        }
	                        else{
	                        mp.play();
	                        }
	                    }
	                });
	                
	                fadeTransition = new FadeTransition(Duration.millis(3000), hbox);
	                fadeTransition.setFromValue(1.0);
	                fadeTransition.setToValue(0.0);
	                
	                scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
	                	@Override
	    	            public void handle(MouseEvent mouseEvent){
	                		hbox.setDisable(false);
	                		scene.setCursor(Cursor.DEFAULT);
	                		fadeTransition.play();
	    	            }
	    	        });
	                
	                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
	                    @Override
	                    public void handle(ActionEvent event) {
	                    	hbox.setDisable(true);
	                        scene.setCursor(Cursor.NONE);
	                    }
	                }); 
	                
	                hbox.setOnMouseEntered(new EventHandler<MouseEvent>(){
	                	@Override
	    	            public void handle(MouseEvent mouseEvent){
	                		fadeTransition.stop();
	    	            }
	    	        });
	                
	                stage1.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
	        		    @Override
	        		    public void handle(KeyEvent event){
	        			    if(event.getCode() == KeyCode.ESCAPE){
	        	            	stage1.close();
	        	            	mediaView.setVisible(true);
	        	            	stage.setFullScreen(true);
	        	            	stage.getScene().setCursor(Cursor.HAND);
	        	            	stage.show();
	        			    }	                                 
	        		    }
	                });                
	                
	            }
	        });
	     	        
	        mediaBar.getChildren().add(fullscreenButton);
	        Label timeLabel = new Label("   Time: ");
	        timeLabel.setTextFill(Color.WHITE);
	        timeLabel.setMinWidth(Control.USE_PREF_SIZE);
	        mediaBar.getChildren().add(timeLabel);
	        
	        
	        timeSlider = new Slider();
	        HBox.setHgrow(timeSlider, Priority.ALWAYS);
	        timeSlider.valueProperty().addListener(new InvalidationListener() {

	            @Override
	            public void invalidated(Observable ov) {
	                if (timeSlider.isValueChanging()) {
	                    // multiply duration by percentage calculated by slider position
	                    if (duration != null) {
	                    	mp.seek(duration.multiply(timeSlider.getValue()/ 100.0));
	                    }
	                }
	            }
	        });
	        
	        timeSlider.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent mouseEvent) {
	                mp.seek(duration.multiply(timeSlider.getValue()/ 100.0));
	            }
	        });
	        
	        mediaBar.getChildren().add(timeSlider);
	        
	        playTime = new Label();
	        playTime.setMinWidth(50);
	        playTime.setTextFill(Color.WHITE);
	        mediaBar.getChildren().add(playTime);

	        Label volumeLabel = new Label(" Vol: ");
	        volumeLabel.setTextFill(Color.WHITE);
	        volumeLabel.setMinWidth(Control.USE_PREF_SIZE);
	        mediaBar.getChildren().add(volumeLabel);

	        volumeSlider = new Slider();
	        volumeSlider.setPrefWidth(mpWidth/6);
	        volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
	        volumeSlider.setMinWidth(20);
	        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

	            @Override
	            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	                if (volumeSlider.isValueChanging()) {
	                    mp.setVolume(volumeSlider.getValue() / 100.0);
	                }
	                updateValues();
	            }
	        });
	        mediaBar.getChildren().add(volumeSlider);
	        
	        box.getChildren().add(mediaBar);
	        
        	timeSlider1 = new Slider();
        	timeSlider1.setMinWidth(bounds.getWidth()-100);
        	
        	timeSlider1.valueProperty().addListener(new InvalidationListener() {

   	            @Override
   	            public void invalidated(Observable ov) {
   	                if (timeSlider1.isValueChanging()) {
   	                    // multiply duration by percentage calculated by slider position
   	                    if (duration != null) {
   	                    	mp.seek(duration.multiply(timeSlider1.getValue()/ 100.0));
   	                    }
   	                }
   	            }
   	        });
   	        
   	        timeSlider1.setOnMousePressed(new EventHandler<MouseEvent>() {
   	            @Override
   	            public void handle(MouseEvent mouseEvent) {
   	            	fadeTransition.stop();
   	            	mp.seek(duration.multiply(timeSlider1.getValue()/ 100.0));
   	            }
   	        });
   	        
   	        playTime1 = new Label();
	        playTime1.setMinWidth(50);
	        playTime1.setTextFill(Color.WHITE);
	        
   	        hbox = new HBox();  
   	        hbox.getChildren().add(playButton1);
   	        hbox.getChildren().add(timeSlider1);
   	        hbox.getChildren().add(playTime1);
   	        hbox.setLayoutY(bounds.getHeight()-20);
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
	    
	    Task<Object> startTimerThread = new Task<Object>() {
 	    	
 			@Override
 			protected Object call() throws Exception {
 				 int count=0;
 				 while (count <= startTime && startTime != 0) {
 					try {
 						TimeUnit.SECONDS.sleep(1);
 					} catch (InterruptedException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
 					count++;
 			 	}
 			 
 				Platform.runLater (new Runnable() {
 					public void run(){
 						mp.play();
 					}
 				});
 				if (playDuration != null && playDuration != 0)
 					mp.setStopTime(Duration.seconds(playDuration));
					//new Thread(durationTimerThread).start();
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
