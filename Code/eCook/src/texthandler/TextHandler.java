/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a Hbox with the text object added to it.
 * Version History: 1.0 - Created.
 * 
 */
package texthandler;

import java.util.concurrent.TimeUnit;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.stage.Screen;

public class TextHandler {
	
	public HBox textBox;
	public Text text;
	private Integer startTime;
	private Integer duration;
	

	public TextHandler( String inputString, String font, Integer x_start, Integer y_start, Integer fontsize, 
			String fontcolor, String linecolor, Integer x_end, Integer startTime, Integer duration, 
			Integer layer, Integer branch, Integer orientation){
		
		 Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		 this.startTime = startTime;
		 this.duration = duration;
		
		 text = TextBuilder.create().text(inputString).build();
		 text.setStroke(Color.web(linecolor));
		 text.setFont(Font.font(font, (double)fontsize));
		 text.setFill(Color.web(fontcolor));
		 if (x_end == null){
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
	

}
