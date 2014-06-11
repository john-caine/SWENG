/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: Graphics Handler, Creates a graphics object which appears on a screen sized Canvas.
 */
package media;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import xmlparser.Point;
import eCook.SlideShow;
import eCook.eCook;

public class GraphicHandler extends SubSlideMedia{

	private Logger logger;

	/*
	 * Graphics Handler Constructor
	 * @Param parent: The slideshow class which has called the constructor
	 * @Param totalPoints: Total of number of points in the shape 
	 * @Param width: The width of the shape
	 * @Param height: The height of the shape
	 * @Param startTime: The time the shape is to appear on the slide
	 * @Param duration: The time to shape is to disappear from the slide
	 * @Param fillColour: The colour of shape
	 * @Param lineColour: The colour of the outline of the shape
	 * @Param branchID: The branch ID of the object
	 * @Param points: A list of the points in the shape
	 */
	
	public GraphicHandler(SlideShow parent, int totalPoints, int width, int height, Integer startTime, Integer duration,
							String fillColour, String lineColour, Integer branchID, List<Point> points) {
		super(parent, 0, 0, startTime, duration, branchID, null);
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		//Get the size of the screen
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		
		Canvas canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
		GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
		
		//Set the fill colour
		graphicsContext.setFill(Color.web(fillColour));
		
		//Set the line colour
		graphicsContext.setStroke(Color.web(lineColour));
		
		drawGraphic(graphicsContext, totalPoints, points, width, height);
		logger.log(Level.INFO, "Graphics Object attributes set");
		//Add the canvas into a HBox
		hbox.getChildren().add(canvas);
		
		setTimingValues();
	}
	
	/*
	 * Sets the graphics context attributes
	 * @Param graphicsContext: The graphics object which is to be set.
	 * @Param totalPoints: The total number of points in the object.
	 * @Param points: The list containing the point information.
	 * @Param width: The width of the graphics object
	 * @Param height: The height of the graphics object
	 * @Return graphicsContext: The graphics context with attributes set.
	 */
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
