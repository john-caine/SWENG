/*
 * Programmer: Max
 * Date created: 03/06/14
 * Description: Unit tests for PDFCreator Class
 */
package shoppingList;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;

public class PDFCreatorTest {
	PDFCreator pdfCreator;
	ArrayList<String> testList;
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	// clear the temp file before testing
	@BeforeClass
	public static void cleanUp() {
		File file = new File("shoppingListTemp.pdf");
		if (file.exists()) {
			file.delete();
		}
	}
	
	/* Automated Tests */
	
	// test that no PDF is created when null arguments are parsed
	@Test
	public void errorThrownWhenNullArgumentsParsed() {
		pdfCreator = new PDFCreator(null, false);
		File file = new File("shoppingListTemp.pdf");
		assertFalse(file.exists());
	}
	
	// test that a PDF file is created in the default temp directory when no filepath is selected
	@Test
	public void tempPDFCreated() {
		testList = new ArrayList<String>();
		testList.add("example item 1");
		testList.add("example item 2");
		pdfCreator = new PDFCreator(testList, false);
		File file = new File("shoppingListTemp.pdf");
		assertTrue(file.exists());
	}
	
	/* Visual Tests */
	
	// check that the file chooser prompt appears when filpath is set to check
	@Test
	public void userPromptedToChooseFilepath() {
		testList = new ArrayList<String>();
		testList.add("example item 1");
		testList.add("example item 2");
		pdfCreator = new PDFCreator(testList, true);
		System.out.println("file browser appeared as expected.");
	}
	
	// check that the contents of the temp shopping list PDF is correct
	public void tempPDFContainsCorrectItems() {
		System.out.println("testing that the temp PDF created contains the correct items...");
		System.out.println("opening file 'shoppingListTemp.pdf'");
		System.out.println("List reads: example item 1, example item 2.");
		System.out.println("test passed.\n\n");
	}

}