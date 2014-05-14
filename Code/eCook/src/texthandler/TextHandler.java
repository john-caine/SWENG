/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a TextFlow object with the Text object added to it.
 * Text object is defined by the arguments to the TextHandler constructor which conform to PWS.
 * 
 */
package texthandler;


import java.util.List;
import java.util.concurrent.TimeUnit;

import eCook.SlideShow;
import xmlparser.TextBody;
import xmlparser.TextString;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.util.Duration;

public class TextHandler {
	
	public TextFlow textBox;
	public Text text;
	private Integer startTime;
	private Integer duration;
	private FontWeight weight;
	private FontPosture posture;
	private SlideShow parent;
	private Integer branchID;
	private List<TextString> stringList;
	private TextString textString;
	private Timeline timeLineStart;
	private Timeline timeLineDuration;
	private final Integer runDuration;
	
	/*
	 * Text Handler Constructor. Creates a TextFlow object using all PWS required and optional attributes
	 */
	
	
	public TextHandler(SlideShow parent, TextBody textBody, String font, Integer x_start, Integer y_start, Integer fontsize, 
			String fontcolor, Integer x_end, Integer y_end, Integer startTime, Integer duration, 
			Integer layer, Integer branchID, Integer orientation){
		
		 this.startTime = startTime;
		 this.duration = duration;
		 this.branchID = branchID;
		 this.parent = parent;
		 this.runDuration = duration;
		 
		 //Gets the screen width to wrap text to if the XML has not specified an X end value.
		 if (x_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

			 x_end = (int)screenBounds.getWidth();
		 }
		 
		 //Gets the screen height to wrap text to if the XML has not specified an Y end value.
		 if (y_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

			 y_end =(int)screenBounds.getHeight();
		 }
		 
		stringList = textBody.getTextBody();
		
	
		 //Creates new TextFlow textBox, sets to invisible
		 textBox = new TextFlow();
		 textBox.setVisible(false);
		 
		 /* Sets TextFlow to size of a box defined by x_start, x_end, y_start and y_end
		  * If the Text object added to textBox is greater than this size the TextFlow
		  * will over run and display the full string.
		  */
		 textBox.setMaxWidth(x_end - x_start);
		 textBox.setMaxHeight(y_end - y_start);
		
		for (int i = 0 ; i < stringList.size(); i++){
			
			textString = stringList.get(i);
			
			 //Set the text weight value to Bold if getBold is true
			 if((textString.getBold() == null) || (textString.getBold() == false) ){
				 weight = FontWeight.NORMAL;
			 }
			 else  {
				 weight = FontWeight.BOLD;
			 }

		
			//Set the text posture value to Italic if getItalic is true 
			 if ((textString.getItalic() == null) || (textString.getItalic() == false)){
				 posture = FontPosture.REGULAR;
			 }
			 else {
				 posture = FontPosture.ITALIC;
			 }
			 
			//Create the text object which contains a string
			 text = new Text(textString.getText());
			 
			// Set the font, bold, italic and font size 
			 text.setFont(Font.font(font,weight, posture, (double)fontsize));
			 
			//Underlines the text if true, no underline if false
			 if(textString.getUnderline() != null){
				 text.setUnderline(textString.getUnderline());
			 }
			 
			 //Sets the colour of the text, set Fill sets the interior fill colour
			 // Set stroke sets the outline of the text.
			 text.setFill(Color.web(fontcolor));
			 
			//Sets the wrapping width of the text object, if  x end is null, the wrapping width is set to the edge 
			 // of the screen.
			 text.setWrappingWidth((x_end - x_start));
		     
			 textBox.getChildren().add(i, text);		
		}	
		 
		//Sets the XY position of textBox
		setStartXY(x_start, y_start, textBox);
		 
		
		timeLineStart = new Timeline();
		
		timeLineStart.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				showText();	
				
				if(runDuration != null)
				timeLineDuration.playFromStart();
			}	
		});
		
		timeLineDuration = new Timeline();
		
		timeLineDuration.setOnFinished(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("Removing text");
				removeText();
			}
		});

		createKeyFrame();
		
		if(startTime != null){
			System.out.println("Starttime timeline running");
			timeLineStart.setCycleCount(this.startTime);
			if(this.duration == null){
				this.duration = 0;
			}
			timeLineDuration.setCycleCount(this.duration);
			timeLineStart.playFromStart();
		}
		else if(duration != null){
		 showText();
		 System.out.println("Duration timeline running");
		 timeLineDuration.setCycleCount(this.duration);
		 timeLineDuration.playFromStart();
		}
		else{
		showText();
		}
		
		//Begin the start Timer thread
		//if (startTime == null) {
			//this.startTime = 0;
			//new Thread(startTimerThread).start();
		//}
		//else
			
		//new Thread(startTimerThread).start();
	};
	
	// Set the XY Layout of the TextFlow box
	public void setStartXY(int x_start, int y_start, TextFlow box){
		
		box.setLayoutX((double)x_start);
		box.setLayoutY((double)y_start);
	}
	
	// Set the visibility of textBox to true
	public void showText() {
		
		Platform.runLater( new Runnable(){
			public void run(){
				 textBox.setVisible(true);
			}
		});

	 }
	
	//Set the visibility of textBox to false
	 public void removeText() {
		 
		 Platform.runLater( new Runnable(){
				public void run(){
					textBox.setVisible(false);
				}
			});
		 
	 }
	// Thread waits until count = startTime then sets the visibility of the image to true.
	 Task<Object> startTimerThread = new Task<Object>() {
		 
			@Override
			protected Object call() throws Exception {
				//Task waits for number of seconds equal to startTime
				TimeUnit.SECONDS.sleep(startTime);
			 
			
			 
			 if (branchID != null && branchID != 0)
				 doBranch();
			 if (duration != null && duration != 0)
				 new Thread(durationTimerThread).start();
			 

				return null;
			}
		 };
	 
	 // Thread waits until duration and then sets the image visibility to false once duration = count.
		 Task<Object> durationTimerThread = new Task<Object>() {
			 
				@Override
				protected Object call() throws Exception {
					
					//Task waits for number of seconds equal to Duration
					TimeUnit.SECONDS.sleep(duration);
					
					//Allows removeText() to be run on the JavaFX thread, all GUI changes must happen on JavaFX thread
					 Platform.runLater( new Runnable(){
							public void run(){
								 removeText();
							}
						});	 
					return null;
				}
			 };
	
	 // When textBox is clicked on, a new branch slide is created with an id of branchID
	 private void doBranch() {
		 textBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
		{
			public void handle(MouseEvent e) {
				parent.newSlide(branchID, true, null);
			}
		});
	 }
	 
	 private void createKeyFrame(){
			
			timeLineStart.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					startTime--;	
				}
			} ));
			
			timeLineDuration.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){	
				@Override
				public void handle(ActionEvent event) {
					duration--;
					
				}
			} ));	
	}
	 
	 public void pause(){
		 timeLineStart.pause();
		 timeLineDuration.pause();
		
	 }
	 
	 
	 public void resume(){
		 if(textBox.isVisible() == true && timeLineDuration.getStatus() != Status.STOPPED){
			 timeLineDuration.play();
		 }else if (textBox.isVisible() == false && timeLineStart.getStatus() != Status.STOPPED){
			 timeLineStart.play();
		 System.out.println(timeLineDuration.getCycleCount());
		 }
	 }
	
}