/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a Hbox with the text object added to it.
 * Version History: 1.0 - Created.
 * 
 */
package cookbook;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextHandler {
	
	public HBox box;
	public Text text;

	public TextHandler(String inputString, String font, int x_start, int y_start){
	
	 text = new Text();
	 text.setText(inputString);
	 text.setFont(Font.font(font, 20));
	 box = new HBox();
	 box.getChildren().add(text);
	 setStartXY(x_start, y_start);
	 
	
	
	
	};
	
	private void setStartXY(int x_start, int y_start){
		
		box.setLayoutX((double)x_start);
		box.setLayoutY((double)y_start);
	}
	

}
