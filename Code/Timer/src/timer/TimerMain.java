package timer;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TimerMain extends Application{

	private Group group;
	private Scene scene;

	@Override
	public void start(Stage stage) throws Exception {
		
		group = new Group();
		scene = new Scene(group);
		
		stage.setScene(scene);
		new TimerManager(group);
		
		Button button = new Button("I am the main button");
		button.setLayoutX(600);
		button.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("I am working");
				
			}
			
		});
		
		
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
