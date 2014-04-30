package xmlparser;
/* Title: XMLReaderTest
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: Test class for XMLReader. Test specifics are supplied in comments below.
 * 
 * Version History: v1.1 (27/03/14) - Modified to test updated PWS standard documents (v0.9).
 * 					v1.2 (01/04/14) - Removed CookBook so all references are now directed at Recipe Instance
 * 					v1.3 (06/04/14) - Updated test 'secondSlideReadCorrectly' to see Point class.
 * 					v1.4 (10/04/14) - Updated testing to facilitate changes from Integer to int across subclasses.
 * 					v1.5 (28/04/14) - Updated tests after eCook integration to read from 'PWSExamplePlaylist_2.xml'
 */

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class XMLReaderTest {
	private XMLReader reader;
	private Recipe recipe;
	private Ingredients ingredients;
	private ArrayList<Ingredient> ingredientsList;

	// create instances of the XML reader and recipe
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("../Resources/Recipe_Example.xml");
		recipe = reader.getRecipe();
	}
	
	// test that a recipe has been created
	@Test
	public void createRecipe() {
		assertTrue(recipe instanceof Recipe);
	}

	// check that the recipe contains information in all fields
	@Test
	public void recipeReturnsCorrectFields() {
		assertNotNull(recipe.info);
		assertNotNull(recipe.defaults);
		assertNotNull(recipe.slides);
	}

	// confirm that the correct information and default data is contained in the recipe
	@Test
	public void correctInfoDefaultDataReadIntoRecipe() {
		// check info
		assertEquals("John Caine", recipe.info.author);
		assertEquals("1.1", recipe.info.version);
		assertEquals("Test Playlist", recipe.info.title);
		assertEquals("Test playlist for eCook product", recipe.info.comment);
		assertEquals(1000, recipe.info.width.intValue());
		assertEquals(800, recipe.info.height.intValue());
		
		// check defaults
		assertEquals("#00FF00", recipe.defaults.backgroundColor);
		assertEquals("#FF0000", recipe.defaults.fillColor);
		assertEquals("Times New Roman", recipe.defaults.font);
		assertEquals("#0000FF", recipe.defaults.fontColor);
		assertEquals(24, recipe.defaults.fontSize.intValue());
	}

	// confirm that all fields in the first slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void firstSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 1 ID: " + recipe.slides.get(0).ID);
		System.out.println("Slide 1 Duration: " + recipe.slides.get(0).duration);
		System.out.println("Is Slide 1 the last slide? " + recipe.slides.get(0).lastSlide);
		
		// loop through the four text objects, printing out the contents
		for (int i=0; i<recipe.slides.get(0).content.getNumberOfTexts(); i++) {
			TextBody text = recipe.slides.get(0).content.texts.get(i);
			System.out.println("\nSlide 1: Text Object "+ (i+1));
			System.out.println("\tXStart: " + text.getXStart());
			System.out.println("\tXEnd: " + text.getXEnd());
			System.out.println("\tYStart: " + text.getYStart());
			System.out.println("\tYEnd: " + text.getYEnd());
			System.out.println("\tFont: " + text.getFont());
			System.out.println("\tFont Colour: " + text.getFontColor());
			System.out.println("\tFont Size: " + text.getFontSize());
			System.out.println("\tStart Time: " + text.getStartTime());
			System.out.println("\tDuration: " + text.getDuration());
			System.out.println("\tLayer: " + text.getLayer());
			for (int j=0; j<recipe.slides.get(0).content.getText(i).getNumberOfTextStrings(); j++) {
				if (text.getTextString(j).getBold()) {
					System.out.println("\tText String " + (j+1) + ": " + text.getTextString(j).getText() + " (bold)");
				}
				else if (text.getTextString(j).getItalic()) {
					System.out.println("\tText String " + (j+1) + ": " + text.getTextString(j).getText() + " (italic)");
				}
				else if (text.getTextString(j).getUnderline()) {
					System.out.println("\tText String " + (j+1) + ": " + text.getTextString(j).getText() + " (underline)");
				}
				else {
					System.out.println("\tText String " + (j+1) + ": " + text.getTextString(j).getText());
				}
			}
		}
		
		// check slide 1
		assertEquals(0, recipe.slides.get(0).ID);
		assertEquals(60, recipe.slides.get(0).duration.intValue());
		assertFalse(recipe.slides.get(0).lastSlide);
			// text 1
			assertEquals(10, recipe.slides.get(0).content.texts.get(0).getXStart());
			assertEquals(10, recipe.slides.get(0).content.texts.get(0).getYStart());
			assertEquals(1000, recipe.slides.get(0).content.texts.get(0).getXEnd());
			assertEquals(100, recipe.slides.get(0).content.texts.get(0).getYEnd());
			assertEquals("This is a test of required attributes", recipe.slides.get(0).content.texts.get(0).getTextString(0).getText());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getBold());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getItalic());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getUnderline());
			// text 2
			assertEquals(10, recipe.slides.get(0).content.texts.get(1).getXStart());
			assertEquals(100, recipe.slides.get(0).content.texts.get(1).getYStart());
			assertEquals(1000, recipe.slides.get(0).content.texts.get(1).getXEnd());
			assertEquals(300, recipe.slides.get(0).content.texts.get(1).getYEnd());
			assertEquals("Times New Roman", recipe.slides.get(0).content.texts.get(1).getFont());
			assertEquals(16, recipe.slides.get(0).content.texts.get(1).getFontSize().intValue());
			assertEquals("#00FFFF", recipe.slides.get(0).content.texts.get(1).getFontColor());
			assertEquals("This is a test of optional font attributes", recipe.slides.get(0).content.texts.get(1).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getUnderline());
			// text 3
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getXStart());
			assertEquals(200, recipe.slides.get(0).content.texts.get(2).getYStart());
			assertEquals(1000, recipe.slides.get(0).content.texts.get(2).getXEnd());
			assertEquals(400, recipe.slides.get(0).content.texts.get(2).getYEnd());
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getStartTime().intValue());
			assertEquals(40, recipe.slides.get(0).content.texts.get(2).getDuration().intValue());
			assertEquals(2, recipe.slides.get(0).content.texts.get(2).getLayer().intValue());
			assertEquals("This is a test of timing attributes", recipe.slides.get(0).content.texts.get(2).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getUnderline());
			// text 4
			assertEquals(10, recipe.slides.get(0).content.texts.get(3).getXStart());
			assertEquals(300, recipe.slides.get(0).content.texts.get(3).getYStart());
			assertEquals(500, recipe.slides.get(0).content.texts.get(3).getXEnd());
			assertEquals(330, recipe.slides.get(0).content.texts.get(3).getYEnd());
			// text 4 textStrings
			assertEquals("This text should be bold. ", recipe.slides.get(0).content.texts.get(3).getTextString(0).getText());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(0).getUnderline());
			assertEquals("This text should be italic. ", recipe.slides.get(0).content.texts.get(3).getTextString(1).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(1).getBold());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(1).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(1).getUnderline());
			assertEquals("This text should be underlined. ", recipe.slides.get(0).content.texts.get(3).getTextString(2).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(2).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(2).getItalic());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(2).getUnderline());
	}
	
	// confirm that all fields in the second slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void secondSlideReadCorrectly() {		
		// print slide information
		System.out.println("Slide 2 ID: " + recipe.slides.get(1).ID);
		System.out.println("Slide 2 Duration: " + recipe.slides.get(1).duration);
		System.out.println("Is Slide 2 the last slide? " + recipe.slides.get(1).lastSlide);
		
		// loop through the three shape objects, printing out the contents
		for (int i=0; i<recipe.slides.get(1).content.getNumberOfShapes(); i++) {
			Shape shape = recipe.slides.get(1).content.shapes.get(i);
			System.out.println("\nSlide 2: Shape Object "+ (i+1));
			System.out.println("\ttotal points: " + shape.getTotalPoints());
			System.out.println("\twidth: " + shape.getWidth());
			System.out.println("\tHeight: " + shape.getHeight());
			System.out.println("\tFill color: " + shape.getFillColor());
			System.out.println("\tLine color: " + shape.getLineColor());
			System.out.println("\tStart time: " + shape.getStartTime());
			System.out.println("\tDuration: " + shape.getDuration());
			System.out.println("\tLayer: " + shape.getLayer());
			
			for (int j=0; j<recipe.slides.get(1).content.getShape(i).getTotalPoints(); j++) {
				Point point = shape.getPoint(j);
				System.out.println("\tShape point " + (j+1) + " num: " + point.getNum());
				System.out.println("\tShape point " + (j+1) + " x: " + point.getX());
				System.out.println("\tShape point " + (j+1) + " y: " + point.getY());
			}
		}
		
		// check slide 2
		assertEquals(1, recipe.slides.get(1).ID);
		assertEquals(30, recipe.slides.get(1).duration.intValue());
		assertFalse(recipe.slides.get(1).lastSlide);
			// shape 1
			assertEquals(4, recipe.slides.get(1).content.shapes.get(0).getTotalPoints());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(0).getWidth().intValue());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(0).getHeight().intValue());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getNum().intValue());
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getX().intValue());
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getY().intValue());
				// point 2
				assertEquals(2, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getNum().intValue());
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getX().intValue());
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getY().intValue());
				// point 3
				assertEquals(3, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getNum().intValue());
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getX().intValue());
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getY().intValue());
				// point 4
				assertEquals(4, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getNum().intValue());
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getX().intValue());
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getY().intValue());
			// shape 2
			assertEquals(1, recipe.slides.get(1).content.shapes.get(1).getTotalPoints());
			assertEquals(10, recipe.slides.get(1).content.shapes.get(1).getWidth().intValue());
			assertEquals(10, recipe.slides.get(1).content.shapes.get(1).getHeight().intValue());
			assertEquals("", recipe.slides.get(1).content.shapes.get(1).getFillColor());
			assertEquals("", recipe.slides.get(1).content.shapes.get(1).getLineColor());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getNum().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getX().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getY().intValue());
			// shape 3
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getTotalPoints());
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getWidth().intValue());
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getHeight().intValue());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(2).getStartTime().intValue());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(2).getDuration().intValue());
			assertEquals(1, recipe.slides.get(1).content.shapes.get(2).getLayer().intValue());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(2).getPoint(0).getNum().intValue());
				assertEquals(150, recipe.slides.get(1).content.shapes.get(2).getPoint(0).getX().intValue());
				assertEquals(150, recipe.slides.get(1).content.shapes.get(2).getPoint(0).getY().intValue());
				// point 2
				assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getPoint(1).getNum().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(2).getPoint(1).getX().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(2).getPoint(1).getY().intValue());
	}
	
	// confirm that all fields in the third slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void thirdSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 3 ID: " + recipe.slides.get(2).ID);
		System.out.println("Slide 3 Duration: " + recipe.slides.get(2).duration);
		System.out.println("Is Slide 3 the last slide? " + recipe.slides.get(2).lastSlide);
		
		// loop through the two audio objects, printing out the contents
		for (int i=0; i<recipe.slides.get(2).content.getNumberOfAudios(); i++) {
			Audio audio = recipe.slides.get(2).content.audios.get(i);
			System.out.println("\nSlide 3: Audio Object "+ (i+1));
			System.out.println("\tURL name: " + audio.getUrlName());
			System.out.println("\tStart time: " + audio.getStartTime());
			System.out.println("\tLoop: " + audio.getLoop());
		}
		
		// check slide 3
		assertEquals(2, recipe.slides.get(2).ID);
		assertEquals(90, recipe.slides.get(2).duration.intValue());
		assertFalse(recipe.slides.get(2).lastSlide);
			// audio 1
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", recipe.slides.get(2).content.audios.get(0).getUrlName());
			assertEquals(5, recipe.slides.get(2).content.audios.get(0).getStartTime().intValue());
			assertEquals(20, recipe.getSlides().get(2).content.audios.get(0).getDuration().intValue());
			assertFalse(recipe.slides.get(2).content.audios.get(0).getLoop());
			// audio 2
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample2.mp3", recipe.slides.get(2).content.audios.get(1).getUrlName());
			assertEquals(35, recipe.slides.get(2).content.audios.get(1).getStartTime().intValue());
			assertFalse(recipe.slides.get(2).content.audios.get(1).getLoop());
	}
	
	
	// confirm that all fields in the fourth slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void fourthSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 4 ID: " + recipe.slides.get(3).ID);
		System.out.println("Slide 4 Duration: " + recipe.slides.get(3).duration);
		System.out.println("Is Slide 4 the last slide? " + recipe.slides.get(3).lastSlide);
		
		// loop through the three video objects, printing out the contents
		for (int i=0; i<recipe.slides.get(3).content.getNumberOfImages(); i++) {
			Image image = recipe.slides.get(3).content.images.get(i);
			System.out.println("\nSlide 4: Image Object "+ (i+1));
			System.out.println("\tURL name: " + image.getUrlName());
			System.out.println("\tXStart: " + image.getXStart());
			System.out.println("\tYStart: " + image.getYStart());
			System.out.println("\tWidth: " + image.getWidth());
			System.out.println("\tHeight: " + image.getHeight());
			System.out.println("\tLayer: " + image.getLoop());
			System.out.println("\tDuration: " + image.getDuration());
		}
		
		// check slide 4
		assertEquals(3, recipe.slides.get(3).ID);
		assertEquals(40, recipe.slides.get(3).duration.intValue());
		assertFalse(recipe.slides.get(3).lastSlide);
			// image 1
			assertEquals("http://4.bp.blogspot.com/-D6YYIZDSxks/UXvED6as6cI/AAAAAAAAAHs/ddOuPj9Yh7I/s1600/waffle+and+syrup.jpg", recipe.slides.get(3).content.images.get(0).getUrlName());
			assertEquals(10, recipe.slides.get(3).content.images.get(0).getXStart());
			assertEquals(10, recipe.slides.get(3).content.images.get(0).getYStart());
			assertEquals(0, recipe.slides.get(3).content.images.get(0).getLayer().intValue());
			// image 2
			assertEquals("http://www.foodfunhealth.com/wp-content/uploads/2010/11/By-Aylanah.jpg", recipe.slides.get(3).content.images.get(1).getUrlName());
			assertEquals(400, recipe.slides.get(3).content.images.get(1).getXStart());
			assertEquals(400, recipe.slides.get(3).content.images.get(1).getYStart());
			assertEquals(200, recipe.slides.get(3).content.images.get(1).getWidth().intValue());
			assertEquals(200, recipe.slides.get(3).content.images.get(1).getHeight().intValue());
			assertEquals(2, recipe.slides.get(3).content.images.get(1).getLayer().intValue());
			// image 3
			assertEquals("http://2.bp.blogspot.com/-UwQy6WiradM/T9igHkNTakI/AAAAAAAARfA/2lj7tz2L-So/s1600/P5294569.jpg", recipe.slides.get(3).content.images.get(2).getUrlName());
			assertEquals(430, recipe.slides.get(3).content.images.get(2).getXStart());
			assertEquals(430, recipe.slides.get(3).content.images.get(2).getYStart());
			assertEquals(20, recipe.slides.get(3).content.images.get(2).getDuration().intValue());
			assertEquals(100, recipe.slides.get(3).content.images.get(2).getWidth().intValue());
			assertEquals(100, recipe.slides.get(3).content.images.get(2).getHeight().intValue());
			assertEquals(3, recipe.slides.get(3).content.images.get(2).getLayer().intValue());
	}
	
	// confirm that all fields in the last slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void lastSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 5 ID: " + recipe.slides.get(recipe.getNumberOfSlides()-1).ID);
		System.out.println("Slide 5 Duration: " + recipe.slides.get(recipe.getNumberOfSlides()-1).duration);
		System.out.println("Is Slide 5 the last slide? " + recipe.slides.get(recipe.getNumberOfSlides()-1).lastSlide);
		
		// loop through the two video objects, printing out the contents
		for (int i=0; i<recipe.slides.get(recipe.getNumberOfSlides()-1).content.getNumberOfVideos(); i++) {
			Video video = recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(i);
			System.out.println("\nSlide 5: Video Object "+ (i+1));
			System.out.println("\tURL name: " + video.getUrlName());
			System.out.println("\tXStart: " + video.getXStart());
			System.out.println("\tYStart: " + video.getYStart());
			System.out.println("\tLayer: " + video.getLayer());
			System.out.println("\tLoop: " + video.getLoop());
			System.out.println("\tStart Time: " + video.getStartTime());
			System.out.println("\tDuration: " + video.getDuration());
		}
		
		// check slide 4
		assertEquals(4, recipe.slides.get(recipe.getNumberOfSlides()-1).ID);
		assertEquals(200, recipe.slides.get(recipe.getNumberOfSlides()-1).duration.intValue());
		assertFalse(recipe.slides.get(recipe.getNumberOfSlides()-1).lastSlide);
			// video 1
			assertEquals("http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4", recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getUrlName());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getXStart());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getYStart());
			assertEquals(0, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getLayer().intValue());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getDuration().intValue());
			// video 2
			assertEquals("http://ystv.co.uk/download/2064/Union%20-%20Episode%202.mp4", recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getUrlName());
			assertEquals(300, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getXStart());
			assertEquals(300, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getYStart());
			assertTrue(recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getLoop());
			assertEquals(15, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getStartTime().intValue());
	}
	/*
	 * A test to check that the ingredients are read in correctly from the .xml file
	 * James and Sam
	 */
	@Test
	public void ingredientsTest() {
		ingredients = reader.getIngredients();
		ingredientsList = ingredients.getIngredients();
		// 6 ingredients in total
		// Ingredient
		assertEquals("onion", ingredientsList.get(0).getName());
		assertEquals(700, ingredientsList.get(0).getAmount(), 0.01);
		assertEquals("grams", ingredientsList.get(0).getUnits());
		// Ingredient
		assertEquals("olive oil", ingredientsList.get(1).getName());
		assertEquals(2, ingredientsList.get(1).getAmount(), 0.01);
		assertEquals("tablespoons", ingredientsList.get(1).getUnits());
		// Ingredient
		assertEquals("butter", ingredientsList.get(2).getName());
		assertEquals(2, ingredientsList.get(2).getAmount(), 0.01);
		assertEquals("oz", ingredientsList.get(2).getUnits());
		// Ingredient
		assertEquals("garlic", ingredientsList.get(3).getName());
		assertEquals(2, ingredientsList.get(3).getAmount(), 0.01);
		assertEquals("cloves", ingredientsList.get(3).getUnits());
		// Ingredient
		assertEquals("granulated sugar", ingredientsList.get(4).getName());
		assertEquals(0.5, ingredientsList.get(4).getAmount(), 0.01);
		assertEquals("teaspoon", ingredientsList.get(4).getUnits());
		// Ingredient
		assertEquals("beef stock", ingredientsList.get(5).getName());
		assertEquals(2, ingredientsList.get(5).getAmount(), 0.01);
		assertEquals("pints", ingredientsList.get(5).getUnits());
	}
}
