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
	List<Ingredient> ingredients;
	List<Slide> slides;
		
	public Recipe() {
		slides = new ArrayList<Slide>();
		info = new Info();
		defaults = new Defaults();
		ingredients = new ArrayList<Ingredient>();
	}
	
	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		System.out.println(errorMessage);
	}

	public List<Slide> getSlides() {
		return slides;
	}
	
	/*
	 * James and Prakruti
	 * 
	 */
	public Boolean lastSlideExists() {
		for (int i = 0; i < slides.size(); i++) {
			if (slides.get(i).lastSlide == true) {
				return true;
			}
		}
		return false;
	}
	
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	
	// method to return a single string with a list of every ingredient stored within the array list
	public String getStringOfIngredients(Integer guests) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Integer i = 0; i < this.getNumberOfIngredients(); i++) {
			 stringBuilder.append(ingredients.get(i).getAmount()*guests+" "+ingredients.get(i).getUnits() + " of "+ingredients.get(i).getName()+"\n");
		}
		return stringBuilder.toString();
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
	
	public Ingredient getIngredient(int ingredientNumber) {
		if (ingredientNumber >= 0 && ingredientNumber < this.getNumberOfIngredients()) {
			return ingredients.get(ingredientNumber);
		}
		else {
			reportError("Error getting ingredient: index out of range");
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
	
	public void addIngredient(Ingredient ingredient) {
		if (ingredient != null) {
			ingredients.add(ingredient);
		}
		else {
			reportError("Error adding ingredient: object received from parser is null");
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
	
	// get the number of ingredients
	public int getNumberOfIngredients() {
		return ingredients.size();
	}
	
	public Defaults getDefaults() {
		 return defaults;
	}
	
	public Info getInfo() {
		return info;
	}
}