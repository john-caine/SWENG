/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a Hbox with the text object added to it.
 * Version History: 1.0 - Created.
 * 
 */
package texthandler;

import java.util.concurrent.TimeUnit;

import eCook.SlideShow;
import xmlparser.TextBody;
import xmlparser.TextString;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Screen;

public class TextHandler {
	
	public HBox textBox;
	public Text text;
	private Integer startTime;
	private Integer duration;
	private FontWeight weight;
	private FontPosture posture;
	private SlideShow parent;
	private Integer branchID;
	

	public TextHandler(SlideShow parent, TextString textString, String font, Integer x_start, Integer y_start, Integer fontsize, 
			String fontcolor, String linecolor, Integer x_end, Integer startTime, Integer duration, 
			Integer layer, Integer branchID, Integer orientation){
		
		 
		 this.startTime = startTime;
		 this.duration = duration;
		 this.branchID = branchID;
		 this.parent = parent;
		 
		
		
			 
			 if((textString.getBold() == null) || (textString.getBold() == false) ){
				 weight = FontWeight.NORMAL;
			 }
			 else  {
				 weight = FontWeight.BOLD;
			 }
		 
		 
		
			
			 if ((textString.getItalic() == null) || (textString.getItalic() == false)){
				 posture = FontPosture.REGULAR;
			 }
			 else {
				 posture = FontPosture.ITALIC;
			 }
		
		 
		 
		
		 text = TextBuilder.create().text(textString.getText()).build();
		 text.setStroke(Color.web(linecolor));
		 text.setFont(Font.font(font,weight, posture, (double)fontsize));
		 
		 if(textString.getUnderline() != null){
			 
		 text.setUnderline(textString.getUnderline());
		 
		 }
		 text.setFill(Color.web(fontcolor));
		 
		 if (x_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
			 x_end = (int)screenBounds.getWidth();
		 }
		 text.setWrappingWidth((x_end - x_start));
		 textBox = new HBox();
		 textBox.setVisible(false);
		 textBox.getChildren().add(text);
		 setStartXY(x_start, y_start, textBox);
		 
		 if (startTime == null) {
			 this.startTime = 0;
			 startTimerThread.start();
		 }
		 else
			 startTimerThread.start();
		 
		 
	 
	
	
	
	};
	
	public void setStartXY(int x_start, int y_start, HBox box){
		
		box.setLayoutX((double)x_start);
		box.setLayoutY((double)y_start);
	}
	
	public void showText() {
	     textBox.setVisible(true);
	     
	 }
	 
	 public void removeText() {
		 textBox.setVisible(false);
	 }
	
	 Thread startTimerThread = new Thread("startTimer") {
		 public void run() {
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
			 
		 
		 showText();
		 if (branchID != null && branchID != 0)
			 doBranch();
		 
		 if (duration != null && duration != 0)
			 durationTimerThread.start();
		 }
	 };
	 
	 Thread durationTimerThread = new Thread("durationTimer") {
		 public void run() {
			 int count=0;
			 while (count <= duration) {
				try {
				
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 count++;
			 }
			 removeText();
		 }
	 };
	 
	 private void doBranch() {
		 textBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
		{
			public void handle(MouseEvent e) {
				parent.newSlide(branchID, true);
			}
		});
	 }
	

}
