/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Ingredient Class
 */

package xmlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class IngredientTest {
	Ingredient ingredient;
	
	/*
	 * check that setting attributes works correctly
	 */
	@Test
	public void canSetAttributes() {
		// instantiate a new ingredient class
		ingredient = new Ingredient();
		
		// check that everything is null/empty to start with
		assertNull(ingredient.name);
		assertEquals("", ingredient.units);
		assertEquals(0, ingredient.amount, 0.0001);
		
		// set arbitrary values
		ingredient.setName("example name");
		ingredient.setUnits("example units");
		ingredient.setAmount("12.68");
		
		// check that the fields have been set correctly
		assertEquals("example name", ingredient.name);
		assertEquals("example units", ingredient.units);
		assertEquals(12.68, ingredient.amount, 0.001);
	}
	
	/*
	 * check that getting attributes works correctly
	 */
	@Test
	public void canGetAttributes() {
		// instantiate a new ingredient class
		ingredient = new Ingredient();
		
		// check that the get methods return null/empty if no data has been set
		assertNull(ingredient.getName());
		assertEquals("", ingredient.getUnits());
		assertEquals(0, ingredient.getAmount(), 0.0001);

		// set arbitrary values
		ingredient.setName("example name");
		ingredient.setUnits("example units");
		ingredient.setAmount("12.68");

		// check that the fields have been set correctly
		assertEquals("example name", ingredient.getName());
		assertEquals("example units", ingredient.getUnits());
		assertEquals(12.68, ingredient.getAmount(), 0.0001);
	}
}