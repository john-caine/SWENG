/*
 * Programmer: Zayyad Tagwai, Roger Tan, Ankita Gangotra and James Oatley
 * Date Created: 05/05/2014
 * Adds components of the recipe screen to the bigBox window
 */

package gui;

import java.util.ArrayList;
import xmlfilepathhandler.XMLFilepathHandler;
import xmlparser.Recipe;
import eCook.RecipeCollection;
import eCook.SlideShow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecipeScreen extends menu{

	private VBox recipeInfoBox;
	protected VBox bigBox;
	Button currentDownloadButton;
	int lastRowHovered = 0;
	static final int downloading = 1;
	static final int existsLocally = 2;
	static final int canBeDownloaded = 3;

	final Button[] downloadButtons;
	final Button[] playButtons;
	
	public RecipeScreen (VBox bigBox, double height, double width, final RecipeCollection recipeCollection, final Stage stage){
		super (recipeCollection);
		
		//Get bigBox and set background image
		//this.bigBox = bigBox;
		bigBox.setId("RecipeScreenBigBox");
		bigBox.getStylesheets().add("css.css");

		//Define leftBox, rightBox and midBox
		VBox leftBox = new VBox();
		VBox midBox = new VBox(20);
		VBox rightBox = new VBox();

		//Sets parameters for leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height - topBox.getPrefHeight()-100);
		midBox.setPrefSize(width*0.6, height - topBox.getPrefHeight()-100);
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2, height - topBox.getPrefHeight()-100);
		
		// Ask James and Max about the following!
		// Arrays of download and play buttons for each of the recipes
		downloadButtons = new Button[recipeCollection.getNumberOfRecipes()];
		playButtons = new Button[recipeCollection.getNumberOfRecipes()];
		final ArrayList<HBox> recipeRows = new ArrayList<HBox>();
		ArrayList<String> recipeTitles = new ArrayList<String>();
		// Loop through and initialise all of the download and play buttons to the desired settings (for each recipe in defaultRecipes)
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			// Get recipe title String and set up HBox
			recipeTitles.add(recipeCollection.getRecipe(i).getInfo().getTitle());
			HBox row = new HBox(10);
			row.setId("r" + i);
			row.setPrefWidth(rightBox.getPrefWidth()-10);
			row.setAlignment(Pos.CENTER_LEFT);

			// Set up default button parameters

			
			// Set up buttons

			downloadButtons[i] = new Button("Download Recipe Content");
			downloadButtons[i].setVisible(false);
			downloadButtons[i].setId("downloadButtons");
			downloadButtons[i].getStylesheets().add("css.css");
			playButtons[i] = new Button("Play");
			playButtons[i].setVisible(false);

			playButtons[i].setId("playButtons");
			playButtons[i].getStylesheets().add("css.css");

			// Put some tooltips on the buttons
			downloadButtons[i].setTooltip(new Tooltip("Downloads online content for this recipe"));
			playButtons[i].setTooltip(new Tooltip("Opens the selected recipe"));
			// Download button requires a thread on action, methods within the thread will be run in the background

			
			//Set tool tips
			downloadButtons[i].setTooltip(new Tooltip("Download recipe content file to your local machine (speeds up playback)"));
			playButtons[i].setTooltip(new Tooltip("Click here to open slideshow for selected recipe"));
			
			// Configure the buttons

			downloadButtons[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent event) {
					// Get the ID of the button pressed
					currentDownloadButton = (Button) event.getSource();
					// Update the button to say its downloading
					updateButtonLabels(Integer.parseInt(currentDownloadButton.getId()), downloading);
					// Create a new thread to run the download on
					new Thread(new Runnable() {
						@Override
						public void run() {
							// Loop through the recipe and download content from all of the links
							XMLFilepathHandler filepathHandler = new XMLFilepathHandler();
							recipeCollection.getRecipe(Integer.parseInt(currentDownloadButton.getId())).setDownloading(true);
							filepathHandler.downloadRecipeMedia(recipeCollection.getRecipe(Integer.parseInt(currentDownloadButton.getId())));
							recipeCollection.getRecipe(Integer.parseInt(currentDownloadButton.getId())).setDownloading(false);
						}
					}).start();
				}
				
			});
			playButtons[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					int index = 0;
					// get the selected recipe
					Button focusButton = (Button) event.getSource();
					System.out.println(focusButton.getId().substring(0));
					index = Integer.valueOf(focusButton.getId().substring(0));
					//Get filename of currently selected recipe
					String fileName = recipeCollection.getRecipe(index).getFileName();
					// play the slideshow
					new SlideShow(stage, fileName, recipeCollection);
				}
			});
			
			// Set up the HBox so that mouseover works as a selection tool
			row.setOnMouseEntered(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					// hide any previously showing buttons
					playButtons[lastRowHovered].setVisible(false);
					downloadButtons[lastRowHovered].setVisible(false);
					int index = 0;
					// get the selected recipe
					HBox focusBox = (HBox) event.getSource();
					index = Integer.valueOf(focusBox.getId().substring(1));
					if (recipeCollection.getRecipe(index).existsLocally()) {
						updateButtonLabels(index, existsLocally);
					}
					else if (recipeCollection.getRecipe(index).isDownloading()) {
						updateButtonLabels(index, downloading);
					}
					else {
						updateButtonLabels(index, canBeDownloaded);
					}
				}
			});
			
			// Set up the HBox so that mouseout hides the buttons for that row
			row.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					int index = 0;
					// Get the selected recipe
					HBox focusBox = (HBox) event.getSource();
					index = Integer.valueOf(focusBox.getId().substring(1));
					// Hide the buttons for that row
					playButtons[index].setVisible(false);
					downloadButtons[index].setVisible(false);
					lastRowHovered = index;
				}
			});
			// Add everything to the HBox
			Label recipeTitle = new Label(recipeTitles.get(i));
			HBox labelBox = new HBox();
			double boxWidth = 9*midBox.getPrefWidth()/16;
			labelBox.setPrefWidth(boxWidth);
			labelBox.getChildren().add(recipeTitle);
			row.getChildren().addAll(labelBox, downloadButtons[i], playButtons[i]);
		
			// Add HBox to list
			recipeRows.add(row);
		}

		// Create a list view and populate it with the recipe titles
		final ListView<HBox> listOfRecipes = new ListView<HBox>();
		listOfRecipes.getStylesheets().add("css.css");
		listOfRecipes.setPrefSize(rightBox.getPrefWidth() , rightBox.getPrefHeight()*0.75);
		listOfRecipes.setItems(FXCollections.observableList(recipeRows));
		listOfRecipes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// Set up the list so that mouseout continues showing the buttons for the last row hovered over
		listOfRecipes.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// show the buttons for that row
				if (recipeCollection.getRecipe(lastRowHovered).existsLocally()) {
					updateButtonLabels(lastRowHovered, existsLocally);
				}
				else if (recipeCollection.getRecipe(lastRowHovered).isDownloading()) {
					updateButtonLabels(lastRowHovered, downloading);
				}
				else {
					updateButtonLabels(lastRowHovered, canBeDownloaded);
				}
			}
		});

		// Create a new HBox to hold the recipe information
		recipeInfoBox = new VBox();
		recipeInfoBox.setPrefSize(rightBox.getPrefWidth(), rightBox.getPrefHeight()*0.8);

		// Set the first recipe in the list to be selected on loading
		if (listOfRecipes.getItems().size() != 0) {
			listOfRecipes.getSelectionModel().select(0);
			if (recipeCollection.getRecipe(0) != null) {
				updateInfoLabels(recipeCollection.getRecipe(0));
			}
		}
		else {
			updateInfoLabels(null);
		}
		
		// When recipe selection changes, update the info and ingredients fields
		listOfRecipes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<HBox>() {
			public void changed(ObservableValue<? extends HBox> ov,
					HBox old_val, HBox new_val) {
				// get the selected recipe
				int selectedIndex = listOfRecipes.getSelectionModel().getSelectedIndex();
				// call the update info labels method
				updateInfoLabels(recipeCollection.getRecipe(selectedIndex));
			}
		});

		//Box where all content of the RecipeScreen class are collated
		HBox horizontalBox = new HBox();
		horizontalBox.getChildren().addAll(leftBox,midBox,rightBox);
		bigBox.getChildren().addAll(topBox,horizontalBox);	

		Label recipesLabel = new Label("Recipe Playlist");
		recipesLabel.setId("recipesLabel");
		recipesLabel.getStylesheets().add("css.css");

		midBox.setAlignment(Pos.CENTER);
		midBox.getChildren().addAll(recipesLabel,listOfRecipes, recipeInfoBox);
		for (int i1=0; i1<recipeCollection.getNumberOfRecipes(); i1++) {
			if (recipeCollection.getRecipe(i1).existsLocally()) {
				updateButtonLabels(i1, existsLocally);
			}
			else if (recipeCollection.getRecipe(i1).isDownloading()) {
				updateButtonLabels(i1, downloading);
			}
			else {
				updateButtonLabels(i1, canBeDownloaded);
			}
			if (i1 != 0) {
				downloadButtons[i1].setVisible(false);
				playButtons[i1].setVisible(false);
			}
		}
	}

	//Method for updating button lablels
	private void updateButtonLabels(int buttonId, int setting) {
		if (setting == downloading) {
			downloadButtons[buttonId].setVisible(true);
			downloadButtons[buttonId].setText("Downloading...");
			downloadButtons[buttonId].setDisable(true);
			playButtons[buttonId].setVisible(true);
			playButtons[buttonId].setDisable(true);
		}
		if (setting == canBeDownloaded) {
			downloadButtons[buttonId].setVisible(true);
			downloadButtons[buttonId].setText("Download Recipe Content");
			downloadButtons[buttonId].setDisable(false);
			playButtons[buttonId].setVisible(true);
			playButtons[buttonId].setDisable(false);
		}
		if (setting == existsLocally) {
			downloadButtons[buttonId].setVisible(false);
			playButtons[buttonId].setVisible(true);
			playButtons[buttonId].setDisable(false);
		}
	}
	
	// Method to update labels in the recipe info box
	public void updateInfoLabels(Recipe recipe) {
		String author = "", comment = "", cook = "", prep = "", guests = "", veg ="";

		if (recipe != null) {
			// update the info Strings
			author = recipe.getInfo().getAuthor();
			comment = recipe.getInfo().getComment();
			cook = recipe.getInfo().getCook();
			prep = recipe.getInfo().getPrep();
			guests = recipe.getInfo().getGuests();
			veg = recipe.getInfo().getVeg();
		}

		// Add labels for author, version and comment
		Label authorLabel = new Label("Author: " + author);
		//Label versionLabel = new Label("Version: " + version);
		Label commentLabel = new Label("Description: " + comment);
		Label cookLabel = new Label("Cooking Time: " + cook);
		Label prepLabel = new Label("Preperation Time: " + prep);
		Label guestsLabel = new Label("Number of People Serves: " + guests);
		Label vegLabel = new Label("Suitable for Vegetarians? " + veg);


		//Set ID for labels to access CSS file
		authorLabel.setWrapText(true);
		authorLabel.setId("authorLabel");
		authorLabel.getStylesheets().add("css.css");
		commentLabel.setWrapText(true);
		commentLabel.setId("commentLabel");
		commentLabel.getStylesheets().add("css.css");
		cookLabel.setWrapText(true);
		cookLabel.setId("cookLabel");
		cookLabel.getStylesheets().add("css.css");
		prepLabel.setWrapText(true);
		prepLabel.setId("prepLabel");
		prepLabel.getStylesheets().add("css.css");
		guestsLabel.setWrapText(true);
		guestsLabel.setId("guestsLabel");
		guestsLabel.getStylesheets().add("css.css");
		vegLabel.setWrapText(true);
		vegLabel.setId("vegLabel");
		vegLabel.getStylesheets().add("css.css");

		// Remove old labels
		recipeInfoBox.getChildren().clear();
		// Add the labels to the info box
		recipeInfoBox.getChildren().addAll(authorLabel, cookLabel, prepLabel, guestsLabel, vegLabel, commentLabel);
	}


}