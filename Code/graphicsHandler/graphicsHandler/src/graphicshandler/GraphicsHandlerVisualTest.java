package graphicshandler;

import java.util.PriorityQueue;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.stage.Stage;

public class GraphicsHandlerVisualTest extends Application {

	public PriorityQueue<ShapePoint> points;
	int width = 50, height = 50, pointLow = 0, pentagonX = 25, pentagonY = 25;
	int point1Num = 1, point2Num = 2, point3Num = 3, point4Num = 4, point5Num = 5;
	public SlideShapeFactory shapeFactory;
	public ShapePoint point1, point2, point3, point4, point5, testingPoint;
	public SlidePolygon square, pentagon;
	public SlideEllipse circle;
	private String blue = "#0000FF";
	private int startTime = 5;
	private int duration = 7;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		setupPoints();
		setupSquare();
		setupPentagon();
		
		setupCircle();
		
		Scene scene = setupScene();
		
		mainStage.setScene(scene);
		
		mainStage.show();
		
	}


	private void setupCircle() {
		
		points = new PriorityQueue<ShapePoint>();
		
		points.add(point3);
		
		shapeFactory = new SlideShapeFactory(points, 50, 50,blue,blue,startTime, duration);
		
		circle = (SlideEllipse) shapeFactory.getShape();
		
	}

	private Scene setupScene() {
		
		//Create panes to hold the test shapes
		Pane squarePane = new Pane();
		Pane pentagonPane = new Pane();
		Pane circlePane = new Pane();
		VBox vBox = new VBox();
		
		//put the shapes into their panes.
		squarePane.getChildren().add(square);
		
		pentagonPane.getChildren().add(pentagon);
		
		circlePane.getChildren().add(circle);
		
		vBox.getChildren().addAll(squarePane, pentagonPane, circlePane);
		
		Scene scene = new Scene(vBox);
		
		showShapes();
		
		return scene;
	}
	
	private void showShapes(){
		square.show();
		pentagon.show();
		circle.show();
	}
	
	private void setupPentagon() {
		
		points = new PriorityQueue<ShapePoint>();
		points.add(point1);
		points.add(point3);
		points.add(point2);
		points.add(point4);
		points.add(point5);
		
		shapeFactory = new SlideShapeFactory(points, height,width,blue,blue,startTime, duration);
		pentagon = (SlidePolygon) shapeFactory.getShape();
		
		pentagon.setStroke(Color.AQUA);
		pentagon.setStrokeType(StrokeType.OUTSIDE);
		pentagon.setStrokeWidth(5);
	}

	private void setupSquare() {
		
		points = new PriorityQueue<ShapePoint>();
		points.add(point1);
		points.add(point3);
		points.add(point2);
		points.add(point4);
		
		//Instantiate a shapeFactory with the current values
		shapeFactory = new SlideShapeFactory(points, width, height,blue,blue,startTime, duration);
		
		shapeFactory.setHeight(100);
		//get the shape that is created by the values previously given to the factory
		square = (SlidePolygon) shapeFactory.getShape();
	}

	private void setupPoints() {
		//create a square using slideShapeFactory
		point1 = new ShapePoint(pointLow,pointLow,point1Num);
		point2 = new ShapePoint(width,pointLow,point2Num);
		point3 = new ShapePoint(width,height,point3Num);
		point4 = new ShapePoint(pointLow,width,point4Num);
		point5 = new ShapePoint(pentagonX, pentagonY, point5Num);
	}

}
