/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Create a create slide button
 Version : 1.0 27/2/2014
 		   1.1 Removed the stage creation, now accepts stage as an argument, sets position of button to the centre
 		   	   of the screen in mainMenuButtonHBox
 */
package eCook;
	
import recipehttpaccess.RecipeBrowser;
import shoppingList.IngredientsGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class MainMenuButton {
	public HBox mainMenuButtonHbox;
	private Button createSlideShow, showIngredientsList, showOnlineRecipeBrowser;


	public MainMenuButton(final Stage stage) {
			
			
			
			Screen screen = Screen.getPrimary();
			//Get the visual bounds of the screen
			Rectangle2D screenBounds = screen.getVisualBounds();
			
			createSlideShow = new Button("Create SlideShow");
			mainMenuButtonHbox = new HBox();
			
			mainMenuButtonHbox.setAlignment(Pos.CENTER);
			
			mainMenuButtonHbox.setLayoutX((screenBounds.getWidth()- createSlideShow.getPrefWidth())/2);
			
			//Makes the createSlideShow button appear in the center of the screen
			mainMenuButtonHbox.setLayoutY((screenBounds.getHeight())/2);
	        
			mainMenuButtonHbox.getChildren().add(createSlideShow);
			
			// Add a button to display the ingredients list for adding stuff to your shopping list
			showIngredientsList = new Button("Display Ingredients List");
			mainMenuButtonHbox.getChildren().add(showIngredientsList);
			
			// Add a button to display the ingredients list for adding stuff to your shopping list
			showOnlineRecipeBrowser = new Button("Go to Online Recipe Browser");		
			mainMenuButtonHbox.getChildren().add(showOnlineRecipeBrowser);
	        
			
//Creates a new slide show when the button is pressed		
		createSlideShow.setOnAction(new EventHandler<ActionEvent>() {

				@Override
	            public void handle(ActionEvent event) {
					
					new SlideShow(stage);

	            }
	        });
		
//Show an example ingredients list when the button is pressed		
		showIngredientsList.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				// Note that the sample ingredients list is populated within the IngredientsGUI Class
				new IngredientsGUI(stage);
			}
		});
		
//Show the online recipe browser GUI when the button is pressed	
		showOnlineRecipeBrowser.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				new RecipeBrowser(stage);
			}
		});
	}
	
}
