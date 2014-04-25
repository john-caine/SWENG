package timer;

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
				timer.getPane().setLayoutX(timer.getPane().getLayoutX() + timer.getPane().getWidth()*numberOfTimers);
				group.getChildren().add(timer.getPane());
				numberOfTimers++;
				event.consume();
			}
			
			
		});
		
		
	}

	

}
