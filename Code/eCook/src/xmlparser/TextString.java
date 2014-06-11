package xmlparser;

public class TextString {
	// declare variables
	Boolean bold = false, italic = false, underline = false;
	Integer branch = null;
	String text = "";

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
		if (bold != null) {
			this.bold = Boolean.valueOf((String) bold);
		}
	}
	
	public void setItalic(Object italic) {
		if (italic != null) {
			this.italic = Boolean.valueOf((String) italic);
		}
	}
	
	public void setUnderline(Object underline) {
		if (underline != null) {
			this.underline = Boolean.valueOf((String) underline);
		}
	}
	
	public void setBranch(Object branch) {
		if (branch != null) {
			this.branch = Integer.valueOf((String) branch);
		}
	}
	
	public void setText(Object text) {
		if (text != null) {
			this.text = (String) text;
		}
	}
}
