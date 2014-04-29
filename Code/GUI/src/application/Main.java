package application;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
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
			borders.setHgap(0); //horizontal gap in pixels
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
			//primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void borderContent (){
		
		Rectangle2D screenBounds;
		double width;
		double height;
		
		screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		//TOP: Logo Pane and calendar thing using HBOX
		HBox topBox = new HBox(100);
		ImageView logoHolder1 = new ImageView("SpoonSmall.png");
		ImageView logoHolder2 = new ImageView("eCookLogo.png");
		topBox.getChildren().add(logoHolder1);
		topBox.getChildren().add(logoHolder2);
		
		borders.addRow(0, topBox);
		
		//MID: Button Panel using HBOX
		HBox midBox = new HBox(50);
		midBox.setMaxWidth(width);
		midBox.setPrefHeight(height/2);
		midBox.setAlignment(Pos.CENTER);
		midBox.setAlignment(Pos.BOTTOM_CENTER);
		
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

