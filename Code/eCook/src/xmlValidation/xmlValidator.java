package xmlValidation;

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
	private Boolean broken;
	private String errorMsg;

	/*
	 * Constructor sets the desired xml version and reads in inputFile
	 */
	public xmlValidator(String inputFile) {
		versionRequired = 1.1;
		broken = false;
		reader = new XMLReader(inputFile);
		// If there have been errors with the inputFile then set error message
		// and Boolean accordingly
		if (reader.isBroken()) {
			broken = true;
			errorMsg = reader.getErrorMsg();
		} else if (!broken) {
			if (!missingSlideshowElements()) {
				if (!missingElementAttributes()) {
					if (Math.abs(Double.parseDouble(reader.getInfo().getVersion()) - versionRequired) > 0.05) {
						broken = true;
						errorMsg = "Error: Unsupported playlist version.";
					}
				}
			}
		}
	}

	private Boolean missingElementAttributes() {
		// Section needs completing
		return false;
	}

	private Boolean missingSlideshowElements() {
		// See if info, defaults or slide have not been initialised
		if ((reader.getInfo() == null) || (reader.getDefaults() == null)
				|| (reader.getRecipe() == null)) {
			StringBuilder tempString = new StringBuilder();
			tempString.append("Error: Missing section(s) from");
			if (reader.getInfo() == null) {
				tempString.append(" documentinfo,");
			}
			if (reader.getDefaults() == null) {
				tempString.append(" defaults,");
			}
			if (reader.getRecipe() == null) {
				tempString.append(" slide,");
			}
			tempString.setLength(tempString.length() - 1);
			tempString.append(".");
			errorMsg = tempString.toString();
			broken = true;
		}
		// If info, defaults or slides have been initialised check whether all
		// data is there
		else if (!reader.getInfo().infoComplete()
				|| !reader.getDefaults().defaultsComplete()
				|| !reader.getRecipe().lastSlideExists()) {
			StringBuilder tempString = new StringBuilder();
			tempString.append("Error: Missing parameters from");
			if (!reader.getInfo().infoComplete()) {
				tempString.append(" documentinfo,");
			}
			if (!reader.getDefaults().defaultsComplete()) {
				tempString.append(" defaults,");
			}
			if (!reader.getRecipe().lastSlideExists()) {
				tempString.append(" slide,");
			}
			tempString.setLength(tempString.length() - 1);
			tempString.append(".");
			errorMsg = tempString.toString();
			broken = true;
		}
		return broken;

	}

	/*
	 * Function to determine if XML file is broken
	 */
	public boolean isXMLBroken() {
		return broken;
	}

	/*
	 * Function to return String containing error message
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
}
