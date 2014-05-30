/*
 * Programmer: Zayyad Tagwai, Roger Tan, Max Holland & Ankita Gangotra
 * Date Created: 26/05/2014
 * A small window pops up to enable to user to choose between Http or File Browser for Recipe
 */
package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import recipehttpaccess.RecipeBrowser;
import eCook.RecipeCollection;
import filebrowser.FileHandler;
import xmlValidation.XMLValidator;
import xmlparser.Recipe;
import xmlparser.XMLReader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadExternalRecipe {
	
	protected Stage dialog;
	protected InputStream inputStream;
	private FileHandler fileHandler;
	Label statusBar;

	public LoadExternalRecipe(final Stage stage, final RecipeCollection recipeCollection){
		//Creates a new stage bound to the previous that lets the user
		//pick between the two options
		dialog = new Stage();
		
		//Sets at forefront of screen and sets focus on this stage
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        //Removes windows' UI close and minimise buttons
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initOwner(stage);        
        
        Button downloadBtn = new Button("Download Recipes from eCook Store");
        downloadBtn.setWrapText(true);
        Button fileBrowserBtn = new Button("Get Recipe from Local Directory");
        fileBrowserBtn.setWrapText(true);
        
        Label httpLbl = new Label("http://");
        httpLbl.setId("httpLbl");
        httpLbl.getStylesheets().add("file:../Resources/css.css");
        httpLbl.setAlignment(Pos.CENTER);
        httpLbl.setTextAlignment(TextAlignment.CENTER);
        
        final TextField httpField = new TextField();
        httpField.setPromptText("Enter Recipe File's URL");
        httpField.setId("httpField");
        httpField.getStylesheets().add("file:../Resources/css.css");
		//Add tool tip
		Tooltip URL = new Tooltip("Link must end in .XML");
		Tooltip.install(httpField, URL);
        
        final Button getFromURLBtn = new Button("Get Recipe from URL");
        getFromURLBtn.setDisable(true);
        downloadBtn.setPrefSize(256, 228);
        fileBrowserBtn.setPrefSize(256, 228);
        getFromURLBtn.setPrefSize(150, 30);
        httpField.setPrefSize(285, 30);
        
        // set up the status bar
        statusBar = new Label();
        statusBar.setPadding(new Insets(10,200,0,0));
        
        //defining IDs in CSS
        downloadBtn.setId("urlBtn");
        fileBrowserBtn.setId("fileBrowserBtn");
        getFromURLBtn.setId("directUrlBtn");
		downloadBtn.getStylesheets().add("file:../Resources/css.css");
		fileBrowserBtn.getStylesheets().add("file:../Resources/css.css");
		getFromURLBtn.getStylesheets().add("file:../Resources/css.css");
		
		downloadBtn.setWrapText(true);
		downloadBtn.setAlignment(Pos.CENTER);
		downloadBtn.setTextAlignment(TextAlignment.CENTER);
		fileBrowserBtn.setWrapText(true);
		fileBrowserBtn.setAlignment(Pos.CENTER);
		fileBrowserBtn.setTextAlignment(TextAlignment.CENTER);
		getFromURLBtn.setWrapText(true);
		getFromURLBtn.setAlignment(Pos.CENTER);
		getFromURLBtn.setTextAlignment(TextAlignment.CENTER);
		
        VBox loadExtBox = new VBox(20);
        HBox topBox = new HBox();
        HBox midBox = new HBox();
        HBox bottomBox = new HBox(10);
        topBox.setAlignment(Pos.TOP_RIGHT);
        
        //New imageView required as using the old one moves its content as well
        //Adds image onto the box containing the load Ext window made according to the 
        //GUI specifications
		ImageView loadExtWinCloseBtnHolder = new ImageView();
		try {
			inputStream = new FileInputStream("../Resources/redx.png");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		Image closeIcon = new Image(inputStream);
		
		Tooltip c = new Tooltip("Close");
		Tooltip.install(loadExtWinCloseBtnHolder, c);
		
		//Creates and adds close button to top Box
		loadExtWinCloseBtnHolder.setImage(closeIcon);
        topBox.getChildren().addAll(statusBar, loadExtWinCloseBtnHolder);
        
        //adds buttons to midBox and aligns to middle
        midBox.getChildren().addAll(downloadBtn,fileBrowserBtn);
        midBox.setAlignment(Pos.CENTER);
        
        // add URL field and button below
        bottomBox.getChildren().addAll(httpLbl, httpField, getFromURLBtn);
        
        // add everything to the dialog box
        loadExtBox.getChildren().addAll(topBox,midBox,bottomBox);
        loadExtBox.setStyle("-fx-background-size: cover; -fx-background-position: center center;-fx-background-image: url('file:../Resources/ingredients.jpg');");
        Scene dialogScene = new Scene(loadExtBox, 500, 300);
        dialog.setScene(dialogScene);
        dialog.show();
        
        //Adds action to the buttons and makes it close upon 
        //selection of option
        
        // download button calls the recipe browser class
		downloadBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				stage.setOpacity(0.85);
				Stage popup = new Stage();
				new RecipeBrowser(popup, recipeCollection, true, statusBar);
				popup.centerOnScreen();
			}
		});
		
		// get from URL button gets XML file via HTTP and saves to defaults folder
		getFromURLBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String fileURL = "http://";
				// if valid filepath
				if (httpField != null && !httpField.getText().equals("") && httpField.getText().endsWith(".xml")) {
					// complete the URL from the textfield
					fileURL = fileURL + httpField.getText().toString();
					// call the recipe browser (no GUI)
					RecipeBrowser browser = new RecipeBrowser(dialog, recipeCollection, false, statusBar);
					try {
						String assumedFileName = fileURL.substring(fileURL.length()-12);
						browser.downloadRecipeFile(fileURL, "defaultRecipes/" + assumedFileName);
						// update the recipe collection with the new file (parse)
            			XMLReader reader = new XMLReader("defaultRecipes/" + assumedFileName);
            			Recipe newRecipe = reader.getRecipe();
            			newRecipe.setFileName(assumedFileName);
            			// only add the recipe file if it doesn't already exist in eCook
            			boolean exists = false;
            			for (int j=0; j<recipeCollection.getNumberOfRecipes(); j++) {
            				if (recipeCollection.getRecipe(j).getFileName().equals(newRecipe.getFileName())) {
            					exists = true;
                			}
            			}
            			if (!exists) {
            				recipeCollection.addRecipe(newRecipe);
            			}
					} catch (Exception e) {
						System.out.println("error downloading recipe file from URL");
					}
				}
				dialog.close();
			}
		});
		
		fileHandler = new FileHandler();
		// call the file handler to get a local XML file
		fileBrowserBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				String filepath = fileHandler.openFile(dialog);
				// if the filepath points to a vaild XML file, copy to the defaults folder
				if (filepath != null) {
					Path source = Paths.get(filepath);
					Path defaultsFolder = Paths.get("defaultRecipes/");
					try {
						// parse the source file through the reader to validate
						XMLReader reader = new XMLReader(filepath);
						// Check integrity of XML file, report error message if invalid
				    	XMLValidator validator = new XMLValidator(reader);
				    	if (validator.isXMLBroken()) {
				    		statusBar.setText(validator.getErrorMsg());
						} else {
							// copy the file to the defaults folder if it passes the validation test
							Files.copy(source, defaultsFolder.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
							Recipe newRecipe = reader.getRecipe();
							newRecipe.setFileName(source.getFileName().toString());
							recipeCollection.addRecipe(newRecipe);
							dialog.close();
						}
					} catch (IOException e) {
						System.out.println("Error copying XML file from local directory to defaults folder");
						e.printStackTrace();
					}
				}
			}
		});
		
		loadExtWinCloseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage dialog= (Stage) source.getScene().getWindow();
         	dialog.close();
            }
        });
		
		// add a keylistener to detect when a URL is entered
		httpField.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent event) {
				// only allow URLs which end with ".xml"
				if (!httpField.getText().equals("") && httpField.getText().endsWith(".xml")) {
					getFromURLBtn.setDisable(false);
					getFromURLBtn.setTooltip(new Tooltip("Click here to download recipe from external website"));
				}
				else {
					getFromURLBtn.setDisable(true);
				}
			}
		});
		
		//Allows for using the ESC key to exit the stage
		dialog.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
		        public void handle(KeyEvent t) {
		          if(t.getCode()==KeyCode.ESCAPE)
		          {
		        	  dialog.close();
		          }
		        }
		});
}	
}
