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
 */

import java.util.ArrayList;
import java.util.List;

public class Text extends Content {
	private int xEnd, yEnd;
	private String font, fontSize, fontColor;
	private List<TextString> textBody;
	
	public Text() {
		super();
		textBody = new ArrayList<TextString>();
	}

	// getters
	public int getXEnd() {
		return xEnd;
	}

	public int getYEnd() {
		return yEnd;
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
	
	// setters
	public void setXEnd(Object xEnd) {
		this.xEnd = Integer.valueOf((String) xEnd);
	}
	
	public void setYEnd(Object yEnd) {
		this.yEnd = Integer.valueOf((String) yEnd);
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
	
	// list operations
	public List<TextString> getTextBody() {
		return textBody;
	}

	public TextString getTextString(int textStringNumber) {
		return textBody.get(textStringNumber);
	}

	public void addTextString(TextString textString) {
		textBody.add(textString);
	}

	public int getNumberOfTextStrings() {
		return textBody.size();
	}
}