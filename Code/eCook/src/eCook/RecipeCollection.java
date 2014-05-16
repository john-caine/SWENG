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
	
	// constructor
	public RecipeCollection() {
		recipes = new ArrayList<Recipe>();
	}
	
	// method to add recipes to the list
	public void addRecipe(Recipe recipe) {
		if (recipe != null) {
			recipes.add(recipe);
		}
	}
	
	// method to get a recipe from the list
	public Recipe getRecipe(int recipeNumber) {
		return recipes.get(recipeNumber);
	}
	
	// method to get the number of recipes in the list
	public int getNumberOfRecipes() {
		return recipes.size();
	}
}
