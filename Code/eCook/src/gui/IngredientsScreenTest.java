/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 27/05/2014
 * Junit Test for IngredientScreen Class
 */
package gui;

import static org.junit.Assert.*;

import java.io.File;

import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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

public class IngredientsScreenTest {
	
	private IngredientsScreen ingredientsScreen;
	private VBox bigBox;
	private double height;
	private double width;
	private RecipeCollection recipeCollection;
	private CheckBox[] checkboxes;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	@Before
	public void setUp() throws Exception {
		
		//Gets the visual bounds of the screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
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
		ingredientsScreen = new IngredientsScreen(bigBox, height, width, recipeCollection);
	}

	@Test
	public void ingredientsScreenTopBoxTest() {
		/* Test if bigBox contains topBox */
		assertTrue(ingredientsScreen.bigBox.getChildren().get(0) instanceof HBox);
		
		/* Test if topBox contains topLeftBox & topRightBox */
		HBox topBox = (HBox) ingredientsScreen.bigBox.getChildren().get(0);
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
	public void ingredientsScreenMidBoxTest() {
		/* Test if bigBox contains midBox */
		assertTrue(ingredientsScreen.bigBox.getChildren().get(1) instanceof HBox);
		
		/* Test if midBox contains midBoxLeft & midBoxRight */
		HBox midBox = (HBox) ingredientsScreen.bigBox.getChildren().get(1);
		assertTrue(midBox.getChildren().get(0) instanceof VBox);
		assertTrue(midBox.getChildren().get(1) instanceof VBox);
		
		/* Test if midBoxLeft contains Recipe Label */
		VBox midBoxLeft = (VBox) midBox.getChildren().get(0);
		assertTrue(midBoxLeft.getChildren().get(0) instanceof Label);
		
		/* Test if the Label is Recipe */
		Label recipeLabel = (Label) midBoxLeft.getChildren().get(0);
		assertEquals("Recipes:", recipeLabel.getText());
		
		/* Test if midBoxLeft contains List of Recipes */
		assertTrue(midBoxLeft.getChildren().get(1) instanceof ListView);
		
		/* Test if the background of the listOfRecipes is Grey */
		ListView<String> listOfRecipes = (ListView<String>) midBoxLeft.getChildren().get(1);
		assertEquals("-fx-background: lightgrey;",listOfRecipes.getStyle());
		
		
		/* Test if midBoxRight contains Recipe Information Label */
		VBox midBoxRight = (VBox) midBox.getChildren().get(1);
		assertTrue(midBoxRight.getChildren().get(0) instanceof Label);
		
		/* Test if the Label is Recipe Information */
		Label recipeInformationLabel = (Label) midBoxRight.getChildren().get(0);
		assertEquals("Recipe Information:", recipeInformationLabel.getText());
		
		/* Test if midBoxRight contains Recipe Information Box */
		assertTrue(midBoxRight.getChildren().get(1) instanceof VBox);
		
		/* Test if Recipe Information Box contains author Label */
		VBox recipeInfoBox = (VBox) midBoxRight.getChildren().get(1);
		assertTrue(recipeInfoBox.getChildren().get(0) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct author */
		Label authorLabel = (Label) recipeInfoBox.getChildren().get(0);
		assertEquals("Author: Jim Dee",  authorLabel.getText());
		
		/* Test if Recipe Information Box contains version Label */
		assertTrue(recipeInfoBox.getChildren().get(1) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct version */
		Label versionLabel = (Label) recipeInfoBox.getChildren().get(1);
		assertEquals("Version: 1.0",  versionLabel.getText());
		
		/* Test if Recipe Information Box contains comment Label */
		assertTrue(recipeInfoBox.getChildren().get(2) instanceof Label);
		
		/* Test if the AuthorLabel is displaying the correct comment */
		Label commentLabel = (Label) recipeInfoBox.getChildren().get(2);
		assertEquals("Comment: This is a soup which simply celebrates the squash, so it important to buy a good quality one. If you are partial to garlic or any particular herb, you can add it at the roasting stage.",  
				commentLabel.getText());
		
		/* Test if midBoxRight contains Ingredients Label */
		assertTrue(midBoxRight.getChildren().get(2) instanceof Label);
		
		/* Test if the Ingredients Label is displaying the correct Text */
		Label ingredientsLabel = (Label) midBoxRight.getChildren().get(2);
		assertEquals("Ingredients:",  ingredientsLabel.getText());
		
		/* Test if midBoxRight contains Ingredients List */
		assertTrue(midBoxRight.getChildren().get(3) instanceof VBox);
		
		/* Test if the ingredientList contains a ScrollPane */
		VBox ingredientList = (VBox) midBoxRight.getChildren().get(3);
		assertTrue(ingredientList.getChildren().get(0) instanceof ScrollPane);
		
		/* Test if the ScrollPane contains a scrollBox */
		ScrollPane scrollPane = (ScrollPane) ingredientList.getChildren().get(0);
		assertTrue(scrollPane.getContent() instanceof VBox);
		
		/* Test if the scrollBox contains CheckBoxes */
		VBox scrollBox = (VBox) scrollPane.getContent();
		for(int i = 0; i<scrollBox.getChildren().size(); i++){
			assertTrue(scrollBox.getChildren().get(i) instanceof CheckBox);
		}
		
		/* Test if the ingredientList contains a buttonBox */
		assertTrue(ingredientList.getChildren().get(1) instanceof HBox);
		
		/* Test if the buttonBox contains SelectAll Button */
		HBox buttonBox =  (HBox) ingredientList.getChildren().get(1);
		assertTrue(buttonBox.getChildren().get(0) instanceof Button);
		
		/* Test if selectAllButton display the correct Text and all CheckBoxes are not selected*/
		Button selectAllButton = (Button) buttonBox.getChildren().get(0);
		assertEquals("Select All", selectAllButton.getText());
		checkboxes = new CheckBox[scrollBox.getChildren().size()];
		for(int j = 0; j<scrollBox.getChildren().size(); j++){
			checkboxes[j] = (CheckBox) scrollBox.getChildren().get(j);
			assertFalse(checkboxes[j].isSelected());
		}
		/* Test if selectAllButton display the correct Text and CheckBoxes get selected when pressed*/
		selectAllButton.fire();
		assertEquals("Deselect All", selectAllButton.getText());
		checkboxes = new CheckBox[scrollBox.getChildren().size()];
		for(int k = 0; k<scrollBox.getChildren().size(); k++){
			checkboxes[k] = (CheckBox) scrollBox.getChildren().get(k);
			assertTrue(checkboxes[k].isSelected());
		}
		/* Test if buttonBox contains statusBar */
		assertTrue(buttonBox.getChildren().get(2) instanceof Label);
		Label statusBar = (Label) buttonBox.getChildren().get(2);
		
		/* Test if the Label is empty */
		assertEquals("", statusBar.getText());
		
		/* Test if buttonBox contains updateShoppingListButton */
		assertTrue(buttonBox.getChildren().get(1) instanceof Button);
		
		/* Test if updateShoppingListButton display the correct Text */
		Button updateShoppingListButton = (Button) buttonBox.getChildren().get(1);
		assertEquals("Add Selected Ingredients to Shopping List", updateShoppingListButton.getText());
		
		/* Test if statusBar display a message when updateShoppingListButton is Pressed */
		updateShoppingListButton.fire();
		assertEquals("Ingredients Added to Shopping List.", statusBar.getText());
	}
}
