
/*Programmer: P.Mathema, S.Beedell & Roger Tan
 * Date Created: 14/03/2014
 * Description: 

*/

package audiohandler;

import static org.junit.Assert.*;

import java.util.List;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Audio;
import xmlparser.XMLReader;
import eCook.SlideShow;


public class AudioHandlerTest {
	private AudioHandler audioHandler;
	private XMLReader reader;
	private SlideShow parent;
	private List<Audio> audioList;
	private double width, height;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	//Read XML playlist and create a new audioHandler object based on the first audio found
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("PWSExamplePlaylist_4.xml");
		audioList = reader.getRecipe().getSlide(2).getContent().getAudios();
		audioHandler = new AudioHandler(parent, audioList.get(0).getUrlName(), audioList.get(0).getStartTime(), 
				audioList.get(0).getDuration(), audioList.get(0).getLoop());
		/* Expected width and height of the AudioHandler when width and height of the handler is set to null */
		Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
		width = (int) (bounds.getWidth()/2);
		height = (int) (bounds.getHeight()/3);
	}
	
	@Test
	public void audioHandlerTests() throws InterruptedException{
		
		/* VideoPlayer's X and Y Location (setMediaPlayerLocation Method)*/
		assertEquals(100, audioHandler.mediaControl.overallBox.getLayoutX(), 0.01);
		assertEquals(100, audioHandler.mediaControl.overallBox.getLayoutY(), 0.01);
		
		/* set Media to be the provided Path */
		assertEquals("http://ystv.co.uk/~john.caine/swengrepo/sample1.mp3",
				audioHandler.media.getSource());
		
		/* VideoPlayer's Width and Height */
		assertEquals(width, audioHandler.mediaControl.overallBox.getMaxWidth(), 0.01);
		assertEquals(height, audioHandler.mediaControl.overallBox.getMaxHeight(), 0.01);
		
		/* VideoPlayer's MediaView and Control Panel are visible */
		assertTrue(audioHandler.mediaControl.mediaView.isVisible());
		assertTrue(audioHandler.mediaControl.mediaBar.isVisible());
	}
	
	@Test
	public void audioHandlerStopTest() throws InterruptedException{
		audioHandler.mediaControl.stopButton.fire();
		assertTrue(audioHandler.mediaControl.atEndOfMedia);
	}
	
	
	
}
