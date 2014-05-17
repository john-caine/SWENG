/* Title: IngredientsList
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 15/04/14
 * 
 * Description: A basic GUI which shows a list of pre-defined ingredients with checkboxes
 * 				and allows a user to add these items to a shopping list, before saving the 
 * 				list to PDF using the PDFCreator class.
 */
package shoppingList;

import java.util.ArrayList;

import xmlparser.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IngredientsList {
	static ArrayList<String> ingredientsList;
	CheckBox[] checkboxes;
	VBox ingredientsListBox, scrollBox;
	ScrollPane scrollPane;
	HBox buttonBox;
	Button savePDFButton, updateShoppingListButton;
	Label statusBar;
	ShoppingList currentShoppingList;
	
	// constructor
	public IngredientsList(Recipe recipe) {
		// populate the ingredientsList
		ingredientsList = new ArrayList<String>();
		for (int i=0; i<recipe.getNumberOfIngredients(); i++) {
			ingredientsList.add(recipe.getIngredient(i).getName());
		}
		
		// copy the current shopping list to the local instance
		currentShoppingList = new ShoppingList();
		
		// create the VBox with the list of ingredients and checkboxes
		setupIngredientsListGUI();
	}
	
	// method to get the ingredients list GUI VBox
	public VBox getIngredientsListGUI() {
		return ingredientsListBox;
	}
	
	// method to update the status bar
	public void updateStatusBar(String text) {
		if (text != null) {
			statusBar.setText(text);
		}
	}
	
	// method to update shopping list when user clicks checkbox
	public void updateShoppingList() {		
		// loop through the checkboxes and check their values
		for (int i=0; i<ingredientsList.size(); i++) {
			// if the checkbox is selected and the ingredients is not already on the list, add to shopping list
			// but only if there are fewer than 24 items already on the list
			if (checkboxes[i].isSelected()) {
				if (!currentShoppingList.readFromTextFile().contains(ingredientsList.get(i))) {
					currentShoppingList.addItem(ingredientsList.get(i));
				}
			}
			// if the checkbox is not selected and the ingredient is already on the shopping list, remove it.
			else {
				if (currentShoppingList.readFromTextFile().contains(ingredientsList.get(i))) {
					currentShoppingList.removeItem(ingredientsList.get(i));
				}
			}
		}		
	}
	
	// method to create the ingredients list VBox
	public void setupIngredientsListGUI() {		
		// create VBox for the ingredients list	
		ingredientsListBox = new VBox();
		scrollBox = new VBox();
		scrollBox.setSpacing(8);
		
		// set up the scroll pane
		scrollPane = new ScrollPane();
		scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setPrefHeight(400);
		
		// set up the checkboxes
		checkboxes = new CheckBox[ingredientsList.size()];
		
		// setup the status bar
		statusBar = new Label("");
		statusBar.setPadding(new Insets(5,0,0,100));
			
		// populate the list with checkboxes
		// assign each checkbox an event handler
		for (int i=0; i<ingredientsList.size(); i++) {
			CheckBox box = checkboxes[i] = new CheckBox(ingredientsList.get(i));
			box.setSelected(false);
			box.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
					// check whether or not to display the save button
					testEnableSaveButton();
				}
			});
			scrollBox.getChildren().add(checkboxes[i]);
		}
		
		// add a button to select all of the ingredients listed
		final Button selectAllButton = new Button("Select All");
		selectAllButton.setPrefWidth(95);
		// configure the selectAll button
        selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {            	
            	// when button pressed, set all checkboxes to selected
            	if (selectAllButton.getText().equals("Select All")) {
                	for (int i=0; i<checkboxes.length; i++) {
                		checkboxes[i].setSelected(true);
                	}
                	// change the button to deselect all
                	selectAllButton.setText("Deselect All");
            		buttonBox.setSpacing(10);
            	}
            	// when button pressed, set all checkboxes to selected
            	else if (selectAllButton.getText().equals("Deselect All")) {
                	for (int i=0; i<checkboxes.length; i++) {
                		checkboxes[i].setSelected(false);
                	}
                	// change the button to deselect all
                	selectAllButton.setText("Select All");
            		buttonBox.setSpacing(10);
            	}
            	else {
            		System.out.println("select all button is broken!");
            	}
            }
        });
		
		// add a button to save the shopping list to PDF
		savePDFButton = new Button("Save shopping list");
		// add a button to add ingredients to the shopping list (eCook side)
		updateShoppingListButton = new Button("Add Selected Ingredients to Shopping List");
		// configure the add to shopping list button
        updateShoppingListButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	// when button pressed, do some stuff with the external shopping list class
            	updateShoppingList();
            	statusBar.setText("Ingredients Added to Shopping List.");
            }
        });
		// configure the savePDF button
        savePDFButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            	// when button pressed, send the shopping list to the PDF creator
            	new PDFCreator(currentShoppingList.readFromTextFile());
            	System.out.println("shopping list saved to PDF");
            }
        });
		testEnableSaveButton();
		
		// add the buttons and the status bar to the bottom of the VBox
		buttonBox = new HBox();
		buttonBox.setPadding(new Insets(10,0,0,0));
		buttonBox.setSpacing(10);
		buttonBox.getChildren().addAll(selectAllButton, updateShoppingListButton, statusBar);
		scrollPane.setContent(scrollBox);
		ingredientsListBox.getChildren().addAll(scrollPane, buttonBox);
	}
	
	// method to test for save button enable
    public void testEnableSaveButton() {
    	// clear the status bar
    	if (!statusBar.getText().equals("")) {
    		statusBar.setText("");
    	}
    	
    	boolean itemSelected = false;
    	
    	// only enable the save button when items are selected
    	for (int i=0; i<ingredientsList.size(); i++) {
			if (checkboxes[i].isSelected()) {
				itemSelected = true;
			}
    	}

    	if (itemSelected) {
    		savePDFButton.setDisable(false);
    		updateShoppingListButton.setDisable(false);
    	}
    	else {
    		savePDFButton.setDisable(true);
    		updateShoppingListButton.setDisable(true);
    	}
    }	 
}