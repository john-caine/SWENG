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
 * 
 */

public class Info {
	String author, version, title, comment, cook, prep, numOfGuests, veg = null;
	Integer width, height = null;

	// getters
	/**
	 * Get the XML Autor
	 * @return author: XML author name
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Get the XML version
	 * @return version: The XML version
	 */
	public String getVersion() {
		return version;
	}
	
	/**
	 * Get the title of the Recipe
	 * @return title: Recipe title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Get any comments about the recipe
	 * @return: The recipe comments
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Get the name of the chef
	 * @return cook: name of the chef
	 */
	public String getCook() {
		return cook;
	}
	/**
	 * Get the prep time
	 * @return prep: Amount of prep time required
	 */
	public String getPrep() {
		return prep;
	}
	
	/**
	 * Get the number of guests the recipe is for
	 * @return numOfGuests: Number of guests
	 */
	public String getGuests() {
		return numOfGuests;
	}
	
	/**
	 * Get if the recipe is vegetarian
	 * @return: The vegetarian suitability
	 */
	public String getVeg() {
		return veg;
	}
	
	/**
	 * The
	 * @return the width
	 */
	public Integer getWidth() {
		return width;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	/**
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
	/**
	 * Set the XML author field
	 * @param author: The xml author
	 */
	public void setAuthor(Object author) {
		if (author != null) {
			this.author = (String) author;
		}
	}
	
	/**
	 * Set the XML version field
	 * @param version: The XML version
	 */
	public void setVersion(Object version) {
		if (version != null) {
			this.version = (String) version;
		}
	}
	
	/**
	 * Set the XML title field
	 * @param title: The XML title
	 */
	public void setTitle(Object title) {
		if (title != null) {
			this.title = (String) title;
		}
	}

	/**
	 * Set the recipe comment field
	 * @param comment: The recipe comment
	 */
	public void setComment(Object comment) {
		if (comment != null) {
			this.comment = (String) comment;
		}
	}
	/**
	 * Set the name of the chef
	 * @param cook: The chef name
	 */
	public void setCook(Object cook) {
		if (cook != null) {
			this.cook = (String) cook;
		}
	}
	/**
	 * Set the prep time
	 * @param prep: The recipe preparation time
	 */
	public void setPrep(Object prep) {
		if (prep != null) {
			this.prep = (String) prep;
		}
	}
	
	/**
	 * Set the number of guests the recipe is for
	 * @param numberOfGuests: The number of guests
	 */
	public void setGuests(Object numberOfGuests) {
		if (numberOfGuests != null) {
			this.numOfGuests = (String) numberOfGuests;
		}
	}
	
	/**
	 * Set if the recipe is vegetarian 
	 * @param veg: recipe vegetarian suitablityl
	 */
	public void setVeg(Object veg) {
		if (veg != null) {
			this.veg = (String) veg;
		}
	}
	
	/**
	 * Set the XML width
	 * @param width
	 */
	public void setWidth(Object width) {
		if (width != null) {
			this.width = Integer.valueOf((String) width);
		}
	}
	
	/**
	 * Set the XML height
	 * @param height
	 */
	public void setHeight(Object height) {
		if (height != null) {
			this.height = Integer.valueOf((String) height);
		}
	}
}