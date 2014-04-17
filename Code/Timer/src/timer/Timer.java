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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Timer extends Application {
	

	private Group group;
	private Scene scene;
	private Button startTimer;
	private Integer timerStartSeconds = 10;
	private Integer timerValueSeconds= 0;
	private Label labelSeconds;
	private Timeline timeLineSeconds;
	private Integer timerValueMinutes;
	private Timeline timeLineMinutes;
	private Label labelMinutes;
	private HBox timerBox;
	private Integer timerStartMinutes = 0;
	private Integer timerValueHours;
	private Timeline timeLineHours;
	private Integer timerStartHours =1 ;
	private Label labelHours;

	

	@Override
	public void start(Stage stage) throws Exception {
		
		
			
			group = new Group();
			scene = new Scene(group);
			timerBox = new HBox();
			

			startTimer = new Button("Start Timer");
			startTimer.setLayoutX(400);
			startTimer.setLayoutY(350);
			
			if(timerStartSeconds > 9){
				labelSeconds = new Label(timerStartSeconds.toString());
			}
			else{
				labelSeconds = new Label("0" + timerStartSeconds.toString());
			}
			
			if(timerStartMinutes > 9){
				labelMinutes = new Label(timerStartMinutes.toString() + " : ");
			}
			else{
				labelMinutes = new Label("0" + timerStartMinutes.toString() + " : ");
			}
			
			if(timerStartHours > 9){
				labelHours = new Label(timerStartHours.toString() + " : ");
			}
			else{
				labelHours = new Label("0" + timerStartHours.toString() + " : ");
			}
			
			
			timerBox.setLayoutX(400);
			timerBox.setLayoutY(400);
			
			timerBox.getChildren().add(labelHours);
			timerBox.getChildren().add(labelMinutes);
			timerBox.getChildren().add(labelSeconds);
			
			group.getChildren().add(startTimer);
			group.getChildren().add(timerBox);
			stage.setScene(scene);
			stage.show();
			
			
			
			startTimer.setOnAction(new EventHandler<ActionEvent>() {

				

				

				

			

				@Override
		        public void handle(ActionEvent event) {
					timerValueSeconds = timerStartSeconds;
					timerValueMinutes = timerStartMinutes;
					timerValueHours = timerStartHours;
					timeLineSeconds = new Timeline();
				
					
					timeLineSeconds.setCycleCount(Timeline.INDEFINITE);
					timeLineSeconds.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){

						
						@Override
						public void handle(ActionEvent event) {
							timerValueSeconds--;
							
							if(timerValueSeconds > 9){
								labelSeconds.setText(timerValueSeconds.toString());
							}
							else {
							
								labelSeconds.setText("0" + timerValueSeconds.toString());
							}
							
							if(timerValueHours <= 0 && timerValueMinutes <= 0 && timerValueSeconds <= 0){
								timeLineSeconds.stop();
							}
							else if(timerValueMinutes <= 0 && timerValueSeconds <= 0){
								timerValueHours--;
								timerValueMinutes = 59;
								timerValueSeconds = 59;
								
								if(timerValueHours > 9){
									labelHours.setText(timerValueHours.toString() + " : ");
								}
								else {
								
									labelHours.setText("0" + timerValueHours.toString() + " : ");
								}
								
								labelMinutes.setText(timerValueMinutes.toString() + " : ");
								labelSeconds.setText(timerValueSeconds.toString());
							}
							else if(timerValueSeconds <= 0){
								timerValueMinutes--;
								timerValueSeconds = 60;
								
								if(timerValueMinutes > 9){
									labelMinutes.setText(timerValueMinutes.toString() + " : ");
								}
								else{
									labelMinutes.setText("0" + timerValueMinutes.toString() + " : ");
								}
							}
							
								
							
							
							
						}
					
						
					} ));
					
//				timeLineMinutes = new Timeline();
//				
//				timeLineMinutes.setCycleCount(Timeline.INDEFINITE);
//				timeLineMinutes.getKeyFrames().add(new KeyFrame(Duration.minutes(1), new EventHandler<ActionEvent>(){
//
//					
//
//					@Override
//					public void handle(ActionEvent arg0) {
//						timerValueMinutes--;
//						
//						if(timerValueMinutes > 9){
//							labelMinutes.setText(timerValueMinutes.toString() + " : ");
//						}
//						else{
//							labelMinutes.setText("0" + timerValueMinutes.toString() + " : ");
//						}
//						
//						if(timerValueMinutes == 0){
//							timerValueMinutes = 60;
//						}
//						else if(timerValueMinutes <= 0 && timerValueHours <= 0){
//							
//							timeLineMinutes.stop();
//						}
//						
//					}
//					
//				}));
//				
//				timeLineHours = new Timeline();
//				
//				timeLineHours.setCycleCount(Timeline.INDEFINITE);
//				timeLineHours.getKeyFrames().add(new KeyFrame(Duration.hours(1), new EventHandler<ActionEvent>(){
//
//					
//
//					
//
//				
//
//					@Override
//					public void handle(ActionEvent arg0) {
//						timerValueHours--;
//						
//						if(timerValueHours > 9){
//							labelHours.setText(timerValueHours.toString() + " : ");
//						}
//						else{
//							labelHours.setText(timerValueHours.toString() + " : ");
//						}
//						
//						
//						if(timerValueHours <= 0){
//							
//							timeLineHours.stop();
//						}
//						
//					}
//					
//				}));
//				
//				
//				timeLineHours.playFrom(Duration.minutes(59));
//				timeLineMinutes.playFrom(Duration.seconds(59));
				timeLineSeconds.playFromStart();
				
				
		        }
		    });
			}
		
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}

}
