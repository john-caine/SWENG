import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Frame extends Application{

	
	public static void main(String[]args){
		launch(args);
	}

	@Override
	public void start(Stage primarystage) throws Exception {
		
		//Define title of frame
		primarystage.setTitle("Display Window");
		
		//root-container
		Group root = new Group();
		
		//Define the size of frame
		Scene scene = new Scene(root,500,500,Color.GREY);
		
		//Displays frame on screen 
		primarystage.setScene(scene);
		primarystage.show();
		
	}

}
