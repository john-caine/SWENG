package gui;
/*
 * Programmer: Ankita Gangotra
 * Date Created: 06/05/2014
 * Makes a class menuMock to be used by menuTest for testing.
 */
import eCook.RecipeCollection;

public class menuMock extends menu {
	
	/*
	 * Constructor for class menuMock used in menuTest class
	 */
	public menuMock(RecipeCollection recipeCollection) {
		super(recipeCollection);
		 
	}
	
	/*
	 * Method to return height 
	 */
	public double height() {
		return height;
	}

	/*
	 * Method to return weight 
	 */
	public double width() {
		return width;
	}
}
