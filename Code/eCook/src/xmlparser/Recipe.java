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

import xmlfilepathhandler.XMLFilepathHandler;

public class Recipe {
	Info info;
	Defaults defaults;
	List<Ingredient> ingredients;
	List<Slide> slides;
	String fileName = null;
	Integer prevGuests;
	Boolean downloadingLocalContent;
	Boolean localExistance;
	Boolean localExistanceSet;
		
	public Recipe() {
		slides = new ArrayList<Slide>();
		info = new Info();
		defaults = new Defaults();
		ingredients = new ArrayList<Ingredient>();
		prevGuests = 1;
		downloadingLocalContent = false;
		localExistance = false;
		localExistanceSet = false;
	}
	
	// James- use this method in a thread to check if the recipe is being downloaded
	public Boolean isDownloading() {
		return downloadingLocalContent;
	}
	
	// James- use this method to set the downloading var
	public void setDownloading(Boolean value) {
		localExistanceSet = false;
		downloadingLocalContent = value;
	}
	
	public Boolean existsLocally() {
		if (downloadingLocalContent) {
			return false;
		}
		else {
			if (!localExistanceSet) {
				XMLFilepathHandler filepathHandler = new XMLFilepathHandler();
				localExistance = filepathHandler.checkMediaPathsExistOffline(getFileName());
				localExistanceSet = true;
				return localExistance;
			}
			else {
				return localExistance;
			}
		}
	}

	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		System.out.println(errorMessage);
	}
	
	public void setFileName(String fileName) {
		if (fileName != null && !fileName.equals("")){
			this.fileName = fileName;
		} 
	}
	
	public void setInfo(Info info) {
		if (info != null) {
			this.info = info;
		}
	}
	
	public void setDefaults(Defaults defaults) {
		if (defaults != null) {
			this.defaults = defaults;
		}
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

	public void ingredientsAmountUpdate(Integer guests) {
		if (guests != null) {
			for (Integer i = 0; i < this.getNumberOfIngredients(); i++) {
				// Reset to 1 guest
				ingredients.get(i).setAmount(ingredients.get(i).getAmount()/prevGuests);
				ingredients.get(i).setAmount(ingredients.get(i).getAmount()*guests);
			}
		
			prevGuests = guests;
		}
		else {
			/* TODO */
			// Max & logger.
		}
	}

	public Slide getSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < this.getNumberOfSlidesIncBranchSlides()) {
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
	public int getNumberOfSlidesExcBranchSlides() {
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
	
	public String getFileName() {
		return fileName;
	}
}