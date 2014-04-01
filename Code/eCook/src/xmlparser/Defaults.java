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
 *  
 */

public class Defaults {
	String backgroundColor, font, fontColor, fillColor;
	int fontSize;
		
	public Defaults() {
	}

	// getters
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getFont() {
		return font;
	}

	public int getFontSize() {
		return fontSize;
	}
	
	public String getFontColor() {
		return fontColor;
	}
	
	public String getFillColor() {
		return fillColor;
	}	
	
	// setters
	public void setBackgroundColor(Object backgroundColor) {
		this.backgroundColor = (String) backgroundColor;
	}
	
	public void setFont(Object font) {
		this.font = (String) font;		
	}

	public void setFontSize(Object fontSize) {
		this.fontSize = Integer.valueOf((String) fontSize);		
	}
	
	public void setFontColor(Object fontColor) {
		this.fontColor = (String) fontColor;
	}
	
	public void setFillColor(Object fillColor) {
		this.fillColor = (String) fillColor;
	}
}