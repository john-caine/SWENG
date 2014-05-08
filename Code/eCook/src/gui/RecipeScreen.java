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
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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
	String recipeInfo;
	ImageView homeHolder, logoholder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, logoIcon, closeIcon, minimiseIcon;	
	
	
	
	public RecipeScreen(final VBox bigBox, final double height, final double width){
		
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
		homeHolder.setImage(homeIcon);
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
         
		VBox leftBox = new VBox();
		VBox midBox = new VBox(20);
		VBox rightBox = new VBox();
		HBox buttonBox = new HBox();
		
		leftBox.setPrefSize(width*0.2, height);
		midBox.setPrefSize(width*0.6, height);
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2, height);
		
	

		
		ScrollPane recipeList = new ScrollPane(); //recipeList is the scroll panel
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setMinSize(midBox.getPrefWidth() , height*0.5);
		recipeList.setHbarPolicy(ScrollBarPolicy.NEVER);
		recipeList.setVbarPolicy(ScrollBarPolicy.ALWAYS);

		System.out.println("scroll pane height: " + recipeList.getPrefHeight() + "scroll pane width: " + recipeList.getPrefWidth());
		
		VBox listContent = new VBox(10);  //content of the scroll panel
		listContent.setPrefWidth(recipeList.getPrefWidth() - 20);
		recipeList.setContent(listContent);
		
		
		
		Label testLabel = new Label("Test"); 
		testLabel.setMinSize(recipeList.getMinWidth(), midBox.getPrefHeight()/4.5 );
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
		
		recipeInfoLabel.setPrefSize(midBox.getPrefWidth(), height*1/3);
		
		
		Button playSlideBtn = new Button("How to Prepare");
		playSlideBtn.setPrefSize(midBox.getPrefWidth()/4, 40);
		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().addAll(recipeList,recipeInfoLabel, playSlideBtn);
		
		
//		FadeTransition fadeTransition 
//        = new FadeTransition(Duration.millis(1000), bigBox);
//		 fadeTransition.setFromValue(0.0);
//		 fadeTransition.setToValue(1.0);
//		 fadeTransition.play();
		 
		 	ImageView closeBtnHolder = new ImageView();
			try {
				inputStream = new FileInputStream("../Resources/redx.png");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			image2 = new Image(inputStream);
			closeBtnHolder.setImage(image2);
			
			ImageView minimiseBtnHolder = new ImageView();
			try {
				inputStream = new FileInputStream("../Resources/minimise.png");
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 

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
	         	root.getChildren().add(new MainMenu().bigBox);
	         	stage.show();
	            }
	        });
		
		leftBox.getChildren().add(homeHolder);
		rightBox.getChildren().addAll(closeBtnHolder,minimiseBtnHolder);	
		rightBox.setAlignment(Pos.TOP_RIGHT);
		rightBox.getChildren().add(buttonBox);
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().add(horizontalBox);

	}
	 
}
