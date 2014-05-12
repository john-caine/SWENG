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
	private XMLReader reader;
	
	@Before
	public void setUp() throws Exception {
		xmlValidationInfo = new xmlValidator();
	}
	
	/*
	 *  Test to check that a version 1.1 file is valid and a version 1.x (!= 2) is not valid
	 */
	@Test
	public void isXMLVersionCorrect() throws Exception {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_3.xml");
		assertEquals(true, xmlValidationInfo.isXMLVersionCorrect(reader.getInfo()));
		reader = new XMLReader("../Resources/InvalidVersionPlaylist.xml");
		assertEquals(false, xmlValidationInfo.isXMLVersionCorrect(reader.getInfo()));
	}
}