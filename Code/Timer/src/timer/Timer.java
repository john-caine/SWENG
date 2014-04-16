package timer;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Timer extends Application {
	

	private Group group;
	private Scene scene;
	private Button startTimer;
	private Integer timerStart = 10;
	private Integer timerValue= 0;
	private Label label;
	private Timeline timeLine;

	

	@Override
	public void start(Stage stage) throws Exception {
		
		
			
			group = new Group();
			scene = new Scene(group);
			

			startTimer = new Button("Start Timer");
			startTimer.setLayoutX(400);
			startTimer.setLayoutY(350);
			label = new Label(timerStart.toString());
			label.setLayoutX(400);
			label.setLayoutY(400);
			group.getChildren().add(startTimer);
			group.getChildren().add(label);
			stage.setScene(scene);
			stage.show();
			
			
			
			startTimer.setOnAction(new EventHandler<ActionEvent>() {

				

				@Override
		        public void handle(ActionEvent event) {
					timerValue = timerStart;
					timeLine = new Timeline();
					timeLine.setCycleCount(Timeline.INDEFINITE);
					timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

						
						@Override
						public void handle(ActionEvent event) {
							timerValue--;
							label.setText(timerValue.toString());
							
							if(timerValue <= 0){
								
								timeLine.stop();
							}
							
						}
						
						
					}));
				timeLine.playFromStart();
		        }
		    });
			}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}

}
