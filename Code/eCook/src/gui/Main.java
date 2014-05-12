package gui;



import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

	Rectangle2D screenBounds;
	
public static void main(String[] args) {
    launch(args);
}
 

public void start(Stage primaryStage) {
		
		try {
			screenBounds = Screen.getPrimary().getVisualBounds();
			Group root = new Group();
			Scene scene = new Scene(root, screenBounds.getWidth(), screenBounds.getHeight());
			root.getChildren().add(new MainMenuContent().bigBox);
			scene.setFill(Color.WHITE);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED);
			primaryStage.setFullScreen(true);
			primaryStage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}

