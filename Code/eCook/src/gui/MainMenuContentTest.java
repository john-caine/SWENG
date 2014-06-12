/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for MainMenuContent Class
 */

package gui;

import static org.junit.Assert.*;
import java.io.File;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import xmlparser.Recipe;
import xmlparser.XMLReader;
import eCook.MainMenu;
import eCook.RecipeCollection;
import eCook.JavaFXThreadingRule;


public class MainMenuContentTest {
	
	private MainMenuContent mainMenuContent;
	private Stage stage;
	private Rectangle2D screenBounds;
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
		
		new MainMenu(stage, recipeCollection);
		
		// pass a stage and a set of recipe collection into MainMenuContent class
		mainMenuContent = new MainMenuContent(stage, recipeCollection);
		screenBounds = Screen.getPrimary().getBounds();
	}
	
	/**
	 * Test to check bigBox is the right size
	 */
	@Test
	public void mainMenuBigBoxTest() {
		/* Test the Width and Height of the bigBox */
		assertEquals(screenBounds.getWidth(), mainMenuContent.bigBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight(), mainMenuContent.bigBox.getPrefHeight(),0.01);
		assertEquals("[css.css]", mainMenuContent.bigBox.getStylesheets().toString());
	}
	
	/**
	 * Test to check the midBox contains the right content and and is located correctly
	 */
	@Test
	public void  mainMenuMidBoxTest(){
		/* Test if bigBox contains midBox */
		assertTrue(mainMenuContent.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if midBox contains a ImageView(logoholder) */
		HBox midBox = (HBox) mainMenuContent.bigBox.getChildren().get(1);
		
		/* Test the Width and Height of the midBox */
		assertEquals(screenBounds.getWidth(),midBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()*0.6,midBox.getPrefHeight(),0.01);
		
		/* Test the Alignment of midBox */
		assertEquals(Pos.CENTER, midBox.getAlignment());
		assertTrue(midBox.getChildren().get(0) instanceof ImageView);
		
		/* Test for eCook Image */
		ImageView logoHolder = (ImageView) midBox.getChildren().get(0);
		assertTrue(logoHolder.getImage() instanceof Image);
	}
	
	/**
	 * Test to check the bottomBox contains the right content and and is located correctly, and loadExternalRecipe
	 * button works correctly.
	 */
	@Test
	public void bottomBoxMoreRecipesTest(){
		/* Test if bigBox contains buttomBox */
		assertTrue(mainMenuContent.bigBox.getChildren().get(2) instanceof HBox);
		bottomBox = (HBox) mainMenuContent.bigBox.getChildren().get(2);
		
		/* Test the Alignment of bottomBox */
		assertEquals(Pos.CENTER, bottomBox.getAlignment());
		
		/* Test the Width and Height of the bottomBox */
		assertEquals(screenBounds.getWidth(), bottomBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()*0.3, bottomBox.getPrefHeight(),0.01);
		
		/* Test if bottomBox contains More Recipe Button */
		assertTrue(bottomBox.getChildren().get(3) instanceof Button);
		Button loadExtBtn = (Button) bottomBox.getChildren().get(3);
		
		/* Test the Width and height of the Button */
		assertEquals(screenBounds.getWidth()/5, loadExtBtn.getMinWidth(),0.01);
		assertEquals(bottomBox.getPrefHeight()-40, loadExtBtn.getMinHeight(),0.01);
		
		/* Test for More Recipe Button's Text */
		assertEquals("", loadExtBtn.getText());
		
		/* Test for More Recipe Button's ID */
		assertEquals("loadExtBtn", loadExtBtn.getId());
		
		/* Test if Load External Recipe Button's style is from css.css */
		assertEquals("[css.css]", loadExtBtn.getStylesheets().toString());
		
		/* Get ToolTip of the Button */
		assertEquals("Click here to load external recipes form the internet or locally", loadExtBtn.getTooltip().getText());
		
		/* Test when More Recipe Button is Pressed */
		loadExtBtn.fire();
		loadExtBtn.getOnAction();
		assertTrue(new LoadExternalRecipe(stage, recipeCollection).dialog.isShowing());		
	}
	
	/**
	 * Test to check the bottomBox contains the right content and and is located correctly, and generateShoppingList
	 * button works correctly.
	 */
	@Test
	public void bottomBoxGenearteShoppingListButtonTest(){
		bottomBox = (HBox) mainMenuContent.bigBox.getChildren().get(2);
		
		/* Test if bottomBox contains Generate Shopping List Button */
		assertTrue(bottomBox.getChildren().get(2) instanceof Button);
		Button generateListBtn = (Button) bottomBox.getChildren().get(2);
		
		/* Test the Width and height of the Button */
		assertEquals(screenBounds.getWidth()/5, generateListBtn.getMinWidth(),0.01);
		assertEquals(bottomBox.getPrefHeight()-40, generateListBtn.getMinHeight(),0.01);
		
		/* Test for Shopping List Button's Text */
		assertEquals("", generateListBtn.getText());
		
		/* Test for Shopping List Button's ID */
		assertEquals("generateListBtn", generateListBtn.getId());
		
		/* Test if Shopping List Button's style is from css.css */
		assertEquals("[css.css]", generateListBtn.getStylesheets().toString());
		
		/* Get ToolTip of the Button */
		assertEquals("Click here to make shopping list from ingredients chosen", generateListBtn.getTooltip().getText());
		
		/* Test when Shopping List Button is Pressed */
		generateListBtn.fire();
		generateListBtn.getOnAction();
		
		/* Test if GenerateShoppingList Class is being called and the content of the bigBox changes.
		 * It now has a Label(Shopping List) in the bigBox.
		 */
		
		/* Test if bigBox contains horizontalBox */
		assertTrue(mainMenuContent.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if horizontalBox contains leftBox */
		HBox horizontalBox = (HBox) mainMenuContent.bigBox.getChildren().get(1);
		assertTrue(horizontalBox.getChildren().get(0) instanceof VBox);
		
		/* Test if horizontalBox contains midBox */
		assertTrue(horizontalBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBox contains Shopping List Label*/
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof Label);
		
		/* Test if Shopping List Label display the text Shopping List */
		Label shoppingListLabel = (Label) midBox.getChildren().get(0);
		assertEquals("Shopping List", shoppingListLabel.getText());
	}
	
	/**
	 * Test to check the bottomBox contains the right content and and is located correctly, and ingredientPicker
	 * button works correctly.
	 */
	@Test
	public void bottomBoxIngredientPickerButtonTest(){
		bottomBox = (HBox) mainMenuContent.bigBox.getChildren().get(2);
		
		/* Test if bottomBox contains Ingredients Button */
		assertTrue(bottomBox.getChildren().get(1) instanceof Button);
		Button ingredientsPickBtn = (Button) bottomBox.getChildren().get(1);
		
		/* Test the Width and height of the Button */
		assertEquals(screenBounds.getWidth()/5, ingredientsPickBtn.getMinWidth(),0.01);
		assertEquals(bottomBox.getPrefHeight()-40, ingredientsPickBtn.getMinHeight(),0.01);
		
		/* Test for Ingredients Button's Text */
		assertEquals("", ingredientsPickBtn.getText());
		
		/* Test for Ingredients Button's ID */
		assertEquals("ingredientsPickBtn", ingredientsPickBtn.getId());
		
		/* Test if Ingredients Button's style is from css.css */
		assertEquals("[css.css]", ingredientsPickBtn.getStylesheets().toString());
		
		/* Get ToolTip of the Button */
		assertEquals("Click here to choose ingredients from list of recipes", ingredientsPickBtn.getTooltip().getText());
		
		/* Test when Ingredients Button is Pressed */
		ingredientsPickBtn.fire();
		ingredientsPickBtn.getOnAction();
		
		/* Test if IngredientsScreen Class is being called and the content of the bigBox changes.
		 * It now has a Label(Recipes) in the bigBox.
		 */
		
		/* Test if bigBox contains midBox */
		assertTrue(mainMenuContent.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if midBox contains midBoxLeft & midBoxRight */
		HBox midBox = (HBox) mainMenuContent.bigBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof VBox);
		assertTrue(midBox.getChildren().get(1) instanceof VBox);
		
	}
	
	/**
	 * Test to check the bottomBox contains the right content and and is located correctly, and recipes
	 * button works correctly.
	 */
	@Test
	public void bottomBoxRecipesButtonTest(){
		bottomBox = (HBox) mainMenuContent.bigBox.getChildren().get(2);
		
		/* Test if bottomBox contains Recipes Button */
		assertTrue(bottomBox.getChildren().get(0) instanceof Button);
		Button recipesBtn = (Button) bottomBox.getChildren().get(0);
		
		/* Test the Width and height of the Button */
		assertEquals(screenBounds.getWidth()/5, recipesBtn.getMinWidth(),0.01);
		assertEquals(bottomBox.getPrefHeight()-40, recipesBtn.getMinHeight(),0.01);
		
		/* Test for Recipes Button's Text */
		assertEquals("", recipesBtn.getText());
		
		/* Test for Recipes Button's ID */
		assertEquals("recipesBtn", recipesBtn.getId());
		
		/* Test if Recipes Button's style is from css.css */
		assertEquals("[css.css]", recipesBtn.getStylesheets().toString());
		
		/* Get ToolTip of the Button */
		assertEquals("Click here to choose from a list of recipes", recipesBtn.getTooltip().getText());
		
		/* Test when Ingredients Button is Pressed */
		recipesBtn.fire();
		recipesBtn.getOnAction();
		
		/* Test if RecipeScreen Class is being called and the content of the bigBox changes.
		 * It now has a Label(Recipe Playlist) in the bigBox.
		 */
		
		/* Test if bigBox contains horizontalBox */
		assertTrue(mainMenuContent.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if horizontalBox contains leftBox */
		HBox horizontalBox = (HBox) mainMenuContent.bigBox.getChildren().get(1);
		assertTrue(horizontalBox.getChildren().get(0) instanceof VBox);
		
		/* Test if horizontalBox contains midBox */
		assertTrue(horizontalBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBox contains a List of Recipes */
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(1) instanceof ListView);
		
		/* Test if midBox contains Recipe Playlist Label */
		assertTrue(midBox.getChildren().get(0) instanceof Label);
		Label recipePlaylistLabel = (Label) midBox.getChildren().get(0);
		assertEquals("Recipe Playlist", recipePlaylistLabel.getText());
	}
}