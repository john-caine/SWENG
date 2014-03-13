package texthandler;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextMain extends Application {

	private TextHandler text_object;
	private String inputString = "Times New Roman";
	private String font = "Times New Roman";
	private Integer x_start = 100;
	private Integer y_start = 600;
	private Integer fontsize;
	private String fontcolor;
	private String linecolor;
	private Integer x_end;
	private Integer startTime;
	private Integer duration;
	
	

	@Override
	public void start(Stage stage) throws Exception {
		text_object = new TextHandler(inputString, font, x_start, y_start, fontsize, fontcolor, linecolor, x_end,startTime, duration);
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
