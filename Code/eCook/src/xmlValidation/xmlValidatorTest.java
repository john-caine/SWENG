package xmlValidation;
/* Title: XMLValidation
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 07/05/14
 * 
 * Description: Test class for XMLValidator class
 * 
 * Version History: v1.1 (07/05/14) - Added test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class xmlValidatorTest {
	private xmlValidator xmlValidationInfo;
	
	@Before
	public void setUp() throws Exception {
	}
	
	/*
	 * Test to check that no errors are reported for an OK XML file
	 */
	@Test
	public void areNoErrorsReported() {
		xmlValidationInfo = new xmlValidator("../Resources/PWSExamplePlaylist_3.xml");
		assertEquals(false, xmlValidationInfo.isXMLBroken());
	}
	
	/*
	 *  Test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
	 */
	@Test
	public void isXMLVersionHandled() throws Exception {
		xmlValidationInfo = new xmlValidator("../Resources/TEST_InvalidVersionPlaylist.xml");
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
		xmlValidationInfo = new xmlValidator("../Resources/TEST_BrokenPlaylist.xml");
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
		xmlValidationInfo = new xmlValidator("AFilePathThatDoesNotExist");
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
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
		xmlValidationInfo = new xmlValidator("../Resources/TEST_MissingInfoPlaylist.xml");
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
			System.out.println("isMissingInfoHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
		}
	}
	
	/*
	 * Test to check that the input recipe contains defaults section
	 */
	@Test
	public void isMissingDefaultsHandled() throws Exception {
		xmlValidationInfo = new xmlValidator("../Resources/TEST_MissingDefaultsPlaylist.xml");
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
			System.out.println("isMissingDefaultsHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
		}
	}
	
	/*
	 * Test to check that the input recipe contains last slideshow element
	 */
	@Test
	public void isMissingLastSlideHandled() throws Exception {
		xmlValidationInfo = new xmlValidator("../Resources/TEST_MissingLastSlideshowPlaylist.xml");
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
			System.out.println("isMissingLastSlideshowHandled():");
			System.out.println(xmlValidationInfo.getErrorMsg() + "\n");
		}
	}
}