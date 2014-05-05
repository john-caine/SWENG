package timer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import javafx.util.Duration;

public class Timer extends Task<Object>{
	
	private Button startButton;
	private Integer timerStartSeconds = 10;
	private Integer timerValueSeconds= 0;
	private Label labelSeconds;
	private Timeline timeLineSeconds;
	private Integer timerValueMinutes;
	private Label labelMinutes;
	HBox timerBox;
	private Integer timerStartMinutes = 0;
	private Integer timerValueHours;
	private Integer timerStartHours =1 ;
	private Label labelHours;
	private ListView<Integer> numbersListSeconds;
	private ListView<Integer> numbersListMinutes;
	private ListView<Integer> numbersListHours;
	private boolean started = false;
	private boolean paused = false;
	private boolean timerSetupFinished = false;
	private ArrayList<Integer> timerValues;
	private HBox listBox;
	private VBox timerVBox;
	private int i;
	private Button resetTimer;
	private HBox buttonBox;
	private AudioClip audio;
	private VBox pane;
	private boolean resumeTimer = false;

	public Timer(Integer currentHours, Integer currentMinutes, Integer currentSeconds) {
		if((currentHours != null) && (currentMinutes != null) && (currentSeconds != null)){
		timerValueHours = currentHours;
		timerValueMinutes = currentMinutes;
		timerValueSeconds = currentSeconds;
		resumeTimer  = true;
		}
	}

	@Override
	protected Object call() throws Exception {
		System.out.println("Timer Thread running");
		timerBox = new HBox();
		listBox = new HBox();
		

		startButton = new Button("Start");
		startButton.setPrefWidth(50);
		resetTimer = new Button("Reset");
		buttonBox = new HBox();
		buttonBox.setLayoutX(400);
		buttonBox.setLayoutY(350);
		buttonBox.getChildren().addAll(startButton,resetTimer);
	
		
	
		numberListSetup();
		
		
		listBox.getChildren().add(numbersListHours);	
		listBox.getChildren().add(numbersListMinutes);
		listBox.getChildren().add(numbersListSeconds);
		
		
		
		if(timerStartSeconds > 9){
			labelSeconds = new Label(timerStartSeconds.toString());
		}
		else{
			labelSeconds = new Label("0" + timerStartSeconds.toString());
		}
		
		if(timerStartMinutes > 9){
			labelMinutes = new Label(timerStartMinutes.toString() + " : ");
		}
		else{
			labelMinutes = new Label("0" + timerStartMinutes.toString() + " : ");
		}
		
		if(timerStartHours > 9){
			labelHours = new Label(timerStartHours.toString() + " : ");
		}
		else{
			labelHours = new Label("0" + timerStartHours.toString() + " : ");
		}
		
		labelSeconds.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(true);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(false);
				
			}
			
		});
		
		labelMinutes.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(true);
				numbersListHours.setVisible(false);
				
			}
			
		});
		
		labelHours.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(true);
				
			}
			
		});
		
		
		timerBox.getChildren().add(labelHours);
		timerBox.getChildren().add(labelMinutes);
		timerBox.getChildren().add(labelSeconds);
		
		timerVBox = new VBox();
		
		
		timerVBox.getChildren().addAll(timerBox, listBox);
		
		pane = new VBox();
		System.out.println("Pane Created");
		
		pane.getChildren().addAll(buttonBox, timerVBox);
		
		
		
		loadAudio("file:///C:/Users/Phainax/Documents/GitHub/SWENG/Code/Resources/Ship_Bell_Mike_Koenig_1911209136.wav");
		
		
		
		timeLineSeconds = new Timeline();
		timeLineSeconds.setCycleCount(Timeline.INDEFINITE);
		createKeyFrame();
		setButtonEventListeners();
		
		
		timerSetupFinished = true;
		
		if(resumeTimer  == true){
			timeLineSeconds.play();
			startButton.setText("Pause");
			started = true;
			
		}
		return null;
	}
 
	
	
	
	
	
	public void setButtonEventListeners(){
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			

			@Override
	        public void handle(ActionEvent event) {
				
				if(started == false && paused == false){
				
					timerValueSeconds = timerStartSeconds;
					timerValueMinutes = timerStartMinutes;
					timerValueHours = timerStartHours;
					timeLineSeconds.play();
					startButton.setText("Pause");
					
					started = true;
				}
				else if(started == true && paused == false){
					timeLineSeconds.stop();
					startButton.setText("Play");
					paused = true;
				}
				
				else if(started == true && paused == true){
					
					timeLineSeconds.play();
					startButton.setText("Pause");
					paused = false;
				}
			
			
	        }
	    });
		
		resetTimer.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				timeLineSeconds.stop();
				started = false;
				paused = false;
				
				if (timerStartSeconds >9){
					
					labelSeconds.setText(timerStartSeconds.toString());
					
				}
				else{
					
					labelSeconds.setText("0" + timerStartSeconds.toString());
					
				}
				
				if (timerStartMinutes > 9){
					
					labelMinutes.setText(timerStartMinutes.toString() + " : ");
					
				}
				else{
					
					labelMinutes.setText("0" + timerStartMinutes.toString() + " : ");
				}
				
				
				if (timerStartHours > 9){
					
					labelHours.setText(timerStartHours.toString() + " : ");
				}
				else{
					
					labelHours.setText("0" + timerStartHours.toString() + " : ");
				}
					
				startButton.setText("Start");
			}
			
			
		});
		
		
	}
	
	/*Creates the keyframe that operates the timer. The keyframe counts for one second, decrementing the 
	 * remaining seconds each time. Once the number of seconds reaches 0, the method evaluates if the 
	 * minutes,hours need to be decremented.
	 * 
	 */
	
	private void createKeyFrame(){
		
		timeLineSeconds.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

			
			@Override
			public void handle(ActionEvent event) {
				timerValueSeconds--;
				
				if(timerValueSeconds > 9){
					labelSeconds.setText(timerValueSeconds.toString());
				}
				else {
				
					labelSeconds.setText("0" + timerValueSeconds.toString());
				}
				
				if(timerValueHours <= 0 && timerValueMinutes <= 0 && timerValueSeconds <= 0){
					timeLineSeconds.stop();
					audio.play();
					labelHours.setText("Finished");
					labelMinutes.setText("");
					labelSeconds.setText("");
					started = false;
					paused = false;
					startButton.setText("Start");
				}
				else if(timerValueMinutes <= 0 && timerValueSeconds < 0){
					timerValueHours--;
					timerValueMinutes = 59;
					timerValueSeconds = 59;
					
					if(timerValueHours > 9){
						labelHours.setText(timerValueHours.toString() + " : ");
					}
					else {
					
						labelHours.setText("0" + timerValueHours.toString() + " : ");
					}
					
					labelMinutes.setText(timerValueMinutes.toString() + " : ");
					labelSeconds.setText(timerValueSeconds.toString());
				}
				else if(timerValueSeconds < 0){
					timerValueMinutes--;
					
					timerValueSeconds = 59;
					
					if(timerValueMinutes > 9){
						labelMinutes.setText(timerValueMinutes.toString() + " : ");
					}
					else{
						labelMinutes.setText("0" + timerValueMinutes.toString() + " : ");
					}
					
					labelSeconds.setText(timerValueSeconds.toString());
				}
				
					
				
				
				
			}
		
			
		} ));
		
		
	}
		/* The lists of numbers which can be selected by the user to set the timer are created in this method.
		 * There is a list for seconds, minutes and hours, which when clicked sets the start value of the timer to 
		 * the selected value then hides the list again.
		 */
	
	private void numberListSetup() {
		
		 ObservableList<Integer> numbers = FXCollections.observableArrayList();
		 
		 for( i = 0; i < 60; i++){
			 
			 numbers.add(i);
			 
		 }
		
		 
		//Create ListViews which are used to select the start values of the timer
		numbersListSeconds = new ListView<Integer>();
		numbersListMinutes = new ListView<Integer>();
		numbersListHours = new ListView<Integer>();
		
		// Add the numbers to each of the list
		numbersListSeconds.setItems(numbers);
		numbersListMinutes.setItems(numbers);
		numbersListHours.setItems(numbers);
		
		numbersListSeconds.setPrefWidth(30);
		numbersListMinutes.setPrefWidth(30);
		numbersListHours.setPrefWidth(30);
		
		//Hide all of the number lists
		numbersListSeconds.setVisible(false);
		numbersListMinutes.setVisible(false);
		numbersListHours.setVisible(false);
		
		numbersListSeconds.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				timerStartSeconds = numbersListSeconds.getSelectionModel().getSelectedItem();
				if(timerStartSeconds > 9){
					labelSeconds.setText(timerStartSeconds.toString());
				}
				else{
					labelSeconds.setText("0" + timerStartSeconds.toString());
				}
				numbersListSeconds.setVisible(false);
				
			}
			
		});
		
		numbersListMinutes.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				timerStartMinutes = numbersListMinutes.getSelectionModel().getSelectedItem();
				if(timerStartMinutes > 9){
					labelMinutes.setText(timerStartMinutes.toString() + " : ");
				}
				else{
					labelMinutes.setText("0" + timerStartMinutes.toString() + " : ");
				}
				numbersListMinutes.setVisible(false);
				
			}
			
		});
		
		numbersListHours.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				timerStartHours = numbersListHours.getSelectionModel().getSelectedItem();
				if(timerStartHours > 9){
					labelHours.setText(timerStartHours.toString() + " : ");
				}
				else{
					labelHours.setText("0" + timerStartHours.toString() + " : ");
				}
				numbersListHours.setVisible(false);
				
			}
			
		});
		
	}
	
	private void loadAudio(String urlname) {
		try {
			audio = new AudioClip(urlname);
		} catch (IllegalArgumentException i) {
			System.out.println("Audio File not found at " + urlname);
		} catch (MediaException m) {
			System.out.println("Audio File " + urlname + " was of an unexpected format");
		}
	}
	
	public Pane getPane(){
		
		return pane;
		
		
	}
	
	public boolean getTimerSetupStatus(){
		
		
		return timerSetupFinished;
		
		
	}
	
	public List<Integer> getTimerValues(){
		
		timerValues = new ArrayList<Integer>();
		timerValues.add(timerValueHours);
		timerValues.add(timerValueMinutes);
		timerValues.add(timerValueSeconds);
		
		
		
		return timerValues ;
		
	}

	
	

}
