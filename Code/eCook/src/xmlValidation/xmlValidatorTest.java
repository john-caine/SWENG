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

import xmlparser.XMLReader;

public class xmlValidatorTest {
	private xmlValidator xmlValidationInfo;
	
	@Before
	public void setUp() throws Exception {
	}
	
	/*
	 *  Test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
	 */
	@Test
	public void isXMLVersionCorrect() throws Exception {
		xmlValidationInfo = new xmlValidator("../Resources/PWSExamplePlaylist_3.xml");
		assertEquals(true, xmlValidationInfo.isXMLVersionCorrect());
		xmlValidationInfo = new xmlValidator("../Resources/InvalidVersionPlaylist.xml");
		assertEquals(false, xmlValidationInfo.isXMLVersionCorrect());
	}
	
	/*
	 * Test to check that an invalid syntax XML file (broken) reports a meaningful error message
	 */
	@Test
	public void isXMLFileBroken() throws Exception {
		xmlValidationInfo = new xmlValidator("../Resources/TEST_BrokenInvalidVersionPlaylist.xml");
		assertEquals(true, xmlValidationInfo.isXMLBroken());
		if (xmlValidationInfo.isXMLBroken()) {
			// Write the actual error string to the console, not actually important
			// Important part is that it has picked up the only error we can test for currently
			// (syntax errors)
			// Other possible errors are within SAX parser and corrupt files
			System.out.println(xmlValidationInfo.getErrorMsg());
		}
	}
}