/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Defaults Class
 */

package xmlparser;

import static org.junit.Assert.*;
import org.junit.Test;

public class DefaultsTest {
	Defaults defaults;
	
	/*
	 * check that setting attributes works correctly
	 */
	@Test
	public void canSetAttributes() {
		// instantiate a new defaults class
		defaults = new Defaults();
		
		// check that everything is null to start with
		assertNull(defaults.backgroundColor);
		assertNull(defaults.font);
		assertNull(defaults.fontColor);
		assertNull(defaults.fillColor);
		assertNull(defaults.lineColor);
		assertNull(defaults.fontSize);
		assertFalse(defaults.defaultsComplete());
		
		// set arbitrary values
		defaults.setBackgroundColor("example background colour");
		defaults.setFont("example font");
		defaults.setFontColor("example font colour");
		defaults.setFillColor("example fill colour");
		defaults.setLineColor("example line colour");
		defaults.setFontSize("12");
		
		// check that all default fields are registered as complete
		assertTrue(defaults.defaultsComplete());
		
		// check that the fields have been set correctly
		assertEquals("example background colour", defaults.backgroundColor);
		assertEquals("example font", defaults.font);
		assertEquals("example font colour", defaults.fontColor);
		assertEquals("example fill colour", defaults.fillColor);
		assertEquals("example line colour", defaults.lineColor);
		assertEquals(12, defaults.fontSize.intValue());
	}
	
	/*
	 * check that getting attributes works correctly
	 */
	@Test
	public void canGetAttributes() {
		// instantiate a new defaults class
		defaults = new Defaults();
		
		// check that the get methods return null if no data has been set
		assertNull(defaults.getBackgroundColor());
		assertNull(defaults.getFont());
		assertNull(defaults.getFontColor());
		assertNull(defaults.getFillColor());
		assertNull(defaults.getLineColor());
		assertNull(defaults.getFontSize());
		assertFalse(defaults.defaultsComplete());

		// set arbitrary values
		defaults.setBackgroundColor("example background colour");
		defaults.setFont("example font");
		defaults.setFontColor("example font colour");
		defaults.setFillColor("example fill colour");
		defaults.setLineColor("example line colour");
		defaults.setFontSize("12");
		
		// check that all default fields are registered as complete
		assertTrue(defaults.defaultsComplete());
		
		// check that the get methods work correctly
		assertEquals("example background colour", defaults.getBackgroundColor());
		assertEquals("example font", defaults.getFont());
		assertEquals("example font colour", defaults.getFontColor());
		assertEquals("example fill colour", defaults.getFillColor());
		assertEquals("example line colour", defaults.getLineColor());
		assertEquals(12, defaults.getFontSize().intValue());
	}
}