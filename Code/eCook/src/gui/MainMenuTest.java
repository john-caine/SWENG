package gui;

import static org.junit.Assert.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;


public class MainMenuTest {
	
	MainMenuContent mainMenu;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		mainMenu = new MainMenuContent();
	}

	@Test
	public void mainMenuTest() {
		/* Test if bigBox contains topBox & midBox */
		assertTrue(mainMenu.bigBox.getChildren().get(0) instanceof HBox);
		assertTrue(mainMenu.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topBox contains 2 Imageview */
		assertTrue(mainMenu.topBox.getChildren().get(0) instanceof ImageView);
		assertTrue(mainMenu.topBox.getChildren().get(1) instanceof ImageView);
		
		/*Test if midBox contains 1 blank Label & 4 Buttons */
		assertTrue(mainMenu.midBox.getChildren().get(0) instanceof Label);
		assertTrue(mainMenu.midBox.getChildren().get(1) instanceof Button);
		assertTrue(mainMenu.midBox.getChildren().get(2) instanceof Button);
		assertTrue(mainMenu.midBox.getChildren().get(3) instanceof Button);
		assertTrue(mainMenu.midBox.getChildren().get(4) instanceof Button);
		
	}

}
