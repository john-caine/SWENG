package xmlparser;
/* Title: Info
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with a recipe.
 * 				Methods are provided for 'setting' and 'getting' fields for this class.
 *  
 */

public class Info {
	String author, version, comment;
	int width, height;
		
	public Info() {
	}

	// getters
	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}

	public String getComment() {
		return comment;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	// setters
	public void setAuthor(Object author) {
		this.author = (String) author;
	}
	
	public void setVersion(Object version) {
		this.version = (String) version;		
	}

	public void setComment(Object comment) {
		this.comment = (String) comment;		
	}
	
	public void setWidth(Object width) {
		this.width = Integer.valueOf((String) width);
	}
	
	public void setHeight(Object height) {
		this.height = Integer.valueOf((String) height);
	}
}
