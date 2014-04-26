package timer;

import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class TimerManager {
	
	private Timer timer;
	private int numberOfTimers = 0;
	private Button timerManagerlabel;

	public TimerManager(Group group){
		
		timerManagerlabel = new Button("Create timer");
		group.getChildren().add(timerManagerlabel);
		
		timerManagerlabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent event) {
				
				System.out.println("Timer created");
				timer = new Timer();
				 
				 timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

					@Override
					public void handle(WorkerStateEvent event) {
						System.out.println("Success!");
						timer.getPane().setLayoutX(timer.getPane().getLayoutX() + timer.getPane().getWidth()*numberOfTimers);
						group.getChildren().add(timer.getPane());
						numberOfTimers++;
						System.out.println(event.isConsumed());
						event.consume();
						System.out.println(event.isConsumed());
						
					}
					 
				 });
				
				 new Thread(timer).start();
				 System.out.println("I am here");
				 
				System.out.println(event.isConsumed());
			}
			
			
		});
		
		
	}

	

}
