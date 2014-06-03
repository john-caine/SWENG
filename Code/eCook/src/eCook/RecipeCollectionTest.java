/*
 * Programmer: Max
 * Date Created: 03/06/14
 * Description: Unit tests for RecipeCollection class.
 */
package eCook;

import static org.junit.Assert.*;

import org.junit.Test;

import xmlparser.Recipe;

public class RecipeCollectionTest {
	RecipeCollection recipeCollection;
	
	// check that the recipe list is instantiated with the constructor
	@Test
	public void recipeListIsCreated() {
		recipeCollection = new RecipeCollection();
		assertNotNull(recipeCollection.recipes);
		assertEquals(0, recipeCollection.recipes.size());
	}
	
	// test the add function
	@Test
	public void recipeCanBeAddedToList() {
		recipeCollection = new RecipeCollection();
		Recipe testRecipe = new Recipe();
		testRecipe.setFileName("example recipe filename");
		
		recipeCollection.addRecipe(testRecipe);
		assertEquals(1, recipeCollection.recipes.size());
		assertEquals(testRecipe, recipeCollection.getRecipe(0));
	}
	
	// test the get function
	@Test
	public void recipeCanBeRemovedFromList() {
		recipeCollection = new RecipeCollection();
		Recipe testRecipe = new Recipe();
		testRecipe.setFileName("example recipe filename");
		
		assertEquals(0, recipeCollection.getNumberOfRecipes());
		
		recipeCollection.addRecipe(null);
		recipeCollection.addRecipe(testRecipe);
		recipeCollection.addRecipe(null);
		
		assertEquals(1, recipeCollection.getNumberOfRecipes());
		
		assertEquals("example recipe filename", recipeCollection.getRecipe(0).getFileName());
	}

}
