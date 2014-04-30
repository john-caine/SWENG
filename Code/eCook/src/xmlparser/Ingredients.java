/*
 * Programmers: James Sam
 * 
 * Description:
 * A class to hold data relating to multiple ingredients
 * Includes an addIngredient method for adding an ingredient object to an ArrayList of ingredients
 * Includes an ArrayList<Ingredients> to string method for returning a string of ingredients relating to 'n' guests
 * Includes a method to return the actual array list
 * 
 * Created 29/04/2014
 * 
 */
package xmlparser;
import java.util.ArrayList;

public class Ingredients {
	ArrayList<Ingredient> ingredients;
	
	public Ingredients() {
		ingredients = new ArrayList<Ingredient>();
	}
	
	/*
	 * Provides user with an array list of ingredients based on number of guests required
	 */
	public ArrayList<Ingredient> getIngredientsArrayList() {
		return ingredients;
	}
	
	/*
	 * 
	 * Return a single string with a list of every ingredient stored within the array list
	 */
	public String getIngredients(Integer guests) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Integer i = 0; i < ingredients.size(); i++) {
			 stringBuilder.append(ingredients.get(i).getAmount()*guests+" "+ingredients.get(i).getUnits() + " of "+ingredients.get(i).getName()+"\n");
		}
		return stringBuilder.toString();
	}
	
	// add an ingredient to the array list
	public void addIngredient(Ingredient thisIngredient) {
		ingredients.add(thisIngredient);
	}
	
}
