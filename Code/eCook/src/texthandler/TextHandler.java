/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a Hbox with the text object added to it.
 * Version History: 1.0 - Created.
 * 					1.1 - Start Time and Duration threads added with show image methods
 * 					1.2 Branching, bold italic and underlined text methods added
 */
package texthandler;


import java.util.List;
import java.util.concurrent.TimeUnit;
import eCook.SlideShow;
import xmlparser.TextBody;
import xmlparser.TextString;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;

public class TextHandler {
	
	public TextFlow textBox;
	public Text text;
	private Integer startTime;
	private Integer duration;
	private FontWeight weight;
	private FontPosture posture;
	private SlideShow parent;
	private Integer branchID;
	private TextArea textArea;
	private List<TextString> stringList;
	private TextString textString;
	private TextFlow textflow;

	public TextHandler(SlideShow parent, TextBody textBody, String font, Integer x_start, Integer y_start, Integer fontsize, 
			String fontcolor, Integer x_end, Integer y_end, Integer startTime, Integer duration, 
			Integer layer, Integer branchID, Integer orientation){
		
		 this.startTime = startTime;
		 this.duration = duration;
		 this.branchID = branchID;
		 this.parent = parent;
		 
		 if (x_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

			 x_end = (int)screenBounds.getWidth();
		 }
		 
		 if (y_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

			 y_end =(int)screenBounds.getHeight();
		 }
		 
		stringList = textBody.getTextBody();
		
	
		 //Creates new Hbox textBox, sets to invisible and adds the text object.
		 textBox = new TextFlow();
		 textBox.setVisible(false);
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
		 
		//Begin the start Timer thread
		if (startTime == null) {
			this.startTime = 0;
			new Thread(startTimerThread).start();
		}
		else
		new Thread(startTimerThread).start();
	};
	
	public void setStartXY(int x_start, int y_start, TextFlow box){
		
		box.setLayoutX((double)x_start);
		box.setLayoutY((double)y_start);
	}
	
	public void showText() {
	     textBox.setVisible(true);
	     
	 }
	 
	 public void removeText() {
		 textBox.setVisible(false);
	 }
	// Thread waits until count = startTime then sets the visibility of the image to true.
	 Task<Object> startTimerThread = new Task<Object>() {
		 
			
			@Override
			protected Object call() throws Exception {
				TimeUnit.SECONDS.sleep(startTime);
			 
			Platform.runLater( new Runnable(){
				public void run(){
					showText();
				}
			});	 
			 
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
					
					TimeUnit.SECONDS.sleep(duration);
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
				parent.newSlide(branchID, true);
			}
		});
	 }
	

}