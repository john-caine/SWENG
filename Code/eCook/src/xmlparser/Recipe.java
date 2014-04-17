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
 * 									 - Added validation to getting and setting lists.
 *  								 - Added method to report errors (Console print for now but will extend in future).
 *  				v1.2  (16/04/14) - Modified method getNumberOfSlides() to ignore branch slides.
 *  								 - Added new method getNumberOfSlidesIncBranchSlides().
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
	
	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		System.out.println(errorMessage);
	}

	public List<Slide> getSlides() {
		return slides;
	}

	public Slide getSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < this.getNumberOfSlides()) {
			return slides.get(slideNumber);
		}
		else {
			reportError("Error getting slide: index out of range");
			return null;
		}
	}

	public void addSlide(Slide slide) {
		if (slide != null) {
			slides.add(slide);
		}
		else {
			reportError("Error adding slide: object received from parser is null");
		}
	}

	// get the number of slides excluding branch slides
	public int getNumberOfSlides() {
		for (int i=0; i<slides.size(); i++) {
			if (slides.get(i).getLastSlide() == true) {
				return i+1;
			}
		}
		return slides.size();
	}
	
	// get the number of slides including branch slides
	public int getNumberOfSlidesIncBranchSlides() {
		return slides.size();
	}
	
	public Defaults getDefaults() {
		 return defaults;
	}
}