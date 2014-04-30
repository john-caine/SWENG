/* Title: IngredientsTest
 * 
 * Programmers: James, Sam
 * 
 * Date Created: 30/04/14
 * 
 * Description: Test class for Ingredients.
 * 
 * Version History: v1.0 (30/04/14) - Tests the string output value from ingredients after reading example .xml file
 */
package xmlparser;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class IngredientsTest {
	private XMLReader reader;
	private Ingredients ingredients;

	
	@Before
	public void setUp() throws Exception {// Call XML parser
		reader = new XMLReader("../Resources/Recipe_Example.xml");
		ingredients = reader.getIngredients();	
	}

	/*
	 * A test to check that the ingredient strings are updated accordingly based on the number of guests
	 * provided into the method
	 * 1 guest, 2 guests and 1 guests are tested
	 * James and Sam
	 */
	@Test
	public void nGuestsTest() {
		assertEquals("700.0 grams of onion\n2.0 tablespoons of olive oil\n2.0 oz of butter\n2.0 cloves of garlic\n0.5 teaspoon of granulated sugar\n2.0 pints of beef stock\n", ingredients.getIngredients(1));
		assertEquals("1400.0 grams of onion\n4.0 tablespoons of olive oil\n4.0 oz of butter\n4.0 cloves of garlic\n1.0 teaspoon of granulated sugar\n4.0 pints of beef stock\n", ingredients.getIngredients(2));
		assertEquals("700.0 grams of onion\n2.0 tablespoons of olive oil\n2.0 oz of butter\n2.0 cloves of garlic\n0.5 teaspoon of granulated sugar\n2.0 pints of beef stock\n", ingredients.getIngredients(1));
	}
}