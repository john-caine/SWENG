package errorhandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import eCook.JavaFXThreadingRule;
import javafx.scene.text.Text;

public class ErrorHandlerTest {

	private ErrorHandler testError;
	private String textMessage;
	
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before 
	public void SetUp() {
		JFXPanel fxPanel = new JFXPanel();
		
		textMessage = "A simple test message";
		testError = new ErrorHandler(textMessage);
	}
	
	@Test
	public void textObjectContainsErrorMessage() {
		assertTrue(testError.text instanceof Text);
		assertEquals(testError.text.getText(), textMessage);
		assertEquals(testError.text.getFont().getSize(), 20);	
	}
	
	@Test
	public void newWindowHasFocus() {	
		assertTrue(testError.stage.isFocused());
	}
	
	@Test
	public void newWindowContainsButton() {
		assertEquals(testError.okButton.getLayoutX(), ((testError.stage.getScene().getWidth() / 2) - (testError.okButton.getPrefWidth() / 2) - 10), 0.1); 
		assertEquals(testError.okButton.getLayoutY(), (testError.stage.getScene().getHeight() - 35), 0.1);
	}
	
	@Test
	public void windowClosesOnButtonPress() {	
		//TODO find out how to press "ok" button then check - window.onCloseRequest() - called when closed
	}
}
