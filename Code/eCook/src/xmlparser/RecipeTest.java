/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Recipe Class
 */

package xmlparser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class RecipeTest {
	Recipe recipe;
	
	// check that setting attributes works correctly
	@Test
	public void canSetAndAddAttributes() {
		// instantiate a new recipe class
		recipe = new Recipe();
		
		// check that everything is null to start with
		assertNotNull(recipe.info);
		assertNotNull(recipe.defaults);
		assertNotNull(recipe.ingredients);
		assertNotNull(recipe.slides);
		assertNull(recipe.fileName);
		assertEquals(0, recipe.ingredients.size());
		assertEquals(0, recipe.slides.size());
		
		// set arbitrary values
		Info testInfo = new Info();
		recipe.setInfo(testInfo);
		Defaults testDefaults = new Defaults();
		recipe.setDefaults(testDefaults);
		Ingredient ingredient1 = new Ingredient();
		Ingredient ingredient2 = new Ingredient();
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(null);
		Slide slide1 = new Slide();
		Slide slide2 = new Slide();
		slide2.setLastSlide("true");
		Slide branchSlide = new Slide();
		recipe.addSlide(slide1);
		recipe.addSlide(null);
		recipe.addSlide(slide2);
		recipe.addSlide(branchSlide);
		recipe.setFileName("example filename");
		
		// check that all recipe fields are registered as complete
		assertTrue(recipe.lastSlideExists());
		
		// check that the fields have been set correctly
		assertEquals(testInfo, recipe.info);
		assertEquals(testDefaults, recipe.defaults);
		List<Ingredient> testIngredients = new ArrayList<Ingredient>();
		testIngredients.add(ingredient1);
		testIngredients.add(ingredient2);
		assertEquals(testIngredients, recipe.ingredients);
		List<Slide> testSlides = new ArrayList<Slide>();
		testSlides.add(slide1);
		testSlides.add(slide2);
		testSlides.add(branchSlide);
		assertEquals(testSlides, recipe.slides);
		assertEquals("example filename", recipe.fileName);
	}
	
	// check that getting attributes works correctly
	@Test
	public void canGetAttributes() {
		// instantiate a new recipe class
		recipe = new Recipe();

		// check that everything is null to start with
		assertNotNull(recipe.info);
		assertNotNull(recipe.defaults);
		assertNotNull(recipe.ingredients);
		assertNotNull(recipe.slides);
		assertNull(recipe.fileName);
		assertEquals(0, recipe.ingredients.size());
		assertEquals(0, recipe.slides.size());

		// set arbitrary values
		Info testInfo = new Info();
		recipe.setInfo(testInfo);
		Defaults testDefaults = new Defaults();
		recipe.setDefaults(testDefaults);
		Ingredient ingredient1 = new Ingredient();
		Ingredient ingredient2 = new Ingredient();
		recipe.addIngredient(ingredient1);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(null);
		Slide slide1 = new Slide();
		Slide slide2 = new Slide();
		slide2.setLastSlide("true");
		Slide branchSlide = new Slide();
		recipe.addSlide(slide1);
		recipe.addSlide(null);
		recipe.addSlide(slide2);
		recipe.addSlide(branchSlide);
		recipe.setFileName("example filename");

		// check that all recipe fields are registered as complete
		assertTrue(recipe.lastSlideExists());

		// check that the fields have been set correctly
		assertEquals(testInfo, recipe.getInfo());
		assertEquals(testDefaults, recipe.getDefaults());
		List<Ingredient> testIngredients = new ArrayList<Ingredient>();
		testIngredients.add(ingredient1);
		testIngredients.add(ingredient2);
		assertEquals(testIngredients, recipe.getIngredients());
		List<Slide> testSlides = new ArrayList<Slide>();
		testSlides.add(slide1);
		testSlides.add(slide2);
		testSlides.add(branchSlide);
		assertEquals(testSlides, recipe.getSlides());
		assertEquals("example filename", recipe.getFileName());
		
		// check the list operations
		assertEquals(slide1, recipe.getSlide(0));
		assertEquals(slide2, recipe.getSlide(1));
		
		assertEquals(3, recipe.getNumberOfSlidesIncBranchSlides());
		assertEquals(2, recipe.getNumberOfSlidesExcBranchSlides());
				
		assertEquals(ingredient1, recipe.getIngredient(0));
		assertEquals(ingredient2, recipe.getIngredient(1));
		assertEquals(null, recipe.getIngredient(9));
		
		assertEquals(2, recipe.getNumberOfIngredients());
	}
}