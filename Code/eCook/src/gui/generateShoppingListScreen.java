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

import shoppingList.PDFCreator;
import shoppingList.ShoppingList;

import eCook.RecipeCollection;

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
	
	public generateShoppingListScreen(VBox bigBox, double height, double width, final RecipeCollection recipeCollection) {
		
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
         	root.getChildren().add(new MainMenuContent(stage, recipeCollection).bigBox);
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
        topBoxRight = new HBox(5);
		
		topBoxRight.setPrefSize(width/2, height*0.1);
		topBoxRight.setAlignment(Pos.TOP_RIGHT);
		topBoxLeft.setPrefSize(width/2, height*0.1);
		topBoxLeft.setAlignment(Pos.TOP_LEFT);
		
		topBox.setPadding(new Insets(10, 45, 0, 40));
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
		getShoppingList(text);
		
		//Sets actions to be performed when saveBtn is clicked
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	new PDFCreator(getShoppingList(null).readFromTextFile());
            	statusBar.setText("Shopping list saved to PDF");
            }
        });
		
		//Sets actions to be performed when printBtn is clicked
		printBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	File fileToRead = new File("Your Shopping List.pdf");
        		if (fileToRead.exists()) {
        			printShoppingList();
        		}
        		// if no PDF exists, create one first
        		else {
        			new PDFCreator(getShoppingList(null).readFromTextFile());
        			printShoppingList();
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
	
	// method to get the shopping list and create a Text Area
	public ShoppingList getShoppingList(TextArea text) {
		// read the shopping list file
		ShoppingList list = new ShoppingList();
		ArrayList<String> shoppingList = new ArrayList<String>();
		shoppingList = list.readFromTextFile();
		
		// loop through list adding items to the text area
		if (text != null) {
			if (shoppingList != null && shoppingList.size() != 0) {
				for (int i=0; i<shoppingList.size(); i++) {
					text.appendText(shoppingList.get(i) + "\n");
				}
				statusBar.setText("You have " + shoppingList.size() + " items in your shopping list");
			}
			else {
				text.setText("No items in shopping list");
				saveBtn.setDisable(true);
				printBtn.setDisable(true);
			}
		}
		
		return list;
	}
	
	// method to print the shopping list PDF
	public void printShoppingList() {
		PDDocument pdf = null;
		try {
			pdf = PDDocument.load("Your Shopping List.pdf");
			statusBar.setText("Printing shopping list...");
			pdf.silentPrint();											// WARNING MAC USERS: This may write a big scary red error message
																		// to the console. It will print the file anyway though!
		} catch (IOException | PrinterException e) {
			statusBar.setText("Sorry. Print error: cannot print file");
			e.printStackTrace();
		}
		finally {
			if (pdf != null) {
				try {
					pdf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}