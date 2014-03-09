/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 01/03/2014
 * Description: The text module class, creates a Hbox with the text object added to it.
 * Version History: 1.0 - Created.
 * 
 */
package texthandler;

import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class TextHandler {
	
	public HBox textBox;
	public Text text;

	public TextHandler(String inputString, String font, int x_start, int y_start){
	
	 text = new Text();
	 text.setText(inputString);
	 text.setFont(Font.font(font, 20));
	 textBox = new HBox();
	 textBox.getChildren().add(text);
	 setStartXY(x_start, y_start);
	 
	
	
	
	};
	
	private void setStartXY(int x_start, int y_start){
		
		textBox.setLayoutX((double)x_start);
		textBox.setLayoutY((double)y_start);
	}
	

}
