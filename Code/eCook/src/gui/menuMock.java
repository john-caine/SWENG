package gui;
/*
 * Programmer: Ankita Gangotra
 * Date Created: 06/05/2014
 * Makes a class menuMock to be used by menuTest for testing.
 */
import eCook.RecipeCollection;

public class menuMock extends menu {
	
	/**
	 * Constructor for class menuMock used in menuTest class
	 * @param recipeCollection: The collection of parsed XML recipes
	 */
	public menuMock(RecipeCollection recipeCollection) {
		super(recipeCollection);
		 
	}
	
	/**
	 * Method to return height 
	 * @return: The height of the menu
	 */
	public double height() {
		return height;
	}

	/**
	 * Method to return width 
	 * @return The width of the menu
	 */
	public double width() {
		return width;
	}
}
