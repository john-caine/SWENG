package timer;

import javafx.application.Platform;
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
	private Timer timer2;

	public TimerManager(Group group){
		
		timerManagerlabel = new Button("Create timer");
		
				group.getChildren().add(timerManagerlabel);
		
		
		System.out.println(group.getChildren().size());
		timerManagerlabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

		

			@Override
			public void handle(MouseEvent event) {
				
			
				timer = new Timer();
				 
				 timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

					

					@Override
					public void handle(WorkerStateEvent event) {
						System.out.println("Success!");
						timer.getPane().setLayoutX(timer.getPane().getLayoutX() + timer.getPane().getWidth()*numberOfTimers);
						
						Platform.runLater( new Runnable(){
							public void run(){
								group.getChildren().add(timer.getPane());
							}
						});	 
						
						numberOfTimers++;
						System.out.println(numberOfTimers);
						System.out.println(group.getChildren().size());
						timer2 = new Timer();
				
						
						
					}
					 
				 });
				
				new Thread(timer).start();
				
				 System.out.println("I am here");
				 event.consume();
				System.out.println(event.isConsumed());
				
			}
			
			
		});
		
		
	}

	

}
