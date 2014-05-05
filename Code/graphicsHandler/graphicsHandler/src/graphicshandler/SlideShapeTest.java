/**
 * SmartTrolley
 *
 * A DESCRIPTION OF THE FILE
 *
 * @author Alasdair Munday
 * @author Matt Wells
 *
 * @author Checked By: Checker(s) fill here
 *
 * @version version of this file [Date Created: 1 May 2014]
 */


package graphicshandler;

import static org.junit.Assert.*;

import java.util.PriorityQueue;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import org.junit.Before;
import org.junit.Test;

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

public class SlideShapeTest {

	public PriorityQueue<ShapePoint> points;
	int width = 50, height = 50, pointLow = 0, pentagonX = 25, pentagonY = 25;
	int point1Num = 1, point2Num = 2, point3Num = 3, point4Num = 4, point5Num = 5;
	public ShapePoint point1, point2, point3, point4, point5, testingPoint;
	public SlidePolygon square; 
	public SlideEllipse circle;
	public String fillColor = "#0000FF", lineColor = "#FF0000";
	int duration = 5;
	int startTime = 5;

	@Before
	public void setup(){
		pointsSetup();
		squareSetUp();
		square = new SlidePolygon(points);
		circle = new SlideEllipse(point3, width, height);
	}

	/**
	 *setup points for drawing polygons in SlideShapeFactoryTest
	 *<p> Date Modified: 25 Apr 2014
	 */
	private void pointsSetup() {
		point1 = new ShapePoint(pointLow,pointLow,point1Num);
		point2 = new ShapePoint(width,pointLow,point2Num);
		point3 = new ShapePoint(width,height,point3Num);
		point4 = new ShapePoint(pointLow,width,point4Num);
		point5 = new ShapePoint(pentagonX, pentagonY, point5Num);

		//priority queue orders the points by point number
		points = new PriorityQueue<ShapePoint>();
	}


	/**
	 *Setup Squares for testing in SlideShapeFactoryTest
	 *<p> Date Modified: 25 Apr 2014
	 */
	private void squareSetUp() {
		//set points with the first 4 ShapePoints to create a square
		points.add(point1);
		points.add(point3);
		points.add(point2);
		points.add(point4);

	}

	/**
	 *Tests that the points in the list passed to the factory
	 *correspond to the points the factory will use.
	 *
	 *The factory should internaly convert ShapePoints to 2 element arrays of 
	 *Doubles.
	 *
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void squarePointsTest()	{
		// double array to hold the x and y values contained in the shape points
		double[] testingPointDouble, squarePointDouble;

		int i = 1;

		while(!points.isEmpty())	{

			// populate testingPoint with the coordinates of the current point.
			testingPoint = points.remove();
			testingPointDouble = new double[]{testingPoint.getxCoordinate(), testingPoint.getyCoordinate()};

			squarePointDouble = new double[]{square.getPoints().get(i*2).doubleValue(),square.getPoints().get(i*2+1).doubleValue()};

			// test the double[] returned from the factory for current point
			assertEquals(testingPointDouble, squarePointDouble);

			// Move to the next point
			i++;
		}
	}
	
	@Test
	public void circlePointTest()	{
		//Test that the point passed in is the centre point of the circle
		assertEquals(circle.getCenterX(), point3.getxCoordinate(), 0.0001);
	}
	

	/**
	 *Tests that the Height (According to its self) of the polygon 
	 *is equal to the expected height.
	 *
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void squareHeightWidthTest()	{
		Bounds squareBounds = square.getBoundsInLocal();
		double squareHeight =squareBounds.getHeight();
		assertEquals(squareHeight, height, 0.0001);
		
		double squareWidth = squareBounds.getWidth();
		assertEquals(squareWidth, width, 0.0001);
	}
	
	/**
	 *Tests that the Height (According to its self) of the polygon 
	 *is equal to the expected height.
	 *
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void circleHeightWidthTest()	{
		Bounds circleBounds = circle.getBoundsInLocal();
		
		double circleHeight =circleBounds.getHeight();
		assertEquals(circleHeight, height, 0.0001);
		
		double circleWidth = circleBounds.getWidth();
		assertEquals(circleWidth, width, 0.0001);
	}

	/**
	 *Test that setfill in SlideShape factory receives the 
	 *PWS input and changes the color of it's shape accordingly.
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void polygonFillColorTest(){

		//Paint for holding returned colour from class under test
		Paint squareColor;

		square.setFillColor(fillColor);
		squareColor = square.getFillColor();

		//Confirm the square is the specified colour
		assertEquals(Color.BLUE, squareColor);
	}
	
	@Test
	public void circleFillColorTest(){

		//Paint for holding returned colour from class under test
		Paint circleColor;

		circle.setFillColor(fillColor);
		circleColor = circle.getFillColor();

		//Confirm the square is the specified colour
		assertEquals(Color.BLUE, circleColor);
	}
	
	@Test
	public void polygonLineColorTest(){

		//Paint for holding returned color from class under test
		Paint squareLineColor;

		square.setLineColor(lineColor);
		squareLineColor = square.getLineColor();

		assertEquals(Color.RED, squareLineColor);
	}

	/**
	 *Test that setLineColor in SlideShape factory receives the 
	 *PWS input and changes the color of its shape's outline accordingly.
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void circleLineColorTest(){

		//Paint for holding returned color from class under test
		Paint circleLineColor;

		circle.setLineColor(lineColor);
		circleLineColor = circle.getLineColor();

		assertEquals(Color.RED, circleLineColor);
	}

	/**
	 *Test that setWidth and setHeight correctly change the width and height of
	 *the space the shape occupies in its parent.
	 *<p> Date Modified: 25 Apr 2014
	 */
	@Test
	public void polygonResizeTest(){

		int extra = 8;

		int newWidth = width + extra;
		int newHeight = height + extra;

		square.setWidth(newWidth);
		square.setHeight(newHeight);


		assertEquals(newWidth, square.getBoundsInParent().getWidth(), 0.001);
		assertEquals(newHeight, square.getBoundsInParent().getHeight(), 0.001);
	}
	
	@Test
	public void circleResizeTest(){

		int extra = 8;

		int newWidth = width + extra;
		int newHeight = height + extra;

		circle.setWidth(newWidth);
		circle.setHeight(newHeight);


		assertEquals(newWidth, circle.getBoundsInParent().getWidth(), 0.001);
		assertEquals(newHeight, circle.getBoundsInParent().getHeight(), 0.001);
	}

	
	@Test
	public void polygonDurationTest(){

		square.setStartTime(startTime);
		square.setDuration(duration);
		
		square.show();
		
		assertFalse(square.isVisible());
		
		// sleep for a little longer than start time
		try {
			Thread.sleep(startTime*1001);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//shape should have appeared
		assertTrue(square.isVisible());

		// sleep for a little longer than duration
		try {
			Thread.sleep(duration*1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// shape should have disappeared
		assertFalse(square.isVisible());		
	}
		
	@Test
	public void circleDurationTest(){

		circle.setStartTime(startTime);
		circle.setDuration(duration);
		
		circle.show();
		
		assertFalse(circle.isVisible());
		
		// sleep for a little longer than start time
		try {
			Thread.sleep(startTime*1001);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//shape should have appeared
		assertTrue(circle.isVisible());

		// sleep for a little longer than duration
		try {
			Thread.sleep(duration*1001);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// shape should have disappeared
		assertFalse(circle.isVisible());		
	}
	
	
	@Test
	public void polygonZeroDurationTest(){
		square.setDuration(0);
		square.show();
		
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(square.isVisible());
	}
	
	@Test
	public void circleZeroDurationTest(){
		circle.setDuration(0);
		circle.show();
		
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		assertTrue(circle.isVisible());
	}
	
}

/**************End of SlideShapeTest.java**************/
