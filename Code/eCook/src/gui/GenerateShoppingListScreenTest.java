/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for GenerateShoppingListScreen Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
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

public class GenerateShoppingListScreenTest {
	
	private GenerateShoppingListScreen generateShoppingListScreen;
	private VBox bigBox;
	private Stage stage;
	private double height;
	private double width;
	private RecipeCollection recipeCollection;
	private Rectangle2D screenBounds;
	private HBox topBox;
	private Button printBtn;
	private Label statusBar;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		
		//Gets the visual bounds of the screen
		screenBounds = Screen.getPrimary().getBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		bigBox =  new VBox();
		stage =  new Stage();
		//Populate the recipeList
		recipeCollection = new RecipeCollection();
		File directory = new File("defaultRecipes_new");
		if (directory.exists()) {
			// parse all files in folder, adding recipes to collection
			for (int i=0; i<directory.list().length; i++) {
				// only read XML files if for some reason other files exist
				if (directory.list()[i].endsWith(".xml")) {
					XMLReader reader = new XMLReader("defaultRecipes_new/" + directory.list()[i]);
					Recipe currentRecipe = reader.getRecipe();
					currentRecipe.setFileName(directory.list()[i]);
					recipeCollection.addRecipe(currentRecipe);
				}
			}
		}
		new MainMenu(stage, recipeCollection);
		generateShoppingListScreen = new GenerateShoppingListScreen(bigBox, height, width, recipeCollection);
		/* Test if bigBox contains topBox */
		topBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(0);
	}

	@Test
	public void generateShoppingListScreenTopBoxTest() {
		/* Test if bigBox contains topBox */
		assertTrue(generateShoppingListScreen.bigBox.getChildren().get(0) instanceof HBox);
		
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
		assertEquals("Home" ,generateShoppingListScreen.h.getText());
	
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
		assertEquals("Minimise" ,generateShoppingListScreen.m.getText());
		
		/* Test Close Image */
		ImageView closeBtnHolder = (ImageView) topRightBox.getChildren().get(1);
		assertTrue(closeBtnHolder.getImage() instanceof Image);	
		closeBtnHolder.getOnMouseClicked();
	    assertFalse(stage.isShowing());
	    /* Test for closeBtnHolder Tooltip */
		assertEquals("Close" ,generateShoppingListScreen.c.getText());
	}
	
	@Test
	public void generateShoppingListScreenHorizontalBoxTest() {
		/* Test if bigBox contains horizontalBox */
		assertTrue(generateShoppingListScreen.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if horizontalBox contains leftBox */
		HBox horizontalBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(1);
		assertTrue(horizontalBox.getChildren().get(0) instanceof VBox);
		VBox leftBox = (VBox) horizontalBox.getChildren().get(0);
		/* Test the Width and Height of the leftBox */
		assertEquals(screenBounds.getWidth()*0.2, leftBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()-topBox.getPrefHeight()-100, leftBox.getPrefHeight(),0.01);
		
		/* Test if horizontalBox contains midBox */
		assertTrue(horizontalBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBox contains Shopping List Label*/
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof Label);
		/* Test the Width and Height of the leftBox */
		assertEquals(screenBounds.getWidth()*0.6, midBox.getPrefWidth(),0.01);
		assertEquals(screenBounds.getHeight()-topBox.getPrefHeight()-100, midBox.getPrefHeight(),0.01);
		
		/* Test if Shopping List Label display the text Shopping List */
		Label shoppingListLabel = (Label) midBox.getChildren().get(0);
		assertEquals("Shopping List", shoppingListLabel.getText());
		
		/* Test for Shopping List Label's ID */
		assertEquals("shoppingListLabel", shoppingListLabel.getId());
		
		/* Test if Shopping List Label's style is from css.css */
		assertEquals("[css.css]", shoppingListLabel.getStylesheets().toString());
		
		/* Test if midBox contains buttonBar*/
		assertTrue(midBox.getChildren().get(1) instanceof HBox);
		
		/* Test if buttonBar contains editBtn */
		HBox buttonBar = (HBox) midBox.getChildren().get(1);
		assertTrue(buttonBar.getChildren().get(0) instanceof Button);
		/* Test the Alignment of topLeftBox */
		assertEquals(Pos.CENTER_RIGHT, buttonBar.getAlignment());
		
		/* editBtn's Text */
		Button editBtn = (Button) buttonBar.getChildren().get(0);
		assertEquals("Edit List", editBtn.getText());
		
		/* Test the Width and Height of the editBtn */
		assertEquals(midBox.getPrefWidth()/8, editBtn.getPrefWidth(),0.01);
		assertEquals(200, editBtn.getPrefHeight(),0.01);
		
		/* Get ToolTip of the editBtn */
		assertEquals("Click here to edit your shopping list"
				, editBtn.getTooltip().getText());
		
		/* Test when editBtn is Pressed */
		editBtn.fire();
		editBtn.getOnAction();
		assertEquals("Remove Unselected Items", editBtn.getText());
		
		/* Test if buttonBar contains addBtn */
		assertTrue(buttonBar.getChildren().get(1) instanceof Button);
		/* addBtn's Text */
		Button addBtn = (Button) buttonBar.getChildren().get(1);
		assertEquals("Add Item", addBtn.getText());
		
		/* Test the Width and Height of the addBtn */
		assertEquals(midBox.getPrefWidth()/8, addBtn.getPrefWidth(),0.01);
		assertEquals(200, addBtn.getPrefHeight(),0.01);
		
		/* Get ToolTip of the addBtn */
		assertEquals("Click here to add extra items to your shopping list"
				, addBtn.getTooltip().getText());
		
		/* Test if midBox contains scrollPane */
		assertTrue(midBox.getChildren().get(2) instanceof ScrollPane);
		ScrollPane scrollPane = (ScrollPane) midBox.getChildren().get(2);
		
		/* Test if scrollPane is assigned to shoppingListBox */
		assertTrue(scrollPane.getContent() instanceof VBox);
		
		/* Test if shoppingListBox contains ingredient(Label) */
		VBox shoppingListBox = (VBox) scrollPane.getContent();
		assertTrue(shoppingListBox.getChildren().get(0) instanceof CheckBox);
		
		/* Test if midBox contains statusBar */
		assertTrue(midBox.getChildren().get(3) instanceof Label);
		
		/* Test for the correct message for the statusBar */ 
		/* -1 is required to remove the invisible TextField */
		statusBar = (Label) midBox.getChildren().get(3);
		assertEquals("You have " + (shoppingListBox.getChildren().size()-1) + " items in your shopping list", statusBar.getText());
		
		/* Test if midBox contains midBoxBottom */
		assertTrue(midBox.getChildren().get(4) instanceof HBox);
		
		/* Test if midBoxBottom contains save Button */
		HBox midBoxBottom = (HBox) midBox.getChildren().get(4);
		assertTrue(midBoxBottom.getChildren().get(0) instanceof Button);
		
		/* Get the Text and ID of the save Button */
		Button saveBtn = (Button) midBoxBottom.getChildren().get(0);
		assertEquals("Save as PDF", saveBtn.getText());
		assertEquals("saveBtn", saveBtn.getId());
		
		/* Test the Width and Height of the save Button */
		assertEquals(midBox.getPrefWidth()/8, saveBtn.getPrefWidth(),0.01);
		assertEquals(200, saveBtn.getPrefHeight(),0.01);
		
		/* Get ToolTip of the save Button */
		assertEquals("Click here to save your shopping list as PDF"
				, saveBtn.getTooltip().getText());
		
		/* Test when save Button is Pressed & FileBrowser Window Pops Up */
		saveBtn.fire();
		saveBtn.getOnAction();
		assertEquals("Shopping list saved to PDF", statusBar.getText());
		
		/* Test if midBoxBottom contains printBtn */
		assertTrue(midBoxBottom.getChildren().get(1) instanceof Button);
		
		/* Get the Text of the printBtn */
		printBtn = (Button) midBoxBottom.getChildren().get(1);
		assertEquals("Print", printBtn.getText());
		assertEquals("printBtn", printBtn.getId());
		
		/* Test the Width and Height of the printBtn */
		assertEquals(midBox.getPrefWidth()/8, printBtn.getPrefWidth(),0.01);
		assertEquals(200, printBtn.getPrefHeight(),0.01);
		
		/* Get ToolTip of the printBtn */
		assertEquals("Click here to view your shopping list"
				, printBtn.getTooltip().getText());
		
		/* Test when printBtn is Pressed */
		printBtn.fire();
		printBtn.getOnAction();
		assertEquals("Printing shopping list...", statusBar.getText());
	}
	
	@Test
	public void printShoppingListTest() {
		/* Setup to retrieve the printBtn and statusBar */
		HBox horizontalBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(1);
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		HBox midBoxBottom = (HBox) midBox.getChildren().get(4);
		printBtn = (Button) midBoxBottom.getChildren().get(1);
		statusBar = (Label) midBox.getChildren().get(3);
		/* Test when printBtn is Pressed */
		printBtn.fire();
		printBtn.getOnAction();
		assertEquals("Printing shopping list...", statusBar.getText());
	}
	
	@Test
	public void getShoppingListTest() {
		/* Setup to retrieve the printBtn and statusBar */
		HBox horizontalBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(1);
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		HBox buttonBar = (HBox) midBox.getChildren().get(1);
		
		/* addBtn's Text */
		Button addBtn = (Button) buttonBar.getChildren().get(1);
		assertEquals("Add Item", addBtn.getText());
		
		/* Test when addBtn is Pressed */
		addBtn.fire();
		addBtn.getOnAction();
		assertEquals("Save Changes", addBtn.getText());
		
		ScrollPane scrollPane = (ScrollPane) midBox.getChildren().get(2);
		
		/* Test if shoppingListBox contains ingredient(Label) */
		VBox shoppingListBox = (VBox) scrollPane.getContent();
		assertTrue(shoppingListBox.getChildren().get(0) instanceof Label);
		
		/* Test if when addBtn is pressed, the last node in shoppingListBox becomes a TextField */
		addBtn.fire();
		assertTrue(shoppingListBox.getChildren().get(shoppingListBox.getChildren().size()-1) instanceof TextField);

	}
}
