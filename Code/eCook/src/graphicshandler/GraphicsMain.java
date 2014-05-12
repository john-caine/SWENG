package graphicshandler;

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
		stage.setTitle("Drawing Operations Test");
        Group root = new Group();
//        Canvas canvas = new Canvas(300,250);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        drawShapes(gc);
        GraphicsHandler graphicsHandler =  new GraphicsHandler(12, 150, 150, null, 2, null, null, "#7FFFD4", null);
        root.getChildren().add(graphicsHandler.graphicsBox);
        stage.setScene(new Scene(root, Color.BLACK));
        stage.show();
}

	public static void main(String[] args) {
		Application.launch(args);
    }

	private void drawShapes(GraphicsContext gc) {
	    gc.setFill(Color.GREEN);
	    gc.setStroke(Color.BLUE);
	    gc.setLineWidth(5);
	    gc.strokeLine(40, 10, 10, 40); // xStart, yStart and  xEnd and yEnd
	    gc.fillOval(10, 60, 30, 30);   // With fill inside
	    gc.strokeOval(60, 60, 30, 30); // Without fill inside
	    gc.fillRoundRect(110, 60, 30, 30, 10, 10);
	    gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
	    gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
	    gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
	    gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
	    gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
	    gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
	    gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
	    gc.fillPolygon(new double[]{10, 40, 10, 40},
	                   new double[]{210, 210, 240, 240}, 4); 
	    gc.strokePolygon(new double[]{60, 90, 60, 90},
	                     new double[]{210, 210, 240, 240}, 4);
	    gc.strokePolyline(new double[]{110, 140, 110, 140},
	                      new double[]{210, 210, 240, 240}, 4);
	}
}