package xmlparser;
import java.util.ArrayList;

public class Ingredients {
	
	ArrayList<Ingredient> ingredients;
	int noOfElements;
	
	
	public Ingredients() {
		ingredients = new ArrayList<Ingredient>();
		
	}

	// Get ArrayList of ingredients
	public ArrayList<Ingredient> getIngredients() {
		
		return ingredients;
	}
	
	//Return the state of ingredients present
	
	
	// setters
	public void addIngredient(Ingredient thisIngredient) {
		ingredients.add(thisIngredient);
	}
	

	
}
