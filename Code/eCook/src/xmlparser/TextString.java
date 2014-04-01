package xmlparser;
/* Title: TextString
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 08/03/14
 * 
 * Description: TextString is a class that holds a String and three flags to tell if
 * 				the String should be displayed in bold and/or in italics and/or underlined
 * 
 * Version History: v1.01 (27/03/14) - Added branch field and setter and getter methods.
 * 					v1.1  (01/04/14) - Changed type of branch from int to Integer.
 */

public class TextString {
	private Boolean bold, italic, underline;
	private Integer branch;
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
	
	public Integer getBranch() {
		return branch;
	}
	
	public String getText() {
		return text;
	}
	
	// setters
	public void setBold(Object bold) {
		this.bold = Boolean.valueOf((String) bold);
	}
	
	public void setItalic(Object italic) {
		this.italic = Boolean.valueOf((String) italic);
	}
	
	public void setUnderline(Object underline) {
		this.underline = Boolean.valueOf((String) underline);			
	}
	
	public void setBranch(Object branch) {
		this.branch = Integer.valueOf((String) branch);
	}
	
	public void setText(Object text) {
		this.text = (String) text;	
	}
}
