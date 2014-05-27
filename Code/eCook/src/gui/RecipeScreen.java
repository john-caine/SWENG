/*
 * Programmer: Zayyad Tagwai  & Roger Tan
 * Date Created: 05/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import xmlparser.Recipe;
import eCook.RecipeCollection;
import eCook.SlideShow;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecipeScreen {
	InputStream inputStream;
	String recipeInfo;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;	
	HBox topBox, topBoxLeft, topBoxRight;
	VBox recipeInfoBox;
	
	
	public RecipeScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection, final Stage stage){
		//Imports home, close and minimise button icons
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
		
		VBox leftBox = new VBox();
		VBox midBox = new VBox(20);
		VBox rightBox = new VBox();
			
		//Sets parameters for leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height - topBox.getPrefHeight()-100);
		midBox.setPrefSize(width*0.6,  height - topBox.getPrefHeight()-100);
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2,  height - topBox.getPrefHeight()-100);
		
		// Create an ArrayList of Recipe Titles
		ArrayList<String> recipeTitles = new ArrayList<String>();
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			recipeTitles.add(recipeCollection.getRecipe(i).getInfo().getTitle());
		}
		
		// Create a list view and populate it with the recipe titles
		final ListView<String> listOfRecipes = new ListView<String>();
		listOfRecipes.setStyle("-fx-background: lightgrey;");
		listOfRecipes.setPrefSize(rightBox.getPrefWidth() , rightBox.getPrefHeight()*0.5);
		listOfRecipes.setItems(FXCollections.observableList(recipeTitles));
		listOfRecipes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

		Label recipeInfoLabel = new Label();
		recipeInfoLabel.setWrapText(true);
		recipeInfoLabel.setStyle("-fx-border-color:red; -fx-background-color: beige;");
		recipeInfo ="Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here";
		recipeInfoLabel.setText(recipeInfo);
		recipeInfoLabel.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight() * 0.3);
		
		// Create a new HBox to hold the recipe information
		recipeInfoBox = new VBox();
		recipeInfoBox.setPrefSize(rightBox.getPrefWidth(), rightBox.getPrefHeight()*0.4-100);
		recipeInfoBox.setStyle("-fx-border-color:black");
		
		
		// when recipe selection changes, update the info and ingredients fields
		listOfRecipes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> ov, 
					String old_val, String new_val) {
				// get the selected recipe
				int selectedIndex = listOfRecipes.getSelectionModel().getSelectedIndex();
				// call the update info labels method
				updateInfoLabels(recipeCollection.getRecipe(selectedIndex));
			}
		});	

		// set the first recipe in the list to be selected on loading
		if (listOfRecipes.getItems().size() != 0) {
			listOfRecipes.getSelectionModel().select(0);
		}
		if (recipeCollection.getRecipe(0) != null) {
			updateInfoLabels(recipeCollection.getRecipe(0));
		}
		else {
			updateInfoLabels(null);
		}
				
		//Box where all content of the RecipeScreen class are collated 
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);	
				
		Button playSlideBtn = new Button("Play");
		playSlideBtn.setPrefSize(midBox.getPrefWidth()/4, 40);
		// define the start slideshow button method
		playSlideBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				//Get filename of currently selected recipe
				String fileName = recipeCollection.getRecipe(listOfRecipes.getSelectionModel().getSelectedIndex()).getFileName();
				new SlideShow(stage, "defaultRecipes/" + fileName, recipeCollection);
			}
			
		});
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().addAll(listOfRecipes, recipeInfoBox, playSlideBtn);
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

	 
}
