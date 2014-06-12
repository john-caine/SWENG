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
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;
import xmlfilepathhandler.XMLFilepathHandler;

public class Recipe {
	// declare variables
	Info info;
	Defaults defaults;
	List<Ingredient> ingredients;
	List<Slide> slides;
	String fileName = null;
	Integer prevGuests;
	Boolean downloadingLocalContent;
	Boolean localExistance;
	Boolean localExistanceSet;
	Logger logger;
		
	// constructor
	/**
	 * Constructor sets up fields needed for the recipe
	 */
	public Recipe() {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		slides = new ArrayList<Slide>();
		info = new Info();
		defaults = new Defaults();
		ingredients = new ArrayList<Ingredient>();
		prevGuests = 4;
		downloadingLocalContent = false;
		localExistance = false;
		localExistanceSet = false;
	}
	

	/**
	 * Checks if the recipe is being downloaded
	 * @return downloadingLocalContent: downloading local content recipe
	 */
	public Boolean isDownloading() {
		return downloadingLocalContent;
	}
	

	/**
	 * Sets the downloading var
	 * @param value: defines whether isDownloading is ture or false
	 */
	public void setDownloading(Boolean value) {
		localExistanceSet = false;
		downloadingLocalContent = value;
	}

	/**
	 * Checks if the file already exists locally
	 * @return false: if the local content is being downloaded
	 */
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

	/**
	 * Reports errors when setting fields
	 * @param errorMessage
	 */
	public void reportError(String errorMessage) {
		logger.log(Level.WARNING, errorMessage);
	}
	
	/**
	 * Sets the fileName
	 * @param fileName: of type String
	 */
	public void setFileName(String fileName) {
		if (fileName != null && !fileName.equals("")){
			this.fileName = fileName;
		} 
	}
	
	/**
	 * Sets information about the recipe
	 * @param info
	 */
	public void setInfo(Info info) {
		if (info != null) {
			this.info = info;
		}
	}
	
	/**
	 * Sets the defaults of the recipe
	 * @param defaults
	 */
	public void setDefaults(Defaults defaults) {
		if (defaults != null) {
			this.defaults = defaults;
		}
	}

	/**
	 * Gets a list of slides
	 * @return slides
	 */
	public List<Slide> getSlides() {
		return slides;
	}
	

	/**
	 * Checks if last slide exists
	 * @return true if it exists
	 */
	public Boolean lastSlideExists() {
		for (int i = 0; i < slides.size(); i++) {
			if (slides.get(i).lastSlide == true) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets a list of ingredients
	 * @return ingredients
	 */
	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	/**
	 * Sets the basic functionality for the multiple guests feature to multiply the amount of each ingredient by the number of guests
	 * @param guests
	 */
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
			logger.log(Level.WARNING, "Number of guests not set in update amount for n guests");
		}
	}

	/**
	 * Sets range for slideshow and gets slide number
	 * @param slideNumber
	 * @return null if index of slide out of range
	 */
	public Slide getSlide(int slideNumber) {
		if (slideNumber >= 0 && slideNumber < this.getNumberOfSlidesIncBranchSlides()) {
			return slides.get(slideNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting slide: index out of range");
			return null;
		}
	}
	
	/**
	 * Sets range for ingredients and gets ingredient number
	 * @param ingredientNumber
	 * @return null if index of ingredient is out of range
	 */
	public Ingredient getIngredient(int ingredientNumber) {
		if (ingredientNumber >= 0 && ingredientNumber < this.getNumberOfIngredients()) {
			return ingredients.get(ingredientNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting ingredient: index out of range");
			return null;
		}
	}

	/**
	 * Adds slide if slide had a value and isn't null
	 * @param slide
	 */
	public void addSlide(Slide slide) {
		if (slide != null) {
			slides.add(slide);
		}
		else {
			logger.log(Level.SEVERE, "Error adding slide: object received from parser is null");
		}
	}
	
	/**
	 * Adds ingredient if ingredient has a value and isn't null
	 * @param ingredient
	 */
	public void addIngredient(Ingredient ingredient) {
		if (ingredient != null) {
			ingredients.add(ingredient);
		}
		else {
			logger.log(Level.SEVERE, "Error adding ingredient: object received from parser is null");
		}
	}

	/**
	 * Gets the number of slides excluding branch slides
	 * @return i+1
	 */
	public int getNumberOfSlidesExcBranchSlides() {
		for (int i=0; i<slides.size(); i++) {
			if (slides.get(i).getLastSlide() == true) {
				return i+1;
			}
		}
		return slides.size();
	}
	
	/**
	 * Gets the number of slides including branch slides
	 * @return size of slides
	 */
	public int getNumberOfSlidesIncBranchSlides() {
		return slides.size();
	}
	
	/**
	 * Gets the number of ingredients
	 * @return size of ingredients
	 */
	public int getNumberOfIngredients() {
		return ingredients.size();
	}
	
	/**
	 * Gets recipe defaults
	 * @return recipe defaults
	 */
	public Defaults getDefaults() {
		 return defaults;
	}
	
	/**
	 * Gets recipe information 
	 * @return recipe info
	 */
	public Info getInfo() {
		return info;
	}
	
	/**
	 * Gets file name of recipe
	 * @return recipe fileName
	 */
	public String getFileName() {
		return fileName;
	}
}