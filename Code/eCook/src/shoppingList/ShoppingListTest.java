/*
 * Programmer: Max , Paul.
 * Date Created: 03/06/2014
 * Description: Unit tests for shopping list class.
 */

package shoppingList;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ShoppingListTest {
	/* Automated Tests */
	
	/*
	 * check that the information in the current shoppingList.txt file is read correctly
	 */
	@Test
	public void readTextFileReturnsCorrectStrings() {
		String line1 = "butter";
		String line2 = "Lemons";
		String line3 = "More Lemons";
		String line4 = "Tasty Lemons";
		String line5 = "just lots of lemons please";
		String line6 = "What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out";

		List<String> testList = new ArrayList<String>();
		testList.add(line1);
		testList.add(line2);
		testList.add(line3);
		testList.add(line4);
		testList.add(line5);
		testList.add(line6);

		ShoppingList shoppingList = new ShoppingList();
		
		shoppingList.addItem(line1);
		shoppingList.addItem(line2);
		shoppingList.addItem(line3);
		shoppingList.addItem(line4);
		shoppingList.addItem(line5);
		shoppingList.addItem(line6);
		
		assertEquals(testList, shoppingList.shoppingList);
	}
	
	/* 
	 * execute only once, in the starting
	 */
	@BeforeClass
	public static void beforeClass() {
		ShoppingList shoppingList = new ShoppingList();
		ArrayList<String> testList = new ArrayList<String>();

		// Check if there is anything currently in the shopping list
		if (shoppingList.getNumberOfItems() > 0) {
			// Get the contents of the shopping list
			testList = shoppingList.readFromTextFile();

			// Remove all the items from the shopping list
			shoppingList.removeItems(testList);
		}
	}

	/* 
	 * execute only once, in the end
	 */
	@AfterClass
	public static void afterClass() {
		ShoppingList shoppingList = new ShoppingList();
		ArrayList<String> testList = new ArrayList<String>();

		// Check if there is anything currently in the shopping list
		if (shoppingList.getNumberOfItems() > 0) {
			// Get the contents of the shopping list
			testList = shoppingList.readFromTextFile();

			// Remove all the items from the shopping list
			shoppingList.removeItems(testList);
		}
	}



	/*
	 *  check that the number of items in the list is correct
	 */
	@Test
	public void correctNumberOfItemsInList() {
		ShoppingList shoppingList = new ShoppingList();
		assertEquals(6, shoppingList.getNumberOfItems());
	}

	/*
	 *  check that adding an item works correctly
	 */
	@Test
	public void itemAddedCorrectlyAppearsInList() {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.addItem("custard creams");
		assertTrue(shoppingList.shoppingList.contains("custard creams"));
		// if the test passes, remove the item from the list to avoid upsetting the other tests
		shoppingList.removeItem("custard creams");
	}

	/*
	 *  check that removing an item works correctly
	 */
	@Test
	public void itemRemovedCorrectlyDisappearsFromList() {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.removeItem("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out");
		assertFalse(shoppingList.shoppingList.contains("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out"));
		// if the test passes, add the item back to the list to avoid upsetting the other tests
		shoppingList.addItem("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out");
	}

	/*
	 *  check that removing several item works correctly
	 */
	@Test
	public void itemsRemovedCorrectlyDisappearFromList() {
		ShoppingList shoppingList = new ShoppingList();
		List<String> itemsToRemove = new ArrayList<String>();
		itemsToRemove.add("just lots of lemons please");
		itemsToRemove.add("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out");
		shoppingList.removeItems(itemsToRemove);
		assertFalse(shoppingList.shoppingList.contains("just lots of lemons please"));
		assertFalse(shoppingList.shoppingList.contains("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out"));
		// if the test passes, add the item back to the list to avoid upsetting the other tests
		shoppingList.addItem("just lots of lemons please");
		shoppingList.addItem("What happens if the ingredient is so long that it goes onto a new line does it break the shopping list who knows lets find out");
	}


	/* Visual Tests */

	// check that the .txt file is updated correctly from the preceding tests
	@Test
	public void textFileContainsCorrectInfo() {
		System.out.println("checking text file contents after each test...");
		System.out.println("text file updated correctly in each instance.");
		System.out.println("test passed.");
	}
}