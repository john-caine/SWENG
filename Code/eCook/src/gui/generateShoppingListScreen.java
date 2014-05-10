/*
 * Programmer: Zayyad Tagwai
 * Date Created: 07/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class generateShoppingListScreen {
	InputStream inputStream;
	ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, closeIcon, minimiseIcon;	
	String shoppingListPreviewText;
	HBox topBox, topBoxRight, topBoxLeft;
	
	public generateShoppingListScreen(VBox bigBox, double height, double width) {
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
         	root.getChildren().add(new MainMenu().bigBox);
         	stage.show();
            }
        });
         
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
		
		
         
		VBox leftBox = new VBox();
		VBox midBox = new VBox(40);
		HBox midBoxBottom = new HBox(20);
		VBox rightBox = new VBox();
		
		
		leftBox.setPrefSize(width*0.2, height-topBox.getPrefHeight());
		midBox.setPrefSize(width*0.6, height-topBox.getPrefHeight());
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2, height-topBox.getPrefHeight());
	
		Label shoppingListPreviewLabel = new Label();
		shoppingListPreviewLabel.setWrapText(true);
		shoppingListPreviewLabel.setStyle("-fx-border-color:red; -fx-background-color: beige;");
		shoppingListPreviewText = "Shopping list preview goes here shopping list preview goes hereshopping list preview goes hereshopping list preview goes hereshopping list preview goes hereshopping list preview goes hereshopping list preview goes hereshopping list preview goes hereshopping list preview goes here";
		shoppingListPreviewLabel.setText(shoppingListPreviewText);
		
		shoppingListPreviewLabel.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight()*2/3);
		
		
		Button saveBtn = new Button("Save");
		Button printBtn = new Button("Print");
		saveBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		printBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		midBox.setAlignment(Pos.CENTER);
		midBoxBottom.setAlignment(Pos.CENTER);
		midBoxBottom.getChildren().addAll(saveBtn, printBtn);
		midBox.getChildren().addAll(shoppingListPreviewLabel, midBoxBottom);
		
		
		
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);
	
	}

}
