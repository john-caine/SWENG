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
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void SetUp(){
		imageHandler = new ImageHandler(parent, "cup.jpg", 40, 40, 40, 40, 40, 5, 0, 0 );
	}
	@Test
	public void reSizeImageTest() {
		assertEquals(40, imageHandler.getImageView().getFitHeight(), 0.1);
		assertEquals(40, imageHandler.getImageView().getFitWidth(),0.1);
		assertTrue(imageHandler.getImageView().isSmooth());
		assertTrue(imageHandler.getImageView().isCache());
	}
	
	@Test
	public void validImageCreated(){
		assertTrue(imageHandler.retrieveImage("cup.jpg") instanceof Image);
		
	}

}
