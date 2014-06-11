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
import eCook.JavaFXThreadingRule;

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
		File directory = new File(System.getenv("localappdata") + "/eCook/Recipes");
		if (directory.exists()) {
			// parse all files in folder, adding recipes to collection
			for (int i=0; i<directory.list().length; i++) {
				// only read XML files if for some reason other files exist
				if (directory.list()[i].endsWith(".xml")) {
					XMLReader reader = new XMLReader(System.getenv("localappdata") + "/eCook/Recipes/" + directory.list()[i]);
					Recipe currentRecipe = reader.getRecipe();
					currentRecipe.setFileName(directory.list()[i]);
					recipeCollection.addRecipe(currentRecipe);
				}
			}
		}
		ingredientsScreen = new IngredientsScreen(bigBox, height, width, recipeCollection);
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
		assertEquals("Recipes", recipeLabel.getText());

		/* Test if midBoxLeft contains List of Recipes */
		assertTrue(midBoxLeft.getChildren().get(1) instanceof ListView);

		/* Test if midBoxRight contains Recipe Information Label */
		VBox midBoxRight = (VBox) midBox.getChildren().get(1);
		assertTrue(midBoxRight.getChildren().get(0) instanceof Label);

		/* Test if the Label is Recipe Information */
		Label recipeInformationLabel = (Label) midBoxRight.getChildren().get(0);
		assertEquals("Recipe Information", recipeInformationLabel.getText());

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
		Label guestsLabel = (Label) recipeInfoBox.getChildren().get(1);
		assertEquals("Number of People Serves: 4",  guestsLabel.getText());

		/* Test if Recipe Information Box contains comment Label */
		assertTrue(recipeInfoBox.getChildren().get(2) instanceof Label);

		/* Test if midBoxRight contains Ingredients Label */
		assertTrue(midBoxRight.getChildren().get(2) instanceof Label);

		/* Test if the Ingredients Label is displaying the correct Text */
		Label ingredientsLabel = (Label) midBoxRight.getChildren().get(2);
		assertEquals("Ingredients",  ingredientsLabel.getText());

		/* Test if midBoxRight contains Ingredients List */
		assertTrue(midBoxRight.getChildren().get(4) instanceof VBox);

		/* Test if midBoxRight contains nGuestsBox */
		HBox nGuestsBox = (HBox) midBoxRight.getChildren().get(3);
		assertTrue(midBoxRight.getChildren().get(3) instanceof HBox);

		/*Test if nGuestsBox contains nGuests Label */		

		assertTrue(nGuestsBox.getChildren().get(0) instanceof Label);

		/* Test if the Label displays the correct text */
		Label nGuestsLabel = (Label) nGuestsBox.getChildren().get(0);
		assertTrue(nGuestsBox.getChildren().get(0) instanceof Label);
		assertEquals("Serves:",  nGuestsLabel.getText());

		/* Test if the nGuests TextField is displaying the correct Text */
		TextField nGuests = (TextField) nGuestsBox.getChildren().get(1);
		assertTrue(nGuestsBox.getChildren().get(1) instanceof TextField);
		assertEquals("4",  nGuests.getText());

		/* Test if the updateIngredients Button is displaying the correct Text */
		Button updateIngredients = (Button) nGuestsBox.getChildren().get(2);
		assertTrue(nGuestsBox.getChildren().get(2) instanceof Button);
		assertEquals("Update Ingredients",  updateIngredients.getText());

	}
}
