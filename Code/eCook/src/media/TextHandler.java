package media;

import java.util.logging.Logger;

import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import xmlparser.TextBody;
import eCook.SlideShow;

public class TextHandler extends SubMedia {
	
	public TextHandler(SlideShow parent, TextBody textBody, String font, Integer xStart, Integer yStart, Integer fontsize, 
			String fontcolor, Integer x_end, Integer y_end, Integer startTime, Integer duration, 
			Integer branchID, Integer orientation){
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);
		
		 //Gets the screen width to wrap text to if the XML has not specified an X end value.
		 if (x_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getBounds();

			 x_end = (int)screenBounds.getWidth();
		 }
		 
		 //Gets the screen height to wrap text to if the XML has not specified an Y end value.
		 if (y_end == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getBounds();

			 y_end =(int)screenBounds.getHeight();
		 }
		 
		 stringList = textBody.getTextBody();
			
			
		 //Creates new TextFlow textBox, sets to invisible
		 textBox = new TextFlow();
		 textBox.setVisible(false);
		
	}
	
	private void setTextAttributes(){
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
	}

	@Override
	public void tearDown() {
		// TODO Auto-generated method stub
		
	}

}
