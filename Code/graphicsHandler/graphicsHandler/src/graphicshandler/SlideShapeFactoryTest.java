package graphicshandler;

import static org.junit.Assert.*;

import java.util.PriorityQueue;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

import org.junit.Before;
import org.junit.Test;

/** 
* 
* Tests the functionality of the SlideShapeFactory. 
* coheres to the Contract made with Spoon
*
* @author Matthew Wells
* @author Alasdair Munday
*
* @author [Checked By:] [Checker(s) fill here]
*
* @version [V1] [Date Created: 25/04/2014]
*/
public class SlideShapeFactoryTest {

	public PriorityQueue<ShapePoint> points;
	int width = 50, height = 50, pointLow = 0, pentagonX = 25, pentagonY = 25;
	int point1Num = 1, point2Num = 2, point3Num = 3, point4Num = 4, point5Num = 5;
	public SlideShapeFactory shapeFactory, circleFactory;
	public ShapePoint point1, point2, point3, point4, point5, testingPoint;
	public Shape square, pentagon, circle;
	public String blue = "#0000FF";
	private int startTime = 5, duration = 7;

	/**
	*Setup Class For SlideShapeFactory Test
	*<p> Date Modified: 25 Apr 2014
	*/
	@Before
	public void setUp() throws Exception{
		pointsSetup();		
		squareSetUp();
		circleSetup();
		
	}

	/**
	*Setup circles for testing in SlideShapeFactoryTest
	*[If applicable]@see [Reference URL OR Class#Method]
	*<p> Date Modified: 25 Apr 2014
	*/
	private void circleSetup() {
		PriorityQueue<ShapePoint> point = new PriorityQueue<ShapePoint>();
		point.add(point4);
		
		circleFactory = new SlideShapeFactory(point, height,width,blue,blue,startTime, duration);
		
		circle = circleFactory.getShape();
		
		
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
		
		//Instantiate a shapeFactory with the current values
		shapeFactory = new SlideShapeFactory(points, width, height, blue,blue,startTime, duration);
		
		//get the shape that is created by the values previously given to the factory
		square = shapeFactory.getShape();
	}
	
	/*..................SQUARE TESTS...............................*/

	/**
	*Test that the shape returned is a polygon when polygon criteria is entered.
	*(more than 1 point)
	*
	*<p> Date Modified: 25 Apr 2014
	*/
	@Test
	public void squareClassTest() {
		
		String shapetype = square.getClass().getName();
		// print the class of square to the console for manual test
		System.out.println("square is a: " + shapetype);
		
		//check that getShape returns a polygon using junit
		assertEquals(SlidePolygon.class, square.getClass());
	}
	
	/**
	*Tests that the Height (According to its self) of the polygon 
	*is equal to the expected height.
	*
	*<p> Date Modified: 25 Apr 2014
	*/
	@Test
	public void squareHeightTest()	{
		Bounds squareBounds = square.getBoundsInLocal();
		double squareHeight =squareBounds.getHeight();
		assertEquals(squareHeight, height, 0.0001);
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
		double[] testingPointDouble;
		
		int i = 1;
		
		while(!points.isEmpty())	{
			
			// populate testingPoint with the coordinates of the current point.
			testingPoint = points.remove();
			testingPointDouble = new double[]{testingPoint.getxCoordinate(), testingPoint.getyCoordinate()};
			
			// test the double[] returned from the factory for current point
			assertEquals(testingPointDouble, shapeFactory.getPoint(i));
			
			// Move to the next point
			i++;
		}
	}
	
	/**
	*Test that setfill in SlideShape factory receives the 
	*PWS input and changes the color of it's shape accordingly.
	*<p> Date Modified: 25 Apr 2014
	*/
	@Test
	public void fillColorTest(){
		
		//Paint for holding returned colour from class under test
		Paint squareColor;
		
		shapeFactory.setFillColor(blue);
		squareColor = shapeFactory.getFillColor();
		
		//Confirm the square is the specified colour
		assertEquals(Color.BLUE, squareColor);
	}


	
	/**
	*Test that setLineColor in SlideShape factory receives the 
	*PWS input and changes the color of its shape's outline accordingly.
	*<p> Date Modified: 25 Apr 2014
	*/
	@Test
	public void lineColorTest(){
	
		//Paint for holding returned color from class under test
		Paint squareLineColor;
		
		shapeFactory.setLineColor(blue);
		squareLineColor = shapeFactory.getLineColor();
		
		assertEquals(Color.BLUE, squareLineColor);
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
		
		shapeFactory.setWidth(newWidth);
		shapeFactory.setHeight(newHeight);
		
		Shape shape = shapeFactory.getShape();
		
		assertEquals(newWidth, shape.getBoundsInParent().getWidth(), 0.001);
		assertEquals(newHeight, shape.getBoundsInParent().getHeight(), 0.001);
	}
	
	
	/*..................Circle TESTS...............................*/
	
	@Test
	public void circleClassTest()	{
		assertEquals(SlideEllipse.class, circle.getClass());
	}
	
	@Test
	public void circleDiameterTest(){
		double circleHeight = circle.getBoundsInLocal().getHeight();
		
		assertEquals(height, circleHeight, 0.0001);
	}
	
	
}
