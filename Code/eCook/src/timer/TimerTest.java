package timer;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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
		
		timer = new Timer(null, null, null, null);
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
	public void timerThreadCreated() throws InterruptedException {
		final CountDownLatch latch = new CountDownLatch(1);
		final Timer timer = new Timer(null, null, null, null);
		Runnable run  = new Runnable(){

			@Override
			public void run() {
				timer.run();
				latch.countDown();
				
			}
			
		};
		
		Thread thread = new Thread(run);
		thread.start();
		assertTrue(latch.await(1000, TimeUnit.SECONDS));
		assertNotNull(timer.numbersListSeconds.getChildrenUnmodifiable());
	}
	
	@Test 
	public void timerHBoxPopulated(){
		
	
		
		
	}
	
	@Test
	public void timerListPopulated(){
		
	}

}
