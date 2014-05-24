package xmlValidation;
/* Title: XMLValidation
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 07/05/14
 * 
 * Description: Test class for XMLValidator class
 * 
 * Version History: v0.1 (07/05/14) - Added test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
 * 					v1.0 (12/05/14)	- Complete test class with 12 tests written to diagnose a huge number of potential XML issues
 */
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import xmlparser.XMLReader;

public class XMLValidatorTest {
	private XMLReader reader;
	private XMLValidator xmlValidationInfo;
	
	@Before
	public void setUp() throws Exception {
	}
	
	/*
	 * Test to check that no errors are reported for an OK XML file
	 */
	@Test
	public void areNoErrorsReported() {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_3.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(false, xmlValidationInfo.isXMLBroken());
	}
	
	/*
	 *  Test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
	 */
	@Test
	public void isXMLVersionHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_InvalidVersionPlaylist.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Error message should be error with XML version
			System.out.println("isXMLVersionHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Unsupported playlist version.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that an invalid syntax XML file (broken) reports a meaningful error message
	 */
	@Test
	public void isXMLSyntaxHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_BrokenPlaylist.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
			System.out.println("isXMLSyntaxHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: File could not be read; errors exist in XML.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a non-existant or corrupted file reports meaningful error message
	 */
	@Test
	public void isCorruptXMLHandled() throws Exception {
		reader = new XMLReader("AFilePathThatDoesNotExist");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isCorruptXMLHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: File could not be read; it could be corrupt or damaged.", xmlValidationInfo.getErrorMsg());
		}
	}

	/*
	 * Test to check that the input recipe contains slideshow information section
	 */
	@Test
	public void isMissingInfoHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingInfoPlaylist.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingInfoHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing parameters from documentinfo.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that the input recipe contains defaults section
	 */
	@Test
	public void isMissingDefaultsHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingDefaultsPlaylist.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingDefaultsHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing parameters from defaults.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that the input recipe contains last slideshow element
	 */
	@Test
	public void isMissingLastSlideHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingLastSlideshowPlaylist.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingLastSlideHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing parameters from slide.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a missing text attribute is picked up
	 */
	@Test
	public void isMissingTextAttributeHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingTextAttributes.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingTextAttributeHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing text attributes from slide 0.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a missing text attribute is picked up
	 */
	@Test
	public void isMissingShapeAttributeHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingShapeAttributes.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingShapeAttributeHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing shape attributes from slide 1.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a missing text attribute is picked up
	 */
	@Test
	public void isMissingAudioAttributeHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingAudioAttributes.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingAudioAttributeHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing audio attributes from slide 2.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a missing text attribute is picked up
	 */
	@Test
	public void isMissingImageAttributeHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingImageAttributes.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingImageAttributeHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing image attributes from slide 3.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	/*
	 * Test to check that a missing text attribute is picked up
	 */
	@Test
	public void isMissingVideoAttributeHandled() throws Exception {
		reader = new XMLReader("../Resources/TEST_MissingVideoAttributes.xml");
		xmlValidationInfo = new XMLValidator(reader);
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			System.out.println("isMissingVideoAttributeHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
			assertEquals("Error: Missing video attributes from slide 4.", xmlValidationInfo.getErrorMsg());
		}
	}
	
	
}