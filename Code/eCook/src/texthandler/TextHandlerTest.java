package texthandler;

/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 1/03/2014
 * Description: The JFX text module test class
 * Version History: 1.0 - Created.
 * 
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;
import xmlparser.TextString;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class TextHandlerTest {
	
	TextHandler text_object;
	String inputString = "Here is some text";
	String font = "Times New Roman";
	Integer x_start = 20;
	Integer y_start = 20;
	private Integer fontsize = 20;
	private String fontcolor = "#00FF00";
	private String linecolor = "#0000FF";
	private Integer x_end = 40;
	private Integer startTime = 5;
	private Integer duration = 20;
	private TextString textString;
	private SlideShow parent;
	private Integer layer;
	private Integer branchID;
	private Integer orientation;
	@Before 
	public void SetUp() {
		
		textString = new TextString();
		textString.setText(inputString);
		textString.setBold(false);
		textString.setItalic(true);
		textString.setUnderline(true);
	
	text_object = new TextHandler(parent, textString, font, x_start, y_start, fontsize, fontcolor, linecolor, x_end,startTime, duration, layer, branchID, orientation);
	
	

	
	
	}

	@Test
	public void instanceofHbox() {
		
		
		
		assertTrue(text_object.textBox instanceof HBox);
	};

	
	@Test 
	public void hboxContainsInstanceofText(){
		
		assertTrue(text_object.textBox.getChildren().get(0) instanceof Text);
		Text testText = (Text) text_object.textBox.getChildren().get(0);
		assertEquals("Here is some text", testText.getText());
		assertEquals("Times New Roman Italic", testText.getFont().getName());
		assertEquals("0x00ff00ff", testText.getFill().toString());
		assertTrue(testText.isUnderline());
		
		
		
	}
	
	
	@Test
	public void setHboxXYcoordinates (){
		
		assertEquals(20, text_object.textBox.getLayoutX(),0.1);
		assertEquals(20, text_object.textBox.getLayoutY(), 0.1);
		
	}
	
	
}
