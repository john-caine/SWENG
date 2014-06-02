/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * Junit Test for GenerateShoppingListScreen Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import xmlparser.Recipe;
import xmlparser.XMLReader;
import eCook.RecipeCollection;

public class GenerateShoppingListScreenTest {
	
	private GenerateShoppingListScreen generateShoppingListScreen;
	private VBox bigBox;
	private double height;
	private double width;
	private RecipeCollection recipeCollection;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		
		//Gets the visual bounds of the screen
		Rectangle2D screenBounds = Screen.getPrimary().getBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		bigBox =  new VBox();
		
		//Populate the recipeList
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
		generateShoppingListScreen = new GenerateShoppingListScreen(bigBox, height, width, recipeCollection);
	}

	@Test
	public void generateShoppingListScreenTopBoxTest() {
		/* Test if bigBox contains topBox */
		assertTrue(generateShoppingListScreen.bigBox.getChildren().get(0) instanceof HBox);
		
		/* Test if topBox contains topLeftBox & topRightBox */
		HBox topBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(0);
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
	public void generateShoppingListScreenHorizontalBoxTest() {
		/* Test if bigBox contains horizontalBox */
		assertTrue(generateShoppingListScreen.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if horizontalBox contains leftBox */
		HBox horizontalBox = (HBox) generateShoppingListScreen.bigBox.getChildren().get(1);
		assertTrue(horizontalBox.getChildren().get(0) instanceof VBox);
		
		/* Test if horizontalBox contains midBox */
		assertTrue(horizontalBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBox contains Shopping List Label*/
		VBox midBox = (VBox) horizontalBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof Label);
		
		/* Test if Shopping List Label display the text Shopping List */
		Label shoppingListLabel = (Label) midBox.getChildren().get(0);
		assertEquals("Shopping List", shoppingListLabel.getText());
		
		/* Test if midBox contains buttonBar*/
		assertTrue(midBox.getChildren().get(1) instanceof HBox);
		
		/* Test if buttonBar contains editBtn */
		HBox buttonBar = (HBox) midBox.getChildren().get(1);
		assertTrue(buttonBar.getChildren().get(0) instanceof Button);
		/* editBtn's Text */
		Button editBtn = (Button) buttonBar.getChildren().get(0);
		assertEquals("Edit List", editBtn.getText());
		
		/* Test if buttonBar contains addBtn */
		assertTrue(buttonBar.getChildren().get(1) instanceof Button);
		/* addBtn's Text */
		Button addBtn = (Button) buttonBar.getChildren().get(1);
		assertEquals("Add Item", addBtn.getText());
		
		/* Test if midBox contains scrollPane */
		assertTrue(midBox.getChildren().get(2) instanceof ScrollPane);
		ScrollPane scrollPane = (ScrollPane) midBox.getChildren().get(2);
		
		/* Test if scrollPane is assigned to shoppingListBox */
		assertTrue(scrollPane.getContent() instanceof VBox);
		
		/* Test if shoppingListBox contains ingredient(Label) */
		VBox shoppingListBox = (VBox) scrollPane.getContent();
		assertTrue(shoppingListBox.getChildren().get(0) instanceof Label);
		
		/* Test if when addBtn is pressed, the last node in shoppingListBox becomes a TextField */
		addBtn.fire();
		assertTrue(shoppingListBox.getChildren().get(shoppingListBox.getChildren().size()-1) instanceof TextField);
		
		/* Test if midBox contains statusBar */
		assertTrue(midBox.getChildren().get(3) instanceof Label);
		
		/* Test for the correct message for the statusBar */ 
		/* -1 is required to remove the invisible TextField */
		Label statusBar = (Label) midBox.getChildren().get(3);
		assertEquals("You have " + (shoppingListBox.getChildren().size()-1) + " items in your shopping list", statusBar.getText());
		
		/* Test if midBox contains midBoxBottom */
		assertTrue(midBox.getChildren().get(4) instanceof HBox);
		
		/* Test if midBoxBottom contains save Button */
		HBox midBoxBottom = (HBox) midBox.getChildren().get(4);
		assertTrue(midBoxBottom.getChildren().get(0) instanceof Button);
		
		/* Get the Text and ID of the save Button */
		Button saveBtn = (Button) midBoxBottom.getChildren().get(0);
		assertEquals("Save", saveBtn.getText());
		assertEquals("saveBtn", saveBtn.getId());
		
		/* Test if midBoxBottom contains print Button */
		assertTrue(midBoxBottom.getChildren().get(1) instanceof Button);
		
		/* Get the Text of the save Button */
		Button printBtn = (Button) midBoxBottom.getChildren().get(1);
		assertEquals("Print", printBtn.getText());	
	}
}
