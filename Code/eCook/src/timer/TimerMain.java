package timer;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TimerMain extends Application{

	private Group group;
	private Scene scene;
	private int numberOfTimers;
	private Timer timer;
	private HBox timerHbox;

	@Override
	public void start(Stage stage) throws Exception {
		
		group = new Group();
		scene = new Scene(group);
		timerHbox = new HBox();
		timerHbox.setLayoutX(400);
		timerHbox.setLayoutY(400);
		
		stage.setScene(scene);
		
		
		Button button = new Button("I am the main button");
		button.setLayoutX(600);
		button.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				timer = new Timer();
				 
				timer.setOnSucceeded(new EventHandler<WorkerStateEvent>(){

					@Override
					public void handle(WorkerStateEvent event) {
						System.out.println("Success!");
						
						
						Platform.runLater( new Runnable(){
							public void run(){
								timerHbox.getChildren().add(timer.getPane());
							}
						});	 
						
						
						numberOfTimers++;
						System.out.println((timer.getPane().getWidth()*numberOfTimers));
						System.out.println(numberOfTimers);
						System.out.println(group.getChildren().size());
					}
				
				});
				
				new Thread(timer).start();
				System.out.println("Timer Thread started");
			}	
			
		});
		
		
		group.getChildren().add(timerHbox);
		group.getChildren().add(button);
		
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}

}
