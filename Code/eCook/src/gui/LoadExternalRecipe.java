/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 26/05/2014
 * A small window pops up to enable to user to choose between Http or File Browser for Recipe
 */
package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadExternalRecipe {
	
	protected Stage dialog;
	protected InputStream inputStream;

	public LoadExternalRecipe(Stage stage){
		//Creates a new stage bound to the previous that lets the user
		//pick between the two options
		dialog = new Stage();
		
		//Sets at forefront of screen and sets focus on this stage
        dialog.initModality(Modality.APPLICATION_MODAL);
        
        //Removes windows' UI close and minimise buttons
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.initOwner(stage);        
        
        Button urlBtn = new Button("HTTP://");
        Button fileBrowserBtn = new Button("Browse...");
        urlBtn.setPrefSize(256, 228);
        fileBrowserBtn.setPrefSize(256, 228);
        
        //defining IDs in CSS
        urlBtn.setId("urlBtn");
        fileBrowserBtn.setId("fileBrowserBtn");
		urlBtn.getStylesheets().add("file:../Resources/css.css");
		fileBrowserBtn.getStylesheets().add("file:../Resources/css.css");
		
        VBox loadExtBox = new VBox(20);
        HBox topBox = new HBox();
        HBox midBox = new HBox(); 
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
		
		//Creates and adds close button to top Box
		loadExtWinCloseBtnHolder.setImage(closeIcon);
        topBox.getChildren().add(loadExtWinCloseBtnHolder);
        
        //adds buttons to midBox and aligns to middle
        midBox.getChildren().addAll(urlBtn,fileBrowserBtn);
        midBox.setAlignment(Pos.CENTER);
        loadExtBox.getChildren().addAll(topBox,midBox);
        loadExtBox.setStyle("-fx-background-color: lightgrey");
        Scene dialogScene = new Scene(loadExtBox, 500, 295);
        dialog.setScene(dialogScene);
        dialog.show();
        
      //Adds action to the buttons and makes it close upon 
       //selection of option
		urlBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("URL BUTTON CLICKED");
				dialog.close();
			}
		});
	
		fileBrowserBtn.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				System.out.println("FILE BROWSER BUTTON CLICKED");
				dialog.close();
			}
		});
		loadExtWinCloseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
            Node  source = (Node)  mouseEvent.getSource();
         	Stage dialog= (Stage) source.getScene().getWindow();
         	dialog.close();
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
