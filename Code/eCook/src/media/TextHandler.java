

package media;

import java.util.List;

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
import xmlparser.TextBody;
import xmlparser.TextString;
import eCook.SlideShow;

public class TextHandler extends SubSlideMedia {
	
	private List<TextString> stringList;
	private String font;
	private double fontSize;
	private String fontColor;
	private int xEnd;
	private TextFlow textBox;
	private SlideShow parent;

	/*
	 * Text Handler Constructor
	 * @Param parent: Reference back to the slideshow which has called the handler
	 * @Param textBody: TextBody containing the input text string, italic, bold, underline and branch elements
	 * @Param font: The font of the text string
	 * @Param xStart: The x co ordinate of the top left of the hBox
	 * @Param yStart:The y co ordinate of the top left of the hBox
	 * @Param fontSize: The size of the font
	 * @Param fontColor: Color of the font in hex
	 * @Param xEnd: The x co ordinate of the bottom right of the hBox
	 * @Param yEnd: The y co ordinte of the bottom right of the hBox
	 * @Param startTime: The time of the text to appear on the screen
	 * @Param duration: The time of the text to be removed from the screen
	 */
	public TextHandler(SlideShow parent, TextBody textBody, String font, Integer xStart, Integer yStart, Integer fontSize, 
			String fontColor, Integer xEnd, Integer yEnd, Integer startTime, Integer duration, 
			Integer branchID, Integer orientation){
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);
		
		this.font = font;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
		this.xEnd = xEnd;
		this.parent = parent;
		
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
		//Sets the size of the text flow
		 textBox.setMaxWidth(xEnd - xStart);
		 hbox.getChildren().add(textBox);
		 
		 setTimingValues();		
	}
	
	/*
	 * Sets all of the attributes for a a single text element
	 * @Param textString: 
	 */
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
		 text.setFont(Font.font(font,weight, posture, (double)fontSize));
		 
		//Underlines the text if true, no underline if false
		 if(textString.getUnderline() != null){
			 text.setUnderline(textString.getUnderline());
		 }
		 
		 //Sets the colour of the text, set Fill sets the interior fill colour
		 // Set stroke sets the outline of the text.
		 text.setFill(Color.web(fontColor));
		 
		//Sets the wrapping width of the text object, if  x end is null, the wrapping width is set to the edge 
		 // of the screen.
		 text.setWrappingWidth((xEnd - xStart));
		 
		 // Gets the branch id from the text String
		 final Integer branch = textString.getBranch();
		 
		 //Adds an event handler to call the new branch slide when the specific section of text with the branch ID is selected#
		 // if the branch is not null
		if (branch != null){
		 
		 text.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				parent.tearDownHandlers();
				parent.newSlide(branch, true, parent.getTimerData());
				
			}
			 
		 });
		}
		 return text;
	}

}
