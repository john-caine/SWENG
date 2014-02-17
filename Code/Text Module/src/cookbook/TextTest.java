/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 13/02/2014
 * Description: The text module test class
 * Version History: 1.0 - Created.
 * 
 */

package cookbook;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TextTest {

	private String textObject;
	 TextGenerator text;
	//GUI Test: Displays Text Object in Content Pane 
	
	@Before
	public void SetUp() throws Exception {
		
		text = new TextGenerator();
		text.setText("Your Recipe Here");
	}

	@Test
	public void TextObjectNotNull() {
		
		
		textObject = text.getText();
		assertNotNull(textObject);
		
	}

	@Test 
	public void DisplayTextObject() throws InterruptedException{
		
		text.displayText();
		Thread.sleep(2000);
	}
}
