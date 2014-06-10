/*
 * Programmer: Zayyad Tagwai, Roger Tan, Max Holland, Ankita Gangotra and James Oatley
 * Date Created: 06/05/2014
 * Adds components of the recipe screen to the bigBox window
 */

package gui;

import java.util.ArrayList;

import shoppingList.IngredientsList;
import xmlparser.Recipe;
import eCook.RecipeCollection;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class IngredientsScreen extends menu {

	private HBox midBox,nGuestsBox;
	private VBox midBoxLeft, midBoxRight, recipeInfoBox;

	private VBox ingredientsList;

	private TextField nGuests;
	private Button updateIngredients;
	protected VBox bigBox;
	protected double height, width;

	public IngredientsScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection) {
		super (recipeCollection);
		//Get bigBox and set background

		//Get bigBox and set background

		this.bigBox = bigBox;
		bigBox.setId("IngredientsScreenBigBox");
		bigBox.getStylesheets().add("css.css");
		this.height = height;
		this.width = width;

		//Define midBox
		midBox = new HBox(20);
		midBoxLeft = new VBox();
		midBoxRight = new VBox(20);

		midBox.setPadding(new Insets(10, 20, 10, 20));
		midBoxLeft.setPrefSize(width / 3, height - 100);
		midBoxLeft.setPadding(new Insets(60, 60, 60, 60));
		midBoxLeft.setId("IngredientsScreenMidBoxLeft");
		midBoxLeft.getStylesheets().add("css.css");
		midBoxRight.setPrefSize(width * 2 / 3, height - 100);

		// Create an ArrayList of Recipe Titles
		ArrayList<String> recipeTitles = new ArrayList<String>();

		for (int i = 0; i < recipeCollection.getNumberOfRecipes(); i++) {
			recipeTitles
			.add(recipeCollection.getRecipe(i).getInfo().getTitle());
		}

		// Create a list view and populate it with the recipe titles
		final ListView<String> listOfRecipes = new ListView<String>();
		listOfRecipes.getStylesheets().add("css.css");
		listOfRecipes.setPrefSize(midBoxLeft.getPrefWidth(),
				midBoxLeft.getPrefHeight());
		listOfRecipes.setItems(FXCollections.observableList(recipeTitles));
		listOfRecipes.getSelectionModel()
		.setSelectionMode(SelectionMode.SINGLE);

		// Initialise the ingredients list VBox
		ingredientsList = new VBox();

		// when recipe selection changes, update the info and ingredients fields
		listOfRecipes.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov,
					String old_val, String new_val) {
				// get the selected recipe
				int selectedIndex = listOfRecipes.getSelectionModel()
						.getSelectedIndex();
				// call the update info labels method
				updateInfoLabels(recipeCollection
						.getRecipe(selectedIndex));
				updateIngredientsList(recipeCollection
						.getRecipe(selectedIndex));
			}
		});

		// Create a new VBox to hold the recipe information
		recipeInfoBox = new VBox();
		recipeInfoBox.setPrefSize(midBoxRight.getPrefWidth(),
				midBoxRight.getPrefHeight() * 0.4 - 100);

		// Set the first recipe in the list to be selected on loading
		if (listOfRecipes.getItems().size() != 0) {
			listOfRecipes.getSelectionModel().select(0);
			if (recipeCollection.getRecipe(0) != null) {
				updateInfoLabels(recipeCollection.getRecipe(0));
				updateIngredientsList(recipeCollection.getRecipe(0));
			}
		}

		else {
			updateInfoLabels(null);
		}

		//Add labels
		Label recipesLabel = new Label("Recipes");
		recipesLabel.setId("recipesLabel");
		recipesLabel.getStylesheets().add("css.css");
		recipesLabel.setPadding(new Insets(0, 0, 40, 10));

		// Add content to the main boxes
		midBoxLeft.getChildren().addAll(recipesLabel, listOfRecipes);
		midBox.getChildren().addAll(midBoxLeft, midBoxRight);

		// Box where all content of the IngredientsScreen class are collated
		bigBox.getChildren().addAll(topBox, midBox);
	}

	// Method to update labels in the recipe info box
	public void updateInfoLabels(Recipe recipe) {
		String author = "", guests = "", comment = "";

		if (recipe != null) {
			// update the info Strings
			author = recipe.getInfo().getAuthor();
			guests = recipe.getInfo().getGuests();
			comment = recipe.getInfo().getComment();
		}

		// Add labels for author, version and comment
		Label authorLabel = new Label("Author: " + author);
		Label guestsLabel = new Label("Number of People Serves: " + guests);
		Label commentLabel = new Label("Comment: " + comment);
		System.out.println(commentLabel);

		authorLabel.setWrapText(true);
		authorLabel.setId("authorLabel");
		authorLabel.getStylesheets().add("css.css");
		guestsLabel.setWrapText(true);
		guestsLabel.setId("guestsLabel");
		guestsLabel.getStylesheets().add("css.css");
		commentLabel.setWrapText(true);
		commentLabel.setId("commentLabel");
		commentLabel.getStylesheets().add("css.css");

		// Remove old labels
		recipeInfoBox.getChildren().clear();
		// add the labels to the info box
		recipeInfoBox.getChildren().addAll(authorLabel, guestsLabel,
				commentLabel);
	}



	// Method to update list of ingredients from recipe and update for multiple guests
	public void updateIngredientsList(final Recipe recipe) {	

		final Label recipeInformationLabel = new Label("Recipe Information");
		recipeInformationLabel.setId("recipeInformationLabel");
		recipeInformationLabel.getStylesheets().add("css.css");

		final Label ingredientsLabel = new Label("Ingredients");
		ingredientsLabel.setId("ingredientsLabel");
		ingredientsLabel.getStylesheets().add("css.css");

		//Adding multiple guests functionality 
		//Add label for nGuests
		Label nGuestsLabel = new Label("Serves:");
		nGuestsLabel.setId("nGuestsLabel");
		nGuestsLabel.getStylesheets().add("css.css");


		//Add text field
		nGuests = new TextField();
		nGuests.setText("4");
		nGuests.setMaxSize(midBoxRight.getPrefWidth()/20, midBoxRight.getPrefHeight()/20);

		// Add tool tip
		Tooltip TTnGuests = new Tooltip("Must be a number more than 0 and less than 100");
		Tooltip.install(nGuests, TTnGuests);

		// Add an eventhandler to detect when user selects number of guests
		nGuests.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				// only allows integer numbers
				if (!nGuests.getText().equals("0")&& nGuests.getText().matches("[0-9]*") && !nGuests.getText().equals("") && Integer.valueOf(nGuests.getText()) < 100) {
					updateIngredients.setDisable(false);
					updateIngredients.setTooltip(new Tooltip("Click here to choose number of people to modify quantity of ingredients"));
				} else {
					updateIngredients.setDisable(true);
				}
			}
		});

		//Add button 
		updateIngredients = new Button("Update Ingredients");
		updateIngredients.setDisable(true);
		updateIngredients.setMaxSize(midBoxRight.getPrefWidth()/5, midBoxRight.getPrefHeight()/20);
		updateIngredients.setId("nGuestsBtn");
		updateIngredients.getStylesheets().add("css.css");
		updateIngredients.setWrapText(true);
		updateIngredients.setAlignment(Pos.CENTER);
		updateIngredients.setTextAlignment(TextAlignment.CENTER);


		if (recipe != null) {
			// call the ingredients list generator
			final IngredientsList generator = new IngredientsList(recipe, height, width);
			ingredientsList = generator.getIngredientsListGUI();
			//Call updateIngredients button to change amount of ingredients
			updateIngredients.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					int NumberOfGuests;
					// if valid number of guests entered
					if (nGuests != null && !nGuests.getText().equals("") && nGuests.getText().matches("[0-9]*")) {
						// get logic to update amount of ingredients 
						NumberOfGuests = Integer.valueOf(nGuests.getText().toString());
						recipe.ingredientsAmountUpdate(NumberOfGuests);	
						ingredientsList.getChildren().clear();
						IngredientsList changedingredients = new IngredientsList(recipe,height,width);
						ingredientsList = changedingredients.getIngredientsListGUI();
						// refresh the entire box contents
						midBoxRight.getChildren().clear();
						midBoxRight.getChildren().addAll(recipeInformationLabel, recipeInfoBox, ingredientsLabel, nGuestsBox, ingredientsList);
					}
				}});
		} else {
			ingredientsList.getChildren().clear();
			ingredientsList.getChildren().add(new Label("Sorry. Cannot find ingredients list."));
		}

		// add the buttons and the status bar to the bottom of the VBox
		nGuestsBox = new HBox(10);
		nGuestsBox.setMaxSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()/20);
		nGuestsBox.setSpacing(10);
		nGuestsBox.getChildren().addAll(nGuestsLabel,nGuests,updateIngredients);
		// midBoxRight.getChildren().add(nGuestsBox);

		// refresh the entire box contents
		midBoxRight.getChildren().clear();
		midBoxRight.getChildren().addAll(recipeInformationLabel, recipeInfoBox, ingredientsLabel, nGuestsBox, ingredientsList);
	}
}