package xmlparser;

public class TextString {
	// declare variables
	Boolean bold = false, italic = false, underline = false;
	Integer branch = null;
	String text = "";

	// getters
	/**
	 * Get TextString bold field
	 * @return bold: Set true if the text is bold
	 */
	public Boolean getBold() {
		return bold;
	}

	/**
	 * Get TextString Italic field
	 * @return italic: Set true if the text is italic
	 */
	public Boolean getItalic() {
		return italic;
	}

	/**
	 * Get TextString Underline field
	 * @return underline: Set true if text is underlined
	 */
	public Boolean getUnderline() {
		return underline;
	}
	
	/**
	 * Get the slide BranchId the text is to branch to 
	 * @return branch: The slide branch Id
	 */
	public Integer getBranch() {
		return branch;
	}
	
	/**
	 * Get the TextString text
	 * @return: The TextString text
	 */
	public String getText() {
		return text;
	}
	
	// setters
	/**
	 * Set the TextString bold field
	 * @param bold: Set true if the text is bold
	 */
	public void setBold(Object bold) {
		if (bold != null) {
			this.bold = Boolean.valueOf((String) bold);
		}
	}
	
	/**
	 * Set the TextString Italic field
	 * @param italic: Set true if the text is italic
	 */
	public void setItalic(Object italic) {
		if (italic != null) {
			this.italic = Boolean.valueOf((String) italic);
		}
	}
	
	/**
	 * Set the TextString Underline field
	 * @param underline: Set true if the text is underlined
	 */
	public void setUnderline(Object underline) {
		if (underline != null) {
			this.underline = Boolean.valueOf((String) underline);
		}
	}
	
	/**
	 * Set the text branch slide ID
	 * @param branch: The slideID
	 */
	public void setBranch(Object branch) {
		if (branch != null) {
			this.branch = Integer.valueOf((String) branch);
		}
	}
	/**
	 * Set the TextString
	 * @param text: The text 
	 */
	public void setText(Object text) {
		if (text != null) {
			this.text = (String) text;
		}
	}
}
