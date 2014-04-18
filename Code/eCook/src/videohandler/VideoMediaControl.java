package videohandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.animation.FadeTransition;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class VideoMediaControl extends eCook.MediaControl{
	
	private boolean stopRequested = false;
	private boolean atEndOfMedia = false;
	private InputStream inputStream;
	private Image image, image1, image2, image3;
	private Stage stage, stage1;
	private HBox hbox;
	private Button playButton, playButton1;
	private FadeTransition fadeTransition;

	public VideoMediaControl(MediaPlayer mp, Integer width, Integer height,
			Boolean loop, Integer startTime, Integer playDuration) {
		super(mp, width, height, loop, startTime, playDuration);
		// TODO Auto-generated constructor stub
	}

	@Override
	public
	void additionalSetup() {
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
                updateValues();
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
                        updateValues();
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
                updateValues();
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
                        updateValues();
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
        		mp.setStopTime(Duration.INDEFINITE);
        		atEndOfMedia = true; 
	            playButton.setGraphic(new ImageView(image));
	            playButton1.setGraphic(new ImageView(image));
	       }
        });
        
        mp.setOnReady(new Runnable() {
        	
            public void run() {
                duration = mp.getMedia().getDuration();
                updateValues();
                if(adjustedSize){
	                mediaView.setPreserveRatio(false);
	                mediaView.setFitWidth(mpWidth);
	                mediaView.setFitHeight(mpHeight);
                }
                else{
                	mediaView.setPreserveRatio(true);
 	                mediaView.setFitWidth(mpWidth);
                }
                timeSlider.setMaxWidth((2*mpWidth)/3);
            }
        });
        
        mp.setOnEndOfMedia(new Runnable() {

            public void run() {
            	if (!loop1){ 
                	playButton.setGraphic(new ImageView(image));
                    playButton1.setGraphic(new ImageView(image));
                    atEndOfMedia = true;
                    mp.stop();
                	}
                	mp.seek(mp.getStartTime());
                }
        });
        
        mp.setOnRepeat(new Runnable() {
        	 public void run() {
        		atEndOfMedia = false;
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
        volumeSlider.setManaged(true);
        volumeSlider.setMaxWidth(mpWidth/6);
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

}