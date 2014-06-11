/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: Image Handler test class.
 */

package media;

import static org.junit.Assert.*;
import javafx.scene.image.Image;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.JavaFXThreadingRule;
import eCook.SlideShow;

public class ImageHandlerTest {

	private SlideShow parent;
	private ImageHandler imageHandler;
	// Run tests on JavaFX thread ref. Andy Till
	// http://andrewtill.blogspot.co.uk/2012/10/junit-rule-for-javafx-controller-testing.html
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void SetUp(){
		imageHandler = new ImageHandler(parent, "cup.jpg", 40, 40, 40, 40, 40, 5, 0, 0 );
	}
	
	/*
	 * Test that the image is resized correctly
	 */
	@Test
	public void reSizeImageTest() {
		assertEquals(40, imageHandler.getImageView().getFitHeight(), 0.1);
		assertEquals(40, imageHandler.getImageView().getFitWidth(),0.1);
		assertTrue(imageHandler.getImageView().isSmooth());
		assertTrue(imageHandler.getImageView().isCache());
	}
	
	/*
	 * Test that a valid image is created from the file path
	 */
	@Test
	public void validImageCreated(){
		assertTrue(imageHandler.retrieveImage("cup.jpg") instanceof Image);
		
	}

}
