package texthandler;

/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 1/03/2014
 * Description: The JFX text module test class
 * Version History: 1.0 - Created.
 * 
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import eCook.SlideShow;
import xmlparser.TextBody;
import xmlparser.XMLReader;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;


public class TextHandlerTest {
	
	TextHandler text_object;
	String inputString = "Here is some text";
	String font = "Times New Roman";
	Integer x_start = 20;
	Integer y_start = 20;
	private SlideShow parent;
	private XMLReader reader;
	private List<TextBody> textList;
	
	@Before 
	public void SetUp() {
		
		 reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
		 textList = reader.getRecipe().getSlide(2).getContent().getTexts();
	
	text_object = new TextHandler(parent, textList.get(0), textList.get(0).getFont(), textList.get(0).getXStart(),
			textList.get(0).getYStart(), textList.get(0).getFontSize(), 
			textList.get(0).getFontColor(), textList.get(0).getXEnd(), textList.get(0).getYEnd(), textList.get(0).getStartTime(),
			textList.get(0).getDuration(), 
			textList.get(0).getLayer(),  null,null);
	
	

	
	
	}

	@Test
	public void instanceofHbox() {
		
		
		
		assertTrue(text_object.textBox instanceof TextFlow);
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
