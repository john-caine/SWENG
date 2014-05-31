/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 19/03/2014
 * Makes Box conatining components of main menu upon opening the program 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import eCook.RecipeCollection;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuContent {
	
	private Rectangle2D screenBounds;
	private double width;
	private double height;
	private HBox topBox, topBoxRight, topBoxLeft;
	private HBox midBox;
	private HBox bottomBox;
	private InputStream inputStream;
	private ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	private Image homeIcon;
	private Image logoIcon, closeIcon, minimiseIcon;	
	private Tooltip h,c,m;
	public VBox bigBox;
	protected Stage dialog;
	
	public MainMenuContent(final Stage stage, final RecipeCollection recipeCollection) {
		//Gets the visual bounds of the screen
		screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		
		//Imports eCook logo, home, close and minimise button icons and set tootips
		logoholder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/eCookLogo1.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		logoIcon = new Image(inputStream);
		
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
		
		//Add tool tip
		h = new Tooltip("Home");
		Tooltip.install(homeHolder, h);
		c = new Tooltip("Close");
		Tooltip.install(closeBtnHolder, c);
		m = new Tooltip("Minimise");
		Tooltip.install(minimiseBtnHolder, m);
		
		
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
         
		logoholder.setImage(logoIcon);
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
		homeHolder.setImage(homeIcon);
	
		//Creates a box containing the menu bar
		//Sets size and location parameters for eCook's menu bar containing home, minimse and close buttons
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
		
		//
		bigBox = new VBox();
		midBox = new HBox();
		bottomBox = new HBox(40);
		bottomBox.setPadding(new Insets(0, 45, 0, 40));
		bottomBox.setAlignment(Pos.CENTER);
		
		bigBox.setPrefSize(width, height+100);
		bigBox.setMaxSize(width, height+100);
		bigBox.setStyle("-fx-background-size: cover; -fx-background-position: center center;-fx-background-image: url('file:../Resources/IMG_4906.jpg');");
		
		//Sets size and location parameters for the midBox and bottomBox
		midBox.setPrefSize(width, height * 0.6);
		bottomBox.setPrefSize(width, height*0.3);
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().add(logoholder);
		
		//Creates the main menu options
		Button loadExtBtn = new Button("Load External Recipe");
		Button generateListBtn = new Button("Generate Shopping List");
		Button ingredientsPickBtn = new Button("Ingredient Picker");
		Button recipesBtn= new Button("Recipes");
		
		//Defining the CSS Identifiers
		loadExtBtn.setId("loadExtBtn");
		generateListBtn.setId("generateListBtn");
		ingredientsPickBtn.setId("ingredientsPickBtn");
		recipesBtn.setId("recipesBtn");
		
		//Setting the sizes of the buttons relative to the size of the screen
		loadExtBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		generateListBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		ingredientsPickBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		recipesBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		
		//adding the stylesheet to each of the buttons
		loadExtBtn.getStylesheets().add("file:../Resources/css.css");
		generateListBtn.getStylesheets().add("file:../Resources/css.css");
		ingredientsPickBtn.getStylesheets().add("file:../Resources/css.css");
		recipesBtn.getStylesheets().add("file:../Resources/css.css");

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
