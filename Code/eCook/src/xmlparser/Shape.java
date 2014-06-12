package xmlparser;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;

/* Title: Shape
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with slide shapes.
 * 				Extends common methods and variables from Content class.
 * 				Methods are provided for 'setting' and 'getting' unique fields for this class.
 *
 */

public class Shape extends Content {
	// declare variables
	private Integer totalPoints = null;
	private List<Point> points;
	private String fillColor, lineColor = "black";
	private Logger logger;
	
	/**
	 *  constructor
	 */
	public Shape() {
		super();
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());

		points = new ArrayList<Point>();
	}

	// getters
	/**
	 * Get the total number of points in the shape
	 * @return totalPoints: Number of shape points
	 */
	public Integer getTotalPoints() {
		return totalPoints;
	}

	/**
	 * Get the fill colour of the shape
	 * @return fillColour: Shape fill colour 
	 */
	public String getFillColor() {
		return fillColor;
	}
	
	/**
	 * Get the Shape line colour
	 * @return lineColour: Shape line colour
	 */
	public String getLineColor() {
		return lineColor;
	}
	
	// setters
	/**
	 * Set the total number of points in the shape
	 * @param totalPoints: Number of points in the shape
	 */
	public void setTotalPoints(Object totalPoints) {
		if (totalPoints != null) {
			this.totalPoints = Integer.valueOf((String) totalPoints);
		}
	}

	/**
	 * Set the line colour of the shape
	 * @param lineColor: The shape line colour
	 */
	public void setLineColor(Object lineColor) {
		if (lineColor != null) {
			this.lineColor = (String) lineColor;
		}
	}
	
	/**
	 * Set the fill colour of the shape
	 * @param fillColor: The fill colour of the shape
	 */
	public void setFillColor(Object fillColor) {
		if (fillColor != null) {
			this.fillColor = (String) fillColor;
		}
	}
	
	// list operations
	/**
	 * Get the list of point objects
	 * @return points: List of points in the shape
	 */
	public List<Point> getPoints() {
		return points;
	}

	/**
	 * Get a point at an index of the Point List
	 * @param pointNumber: The Point List index
	 * @return The Point at index pointNumber
	 */
	public Point getPoint(int pointNumber) {
		if (pointNumber >= 0 && pointNumber < this.getNumberOfPoints()) {
			return points.get(pointNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting point: index out of range");
			return null;
		}
	}

	/**
	 * Add a point to the Point List
	 * @param point: The point object to be added
	 */
	public void addPoint(Point point) {
		if (point != null) {
			points.add(point);
		}
		else {
			logger.log(Level.SEVERE, "Error adding point: object received from parser is null");
		}
	}

	/**
	 * Get the total number of points in the shape
	 * @return The size of the Point List
	 * 
	 */
	public int getNumberOfPoints() {
		return points.size();
	}
}
