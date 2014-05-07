/*
 * Programmer: Zayyad Tagwai
 * Date Created: 19/03/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


public class RecipeScreen {
	InputStream inputStream;
	ImageView homeLogo;
	ImageView logoHolder2;
	Image image1, image2;	
	
	
	
	public RecipeScreen(final VBox bigBox, final double height, final double width){
//		homeLogo = new ImageView();
//		try {
//			inputStream = new FileInputStream("../Resources/home3.png");
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//		image1 = new Image(inputStream);
//		homeLogo.setImage(image1);
//		
//		logoHolder2 = new ImageView();
//		try {
//			inputStream = new FileInputStream("../Resources/eCookLogo.png");
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//		image2 = new Image(inputStream);
//		logoHolder2.setImage(image2);
//		
//		HBox topBox = new HBox(width/2 - image1.getWidth() - (image2.getWidth()/2));
//		topBox.setPrefHeight(height/10);
//		topBox.setPrefWidth(width);
//		topBox.getChildren().add(homeLogo);
//		topBox.getChildren().add(logoHolder2);
         
		VBox leftBox = new VBox();
		VBox midBox = new VBox();
		VBox rightBox = new VBox();
		
		leftBox.setPrefSize(width*0.15, height);
		midBox.setPrefSize(width*0.7, height);
		midBox.setPadding(new Insets(10,0,10,0));
		rightBox.setPrefSize(width*0.15, height);
		

		
		ScrollPane recipeList = new ScrollPane(); //recipeList is the scroll panel
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setPrefSize((width*0.7) , height- topBox.getPrefHeight() - 80);
		System.out.println("scroll pane height: " + recipeList.getPrefHeight() + "scroll pane width: " + recipeList.getPrefWidth());
		
		VBox listContent = new VBox(10);  //content of the scroll panel
		listContent.setPrefWidth(recipeList.getPrefWidth() - 20);
		recipeList.setContent(listContent);
		
		
		
		Label testLabel = new Label("Test"); 
		testLabel.setMinSize((width/2) - 30, midBox.getPrefHeight()/4.5 );
	    testLabel.setStyle("-fx-border-color:black; -fx-background-color: white;");
		testLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				System.out.println("Label CLicked");
				bigBox.getChildren().clear();
				new IngredientsScreen(bigBox, height, width);
			}
			
		});
		listContent.getChildren().add(testLabel);
		
		
		for (int i = 0; i < 50; i++)
		{
		    Label tempList = new Label("Label " + i); //tempList = temporary list; recipes that make up the content
		    
		    tempList.setMinSize((width/2) - 30, midBox.getPrefHeight()/4.5);
		    tempList.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    
		    listContent.setPrefHeight(recipeList.getPrefHeight() + tempList.getPrefHeight());
		    recipeList.setPrefHeight(100);
		    listContent.getChildren().add(tempList);
		    
			
		    
		}
		FadeTransition fadeTransition 
        = new FadeTransition(Duration.millis(1000), bigBox);
		 fadeTransition.setFromValue(0.0);
		 fadeTransition.setToValue(1.0);
		 fadeTransition.play();
		 
		homeLogo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	Group root = (Group) source.getScene().getRoot();
         	root.getChildren().clear();
         	root.getChildren().add(new MainMenu().bigBox);
         	stage.show();
            }
        });
		
		midBoxRight.getChildren().add(recipeList);
		
		midBox.setAlignment(Pos.TOP_CENTER);
		midBox.getChildren().addAll(midBoxLeft,midBoxRight);
		bigBox.getChildren().add(topBox);
		bigBox.getChildren().add(midBox);
	}
	 
}
