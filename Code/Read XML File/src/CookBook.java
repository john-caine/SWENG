import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CookBook implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<Recipe> recipes;
	private int bookLength;

	public CookBook(String name) {
		this.name = name;
		recipes = new ArrayList<Recipe>();
	}

	public String getName() {
		return name;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public Recipe getRecipe(int recipeNumber) {
		return recipes.get(recipeNumber);
	}

	public void addRecipe(Recipe recipe) {
		recipes.add(recipe);
	}

	public int getSize() {
		return recipes.size();
	}

	public String[] getTitles() {
		bookLength = recipes.size();
		String[] allTitles = new String[100]; // for now, max size of cookbook is 100

		for (int i = 0; i < bookLength; i++) {
			allTitles[i] = recipes.get(i).getTitle();
		}

		return allTitles;
	}
}