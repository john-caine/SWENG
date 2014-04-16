package application;

import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class RecipeScreen {
	GridPane recipeGrid = new GridPane(); 
	
	public RecipeScreen(HBox bigBox){
		//TOP
		HBox topBox = new HBox(100);
		ImageView logoHolder1 = new ImageView("SpoonSmall.png");
		ImageView logoHolder2 = new ImageView("eCookLogo.png");
		topBox.getChildren().add(logoHolder1);
		topBox.getChildren().add(logoHolder2);
		recipeGrid.addRow(0, topBox);
		
		//MID LEFT: CHOICES (i.e. NEWEST, $5 MEALS, UNDER 5 MINUTES ETC)
		
		
		//MID RIGHT: RECIPE LIST 
		ScrollPane recipeList = new ScrollPane(); //recipeList is the scroll panel
		recipeList.setStyle("-fx-background-color: lightblue;");
		recipeList.setMinWidth(200);
		recipeList.setMaxHeight(440);
		
		
		VBox listContent = new VBox(10);  //content of the scroll panel
		listContent.setMinWidth(recipeList.getMinWidth() - 20);
		recipeList.setContent(listContent);
		
		for (int i = 0; i < 50; i++)
		{
		    Label temp_list = new Label("Label " + i); //temp_list = temporary list; recipes that make up the content
		    
		    temp_list.setMinHeight(50);
		    temp_list.setMinWidth(listContent.getMinWidth() - 50);
		    temp_list.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    
		    listContent.setPrefHeight(recipeList.getPrefHeight() + temp_list.getPrefHeight());
		    recipeList.setMinHeight(100);
		    listContent.getChildren().add(temp_list);
		    
		}
		
		//MID
		HBox midBoxSelection = new HBox(100);
		midBoxSelection.getChildren().add(recipeList);
		RowConstraints row1 = new RowConstraints();
		row1.setMinHeight(recipeList.getHeight());
//	    row1.setMaxHeight(recipeList.getPrefHeight());
//	    row1.setMinHeight(recipeList.getPrefHeight());
	    row1.setFillHeight(true);
	    recipeGrid.getRowConstraints().add(row1);
		recipeGrid.addRow(2, midBoxSelection);
		
		
		
		FadeTransition fadeTransition 
        = new FadeTransition(Duration.millis(1000), bigBox);
		 fadeTransition.setFromValue(0.0);
		 fadeTransition.setToValue(1.0);
		 fadeTransition.play();
		bigBox.getChildren().add(recipeGrid);
		bigBox.setVisible(true);
	}
	 
}
