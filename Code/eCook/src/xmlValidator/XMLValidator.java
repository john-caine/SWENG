package xmlValidator;
/* Title: XMLValidation
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 07/05/14
 * 
 * Description: Class to validate contents of .xml file according to requirements
 * 
 * Version History: v1.1 (07/05/14) - Added class to determine if the version is correct 
 */
import xmlparser.Info;

public class XMLValidator {
	private Double versionRequired;
	
	/*
	 * Constructor sets the desired xml version
	 */
	public XMLValidator() {
		versionRequired = 1.1;
	}
	/*
	 * Function to determine if the xml version held within Info object is correct
	 */
	public boolean isXMLVersionCorrect(Info xmlInformation) {
		System.out.println(xmlInformation.getVersion());
		// If the difference between the xml version and the required version is less than 0.05 it's (hopefully!) the correct version
		if(Math.abs(Double.parseDouble(xmlInformation.getVersion()) - versionRequired) < 0.05) {
			return true;
		}
		else {
			return false;
		}
	}
}
