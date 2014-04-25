package timer;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
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
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.show();
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Application.launch(args);
	}

}
