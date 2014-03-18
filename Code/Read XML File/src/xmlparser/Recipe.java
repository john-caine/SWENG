package xmlparser;
/* Title: Recipe
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
 * 					v1.1  (05/03/14) - Recipe class now complies with PWS standards from sample XML file
 * 									 - New fields of type Info and Defaults, with a list to hold slide instances
 * 					
 */

import java.util.ArrayList;
import java.util.List;

public class Recipe {
	Info info;
	Defaults defaults;
	List<Slide> slides;
		
	public Recipe() {
		slides = new ArrayList<Slide>();
		info = new Info();
		defaults = new Defaults();
	}

	public List<Slide> getSlides() {
		return slides;
	}

	public Slide getSlide(int slideNumber) {
		return slides.get(slideNumber);
	}

	public void addSlide(Slide slide) {
		slides.add(slide);
	}

	public int getNumberOfSlides() {
		return slides.size();
	}
}