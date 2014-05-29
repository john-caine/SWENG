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

import shoppingList.IngredientsList;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IngredientsScreen {
	private HBox topBox, topBoxLeft, topBoxRight;
	private HBox midBox;
	private VBox midBoxLeft, midBoxRight, recipeInfoBox;
	private InputStream inputStream;
	private ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	private Image homeIcon, closeIcon, minimiseIcon;
	private VBox ingredientsList;
	protected VBox bigBox;
	protected double height, width;
	
	public IngredientsScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection){
		this.bigBox = bigBox;
		this.height = height;
		this.width = width;
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
        topBoxRight = new HBox(5);
		
		topBoxRight.setPrefSize(width/2, height*0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width/2, height*0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);
		
		topBox.setPadding(new Insets(10, 45, 0, 40));
		topBoxLeft.getChildren().add(homeHolder);
		topBoxRight.getChildren().addAll(minimiseBtnHolder,closeBtnHolder);
		topBox.getChildren().addAll(topBoxLeft,topBoxRight);
	
		midBox = new HBox(20);
		midBoxLeft= new VBox();
		midBoxRight = new VBox(20);
		
		midBox.setPadding(new Insets(10,20,10,20));
		midBoxLeft.setPrefSize(width/3, height-100);
		midBoxRight.setPrefSize(width*2/3, height-100);
		
		// Create an ArrayList of Recipe Titles
		ArrayList<String> recipeTitles = new ArrayList<String>();
		
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			recipeTitles.add(recipeCollection.getRecipe(i).getInfo().getTitle());
		}
		
		// Create a list view and populate it with the recipe titles
		final ListView<String> listOfRecipes = new ListView<String>();
		listOfRecipes.getStylesheets().add("file:../Resources/css.css");
		listOfRecipes.setPrefSize(midBoxLeft.getPrefWidth(), midBoxLeft.getPrefHeight());
		listOfRecipes.setItems(FXCollections.observableList(recipeTitles));
		listOfRecipes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// initialise the ingredients list VBox
		ingredientsList = new VBox();
		
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
		recipeInfoBox.setPrefSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()*0.4-100);
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
		
		Label recipesLabel = new Label("Recipes:");
		recipesLabel.setId("recipesLabel");
		recipesLabel.getStylesheets().add("file:../Resources/css.css");
		
		// add content to the main boxes
		midBoxLeft.getChildren().addAll(recipesLabel, listOfRecipes);
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
		
		authorLabel.setWrapText(true);
		authorLabel.setId("authorLabel");
		authorLabel.getStylesheets().add("file:../Resources/css.css");
		versionLabel.setWrapText(true);
		versionLabel.setId("versionLabel");
		versionLabel.getStylesheets().add("file:../Resources/css.css");
		commentLabel.setWrapText(true);
		commentLabel.setId("commentLabel");
		commentLabel.getStylesheets().add("file:../Resources/css.css");
		
		// remove old labels
		recipeInfoBox.getChildren().clear();
		// add the labels to the info box
		recipeInfoBox.getChildren().addAll(authorLabel, versionLabel, commentLabel);
	}
	
	// method to update list of ingredients from recipe
	public void updateIngredientsList(Recipe recipe) {		
		if (recipe != null) {
			// call the ingredients list generator
			IngredientsList generator = new IngredientsList(recipe, height, width);
			ingredientsList = generator.getIngredientsListGUI();
		}
		else {
			ingredientsList.getChildren().clear();
			ingredientsList.getChildren().add(new Label("Sorry. Cannot find ingredients list."));
		}
		
		Label recipeInformationLabel = new Label("Recipe Information:");
		recipeInformationLabel.setId("recipeInformationLabel");
		recipeInformationLabel.getStylesheets().add("file:../Resources/css.css");
		
		Label ingredientsLabel = new Label("Ingredients:");
		ingredientsLabel.setId("ingredientsLabel");
		ingredientsLabel.getStylesheets().add("file:../Resources/css.css");
		// refresh the entire box contents
		midBoxRight.getChildren().clear();
		midBoxRight.getChildren().addAll(recipeInformationLabel, recipeInfoBox, ingredientsLabel, ingredientsList);
	}
}
