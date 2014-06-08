/*
 * Programmer: Zayyad Tagwai, Roger Tan & Ankita Gangotra
 * Date Created: 05/05/2014
 * Adds components of the recipe screen to the bigBox window
 */

package gui;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import xmlfilepathhandler.XMLFilepathHandler;
import xmlparser.Recipe;
import eCook.RecipeCollection;
import eCook.SlideShow;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RecipeScreen {

	private ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	private Image homeIcon, closeIcon, minimiseIcon;	
	private HBox topBox, topBoxLeft, topBoxRight;
	private VBox recipeInfoBox;
	private Tooltip h,c,m;
	protected VBox bigBox;
	Button currentDownloadButton;
	int lastRowHovered = 0;
	static final int downloading = 1;
	static final int existsLocally = 2;
	static final int canBeDownloaded = 3;

	final Button[] downloadButtons;
	final Button[] playButtons;
	
	public RecipeScreen(VBox bigBox, double height, double width, final RecipeCollection recipeCollection, final Stage stage){
		
		this.bigBox = bigBox;
		bigBox.setStyle("-fx-background-size: cover; -fx-background-position: center center; -fx-background-image: url('backgroundBlur.png');");
		//Imports home, close and minimise button icons
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
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.close();
			}
		});

		//Sets the event to happen when the minimise icon is clicked
		//Gets the node before closing the stage
		minimiseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
				stage.setIconified(true);
			}
		});

		//Sets the event to happen when the home icon is clicked
		//Gets the node before closing the stage and returning to the main menu
		homeHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node source = (Node) mouseEvent.getSource();
				Stage stage = (Stage) source.getScene().getWindow();
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
		//Sets size and location parameters for eCook's menu bar containing home, minimise and close buttons
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
		VBox midBox = new VBox(20);
		VBox rightBox = new VBox();

		//Sets parameters for leftBox, midBox and rightBox
		leftBox.setPrefSize(width*0.2, height - topBox.getPrefHeight()-100);
		midBox.setPrefSize(width*0.6, height - topBox.getPrefHeight()-100);
		midBox.setPadding(new Insets(40,0,10,0));
		rightBox.setPrefSize(width*0.2, height - topBox.getPrefHeight()-100);

		// Create an ArrayList of Recipe Titles
		// create an ArrayList of HBoxes to use to display text and buttons
		// create lists of buttons to use
		downloadButtons = new Button[recipeCollection.getNumberOfRecipes()];
		playButtons = new Button[recipeCollection.getNumberOfRecipes()];
		final ArrayList<HBox> recipeRows = new ArrayList<HBox>();
		ArrayList<String> recipeTitles = new ArrayList<String>();
		for (int i=0; i<recipeCollection.getNumberOfRecipes(); i++) {
			// get recipe title String and set up HBox
			recipeTitles.add(recipeCollection.getRecipe(i).getInfo().getTitle());
			HBox row = new HBox(10);
			row.setId("r" + i);
			row.setPrefWidth(rightBox.getPrefWidth()-10);
			row.setAlignment(Pos.CENTER_LEFT);
			// set up buttons
			downloadButtons[i] = new Button("Download Recipe Content");
			downloadButtons[i].setVisible(false);
			downloadButtons[i].setId(Integer.toString(i));
			downloadButtons[i].setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-font-size: 16px; -fx-text-fill:    #000000; -fx-font-family: 'Buxton Sketch';");
			playButtons[i] = new Button("Play");
			playButtons[i].setVisible(false);
			playButtons[i].setId("p" + i);
			playButtons[i].setStyle("-fx-background-color: rgba(0,0,0,0.04); -fx-font-size: 16px; -fx-text-fill:    #000000; -fx-font-family: 'Buxton Sketch';");
			//Set tool tips
			downloadButtons[i].setTooltip(new Tooltip("Download recipe content file to your local machine (speeds up playback)"));
			playButtons[i].setTooltip(new Tooltip("Click here to open slideshow for selected recipe"));
			// configure the buttons
			downloadButtons[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent event) {
					// Set button state to downloading
					currentDownloadButton = (Button) event.getSource();
					updateButtonLabels(Integer.parseInt(currentDownloadButton.getId()), downloading);
					new Thread(new Runnable() {
						@Override
						public void run() {
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
					index = Integer.valueOf(focusButton.getId().substring(1));
					//Get filename of currently selected recipe
					String fileName = recipeCollection.getRecipe(index).getFileName();
					// play the slideshow
					new SlideShow(stage, fileName, recipeCollection);
				}
			});
			
			// set up the HBox so that mouseover works as a selection tool
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
			
			// set up the HBox so that mouseout hides the buttons for that row
			row.setOnMouseExited(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					int index = 0;
					// get the selected recipe
					HBox focusBox = (HBox) event.getSource();
					index = Integer.valueOf(focusBox.getId().substring(1));
					// hide the buttons for that row
					playButtons[index].setVisible(false);
					downloadButtons[index].setVisible(false);
					lastRowHovered = index;
				}
			});
			// add everything to the HBox
			Label recipeTitle = new Label(recipeTitles.get(i));
			HBox labelBox = new HBox();
			double boxWidth = 9*midBox.getPrefWidth()/16;
			labelBox.setPrefWidth(boxWidth);
			labelBox.getChildren().add(recipeTitle);
			row.getChildren().addAll(labelBox, downloadButtons[i], playButtons[i]);
		
			// add HBox to list
			recipeRows.add(row);
		}

		// Create a list view and populate it with the recipe titles
		final ListView<HBox> listOfRecipes = new ListView<HBox>();
		listOfRecipes.getStylesheets().add("css.css");
		listOfRecipes.setPrefSize(rightBox.getPrefWidth() , rightBox.getPrefHeight()*0.75);
		listOfRecipes.setItems(FXCollections.observableList(recipeRows));
		listOfRecipes.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		// set up the list so that mouseout continues showing the buttons for the last row hovered over
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

		// set the first recipe in the list to be selected on loading
		if (listOfRecipes.getItems().size() != 0) {
			listOfRecipes.getSelectionModel().select(0);
			if (recipeCollection.getRecipe(0) != null) {
				updateInfoLabels(recipeCollection.getRecipe(0));
			}
		}
		else {
			updateInfoLabels(null);
		}
		
		// when recipe selection changes, update the info and ingredients fields
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
	
	// method to update labels in the recipe info box
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

		// add labels for author, version and comment
		Label authorLabel = new Label("Author: " + author);
		//Label versionLabel = new Label("Version: " + version);
		Label commentLabel = new Label("Description: " + comment);
		Label cookLabel = new Label("Cooking Time: " + cook);
		Label prepLabel = new Label("Preperation Time: " + prep);
		Label guestsLabel = new Label("Number of People Serves: " + guests);
		Label vegLabel = new Label("Suitable for Vegetarians? " + veg);


		authorLabel.setWrapText(true);
		authorLabel.setId("authorLabel");
		authorLabel.getStylesheets().add("css.css");
		//versionLabel.setWrapText(true);
		//versionLabel.setId("versionLabel");
		//versionLabel.getStylesheets().add("css.css");
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

		// remove old labels
		recipeInfoBox.getChildren().clear();
		// add the labels to the info box
		//recipeInfoBox.getChildren().addAll(authorLabel, versionLabel, commentLabel);
		recipeInfoBox.getChildren().addAll(authorLabel, cookLabel, prepLabel, guestsLabel, vegLabel, commentLabel);
	}


}