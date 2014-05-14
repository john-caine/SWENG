package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
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

public class IngredientsScreen {
	
	
	double width;
	double height;
	HBox topBox;
	HBox midBox;
	InputStream inputStream;
	ImageView logoHolder1;
	ImageView logoHolder2;
	ImageView recipeImage;
	Image image1, image2;	
	VBox bigBox;
	Label blank;
	
	public IngredientsScreen(final VBox bigBox, final double height, final double width){
		ImageView homeLogo = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/home3.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		image1 = new Image(inputStream);
		homeLogo.setImage(image1);
		
		logoHolder2 = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/eCookLogo.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		image2 = new Image(inputStream);
		logoHolder2.setImage(image2);
		
		homeLogo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage stage  = (Stage) source.getScene().getWindow();
         	Group root = (Group) source.getScene().getRoot();
         	root.getChildren().clear();
         	root.getChildren().add(new MainMenu(stage).bigBox);
         	stage.show();
            }
        });
		
		HBox topBox = new HBox(width/2 - image1.getWidth() - (image2.getWidth()/2));
		topBox.setPrefHeight(height/10);
		topBox.setPrefWidth(width);
		topBox.getChildren().add(homeLogo);
		topBox.getChildren().add(logoHolder2);
		
		HBox midBox = new HBox();
		midBox.setPrefHeight(height - topBox.getPrefHeight());
		midBox.setPrefWidth(width);
		
		VBox midBoxLeft = new VBox(20);
		midBoxLeft.setPadding(new Insets(0, 20, 0, 20));
		midBoxLeft.setPrefSize((width/2) - 10, height- topBox.getPrefHeight() );
		
	
		VBox midBoxRight = new VBox(10);
		midBoxRight.setPrefSize((width/2) - 10, height- topBox.getPrefHeight() );
		
		HBox midBoxRightTop = new HBox(10);
		midBoxRightTop.setPrefSize((width/2) - 10, midBoxRight.getPrefHeight()/3 );
		
		HBox midBoxRightBottom = new HBox();
		midBoxRightBottom.setPrefSize((width/2) - 10, midBoxRight.getPrefHeight()-midBoxRightTop.getPrefHeight() );
		//midBoxRightBottom.setMaxSize((width/2) - 10, midBoxRight.getPrefHeight()-midBoxRightTop.getPrefHeight());
		
		
		ScrollPane IngredientsList = new ScrollPane(); //recipeList is the scroll panel
		IngredientsList.setStyle("-fx-background: lightgrey;");
		IngredientsList.setPrefSize(midBoxLeft.getPrefWidth(), midBoxLeft.getPrefHeight());
		System.out.println("scroll pane height: " + IngredientsList.getPrefHeight() + "scroll pane width: " + IngredientsList.getPrefWidth());
		
		VBox listContent = new VBox(10);  //content of the scroll panel
		listContent.setPrefWidth(IngredientsList.getPrefWidth() - 20);
		IngredientsList.setContent(listContent);
			
		Button shoppingListBtn = new Button("Make Shopping List");
		Button playSlideBtn = new Button("How to Prepare");
		
		shoppingListBtn.setPrefSize(midBoxRightTop.getPrefWidth()/2 -5, midBoxRightTop.getPrefHeight());
		playSlideBtn.setPrefSize(midBoxRightTop.getPrefWidth()/2 -5, midBoxRightTop.getPrefHeight());
	
		recipeImage = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/soup.jpg");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		image1 = new Image(inputStream);
		recipeImage.setImage(image1);
		recipeImage.setPreserveRatio(false);
		recipeImage.setFitHeight(midBoxRightBottom.getPrefHeight()-10);
		recipeImage.setFitWidth(midBoxRightBottom.getPrefWidth());
		
		
		
		bigBox.setPrefHeight(height);
		bigBox.setPrefWidth(width);
		System.out.println("bigBox height " + bigBox.getPrefHeight() + " bigBox width: " + bigBox.getPrefWidth());
		System.out.println("top height: " + topBox.getPrefHeight());
		System.out.println("topBox height: " + topBox.getPrefHeight() + " topBox width: " + topBox.getPrefWidth());
		System.out.println("midBox height: " + midBox.getPrefHeight() + " midBox width: " + midBox.getPrefWidth());
		System.out.println("midLeft height: " + midBoxLeft.getPrefHeight() + " midLeft width: " + midBoxLeft.getPrefWidth());
		System.out.println("midRight height: " + midBoxRight.getPrefHeight() + " midRight width: " + midBoxRight.getPrefWidth());
		System.out.println("midRightTop height: " + midBoxRightTop.getPrefHeight() + " midRightTop: " + midBoxRightTop.getPrefWidth());
		System.out.println("midRightBottom height: " + midBoxRightBottom.getPrefHeight() + " midRightBottom width: " + midBoxRightBottom.getPrefWidth());
		System.out.println("imageView height: " + recipeImage.getFitHeight() + "imageView width: " + recipeImage.getFitWidth());
		
		midBoxLeft.getChildren().add(IngredientsList);
		midBoxRightTop.getChildren().addAll(shoppingListBtn,playSlideBtn);
		midBoxRightBottom.getChildren().add(recipeImage);
		midBoxRight.getChildren().addAll(midBoxRightTop,midBoxRightBottom);
		midBox.getChildren().addAll(midBoxLeft,midBoxRight);
		bigBox.getChildren().addAll(topBox,midBox);
	}
}
