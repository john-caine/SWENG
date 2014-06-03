package media;

import java.util.List;
import javafx.geometry.Rectangle2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import xmlparser.TextBody;
import xmlparser.TextString;
import eCook.SlideShow;

public class TextHandler extends SubMedia {
	
	private List<TextString> stringList;
	private String font;
	private double fontsize;
	private String fontcolor;
	private int xEnd;
	private TextFlow textBox;

	public TextHandler(SlideShow parent, TextBody textBody, String font, Integer xStart, Integer yStart, Integer fontsize, 
			String fontcolor, Integer xEnd, Integer yEnd, Integer startTime, Integer duration, 
			Integer branchID, Integer orientation){
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);
		
		this.font = font;
		this.fontsize = fontsize;
		this.fontcolor = fontcolor;
		this.xEnd = xEnd;	
		
		 //Gets the screen width to wrap text to if the XML has not specified an X end value.
		 if (xEnd == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getBounds();

			 xEnd = (int)screenBounds.getWidth();
		 }
		 
		 //Gets the screen height to wrap text to if the XML has not specified an Y end value.
		 if (yEnd == null){
			 Rectangle2D screenBounds = Screen.getPrimary().getBounds();

			 yEnd =(int)screenBounds.getHeight();
		 }
		 
		//Creates new TextFlow textBox, sets to invisible
		 textBox = new TextFlow();
		 
		 
		 stringList = textBody.getTextBody();
		
		 for(int i = 0; i < stringList.size(); i++){ 
			TextString textString = stringList.get(i);
			Text text = setTextAttributes(textString);
			textBox.getChildren().add(text); 
		 }
		
		 hbox.getChildren().add(textBox);
		 
		 setTimeLines();		
	}
	
	private Text setTextAttributes(TextString textString){
		 FontWeight weight;
		 FontPosture posture;
		 Text text;
		 
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
		 text.setWrappingWidth((xEnd - xStart));
		 
		 return text;
	}

}
