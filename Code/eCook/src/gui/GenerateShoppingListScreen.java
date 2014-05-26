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
import java.util.List;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GenerateShoppingListScreen {
	InputStream inputStream;
	ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	Image homeIcon, closeIcon, minimiseIcon;	
	String shoppingListPreviewText;
	HBox topBox, topBoxRight, topBoxLeft;
	Label statusBar;
	Button saveBtn, printBtn, editBtn, addBtn;
	VBox shoppingListBox;
	ScrollPane scrollPane;
	CheckBox[] checkboxes;
	TextField newItem;
	boolean inEditMode = false;
	protected VBox bigBox;
	
	public GenerateShoppingListScreen(VBox bigBox, double height, double width, final RecipeCollection recipeCollection) {
		this.bigBox = bigBox;
		
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
		
		// create a scroll box for the shopping list display
		// create VBox for the list	
		shoppingListBox = new VBox();
		shoppingListBox.setSpacing(8);
		// set up the scroll pane
		scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight()*2/3);
		// add the box to the scroll pane
		scrollPane.setContent(shoppingListBox);
		
		// set up the new item field
		newItem = new TextField();
		newItem.setPrefWidth(midBox.getPrefWidth()/3);
		newItem.setPromptText("Click to add an item");
		newItem.setVisible(false);
		newItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					if (addBtn.getText().equals("Save")) {
						if (!newItem.getText().equals("")) {
							getShoppingList(inEditMode).addItem(newItem.getText());
						}
						newItem.setText("");
						newItem.setPromptText("keep typing to add more items");
						getShoppingList(inEditMode);
					}
				}
				else {
					statusBar.setText("Press enter to add multiple items");
				}
			}
		});

		//Buttons for saving and printing the shopping list
		saveBtn = new Button("Save");
		printBtn = new Button("Print");
		editBtn = new Button("Edit List");
		addBtn = new Button("Add Item");
		saveBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		printBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		editBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		addBtn.setPrefSize(midBox.getPrefWidth()/4, 60);
		
		saveBtn.setId("saveBtn");
		saveBtn.getStylesheets().add("file:../Resources/css.css");
		
		// set up the status bar
		statusBar = new Label("");
		
		// populate the shopping list display
		getShoppingList(inEditMode);
		
		//Sets actions to be performed when saveBtn is clicked
		saveBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	new PDFCreator(getShoppingList(inEditMode).readFromTextFile(), true);
            	statusBar.setText("Shopping list saved to PDF");
            }
        });
		
		//Sets actions to be performed when addBtn is clicked
		addBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				if (addBtn.getText().equals("Add Item")) {
					newItem.setVisible(true);
					addBtn.setText("Save");
				}
				else if (addBtn.getText().equals("Save")) {
					newItem.setPromptText("Click to add an item");
					if (!newItem.getText().equals("")) {
						getShoppingList(inEditMode).addItem(newItem.getText());
						statusBar.setText("Added " + newItem.getText() + " to Shopping List");
					}
					newItem.setText("");
					newItem.setVisible(false);
					addBtn.setText("Add Item");
				}
				getShoppingList(inEditMode);
			}
		});
		
		//Sets actions to be performed when editBtn is clicked
		editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				if (inEditMode) {
					// update the shopping list by deleting unselected items
					List<String> itemsToRemove = new ArrayList<String>();
					for (int i=0; i<checkboxes.length; i++) {
						if (!checkboxes[i].isSelected()) {
							itemsToRemove.add(checkboxes[i].getText());
						}
					}
					getShoppingList(inEditMode).removeItems(itemsToRemove);
					
					// reset the button
					inEditMode = false;
					editBtn.setText("Edit List");
					saveBtn.setDisable(false);
					printBtn.setDisable(false);
				}
				else {
					inEditMode = true;
					editBtn.setText("Remove Unselected Items");
					saveBtn.setDisable(true);
					printBtn.setDisable(true);
				}
				getShoppingList(inEditMode);
			}
		});
		
		//Sets actions to be performed when printBtn is clicked
		printBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            	File fileToRead = new File("ShoppingListTemp.pdf");
        		if (fileToRead.exists()) {
        			printShoppingList();
        		}
        		// if no PDF exists, create a temporary document to print from
        		else {
        			new PDFCreator(getShoppingList(inEditMode).readFromTextFile(), false);
        			printShoppingList();
        			File tempFile = new File("ShoppingListTemp.pdf");
        			if (tempFile.exists()) {
            			fileToRead.deleteOnExit();
        			}
        		}
            }
        });
		midBox.setAlignment(Pos.CENTER);
		midBoxBottom.setAlignment(Pos.CENTER);
		midBoxBottom.getChildren().addAll(saveBtn, printBtn);
		HBox buttonBar = new HBox();
		buttonBar.setAlignment(Pos.CENTER_RIGHT);
		buttonBar.getChildren().addAll(editBtn, addBtn);
		midBox.getChildren().addAll(new Label("Shopping List"), buttonBar, scrollPane, statusBar, midBoxBottom);
		
		//Horizontal aligns content horizontally 
		//bigBox collecting all content of generateShoppingListScreen
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);
	
	}
	
	// method to get the shopping list and create a Text Area
	public ShoppingList getShoppingList(boolean editMode) {
		// read the shopping list file
		ShoppingList list = new ShoppingList();
		ArrayList<String> shoppingList = new ArrayList<String>();
		shoppingList = list.readFromTextFile();
		
		if (shoppingListBox != null && scrollPane != null) {
			// clear the list before adding items
			shoppingListBox.getChildren().clear();
			
			if (inEditMode) {
				// set up the checkboxes
				checkboxes = new CheckBox[shoppingList.size()];
			}
			
			// populate the list with checkboxes or labels
			// assign each checkbox an event handler (if in edit mode)
			if (shoppingList != null && shoppingList.size() != 0) {
				editBtn.setDisable(false);
				saveBtn.setDisable(false);
				printBtn.setDisable(false);
				for (int i=0; i<shoppingList.size(); i++) {
					if (inEditMode) {
						CheckBox box = checkboxes[i] = new CheckBox(shoppingList.get(i));
						box.setSelected(true);
						shoppingListBox.getChildren().add(checkboxes[i]);
					}
					else {
						Label item = new Label(shoppingList.get(i));
						shoppingListBox.getChildren().add(item);
					}
				}
				statusBar.setText("You have " + shoppingList.size() + " items in your shopping list");
			}
			else {
				statusBar.setText("No items in shopping list");
				editBtn.setDisable(true);
				saveBtn.setDisable(true);
				printBtn.setDisable(true);
			}
			// add the new item field
			shoppingListBox.getChildren().add(newItem);
		}
		else {
			System.out.println("Shopping List display is broken.");
		}
		
		// return the ShoppingList instance
		return list;
	}
	
	// method to print the shopping list PDF
	public void printShoppingList() {
		PDDocument pdf = null;
		try {
			pdf = PDDocument.load("ShoppingListTemp.pdf");
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