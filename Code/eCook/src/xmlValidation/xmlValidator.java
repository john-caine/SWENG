package xmlValidation;

import xmlparser.Info;
/* Title: XMLValidation
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 07/05/14
 * 
 * Description: Class to validate contents of .xml file according to requirements
 * 
 * Version History: v0.1 (07/05/14) - Added class to determine if the version is correct 
 * 					v0.2 (
 */
import xmlparser.XMLReader;

public class xmlValidator {
	private Double versionRequired;
	private XMLReader reader;
	
	/*
	 * Constructor sets the desired xml version and reads in inputFile
	 */
	public xmlValidator(String inputFile) {
		versionRequired = 1.1;
		reader = new XMLReader(inputFile);
	}
	
	/*
	 * Function to determine if the xml version held within xml file
	 */
	public boolean isXMLVersionCorrect() {
		// If the difference between the xml version and the required version is less than 0.05 it's (hopefully!) the correct version
		if(Math.abs(Double.parseDouble(reader.getInfo().getVersion()) - versionRequired) < 0.05) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/*
	 * Function to determine if xml file is broken
	 */
	public boolean isXMLBroken() {
		return reader.isBroken();
	}
	
	/*
	 * Function to return String containing error message
	 */
	public String getErrorMsg() {
		return reader.getErrorMsg();
	}
}
