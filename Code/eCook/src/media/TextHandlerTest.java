/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: Text Handler test class
 */
package media;

import static org.junit.Assert.*;
import java.util.List;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import org.junit.Before;
import org.junit.Test;
import eCook.SlideShow;
import xmlparser.TextBody;
import xmlparser.XMLReader;

public class TextHandlerTest {

	private XMLReader reader;
	private TextHandler textHandler;
	private SlideShow parent;
	private List<TextBody> textList;
	@Before
	public void SetUp(){
		
		reader = new XMLReader("../Resources/PWSExamplePlaylist_4.xml");
		textList = reader.getRecipe().getSlide(0).getContent().getTexts();

		textHandler = new TextHandler(parent, textList.get(0), textList.get(0).getFont(), textList.get(0).getXStart(),
										textList.get(0).getYStart(), textList.get(0).getFontSize(), textList.get(0).getFontColor(),
										textList.get(0).getXEnd(), textList.get(0).getYEnd(), textList.get(0).getStartTime(), textList.get(0).getDuration(),
										textList.get(0).getDuration(), textList.get(0).getOrientation());
	}
	
	/*
	 * Test the Hbox contains a text flow object
	 */
	@Test
	public void hboxContainsTextFlow() {
		
		assertTrue(textHandler.getHbox().getChildren().get(0) instanceof TextFlow);
	}
	
	/*
	 * Test the TextFlow object contains the correct number of text objects
	 */
	@Test
	public void textFlowIsPopulated(){
		assertEquals(1, ((TextFlow)textHandler.getHbox().getChildren().get(0)).getChildren().size());
	}
	
	/*
	 * Test the text object properties are correctly set
	 */
	@Test
	public void setTextAttributes(){
		 Text text = textHandler.getText(0);
		assertEquals("Font[name=Times New Roman Bold Italic, family=Times New Roman, style=Bold Italic, size=16.0]", text.getFont().toString());
		assertEquals("0x00ffffff", text.getFill().toString());
	}

}
