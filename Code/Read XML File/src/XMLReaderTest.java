import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class XMLReaderTest {
	private XMLReader reader;
	private CookBook cookBook;

	@Before
	public void setUp() throws Exception {
		reader = new XMLReader();
		cookBook = reader.getCookBook();

	}

	@Test
	// Test 1
	public void createListOfRecipes() {
		assertTrue(cookBook instanceof CookBook);

	}

	@Test
	// Test 2
	public void bookContainsRecipes() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertTrue(recipe instanceof Recipe);

	}

	@Test
	// Test 2
	public void recipeReturnsCorrectFields() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertNotNull(recipe.getID());
		assertNotNull(recipe.getTitle());
		assertNotNull(recipe.getPeople());
		assertNotNull(recipe.getTime());
		assertNotNull(recipe.getChef());
		assertNotNull(recipe.getDescription());
	}

	@Test
	// Test 3
	public void singleRecipeDataReadIntoBook() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertEquals("OS14022014", recipe.getID());
		assertEquals("Onion Soup", recipe.getTitle());
		assertEquals("4", recipe.getPeople());
		assertEquals("1200", recipe.getTime());
		assertEquals("Jamie Oliver", recipe.getChef());
		assertEquals("Very tasty soup", recipe.getDescription());
	}

	@Test
	// Test 4
	public void recipeDataReadIntoBook() {
		List<Recipe> book = cookBook.getRecipes();
		Recipe recipe = book.get(0);
		assertEquals("OS14022014", recipe.getID());
		assertEquals("Onion Soup", recipe.getTitle());
		assertEquals("4", recipe.getPeople());
		assertEquals("1200", recipe.getTime());
		assertEquals("Jamie Oliver", recipe.getChef());
		assertEquals("Very tasty soup", recipe.getDescription());
		
		recipe = book.get(1);
		assertEquals("LPS14022014", recipe.getID());
		assertEquals("Potato and Leek Soup", recipe.getTitle());
		assertEquals("3", recipe.getPeople());
		assertEquals("1800", recipe.getTime());
		assertEquals("Nigella Lawson", recipe.getChef());
		assertEquals("Very gross soup", recipe.getDescription());

		recipe = book.get(2);
		assertEquals("TYS14022014", recipe.getID());
		assertEquals("Tom Yum Soup", recipe.getTitle());
		assertEquals("2", recipe.getPeople());
		assertEquals("2400", recipe.getTime());
		assertEquals("Gordon Ramsay", recipe.getChef());
		assertEquals("Very tasty thai soup", recipe.getDescription());
		
		// print list contents
		int bookLength = book.size();
		for (int i = 0; i < bookLength; i++) {
			recipe = book.get(i);
			System.out.println("\tID: " + recipe.getID());
			System.out.println("\tTitle: " + recipe.getTitle());
			System.out.println("\tPeople: " + recipe.getPeople());
			System.out.println("\tTime: " + recipe.getTime());
			System.out.println("\tChef: " + recipe.getChef());
			System.out.println("\tDescription: " + recipe.getDescription() + "\n");			
		}
		System.out.println("\tcookBook name: " + cookBook.getName());
		System.out.println("\tNo. of recipes in book: " + bookLength + "\n");
		
	}
	

}
