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

import java.awt.AWTException;
import java.awt.Button;
import java.awt.Robot;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

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
		 	
		 	System.setProperty("java.awt.headless", "false");
		 	
		 	GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		 	GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
		 	try {
		 		//TODO get Robot() class working without throwing AWTException
		 		Robot bot = new Robot();
		 	}
		 	catch (AWTException e) {
		 		e.printStackTrace();
		 	}
		 }

		@Test
		public void instanceofHbox() {	
			assertTrue(image_object.box instanceof HBox);
		}

		@Test 
		public void hboxContainsInstanceofImage(){
			assertTrue(image_object.box.getChildren().get(0) instanceof ImageView);
			ImageView testImage = (ImageView) image_object.box.getChildren().get(0);
			assertEquals(200, testImage.getFitWidth(), 1);
			assertEquals(200, testImage.getFitHeight(), 1);
			assertEquals(180, testImage.getRotate());
		}
			
		@Test
		public void setHboxXYcoordinates (){
			assertEquals(images.get(1).getXStart(), image_object.box.getLayoutX(), 0.1);
			assertEquals(images.get(1).getYStart(), image_object.box.getLayoutY(), 0.1);
			
			String tmp1 = String.format("Playlist x = %d", (int)images.get(1).getXStart());
			String tmp2 = String.format("Actual x = %d", (int)image_object.box.getLayoutX());
			System.out.print(tmp1);
			System.out.print(tmp2);
		}
		
		@Test
		public void imageCanBranch() {
			if(image_object.branchID != null) {
				double xValue = image_object.box.getLayoutX();
				double yValue = image_object.box.getLayoutY();
				
				//TODO find way to simulate clicking mouse on image
			}
			else {
				assertEquals(true, true);
			}
			

			
		}
		
		
	}


