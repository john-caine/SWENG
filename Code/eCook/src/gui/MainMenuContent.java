/*
 * Programmer: Zayyad Tagwai, Roger Tan, Ankita Gangotra and James Oatley
 * Date Created: 19/03/2014
 * Makes Box conatining components of main menu upon opening the program 
 */

package gui;

import eCook.RecipeCollection;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuContent extends menu{

	private HBox midBox;
	private HBox bottomBox;
	private ImageView logoholder;
	private Image logoIcon;	
	public VBox bigBox;
	protected Stage stage;

	public MainMenuContent(final Stage stage, final RecipeCollection recipeCollection) {
		super (recipeCollection);
		this.stage =  stage;

		//Imports eCook logo
		logoholder = new ImageView();	
		logoIcon = new Image("logo_board.png");
		logoholder.setImage(logoIcon);

		//Add bigBox, midBox, bottomBox
		bigBox = new VBox();
		midBox = new HBox();
		bottomBox = new HBox(40);
		bottomBox.setPadding(new Insets(0, 45, 0, 40));
		bottomBox.setAlignment(Pos.CENTER);

		//bigBox height, width and background
		bigBox.setPrefSize(width, height);
		bigBox.setMaxSize(width, height);
		bigBox.setId("MainMenuContentBigBox");
		bigBox.getStylesheets().add("css.css");

		//Sets size and location parameters for the midBox and bottomBox
		midBox.setPrefSize(width, height * 0.6);
		bottomBox.setPrefSize(width, height*0.3);
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().add(logoholder);

		//Creates the main menu options
		Button loadExtBtn = new Button("");
		Button generateListBtn = new Button("");
		Button ingredientsPickBtn = new Button("");
		Button recipesBtn= new Button("");

		//Defining the CSS Identifiers
		loadExtBtn.setId("loadExtBtn");
		generateListBtn.setId("generateListBtn");
		ingredientsPickBtn.setId("ingredientsPickBtn");
		recipesBtn.setId("recipesBtn");

		//Setting the sizes of the buttons relative to the size of the screen
		loadExtBtn.setMinSize(width/5,bottomBox.getPrefHeight()-40);
		generateListBtn.setMinSize(width/5,bottomBox.getPrefHeight()-40);
		ingredientsPickBtn.setMinSize(width/5,bottomBox.getPrefHeight()-40);
		recipesBtn.setMinSize(width/5,bottomBox.getPrefHeight()-40);

		//adding the stylesheet to each of the buttons
		loadExtBtn.getStylesheets().add("css.css");
		generateListBtn.getStylesheets().add("css.css");
		ingredientsPickBtn.getStylesheets().add("css.css");
		recipesBtn.getStylesheets().add("css.css");

		//Set tool tip
		loadExtBtn.setTooltip(new Tooltip("Click here to load external recipes form the internet or locally"));
		generateListBtn.setTooltip(new Tooltip("Click here to make shopping list from ingredients chosen"));
		ingredientsPickBtn.setTooltip(new Tooltip("Click here to choose ingredients from list of recipes"));
		recipesBtn.setTooltip(new Tooltip("Click here to choose from a list of recipes"));

		bottomBox.getChildren().add(recipesBtn);
		bottomBox.getChildren().add(ingredientsPickBtn);
		bottomBox.getChildren().add(generateListBtn);
		bottomBox.getChildren().add(loadExtBtn);		

		bigBox.getChildren().addAll(topBox,midBox,bottomBox);

		FadeTransition fadeTransition 
		= new FadeTransition(Duration.millis(500), bigBox);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);
		fadeTransition.play();


		//BUTTON ACTIONS
		//Making the Load External Recipe button make a new window that takes the focus off the main stage
		//When any of the options are chosen, the new window closes
		//New window allows for closing using the ESC button

		loadExtBtn.setOnAction(new EventHandler<ActionEvent>() {
			//Creates a new stage bound to the previous that lets the user
			//pick between the two options
			public void handle(ActionEvent event) {
				new LoadExternalRecipe(stage, recipeCollection);
			}
		});

		//Clears the bigBox and runs generateShoppingListScreen passing
		//the bigBox, height and width to the class
		generateListBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				bigBox.getChildren().clear();  
				new GenerateShoppingListScreen(bigBox, height, width, recipeCollection);
			}
		});
		//Clears the bigBox and runs IngredientsScreen passing
		//the bigBox, height and width to the class
		ingredientsPickBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				bigBox.getChildren().clear();
				new IngredientsScreen(bigBox, height, width, recipeCollection);
			}
		});
		//Clears the bigBox and runs RecipeSceen passing 
		//the bigBox, height and width to the class
		recipesBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				bigBox.getChildren().clear();
				new RecipeScreen(bigBox, height, width, recipeCollection, stage);
			}
		});


	}
}
