package texthandler;

import eCook.SlideShow;
import xmlparser.TextString;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TextMain extends Application {

	private TextHandler text_object;
	private String inputString = "Times New Roman";

	private TextString textString;
	private SlideShow parent;
	
	

	@Override
	public void start(Stage stage) throws Exception {
		textString = new TextString();
		textString.setText(inputString);
		textString.setBold(true);
		textString.setItalic(true);
		text_object = new TextHandler(parent, textString,"Times New Roman",100, 600, 20, "#00FF00", "#0000FF", 40, 5, 10, null, null, null);
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
