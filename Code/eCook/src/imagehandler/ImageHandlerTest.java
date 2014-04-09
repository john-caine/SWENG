package imagehandler;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import eCook.SlideShow;
import xmlparser.TextString;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class ImageHandlerTest {
		
		ImageHandler image_object;
		Integer x_start = 20;
		Integer y_start = 20;
		Integer width = 100;
		Integer height = 1200;
		private Integer startTime = 5;
		private Integer duration = 20;
		private SlideShow parent;
		private Integer layer;
		private Integer branchID;
		private Integer orientation;
		
		@Before 
		public void SetUp() {		
		image_object = new ImageHandler(parent, "../Resources/bike2.jpeg", x_start, y_start, width, height, startTime, duration, layer, branchID, orientation);
		}

		@Test
		public void instanceofHbox() {	
			assertTrue(image_object instanceof ImageView);
		};

		
		@Test 
		public void hboxContainsInstanceofText(){
			
			assertTrue(text_object.textBox.getChildren().get(0) instanceof Text);
			Text testText = (Text) text_object.textBox.getChildren().get(0);
			assertEquals("Here is some text", testText.getText());
			assertEquals("Times New Roman Italic", testText.getFont().getName());
			assertEquals("0x00ff00ff", testText.getFill().toString());
			assertTrue(testText.isUnderline());
			
			
			
		}
		
		
		@Test
		public void setHboxXYcoordinates (){
			
			assertEquals(20, text_object.textBox.getLayoutX(),0.1);
			assertEquals(20, text_object.textBox.getLayoutY(), 0.1);
			
		}
		
		
	}

}
