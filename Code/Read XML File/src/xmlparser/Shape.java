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
 *  
 */

public class Shape extends Content {
	private int totalPoints;
	private List<int[]> points;
	private String fillColor, lineColor;
	
	public Shape() {
		super();
		points = new ArrayList<int[]>();
	}

	// getters
	public int getTotalPoints() {
		return totalPoints;
	}

	public String getFillColor() {
		return fillColor;
	}
	
	public String getLineColor() {
		return lineColor;
	}
	
	// setters
	public void setTotalPoints(Object totalPoints) {
		this.totalPoints = Integer.valueOf((String) totalPoints);
	}

	public void setLineColor(Object lineColor) {
		this.lineColor = (String) lineColor;
	}
	
	public void setFillColor(Object fillColor) {
		this.fillColor = (String) fillColor;
	}
	
	// list operations
	public List<int[]> getPoints() {
		return points;
	}

	public int[] getPoint(int pointNumber) {
		return points.get(pointNumber);
	}

	public void addPoint(int[] point) {
		// point has this format:
		/*
		 * num = point[0]
		 * x = point[1]
		 * y = point[2]
		 */
		points.add(point);
	}

	public int getNumberOfPoints() {
		return points.size();
	}
}
