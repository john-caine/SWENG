package gui;
/*
 * Programmer: Ankita Gangotra
 * Date Created: 06/05/2014
 * Makes a test class for menu.
 */
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import eCook.MainMenu;
import eCook.RecipeCollection;
import eCook.JavaFXThreadingRule;

public class menuTest {
	
	private menuMock Menu;
	private Stage stage;
	private RecipeCollection recipeCollection;
	private Rectangle2D screenBounds;
	private HBox topBox;
	
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() {
		stage =  new Stage();
		new MainMenu(stage, recipeCollection);
		Menu = new menuMock (recipeCollection);
	}
	
	@Test
	public void menuTopBoxTest() {
		
		/* Test if topBox contains topLeftBox & topRightBox */
		assertTrue(topBox.getChildren().get(0) instanceof HBox);
		assertTrue(topBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topLeftBox contains 1 Imageview */
		HBox topLeftBox = (HBox) topBox.getChildren().get(0);
		assertTrue(topLeftBox.getChildren().get(0) instanceof ImageView);
		
		/* Test the Width and Height of the topLeftBox */
		assertEquals(screenBounds.getWidth()/2, topLeftBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()*0.1, topLeftBox.getPrefHeight(),0.01);
		/* Test the Alignment of topLeftBox */
		assertEquals(Pos.TOP_LEFT, topLeftBox.getAlignment());
		
		/* Test Home Image */
		ImageView homeHolder = (ImageView) topLeftBox.getChildren().get(0);
		assertTrue(homeHolder.getImage() instanceof Image);
		homeHolder.getOnMouseClicked();
		assertTrue(stage.getScene().getRoot().getChildrenUnmodifiable().get(0) instanceof VBox);	
		/* Test for homeHolder Tooltip */
		assertEquals("Home" , Menu.h.getText());
	
		/*Test if topRightBox contains 2 ImageView */
		HBox topRightBox = (HBox) topBox.getChildren().get(1);
		assertTrue(topRightBox.getChildren().get(0) instanceof ImageView);
		assertTrue(topRightBox.getChildren().get(1) instanceof ImageView);
		
		/* Test the Width and Height of the topRightBox */
		assertEquals(screenBounds.getWidth()/2, topRightBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()*0.1, topRightBox.getPrefHeight(),0.01);
		/* Test the Alignment of topRightBox */
		assertEquals(Pos.TOP_RIGHT, topRightBox.getAlignment());
		
		/* Test Minimise Image */
		ImageView minimiseBtnHolder = (ImageView) topRightBox.getChildren().get(0);
		assertTrue(minimiseBtnHolder.getImage() instanceof Image);
		minimiseBtnHolder.getOnMouseClicked();
		assertFalse(stage.isMaximized());
		/* Test for minimiseBtnHolder Tooltip */
		assertEquals("Minimise" , Menu.m.getText());
		
		/* Test Close Image */
		ImageView closeBtnHolder = (ImageView) topRightBox.getChildren().get(1);
		assertTrue(closeBtnHolder.getImage() instanceof Image);	
		closeBtnHolder.getOnMouseClicked();
	    assertFalse(stage.isShowing());
	    /* Test for closeBtnHolder Tooltip */
		assertEquals("Close" , Menu.c.getText());
	}
	

}
