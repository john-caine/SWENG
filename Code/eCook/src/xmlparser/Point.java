package xmlparser;

/* Title: Point
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 06/04/14
 * 
 * Description: A class to contain all information associated with shape points.
 * 				Methods are provided for 'setting' and 'getting' fields for this class.
 */

public class Point {
	Integer num, x, y; 

	//getters
	/**
	 * Get the num
	 * @return num: get number of points to make graphics object
	 */
	
	public Integer getNum() {
		return num;
	}

	/**
	 * Get X
	 * @return x: get the X value
	 */
	public Integer getX() {
		return x;
	}
	
	/**
	 * Get Y
	 * @return y: get the Y value
	 */
	public Integer getY() {
		return y;
	}
	
	// setters
	/**
	 * Sets the x location of first point 
	 * @param num: The number of points
	 */
	public void setNum(Object num) {
		if (num != null) {
			this.num = Integer.valueOf((String) num);
		}
	}

	/**
	 * Sets the y location of first point
	 * @param x: x coordinate
	 */
	public void setX(Object x) {
		if (x != null) {
			this.x = Integer.valueOf((String) x);
		}
	}
	
	/**
	 * Sets Y
	 * @param y: y coordinate
	 */
	public void setY(Object y) {
		if (y != null) {
			this.y = Integer.valueOf((String) y);
		}
	}
}
