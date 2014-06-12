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
	
	protected RecipeCollection recipeCollection;
	
	/**
	 *  Check that the recipe list is instantiated with the constructor.
	 */
	@Test
	public void recipeListIsCreated() {
		// Make a new collection
		recipeCollection = new RecipeCollection();
		
		// Assert the collection is not null, but has nothing in it.
		assertNotNull(recipeCollection.recipes);
		assertEquals(0, recipeCollection.recipes.size());
	}
	
	/**
	 *  Test the add function to check recipes are added to the right index.
	 */
	@Test
	public void recipeCanBeAddedToList() {
		// Create a new collection and add a test recipe
		recipeCollection = new RecipeCollection();
		Recipe testRecipe = new Recipe();
		testRecipe.setFileName("example recipe filename");
		recipeCollection.addRecipe(testRecipe);
		
		// Assert the recipe is in the collection and has the right file name.
		assertEquals(1, recipeCollection.recipes.size());
		assertEquals(testRecipe, recipeCollection.getRecipe(0));
	}
	
	/**
	 *  Test the get function to ensure the expected recipe is returned by passing the index.
	 */
	@Test
	public void recipeCanBeRemovedFromList() {
		// Create a new collection and add a test recipe
		recipeCollection = new RecipeCollection();
		Recipe testRecipe = new Recipe();
		testRecipe.setFileName("example recipe filename");
		
		// Assert the collection is still empty
		assertEquals(0, recipeCollection.getNumberOfRecipes());
		
		// Add some valid and invalid recipes to the collection
		recipeCollection.addRecipe(null);
		recipeCollection.addRecipe(testRecipe);
		recipeCollection.addRecipe(null);
		
		// Assert only the valid receipe is on the collection
		assertEquals(1, recipeCollection.getNumberOfRecipes());
		assertEquals("example recipe filename", recipeCollection.getRecipe(0).getFileName());
	}

}
