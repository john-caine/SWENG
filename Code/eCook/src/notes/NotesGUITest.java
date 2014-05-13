package notes;
/* Title: NotesGUITest
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 09/05/14
 * 
 * Description: Testing for NotesGUI Class. Test specifics are detailed below.
 */

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.sun.glass.events.KeyEvent;

@SuppressWarnings("restriction")
public class NotesGUITest {
	NotesGUI notesGUI;
	TextFileHandler handler;
	Robot robot;
	Rectangle2D screen;

	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before
	public void setup() throws AWTException {
		// get the size of the screen
	    screen = Screen.getPrimary().getVisualBounds();
		robot = new Robot();
	}
	
	// This test acts as a @BeforeClass method - not sure why that won't work.
	public void launchGUI() {
		if (notesGUI == null) {
			notesGUI = new NotesGUI();
		}
	}

	// test to see if the notes panel is correctly displayed when mouse is at LHS of screen
	@Test
	public void notesPanelDisplayedOnMouseover() throws IOException {
		launchGUI();
		System.out.println("moving mouse to middle of screen...");
		robot.mouseMove((int) screen.getWidth()/2, (int) screen.getHeight()/2);
		System.out.println("Is the notes panel displayed? " + notesGUI.getNotesPanelVisible());
		assertFalse(notesGUI.getNotesPanelVisible());
		//robot.delay(2000);
		
		System.out.println("moving mouse to far LHS of screen...");
		robot.mouseMove(0, (int) screen.getHeight()/2);
		//robot.delay(3000);
		BufferedImage capture = robot.createScreenCapture(new Rectangle((int) screen.getWidth(), (int) screen.getHeight()));
		ImageIO.write(capture, "bmp", new File("screenshot.bmp"));
		System.out.println("Is the notes panel displayed? " + notesGUI.getNotesPanelVisible());
		assertTrue(notesGUI.getNotesPanelVisible());
	}
	
	// test to see if the notes textArea shows the correct text
	// and check that the text file created contains the correct text
	@Test
	public void notesBoxDisplaysCorrectText() {
		launchGUI();
		System.out.println("moving mouse to far LHS of screen...");
		robot.mouseMove(0, (int) screen.getHeight()/2);
		//robot.delay(2000);
		
		assertEquals("Write your notes here", notesGUI.getContentOfNotesBox());
		
		robot.keyPress(KeyEvent.VK_T);
		robot.keyPress(KeyEvent.VK_E);
		robot.keyPress(KeyEvent.VK_S);
		robot.keyPress(KeyEvent.VK_T);
		
		//robot.delay(1000);
		
		assertEquals("test", notesGUI.getContentOfNotesBox());
		
		System.out.println("moving mouse to middle of screen...");
		robot.mouseMove((int) screen.getWidth()/2, (int) screen.getHeight()/2);
		
		//robot.delay(2000);
		
		File file = new File("Slide93_notes.txt");
		assertTrue(file.exists());
		handler = new TextFileHandler();
		assertEquals("test", handler.readTextFile("Slide93_notes.txt"));
	}
	
	@Test
	public void waitForAWhile() throws InterruptedException {
		Thread.sleep(5000);
	}
}
