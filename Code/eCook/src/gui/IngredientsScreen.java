/*
 * Programmer: Zayyad Tagwai  & Roger Tan
 * Date Created: 06/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import xmlparser.Recipe;

import eCook.RecipeCollection;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IngredientsScreen {
	HBox topBox, topBoxLeft, topBoxRight;
	HBox midBox;
	VBox midBoxLeft, midBoxRight, recipeInfoBox;
	InputStream inputStream;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;
	VBox ingredientsList;
	
	public IngredientsScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection){
		
		//Imports eCook logo, home, close and minimise button icons
		homeHolder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/home1.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		homeIcon = new Image(inputStream);
		
		closeBtnHolder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/redx.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		closeIcon = new Image(inputStream);
		
		
		minimiseBtnHolder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/minimise.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		minimiseIcon = new Image(inputStream);
		
		
		//Sets the event to happen when the close icon is clicked
		//Gets the node before closing the stage
	    closeBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	stage.close();
            }
        });
	    
	    //Sets the event to happen when the minimise icon is clicked
	    //Gets the node before closing the stage
		minimiseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent mouseEvent) {
		    	Node  source = (Node)  mouseEvent.getSource();
		    	Stage stage  = (Stage) source.getScene().getWindow(); 
		    	stage.setIconified(true);
		    }
		});
		
		//Sets the event to happen when the home icon is clicked
		//Gets the node before closing the stage and returning to the main menu
		homeHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	Group root = (Group) source.getScene().getRoot();
         	root.getChildren().clear();
         	root.getChildren().add(new MainMenuContent(stage, recipeCollection).bigBox);
         	stage.show();
            }
        });
         
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
		homeHolder.setImage(homeIcon);
		
		//Creates a box containing the menu bar
		//Sets size and location parameters for eCook's menu bar containing home, minimise and close buttons
        topBox = new HBox();
        topBoxLeft = new HBox();
        topBoxRight = new HBox();
		
		topBoxRight.setPrefSize(width/2, height*0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width/2, height*0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);
		
		topBoxLeft.getChildren().add(homeHolder);
		topBoxRight.getChildren().addAll(minimiseBtnHolder,closeBtnHolder);
		topBox.getChildren().addAll(topBoxLeft,topBoxRight);
	
		midBox = new HBox(20);
		midBoxLeft= new VBox();
		midBoxRight = new VBox(20);
		
		midBox.setPadding(new Insets(10,20,10,20));
		midBoxLeft.setPrefSize(width/3, height-topBox.getHeight());
		midBoxRight.setPrefSize(width*2/3, height-topBox.getHeight());
		
		// Create an ArrayList of Recipe Titles
		ArrayList<String> recipeTitles = new ArrayList<String>();
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			recipeTitles.add(recipeCollection.getRecipe(i).getInfo().getTitle());
		}
		
		// Create a list view and populate it with the recipe titles
		final ListView<String> listOfRecipes = new ListView<String>();
		listOfRecipes.setStyle("-fx-background: lightgrey;");
		listOfRecipes.setPrefSize(midBoxLeft.getPrefWidth(), midBoxLeft.getPrefHeight());
		listOfRecipes.setItems(FXCollections.observableList(recipeTitles));
		listOfRecipes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// make a scroll pane containing a VBox of the recipe ingredients
		ScrollPane ingredientsListPane = new ScrollPane(); 
		ingredientsListPane.setStyle("-fx-background: lightgrey;");
		ingredientsListPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		ingredientsListPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		ingredientsListPane.setPrefSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()*0.4);
		ingredientsList = new VBox();
		ingredientsListPane.setContent(ingredientsList);
		
		// when recipe selection changes, update the info and ingredients fields
		listOfRecipes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, 
					String old_val, String new_val) {
				// get the selected recipe
				int selectedIndex = listOfRecipes.getSelectionModel().getSelectedIndex();
				// call the update info labels method
				updateInfoLabels(recipeCollection.getRecipe(selectedIndex));
				updateIngredientsList(recipeCollection.getRecipe(selectedIndex));
			}
	    });
		
		// Create a new VBox to hold the recipe information
		recipeInfoBox = new VBox();
		recipeInfoBox.setPrefSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()*0.4);
		recipeInfoBox.setStyle("-fx-border-color:black");
		
		// set the first recipe in the list to be selected on loading
		if (listOfRecipes.getItems().size() != 0) {
			listOfRecipes.getSelectionModel().select(0);
		}
		if (recipeCollection.getRecipe(0) != null) {
			updateInfoLabels(recipeCollection.getRecipe(0));
			updateIngredientsList(recipeCollection.getRecipe(0));
		}
		else {
			updateInfoLabels(null);
		}	
		
		// provide an 'add to shopping list button'
		Button addToShoppingListBtn = new Button("Add To Shopping List");
		// method to handle when the add to shopping list button is pressed
		addToShoppingListBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){
			public void handle(MouseEvent event) {
				System.out.println("Add to Shopping List CLicked");
			}
		});
		
		//midBoxRight.setAlignment(Pos.TOP_CENTER);
		midBoxLeft.getChildren().addAll(new Label("Recipes:"), listOfRecipes);
		midBoxRight.getChildren().addAll(new Label("Recipe Information:"), recipeInfoBox, new Label("Ingredients:"), ingredientsList, addToShoppingListBtn);
		midBox.getChildren().addAll(midBoxLeft,midBoxRight);
		
		//Box where all content of the IngredientsScreen class are collated 
		bigBox.getChildren().addAll(topBox,midBox);
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
		
		// remove old labels
		recipeInfoBox.getChildren().clear();
		// add the labels to the info box
		recipeInfoBox.getChildren().addAll(authorLabel, versionLabel, commentLabel);
	}
	
	// method to update list of ingredients from recipe
	public void updateIngredientsList(Recipe recipe) {
		// clear any previous ingredients
		ingredientsList.getChildren().clear();
		
		if (recipe != null) {
			// populate the list of ingredients and create a checkbox list
			ArrayList<String> listOfIngredients = new ArrayList<String>();
			// loop through ingredients, adding to arrays
			for (int i=0; i<recipe.getNumberOfIngredients(); i++) {
				listOfIngredients.add(recipe.getIngredient(i).getName());
			}
			CheckBox[] checkboxes = new CheckBox[listOfIngredients.size()];
			for (int i=0; i<recipe.getNumberOfIngredients(); i++) {
				CheckBox box = checkboxes[i] = new CheckBox(listOfIngredients.get(i));
				// set the event handler for each checkbox
				box.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
						//updateShoppingList();
					}
				});
				ingredientsList.getChildren().add(checkboxes[i]);
			}
		}
		else {
			System.out.println("Cannot update ingredients: recipe is null");
		}
	}
}
