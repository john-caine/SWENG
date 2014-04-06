package xmlparser;
/* Title: Defaults
 * 
 * Programmers: Ankita, Max
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
 */
public class Defaults {
	String backgroundColor, font, fontColor, fillColor, lineColor;
	Integer fontSize;
		
	public Defaults() {
	}
	
	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		System.out.println(errorMessage);
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
	
	// setters
	public void setBackgroundColor(Object backgroundColor) {
		if (backgroundColor != null) {
			this.backgroundColor = (String) backgroundColor;
		}
		else {
			reportError("error setting background color: object received from parser is null");
		}
	}
	
	public void setFont(Object font) {
		if (font != null) {
			this.font = (String) font;
		}
		else {
			reportError("error setting font: object received from parser is null");
		}		
	}

	public void setFontSize(Object fontSize) {
		if (fontSize != null) {
			this.fontSize = Integer.valueOf((String) fontSize);
		}
		else {
			reportError("error setting font size: object received from parser is null");
		}		
	}
	
	public void setFontColor(Object fontColor) {
		if (fontColor != null) {
			this.fontColor = (String) fontColor;
		}
		else {
			reportError("error setting font colour: object received from parser is null");
		}	
	}
	
	public void setFillColor(Object fillColor) {
		if (fillColor != null) {
			this.fillColor = (String) fillColor;
		}
		else {
			reportError("error setting fill colour: object received from parser is null");
		}	
	}
	
	public void setLineColor(Object lineColor) {
		if (lineColor != null) {
			this.lineColor = (String) lineColor;
		}
		else {
			reportError("error setting line colour: object received from parser is null");
		}	
	}
}