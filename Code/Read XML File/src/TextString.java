/* Title: TextString
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 08/03/14
 * 
 * Description: TextString is a class that holds a String and three flags to tell if
 * 				the String should be displayed in bold and/or in italics and/or underlined
 */

public class TextString {
	private Boolean bold, italic, underline;
	private String text;
	
	public TextString() {
	}

	// getters
	public Boolean getBold() {
		return bold;
	}

	public Boolean getItalic() {
		return italic;
	}

	public Boolean getUnderline() {
		return underline;
	}
	
	public String getText() {
		return text;
	}
	
	// setters
	public void setBold(Object bold) {
		this.bold = (Boolean) bold;
	}
	
	public void setItalic(Object italic) {
		this.italic = (Boolean) italic;
	}
	
	public void setUnderline(Object underline) {
		this.underline = (Boolean) underline;			
	}
	
	public void setText(Object text) {
		this.text = (String) text;	
	}
}
