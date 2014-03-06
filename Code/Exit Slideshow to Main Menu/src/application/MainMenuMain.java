/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a Main Menu
 Version : 1.0 27/2/2014
 */
package application;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainMenuMain extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane border = new BorderPane();
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		Scene scene =  new Scene(border);
		
		HBox hbox = new HBox();
        hbox.getChildren().add(new MainMenuButton().createSlide);
        hbox.setAlignment(Pos.CENTER);
        border.setBottom(hbox);
        
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
		primaryStage.setScene(scene);
		primaryStage.setFullScreen(true);
		primaryStage.show();
	}

}
