/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 09/05/2014
 * Junit Test for Graphics Handler 
 */
package graphicshandler;

import static org.junit.Assert.*;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import eCook.SlideShow;
import gui.JavaFXThreadingRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Shape;
import xmlparser.XMLReader;

public class GraphicsHandlerTest {

	private GraphicsHandler graphicsHandler;
	private SlideShow parent;
	private XMLReader reader;
	private List<Shape> shapeList;
	String fillColour = "#FFFFFF";
	String lineColour = "#000000";
	private GraphicsContext graphicsContext;
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	//Read XML playlist and create a new graphics object based on the first graphics found
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("../Resources/PWSExamplePlaylist_3.xml");
		shapeList = reader.getRecipe().getSlide(1).getContent().getShapes();
		graphicsHandler = new GraphicsHandler(parent, shapeList.get(0).getTotalPoints(), shapeList.get(0).getWidth(),
											  shapeList.get(0).getHeight(), shapeList.get(0).getStartTime(), 
											  shapeList.get(0).getDuration(), shapeList.get(0).getLayer(),
											  fillColour, lineColour, null/*branch*/, shapeList.get(0).getPoints());
	}

	@Test
	public void graphicsHandlerInstanceOfHBox() {
		assertTrue(graphicsHandler.graphicsBox instanceof HBox);
	}
	
	/* HBox contains the Graphic Canvas */
	@Test
	public void hboxInstanceOfCanvas() {
		assertTrue(graphicsHandler.graphicsBox.getChildren().get(0) instanceof Canvas);
		
		/* Add the Canvas to GrapicsContext2D */
		Canvas canvas  = (Canvas) graphicsHandler.graphicsBox.getChildren().get(0);
		graphicsContext = canvas.getGraphicsContext2D();
		
		/* Test that the HBox containing the Canvas is visible */
		assertTrue(canvas.isVisible());
		
		/* Test Fill Colour */
		assertEquals("0xffffffff", graphicsContext.getFill().toString());
		/* Test line Colour */
		assertEquals("0x000000ff", graphicsContext.getStroke().toString());
		
		/* Visual Inspection of the Shapes have to be done as there is no method to retrieve the location
		 * of the GraphicsContext. 
		 */
		
	}
}
