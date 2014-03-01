/*
 Programmers : Roger & James & Paul
 Date created: 26/2/2014
 Description: Create a Slide
 Version : 1.0 26/2/2014
 */
package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class MainMenu extends Application {
	@Override
	public void start(Stage primaryStage) {
		new Slide();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
