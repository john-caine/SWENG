/* Programmer: Max
 * Description: RecipeCollection class to store a list of recipes in eCook
 * Date Created: 16/05/14
 */
package eCook;

import java.util.ArrayList;
import java.util.List;
import xmlparser.Recipe;

public class RecipeCollection {
	List<Recipe> recipes;
	
	/**
	 * Constructor that creates the recipes ArrayList
	 */
	public RecipeCollection() {
		recipes = new ArrayList<Recipe>();
	}
	
	/**
	 * Adds a recipe to the ArrayList provided it is valid.
	 * @param recipe the recipe to be added to the collection.
	 */
	public void addRecipe(Recipe recipe) {
		if (recipe != null) {
			recipes.add(recipe);
		}
	}
	
	/**
	 * Returns the requested recipe by index number of ArrayList
	 * @param recipeNumber the recipe to be retrieved.
	 * @return
	 */
	public Recipe getRecipe(int recipeNumber) {
		return recipes.get(recipeNumber);
	}
	
	/**
	 *  Returns the number of recipes currently in the ArrayList
	 * @return
	 */
	public int getNumberOfRecipes() {
		return recipes.size();
	}
}
