/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for LoadExternalRecipe Class
 */
package gui;

import static org.junit.Assert.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LoadExternalRecipeTest {
	
	private LoadExternalRecipe loadExternalRecipe;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		loadExternalRecipe = new LoadExternalRecipe(new Stage());
	}

	@Test
	public void loadExternalRecipeTest() {
		Scene dialogScene = loadExternalRecipe.dialog.getScene();
		/* Test if dialog stage's root is a VBox */
		assertTrue(dialogScene.getRoot() instanceof VBox);
		
		/* Test if the VBox contains topBox */
		VBox loadExtBox = (VBox) dialogScene.getRoot();
		assertTrue(loadExtBox.getChildren().get(0) instanceof HBox);
		
		/* Test if topBox contains ImageView */
		HBox topBox =  (HBox)loadExtBox.getChildren().get(0);
		assertTrue(topBox.getChildren().get(0) instanceof ImageView);
		
		/* Test for Close Image */
		ImageView loadExtWinCloseBtnHolder = (ImageView) topBox.getChildren().get(0);
		assertTrue(loadExtWinCloseBtnHolder.getImage() instanceof Image);
		
		
		/* Test if the VBox contains midBox */
		assertTrue(loadExtBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topBox contains HTTP Button */
		HBox midBox =  (HBox)loadExtBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof Button);
		
		Button urlBtn = (Button) midBox.getChildren().get(0);
		
		/* Test for HTTP Button's Text */
		assertEquals("HTTP://", urlBtn.getText());
		
		/* Test for HTTP Button's ID */
		assertEquals("urlBtn", urlBtn.getId());
		
		/* Test if HTTP Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", urlBtn.getStylesheets().toString());
		
		/* Test if topBox contains File Browser Button */
		assertTrue(midBox.getChildren().get(1) instanceof Button);
		
		Button fileBrowserBtn = (Button) midBox.getChildren().get(1);
		
		/* Test for HTTP Button's Text */
		assertEquals("Browse...", fileBrowserBtn.getText());
		
		/* Test for HTTP Button's ID */
		assertEquals("fileBrowserBtn", fileBrowserBtn.getId());
		
		/* Test if HTTP Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", fileBrowserBtn.getStylesheets().toString());
	}

}
