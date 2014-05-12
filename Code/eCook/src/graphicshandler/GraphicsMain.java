package graphicshandler;

import java.util.List;

import xmlparser.Point;
import eCook.SlideShow;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class GraphicsMain extends Application {

	public void start(Stage stage) throws Exception {
		SlideShow parent;
		List<Point> points;

		stage.setTitle("Drawing Operations Test");
        Group root = new Group();
        GraphicsHandler graphicsHandler =  new GraphicsHandler(1, 10, 10, 0, 2, 0, null, null, null,);
        root.getChildren().add(graphicsHandler.graphicsBox);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
}

	public static void main(String[] args) {
		Application.launch(args);
    }

}