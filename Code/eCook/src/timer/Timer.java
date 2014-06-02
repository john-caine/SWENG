/*
 * Programmer: Steve Thorpe and Paul Mathema
 * Date Created: 08/04/2014
 * Description: The Timer class, creates a cooking timer that can be set using Combobox fields which are made visible by time labels.
 * The timer is controlled using the start and restart buttons and contains a editable Text Field for the user to label the timer.
 * 
 */

package timer;

import java.io.FileInputStream;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import javafx.util.Duration;

public class Timer extends Task<Object>{
	
	protected Button startButton;
	private Label labelSeconds, labelMinutes, labelHours;
	private Timeline timeLineSeconds; 
	public HBox timerLabelBox, buttonBox, listBox;
	private Integer timerValueSeconds = null, timerValueMinutes = null, timerValueHours = null, timerStartSeconds = 0, timerStartMinutes = 0, timerStartHours = 0;
	protected ListView<Integer> numbersListSeconds;
	private ListView<Integer> numbersListMinutes;
	private ListView<Integer> numbersListHours; 
	private boolean timerSetupFinished = false, resumeTimer = false, paused = false, started = false;
	private TimerData timerValues;
	private VBox timerVBox;
	private int i;
	protected Button resetTimer;
	private AudioClip audio;
	private VBox timerContainer;
	private String timerLabel;
	private TextField textField;
	private FileInputStream inputStream;
	
	/*
	 * Timer Class constructor. As all objects from the slide group are deleted when the slide changes, the value of the timer before the slide was changed
	 * must be passed into the constructor. If Timer values are passed in, set the values timer Values to the corresponding parameter. If no timers were 
	 * present on the previous slide, this is ignored.
	 * @Param currentHours: Previous slide timer hours value.
	 * @Param currentMinutes: Previous slide timer minutes value.
	 * @Param currentSeconds: Previous slide timer seconds value.
	 * @Param startSeconds: Seconds value the timer started from.
	 * @Param startMinutes: Minutes value the timer started from.
	 * @Param startHours: Hours value the timer started from.
	 * @Param timerLabel: The timer label.
	*/
	public Timer(Integer currentHours, Integer currentMinutes, Integer currentSeconds, Integer startSeconds, Integer startMinutes, Integer startHours, String timerLabel) {
		
		if((currentHours != null) && (currentMinutes != null) && (currentSeconds != null)){
		timerValueHours = currentHours;
		timerValueMinutes = currentMinutes;
		timerValueSeconds = currentSeconds;
		timerStartSeconds = startSeconds;
		timerStartMinutes = startMinutes;
		timerStartHours = startHours;
		resumeTimer  = true;
		}
		this.timerLabel = timerLabel;
	}

	@Override
	protected Object call() throws Exception {
	
		timerLabelBox = new HBox();
		listBox = new HBox();
		textField = new TextField(timerLabel);
		//Gets play icon image from resources folder adds to start button.
		inputStream = new FileInputStream("../Resources/play.png");
		Image playImage = new Image(inputStream);
		startButton = new Button("Start", new ImageView(playImage));
		startButton.setPrefWidth(50);
		
		resetTimer = new Button("Reset");
		//Creates buttonBox HBox, sets the layout and adds all buttons to the list.
		buttonBox = new HBox();
		buttonBox.setLayoutX(400);
		buttonBox.setLayoutY(350);
		buttonBox.getChildren().addAll(startButton,resetTimer);
		
		numberListSetup();
		//Adds the number lists to list bokx
		listBox.getChildren().addAll(numbersListHours, numbersListMinutes, numbersListSeconds);	
		
		setTimerLabels();
		// Adds all time labels to the timerLabelbox
		timerLabelBox.getChildren().addAll(labelHours, labelMinutes, labelSeconds);
		
		//Adds timerLabel box and list box so the number lists will appear below the label when clicked
		timerVBox = new VBox();
		timerVBox.getChildren().addAll(timerLabelBox, listBox);
		//Adds all timer components to timerContainer which will be added to the slide group when created
		timerContainer = new VBox();
		timerContainer.getChildren().addAll(textField, buttonBox, timerVBox);
		//Gets the audio for when the timer finishes
		loadAudio("file:///C:/Users/Phainax/Documents/GitHub/SWENG/Code/Resources/Ship_Bell_Mike_Koenig_1911209136.wav");
		
		timeLineSeconds = new Timeline();
		timeLineSeconds.setCycleCount(Timeline.INDEFINITE);
		createKeyFrame();
		setButtonEventListeners();
	
		timerSetupFinished = true;
		// Resumes the timer if the new timer object is recreating a timer from the previous slide
		if(resumeTimer  == true){
			timeLineSeconds.play();
			startButton.setText("Pause");
			started = true;
		}
		return null;
	}
	
	/*
	 * Sets the event handlers for the Start button and Reset Button
	 */
	public void setButtonEventListeners(){
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
	        public void handle(ActionEvent event) {
				
				if(started == false && paused == false){
				
					if((timerValueHours == null) && (timerValueMinutes == null) && (timerValueSeconds == null)){
						timerValueSeconds = timerStartSeconds;
						timerValueMinutes = timerStartMinutes;
						timerValueHours = timerStartHours;
					}
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
		// Sets the text of the timers back to the start values, sets the started and paused flags both back to false
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
				//Timer has finished
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
				// Timer has moved past an hour
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
				//Timer has moved past a minute
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
		//Adds the numbers 0 - 60 to the arraylist numbers
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
		
		//Seconds list event handler, when number is selected sets the seconds label to the selected number, updates the startTimerValue and hides itself.
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
		//Minutes list event handler, when number is selected sets the minutes label to the selected number, updates the startTimerValue and hides itself.
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
		//Hours list event handler, when number is selected sets the hourslabel to the selected number, updates the startTimerValue and hides itself.
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
	/*
	 * Creates new Audio clip object and catches exceptions if the file name can't be found or is the wrong format.
	 * @urlname: The location of the audio file to be played.
	 */
	private void loadAudio(String urlname) {
		try {
			audio = new AudioClip(urlname);
		} catch (IllegalArgumentException i) {
			System.out.println("Audio File not found at " + urlname);
		} catch (MediaException m) {
			System.out.println("Audio File " + urlname + " was of an unexpected format");
		}
	}
	
	/*
	 * Returns timerContainer for adding to the slide group.
	 */
	public Pane getPane(){
		return timerContainer;
	}
	
	public boolean getTimerSetupStatus(){
		return timerSetupFinished;
	}
	
	/*
	 * Returns TimerData object containing the current values of the timer and the timer label
	 */
	public TimerData getTimerValues(){
		timerValues = new TimerData();
		timerValues.setSeconds(timerValueSeconds);
		timerValues.setMinutes(timerValueMinutes);
		timerValues.setHours(timerValueHours);
		timerValues.setSecondStart(timerStartSeconds);
		timerValues.setMinutesStart(timerStartMinutes);
		timerValues.setHoursStart(timerStartHours);
		timerValues.setLabel(textField.getText());
		return timerValues ;
	}

	/*
	 * Creates timer labels and sets the text to the timer value to be displayed. Sets the event handlers for the labels
	 */
	public void setTimerLabels(){
		//If there is no current timer value, sets timer labels to the start values 00:00:00 else sets them to the values passed into the constructor
		//If the value is less than 9, label has a 0 added infront of the the number to retain a 6 digit label view
		if ((timerValueHours == null) && (timerValueMinutes == null) && (timerValueSeconds == null)){
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
		} else {
			if(timerValueSeconds > 9){
				labelSeconds = new Label(timerValueSeconds.toString());
			}
			else{
				labelSeconds = new Label("0" + timerValueSeconds.toString());
			}
			
			if(timerValueMinutes > 9){
				labelMinutes = new Label(timerValueMinutes.toString() + " : ");
			}
			else{
				labelMinutes = new Label("0" + timerValueMinutes.toString() + " : ");
			}
			
			if(timerValueHours > 9){
				labelHours = new Label(timerValueHours.toString() + " : ");
			}
			else{
				labelHours = new Label("0" + timerValueHours.toString() + " : ");
			}
		}
		
		//Displays the seconds numbers list and hides other numbers lists
		labelSeconds.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(true);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(false);
			}
		});
		//Displays the minutes numbers list and hides other numbers lists
		labelMinutes.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(true);
				numbersListHours.setVisible(false);
			}
		});
		//Displays the minutes numbers list and hides other numbers lists
		labelHours.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(true);
			}
		});
	}
}
