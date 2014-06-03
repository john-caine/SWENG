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
	
	public Point() {
	}

	// getters
	public Integer getNum() {
		return num;
	}

	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}
	
	// setters
	public void setNum(Object num) {
		if (num != null) {
			this.num = Integer.valueOf((String) num);
		}
	}

	public void setX(Object x) {
		if (x != null) {
			this.x = Integer.valueOf((String) x);
		}
	}
	
	public void setY(Object y) {
		if (y != null) {
			this.y = Integer.valueOf((String) y);
		}
	}
}
