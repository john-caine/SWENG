package XMLParser;
/* Title: XMLReaderTest
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 14/02/14
 * 
 * Description: Test class for XMLReader. Test specifics are supplied in comments below.
 * 
 * Version History: v1.01 (27/02/14) - Tests modified to ensure imageFileName field is correctly read by the parser
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
		assertNotNull(recipe.getID());
		assertNotNull(recipe.getTitle());
		assertNotNull(recipe.getPeople());
		assertNotNull(recipe.getTime());
		assertNotNull(recipe.getChef());
		assertNotNull(recipe.getDescription());
		assertNotNull(recipe.getImageFileName());
	}

	// confirm that the correct data is contained in all fields of the first recipe in the cookbook
	@Test
	public void singleRecipeDataReadIntoCookBook() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertEquals("OS14022014", recipe.getID());
		assertEquals("Onion Soup", recipe.getTitle());
		assertEquals(4, recipe.getPeople());
		assertEquals(1200, recipe.getTime());
		assertEquals("Jamie Oliver", recipe.getChef());
		assertEquals("Very tasty soup", recipe.getDescription());
		assertEquals("Spoon.png", recipe.getImageFileName());
	}

	// confirm that all recipes in the cookbook contain the correct data
	// print out values of fields for each recipe
	// print out cookbook name and number of recipes contained in the cookbook
	@Test
	public void recipeDataReadIntoBook() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertEquals("OS14022014", recipe.getID());
		assertEquals("Onion Soup", recipe.getTitle());
		assertEquals(4, recipe.getPeople());
		assertEquals(1200, recipe.getTime());
		assertEquals("Jamie Oliver", recipe.getChef());
		assertEquals("Very tasty soup", recipe.getDescription());
		assertEquals("Spoon.png", recipe.getImageFileName());
		
		recipe = book.get(1);
		assertEquals("LPS14022014", recipe.getID());
		assertEquals("Potato and Leek Soup", recipe.getTitle());
		assertEquals(3, recipe.getPeople());
		assertEquals(1800, recipe.getTime());
		assertEquals("Nigella Lawson", recipe.getChef());
		assertEquals("Very gross soup", recipe.getDescription());
		assertEquals("Play.png", recipe.getImageFileName());

		recipe = book.get(2);
		assertEquals("TYS14022014", recipe.getID());
		assertEquals("Tom Yum Soup", recipe.getTitle());
		assertEquals(2, recipe.getPeople());
		assertEquals(2400, recipe.getTime());
		assertEquals("Gordon Ramsay", recipe.getChef());
		assertEquals("Very tasty thai soup", recipe.getDescription());
		assertEquals("Pause.png", recipe.getImageFileName());
		
		// print list contents
		int bookLength = book.size();
		for (int i = 0; i < bookLength; i++) {
			recipe = book.get(i);
			System.out.println("\tID: " + recipe.getID());
			System.out.println("\tTitle: " + recipe.getTitle());
			System.out.println("\tPeople: " + recipe.getPeople());
			System.out.println("\tTime: " + recipe.getTime());
			System.out.println("\tChef: " + recipe.getChef());
			System.out.println("\tDescription: " + recipe.getDescription());
			System.out.println("\tImageFileName: " + recipe.getImageFileName() + "\n");
		}
		
		System.out.println("\tcookBook name: " + cookBook.getName());
		System.out.println("\tNo. of recipes in book: " + bookLength + "\n");
	}
}
