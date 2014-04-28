package texthandler;

/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 1/03/2014
 * Description: The JFX text module test class. StartTime and Duration tests are conducted in the DTP.
 * Tests all PWS inputs with the exception of Branching which is to be added.
 
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
	
	private TextHandler text_object;
	private SlideShow parent;
	private XMLReader reader;
	private List<TextBody> textList;
	
	//Read XML playlist and create a new text object from the first text content found
	@Before 
	public void SetUp() {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
	 	textList = reader.getRecipe().getSlide(0).getContent().getTexts();
	 	text_object = new TextHandler(parent, textList.get(0), textList.get(0).getFont(), textList.get(0).getXStart(),
		textList.get(0).getYStart(), textList.get(0).getFontSize(), 
		textList.get(0).getFontColor(), textList.get(0).getXEnd(), textList.get(0).getYEnd(), textList.get(0).getStartTime(),
		textList.get(0).getDuration(), 
		textList.get(0).getLayer(), null /*BranchID*/, null /*orientation*/);
	}

	//Test that a a textFlow object has been created.
	@Test
	public void instanceofTextFlow() {	
		assertTrue(text_object.textBox instanceof TextFlow);
	};

	//Test the attributes of the textflow object match the attributes from the XML playlist
	@Test 
	public void hboxContainsInstanceofText(){
		//Test the TextFlow object contains a Text Object
		assertTrue(text_object.textBox.getChildren().get(0) instanceof Text);
		//Get the text object for testing
		Text testText = (Text) text_object.textBox.getChildren().get(0);
		//Test correct string has been set to String object
		assertEquals("This is a test of required attributes", testText.getText());
		//Test font, Bold and Italic attributes are returned as part of the font when set
		assertEquals("Times New Roman Bold Italic", testText.getFont().getName());
		//Test the font size
		assertEquals(16, testText.getFont().getSize(), 0.1);
		//Test text colour
		assertEquals("0x00ffffff", testText.getFill().toString());
		//Test text is underlined
		assertTrue(testText.isUnderline());
		//Test text correctly wrapped
		assertEquals((textList.get(0).getXEnd() - textList.get(0).getXStart()), testText.getWrappingWidth(), 0.1);

	}
	
	//Test that the TextFlow XY co-ordinates have been correctly set.
	@Test
	public void seTextFlowXYcoordinates (){
		assertEquals(10, text_object.textBox.getLayoutX(),0.1);
		assertEquals(10, text_object.textBox.getLayoutY(), 0.1);	
	}
}
