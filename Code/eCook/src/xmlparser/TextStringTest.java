/*
 * Programmer: Max
 * Created: 03/06/2014
 * Description: Simple Unit Tests for TextString Class
 */

package xmlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextStringTest {
	TextString textString;
	
	/*
	 * check that setting attributes works correctly
	 */
	@Test
	public void canSetAttributes() {
		// instantiate a new textString class
		textString = new TextString();
		
		// check that everything is null or reset to start with
		assertNull(textString.branch);
		assertFalse(textString.bold);
		assertFalse(textString.underline);
		assertFalse(textString.italic);
		assertEquals("", textString.text);

		// set arbitrary values
		textString.setBold("true");
		textString.setItalic("true");
		textString.setUnderline("true");
		textString.setText("example text");
		textString.setBranch("13");
		
		// check that the fields have been set correctly
		assertTrue(textString.bold);
		assertTrue(textString.italic);
		assertTrue(textString.underline);
		assertEquals("example text", textString.text);
		assertEquals(13, textString.branch.intValue());
	}
	
	/*
	 *  check that getting attributes works correctly
	 */
	@Test
	public void canGetAttributes() {
		// instantiate a new textString class
		textString = new TextString();

		// check that everything is null or reset to start with
		assertNull(textString.branch);
		assertFalse(textString.bold);
		assertFalse(textString.underline);
		assertFalse(textString.italic);
		assertEquals("", textString.text);

		// set arbitrary values
		textString.setBold("true");
		textString.setItalic("true");
		textString.setUnderline("true");
		textString.setText("example text");
		textString.setBranch("13");

		// check that the fields have been set correctly
		assertTrue(textString.getBold());
		assertTrue(textString.getItalic());
		assertTrue(textString.getUnderline());
		assertEquals("example text", textString.getText());
		assertEquals(13, textString.getBranch().intValue());
	}
}