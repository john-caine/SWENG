package xmlparser;
/* Title: Text
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with slide text.
 * 				Extends common methods and variables from Content class.
 * 				Methods are provided for 'setting' and 'getting' unique fields for this class.
 * 
 * Version History: v1.01 (?) - Changed name of class from Text to TextBody to avoid JavaFX protected keyword confusion.
 * 					v1.1  (01/04/14) - Changed Integer fields to Integer.
 * 									 - Added validation to getting and setting lists.
 * 					v1.2  (10/04/14) - Changed type of xEnd and yEnd from Integer to Integer as these are compulsory.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;

public class TextBody extends Content {
	// declare variables
	private Integer xEnd, yEnd = null;
	private Integer fontSize = null;
	private String font, fontColor = null;
	private List<TextString> textBody;
	private Logger logger;
	
	/**
	 * TextBody constructor
	 */
	public TextBody() {
		super();
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		textBody = new ArrayList<TextString>();
	}

	// getters

	 /**
	  * Get the TextBody Xend
	  * @return xEnd: The textBody xEnd
	  */
	public Integer getXEnd() {
		return xEnd;
	}

	/**
	 * Get the TextBody Yend
	 * @return yEnd: The TextBody yEnd
	 */
	public Integer getYEnd() {
		return yEnd;
	}
	
	/**
	 * Get the TextBody font
	 * @return font: The TextBody font
	 */
	public String getFont() {
		return font;
	}
	
	/**
	 * Get the TextBody font size
	 * @return: The TextBody font size
	 */
	public Integer getFontSize() {
		return fontSize;
	}
	
	/**
	 * Get the TextBody font colour
	 * @return: The TextBody font colour
	 */
	public String getFontColor() {
		return fontColor;
	}
	
	// setters
	/**
	 * Set the TextBody XEnd
	 * @param xEnd: The TextBody Xend
	 */
	public void setXEnd(Object xEnd) {
		if (xEnd != null) {
			this.xEnd = Integer.valueOf((String) xEnd);
		}
	}
	
	/**
	 * Set the TextBody YEnd
	 * @param yEnd: The TextBody Yend
	 */
	public void setYEnd(Object yEnd) {
		if (yEnd != null) {
			this.yEnd = Integer.valueOf((String) yEnd);
		}
	}
	
	/**
	 * Set the TextBody font
	 * @param font: The TextBody font
	 */
	public void setFont(Object font) {
		if (font != null) {
			this.font = (String) font;
		}
	}
	
	/**
	 * Set the TextBody font size
	 * @param fontSize: The TextBody font size
	 */
	public void setFontSize(Object fontSize) {
		if (fontSize != null) {
			this.fontSize = Integer.valueOf((String) fontSize);
		}
	}
	
	/**
	 * Set the TextBody font colour
	 * @param fontColor: Set the TextBody fontColour
	 */
	public void setFontColor(Object fontColor) {
		if (fontColor != null) {
			this.fontColor = (String) fontColor;
		}
	}
	
	// list operations
	/**
	 * Get the List of TextStrings
	 * @return textBody: List of Text String objects
	 */
	public List<TextString> getTextBody() {
		return textBody;
	}

	/**
	 * Get a Text string from a TextString List index
	 * @param textStringNumber: The TextString List index
	 * @return The TextString at index textStringNumber
	 */
	public TextString getTextString(Integer textStringNumber) {
		if (textStringNumber >= 0 && textStringNumber < this.getNumberOfTextStrings()) {
			return textBody.get(textStringNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting text string: index out of range");
			return null;
		}
	}

	/**
	 * Add a TextString to the TextString List
	 * @param textString: A TextString Object
	 */
	public void addTextString(TextString textString) {
		if (textString != null) {
			textBody.add(textString);
		}
		else {
			logger.log(Level.SEVERE, "Error adding text string: object received from parser is null");
		}
	}

	/**
	 * Get the total number of TextString objects
	 * @return The TextString List size
	 */
	public Integer getNumberOfTextStrings() {
		return textBody.size();
	}
}