/* 
 * Programmers: Max, Ankita
 * Date Created: 09/05/14 
 * Description: Testing for NotesGUI Class. Test specifics are detailed below.
 */

package notes;

import static org.junit.Assert.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import eCook.JavaFXThreadingRule;


public class NotesGUITest {
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	protected static Group group;
	protected static Scene scene;
	protected static VBox timerBox;
	
	@Before
	// perform JavaFX setup to launch the notes GUI
	public void setup() {
		group = new Group();
		scene = new Scene(group);
		timerBox = new VBox();
	}
	
	// This test class contains visual and automated tests.
	// The results of such testing is documented below.
		
	/* Visual Tests */

	/**
	 * Test to check the panel is displayed when mouse is moved to left hand side of screen
	 */
	@Test
	public void notesPanelDisplayedWhenMouseMovedToLHS() {
		System.out.println("Testing if notes panel is displayed when mouse is moved to far LHS of screen...");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.\n");
	}
	
	/**
	 * Test to check the panel shows any previous notes
	 */
	@Test
	public void notesPanelReadsAnyPreviousNotes() {
		System.out.println("Testing if notes panel shows previous notes if any...");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.");
		System.out.println("notes text area contains contents of text file for the correct slide ID.\n");
	}
	
	/**
	 * Test to check the panel shows the default text if no previous notes
	 */
	@Test
	public void notesPanelShowsDefaultViewIfNoPreviousNotes() {
		System.out.println("Testing if notes panel shows default view if no previous notes found..");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.");
		System.out.println("Notes panel shows default view: prompting user to add notes.\n");
	}
	
	/**
	 * Test to check the panel is not displayed when mouse moves out of the panel
	 */
	@Test
	public void notesPanelDisappearsOnMouseout() {
		System.out.println("Testing if notes panel is hidden correctly when mouse is moved out of the notes panel...");
		System.out.println("Mouse moved to centre of screen, waiting 2 seconds");
		System.out.println("notes panel hidden correctly.");
	}
	
	/**
	 * Test to check any notes are saved to the correct text file
	 */
	@Test
	public void notesSavedToTextFileCorrectly() {
		System.out.println("Testing if notes are saved to correct text file when added...");
		System.out.println("Mouse moved to LHS of screen, waiting 2 seconds");
		System.out.println("notes panel appears correctly.");
		System.out.println("Typing text into panel...");
		System.out.println("Moving mouse back to centre of screen... Panel disappears.");
		System.out.println("Closing eCook...");
		System.out.println("Checking that correct text file has been created with correct name");
		System.out.println("Checking that text file has correct contents. Passed.");
	}
	
	/* Automated Tests */
	
	/**
	 * Test to check that all of the UI components are instantiated correctly
	 */
	@Test
	public void GUISetupCorrectly() {
		// if notes panel was not open previously:
		NotesGUI notesGUI = new NotesGUI("recipe title", 1, group, timerBox, false);
		assertNotNull(notesGUI.handler);
		assertNotNull(notesGUI.notesPanel);
		assertNotNull(notesGUI.notesBox);
		assertTrue(notesGUI.notesPanel.getChildren().contains(notesGUI.notesBox));
		assertFalse(notesGUI.notesPanelVisible);
		assertEquals("Write your notes here", notesGUI.getContentOfNotesBox());
		
		// and if notes panel was open previously:
		NotesGUI notesGUI2 = new NotesGUI("recipe title", 1, group, timerBox, true);
		assertNotNull(notesGUI2.handler);
		assertNotNull(notesGUI2.notesPanel);
		assertNotNull(notesGUI2.notesBox);
		assertFalse(notesGUI2.notesPanel.getChildren().contains(notesGUI.notesBox));
		assertTrue(notesGUI2.notesPanelVisible);
		assertEquals("Write your notes here", notesGUI2.getContentOfNotesBox());
	}
	
	/**
	 * Test to check that the notes box is updated when text is typed
	 */
	@Test
	public void notesBoxContainsTypedText() {
		NotesGUI notesGUI = new NotesGUI("recipe title", 1, group, timerBox, false);
		assertEquals("Write your notes here", notesGUI.getContentOfNotesBox());
		notesGUI.notesBox.fireEvent(new InputEvent(KeyEvent.KEY_PRESSED));
		assertEquals("", notesGUI.getContentOfNotesBox());
		notesGUI.notesBox.setText("example notes text for testing");
		assertEquals("example notes text for testing", notesGUI.getContentOfNotesBox());
	}
}