package graphicshandler;



/** 
* 
* PWS compliant Points for drawing graphics
*
* @author Matthew Wells
* @author Alasdair Munday
*
* @author [Checked By:] [Checker(s) fill here]
*
* @version [v0.1] [Date Created: 25/04/2014]
*/
public class ShapePoint implements Comparable<ShapePoint> {
	
	private int xCoordinate;
	private int yCoordinate;
	private int pointNumber;
	
	/**
	 * @param xCoordinate
	 * @param yCoordinate
	 * @param pointNumber
	 */
	public ShapePoint(int xCoordinate, int yCoordinate, int pointNumber) {
		this.pointNumber = pointNumber;
		this.setxCoordinate(xCoordinate);
		this.setyCoordinate(yCoordinate);
	}
	
	
	//ShapePoints should be organised by pointNumber when used to draw a shape
	@Override
	public int compareTo(ShapePoint pointToCompare)	{
		return Double.compare(pointToCompare.pointNumber, pointNumber);
	}

	/**
	*Return the x coordinate of the point
	*<p>Calling Class returns X coordinate
	*@return xCoordinate
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 25 Apr 2014
	*/
	public int getxCoordinate() {
		return xCoordinate;
	}

	/**
	*Set the X coordinate of the point
	*<p>C
	*@param xCoordinate
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 25 Apr 2014
	*/
	public void setxCoordinate(int xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public int getyCoordinate() {
		return yCoordinate;
	}

	public void setyCoordinate(int yCoordinate) {
		this.yCoordinate = yCoordinate;
	}
	
}
