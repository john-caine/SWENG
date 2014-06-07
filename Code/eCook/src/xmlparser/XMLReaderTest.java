package xmlparser;
/* Title: XMLReaderTest
 * 
 * Programmers: Ankita, Max, James, Sam
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
 * 					v1.6 (30/04/14) - Updated tests to account for new ingredients field within .xml
 */

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XMLReaderTest {
	private XMLReader reader;
	private Recipe recipe;

	// create instances of the XML reader and recipe
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_4.xml");
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
		assertNotNull(recipe.ingredients);
		assertNotNull(recipe.slides);
	}

	// confirm that the correct information and default data is contained in the recipe
	@Test
	public void correctInfoDefaultDataReadIntoRecipe() {
		// check info
		assertEquals("John Caine", recipe.info.author);
		assertEquals("1.1", recipe.info.version);
		assertEquals("Recipe Example #2", recipe.info.title);
		assertEquals("This is recipe example 2 in the defaultRecipe folder", recipe.info.comment);
		assertEquals("example cooking time", recipe.info.cook);
		assertEquals("example preperation time", recipe.info.prep);
		assertEquals("example £5", recipe.info.price);
		assertEquals("example vegetarian comment", recipe.info.veg);
		assertEquals(1600, recipe.info.width.intValue());
		assertEquals(900, recipe.info.height.intValue());
		
		// check defaults
		assertEquals("#00FF00", recipe.defaults.backgroundColor);
		assertEquals("#FF0000", recipe.defaults.fillColor);
		assertEquals("Times New Roman", recipe.defaults.font);
		assertEquals("#0000FF", recipe.defaults.fontColor);
		assertEquals("#000000", recipe.defaults.lineColor);
		assertEquals(24, recipe.defaults.fontSize.intValue());
	}
	
	/*
	 * A test to check that the ingredients are read in correctly from the .xml file
	 * James and Sam
	 */
	@Test
	public void ingredientsTest() {
		List<Ingredient> ingredientsList = recipe.getIngredients();
		// 6 ingredients in total
		// Ingredient
		assertEquals("potato", ingredientsList.get(0).getName());
		assertEquals(700, ingredientsList.get(0).getAmount(), 0.01);
		assertEquals("grams", ingredientsList.get(0).getUnits());
		// Ingredient
		assertEquals("vegetable oil", ingredientsList.get(1).getName());
		assertEquals(2, ingredientsList.get(1).getAmount(), 0.01);
		assertEquals("tablespoons", ingredientsList.get(1).getUnits());
		// Ingredient
		assertEquals("unsalted butter", ingredientsList.get(2).getName());
		assertEquals(2, ingredientsList.get(2).getAmount(), 0.01);
		assertEquals("oz", ingredientsList.get(2).getUnits());
		// Ingredient
		assertEquals("garlic", ingredientsList.get(3).getName());
		assertEquals(2, ingredientsList.get(3).getAmount(), 0.01);
		assertEquals("cloves", ingredientsList.get(3).getUnits());
		// Ingredient
		assertEquals("brown sugar", ingredientsList.get(4).getName());
		assertEquals(0.5, ingredientsList.get(4).getAmount(), 0.01);
		assertEquals("teaspoon", ingredientsList.get(4).getUnits());
		// Ingredient
		assertEquals("chicken stock", ingredientsList.get(5).getName());
		assertEquals(2, ingredientsList.get(5).getAmount(), 0.01);
		assertEquals("pints", ingredientsList.get(5).getUnits());
	}
	
	// confirm that all fields in the first slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void firstSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 1 id: " + recipe.slides.get(0).id);
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
		assertEquals((Integer)0, recipe.slides.get(0).id);
		assertEquals(30, recipe.slides.get(0).duration.intValue());
		assertFalse(recipe.slides.get(0).lastSlide);
			// text 1
			assertEquals((Integer)10, recipe.slides.get(0).content.texts.get(0).getXStart());
			assertEquals((Integer)10, recipe.slides.get(0).content.texts.get(0).getYStart());
			assertEquals((Integer)1000, recipe.slides.get(0).content.texts.get(0).getXEnd());
			assertEquals((Integer)100, recipe.slides.get(0).content.texts.get(0).getYEnd());
			assertEquals("This is a test of required attributes", recipe.slides.get(0).content.texts.get(0).getTextString(0).getText());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getBold());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getItalic());
			assertTrue(recipe.slides.get(0).content.texts.get(0).getTextString(0).getUnderline());
			// text 2
			assertEquals((Integer)10, recipe.slides.get(0).content.texts.get(1).getXStart());
			assertEquals((Integer)100, recipe.slides.get(0).content.texts.get(1).getYStart());
			assertEquals((Integer)1000, recipe.slides.get(0).content.texts.get(1).getXEnd());
			assertEquals((Integer)300, recipe.slides.get(0).content.texts.get(1).getYEnd());
			assertEquals("Times New Roman", recipe.slides.get(0).content.texts.get(1).getFont());
			assertEquals(16, recipe.slides.get(0).content.texts.get(1).getFontSize().intValue());
			assertEquals("#00FFFF", recipe.slides.get(0).content.texts.get(1).getFontColor());
			assertEquals("This is a test of optional font attributes", recipe.slides.get(0).content.texts.get(1).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getUnderline());
			System.out.println("\n ***" + recipe.slides.get(0).content.texts.get(1).getTextBody().get(0).getBranch());
			// text 3
			assertEquals((Integer)10, recipe.slides.get(0).content.texts.get(2).getXStart());
			assertEquals((Integer)200, recipe.slides.get(0).content.texts.get(2).getYStart());
			assertEquals((Integer)1000, recipe.slides.get(0).content.texts.get(2).getXEnd());
			assertEquals((Integer)400, recipe.slides.get(0).content.texts.get(2).getYEnd());
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getStartTime().intValue());
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getDuration().intValue());
			assertEquals(2, recipe.slides.get(0).content.texts.get(2).getLayer().intValue());
			assertEquals("This is a test of timing attributes", recipe.slides.get(0).content.texts.get(2).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getUnderline());
			// text 4
			assertEquals((Integer)10, recipe.slides.get(0).content.texts.get(3).getXStart());
			assertEquals((Integer)300, recipe.slides.get(0).content.texts.get(3).getYStart());
			assertEquals((Integer)500, recipe.slides.get(0).content.texts.get(3).getXEnd());
			assertEquals((Integer)330, recipe.slides.get(0).content.texts.get(3).getYEnd());
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
		System.out.println("Slide 2 id: " + recipe.slides.get(1).id);
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
		assertEquals((Integer)1, recipe.slides.get(1).id);
		assertEquals(30, recipe.slides.get(1).duration.intValue());
		assertFalse(recipe.slides.get(1).lastSlide);
			// shape 1
			assertEquals((Integer)4, recipe.slides.get(1).content.shapes.get(0).getTotalPoints());
			assertEquals(150, recipe.slides.get(1).content.shapes.get(0).getWidth().intValue());
			assertEquals(150, recipe.slides.get(1).content.shapes.get(0).getHeight().intValue());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getNum().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getX().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(0).getPoint(0).getY().intValue());
				// point 2
				assertEquals(2, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getNum().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getX().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(0).getPoint(1).getY().intValue());
				// point 3
				assertEquals(3, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getNum().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getX().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(0).getPoint(2).getY().intValue());
				// point 4
				assertEquals(4, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getNum().intValue());
				assertEquals(100, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getX().intValue());
				assertEquals(250, recipe.slides.get(1).content.shapes.get(0).getPoint(3).getY().intValue());
			// shape 2
			assertEquals((Integer)1, recipe.slides.get(1).content.shapes.get(1).getTotalPoints());
			assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getWidth().intValue());
			assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getHeight().intValue());
			assertEquals("#9400D3", recipe.slides.get(1).content.shapes.get(1).getFillColor());
			assertEquals("#000000", recipe.slides.get(1).content.shapes.get(1).getLineColor());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getNum().intValue());
				assertEquals(500, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getX().intValue());
				assertEquals(200, recipe.slides.get(1).content.shapes.get(1).getPoint(0).getY().intValue());
			// shape 3
			assertEquals((Integer)2, recipe.slides.get(1).content.shapes.get(2).getTotalPoints());
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
		System.out.println("Slide 3 id: " + recipe.slides.get(2).id);
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
		assertEquals((Integer)2, recipe.slides.get(2).id);
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
		System.out.println("Slide 4 id: " + recipe.slides.get(3).id);
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
		assertEquals((Integer)3, recipe.slides.get(3).id);
		assertEquals(40, recipe.slides.get(3).duration.intValue());
		assertFalse(recipe.slides.get(3).lastSlide);
			// image 1
			assertEquals("http://4.bp.blogspot.com/-D6YYIZDSxks/UXvED6as6cI/AAAAAAAAAHs/ddOuPj9Yh7I/s1600/waffle+and+syrup.jpg", recipe.slides.get(3).content.images.get(0).getUrlName());
			assertEquals((Integer)10, recipe.slides.get(3).content.images.get(0).getXStart());
			assertEquals((Integer)10, recipe.slides.get(3).content.images.get(0).getYStart());
			// image 2
			assertEquals("http://2.bp.blogspot.com/-UwQy6WiradM/T9igHkNTakI/AAAAAAAARfA/2lj7tz2L-So/s1600/P5294569.jpg", recipe.slides.get(3).content.images.get(1).getUrlName());
			assertEquals((Integer)1194, recipe.slides.get(3).content.images.get(1).getXStart());
			assertEquals((Integer)600, recipe.slides.get(3).content.images.get(1).getYStart());
			assertEquals(396, recipe.slides.get(3).content.images.get(1).getWidth().intValue());
			assertEquals(300, recipe.slides.get(3).content.images.get(1).getHeight().intValue());
			assertEquals(3, recipe.slides.get(3).content.images.get(1).getLayer().intValue());
	}
	
	// confirm that all fields in the fifth slide of the recipe contain the correct data
	// print out values of fields for this slide
	@Test
	public void fifthSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 5 id: " + recipe.slides.get(4).id);
		System.out.println("Slide 5 Duration: " + recipe.slides.get(4).duration);
		System.out.println("Is Slide 5 the last slide? " + recipe.slides.get(4).lastSlide);
		
		// loop through the two video objects, printing out the contents
		for (int i=0; i<recipe.slides.get(4).content.getNumberOfVideos(); i++) {
			Video video = recipe.slides.get(4).content.videos.get(i);
			System.out.println("\nSlide 5: Video Object "+ (i+1));
			System.out.println("\tURL name: " + video.getUrlName());
			System.out.println("\tXStart: " + video.getXStart());
			System.out.println("\tYStart: " + video.getYStart());
			System.out.println("\tLayer: " + video.getLayer());
			System.out.println("\tLoop: " + video.getLoop());
			System.out.println("\tStart Time: " + video.getStartTime());
			System.out.println("\tDuration: " + video.getDuration());
		}
		
		// check slide 5
		assertEquals((Integer)4, recipe.slides.get(4).id);
		assertEquals(90, recipe.slides.get(4).duration.intValue());
			// video 1
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/14_Fusion_Promo_spr08.mp4", recipe.slides.get(4).content.videos.get(0).getUrlName());
			assertEquals((Integer)10, recipe.slides.get(4).content.videos.get(0).getXStart());
			assertEquals((Integer)10, recipe.slides.get(4).content.videos.get(0).getYStart());
			assertEquals(2, recipe.slides.get(4).content.videos.get(0).getLayer().intValue());
			assertEquals(10, recipe.slides.get(4).content.videos.get(0).getDuration().intValue());
			// video 2
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/ident_ice-XviD.avi", recipe.slides.get(4).content.videos.get(1).getUrlName());
			assertEquals((Integer)300, recipe.slides.get(4).content.videos.get(1).getXStart());
			assertEquals((Integer)300, recipe.slides.get(4).content.videos.get(1).getYStart());
			assertTrue(recipe.slides.get(4).content.videos.get(1).getLoop());
			assertEquals(15, recipe.slides.get(4).content.videos.get(1).getStartTime().intValue());
	}
	
	// check the contents of the penultimate slide
	@Test
	public void sixthSlideReadCorrectly() {
		// print slide information
		System.out.println("Slide 6 id: " + recipe.slides.get(5).id);
		System.out.println("Slide 6 Duration: " + recipe.slides.get(5).duration);
		System.out.println("Is Slide 6 the last slide? " + recipe.slides.get(5).lastSlide);
		
		// print out the text information
		TextBody text = recipe.slides.get(5).content.texts.get(0);
		System.out.println("\nSlide 6: Text Object "+ 1);
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
		
		if (text.getTextString(0).getBold()) {
			System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (bold)");
		}
		else if (text.getTextString(0).getItalic()) {
			System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (italic)");
		}
		else if (text.getTextString(0).getUnderline()) {
			System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (underline)");
		}
		else {
			System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText());
		}
	
		// check contents
		assertEquals((Integer)5, recipe.slides.get(5).id);
		assertFalse(recipe.slides.get(5).lastSlide);
		// text 1
		assertEquals((Integer)10, recipe.slides.get(5).content.texts.get(0).getXStart());
		assertEquals((Integer)10, recipe.slides.get(5).content.texts.get(0).getYStart());
		assertEquals((Integer)1000, recipe.slides.get(5).content.texts.get(0).getXEnd());
		assertEquals((Integer)100, recipe.slides.get(5).content.texts.get(0).getYEnd());
		
		assertEquals("This is the branch slide for the text object", recipe.slides.get(5).content.texts.get(0).getTextString(0).getText());
		assertFalse(recipe.slides.get(5).content.texts.get(0).getTextString(0).getBold());
		assertFalse(recipe.slides.get(5).content.texts.get(0).getTextString(0).getItalic());
		assertFalse(recipe.slides.get(5).content.texts.get(0).getTextString(0).getUnderline());
	}
	
	// check the contents of the last slide
		@Test
		public void lastSlideReadCorrectly() {
			// print slide information
			System.out.println("Slide 7 id: " + recipe.slides.get(6).id);
			System.out.println("Slide 7 Duration: " + recipe.slides.get(6).duration);
			System.out.println("Is Slide 7 the last slide? " + recipe.slides.get(6).lastSlide);
			
			// print out the text information
			TextBody text = recipe.slides.get(6).content.texts.get(0);
			System.out.println("\nSlide 7: Text Object "+ 1);
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
			
			if (text.getTextString(0).getBold()) {
				System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (bold)");
			}
			else if (text.getTextString(0).getItalic()) {
				System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (italic)");
			}
			else if (text.getTextString(0).getUnderline()) {
				System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText() + " (underline)");
			}
			else {
				System.out.println("\tText String " + 1 + ": " + text.getTextString(0).getText());
			}
		
			// check contents
			assertEquals((Integer)6, recipe.slides.get(6).id);
			assertFalse(recipe.slides.get(6).lastSlide);
			// text 1
			assertEquals((Integer)10, recipe.slides.get(6).content.texts.get(0).getXStart());
			assertEquals((Integer)10, recipe.slides.get(6).content.texts.get(0).getYStart());
			assertEquals((Integer)1000, recipe.slides.get(6).content.texts.get(0).getXEnd());
			assertEquals((Integer)100, recipe.slides.get(6).content.texts.get(0).getYEnd());
			assertEquals("This is the branch slide for the image object", recipe.slides.get(6).content.texts.get(0).getTextString(0).getText());
			assertFalse(recipe.slides.get(6).content.texts.get(0).getTextString(0).getBold());
			assertFalse(recipe.slides.get(6).content.texts.get(0).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(6).content.texts.get(0).getTextString(0).getUnderline());
		}
}
