/*
 * Programmer: Zayyad Tagwai  & Roger Tan
 * Date Created: 05/05/2014
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


public class RecipeScreen {
	InputStream inputStream;
	String recipeInfo;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;	
	HBox topBox, topBoxLeft, topBoxRight;
	
	
	public RecipeScreen(final VBox bigBox, final double height, final double width, final RecipeCollection recipeCollection){
		
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
        topBoxRight = new HBox();
		
		topBoxRight.setPrefSize(width/2, height*0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width/2, height*0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);
		
		topBoxLeft.getChildren().add(homeHolder);
		topBoxRight.getChildren().addAll(minimiseBtnHolder,closeBtnHolder);
		topBox.getChildren().addAll(topBoxLeft,topBoxRight);
		
		VBox leftBox = new VBox();
		VBox midBox = new VBox(20);
		VBox rightBox = new VBox();
			
		//Sets parameters for leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height - topBox.getPrefHeight());
		midBox.setPrefSize(width*0.6,  height - topBox.getPrefHeight());
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2,  height - topBox.getPrefHeight());

		//Creates scroll pane containing the recipe choices
		//Turns off the possibility for the scroll bar to be horizontal
		//and allows for the vertical scrolling to be on.
		//Scroll pane is the container with the scroll bar

		ScrollPane recipeList = new ScrollPane(); 
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setMinSize(midBox.getPrefWidth() , midBox.getPrefHeight()*0.5);
		recipeList.setHbarPolicy(ScrollBarPolicy.NEVER);
		recipeList.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		//Creates box to contain content for the scroll pane (i.e. recipes)
		VBox listContent = new VBox(recipeCollection.getNumberOfRecipes()); 
		listContent.setPrefWidth(recipeList.getPrefWidth() - 20);
		recipeList.setContent(listContent);
		
		//Creates a test labels of recipes
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++){
		    Label tempList = new Label(recipeCollection.getRecipe(i).getInfo().getTitle()); 
		    
		    tempList.setMinSize(midBox.getPrefWidth(), midBox.getPrefHeight()/4.5);
		    tempList.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    
		    listContent.setPrefHeight(recipeList.getPrefHeight() + tempList.getPrefHeight());
		    recipeList.setPrefHeight(100);
		    listContent.getChildren().add(tempList);
		}
		
		Label recipeInfoLabel = new Label();
		recipeInfoLabel.setWrapText(true);
		recipeInfoLabel.setStyle("-fx-border-color:red; -fx-background-color: beige;");
		recipeInfo ="Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here";
		recipeInfoLabel.setText(recipeInfo);
		recipeInfoLabel.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight() * 0.3);
		
		
		Button playSlideBtn = new Button("Play");
		playSlideBtn.setPrefSize(midBox.getPrefWidth()/4, 40);		
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().addAll(recipeList,recipeInfoLabel, playSlideBtn);
		
		//Box where all content of the RecipeScreen class are collated 
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);

	}
	 
}
