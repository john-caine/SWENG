/*
 * Programmer: Zayyad Tagwai
 * Date Created: 07/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.pdmodel.PDDocument;


import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	Label statusBar;
	Button saveBtn, printBtn;
	
	public generateShoppingListScreen(VBox bigBox, double height, double width) {
		
		//Imports eCook logo, home, close and minimise button icons
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
         	root.getChildren().add(new MainMenu(stage).bigBox);
         	stage.show();
            }
        });
         
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder.setImage(closeIcon);
		homeHolder.setImage(homeIcon);
		
		//Creates a box containing the menu bar
		//Sets size and location parameters for eCook's menu bar containing home, minimse and close buttons
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
		
		//Sets  parameters for the leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height-topBox.getPrefHeight());
		midBox.setPrefSize(width*0.6, height-topBox.getPrefHeight());
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2, height-topBox.getPrefHeight());
		
		// create a text area to show the shopping list
		TextArea text = new TextArea();
		text.setEditable(false);
		text.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight()*2/3);

		//Buttons for saving and printing the shopping list
		saveBtn = new Button("Save");
		printBtn = new Button("Print");
		saveBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		printBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		
		// set up the status bar
		statusBar = new Label("");
		
		// populate the text area
		
		
		//Sets actions to be performed when saveBtn is clicked
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	
            	statusBar.setText("Shopping list saved to PDF");
            }
        });
		
		//Sets actions to be performed when printBtn is clicked
		printBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	File fileToRead = new File("Your Shopping List.pdf");
        		if (fileToRead.exists()) {
        			
        		}
        		// if no PDF exists, create one first
        		else {
        			
        			
        		}
            }
        });
		midBox.setAlignment(Pos.CENTER);
		midBoxBottom.setAlignment(Pos.CENTER);
		midBoxBottom.getChildren().addAll(saveBtn, printBtn);
		midBox.getChildren().addAll(new Label("Shopping List"), text, statusBar, midBoxBottom);
		
		//Horizontal aligns content horizontally 
		//bigBox collecting all content of generateShoppingListScreen
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);
	
	}
	

	
}