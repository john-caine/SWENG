/*
 * Programmer: Steve Thorpe, Paul Mathema & Roger Tan
 * Date Created: 08/04/2014
 * Description: The Timer class, creates a cooking timer that can be set using ChoiceBox fields which are made visible by time labels.
 * The timer is controlled using the start and restart buttons and contains a editable Text Field for the user to label the timer.
 * 
 */

package timer;


import java.awt.Toolkit;

import eCook.SlideShow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Timer extends Task<Object>{
	
	protected Button startButton;
	private Label labelSeconds, labelMinutes, labelHours;
	private Timeline timeLineSeconds; 
	public HBox timerTFandExitBtn, timerLabelBox, buttonBox;
	private Integer timerValueSeconds = null, timerValueMinutes = null, timerValueHours = null, timerStartSeconds = 0, timerStartMinutes = 0, timerStartHours = 0;
	public ChoiceBox<Integer> numbersListSeconds;
	protected ChoiceBox<Integer> numbersListMinutes;
	protected ChoiceBox<Integer> numbersListHours; 
	public boolean timerSetupFinished = false, resumeTimer = false, paused = false, started = false;
	private TimerData timerValues;
	private VBox timerVBox;
	private int i;
	protected Button resetTimer;
	private AudioClip audio;
	private VBox timerContainer;
	private String timerLabel;
	private TextField textField;
	private Pane listBox;
	private Button exitButton;
	private Pane textFieldBox;
	private HBox completeTimer;
	private int timerID;
	private SlideShow main;
	private ImageView closeBtnHolder, startBtnHolder, pauseBtnHolder, resetBtnHolder;
	private Image closeIcon, startIcon, pauseIcon, resetIcon;
	
	
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
	public Timer(Integer currentHours, Integer currentMinutes, Integer currentSeconds, Integer startSeconds, 
			Integer startMinutes, Integer startHours, String timerLabel, int timerID, SlideShow main) {
		
		//If the timer is still running between slide transition, assign the current timer values to the variables
		if((currentHours != null) && (currentMinutes != null) && (currentSeconds != null)){
		timerValueHours = currentHours;
		timerValueMinutes = currentMinutes;
		timerValueSeconds = currentSeconds;
		timerStartSeconds = startSeconds;
		timerStartMinutes = startMinutes;
		timerStartHours = startHours;
		// Condition to indicate that the timer is resuming counting from the previous slide
		resumeTimer  = true;
		}
		this.timerLabel = timerLabel;
		this.timerID = timerID;
		this.main = main;
		
	}

	@Override
	protected Object call() throws Exception {
		textFieldBox = new Pane();
		timerLabelBox = new HBox();
		listBox = new Pane();
		
		// Insert Text for the TextField 
		if(timerLabel != null){
			textField = new TextField(timerLabel);
		}
		else{
			textField = new TextField("Timer " + timerID);
		}
		
		// Configure TextField's size along with CSS
		textField.setId("timerField");
		textField.getStylesheets().add("css.css");
		textField.setAlignment(Pos.CENTER);
		textField.setPrefWidth(120);
		textFieldBox.getChildren().add(textField);
	
		//Get a play image from resources folder adds to start button.
		startButton = new Button();
		startButton.setPrefWidth(60);
		startBtnHolder = new ImageView();
		startIcon = new Image("PLAY.png");
		startBtnHolder.setImage(startIcon);
		startButton.setGraphic(startBtnHolder);
		startButton.setId("playButton");
		startButton.getStylesheets().add("css.css");
		
		// Get a pause image from resources folder
		pauseBtnHolder = new ImageView();
		pauseIcon = new Image("Pause.png");
		pauseBtnHolder.setImage(pauseIcon);
		
		//Gets play icon image from resources folder adds to reset button.
		resetTimer = new Button();
		resetTimer.setPrefWidth(60);
		resetBtnHolder = new ImageView();
		resetIcon = new Image("reset.png");
		resetBtnHolder.setImage(resetIcon);
		resetTimer.setGraphic(resetBtnHolder);
		resetTimer.setId("playButton");
		resetTimer.getStylesheets().add("css.css");
		
		//Creates buttonBox HBox, sets the layout and adds all buttons to the list.
		buttonBox = new HBox();
		buttonBox.setPrefWidth(100);
		buttonBox.getChildren().addAll(startButton,resetTimer);
		
		closeBtnHolder = new ImageView();
		closeIcon = new Image("closeTimer.png");
		closeBtnHolder.setImage(closeIcon);
		resetTimer.setId("playButton");
		resetTimer.getStylesheets().add("css.css");
		
		exitButton = new Button();
		exitButton.setLayoutX(startButton.getWidth() + resetTimer.getWidth());
		exitButton.setGraphic(closeBtnHolder);
		exitButton.setId("playButton");
		exitButton.getStylesheets().add("css.css");
		
		numberListSetup();
		//Adds the number lists to list box
		listBox.getChildren().addAll(numbersListHours, numbersListMinutes, numbersListSeconds);	
		
		setTimerLabels();
		// Adds all time labels to the timerLabelbox
		timerLabelBox.getChildren().addAll(labelHours, labelMinutes, labelSeconds);
		
		//Adds timerLabel box and list box so the number lists will appear below the label when clicked
		timerVBox = new VBox();
		timerVBox.getChildren().addAll(timerLabelBox, listBox);
		//Adds all timer components to timerContainer which will be added to the slide group when created
		timerTFandExitBtn = new HBox(50);
		timerTFandExitBtn.getChildren().addAll(textField, exitButton);
		timerContainer = new VBox();
		timerContainer.getChildren().addAll(timerTFandExitBtn, buttonBox, timerVBox);
		completeTimer = new HBox(5);
		completeTimer.setAlignment(Pos.CENTER);
		completeTimer.getChildren().addAll(timerContainer);
		//Gets the audio for when the timer finishes
		//loadAudio("Ship_Bell_Mike_Koenig_1911209136.wav");
		
		timeLineSeconds = new Timeline();
		timeLineSeconds.setCycleCount(Timeline.INDEFINITE);
		createKeyFrame();
		setButtonEventListeners();
		
		timerSetupFinished = true;
		// Resumes the timer if the new timer object is recreating a timer from the previous slide
		if(resumeTimer  == true){
			timeLineSeconds.play();
			started = true;
			paused = false;
			startButton.setGraphic(pauseBtnHolder);
			startButton.setDisable(false);
			//Disable all the choice box
			numbersListSeconds.setDisable(true);
			numbersListMinutes.setDisable(true);
			numbersListHours.setDisable(true);
		}
		else{
			started = false;
			paused = false;
			startButton.setDisable(true);
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
				
				// If the Timer is not started non paused
				if(started == false && paused == false){
					
					// If the Timer has no previous time values or the the Timer has been reset to zero
					if((timerValueHours == null) && (timerValueMinutes == null) && (timerValueSeconds == null)
							||(timerValueHours == 0) && (timerValueMinutes == 0) && (timerValueSeconds == 0)){
						timerValueSeconds = timerStartSeconds;
						timerValueMinutes = timerStartMinutes;
						timerValueHours = timerStartHours;
					}
					timeLineSeconds.play();
					startButton.setGraphic(pauseBtnHolder);					
					started = true;
				}
				// If the Timer has started and is counting down. Pause the timer when startButton is pressed
				else if(started == true && paused == false){
					timeLineSeconds.stop();
					startButton.setGraphic(startBtnHolder);
					paused = true;
				}
				// If the Timer is paused, resume the timer when startButton is pressed
				else if(started == true && paused == true){			
					timeLineSeconds.play();
					startButton.setGraphic(pauseBtnHolder);
					paused = false;
				}
				
				//Disable the choicebox
				numbersListSeconds.setDisable(true);
				numbersListMinutes.setDisable(true);
				numbersListHours.setDisable(true);
				
				//Set choicebox to invisible
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(false);

	        }
	    });
		// Sets the text of the timers back to the start values, sets the started and paused flags both back to false
		resetTimer.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent arg0) {
				// Stop the timer
				timeLineSeconds.stop();
				started = false;
				paused = false;
				
				// Reset the hours, minutes, seconds variable
				timerValueSeconds = 0;
				timerValueMinutes = 0;
				timerValueHours = 0;
				timerStartSeconds = 0;
				timerStartMinutes = 0;
				timerStartHours  = 0;
				
				// Reset the hours, minutes, seconds Label
				labelSeconds.setText("00");
				labelMinutes.setText("00" + " : ");
				labelHours.setText(" 00" + " : ");
				
				// Enable the Choice Box
				numbersListSeconds.setDisable(false);
				numbersListMinutes.setDisable(false);
				numbersListHours.setDisable(false);
				
				// Enable start button and set the image of the start button
				startButton.setDisable(false);
				startButton.setGraphic(startBtnHolder);
			}
				
		});
		
		exitButton.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent event) {
				//Remove the appropriate Timmer from the timerbox inside the notes panel
				main.getTimerbox().getChildren().remove(timerID);
				//Decrement the total amount of timers left
				main.decrementNumberOfTimers();
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
				if(timerValueHours <= 0 && timerValueMinutes <= 0 && timerValueSeconds <= 0 && started == true){
					timeLineSeconds.stop();
					Toolkit.getDefaultToolkit().beep();
					//audio.play();
					labelSeconds.setText("00");
					labelMinutes.setText("00" + " : ");
					labelHours.setText(" 00" + " : ");
					started = false;
					paused = false;
					startButton.setDisable(true);
				}
				// Timer has moved past an hour
				else if(timerValueMinutes <= 0 && timerValueSeconds < 0){
					startButton.setDisable(false);
					timerValueHours--;
					timerValueMinutes = 59;
					timerValueSeconds = 59;
					
					if(timerValueHours > 9){
						labelHours.setText(timerValueHours.toString() + " : ");
					}
					else {
						labelHours.setText(" 0" + timerValueHours.toString() + " : ");
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
		numbersListSeconds = new ChoiceBox<Integer>(numbers);
		numbersListMinutes = new ChoiceBox<Integer>(numbers);
		numbersListHours = new ChoiceBox<Integer>(numbers);
		
		numbersListSeconds.setPrefWidth(0);
		numbersListMinutes.setPrefWidth(0);
		numbersListHours.setPrefWidth(0);
		
		//Hide all of the number lists
		numbersListSeconds.setVisible(false);
		numbersListMinutes.setVisible(false);
		numbersListHours.setVisible(false);
		//Seconds list event handler, when number is selected sets the seconds label to the selected number, updates the startTimerValue and hides itself.
		numbersListSeconds.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(ObservableValue ov,
					Number value, Number new_vale){
				startButton.setDisable(false);
				timerStartSeconds = new_vale.intValue();
				if(timerStartSeconds > 9){
					labelSeconds.setText(new_vale.toString());
				}
				else{
					labelSeconds.setText("0" +new_vale.toString());
				}
				numbersListSeconds.setVisible(false);
			}
		});
		//Minutes list event handler, when number is selected sets the minutes label to the selected number, updates the startTimerValue and hides itself.
		numbersListMinutes.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(ObservableValue ov,
					Number value, Number new_vale){
				startButton.setDisable(false);
				timerStartMinutes = new_vale.intValue();
				if(timerStartMinutes > 9){
					labelMinutes.setText(new_vale.toString() + " : ");
				}
				else{
					labelMinutes.setText("0" +new_vale.toString() + " : ");
				}
				numbersListMinutes.setVisible(false);
			}
		});
		//Hours list event handler, when number is selected sets the hourslabel to the selected number, updates the startTimerValue and hides itself.
		numbersListHours.getSelectionModel().selectedIndexProperty().addListener(new
				ChangeListener<Number>() {
			public void changed(ObservableValue ov,
					Number value, Number new_vale){
				startButton.setDisable(false);
				timerStartHours = new_vale.intValue();
				if(timerStartHours > 9){
					labelHours.setText(new_vale.toString() + " : ");
				}
				else{
					labelHours.setText(" 0" +new_vale.toString() + " : ");
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
		return completeTimer;
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
				labelHours = new Label(" 0" + timerStartHours.toString() + " : ");
			}
		} 
		else {
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
				labelHours = new Label(" 0" + timerValueHours.toString() + " : ");
			}
		}
		
		//Displays the seconds numbers list and hides other numbers lists
		labelSeconds.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(true);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(false);
				
				numbersListSeconds.setPrefWidth(40);
				numbersListSeconds.setLayoutX(labelSeconds.getLayoutX());
				numbersListMinutes.setPrefWidth(0);
				numbersListHours.setPrefWidth(0);
			}
		});
		//Displays the minutes numbers list and hides other numbers lists
		labelMinutes.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(true);
				numbersListHours.setVisible(false);
				
				numbersListSeconds.setPrefWidth(0);
				numbersListMinutes.setPrefWidth(40);
				numbersListMinutes.setLayoutX(labelMinutes.getLayoutX());
				numbersListHours.setPrefWidth(0);
			}
		});
		//Displays the minutes numbers list and hides other numbers lists
		labelHours.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				numbersListSeconds.setVisible(false);
				numbersListMinutes.setVisible(false);
				numbersListHours.setVisible(true);
				
				numbersListSeconds.setPrefWidth(0);
				numbersListMinutes.setPrefWidth(0);
				numbersListHours.setPrefWidth(40);
				numbersListHours.setLayoutX(labelHours.getLayoutX());
			}
		});
		
		// Setup Id and Css for the labels
		labelSeconds.setId("timerLabel");
		labelMinutes.setId("timerLabel");
		labelHours.setId("timerLabel");
		labelSeconds.getStylesheets().add("css.css");
		labelMinutes.getStylesheets().add("css.css");
		labelHours.getStylesheets().add("css.css");
		
		// Set the text alignment of the labels
		labelSeconds.setTextAlignment(TextAlignment.CENTER);
		labelMinutes.setTextAlignment(TextAlignment.CENTER);
		labelHours.setTextAlignment(TextAlignment.CENTER);
		
		// Set font color
		labelSeconds.setTextFill(Color.WHITE);
		labelMinutes.setTextFill(Color.WHITE);
		labelHours.setTextFill(Color.WHITE);
	}

	public int getTimerID() {
		return timerID;
	}
	
	public Button getExitButton(){
		return exitButton;
	}
	
}