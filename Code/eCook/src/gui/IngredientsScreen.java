/*
 * Programmer: Zayyad Tagwai  & Roger Tan
 * Date Created: 06/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import eCook.RecipeCollection;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
	VBox midBoxLeft, midBoxRight;
	InputStream inputStream;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;	
	
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
		
		//Creates scroll pane containing the recipe choices
		//Turns off the possibility for the scroll bar to be horizontal
		//and allows for the vertical scrolling to be on.
		//Scroll pane is the container with the scroll bar
		ScrollPane recipeList = new ScrollPane(); 
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setHbarPolicy(ScrollBarPolicy.NEVER);
		recipeList.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		recipeList.setPrefSize(midBoxLeft.getPrefWidth(), midBoxLeft.getPrefHeight());

		//Creates box to contain content for the scroll pane (i.e. recipes)
		VBox recipeListContent = new VBox(recipeCollection.getNumberOfRecipes());  //content of the scroll panel
		recipeListContent.setPrefWidth(recipeList.getPrefWidth() - 20);
		recipeList.setContent(recipeListContent);
		
		//Creates labels for the titles of the recipes
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			Label recipeTitle = new Label(recipeCollection.getRecipe(i).getInfo().getTitle()); 
		    recipeTitle.setMinSize(recipeListContent.getPrefWidth() -60, midBoxLeft.getPrefHeight()/10);
		    recipeTitle.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    recipeListContent.setPrefHeight(recipeList.getPrefHeight() + recipeTitle.getPrefHeight());
		    recipeListContent.getChildren().add(recipeTitle);
		}
		
		//Creates label to contain info of selected recipe
		String recipeInfo;
		Label recipeInfoLabel = new Label();
		recipeInfoLabel.setWrapText(true);
		recipeInfoLabel.setStyle("-fx-border-color:red; -fx-background-color: beige;");
		recipeInfo ="Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here";
		recipeInfoLabel.setText(recipeInfo);
		recipeInfoLabel.setMinSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()*0.4);
		
		//Creates scroll pane containing the ingredients selected
		//Turns off the possibility for the scroll bar to be horizontal
		//and allows for the vertical scrolling to be on.
		//Scroll pane is the container with the scroll bar
		ScrollPane ingredientsList = new ScrollPane(); 
		ingredientsList.setStyle("-fx-background: lightgrey;");
		ingredientsList.setHbarPolicy(ScrollBarPolicy.NEVER);
		ingredientsList.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		ingredientsList.setPrefSize(midBoxRight.getPrefWidth(), midBoxRight.getPrefHeight()*0.4);
		
		Button addToShoppingListBtn = new Button("Add To Shopping List");
		addToShoppingListBtn.setPrefSize(midBoxRight.getPrefWidth()/4, midBoxRight.getPrefHeight()-(recipeInfoLabel.getMinHeight()+ingredientsList.getPrefHeight()));
		
		addToShoppingListBtn.setOnMouseClicked(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				System.out.println("Add to Shopping List CLicked");
			}
			
		});
		
		midBoxRight.setAlignment(Pos.TOP_CENTER);
		midBoxLeft.getChildren().add(recipeList);
		midBoxRight.getChildren().addAll(recipeInfoLabel,ingredientsList,addToShoppingListBtn);
		midBox.getChildren().addAll(midBoxLeft,midBoxRight);
		
		//Box where all content of the IngredientsScreen class are collated 
		bigBox.getChildren().addAll(topBox,midBox);
	}
}
