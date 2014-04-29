/* Title: IngredientsGUITest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 15/04/14
 * 
 * Description: Test class for IngredientsGUI. Test specifics detailed below.
 */
package shoppingList;

import org.junit.BeforeClass;
import org.junit.Test;

public class IngredientsGUITest {
	
	@BeforeClass
	public static void setUp() {
		System.out.println("This test class contains no automated tests.");
		System.out.println("Instead, visual tests were conducted and the results documented as follows:\n");
	}
	
	@Test
	public void ingredientsListDisplayedCorrectly() {
		System.out.println("Checking ingredients list matches the example list...");
		System.out.println("Ingredients list contains the correct items and is displayed correctly on the left hand side.\n");
	}
	
	@Test
	public void selectAllButtonWorksCorrectly() {
		System.out.println("Checking that select all button performs expected operation...");
		System.out.println("when select all button is pressed, all checkboxes are selected.");
		System.out.println("when all checkboxes are selected, button text changes to 'deselect all'.");
		System.out.println("when deselect all button is pressed, all checkboxes are cleared.");
		System.out.println("test passed.\n");
	}
	
	@Test
	public void shoppingListContainsCorrectSelectedIngredients() {
		System.out.println("Checking that shopping list contains the selected ingredients...");
		System.out.println("when no checkboxes are selected, the shopping list is empty.");
		System.out.println("when a checkbox is selected, the selected ingredient is added to the shopping list.");
		System.out.println("the select all button adds all of the ingredients to the shopping list.");
		System.out.println("when a checkbox is deselected, that item is removed from the shopping list.");
		System.out.println("test passed.\n");
	}
	
	@Test
	public void saveShoppingListButtonWorksCorrectly() {
		System.out.println("Checking that the save button works correctly...");
		System.out.println("save button is disabled until items are added to the shopping list, as expected");
		System.out.println("when the shopping list has at least one item, the save button is enabled");
		System.out.println("clicking the save button sends the shopping list to the PDF creator, and this is confirmed by the text in the status bar.");
		System.out.println("test passed.\n");
	}
	
	@Test
	public void PDFFileContainsCorrectShoppingList() {
		System.out.println("Checking that the generated PDF file contains the correct information...");
		System.out.println("PDF file created successfully and given correct file name");
		System.out.println("PDF file contains the Spoon logo at the top of the document as expected.");
		System.out.println("PDF file contains 'Shopping List:' title.");
		System.out.println("PDF file contains the correct list of items selected from the GUI.");
		System.out.println("Large strings are formatted in the PDF file to continue over two lines.");
		System.out.println("If there are more than one page worth of ingredients, the list is continued over several pages.");
		System.out.println("test passed.\n");
	}
	
}
