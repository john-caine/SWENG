package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	
GridPane borders = new GridPane();//Where all window components (buttons, labels etc.) are added
HBox bigBox = new HBox();

public static void main(String[] args) {
    launch(args);
}
 

public void start(Stage primaryStage) {
		
		try {
			Scene scene = new Scene(bigBox,640,480);
			scene.setFill(Color.WHITE);
			primaryStage.setScene(scene);
			
			borders.setPrefHeight(400);
			borderContent() ;
			borders.setHgap(0); //horizontal gap in pixels => that's what you are asking for
			borders.setVgap(30); //vertical gap in pixels
			borders.setPadding(new Insets(10, 10, 10, 10)); 
			
			bigBox.getChildren().add(borders);
			bigBox.setVisible(false);
			
			 FadeTransition fadeTransition 
             = new FadeTransition(Duration.millis(3000), bigBox);
			 fadeTransition.setFromValue(0.0);
			 fadeTransition.setToValue(1.0);
			 fadeTransition.play();
			 
			 bigBox.setVisible(true);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void borderContent (){
		
		
		//TOP: Logo Pane and calendar thing using HBOX
		HBox topBox = new HBox(100);
		ImageView logoHolder1 = new ImageView("SpoonSmall.png");
		//logoHolder1.setImage(new Image("../Resources/SpoonSmall.png"));
		ImageView logoHolder2 = new ImageView("eCookLogo.png");
		//logoHolder2.setImage(new Image("../Resources/eCookLogo.png"));
		topBox.getChildren().add(logoHolder1);
		topBox.getChildren().add(logoHolder2);
		
		borders.addRow(0, topBox);
		
		//MID: Button Panel using VBOX
		HBox midBox = new HBox(0);
		Button recipeBtn = new Button("Recipes");
		Button kitchenBasicsBtn = new Button("Kitchen Basics");
		Button threeIngredientMealsBtn = new Button("3 Ingredient Meals");
		Button onlineStoreBtn = new Button("Online Store");
		
		//BUTTON LAYOUT
		recipeBtn.setId("b1");
		kitchenBasicsBtn.setId("b2");
		threeIngredientMealsBtn.setId("b3");
		onlineStoreBtn .setId("b4");
		
		recipeBtn.setMinSize(150, 150);
		kitchenBasicsBtn.setMinSize(150, 150);
		threeIngredientMealsBtn.setMinSize(150, 150);
		onlineStoreBtn .setMinSize(150, 150);
		
		recipeBtn.getStylesheets().add("css.css");
		kitchenBasicsBtn.getStylesheets().add("css.css");
		threeIngredientMealsBtn.getStylesheets().add("css.css");
		onlineStoreBtn .getStylesheets().add("css.css");

		
	
		midBox.getChildren().add(recipeBtn);
		midBox.getChildren().add(kitchenBasicsBtn);
		midBox.getChildren().add(threeIngredientMealsBtn);
		midBox.getChildren().add(onlineStoreBtn);
		
		borders.addRow(1, midBox);
	
		
		//BUTTON ACTIONS
		recipeBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
               // System.out.println("Recipe Clicked");
             
                bigBox.getChildren().remove(borders); //for 'changing' windows by removing the boxes where stuff is contained and replacing with other boxes 
                new RecipeScreen(bigBox);	
            }
        });
		
		kitchenBasicsBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                System.out.println("Kitchen Basics Clicked");
                bigBox.getChildren().remove(borders); //for 'changing' windows by removing the boxes where stuff is contained and replacing with other boxes 
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
	
	}

}

