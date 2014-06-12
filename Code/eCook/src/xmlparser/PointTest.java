/*
 * Programmer: Max
 * Created: 02/06/2014
 * Description: Simple Unit Tests for Point Class
 */

package xmlparser;

import static org.junit.Assert.*;

import org.junit.Test;

public class PointTest {
	Point point;
	
	/*
	 *  check that setting attributes works correctly
	 */
	@Test
	public void canSetAttributes() {
		// instantiate a new point class
		point = new Point();
		
		// check that everything is null to start with
		assertNull(point.num);
		assertNull(point.x);
		assertNull(point.y);
		
		// set arbitrary values
		point.setNum("7");
		point.setX("100");
		point.setY("200");
		
		// check that the fields have been set correctly
		assertEquals(7, point.num.intValue());
		assertEquals(100, point.x.intValue());
		assertEquals(200, point.y.intValue());
	}
	
	/*
	 *  check that getting attributes works correctly
	 */
	@Test
	public void canGetAttributes() {
		// instantiate a new point class
		point = new Point();

		// check that everything is null to start with
		assertNull(point.num);
		assertNull(point.x);
		assertNull(point.y);

		// set arbitrary values
		point.setNum("7");
		point.setX("100");
		point.setY("200");

		// check that the fields have been set correctly
		assertEquals(7, point.getNum().intValue());
		assertEquals(100, point.getX().intValue());
		assertEquals(200, point.getY().intValue());
	}
}