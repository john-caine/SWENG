package texthandler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextMain extends Application {

	private TextHandler text_object;
	private String input = "Times New Roman";
	private String font = "Times New Roman";
	private int x_start = 100;
	private int y_start = 600;
	
	

	@Override
	public void start(Stage stage) throws Exception {
		text_object = new TextHandler(input, font, x_start, y_start);
		Group root = new Group();
		root.getChildren().add(text_object.textBox);
		Scene scene = new Scene(root);
		stage.setFullScreen(true);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
		
	}
	
	public static void main(String[] args) {
        Application.launch(args);
    }

}
