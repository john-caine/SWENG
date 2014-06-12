

package xmlvalidation;

/* Title: XMLValidation
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 07/05/14
 * 
 * Description: Class to validate contents of .xml file according to requirements
 * 
 */
import xmlparser.XMLReader;

public class XMLValidator {
	private static final double versionID = 1.1;
	private Boolean broken;
	private String errorMsg;
	
	/**
	 * Constructor reads in inputFile
	 * @param reader: the xml file to be tested
	 * 
	 */
	public XMLValidator(XMLReader reader) {
		broken = false;
		// Run through the error hierarchy
		// Has there been an error parsing the file?
		if (reader.isBroken()) {
			broken = true;
			errorMsg = reader.getErrorMsg();
		} else if (!broken) {
			// Are there missing "required" elements?
			if (!missingSlideshowElements(reader)) {
				// Are there missing "required" element attributes?
				if (!missingElementAttributes(reader)) {
					// Is the version ID correct?
					if (Math.abs(Double.parseDouble(reader.getInfo().getVersion()) - versionID) > 0.04) {
						broken = true;
						errorMsg = "Error: Unsupported playlist version.";
					}
				}
			}
		}
	}

	/**
	 * Searches content elements for missing PWS essential attributes
	 * @param reader: the xml file to be tested
	 * @return broken: returns true if elements are missing
	 */
	private Boolean missingElementAttributes(XMLReader reader) {
		// Loop through slides determining missing element attributes within them
		for (int i = 0; (i < reader.getRecipe().getNumberOfSlidesIncBranchSlides()) && !broken; i++) {
			// Check that all slide IDs have been set
			if (reader.getRecipe().getSlide(i).getID() == null) {
				broken = true;
				errorMsg = "Error: Missing ID for slide " + i + ".";
			}
			// Check slide i text attributes
			if (!broken) {
				// Check slide i text attributes
				for (int j = 0; (j < reader.getRecipe().getSlide(i).getContent().getTexts().size()) && !broken; j++) {
					if (reader.getRecipe().getSlide(i).getContent().getTexts().get(j).getXStart() == null || reader.getRecipe().getSlide(i).getContent().getTexts().get(j).getYStart() == null || reader.getRecipe().getSlide(i).getContent().getTexts().get(j).getXEnd() == null || reader.getRecipe().getSlide(i).getContent().getTexts().get(j).getYEnd() == null) {
						broken = true;
						errorMsg = "Error: Missing text attributes from slide " + i + ".";
					}
				}
				if (!broken) {
					// Check slide i shape attributes
					for (int j = 0; (j < reader.getRecipe().getSlide(i).getContent().getShapes().size()) && !broken; j++) {
						if (reader.getRecipe().getSlide(i).getContent().getShapes().get(j).getTotalPoints() == null || reader.getRecipe().getSlide(i).getContent().getShapes().get(j).getWidth() == null || reader.getRecipe().getSlide(i).getContent().getShapes().get(j).getHeight() == null) {
							broken = true;
							errorMsg = "Error: Missing shape attributes from slide " + i + ".";
						}
					}
					if (!broken) {
						// Check slide i audio attributes
						for (int j = 0; (j < reader.getRecipe().getSlide(i).getContent().getAudios().size()) && !broken; j++) {
							if (reader.getRecipe().getSlide(i).getContent().getAudios().get(j).getUrlName() == null) {
								broken = true;
								errorMsg = "Error: Missing audio attributes from slide " + i + ".";
							}
						}
						if (!broken) {
							// Check slide i image attributes
							for (int j = 0; (j < reader.getRecipe().getSlide(i).getContent().getImages().size()) && !broken; j++) {
								if (reader.getRecipe().getSlide(i).getContent().getImages().get(j).getUrlName() == null || reader.getRecipe().getSlide(i).getContent().getImages().get(j).getXStart() == null || reader.getRecipe().getSlide(i).getContent().getImages().get(j).getYStart() == null) {
									broken = true;
									errorMsg = "Error: Missing image attributes from slide " + i + ".";
								}
							}
							if (!broken) {
								// Check slide i video attributes
								for (int j = 0; (j < reader.getRecipe().getSlide(i).getContent().getVideos().size()) && !broken; j++) {
									if (reader.getRecipe().getSlide(i).getContent().getVideos().get(j).getUrlName() == null || reader.getRecipe().getSlide(i).getContent().getVideos().get(j).getYStart() == null || reader.getRecipe().getSlide(i).getContent().getVideos().get(j).getXStart() == null) {
										broken = true;
										errorMsg = "Error: Missing video attributes from slide " + i + ".";
									}
								}
							}
						}
					}
				}
			}
		}
		return broken;
	}
	
	/**
	 * Searches for missing fundamental slideshow elements
	 * @param reader: the xml file to be tested
	 * Returns broken: returns true if elements are missing.
	 */
	private Boolean missingSlideshowElements(XMLReader reader) {
		// See if info, defaults or slide have not been initialised
		if ((reader.getInfo() == null) || (reader.getDefaults() == null) || (reader.getRecipe() == null)) {
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
		else if (!reader.getInfo().infoComplete() || !reader.getDefaults().defaultsComplete() || !reader.getRecipe().lastSlideExists()) {
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

	/**
	 * method to determine if XML file is broken
	 * @return: returns true or false if the XML is broken
	 */
	public boolean isXMLBroken() {
		return broken;
	}

	/**
	 * method to return String containing error message
	 * @return errorMsg: The error message string
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
}
