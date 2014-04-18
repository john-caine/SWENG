package imagehandler;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import eCook.SlideShow;
import xmlparser.Image;
import xmlparser.XMLReader;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


public class ImageHandlerTest {
		
	private ImageHandler image_object;
	private SlideShow parent;
	private XMLReader reader;
	private List<Image> images;
		
		@Before 
		public void SetUp() {
			JFXPanel fxPanel = new JFXPanel();
			reader = new XMLReader("../Resources/PWSExamplePlaylist_2.xml");
		 	images = reader.getRecipe().getSlide(3).getContent().getImages();
		 	image_object = new ImageHandler(parent, images.get(1).getUrlName(), images.get(1).getXStart(), 
		 									images.get(1).getYStart(),images.get(1).getWidth(),
		 									images.get(1).getHeight(), images.get(1).getStartTime(), 
		 									images.get(1).getDuration(), images.get(1).getLayer(),
		 									null /*BranchID*/ , 180 /*orientation*/);		
		 }

		@Test
		public void instanceofHbox() {	
			assertTrue(image_object.box instanceof HBox);
		}

		@Test 
		public void hboxContainsInstanceofImage(){
			assertTrue(image_object.box.getChildren().get(0) instanceof ImageView);
			ImageView testImage = (ImageView) image_object.box.getChildren().get(1);
			assertEquals(200, testImage.getFitWidth(), 1);
			assertEquals(200, testImage.getFitHeight(), 1);
			assertEquals(180, testImage.getRotate());
		}
			
		@Test
		public void setHboxXYcoordinates (){
			assertEquals(50, image_object.box.getLayoutX(), 0.1);
			assertEquals(50, image_object.box.getLayoutY(), 0.1);	
		}
		
		@Test
		public void imageCanBranch() {
			if(image_object.branchID != null) {
				image_object.postActionEvent();
				
			}
		}
		
		
	}


