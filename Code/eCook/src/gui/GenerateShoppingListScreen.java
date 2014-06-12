/*
 * Programmers: Zayyad Tagwai, Roger Tan, Max Holland, Ankita Gangotra & James Oatley
 * Date Created: 07/05/2014
 * Adds components of the recipe screen to the bigBox window 
 */

package gui;

import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class GenerateShoppingListScreen extends menu {
	protected Label statusBar;
	private Button saveBtn;
	private Button printBtn; 
	private Button editBtn; 
	private Button addBtn;
	private VBox shoppingListBox;
	private ScrollPane scrollPane;
	private CheckBox[] checkboxes;
	private TextField newItem;
	private boolean inEditMode = false;
	protected VBox bigBox;
	private PDDocument pdf = null;
	private Logger logger;
	
	/**
	 * Constructs the basic formatting of the ShoppingList screen. Creates all the buttons, bars, and panes required,
	 * and sets their appropriate event handlers.
	 * @param bigBox The overall box for the shoppingList screen
	 * @param height The height of the screen bounds
	 * @param width The width of the screen bounds
	 * @param recipeCollection The current recipe collection
	 */
	public GenerateShoppingListScreen(VBox bigBox, double height, double width, final RecipeCollection recipeCollection) {
		super (recipeCollection);
		
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		//Get bigBox and set background for it
		this.bigBox = bigBox;
		bigBox.setId("GenerateShoppingListBigBox");
		bigBox.getStylesheets().add("css.css");
		
		//Define a VBox for leftBox
		VBox leftBox = new VBox();
		final VBox midBox = new VBox(40);
		VBox rightBox = new VBox();
		
		//Sets  parameters for the leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height-topBox.getPrefHeight()-100);
		midBox.setPrefSize(width*0.6, height-topBox.getPrefHeight()-100);
		rightBox.setPrefSize(width*0.2, height-topBox.getPrefHeight()-100);
		
		// Set the background of the shopping list to a shopping list image
		midBox.setId("GenerateShoppingListMidBox");
		midBox.getStylesheets().add("css.css");
		midBox.setPadding(new Insets(50, 50, 150, 150));
		
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
		scrollPane.getStylesheets().add("css.css");
		
		// set up the new item field
		newItem = new TextField();
		newItem.setPrefWidth(midBox.getPrefWidth()/3);
		newItem.setPromptText("Click to add an item");
		newItem.setVisible(false);
		newItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			// Handler for key events
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
				} else {
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
		
		//Set ID for the buttons to access CSS file
		saveBtn.setWrapText(true);
		printBtn.setWrapText(true);
		editBtn.setWrapText(true);
		addBtn.setWrapText(true);
		
		// Set the alignment of the buttons
		saveBtn.setAlignment(Pos.CENTER);
		saveBtn.setTextAlignment(TextAlignment.CENTER);
		printBtn.setAlignment(Pos.CENTER);
		printBtn.setTextAlignment(TextAlignment.CENTER);
		editBtn.setAlignment(Pos.CENTER);
		editBtn.setTextAlignment(TextAlignment.CENTER);
		addBtn.setAlignment(Pos.CENTER);
		addBtn.setTextAlignment(TextAlignment.CENTER);
		
		// Set the CSS IDs of the buttons
		saveBtn.setId("saveBtn");
		printBtn.setId("printBtn");
		editBtn.setId("editBtn");
		addBtn.setId("addBtn");
		saveBtn.getStylesheets().add("css.css");
		printBtn.getStylesheets().add("css.css");
		editBtn.getStylesheets().add("css.css");
		addBtn.getStylesheets().add("css.css");
		
		// Set up the status bar
		statusBar = new Label("");
		statusBar.setId("statusBar");
		statusBar.getStylesheets().add("css.css");
		
		// Populate the shopping list display
		getShoppingList(inEditMode);
		
		//Sets actions to be performed when saveBtn is clicked
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	// call a PDF creator
            	new PDFCreator(getShoppingList(inEditMode).readFromTextFile(), true);
            	statusBar.setText("Shopping list saved to PDF");
            }
        });
		
		//Sets actions to be performed when addBtn is clicked
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
				if (addBtn.getText().equals("Add Item")) {
					// Add a new item and update the button label
					newItem.setVisible(true);
					addBtn.setText("Save Changes");
				} else if (addBtn.getText().equals("Save Changes")) {
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
				} else {
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
		
		// Configure the shoppingList label
		Label shoppingListLabel = new Label("Shopping List");
		shoppingListLabel.setId("shoppingListLabel");
		shoppingListLabel.getStylesheets().add("css.css");
		
		midBox.setAlignment(Pos.CENTER);
		HBox buttonBar = new HBox(20);
		buttonBar.setAlignment(Pos.CENTER);
		
		// Add buttons to the bar, then the bars, pane and label to the middle box.
		buttonBar.getChildren().addAll(editBtn, addBtn, saveBtn, printBtn);
		midBox.getChildren().addAll(shoppingListLabel, statusBar, scrollPane, buttonBar);
		
		//Horizontal aligns content horizontally 
		//bigBox collecting all content of generateShoppingListScreen
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox, midBox, rightBox);
		bigBox.getChildren().addAll(topBox, horizontalBox);
	
	}
	
	/**
	 * Method to get at the shopping list and create a Text Area. Sets the buttons/labels appropriately
	 * depending on if the editMode is set or not.
	 * @param editMode Boolean for is the user editing the shopping list directly or not.
	 * @return list The constructed shopping list
	 */
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
			
			// Populate the list with checkboxes or labels
			// Assign each checkbox an event handler (if in edit mode)
			if (shoppingList != null && shoppingList.size() != 0) {
				editBtn.setDisable(false);
				saveBtn.setDisable(false);
				printBtn.setDisable(false);
				
				// Iterate over all items in the shopping list, setting the labels or checkboxes appropriately
				for (int i=0; i<shoppingList.size(); i++) {
					if (inEditMode) {
						// configure checkboxes
						CheckBox box = checkboxes[i] = new CheckBox(shoppingList.get(i));
						box.setId("box");
						box.getStylesheets().add("css.css");
						box.setSelected(false);
						shoppingListBox.getChildren().add(checkboxes[i]);
					} else {
						// Configure labels
						Label item = new Label(shoppingList.get(i));
						item.setId("item");
						item.getStylesheets().add("css.css");
						shoppingListBox.getChildren().add(item);
					}
				}
				
				statusBar.setText(shoppingList.size() + " items in your shopping list");
			} else {
				// Otherwise tell the user there is nothing in the shopping list
				statusBar.setText("No items in shopping list");
				editBtn.setDisable(true);
				saveBtn.setDisable(true);
				printBtn.setDisable(true);
			}
			
			// Add the new item field
			shoppingListBox.getChildren().add(newItem);
		} else {
			// catch errors
			logger.log(Level.WARNING, "Shopping List display is broken.");
		}
		
		// Return the ShoppingList instance
		return list;
	}
	
	/**
	 * Method to print the contents of the shopping list to a PDF
	 */
	public void printShoppingList() {
		pdf = null;
		statusBar.setText("Printing shopping list...");
		
		// run the print task on a new thread asynchronously
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// try to print the shopping list to a PDF
					pdf = PDDocument.load("ShoppingListTemp.pdf");
					pdf.silentPrint();
				} catch (IOException | PrinterException e) {
					statusBar.setText("Sorry. Print error: cannot print file");
				} finally {
					// clean up and display finish confirmation
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							// If a PDF was created, close it so it doesn't interfere with our application
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
		}).start();	// Run the thread																
	}
}