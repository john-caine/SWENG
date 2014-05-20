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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MainMenu{
	
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
	
	public MainMenu(final Stage stage) {
		//Gets the visial bounds of the screen
		screenBounds = Screen.getPrimary().getVisualBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();
		
		
		//Imports eCook logo, home, close and minimise button icons
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
         	root.getChildren().add(new MainMenu(stage).bigBox);
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
        topBoxRight = new HBox(0);
		
		topBoxRight.setPrefSize(width/2, height*0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width/2, height*0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);
		topBox.setPadding(new Insets(10, 45, 0, 40));
		
		topBoxLeft.getChildren().add(homeHolder);
		topBoxRight.getChildren().addAll(minimiseBtnHolder,closeBtnHolder);
		topBox.getChildren().addAll(topBoxLeft,topBoxRight);
		
		
		bigBox = new VBox();
		midBox = new HBox();
		bottomBox = new HBox(40);
		bottomBox.setPadding(new Insets(0, 45, 0, 40));
		bottomBox.setAlignment(Pos.CENTER);
		
		bigBox.setPrefSize(width, height);
		bigBox.setMaxSize(width, height);
		
		//Sets size and location parameters for the midBox and bottomBox
		midBox.setPrefSize(width, height * 0.6);
		bottomBox.setPrefSize(width, height * 0.3);
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
		
		System.out.println(loadExtBtn.getMinHeight() +"+"+ loadExtBtn.getMinWidth());
		
		//adding the stylesheet to each of the buttons
		loadExtBtn.getStylesheets().add("file:../Resources/css.css");
		generateListBtn.getStylesheets().add("file:../Resources/css.css");
		ingredientsPickBtn.getStylesheets().add("file:../Resources/css.css");
		recipesBtn.getStylesheets().add("file:../Resources/css.css");
		
		bottomBox.getChildren().add(loadExtBtn);
		bottomBox.getChildren().add(generateListBtn);
		bottomBox.getChildren().add(ingredientsPickBtn);
		bottomBox.getChildren().add(recipesBtn);
		
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
	            	final Stage dialog = new Stage();
	                dialog.initModality(Modality.APPLICATION_MODAL);
	                dialog.initStyle(StageStyle.UNDECORATED);
	                dialog.initOwner(stage);
	                
	                
	                Button urlBtn = new Button("HTTP://");
	                Button fileBrowserBtn = new Button("Browse...");
	                urlBtn.setPrefSize(230,250);
	                fileBrowserBtn.setPrefSize(230,250);
	                VBox loadExtBox = new VBox(20);
	                HBox topBox = new HBox();
	                HBox midBox = new HBox(10); 
	                topBox.setAlignment(Pos.TOP_RIGHT);
	                
	                //New imageView required as using the old one moves its content as well
	                //Adds image onto the box containing the load Ext window made according to the 
	                //GUI spec
	        		ImageView loadExtWinCloseBtnHolder = new ImageView();
	        		try {
	        			inputStream = new FileInputStream("../Resources/redx.png");
	        		} catch (FileNotFoundException e1) {
	        			// TODO Auto-generated catch block
	        			e1.printStackTrace();
	        		} 
	        		Image closeIcon = new Image(inputStream);
	        		loadExtWinCloseBtnHolder.setImage(closeIcon);
	                topBox.getChildren().add(loadExtWinCloseBtnHolder);
	                midBox.getChildren().addAll(urlBtn,fileBrowserBtn);
	                midBox.setAlignment(Pos.CENTER);
	                loadExtBox.getChildren().addAll(topBox,midBox);
	                loadExtBox.setStyle("-fx-background-color: lightgrey");
	                Scene dialogScene = new Scene(loadExtBox, 500, 300);
	                dialogScene.fillProperty().set(Color.BLUE);
	                dialog.setScene(dialogScene);
	                dialog.show();
	                
	                //Adds action to the buttons and makes it close upon 
	                //selection of option
	        		urlBtn.setOnAction(new EventHandler<ActionEvent>() {
	        			public void handle(ActionEvent event) {
	            	
	        				System.out.println("URL BUTTON CLICKED");
	        				dialog.close();
	        			}
	        		});
	  		
	        		fileBrowserBtn.setOnAction(new EventHandler<ActionEvent>() {
	        			public void handle(ActionEvent event) {
	        				
	        				System.out.println("FILE BROWSER BUTTON CLICKED");
	        				dialog.close();
	        			}
	        		});
	        		loadExtWinCloseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
	                    public void handle(MouseEvent mouseEvent) {
	                    Node  source = (Node)  mouseEvent.getSource();
	                 	Stage dialog= (Stage) source.getScene().getWindow();
	                 	dialog.close();
	                    }
	                });
	        		
	        		dialog.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	        		        public void handle(KeyEvent t) {
	        		          if(t.getCode()==KeyCode.ESCAPE)
	        		          {
	        		        	  dialog.close();
	        		          }
	        		        }
	        		    });
	        		
	            }
	        });
		
		 //Clears the bigBox and runs generateShoppingListScreen passing
		 //the bigBox, height and width to the class
		generateListBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bigBox.getChildren().clear();  
                new generateShoppingListScreen(bigBox, height, width);
            }
        });
		//Clears the bigBox and runs IngredientsScreen passing
		//the bigBox, height and width to the class
		ingredientsPickBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bigBox.getChildren().clear();
                new IngredientsScreen(bigBox, height, width);
            }
        });
		//Clears the bigBox and runs RecipeSceen passing 
		//the bigBox, height and width to the class
		recipesBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                bigBox.getChildren().clear();
                new RecipeScreen(bigBox, height, width);
            }
        });

		
	}
}
