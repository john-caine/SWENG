/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Info Class
 */

package xmlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class InfoTest {
	Info info;
	
	// check that setting attributes works correctly
	@Test
	public void canSetAttributes() {
		// instantiate a new info class
		info = new Info();
		
		// check that everything is null to start with
		assertNull(info.author);
		assertNull(info.version);
		assertNull(info.title);
		assertNull(info.comment);
		assertNull(info.width);
		assertNull(info.height);
		assertFalse(info.infoComplete());
		
		// set arbitrary values
		info.setAuthor("example author");
		info.setVersion("1.1");
		info.setTitle("example title");
		info.setComment("example comment");
		info.setWidth("200");
		info.setHeight("100");
		
		// check that all info fields are registered as complete
		assertTrue(info.infoComplete());
		
		// check that the fields have been set correctly
		assertEquals("example author", info.author);
		assertEquals("1.1", info.version);
		assertEquals("example title", info.title);
		assertEquals("example comment", info.comment);
		assertEquals(200, info.width.intValue());
		assertEquals(100, info.height.intValue());
	}
	
	// check that getting attributes works correctly
	@Test
	public void canGetAttributes() {
		// instantiate a new info class
		info = new Info();
		
		// check that the get methods return null if no data has been set
		assertNull(info.getAuthor());
		assertNull(info.getVersion());
		assertNull(info.getTitle());
		assertNull(info.getComment());
		assertNull(info.getWidth());
		assertNull(info.getHeight());
		assertFalse(info.infoComplete());

		// set arbitrary values
		info.setAuthor("example author");
		info.setVersion("1.1");
		info.setTitle("example title");
		info.setComment("example comment");
		info.setWidth("200");
		info.setHeight("100");

		// check that all info fields are registered as complete
		assertTrue(info.infoComplete());
		
		// check that the fields have been set correctly
		assertEquals("example author", info.getAuthor());
		assertEquals("1.1", info.getVersion());
		assertEquals("example title", info.getTitle());
		assertEquals("example comment", info.getComment());
		assertEquals(200, info.getWidth().intValue());
		assertEquals(100, info.getHeight().intValue());
	}
}