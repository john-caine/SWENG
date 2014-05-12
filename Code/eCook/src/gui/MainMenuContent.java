/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 19/03/2014
 * Makes Box conatining components of main menu upon opening the program 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuContent {
	
	Rectangle2D screenBounds;
	double width;
	double height;
	HBox topBox, topBoxRight, topBoxLeft;
	HBox midBox;
	HBox bottomBox;
	InputStream inputStream;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;	
	public VBox bigBox;
	Label blank;
	
	public MainMenuContent() {
		screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		logoholder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/eCookLogo.png");
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
		
		
		
		//CLOSE
	    closeBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	stage.close();
            }
        });
		
		//MINIMISE
		minimiseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
		    public void handle(MouseEvent mouseEvent) {
		    	Node  source = (Node)  mouseEvent.getSource();
		    	Stage stage  = (Stage) source.getScene().getWindow(); 
		    	stage.setIconified(true);
		    }
		});
		
		homeHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	Group root = (Group) source.getScene().getRoot();
         	root.getChildren().clear();
         	root.getChildren().add(new MainMenuContent().bigBox);
         	stage.show();
            }
        });
         
		logoholder.setImage(logoIcon);
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
		homeHolder.setImage(homeIcon);
	
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
		
		
		
		bigBox = new VBox();
		final StackPane overlay = new StackPane();
		midBox = new HBox();
		bottomBox = new HBox(40);
		bottomBox.setPadding(new Insets(0, 45, 0, 40));
		bottomBox.setAlignment(Pos.CENTER);
		
		bigBox.setPrefSize(width, height);
		bigBox.setMaxSize(width, height);

		midBox.setPrefSize(width, height * 0.6);
		bottomBox.setPrefSize(width, height * 0.3);
		

		
		
		//MID: Button Panel using HBOX
		
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().add(logoholder);
		MenuButton loadExtBtn = new MenuButton("Load External Recipe");
		
		MenuItem fileBrowserBtn;
		MenuItem urlBtn;
		loadExtBtn.getItems().addAll(urlBtn = new MenuItem("HTTP://"),fileBrowserBtn = new MenuItem("File Browser"));
		
		Button generateListBtn = new Button("Generate Shopping List");
		Button ingredientsPickBtn = new Button("Ingredient Picker");
		Button recipesBtn= new Button("Recipes");
		
		
		
		//BUTTON LAYOUT
		loadExtBtn.setId("b1");
		generateListBtn.setId("b2");
		ingredientsPickBtn.setId("b3");
		recipesBtn.setId("b4");
		
		loadExtBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		
		generateListBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		ingredientsPickBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		recipesBtn.setMinSize(width/5,bottomBox.getPrefHeight());
		
		loadExtBtn.getStylesheets().add("css.css");
		generateListBtn.getStylesheets().add("css.css");
		ingredientsPickBtn.getStylesheets().add("css.css");
		recipesBtn.getStylesheets().add("css.css");
		
		bottomBox.getChildren().add(loadExtBtn);
		bottomBox.getChildren().add(generateListBtn);
		bottomBox.getChildren().add(ingredientsPickBtn);
		bottomBox.getChildren().add(recipesBtn);
		
		overlay.getChildren().addAll(bottomBox);
		overlay.setPrefSize(width, height * 0.3);
		bigBox.getChildren().addAll(topBox,midBox,overlay);

		FadeTransition fadeTransition 
         = new FadeTransition(Duration.millis(500), bigBox);
		 fadeTransition.setFromValue(0.0);
		 fadeTransition.setToValue(1.0);
		 fadeTransition.play();
		 
		 
		
		//BUTTON ACTIONS
		urlBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	System.out.println("URL BUTTON CLICKED");
            	
            	
            
            }
        });
		
		fileBrowserBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	System.out.println("FILE BROWSER BUTTON CLICKED");
            	
            	
            
            }
        });
		
		generateListBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                
                bigBox.getChildren().clear(); //for 'changing' windows by removing the boxes where stuff is contained and replacing with other boxes 
                new generateShoppingListScreen(bigBox, height, width);
            }
        });
		
		ingredientsPickBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bigBox.getChildren().clear();
//                new IngredientPickerScreen(bigBox, height, width);
                new IngredientsScreen(bigBox, height, width);
            }
        });
		
		recipesBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bigBox.getChildren().clear();
                new RecipeScreen(bigBox, height, width);
            }
        });

		
	}
}
