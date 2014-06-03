/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Slide Class
 */

package xmlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class SlideTest {
	Slide slide;
	
	// check that setting attributes works correctly
	@Test
	public void canSetAttributes() {
		// instantiate a new slide class
		slide = new Slide();
		
		// check that everything is null to start with
		assertNull(slide.id);
		assertEquals(0, slide.duration.intValue());
		assertFalse(slide.lastSlide);
		assertNull(slide.content);
		
		// set arbitrary values
		slide.setID("4");
		slide.setDuration("60");
		slide.setLastSlide("true");
		Content testContent = new Content();
		slide.setContent(testContent);
		
		// check that the fields have been set correctly
		assertEquals(4, slide.id.intValue());
		assertEquals(60, slide.duration.intValue());
		assertTrue(slide.lastSlide);
		assertEquals(testContent, slide.content);
	}
	
	// check that getting attributes works correctly
	@Test
	public void canGetAttributes() {
		// instantiate a new slide class
		slide = new Slide();

		// check that everything is null to start with
		assertNull(slide.id);
		assertEquals(0, slide.duration.intValue());
		assertFalse(slide.lastSlide);
		assertNull(slide.content);

		// set arbitrary values
		slide.setID("4");
		slide.setDuration("60");
		slide.setLastSlide("true");
		Content testContent = new Content();
		slide.setContent(testContent);

		// check that the fields have been set correctly
		assertEquals(4, slide.getID().intValue());
		assertEquals(60, slide.getDuration().intValue());
		assertTrue(slide.getLastSlide());
		assertEquals(testContent, slide.getContent());
	}
}