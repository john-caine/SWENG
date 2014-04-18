package timer;

import static org.junit.Assert.*;
import javafx.scene.control.Button;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



public class TimerTest {
	
	private Timer timer;
	// Run tests on JavaFX thread ref. Andy Till
		// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
		@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before 
	public void SetUp(){
		
		timer = new Timer();
		
	}

	@Test
	public void startButtonCreated() {
		assertNotNull(timer.group.getChildrenUnmodifiable());
	}

}
