/*
 * Programmers: Jonathan Caine
 * Date: 03/05/2014
 * Description: Test class for the ErrorHandler
 * 
 */
package errorhandler;

import static org.junit.Assert.*;
import java.awt.event.InputEvent;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import java.awt.Robot;
import eCook.JavaFXThreadingRule;
import javafx.geometry.Pos;
import javafx.scene.text.Text;

public class ErrorHandlerTest {

	private ErrorHandler testError;
	private String textMessage;
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before 
	public void SetUp() {
		//JFXPanel fxPanel = new JFXPanel();
		
		textMessage = "A simple test message";
		testError = new ErrorHandler(textMessage);
	}
	
	/**
	 * Test to check the error message is inside the text object
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void textObjectContainsErrorMessage() {
		assertTrue(testError.text instanceof Text);
		assertEquals(testError.text.getText(), textMessage);
		assertEquals( 20, testError.text.getFont().getSize(), 0.1);	
	}
	
	/**
	 * Test to check the new ErrorHandler has mouse/keyboard focus
	 */
	@Test
	public void newWindowHasFocus() {	
		assertTrue(testError.stage.isFocused());
	}
	
	/**
	 * Test to check the pop-up window contains the "OK" button
	 */
	@Test
	public void newWindowContainsButton() {	
		//testError = new ErrorHandler(textMessage);
		assertEquals(testError.okButton.getLayoutX(), ((testError.stage.getScene().getWidth() / 2) - (testError.okButton.getPrefWidth() / 2) - 10), 0.1); 
		assertEquals(testError.okButton.getLayoutY(), (testError.stage.getScene().getHeight() - 35), 0.1);
		assertEquals(testError.okButton.getAlignment(), Pos.CENTER);
		assertNotNull(testError.okButton);
		assertTrue(testError.okButton.isVisible());
	}
	
	/**
	 * Test to check the application closes when the "OK" button is pressed on the pop-up window
	 */
	@Test
	public void windowClosesOnButtonPress() {
		new ErrorHandler("button test");
		Robot bot = null;
		try {
			bot = new Robot();
		} catch (Exception fail) {
			System.out.println("Failed instantiation Robot: " + fail);
		}
		int mask = InputEvent.BUTTON1_DOWN_MASK;
		bot.mouseMove(100,100);
		bot.mousePress(mask);
		bot.mouseRelease(mask);
		
		//assertTrue(testError.stage.onCloseRequestProperty());
		//TODO find out how to press "ok" button then check - window.onCloseRequest() - called when closed
	}
}
