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
* @version version of this file [Date Created: 25 Apr 2014]
*/

/*YOUR CODE HERE*/

/**************End of ShapePointTest.java**************/
package graphicshandler;

import static org.junit.Assert.*;

import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

/** 
 * Workspace_Name
 * 
 * Tests for the ShapePoint Class, 
 * Used as an intermediary between PWS points and the Graphics Handler
 *
 * @author Matthew Wells
 * @author Alasdair Munday
 *
 * @author [Checked By:] [Checker(s) fill here]
 *
 * @version [v1] [Date Created: 10/03/14]
 */

public class ShapePointTest {

	ShapePoint point, point2, point3;
	int x = 1, y=2, pointNumber = 3;
	PriorityQueue<ShapePoint> points;
	
	/**
	*Setup the point before each test
	*<p>required for all ShapePointTests
	*
	*<p> Date Modified: 25 Apr 2014
	*/
	@Before
	public void setup(){
		
		//setup a new point with test values
		point = new ShapePoint(x,y,pointNumber);
		
	}
	
	@Test
	public void getCoordinatesTest() {
		
		int x = point.getxCoordinate();
		int y = point.getyCoordinate();
		
		assertEquals(this.x , x);
		assertEquals(this.y , y);
		
	}
	
	/**
	*Test that the shape points implement comparable correctly (largest point first)
	*
	*<p> Date Modified: 25 Apr 2014
	*/
	@Test
	public void orderPointsTest(){
		
		setupUnorderedTestPoints();
		
		//return points from list in order
		ShapePoint testPoint = points.remove(),
				testPoint2 = points.remove();

		assertEquals(testPoint, point3);
		assertEquals(testPoint2,point2);
		
	}
	
	/**
	*Inserts points out of order into a priority queue to make sure they are comparable
	*<p>OrderPointsTest
	*
	*<p> Date Modified: 25 Apr 2014
	*/
	private void setupUnorderedTestPoints() {
		
		//Priority queue to hold and sort points
		points = new PriorityQueue<ShapePoint>();
		
		//add next2 points with increased point number
		pointNumber++;
		point2 = new ShapePoint(x, y, pointNumber);
		pointNumber++;
		point3 = new ShapePoint(x, y, pointNumber);
		
		// add points to list out of order
		points.add(point3);
		points.add(point);
		points.add(point2);
	}
	

}
