package media;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import xmlparser.Point;
import eCook.SlideShow;

public class GraphicHandler extends SubSlideMedia{

	public GraphicHandler(SlideShow parent, int totalPoints, int width, int height, Integer startTime, Integer duration,
							String fillColour, String lineColour, Integer branchID, List<Point> points) {
		super(parent, 0, 0, startTime, duration, branchID, null);
		
		//Get the size of the screen
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		
		Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		//Set the fill colour
		graphicsContext.setFill(Color.web(fillColour));
		
		//Set the line colour
		graphicsContext.setStroke(Color.web(lineColour));
		
		drawGraphic(graphicsContext, totalPoints, points, width, height);
		
		//Add the canvas into a HBox
		hbox.getChildren().add(canvas);
		
		setTimingValues();
	}
	
	private GraphicsContext drawGraphic(GraphicsContext graphicsContext, int totalPoints, List<Point> points, int width, int height) {
		Point point;
				//Circle
				if(totalPoints == 1){
					point = points.get(0);
					graphicsContext.fillOval(point.getX(), point.getY(), width, height);
					graphicsContext.strokeOval(point.getX(), point.getY(), width, height);
				}
				// 2 or more points shapes
				else{
					// Declare the size of X and Y coordinates Arrays based on the totalPoints
					double[] xCoordinates = new double[points.size()];
					double[] yCoordinates = new double[points.size()];
				
					for (int i = 0 ; i < points.size(); i++){
						//Retrieve the points in sequence
						point = points.get(i);
						//Populate the appropriate array
						xCoordinates [i] = point.getX();
						yCoordinates [i] = point.getY();
					}
					//Draw the Shape based on the coordinates given
					graphicsContext.strokePolygon(xCoordinates, yCoordinates, points.size());
					graphicsContext.fillPolygon(xCoordinates, yCoordinates, points.size());
				}
				
				return graphicsContext;
	}
}
