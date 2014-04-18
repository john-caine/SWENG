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
	
	private TextHandler text_object;
	private SlideShow parent;
	private XMLReader reader;
	private List<TextBody> textList;

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
		assertTrue(text_object.textBox.getChildren().get(0) instanceof Text);
		Text testText = (Text) text_object.textBox.getChildren().get(0);
		assertEquals("This is a test of required attributes", testText.getText());
		//Bold and Italic attributes are returned as part of the font when set
		assertEquals("Times New Roman Bold Italic", testText.getFont().getName());
		assertEquals("0x00ffffff", testText.getFill().toString());
		assertTrue(testText.isUnderline());
	}
	
	//Test that the TextFlow XY co-ordinates have been correctly set.
	@Test
	public void seTextFlowXYcoordinates (){
		assertEquals(10, text_object.textBox.getLayoutX(),0.1);
		assertEquals(10, text_object.textBox.getLayoutY(), 0.1);	
	}
}
