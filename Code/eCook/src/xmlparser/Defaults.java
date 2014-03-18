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
 */

public class Defaults {
	String backgroundColor, font, fontSize, fontColor, lineColor, fillColor;
		
	public Defaults() {
	}

	// getters
	public String getBackgroundColor() {
		return backgroundColor;
	}

	public String getFont() {
		return font;
	}

	public String getFontSize() {
		return fontSize;
	}
	
	public String getFontColor() {
		return fontColor;
	}
	
	public String getLineColor() {
		return lineColor;
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
		this.fontSize = (String) fontSize;		
	}
	
	public void setFontColor(Object fontColor) {
		this.fontColor = (String) fontColor;
	}
	
	public void setLineColor(Object lineColor) {
		this.lineColor = (String) lineColor;
	}
	
	public void setFillColor(Object fillColor) {
		this.fillColor = (String) fillColor;
	}
}