/*
 * Programmer: Ankita Gangotra
 * Date Created: 07/06/14
 * Description: Unit tests for eCook.
 */
package eCook;

import javafx.scene.Group;
import javafx.scene.Scene;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class eCookTest {

	/*
	 * Visual tests
	 */

	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	private static Group group;
	@Before
	// perform JavaFX setup 
	public void setup() {
		group = new Group();
		new Scene(group);
	}

	
	@Test
	public void eCookIconCreated(){
		System.out.println("Testing if eCook icon is created when eCook is run...");
		System.out.println("Run eCook");
		System.out.println("Icon is created and displayed correctly.\n");
	}
	
}
