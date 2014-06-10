/*
 * Programmer: Zayyad Tagwai, Roger Tan, Max Holland, Ankita Gangotra & James Oatley
 * Date Created: 07/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;

import shoppingList.PDFCreator;
import shoppingList.ShoppingList;
import eCook.RecipeCollection;
import eCook.eCook;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class GenerateShoppingListScreen {
	private InputStream inputStream;
	private ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	private Image homeIcon, closeIcon, minimiseIcon;	
	private HBox topBox, topBoxRight, topBoxLeft;
	private Label statusBar;
	private Button saveBtn, printBtn, editBtn, addBtn;
	private VBox shoppingListBox;
	private ScrollPane scrollPane;
	private CheckBox[] checkboxes;
	private TextField newItem;
	private boolean inEditMode = false;
	protected Tooltip h,c,m;
	protected VBox bigBox;
	private PDDocument pdf = null;
	
	public GenerateShoppingListScreen(VBox bigBox, double height, double width, final RecipeCollection recipeCollection) {
		this.bigBox = bigBox;
		bigBox.setStyle("-fx-background-size: cover; -fx-background-position: center center; -fx-background-image: url('backgroundBlur.png');");
		//Imports eCook logo, home, close and minimise button icons
		homeHolder = new ImageView();
		homeIcon = new Image("home1.png");
		
		closeBtnHolder = new ImageView();
		closeIcon = new Image("redx.png");
		
		minimiseBtnHolder = new ImageView();
		minimiseIcon = new Image("minimise.png");
		
		//Add tool tip
		h = new Tooltip("Home");
		Tooltip.install(homeHolder, h);
		c = new Tooltip("Close");
		Tooltip.install(closeBtnHolder, c);
		m = new Tooltip("Minimise");
		Tooltip.install(minimiseBtnHolder, m);	
		
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
		final VBox midBox = new VBox(40);
		VBox rightBox = new VBox();
		
		//Sets  parameters for the leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height-topBox.getPrefHeight()-100);
		midBox.setPrefSize(width*0.6, height-topBox.getPrefHeight()-100);
		rightBox.setPrefSize(width*0.2, height-topBox.getPrefHeight()-100);
		
		// Set the background of the shopping list to a shopping list image
		midBox.setPadding(new Insets(50, 50, 150, 150));
		midBox.setStyle("-fx-background-color: transparent;"
				+ "-fx-background-image: url('shoppingListImage.png');"
				+ "-fx-background-position: center center;"
				+ "-fx-background-size: contain;"
				+ "-fx-background-repeat: repeat-y;");
		
		// create a scroll box for the shopping list display
		// create VBox for the list	
		shoppingListBox = new VBox();
		shoppingListBox.setSpacing(8);
		shoppingListBox.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight()*2/3);
		// set up the scroll pane
		scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPrefSize(midBox.getPrefWidth(), midBox.getPrefHeight()*2/3);
		// add the box to the scroll pane
		scrollPane.setContent(shoppingListBox);
		
		//set the scrollpane's desgin
		scrollPane.setId("scrollPane");
		//shoppingListBox.setStyle("-fx-background-color: transparent;");
		scrollPane.getStylesheets().add("css.css");
		
		// set up the new item field
		newItem = new TextField();
		newItem.setPrefWidth(midBox.getPrefWidth()/3);
		newItem.setPromptText("Click to add an item");
		newItem.setVisible(false);
		newItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					if (addBtn.getText().equals("Save Changes")) {
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
		printBtn = new Button("Print");
		saveBtn = new Button("Save as PDF");
		editBtn = new Button("Edit List");
		addBtn = new Button("Add Item");
		saveBtn.setPrefSize(midBox.getPrefWidth()/6, 200);
		printBtn.setPrefSize(midBox.getPrefWidth()/8, 200);
		editBtn.setPrefSize(midBox.getPrefWidth()/4, 200);
		addBtn.setPrefSize(midBox.getPrefWidth()/6, 200);
		
		//Tool tips for buttons
		saveBtn.setTooltip(new Tooltip("Click here to save your shopping list as PDF"));
		printBtn.setTooltip(new Tooltip("Click here to view your shopping list"));
		editBtn.setTooltip(new Tooltip("Click here to edit your shopping list"));
		addBtn.setTooltip(new Tooltip("Click here to add extra items to your shopping list"));
		
		saveBtn.setWrapText(true);
		printBtn.setWrapText(true);
		editBtn.setWrapText(true);
		addBtn.setWrapText(true);
		
		saveBtn.setAlignment(Pos.CENTER);
		saveBtn.setTextAlignment(TextAlignment.CENTER);
		printBtn.setAlignment(Pos.CENTER);
		printBtn.setTextAlignment(TextAlignment.CENTER);
		editBtn.setAlignment(Pos.CENTER);
		editBtn.setTextAlignment(TextAlignment.CENTER);
		addBtn.setAlignment(Pos.CENTER);
		addBtn.setTextAlignment(TextAlignment.CENTER);
		
		saveBtn.setId("saveBtn");
		printBtn.setId("printBtn");
		editBtn.setId("editBtn");
		addBtn.setId("addBtn");
		saveBtn.getStylesheets().add("css.css");
		printBtn.getStylesheets().add("css.css");
		editBtn.getStylesheets().add("css.css");
		addBtn.getStylesheets().add("css.css");
		
		// set up the status bar
		statusBar = new Label("");
		statusBar.setId("statusBar");
		statusBar.getStylesheets().add("css.css");
		
		// populate the shopping list display
		getShoppingList(inEditMode);
		
		//Sets actions to be performed when saveBtn is clicked
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	new PDFCreator(getShoppingList(inEditMode).readFromTextFile(), true);
            	statusBar.setText("Shopping list saved to PDF");
            }
        });
		
		//Sets actions to be performed when addBtn is clicked
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
				if (addBtn.getText().equals("Add Item")) {
					newItem.setVisible(true);
					addBtn.setText("Save Changes");
				}
				else if (addBtn.getText().equals("Save Changes")) {
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
		editBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
				if (inEditMode) {
					// update the shopping list by deleting selected items
					List<String> itemsToRemove = new ArrayList<String>();
					for (int i=0; i<checkboxes.length; i++) {
						if (checkboxes[i].isSelected()) {
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
					editBtn.setText("Remove Selected Items");
					saveBtn.setDisable(true);
					printBtn.setDisable(true);
				}
				getShoppingList(inEditMode);
			}
		});
		
		//Sets actions to be performed when printBtn is clicked
		printBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
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
		
		// Set the Css file for shoppingListLabel
		Label shoppingListLabel = new Label("Shopping List");
		shoppingListLabel.setId("shoppingListLabel");
		shoppingListLabel.getStylesheets().add("css.css");
		
		midBox.setAlignment(Pos.CENTER);
		HBox buttonBar = new HBox(20);
		buttonBar.setAlignment(Pos.CENTER);
		buttonBar.getChildren().addAll(editBtn, addBtn, saveBtn, printBtn);
		midBox.getChildren().addAll(shoppingListLabel,  statusBar, scrollPane, buttonBar);
		
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
						box.setId("box");
						box.getStylesheets().add("css.css");
						box.setSelected(false);
						shoppingListBox.getChildren().add(checkboxes[i]);
					}
					else {
						Label item = new Label(shoppingList.get(i));
						item.setId("item");
						item.getStylesheets().add("css.css");
						shoppingListBox.getChildren().add(item);
					}
				}
				statusBar.setText(shoppingList.size() + " items in your shopping list");
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
		pdf = null;
		statusBar.setText("Printing shopping list...");
		// run the print task on a new thread asynchronously
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					pdf = PDDocument.load("ShoppingListTemp.pdf");
					pdf.silentPrint();
				} catch (IOException | PrinterException e) {
					statusBar.setText("Sorry. Print error: cannot print file");
					e.printStackTrace();
				} finally {
					// clean up and display finsh confirmation
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (pdf != null) {
								try {
									pdf.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							statusBar.setText("Printing complete.");
						}
					});
				}
			}
		}).start();																		
	}
}