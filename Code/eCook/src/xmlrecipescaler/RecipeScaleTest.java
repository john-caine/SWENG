/*
 * Programmers: James, Prakruti, P. Mathema
 * Date Created: 22/05/14
 * Description: Test class for RecipeScale class
 */

package xmlrecipescaler;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import xmlparser.Recipe;
import xmlparser.XMLReader;

public class RecipeScaleTest {
	
	private XMLReader reader;
	private Recipe recipe;
	private RecipeScale recipeScale;
	private Integer testX;
	private Integer testY;
	private Integer testWidth;
	private Integer testHeight;
	private Integer testFontSize;
	
	/**
	 * Although these tests exist, in the short term slideshow scaling
	 * can be reviewed with a visual test. On PWS example playlist 3
	 * slide 3 there are two images. These images should never overlap
	 * and should be 10px from top left (image 1) and 10px from bottom
	 * right (image 2).
	 */
	@Before
	public void setUp() throws Exception {
		recipeScale = new RecipeScale();
		reader = new XMLReader("../Resources/PWSExamplePlaylist_4.xml");
	}
	
	/**
	 * On slide 3 of PWS playlist 3 there are two images
	 * Check that one of these images is not moved when
	 * screen resolution matches XML resolution
	 */
	@Test
	public void ignoreMatchingResolutions() {
		recipe = reader.getRecipe();
		recipeScale.setTest(1600, 900);
		
		// Get initial values
		testX = recipe.getSlide(3).getContent().getImages().get(0).getXStart();
		testY = recipe.getSlide(3).getContent().getImages().get(0).getYStart();
		testWidth = recipe.getSlide(3).getContent().getImages().get(0).getWidth();
		testHeight = recipe.getSlide(3).getContent().getImages().get(0).getHeight();
		
		// Scale
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Assertions
		assertEquals(testX, recipe.getSlide(3).getContent().getImages().get(0).getXStart());
		assertEquals(testY, recipe.getSlide(3).getContent().getImages().get(0).getYStart());
		assertEquals(testWidth, recipe.getSlide(3).getContent().getImages().get(0).getWidth());
		assertEquals(testHeight, recipe.getSlide(3).getContent().getImages().get(0).getHeight());
	}
	
	/**
	 * Test to see if the text values are scaled
	 */
	@Test
	public void checkTextScaling() {
		// Get fresh recipe and set scaling to made-up user resolution
		recipe = reader.getRecipe();
		recipeScale.setTest(3859, 1020);
		
		// Get initial values
		testX = recipe.getSlide(0).getContent().getTexts().get(0).getXStart();
		testY = recipe.getSlide(0).getContent().getTexts().get(0).getYStart();
		testFontSize = recipe.getSlide(0).getContent().getTexts().get(0).getFontSize();
		
		// Scale
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Assertions
		assertNotSame(testX, recipe.getSlide(0).getContent().getTexts().get(0).getXStart());
		assertNotSame(testY, recipe.getSlide(0).getContent().getTexts().get(0).getYStart());
		assertNotSame(testFontSize, recipe.getSlide(0).getContent().getTexts().get(0).getFontSize());
	}
	
	/**
	 * Test to see if the image values are scaled
	 */
	@Test
	public void checkImageScaling() {
		// Get fresh recipe and set scaling to made-up user resolution
		recipe = reader.getRecipe();
		recipeScale.setTest(3859, 1020);
		
		// Get initial values
		testX = recipe.getSlide(3).getContent().getImages().get(0).getXStart();
		testY = recipe.getSlide(3).getContent().getImages().get(0).getYStart();
		testWidth = recipe.getSlide(3).getContent().getImages().get(0).getWidth();
		testHeight = recipe.getSlide(3).getContent().getImages().get(0).getHeight();
		
		// Scale
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Assertions
		assertNotSame(testX, recipe.getSlide(3).getContent().getImages().get(0).getXStart());
		assertNotSame(testY, recipe.getSlide(3).getContent().getImages().get(0).getYStart());
		assertNotSame(testWidth, recipe.getSlide(3).getContent().getImages().get(0).getWidth());
		assertNotSame(testHeight, recipe.getSlide(3).getContent().getImages().get(0).getHeight());	
	}
	
	/**
	 * Test to see if the shape values are scaled
	 */
	@Test
	public void checkShapeScaling() {
		// Get fresh recipe and set scaling to made-up user resolution
		recipe = reader.getRecipe();
		recipeScale.setTest(3859, 1020);
		
		// Get initial values
		testWidth = recipe.getSlide(1).getContent().getShapes().get(1).getWidth();
		testHeight = recipe.getSlide(1).getContent().getShapes().get(1).getHeight();
		
		// Scale
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Assertions
		assertNotSame(testWidth, recipe.getSlide(1).getContent().getShapes().get(1).getWidth());
		assertNotSame(testHeight, recipe.getSlide(1).getContent().getShapes().get(1).getHeight());
	}
	
	/**
	 * Test to see if the video values are scaled
	 */
	@Test
	public void checkVideoScaling() {
		// Get fresh recipe and set scaling to made-up user resolution
		recipe = reader.getRecipe();
		recipeScale.setTest(3859, 1020);
		
		// Get initial values
		testX = recipe.getSlide(4).getContent().getVideos().get(1).getXStart();
		testY = recipe.getSlide(4).getContent().getVideos().get(1).getYStart();
		testWidth = recipe.getSlide(4).getContent().getVideos().get(1).getWidth();
		testHeight = recipe.getSlide(4).getContent().getVideos().get(1).getHeight();
		
		// Scale
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Assertions
		assertNotSame(testX, recipe.getSlide(4).getContent().getVideos().get(1).getXStart());
		assertNotSame(testY, recipe.getSlide(4).getContent().getVideos().get(1).getYStart());
		assertNotSame(testWidth, recipe.getSlide(4).getContent().getVideos().get(1).getWidth());
		assertNotSame(testHeight, recipe.getSlide(4).getContent().getVideos().get(1).getHeight());	
	}
	
	/**
	 *	Finally do an in-depth test for absolute values.
	 *	Use hand calculated expected values for scaling
	 *	and compare them to the output from the recipe scaler.
	 */
	@Test
	public void absoluteValueTest() {
		Integer actualX, actualY, actualWidth, actualHeight;
		
		// Return the current recipe
		recipe = reader.getRecipe();
		
		// Set a test resolution of 1440*900
		recipeScale.setTest(1440, 900);
		
		// run the scaler
		recipe = recipeScale.scaleRecipe(recipe);
		
		// Get values from objects
		testX = recipe.getSlide(3).getContent().getImages().get(0).getXStart();
		testY = recipe.getSlide(3).getContent().getImages().get(0).getYStart();
		testWidth = recipe.getSlide(3).getContent().getImages().get(0).getWidth();
		testHeight = recipe.getSlide(3).getContent().getImages().get(0).getHeight();
		
		// Manually calculated values scaled accordingly
		actualX =  9;
		actualY =  54;
		actualWidth = 460;
		actualHeight = 345;
		
		// Assertions - do object values match manually calculated ones?
		assertEquals(testX, actualX, 0);
		assertEquals(testY, actualY, 0);
		assertEquals(testWidth, actualWidth, 0);
		assertEquals(testHeight, actualHeight, 0);	
	}
}
	