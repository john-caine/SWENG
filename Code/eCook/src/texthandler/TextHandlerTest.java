package texthandler;

/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 1/03/2014
 * Description: The JFX text module test class
 * Version History: 1.0 - Created.
 * 
 */

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;


public class TextHandlerTest {
	
	TextHandler text_object;
	String input_string = "Here is some text";
	String font = "Times New Roman";
	int x_start = 20;
	int y_start = 20;
	
	@Before 
	public void SetUp() {
	
	text_object = new TextHandler(input_string, font, x_start, y_start);
	
	

	
	
	}

	@Test
	public void instanceofHbox() {
		
		
		
		assertTrue(text_object.textBox instanceof HBox);
	};

	
	@Test 
	public void hboxContainsInstanceofText(){
		
		assertTrue(text_object.textBox.getChildren().get(0) instanceof Text);
		Text testText = (Text) text_object.textBox.getChildren().get(0);
		assertEquals("Here is some text", testText.getText());
		assertEquals("Times New Roman", testText.getFont().getName());
		
		
		
	}
	
	
	@Test
	public void setHboxXYcoordinates (){
		
		assertEquals(20, text_object.textBox.getLayoutX(),0.1);
		assertEquals(20, text_object.textBox.getLayoutY(), 0.1);
		
	}
	
	
}