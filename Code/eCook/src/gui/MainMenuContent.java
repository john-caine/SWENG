package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import eCook.FileHandlerButton;
import eCook.MainMenu;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuContent {
	
	Rectangle2D screenBounds;
	double width;
	double height;
	HBox topBox;
	HBox midBox;
	InputStream inputStream;
	ImageView logoHolder1;
	ImageView logoHolder2;
	Image image1, image2;	
	public VBox bigBox;
	Label blank;
	
	public MainMenuContent() {
		screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		logoHolder1 = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/SpoonSmall.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		image1 = new Image(inputStream);
		logoHolder1.setImage(image1);
		
		logoHolder2 = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/eCookLogo.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		image2 = new Image(inputStream);
		logoHolder2.setImage(image2);

		bigBox = new VBox(height/2 - width/6);
		topBox = new HBox(width/2 - image1.getWidth() - (image2.getWidth()/2));
		midBox = new HBox(width/32);
		
		
		
		//TOP: Logo Pane and calendar thing using HBOX	
		topBox.getChildren().add(logoHolder1);
		topBox.getChildren().add(logoHolder2);
		
		
		//MID: Button Panel using HBOX
		midBox.setMaxWidth(width);
		midBox.setPrefHeight(height/2);
		midBox.setAlignment(Pos.CENTER);
		
		Button recipeBtn = new Button("Recipes");
		Button kitchenBasicsBtn = new Button("Kitchen Basics");
		Button threeIngredientMealsBtn = new Button("3 Ingredient Meals");
		Button onlineStoreBtn = new Button("Online Store");
		
		//BUTTON LAYOUT
		recipeBtn.setId("b1");
		kitchenBasicsBtn.setId("b2");
		threeIngredientMealsBtn.setId("b3");
		onlineStoreBtn .setId("b4");
		
		recipeBtn.setMinSize(width/5, width/5);
		kitchenBasicsBtn.setMinSize(width/5, width/5);
		threeIngredientMealsBtn.setMinSize(width/5, width/5);
		onlineStoreBtn .setMinSize(width/5, width/5);
		
		recipeBtn.getStylesheets().add("css.css");
		kitchenBasicsBtn.getStylesheets().add("css.css");
		threeIngredientMealsBtn.getStylesheets().add("css.css");
		onlineStoreBtn .getStylesheets().add("css.css");
		
		blank = new Label("");
		blank.setMinWidth(width/64);
		midBox.getChildren().add(blank);
		midBox.getChildren().add(recipeBtn);
		midBox.getChildren().add(kitchenBasicsBtn);
		midBox.getChildren().add(threeIngredientMealsBtn);
		midBox.getChildren().add(onlineStoreBtn);
		
		
		bigBox.getChildren().add(topBox);
		bigBox.getChildren().add(midBox);
		
		FadeTransition fadeTransition 
         = new FadeTransition(Duration.millis(3000), bigBox);
		 fadeTransition.setFromValue(0.0);
		 fadeTransition.setToValue(1.0);
		 fadeTransition.play();
		 
		//BUTTON ACTIONS
		recipeBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
               // System.out.println("Recipe Clicked");
             
                bigBox.getChildren().clear(); //for 'changing' windows by removing the boxes where stuff is contained and replacing with other boxes 
                new RecipeScreen(bigBox, height, width);	
            }
        });
		
		kitchenBasicsBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Kitchen Basics Clicked");
                bigBox.getChildren().clear(); //for 'changing' windows by removing the boxes where stuff is contained and replacing with other boxes 
                new KitchenBasicsScreen();	
            }
        });
		
		threeIngredientMealsBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("3 Ingredient Meals Clicked");
            }
        });
		
		onlineStoreBtn .setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Online Store Clicked");
            }
        });
		
	    logoHolder1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	Group root = (Group) source.getScene().getRoot();
         	root.getChildren().clear();
         	root.getChildren().add(new MainMenuContent().bigBox);
         	FileHandlerButton fileHandlerButton = new FileHandlerButton(stage);	
         	root.getChildren().add(fileHandlerButton.fileHandlerButtonHbox);
         	stage.show();
            }
        });
	}
}
