package errorhandler;

import static org.junit.Assert.*;

import java.awt.event.InputEvent;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.awt.AWTException;
import java.awt.Robot;
//import com.sun.glass.ui.Robot;




import eCook.JavaFXThreadingRule;
import javafx.embed.swing.JFXPanel;
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
	
	@Test
	public void textObjectContainsErrorMessage() {
		assertTrue(testError.text instanceof Text);
		assertEquals(testError.text.getText(), textMessage);
		//assertEquals(testError.text.getFont().getSize(), 20);	
	}
	
	@Test
	public void newWindowHasFocus() {	
		assertTrue(testError.stage.isFocused());
	}
	
	@Test
	public void newWindowContainsButton() {	//Visual Test using eCook
		testError = new ErrorHandler(textMessage);
		//assertEquals(testError.okButton.getLayoutX(), ((testError.stage.getScene().getWidth() / 2) - (testError.okButton.getPrefWidth() / 2) - 10), 0.1); 
		//assertEquals(testError.okButton.getLayoutY(), (testError.stage.getScene().getHeight() - 35), 0.1);
		//assertEquals(testError.okButton.getAlignment(), Pos.CENTER);
		//assertNotNull(testError.okButton);
		//assertTrue(testError.okButton.isVisible());
	}
	
	@Test
	public void windowClosesOnButtonPress() {	//VISUAL TEST USING ECOOK
		new ErrorHandler("button test");
//		Robot bot = null;
//		try {
//			bot = new Robot();
//		} catch (Exception fail) {
//			System.err.println("Failed instantiation Robot: " + fail);
//		}
//		int mask = InputEvent.BUTTON1_DOWN_MASK;
//		bot.mouseMove(100,100);
//		bot.mousePress(mask);
//		bot.mouseRelease(mask);
		
		//assertTrue(testError.stage.onCloseRequestProperty());
		//TODO find out how to press "ok" button then check - window.onCloseRequest() - called when closed
	}
}
