package xmlparser;
/* Title: Info
 * 
 * Programmers: Ankita, Max, James, Prakruti
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with a recipe.
 * 				Methods are provided for 'setting' and 'getting' fields for this class.
 * 
 * Version History: v1.01 (27/03/14) - Added field 'title' along with setters and getters.
 * 					v1.1  (01/04/14) - Changed type of width and height from int to Integer.
 *  				v1.2  (18/05/14) - Added function infoComplete() - James and Prakruti
 *  				v1.3  (06/06/14) - Added cooking time, preperation time, average price and suitable for vegetarian elements
 *  
 */

public class Info {
	String author, version, title, comment, cook, prep, numOfGuests, veg = null;
	Integer width, height = null;

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
	
	public String getCook() {
		return cook;
	}
	
	public String getPrep() {
		return prep;
	}
	
	public String getGuests() {
		return numOfGuests;
	}
	
	public String getVeg() {
		return veg;
	}
	
	public Integer getWidth() {
		return width;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	/*
	 * James and Prakruti
	 * Decides whether default information has been set
	 */
	public boolean infoComplete() {
		if ((author == null) || (version == null) || (title == null) || (comment == null) || (width == null) || (height == null)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	// setters
	public void setAuthor(Object author) {
		if (author != null) {
			this.author = (String) author;
		}
	}
	
	public void setVersion(Object version) {
		if (version != null) {
			this.version = (String) version;
		}
	}
	
	public void setTitle(Object title) {
		if (title != null) {
			this.title = (String) title;
		}
	}

	public void setComment(Object comment) {
		if (comment != null) {
			this.comment = (String) comment;
		}
	}
	public void setCook(Object cook) {
		if (cook != null) {
			this.cook = (String) cook;
		}
	}
	public void setPrep(Object prep) {
		if (prep != null) {
			this.prep = (String) prep;
		}
	}
	public void setGuests(Object numberOfGuests) {
		if (numberOfGuests != null) {
			this.numOfGuests = (String) numberOfGuests;
		}
	}
	public void setVeg(Object veg) {
		if (veg != null) {
			this.veg = (String) veg;
		}
	}
	
	public void setWidth(Object width) {
		if (width != null) {
			this.width = Integer.valueOf((String) width);
		}
	}
	
	public void setHeight(Object height) {
		if (height != null) {
			this.height = Integer.valueOf((String) height);
		}
	}
}