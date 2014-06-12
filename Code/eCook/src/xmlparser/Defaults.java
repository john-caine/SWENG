package xmlparser;

import java.util.logging.Level;
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
 */
public class Defaults {
	// declare fields
	String backgroundColor = null, font = null, fontColor = null, fillColor = null, lineColor = null;
	Integer fontSize = null;
	Logger logger;
	
	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		logger.log(Level.WARNING, errorMessage);
	}
		
	public Defaults() {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
	}

	// getters
	
	/**
	 * Get the default background colour of the SlideShow
	 * @return backgroundColor: The slideshow background colour
	 */
	public String getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Get the default SlideShow Font
	 * @return font: The SlideShow font
	 */
	public String getFont() {
		return font;
	}

	/**
	 * Get the default SlideShow font size
	 * @return fontSize: The SlideShow font size
	 */
	public Integer getFontSize() {
		return fontSize;
	}
	
	/**
	 * Get the default SlideShow font colour
	 * @return fontColor: The SlideShow font colour
	 */
	public String getFontColor() {
		return fontColor;
	}
	
	/**
	 * Get the default SlideShow shape fill colour
	 * @return fillColor: The SlideShow shape fill colour
	 */
	public String getFillColor() {
		return fillColor;
	}
	
	/**
	 * Get the default SlideShow shape line colour
	 * @return lineColor: The SlideShow line color
	 */
	public String getLineColor() {
		return lineColor;
	}
	
	/**
	 * 
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
	/**
	 * Set the default SlideShow background colour
	 * @param backgroundColor: The SlideShow background colour
	 */
	public void setBackgroundColor(Object backgroundColor) {
		if (backgroundColor != null) {
			this.backgroundColor = (String) backgroundColor;
		}
		else {
			reportError("cannot set backgroundColor default");
		}
	}
	
	/**
	 * Set the default SlideShow font 
	 * @param font: The SlideShow font
	 */
	public void setFont(Object font) {
		if (font != null) {
			this.font = (String) font;
		}
		else {
			reportError("cannot set font default");
		}
	}

	/**
	 * Set the default SlideShow font size
	 * @param fontSize: The SlideShow font size
	 */
	public void setFontSize(Object fontSize) {
		if (fontSize != null) {
			this.fontSize = Integer.valueOf((String) fontSize);
		}
		else {
			reportError("cannot set font size default");
		}
	}
	
	/**
	 * Set the default SlideShow font colour
	 * @param fontColor: The slideshow font colour
	 */
	public void setFontColor(Object fontColor) {
		if (fontColor != null) {
			this.fontColor = (String) fontColor;
		}
		else {
			reportError("cannot set font colour default");
		}
	}
	
	/**
	 * Set the default SlideShow shape fill colour
	 * @param fillColor: SlideShow shape fill colour
	 */
	public void setFillColor(Object fillColor) {
		if (fillColor != null) {
			this.fillColor = (String) fillColor;
		}
		else {
			reportError("cannot set fill colour default");
		}
	}
	
	/**
	 * Set the default SlideShow shape line colour
	 * @param lineColor: SlideShow shape line colour
	 */
	public void setLineColor(Object lineColor) {
		if (lineColor != null) {
			this.lineColor = (String) lineColor;
		}
		else {
			reportError("cannot set line colour default");
		}
	}
}