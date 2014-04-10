package recipehttpaccess;
/* Title: RecipeBrowser
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 20/03/14
 * 
 * Description: A simple mock-up of a GUI to browse recipe files available to download.
 * 				Behind the scenes (no javafx pun intended) we have methods to get the contents 
 * 				of a recipe server from a text file and populate this as a listview.
 * 				Also, methods to allow a user to select a local directory to save the files
 * 				and finally a method to download selected files to the local directory.
 */
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class RecipeBrowser extends Application {
	ArrayList<String> availableRecipeFiles;
	ListView<String> listOfRecipeFiles;
	File localRecipeDirectory;
	Button downloadButton;
	Scene scene;
	
	// method to get the names of the recipe files available for download
	public void getAvailableRecipeFiles(String rootURL) throws Exception {
		availableRecipeFiles = new ArrayList<String>();
		URL serverContents = new URL(rootURL);
		BufferedReader in = new BufferedReader(new InputStreamReader(serverContents.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			availableRecipeFiles.add(inputLine);
		}
		in.close();
	}
	
    // method to test for download button enable
    public void testEnableDownloadButton() {
    	/* only enable the download button when a local file directory has been chosen
    	 * and some recipe files have been selected
    	 */
    	if (!(localRecipeDirectory == null) && !(listOfRecipeFiles.getSelectionModel().getSelectedItem() == null)) {
    		downloadButton.setDisable(false);
    	}
    	else {
    		downloadButton.setDisable(true);
    	}
    }
	
	// method to retrieve file from URL and save locally
	 public void downloadRecipeFile(String recipeURL, String localFileName) throws Exception {
		 URL url = new URL(recipeURL);
		 URLConnection connection = url.openConnection();
		 InputStream inputStream = connection.getInputStream();
		 FileOutputStream fileOutputStream = new FileOutputStream(localFileName);
		 byte[] buffer = new byte[512];
		 
		 while (true) {
		     int length = inputStream.read(buffer);
		     if (length == -1) {
		         break;
		     }
		     fileOutputStream.write(buffer, 0, length);
		 }
		 
		 inputStream.close();
		 fileOutputStream.flush();
		 fileOutputStream.close();
	}
	
	// method to display a basic GUI for recipe file downloads
    public void start(final Stage primaryStage) {
    	ProgressIndicator loading = new ProgressIndicator();
    	final Label header = new Label("Recipes available to download:");
    	Button selectLocalFileDestination = new Button("Select Local Recipe File Destination");
        downloadButton = new Button("Download Selected Recipes");
        downloadButton.setDisable(true);
        HBox buttons = new HBox();
        buttons.getChildren().add(0, selectLocalFileDestination);
        buttons.getChildren().add(1, downloadButton);
        
        // do javaFX setup
        StackPane root = new StackPane();
        scene = new Scene(root, 600, 500);
        primaryStage.setTitle("Recipe Browser");
        primaryStage.setScene(scene);
        primaryStage.show();
        final BorderPane border = new BorderPane();
        root.getChildren().add(border);
        border.setTop(loading);
        
        // configure the select local file destination button
        selectLocalFileDestination.setOnAction(new EventHandler<ActionEvent>() {
        	
        	public void handle(ActionEvent event) {
            	// prompt user to select local destination for recipe files
            	DirectoryChooser directoryChooser = new DirectoryChooser();
            	directoryChooser.setTitle("Select Folder to Save Recipe Files");
            	localRecipeDirectory = directoryChooser.showDialog(primaryStage);
            	
            	testEnableDownloadButton();
        	}
        });
        
        // configure the download button
        downloadButton.setOnAction(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
            	// download and save all selected recipe files
            	System.out.println("Downloading Recipes...");
            	String rootURL = "http://www.propartydj.co.uk/SWEng/";
            	ObservableList<String> selectedFilesList = listOfRecipeFiles.getSelectionModel().getSelectedItems();
            	for (int i=0; i<selectedFilesList.size(); i++) {
            		try {
            			String fileURL = rootURL + selectedFilesList.get(i);
            			String uniqueLocalFileName = localRecipeDirectory.toString() + "/" + selectedFilesList.get(i);
            			downloadRecipeFile(fileURL, uniqueLocalFileName);
            		} 
            		catch (Exception e) {
            			System.out.println("Error when downloading and saving selected recipe files");
            			e.printStackTrace();
            		}
            	}
            	border.setTop(null);
            	border.setBottom(null);
            	border.setCenter(new Label(selectedFilesList.size() + " Recipes saved to " + localRecipeDirectory));
            }
        });

        // get available recipes from server and show list
        try {
			getAvailableRecipeFiles("http://www.propartydj.co.uk/SWEng/contents.txt");
			listOfRecipeFiles = new ListView<String>();
			listOfRecipeFiles.setItems(FXCollections.observableList(availableRecipeFiles));
			listOfRecipeFiles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			border.setCenter(listOfRecipeFiles);
		} catch (Exception e) {
			System.out.println("Problem accessing recipe files on server.");
			e.printStackTrace();
		}
		border.setTop(header);

		if (availableRecipeFiles.isEmpty()) {
			header.setText("Sorry. No recipes available to download.");
			border.setBottom(header);
		}
		else {
			border.setBottom(buttons);
		}
		
		// decide whether or not to enable the download button when list is clicked
        listOfRecipeFiles.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                testEnableDownloadButton();
            }
        });
    }
 
// main method 
 public static void main(String[] args) {
        launch(args);
    }
}