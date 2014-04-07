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
 * Version History: v1.01 (27/03/14) - Added field 'title' along with setters and getters.
 * 					v1.1  (01/04/14) - Changed type of width and height from int to Integer.
 *  
 */

public class Info {
	String author, version, title, comment;
	Integer width, height;
		
	public Info() {
	}

	// getters
	public String getAuthor() {
		return author;
	}

	public String getVersion() {
		return version;
	}
	
	public String getTitle() {
		return title;
	}

	public String getComment() {
		return comment;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	// setters
	public void setAuthor(Object author) {
		this.author = (String) author;
	}
	
	public void setVersion(Object version) {
		this.version = (String) version;		
	}
	
	public void setTitle(Object title) {
		this.title = (String) title;
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
