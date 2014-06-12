/* Title: IngredientsListTest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 15/04/14
 * 
 * Description: Test class for IngredientsList. Test specifics detailed below.
 */
package shoppingList;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.VBox;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;

import xmlparser.Ingredient;
import xmlparser.Recipe;

public class IngredientsListTest {
	IngredientsList testClass;
	Recipe testRecipe;
	
	/* Automated Tests */
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	/*
	 *  check that the ingredients list held in the class is correct from the recipe file
	 */
	@SuppressWarnings({"static-access"})
	@Test
	public void ingredientsListCorrectlyRead() {
		// set up an example recipe file with ingredients
		testRecipe = new Recipe();
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setName("ing1");
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("ing2");
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setName("ing3");
		
		testRecipe.addIngredient(ingredient1);
		testRecipe.addIngredient(ingredient2);
		testRecipe.addIngredient(ingredient3);
		
		// populate the testing list (with 0 for ingredient amount and no units)
		List<String> testIngList = new ArrayList<String>();
		testIngList.add(ingredient1.getName() + " 0 ");
		testIngList.add(ingredient2.getName() + " 0 ");
		testIngList.add(ingredient3.getName() + " 0 ");
		
		// call the ingredients list class and test
		testClass = new IngredientsList(testRecipe, 100, 100);
		
		assertEquals(testIngList, testClass.ingredientsList);
	}
	
	/*
	 *  check that the class constructs a VBox
	 */
	@Test
	public void classReturnsVBox() {
		// call the ingredients list class and test
		// set up an example recipe file with ingredients
		testRecipe = new Recipe();
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setName("ing1");
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("ing2");
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setName("ing3");

		testRecipe.addIngredient(ingredient1);
		testRecipe.addIngredient(ingredient2);
		testRecipe.addIngredient(ingredient3);
		testClass = new IngredientsList(testRecipe, 100, 100);
		
		assertEquals(VBox.class, testClass.getIngredientsListGUI().getClass());
		assertNotNull(testClass.getIngredientsListGUI());
	}
	
	// check that the update status bar method works correctly
	@Test
	public void checkStatusBar() {
		// call the ingredients list class and test
		// set up an example recipe file with ingredients
		testRecipe = new Recipe();
		Ingredient ingredient1 = new Ingredient();
		ingredient1.setName("ing1");
		Ingredient ingredient2 = new Ingredient();
		ingredient2.setName("ing2");
		Ingredient ingredient3 = new Ingredient();
		ingredient3.setName("ing3");

		testRecipe.addIngredient(ingredient1);
		testRecipe.addIngredient(ingredient2);
		testRecipe.addIngredient(ingredient3);
		testClass = new IngredientsList(testRecipe, 100, 100);
		
		assertEquals("", testClass.statusBar.getText());
		
		// change the status bar text
		testClass.updateStatusBar("example status bar text");
		
		// test the result
		assertEquals("example status bar text", testClass.statusBar.getText());
	}
	
	/* Visual Tests */
	
	@BeforeClass
	public static void setUp() {
		System.out.println("This test class contains automated and visual tests.");
		System.out.println("Results of visual tests are documented as follows:\n");
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
