package xmlparser;

import java.util.logging.Logger;

import eCook.eCook;

/* Title: Defaults
 * 
 * Programmers: Ankita, Max, James, Prakruti
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain display defaults associated with a recipe.
 * 				Methods are provided for 'setting' and 'getting' fields for this class.
 * 
 * Version History: v1.01 (27/03/14) - Changed type of fontSize from String to int. Removed lineColor.
 * 									 - Updated setters and getters accordingly.
 *  				v1.1  (01/04/14) - Changed type of fontSize from int to Integer.
 *  								 - Added validation to setters (throws error when null).
 *  								 - Added method to report errors (Console print for now but will extend in future).
 *  				v1.11 (06/04/14) - Re-added field 'lineColor' and setter and getter methods.
 *  				v1.2  (18/05/24) - Added function defaultsComplete() - James and Prakruti
 */
public class Defaults {
	String backgroundColor, font, fontColor, fillColor, lineColor = null;
	Integer fontSize = null;
	private Logger logger;
		
	public Defaults() {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
	}

	// getters
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getFont() {
		return font;
	}

	public Integer getFontSize() {
		return fontSize;
	}
	
	public String getFontColor() {
		return fontColor;
	}
	
	public String getFillColor() {
		return fillColor;
	}
	
	public String getLineColor() {
		return lineColor;
	}
	
	/*
	 * James and Prakruti
	 * Decides whether default information has been set
	 */
	public boolean defaultsComplete() {
		if ((backgroundColor == null) || (font == null) || (fontSize == null) || (fontColor == null) || (lineColor == null) || (fillColor == null)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// setters
	public void setBackgroundColor(Object backgroundColor) {
		if (backgroundColor != null) {
			this.backgroundColor = (String) backgroundColor;
		}
	}
	
	public void setFont(Object font) {
		if (font != null) {
			this.font = (String) font;
		}	
	}

	public void setFontSize(Object fontSize) {
		if (fontSize != null) {
			this.fontSize = Integer.valueOf((String) fontSize);
		}	
	}
	
	public void setFontColor(Object fontColor) {
		if (fontColor != null) {
			this.fontColor = (String) fontColor;
		}
	}
	
	public void setFillColor(Object fillColor) {
		if (fillColor != null) {
			this.fillColor = (String) fillColor;
		}	
	}
	
	public void setLineColor(Object lineColor) {
		if (lineColor != null) {
			this.lineColor = (String) lineColor;
		}
	}
}