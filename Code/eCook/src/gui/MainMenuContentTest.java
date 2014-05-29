/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for MainMenuContent Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Recipe;
import xmlparser.XMLReader;
import eCook.RecipeCollection;


public class MainMenuContentTest {
	
	private MainMenuContent mainMenuCotent;
	private Stage stage;
	private RecipeCollection recipeCollection;
	private HBox bottomBox;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		stage =  new Stage();
		recipeCollection = new RecipeCollection();
		File directory = new File("defaultRecipes");
		if (directory.exists()) {
			// parse all files in folder, adding recipes to collection
			for (int i=0; i<directory.list().length; i++) {
				// only read XML files if for some reason other files exist
				if (directory.list()[i].endsWith(".xml")) {
					XMLReader reader = new XMLReader("defaultRecipes/" + directory.list()[i]);
					Recipe currentRecipe = reader.getRecipe();
					currentRecipe.setFileName(directory.list()[i]);
					recipeCollection.addRecipe(currentRecipe);
				}
			}
		}
		// pass a stage and a set of recipe collection into MainMenuContent class
		mainMenuCotent = new MainMenuContent(stage, recipeCollection);
	}

	@Test
	public void mainMenuTopBoxTest() {
		/* Test if bigBox contains topBox */
		assertTrue(mainMenuCotent.bigBox.getChildren().get(0) instanceof HBox);
		
		/* Test if topBox contains topLeftBox & topRightBox */
		HBox topBox = (HBox) mainMenuCotent.bigBox.getChildren().get(0);
		assertTrue(topBox.getChildren().get(0) instanceof HBox);
		assertTrue(topBox.getChildren().get(1) instanceof HBox);
		
		/* Test if topLeftBox contains 1 Imageview */
		HBox topLeftBox = (HBox) topBox.getChildren().get(0);
		assertTrue(topLeftBox.getChildren().get(0) instanceof ImageView);
		
		/* Test for Home Image */
		ImageView homeHolder = (ImageView) topLeftBox.getChildren().get(0);
		assertTrue(homeHolder.getImage() instanceof Image);
	
		/*Test if topRightBox contains 2 ImageView */
		HBox topRightBox = (HBox) topBox.getChildren().get(1);
		assertTrue(topRightBox.getChildren().get(0) instanceof ImageView);
		assertTrue(topRightBox.getChildren().get(1) instanceof ImageView);
		
		/* Test for Minimise Image */
		ImageView minimiseBtnHolder = (ImageView) topRightBox.getChildren().get(0);
		assertTrue(minimiseBtnHolder.getImage() instanceof Image);
		
		/* Test for Close Image */
		ImageView closeBtnHolder = (ImageView) topRightBox.getChildren().get(1);
		assertTrue(closeBtnHolder.getImage() instanceof Image);
	}
	
	@Test
	public void  mainMenuMidBoxTest(){
		/* Test if bigBox contains midBox */
		assertTrue(mainMenuCotent.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if midBox contains a ImageView */
		HBox midBox = (HBox) mainMenuCotent.bigBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof ImageView);
		
		/* Test for eCook Image */
		ImageView logoHolder = (ImageView) midBox.getChildren().get(0);
		assertTrue(logoHolder.getImage() instanceof Image);
	}
	
	@Test
	public void bottomBoxLoadExternalRecipeButtonTest(){
		/* Test if bigBox contains buttomBox */
		assertTrue(mainMenuCotent.bigBox.getChildren().get(2) instanceof HBox);
		
		bottomBox = (HBox) mainMenuCotent.bigBox.getChildren().get(2);
		
		/* Test if bottomBox contains Load External Recipe Button */
		assertTrue(bottomBox.getChildren().get(3) instanceof Button);
		
		Button loadExtBtn = (Button) bottomBox.getChildren().get(3);
		
		/* Test for Load External Recipe Button's Text */
		assertEquals(loadExtBtn.getText(), "Load External Recipe");
		
		/* Test for Load External Recipe Button's ID */
		assertEquals(loadExtBtn.getId(), "loadExtBtn");
		
		/* Test if Load External Recipe Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", loadExtBtn.getStylesheets().toString());
		
		/* Test when Load External Recipe Button is Pressed */
		loadExtBtn.fire();
		loadExtBtn.getOnAction();
		assertTrue(new LoadExternalRecipe(stage, recipeCollection).dialog.isShowing());		
	}
	
	@Test
	public void bottomBoxGenearteShoppingListButtonTest(){
		bottomBox = (HBox) mainMenuCotent.bigBox.getChildren().get(2);
		/* Test if bottomBox contains Generate Shopping List Button */
		assertTrue(bottomBox.getChildren().get(2) instanceof Button);
		
		Button generateListBtn = (Button) bottomBox.getChildren().get(2);
		
		/* Test for Load External Recipe Button's Text */
		assertEquals("Generate Shopping List", generateListBtn.getText());
		
		/* Test for Load External Recipe Button's ID */
		assertEquals("generateListBtn", generateListBtn.getId());
		
		/* Test if Load External Recipe Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", generateListBtn.getStylesheets().toString());
		
		generateListBtn.fire();
	}
	
	@Test
	public void bottomBoxIngredientPickerButtonTest(){
		bottomBox = (HBox) mainMenuCotent.bigBox.getChildren().get(2);
		/* Test if bottomBox contains Ingredient Picker Button */
		assertTrue(bottomBox.getChildren().get(1) instanceof Button);
		
		Button ingredientsPickBtn = (Button) bottomBox.getChildren().get(1);
		
		/* Test for Load External Recipe Button's Text */
		assertEquals("Ingredient Picker", ingredientsPickBtn.getText());
		
		/* Test for Load External Recipe Button's ID */
		assertEquals("ingredientsPickBtn", ingredientsPickBtn.getId());
		
		/* Test if Load External Recipe Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", ingredientsPickBtn.getStylesheets().toString());
	}
	
	@Test
	public void bottomBoxRecipesButtonTest(){
		bottomBox = (HBox) mainMenuCotent.bigBox.getChildren().get(2);
		/* Test if bottomBox contains Recipe Button */
		assertTrue(bottomBox.getChildren().get(0) instanceof Button);
		
		Button recipesBtn = (Button) bottomBox.getChildren().get(0);
		
		/* Test for Load External Recipe Button's Text */
		assertEquals("Recipes", recipesBtn.getText());
		
		/* Test for Load External Recipe Button's ID */
		assertEquals("recipesBtn", recipesBtn.getId());
		
		/* Test if Load External Recipe Button's style is from css.css */
		assertEquals("[file:../Resources/css.css]", recipesBtn.getStylesheets().toString());
	}
}