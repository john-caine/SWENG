package timer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import notes.NotesGUI;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.SlideShow;



public class TimerTest {
	
	private Timer timer;
	private VBox timerbox;
	private int numberOfTimers = 0;
	private SlideShow slideShow;
	private Thread thread;
	private ArrayList<Timer> timerList;
	NotesGUI notesGUI;
	Group slideRoot;
	// Run tests on JavaFX thread ref. Andy Till
		// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
		//@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

	@Before 
	public void SetUp(){
		timerbox = new VBox();
		timerList = new ArrayList<Timer>();

		timer = new Timer(null, null, null, null, null, null, null, numberOfTimers, slideShow, notesGUI, slideRoot);
		timerList.add(timer);
		timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
			
			@Override
			public void handle(WorkerStateEvent event) {
				
				Platform.runLater( new Runnable(){
					public void run(){
						System.out.println("print");
						timerbox.getChildren().add(timer.getTimerID(), timer.getPane());
						numberOfTimers ++;
					}
				});	 									
			}
		});
		new Thread(timer).start();	
	}
	
	@Test
	public void timerCreatedTest(){
		assertTrue(timerbox.getChildren().get(0) instanceof HBox);
	}

//	@Test
//	public void timerThreadCreated() throws InterruptedException {
//		final CountDownLatch latch = new CountDownLatch(1);
//		final Timer timer = new Timer(null, null, null, null, null, null, null, numberOfTimers, slideShow);
//		Runnable run  = new Runnable(){
//
//			@Override
//			public void run() {
//				timer.run();
//				latch.countDown();
//				
//			}
//			
//		};
//		
//		Thread thread = new Thread(run);
//		thread.start();
//		assertTrue(latch.await(1000, TimeUnit.SECONDS));
//		assertNotNull(timer.numbersListSeconds.getChildrenUnmodifiable());
//	}

}
