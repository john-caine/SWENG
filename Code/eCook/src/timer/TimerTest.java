package timer;

import static org.junit.Assert.*;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;



public class TimerTest {
	
	private Timer timer;
	private HBox timerHbox;
	private Thread thread;
	// Run tests on JavaFX thread ref. Andy Till
		// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
		//@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before 
	public void SetUp(){
		
		timer = new Timer(null, null, null);
		timerHbox = new HBox();
		timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			
		

			@Override
			public void handle(WorkerStateEvent event) {
						System.out.println("Timer box added");
						timerHbox.getChildren().add(timer.getPane());

			}
		});
		
		

		
		
		
		
	}

	@Test
	public void timerThreadCreated() {
		
		assertTrue(thread.isAlive());
	
		
	}
	
	@Test 
	public void timerHBoxPopulated(){
		
		assertNotNull(timerHbox.getChildren());
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
