/*
 * Programmer: Zayyad Tagwai, Roger Tan, Max Holland & Ankita Gangotra
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
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class IngredientsScreen {
	private HBox topBox, topBoxLeft, topBoxRight;
	private HBox midBox,nGuestsBox;
	private VBox midBoxLeft, midBoxRight, recipeInfoBox;
	private ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	private Image homeIcon, closeIcon, minimiseIcon;
	private VBox ingredientsList;
	private Tooltip h, c, m;
	private TextField nGuests;
	private Button updateIngredients;
	protected VBox bigBox;
	protected double height, width;

	public IngredientsScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection) {
		this.bigBox = bigBox;
		bigBox.setStyle("-fx-background-size: cover; -fx-background-position: center center; -fx-background-image: url('backgroundBlur.png');");
		this.height = height;
		this.width = width;
		// Imports eCook logo, home, close and minimise button icons
		homeHolder = new ImageView();
		homeIcon = new Image("home1.png");

		closeBtnHolder = new ImageView();
		closeIcon = new Image("redx.png");

		minimiseBtnHolder = new ImageView();
		minimiseIcon = new Image("minimise.png");

		// Add tool tip
		h = new Tooltip("Home");
		Tooltip.install(homeHolder, h);
		c = new Tooltip("Close");
		Tooltip.install(closeBtnHolder, c);
		m = new Tooltip("Minimise");
		Tooltip.install(minimiseBtnHolder, m);

		// Sets the event to happen when the close icon is clicked
		// Gets the node before closing the stage
		closeBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
		});

		// Sets the event to happen when the minimise icon is clicked
		// Gets the node before closing the stage
		minimiseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.setIconified(true);
			}
		});

		// Sets the event to happen when the home icon is clicked
		// Gets the node before closing the stage and returning to the main menu
		homeHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				Group root = (Group) source.getScene().getRoot();
				root.getChildren().clear();
				root.getChildren().add(
						new MainMenuContent(stage, recipeCollection).bigBox);
				stage.show();
			}
		});

		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
		homeHolder.setImage(homeIcon);

		// Creates a box containing the menu bar
		// Sets size and location parameters for eCook's menu bar containing
		// home, minimise and close buttons
		topBox = new HBox();
		topBoxLeft = new HBox();
		topBoxRight = new HBox(5);

		topBoxRight.setPrefSize(width / 2, height * 0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width / 2, height * 0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);

		topBox.setPadding(new Insets(10, 45, 0, 40));
		topBoxLeft.getChildren().add(homeHolder);
		topBoxRight.getChildren().addAll(minimiseBtnHolder, closeBtnHolder);
		topBox.getChildren().addAll(topBoxLeft, topBoxRight);

		midBox = new HBox(20);
		midBoxLeft = new VBox();
		midBoxRight = new VBox(20);

		midBox.setPadding(new Insets(10, 20, 10, 20));
		midBoxLeft.setPrefSize(width / 3, height - 100);
		midBoxLeft.setPadding(new Insets(60, 60, 60, 60));
		midBoxLeft.setStyle("-fx-background-color: transparent;"
				+ "-fx-background-image: url('half_book.png');"
				+ "-fx-background-position: center center;"
				+ "-fx-background-size: stretch;");
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

		// initialise the ingredients list VBox
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

		// set the first recipe in the list to be selected on loading
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

		Label recipesLabel = new Label("Recipes");
		recipesLabel.setId("recipesLabel");
		recipesLabel.getStylesheets().add("css.css");
		recipesLabel.setPadding(new Insets(0, 0, 40, 10));

		// add content to the main boxes
		midBoxLeft.getChildren().addAll(recipesLabel, listOfRecipes);
		midBox.getChildren().addAll(midBoxLeft, midBoxRight);

		// Box where all content of the IngredientsScreen class are collated
		bigBox.getChildren().addAll(topBox, midBox);
	}

	// method to update labels in the recipe info box
	public void updateInfoLabels(Recipe recipe) {
		String author = "", version = "", comment = "";

		if (recipe != null) {
			// update the info Strings
			author = recipe.getInfo().getAuthor();
			version = recipe.getInfo().getVersion();
			comment = recipe.getInfo().getComment();
		}

		// add labels for author, version and comment
		Label authorLabel = new Label("Author: " + author);
		Label versionLabel = new Label("Version: " + version);
		Label commentLabel = new Label("Comment: " + comment);

		authorLabel.setWrapText(true);
		authorLabel.setId("authorLabel");
		authorLabel.getStylesheets().add("css.css");
		versionLabel.setWrapText(true);
		versionLabel.setId("versionLabel");
		versionLabel.getStylesheets().add("css.css");
		commentLabel.setWrapText(true);
		commentLabel.setId("commentLabel");
		commentLabel.getStylesheets().add("css.css");

		// remove old labels
		recipeInfoBox.getChildren().clear();
		// add the labels to the info box
		recipeInfoBox.getChildren().addAll(authorLabel, versionLabel,
				commentLabel);
	}
	
	

	// method to update list of ingredients from recipe
	public void updateIngredientsList(final Recipe recipe) {	

		final Label recipeInformationLabel = new Label("Recipe Information");
		recipeInformationLabel.setId("recipeInformationLabel");
		recipeInformationLabel.getStylesheets().add("css.css");

		Label ingredientsLabel = new Label("Ingredients");
		ingredientsLabel.setId("ingredientsLabel");
		ingredientsLabel.getStylesheets().add("css.css");
		
/* n guests functionality- Ankita */	
	
	//Add label for nGuests
	Label nGuestsLabel = new Label("Serves:");
	nGuestsLabel.setId("nGuestsLabel");
	nGuestsLabel.getStylesheets().add("css.css");

	
	//Add text field
	nGuests = new TextField();
	nGuests.setText("1");
	nGuests.setMaxSize(midBoxRight.getPrefWidth()/20,
			midBoxRight.getPrefHeight()/20);
	
	// Add tool tip
	Tooltip TTnGuests = new Tooltip("Must be a number eg. 6 ");
	Tooltip.install(nGuests, TTnGuests);
	
	// Add an eventhandler to detect when user selects number of guests
	nGuests.setOnKeyReleased(new EventHandler<KeyEvent>() {
		public void handle(KeyEvent event) {
			// only allows integer numbers
			if (!nGuests.getText().equals("1")
					&& nGuests.getText().matches("[0-9]*") && !nGuests.getText().equals("")) {
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
	updateIngredients.setPrefSize(150, 30);
	updateIngredients.setId("nGuestsBtn");
	updateIngredients.getStylesheets().add("css.css");
	updateIngredients.setWrapText(true);
	updateIngredients.setAlignment(Pos.CENTER);
	updateIngredients.setTextAlignment(TextAlignment.CENTER);
	
		
		if (recipe != null) {
			// call the ingredients list generator
			final IngredientsList generator = new IngredientsList(recipe, height, width);
			ingredientsList = generator.getIngredientsListGUI();
			updateIngredients.setOnAction(new EventHandler<ActionEvent>() {
				public void handle(ActionEvent event) {
					int NumberOfGuests;
					// if valid number of guests entered
					if (nGuests != null && !nGuests.getText().equals("")
							&& nGuests.getText().matches("[0-9]*")) {
						// get logic to update amount of ingredients 
						NumberOfGuests = Integer.valueOf(nGuests.getText().toString());
						recipe.ingredientsAmountUpdate(NumberOfGuests);	
						ingredientsList.getChildren().clear();
						IngredientsList changedingredients = new IngredientsList(recipe,height,width);
						ingredientsList = changedingredients.getIngredientsListGUI();
						midBoxRight.getChildren().add(ingredientsList);
				}
			}});
		} else {
			ingredientsList.getChildren().clear();
			ingredientsList.getChildren().add(
					new Label("Sorry. Cannot find ingredients list."));
		}

		// add the buttons and the status bar to the bottom of the VBox
		nGuestsBox = new HBox(20);
		nGuestsBox.setPadding(new Insets(10,0,0,0));
		nGuestsBox.setSpacing(10);
		nGuestsBox.getChildren().addAll(nGuestsLabel,nGuests,updateIngredients);
		midBoxRight.getChildren().add(nGuestsBox);
		
		// refresh the entire box contents
		midBoxRight.getChildren().clear();
		midBoxRight.getChildren().addAll(recipeInformationLabel, recipeInfoBox, ingredientsLabel,
				nGuestsBox, ingredientsList);
	}
}