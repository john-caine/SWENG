package notes;

import org.junit.Test;

/* Title: NotesGUITest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 09/05/14
 * 
 * Description: Testing for NotesGUI Class. Test specifics are detailed below.
 */

public class NotesGUITest {
	
	// This test class contains no automated tests. Instead, visual tests were carried out.
	// The results of such testing is documented below.

	@Test
	public void notesPanelDisplayedWhenMouseMovedToLHS() {
		System.out.println("Testing if notes panel is displayed when mouse is moved to far LHS of screen...");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.\n");
	}
	
	@Test
	public void notesPanelReadsAnyPreviousNotes() {
		System.out.println("Testing if notes panel shows previous notes if any...");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.");
		System.out.println("notes text area contains contents of text file for the correct slide ID.\n");
	}
	
	@Test
	public void notesPanelShowsDefaultViewIfNoPreviousNotes() {
		System.out.println("Testing if notes panel shows default view if no previous notes found..");
		System.out.println("Mouse moved to far LHS, waiting 2 seconds");
		System.out.println("notes panel displayed correctly.");
		System.out.println("Notes panel shows default view: prompting user to add notes.\n");
	}
	
	@Test
	public void notesPanelDisappearsOnMouseout() {
		System.out.println("Testing if notes panel is hidden correctly when mouse is moved out of the notes panel...");
		System.out.println("Mouse moved to centre of screen, waiting 2 seconds");
		System.out.println("notes panel hidden correctly.");
	}
}
