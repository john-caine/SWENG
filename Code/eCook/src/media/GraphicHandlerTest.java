/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: Graphics Handler test class
 */
package media;

import static org.junit.Assert.*;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.stage.Screen;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import xmlparser.Shape;
import xmlparser.XMLReader;
import eCook.SlideShow;
import eCook.JavaFXThreadingRule;


public class GraphicHandlerTest {
	private GraphicHandler graphicHandler;
	private SlideShow parent;
	private Rectangle2D bounds;
	private XMLReader reader;
	private List<Shape> shapeList;
	private String fillColour = "#FFFFFF";
	private String lineColour = "#000000";
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	

	@Before
	public void SetUp(){
		//Parse a playlist
		reader = new XMLReader("../Resources/PWSExamplePlaylist_4.xml");
		shapeList = reader.getRecipe().getSlide(1).getContent().getShapes();
		graphicHandler = new GraphicHandler(parent, shapeList.get(0).getTotalPoints(), shapeList.get(0).getWidth(),
											  shapeList.get(0).getHeight(), shapeList.get(0).getStartTime(), 
											  shapeList.get(0).getDuration(),
											  fillColour, lineColour, null/*branch*/, shapeList.get(0).getPoints());
		
		bounds = Screen.getPrimary().getBounds();
		
	}
	

	/*
	 * Tests that the Hbox contains a Canvas
	 */
	@Test
	public void hBoxInstanceOfCanvas() {	
		assertTrue(graphicHandler.getHbox().getChildren().get(0) instanceof Canvas);
	}
	
	/*
	 * Test the Canvas is equal to the size of the screen
	 */
	@Test
	public void canvasSizeOfScene(){
		assertEquals(bounds.getHeight(), graphicHandler.getHbox().getChildren().get(0).getLayoutBounds().getHeight(),0.1);
		assertEquals(bounds.getWidth(), graphicHandler.getHbox().getChildren().get(0).getLayoutBounds().getWidth(),0.1);
	}
	
	/*
	 * Test the created graphic object has the correct colour
	 */
	@Test
	public void graphicFillColour(){
		assertEquals("0xffffffff",((Canvas) graphicHandler.getHbox().getChildren().get(0)).getGraphicsContext2D().getFill().toString());
	}
	
	/*
	 * Test the created graphic has the correct line colour
	 */
	@Test
	public void graphicLineColour(){
		assertEquals("0x000000ff", ((Canvas) graphicHandler.getHbox().getChildren().get(0)).getGraphicsContext2D().getStroke().toString());
	}
	

}
