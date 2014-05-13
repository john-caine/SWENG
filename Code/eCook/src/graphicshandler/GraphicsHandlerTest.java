package graphicshandler;

import static org.junit.Assert.*;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Polygon;
import eCook.SlideShow;
import gui.JavaFXThreadingRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Point;
import xmlparser.Shape;
import xmlparser.TextBody;
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
		reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
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
		Canvas canvas  = (Canvas) graphicsHandler.graphicsBox.getChildren().get(0);
		graphicsContext = canvas.getGraphicsContext2D();
		/* Test Fill Colour */
		assertEquals("0xffffffff", graphicsContext.getFill().toString());
		/* Test line Colour */
		assertEquals("0x000000ff", graphicsContext.getStroke().toString());
		//assertTrue(graphicsContext. instanceof Polygon);
		
	}
}
