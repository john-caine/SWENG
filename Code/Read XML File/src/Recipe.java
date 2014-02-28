/* Title: XMLReader
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 14/02/14
 * 
 * Description: A class to contain all information associated with a particular recipe.
 * 				Fields include ID, title, number of servings, time taken etc.
 * 				Methods are provided for 'setting' and 'getting' each field.
 * 
 * Version History: v1.01 (27/02/14) - Class modified to include functionality to set and get imageFileName fields
 * 									 - Type of 'people' and 'time' variables changed from String to int
 * 									 - Setters now take Object as input, and do type conversion as appropriate to the field
 */

import java.io.Serializable;

public class Recipe implements Serializable {
	private static final long serialVersionUID = 1L;
	String id, title, chef, description, imageFileName;
	int people, time;
		
	public Recipe() {
	}

	// getters
	public String getID() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getPeople() {
		return people;
	}
	
	public int getTime() {
		return time;
	}
	
	public String getChef() {
		return chef;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getImageFileName() {
		return imageFileName;
	}
	
	// setters
	public void setID(Object id) {
		this.id = (String) id;
	}
	
	public void setTitle(Object title) {
		this.title = (String) title;		
	}

	public void setPeople(Object people) {
		this.people = Integer.valueOf((String) people);		
	}
	
	public void setTime(Object time) {
		this.time = Integer.valueOf((String) time);
	}
	
	public void setChef(Object chef) {
		this.chef = (String) chef;
	}
	
	public void setDescription(Object description) {
		this.description = (String) description;
	}
	
	public void setImageFileName(Object imageFileName) {
		this.imageFileName = (String) imageFileName;
	}
}