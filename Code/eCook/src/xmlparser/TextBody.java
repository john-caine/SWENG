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
 * 					v1.1  (01/04/14) - Changed int fields to Integer.
 * 									 - Added validation to getting and setting lists.
 * 					v1.2  (10/04/14) - Changed type of xEnd and yEnd from Integer to int as these are compulsory.
 */

import java.util.ArrayList;
import java.util.List;

public class TextBody extends Content {
	private int xEnd, yEnd;
	private Integer fontSize;
	private String font, fontColor;
	private List<TextString> textBody;
	
	public TextBody() {
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
	
	public Integer getFontSize() {
		return fontSize;
	}
	
	public String getFontColor() {
		return fontColor;
	}
	
	// setters
	public void setXEnd(Object xEnd) {
		if (xEnd != null) {
			this.xEnd = Integer.valueOf((String) xEnd);
		}
		else {
			reportError("xEnd must be specified for this text object");
		}
	}
	
	public void setYEnd(Object yEnd) {
		if (yEnd != null) {
			this.yEnd = Integer.valueOf((String) yEnd);
		}
		else {
			reportError("yEnd must be specified for this text object");
		}
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
	
	// list operations
	public List<TextString> getTextBody() {
		return textBody;
	}

	public TextString getTextString(int textStringNumber) {
		if (textStringNumber >= 0 && textStringNumber < this.getNumberOfTextStrings()) {
			return textBody.get(textStringNumber);
		}
		else {
			reportError("Error getting text string: index out of range");
			return null;
		}
	}

	public void addTextString(TextString textString) {
		if (textString != null) {
			textBody.add(textString);
		}
		else {
			reportError("Error adding text string: object received from parser is null");
		}
	}

	public int getNumberOfTextStrings() {
		return textBody.size();
	}
}