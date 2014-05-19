package xmlparser;
import java.util.ArrayList;
import java.util.List;

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
 * Version History: v1.1 (01/04/14) - Changed int fields to Integers (including list of <Integer>).
 * 									- Added validation to getting and setting lists.
 * 					v1.2 (06/04/14) - Changed points list to hold instances of new Point class.
 * 									- Updated setters and getters accordingly.
 * 					v1.3 (10/04/14) - Changed type of totalPoints from Integer to int as this is compulsory.
 * 									- Added overriding methods to set ints for width and height instead of Integer.
 */

public class Shape extends Content {
	private Integer totalPoints;
	private List<Point> points;
	private String fillColor, lineColor;
	
	public Shape() {
		super();
		points = new ArrayList<Point>();
	}

	// getters
	public Integer getTotalPoints() {
		return totalPoints;
	}

	public String getFillColor() {
		return fillColor;
	}
	
	public String getLineColor() {
		return lineColor;
	}
	
	@Override
	public Integer getWidth() {
		return width.intValue();
	}
	
	@Override
	public Integer getHeight() {
		return height.intValue();
	}
	
	// setters
	public void setTotalPoints(Object totalPoints) {
		if (totalPoints != null) {
			this.totalPoints = Integer.valueOf((String) totalPoints);
		}
		else {
			this.totalPoints = null;
		}
	}

	public void setLineColor(Object lineColor) {
		this.lineColor = (String) lineColor;
	}
	
	public void setFillColor(Object fillColor) {
		this.fillColor = (String) fillColor;
	}
	
	@Override
	public void setWidth(Object width) {
		if (width != null) {
			this.width = Integer.valueOf((String) width).intValue();
		}
		else {
			this.width = null;
		}
	}
	
	@Override
	public void setHeight(Object height) {
		if (height != null) {
			this.height = Integer.valueOf((String) height).intValue();
		}
		else {
			this.height = null;
		}
	}
	
	// list operations
	public List<Point> getPoints() {
		return points;
	}

	public Point getPoint(int pointNumber) {
		if (pointNumber >= 0 && pointNumber < this.getNumberOfPoints()) {
			return points.get(pointNumber);
		}
		else {
			reportError("Error getting point: index out of range");
			return null;
		}
	}

	public void addPoint(Point point) {
		if (point != null) {
			points.add(point);
		}
		else {
			reportError("Error adding point: object received from parser is null");
		}
	}

	public int getNumberOfPoints() {
		return points.size();
	}
}
