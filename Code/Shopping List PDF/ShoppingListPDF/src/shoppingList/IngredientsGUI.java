/* Title: IngredientsGUI
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
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IngredientsGUI extends Application {
	static ArrayList<String> ingredientsList, shoppingList;
	CheckBox[] checkboxes;
	VBox shoppingListBox;
	HBox buttonBox;
	Button savePDFButton;
	Text statusBar;
	
	// populate an example list of ingredients
	// ingredients list must not exceed 48 items (at present).
	public static void getIngredientsList() {
		ingredientsList = new ArrayList<String>();
		ingredientsList.add("milk");
		ingredientsList.add("bread");
		ingredientsList.add("onions");
		ingredientsList.add("potatoes");
		ingredientsList.add("orange juice");
		ingredientsList.add("eggs");
		ingredientsList.add("specialist brand of cumberland sausages");
		ingredientsList.add("milky way bars");
		ingredientsList.add("bread rolls");
		ingredientsList.add("red onions");
		ingredientsList.add("potato waffles");
		ingredientsList.add("apple juice");
		ingredientsList.add("dark chocolate min. 70% cocoa solids");
		ingredientsList.add("basmati rice");
		ingredientsList.add("smoked back bacon");
		ingredientsList.add("unsalted butter");
		ingredientsList.add("tomato ketchup");
		ingredientsList.add("salt");
		ingredientsList.add("black pepper");
		ingredientsList.add("fish sauce");
		ingredientsList.add("rice wine vinegar");
		ingredientsList.add("pot noodle - curry flavour");
		ingredientsList.add("filter coffee");
		ingredientsList.add("mayonaise");
//		ingredientsList.add("strawberry jam");
//		ingredientsList.add("red wine");
//		ingredientsList.add("linguine pasta");
//		ingredientsList.add("garlic");
//		ingredientsList.add("anchovies");
//		ingredientsList.add("custard cream biscuits");
	}
	
	// method to update shopping list when user clicks checkbox
	public void updateShoppingList() {
		// remove the help message from the status bar
    	statusBar.setText("");
		
		// loop through the checkboxes and check their values
		for (int i=0; i<ingredientsList.size(); i++) {
			// if the checkbox is selected and the ingredients is not already on the list, add to shopping list
			// but only if there are fewer than 24 items already on the list
			if (checkboxes[i].isSelected()) {
				if (!shoppingList.contains(ingredientsList.get(i))) {
					shoppingList.add(ingredientsList.get(i));
				}
			}
			// if the checkbox is not selected and the ingredient is already on the shopping list, remove it.
			else {
				if (shoppingList.contains(ingredientsList.get(i))) {
					shoppingList.remove(ingredientsList.get(i));
				}
			}
		}
		
		// repopulate the GUI with the updated shopping list
		shoppingListBox.getChildren().clear();
		Text title = new Text("Shopping List");
		title.setFont(new Font(25));
		shoppingListBox.getChildren().add(title);
		
		for (int i=0; i<shoppingList.size(); i++) {
			shoppingListBox.getChildren().add(new Text(shoppingList.get(i)));
		}
		
		// check whether or not to display the save button
		testEnableSaveButton();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		Group root = new Group();
		Scene scene = new Scene(root, 800, 750);
				
		// set up a border layout
		BorderPane border = new BorderPane();
		root.getChildren().add(border);
		
		// create VBox(es) on the LHS for the ingredients list
		// each box will only accept 24 ingredients
		// these VBoxes are stored in an HBox
		HBox ingredientsListsBox = new HBox();
		ingredientsListsBox.setSpacing(10);
		
		VBox ingredientsListBox = new VBox();
		ingredientsListBox.setSpacing(8);
		Text LHtitle = new Text("Ingredients");
		LHtitle.setFont(new Font(25));
		ingredientsListBox.getChildren().add(LHtitle);
		
		// create VBox on the RHS for the shopping list
		// this box will only accept 24 items
		shoppingList = new ArrayList<String>();
		shoppingListBox = new VBox();
		shoppingListBox.setPadding(new Insets(0, 0, 0, 60));
		shoppingListBox.setSpacing(10);
		Text RHtitle = new Text("Shopping List");
		RHtitle.setFont(new Font(25));
		shoppingListBox.getChildren().add(RHtitle);
		border.setRight(shoppingListBox);
		
		checkboxes = new CheckBox[ingredientsList.size()];
		
		// add a second column if there are more than 24 items
		if (ingredientsList.size() > 24) {
			VBox ingredientsListBox1 = new VBox();
			ingredientsListBox1.setSpacing(8);
			Text blankTitle = new Text("");
			ingredientsListBox1.getChildren().add(blankTitle);
			
			for (int i=0; i<24; i++) {
				CheckBox box = checkboxes[i] = new CheckBox(ingredientsList.get(i));
				box.setSelected(false);
				box.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
						updateShoppingList();
					}
				});
				ingredientsListBox.getChildren().add(checkboxes[i]);
			}
			ingredientsListsBox.getChildren().add(ingredientsListBox);
			
			for (int i=0; i<ingredientsList.size()-24; i++) {
				CheckBox box = checkboxes[i+24] = new CheckBox(ingredientsList.get(i+24));
				box.setSelected(false);
				box.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
						updateShoppingList();
					}
				});
				ingredientsListBox1.getChildren().add(checkboxes[i+24]);
			}
			ingredientsListsBox.getChildren().add(ingredientsListBox1);
			border.setLeft(ingredientsListsBox);
		}
		else {
			for (int i=0; i<ingredientsList.size(); i++) {
				CheckBox box = checkboxes[i] = new CheckBox(ingredientsList.get(i));
				box.setSelected(false);
				box.selectedProperty().addListener(new ChangeListener<Boolean>() {
					public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
						updateShoppingList();
					}
				});
				ingredientsListBox.getChildren().add(checkboxes[i]);
			}
			ingredientsListsBox.getChildren().add(ingredientsListBox);
			border.setLeft(ingredientsListsBox);
		}
		
		// Add a button to select all of the ingredients listed
		final Button selectAllButton = new Button("Select All");
		// configure the selectAll button
        selectAllButton.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
            	// remove the help message from the status bar
            	statusBar.setText("");
            	
            	// when button pressed, set all checkboxes to selected
            	if (selectAllButton.getText().equals("Select All")) {
                	for (int i=0; i<checkboxes.length; i++) {
                		checkboxes[i].setSelected(true);
                	}
                	// change the button to deselect all
                	selectAllButton.setText("Deselect All");
            		buttonBox.setSpacing(234);
            	}
            	// when button pressed, set all checkboxes to selected
            	else if (selectAllButton.getText().equals("Deselect All")) {
                	for (int i=0; i<checkboxes.length; i++) {
                		checkboxes[i].setSelected(false);
                	}
                	// change the button to deselect all
                	selectAllButton.setText("Select All");
            		buttonBox.setSpacing(250);
            	}
            	else {
            		System.out.println("select all button is broken!");
            	}
            }
        });
		
		// add a button to save the shopping list to PDF
		savePDFButton = new Button("Save shopping list");
		// configure the savePDF button
        savePDFButton.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
            	// when button pressed, send the shopping list to the PDF creator
            	new PDFCreator(shoppingList);
            	
            	// notify the user
            	statusBar.setText("Your shopping list has been saved as 'Your Shopping List.pdf'");
            }
        });
		testEnableSaveButton();
		
		// add the buttons and a status bar at the bottom of the window
		VBox bottomBox = new VBox();
		bottomBox.setSpacing(20);
		statusBar = new Text("Use the checkboxes to add items to your shopping list");
		buttonBox = new HBox();
		buttonBox.setPadding(new Insets(30,0,0,0));
		buttonBox.setSpacing(250);
		buttonBox.getChildren().addAll(selectAllButton, savePDFButton);
		bottomBox.getChildren().addAll(buttonBox, statusBar);
		border.setBottom(bottomBox);

		primaryStage.setScene(scene);
		primaryStage.setTitle("Ingredients for Recipe: Horrible Stew");
		primaryStage.show();
	}
	
	// method to test for save button enable
    public void testEnableSaveButton() {
    	// only enable the save button when there are items in the shopping list
    	if (shoppingList.size() != 0) {
    		savePDFButton.setDisable(false);
    	}
    	else {
    		savePDFButton.setDisable(true);
    	}
    }

	public static void main(String[] args) {
		// create the example ingredients list
		getIngredientsList();
		// start the ingredients display GUI
		launch(args);
	}	 
}