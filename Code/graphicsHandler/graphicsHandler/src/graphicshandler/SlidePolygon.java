/**
* SmartTrolley
*
* A DESCRIPTION OF THE FILE
*
* @author Name1
* @author Name2
*
* @author Checked By: Checker(s) fill here
*
* @version version of this file [Date Created: 1 May 2014]
*/

/*YOUR CODE HERE*/

/**************End of SlidePolygon.java**************/
package graphicshandler;

import java.util.PriorityQueue;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/** 
 * Workspace_Name
 * 
 * A DESCRIPTION OF THE CLASS
 *
 * @author Matthew Wells
 * @author Alasdair Munday
 *
 * @author [Checked By:] [Checker(s) fill here]
 *
 * @version [version of this class] [Date Created: DD/MM/YY]
 */

public class SlidePolygon extends Polygon implements SlideElement{

	private SlideElementDuration duration;

	/**
	 * @param points
	 */
	public SlidePolygon(PriorityQueue<ShapePoint> points) {
		extractCoordinates(points);
		
		duration = new SlideElementDuration(this);
		setVisible(false);
		}
	
	/**
	 *Method/Test Description
	 *<p>Test(s)/User Story that it satisfies
	 *@param points
	 *@return
	 *[If applicable]@see [Reference URL OR Class#Method]
	 *<p> Date Modified: 28 Apr 2014
	 */
	private void extractCoordinates(PriorityQueue<ShapePoint> points) {

		double yCoordinate;
		double xCoordinate;


		while (!points.isEmpty()) {
			ShapePoint currentPoint = points.remove();

			// get the x coordinate from the current point
			xCoordinate = currentPoint.getxCoordinate();

			getPoints().add(xCoordinate);

			// get the y coordinate from the current point
			yCoordinate = currentPoint.getyCoordinate();

			getPoints().add(yCoordinate);

		}
	}
	
	/**
	 * @see graphicsHandler.SlideElement#setDuration(int)
	 */

	public void setDuration(int seconds) {
		int milliseconds;
		milliseconds = seconds*1000;
		duration.setDuration(milliseconds);
	}

	/* (non-Javadoc)
	 * @see graphicsHandler.SlideElement#show()
	 */
	
	public void show() {
		duration.show();
	}

	/* (non-Javadoc)
	 * @see graphicsHandler.SlideElement#setHeight(int)
	 */
	
	public void setHeight(int newHeight) {
		
		double inherentHeight = super.getBoundsInLocal().getHeight();

		//calculate the ratio of inherent Height to current Height
		double scaler = newHeight/inherentHeight;

		//scale by this ratio
		this.setScaleY(scaler);

	}

	/* (non-Javadoc)
	 * @see graphicsHandler.SlideElement#setWidth(int)
	 */

	public void setWidth(int newWidth) {

		// create local shape to access polygon parameters
		double inherentWidth = super.getBoundsInLocal().getWidth();

		//calculate the ratio of new width to inherent width
		double scaler = newWidth/inherentWidth;

		//scale by this ratio
		this.setScaleX(scaler);
		
	}

	/* (non-Javadoc)
	 * @see graphicsHandler.SlideElement#setStartTime(int)
	 */
	
	public void setStartTime(int seconds) {
		int milliseconds;
		milliseconds = seconds * 1000;
		duration.setStartTime(milliseconds);
	}

	/**
	*Method/Test Description
	*<p>Test(s)/User Story that it satisfies
	*@param fillColor
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 1 May 2014
	*/
	public void setFillColor(String fillColor) {
		this.setFill(Color.web(fillColor));
		
	}

	/**
	*Method/Test Description
	*<p>Test(s)/User Story that it satisfies
	*@return
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 1 May 2014
	*/
	public Paint getFillColor() {
		return this.getFill();
	}

	/**
	*Method/Test Description
	*<p>Test(s)/User Story that it satisfies
	*@param lineColor
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 1 May 2014
	*/
	public void setLineColor(String lineColor) {
		this.setStroke(Color.web(lineColor));
		
	}

	/**
	*Method/Test Description
	*<p>Test(s)/User Story that it satisfies
	*@return
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 1 May 2014
	*/
	public Paint getLineColor() {
		
		return this.getStroke();
	}

}
