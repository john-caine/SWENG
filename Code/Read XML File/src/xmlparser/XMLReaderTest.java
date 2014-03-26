package xmlparser;
/* Title: XMLReaderTest
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: Test class for XMLReader. Test specifics are supplied in comments below.
 */

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class XMLReaderTest {
	private XMLReader reader;
	private CookBook cookBook;

	// create instances of the XML reader and cookbook
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader();
		cookBook = reader.getCookBook();
	}

	// test that a cookbook has been created
	@Test
	public void createCookbook() {
		assertTrue(cookBook instanceof CookBook);
	}

	// check that the cookbook contains at least one recipe
	@Test
	public void cookBookContainsRecipes() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertTrue(recipe instanceof Recipe);
	}

	// check that the first recipe in the cookbook contains information in all fields
	@Test
	public void recipeReturnsCorrectFields() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertNotNull(recipe.info);
		assertNotNull(recipe.defaults);
		assertNotNull(recipe.slides);
	}

	// confirm that the correct information and default data is contained in the first recipe of the cookbook
	// print out cookbook name and number of recipes contained in the cookbook
	@Test
	public void correctInfoDefaultDataReadIntoRecipe() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
		// check info
		assertEquals("Stuart Porter", recipe.info.author);
		assertEquals("1.0", recipe.info.version);
		assertEquals("Sample of the MediaPlayer input File", recipe.info.comment);
		assertEquals(640, recipe.info.width);
		assertEquals(480, recipe.info.height);
		
		// check defaults
		assertEquals("#00FF00", recipe.defaults.backgroundColor);
		assertEquals("#FF0000", recipe.defaults.fillColor);
		assertEquals("Times New Roman", recipe.defaults.font);
		assertEquals("#0000FF", recipe.defaults.fontColor);
		assertEquals("24pt", recipe.defaults.fontSize);
		assertEquals("#00FF00", recipe.defaults.lineColor);
		
		// print cookbook information
		System.out.println("cookBook name: " + cookBook.getName());
		System.out.println("No. of recipes in book: " + book.size() + "\n");
	}

	// confirm that all fields in the first slide of the first recipe in the cookbook contain the correct data
	// print out values of fields for this slide
	@Test
	public void firstSlideReadCorrectly() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
		// print slide information
		System.out.println("Slide 1 ID: " + recipe.slides.get(0).ID);
		System.out.println("Slide 1 Duration: " + recipe.slides.get(0).duration);
		System.out.println("Is Slide 1 the last slide? " + recipe.slides.get(0).lastSlide);
		
		// loop through the three shape objects, printing out the contents
		for (int i=0; i<recipe.slides.get(0).content.getNumberOfTexts(); i++) {
			Text text = recipe.slides.get(0).content.texts.get(i);
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
		assertEquals("sample1(text)", recipe.slides.get(0).ID);
		assertEquals(60, recipe.slides.get(0).duration);
		assertFalse(recipe.slides.get(0).lastSlide);
			// text 1
			assertEquals(10, recipe.slides.get(0).content.texts.get(0).getXStart());
			assertEquals(10, recipe.slides.get(0).content.texts.get(0).getYStart());
			assertEquals(100, recipe.slides.get(0).content.texts.get(0).getXEnd());
			assertEquals(25, recipe.slides.get(0).content.texts.get(0).getYEnd());
			assertEquals("This is a test of required attributes", recipe.slides.get(0).content.texts.get(0).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(0).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(0).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(0).getTextString(0).getUnderline());
			// text 2
			assertEquals(10, recipe.slides.get(0).content.texts.get(1).getXStart());
			assertEquals(35, recipe.slides.get(0).content.texts.get(1).getYStart());
			assertEquals(100, recipe.slides.get(0).content.texts.get(1).getXEnd());
			assertEquals(50, recipe.slides.get(0).content.texts.get(1).getYEnd());
			assertEquals("Time-Roman", recipe.slides.get(0).content.texts.get(1).getFont());
			assertEquals("16", recipe.slides.get(0).content.texts.get(1).getFontSize());
			assertEquals("#F7F4F1", recipe.slides.get(0).content.texts.get(1).getFontColor());
			assertEquals("This is a test of optional font attributes", recipe.slides.get(0).content.texts.get(1).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(1).getTextString(0).getUnderline());
			// text 3
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getXStart());
			assertEquals(60, recipe.slides.get(0).content.texts.get(2).getYStart());
			assertEquals(100, recipe.slides.get(0).content.texts.get(2).getXEnd());
			assertEquals(75, recipe.slides.get(0).content.texts.get(2).getYEnd());
			assertEquals(10, recipe.slides.get(0).content.texts.get(2).getStartTime());
			assertEquals(40, recipe.slides.get(0).content.texts.get(2).getDuration());
			assertEquals(2, recipe.slides.get(0).content.texts.get(2).getLayer());
			assertEquals("This is a test of timing attributes", recipe.slides.get(0).content.texts.get(2).getTextString(0).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(2).getTextString(0).getUnderline());
			// text 4
			assertEquals(10, recipe.slides.get(0).content.texts.get(3).getXStart());
			assertEquals(90, recipe.slides.get(0).content.texts.get(3).getYStart());
			assertEquals(100, recipe.slides.get(0).content.texts.get(3).getXEnd());
			assertEquals(105, recipe.slides.get(0).content.texts.get(3).getYEnd());
			// textStrings
			assertEquals("This text should be bold", recipe.slides.get(0).content.texts.get(3).getTextString(0).getText());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(0).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(0).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(0).getUnderline());
			assertEquals("This text should be italics", recipe.slides.get(0).content.texts.get(3).getTextString(1).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(1).getBold());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(1).getItalic());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(1).getUnderline());
			assertEquals("This text should be underlined", recipe.slides.get(0).content.texts.get(3).getTextString(2).getText());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(2).getBold());
			assertFalse(recipe.slides.get(0).content.texts.get(3).getTextString(2).getItalic());
			assertTrue(recipe.slides.get(0).content.texts.get(3).getTextString(2).getUnderline());
	}
	
	// confirm that all fields in the second slide of the first recipe in the cookbook contain the correct data
	// print out values of fields for this slide
	@Test
	public void secondSlideReadCorrectly() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
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
				int[] point = shape.getPoint(j);
				int num = point[0];
				int x = point[1];
				int y = point[2];
				System.out.println("\tShape point " + (j+1) + " num: " + num);
				System.out.println("\tShape point " + (j+1) + " x: " + x);
				System.out.println("\tShape point " + (j+1) + " y: " + y);
			}
		}
		
		// check slide 2
		assertEquals("sample2(shapes)", recipe.slides.get(1).ID);
		assertEquals(30, recipe.slides.get(1).duration);
		assertFalse(recipe.slides.get(1).lastSlide);
			// shape 1
			assertEquals(4, recipe.slides.get(1).content.shapes.get(0).getTotalPoints());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(0).getWidth());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(0).getHeight());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(0).getPoint(0)[0]);
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(0)[1]);
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(0)[2]);
				// point 2
				assertEquals(2, recipe.slides.get(1).content.shapes.get(0).getPoint(1)[0]);
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(1)[1]);
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(1)[2]);
				// point 3
				assertEquals(3, recipe.slides.get(1).content.shapes.get(0).getPoint(2)[0]);
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(2)[1]);
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(2)[2]);
				// point 4
				assertEquals(4, recipe.slides.get(1).content.shapes.get(0).getPoint(3)[0]);
				assertEquals(10, recipe.slides.get(1).content.shapes.get(0).getPoint(3)[1]);
				assertEquals(25, recipe.slides.get(1).content.shapes.get(0).getPoint(3)[2]);
			// shape 2
			assertEquals(1, recipe.slides.get(1).content.shapes.get(1).getTotalPoints());
			assertEquals(10, recipe.slides.get(1).content.shapes.get(1).getWidth());
			assertEquals(10, recipe.slides.get(1).content.shapes.get(1).getHeight());
			assertEquals("", recipe.slides.get(1).content.shapes.get(1).getFillColor());
			assertEquals("", recipe.slides.get(1).content.shapes.get(1).getLineColor());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(1).getPoint(0)[0]);
				assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getPoint(0)[1]);
				assertEquals(100, recipe.slides.get(1).content.shapes.get(1).getPoint(0)[2]);
			// shape 3
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getTotalPoints());
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getWidth());
			assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getHeight());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(2).getStartTime());
			assertEquals(15, recipe.slides.get(1).content.shapes.get(2).getDuration());
			assertEquals(1, recipe.slides.get(1).content.shapes.get(2).getLayer());
				// point 1
				assertEquals(1, recipe.slides.get(1).content.shapes.get(2).getPoint(0)[0]);
				assertEquals(150, recipe.slides.get(1).content.shapes.get(2).getPoint(0)[1]);
				assertEquals(150, recipe.slides.get(1).content.shapes.get(2).getPoint(0)[2]);
				// point 2
				assertEquals(2, recipe.slides.get(1).content.shapes.get(2).getPoint(1)[0]);
				assertEquals(250, recipe.slides.get(1).content.shapes.get(2).getPoint(1)[1]);
				assertEquals(250, recipe.slides.get(1).content.shapes.get(2).getPoint(1)[2]);
	}
	
	// confirm that all fields in the third slide of the first recipe in the cookbook contain the correct data
	// print out values of fields for this slide
	@Test
	public void thirdSlideReadCorrectly() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
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
		assertEquals("sample3(audio)", recipe.slides.get(2).ID);
		assertEquals(90, recipe.slides.get(2).duration);
		assertFalse(recipe.slides.get(2).lastSlide);
			// audio 1
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3", recipe.slides.get(2).content.audios.get(0).getUrlName());
			assertEquals(5, recipe.slides.get(2).content.audios.get(0).getStartTime());
			assertFalse(recipe.slides.get(2).content.audios.get(0).getLoop());
			// audio 2
			assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample2.mp3", recipe.slides.get(2).content.audios.get(1).getUrlName());
			assertEquals(35, recipe.slides.get(2).content.audios.get(1).getStartTime());
			assertFalse(recipe.slides.get(2).content.audios.get(1).getLoop());
	}
	
	
	// confirm that all fields in the fourth slide of the first recipe in the cookbook contain the correct data
	// print out values of fields for this slide
	@Test
	public void fourthSlideReadCorrectly() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
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
		assertEquals("sample4(image)", recipe.slides.get(3).ID);
		assertEquals(40, recipe.slides.get(3).duration);
		assertFalse(recipe.slides.get(3).lastSlide);
			// image 1
			assertEquals("http://4.bp.blogspot.com/-D6YYIZDSxks/UXvED6as6cI/AAAAAAAAAHs/ddOuPj9Yh7I/s1600/waffle+and+syrup.jpg", recipe.slides.get(3).content.images.get(0).getUrlName());
			assertEquals(10, recipe.slides.get(3).content.images.get(0).getXStart());
			assertEquals(10, recipe.slides.get(3).content.images.get(0).getYStart());
			// image 2
			assertEquals("http://www.foodfunhealth.com/wp-content/uploads/2010/11/By-Aylanah.jpg", recipe.slides.get(3).content.images.get(1).getUrlName());
			assertEquals(50, recipe.slides.get(3).content.images.get(1).getXStart());
			assertEquals(50, recipe.slides.get(3).content.images.get(1).getYStart());
			assertEquals(200, recipe.slides.get(3).content.images.get(1).getWidth());
			assertEquals(200, recipe.slides.get(3).content.images.get(1).getHeight());
			assertEquals(2, recipe.slides.get(3).content.images.get(1).getLayer());
			// image 3
			assertEquals("http://2.bp.blogspot.com/-UwQy6WiradM/T9igHkNTakI/AAAAAAAARfA/2lj7tz2L-So/s1600/P5294569.jpg", recipe.slides.get(3).content.images.get(2).getUrlName());
			assertEquals(100, recipe.slides.get(3).content.images.get(2).getXStart());
			assertEquals(100, recipe.slides.get(3).content.images.get(2).getYStart());
			assertEquals(20, recipe.slides.get(3).content.images.get(2).getDuration());
			assertEquals(3, recipe.slides.get(3).content.images.get(2).getLayer());
	}
	
	// confirm that all fields in the last slide of the first recipe in the cookbook contain the correct data
	// print out values of fields for this slide
	@Test
	public void lastSlideReadCorrectly() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		
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
			System.out.println("\tPlay Time: " + video.getPlayTime());
			System.out.println("\tStart Time: " + video.getStartTime());
			System.out.println("\tDuration: " + video.getDuration());
		}
		
		// check slide 4
		assertEquals("sample5(video)", recipe.slides.get(recipe.getNumberOfSlides()-1).ID);
		assertEquals(200, recipe.slides.get(recipe.getNumberOfSlides()-1).duration);
		assertFalse(recipe.slides.get(recipe.getNumberOfSlides()-1).lastSlide);
			// video 1
			assertEquals("http://ystv.co.uk/download/1715/Idents%20-%20Paper.mp4", recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getUrlName());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getXStart());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getYStart());
			assertEquals(2, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getLayer());
			assertEquals(10, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(0).getDuration());
			// video 2
			assertEquals("http://ystv.co.uk/download/2064/Union%20-%20Episode%202.mp4", recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getUrlName());
			assertEquals(300, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getXStart());
			assertEquals(300, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getYStart());
			assertTrue(recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getLoop());
			assertEquals(50, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getPlayTime());
			assertEquals(15, recipe.slides.get(recipe.getNumberOfSlides()-1).content.videos.get(1).getStartTime());
	}
}
