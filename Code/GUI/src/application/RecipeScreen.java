package application;

import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.util.Duration;


public class RecipeScreen {
	GridPane recipeGrid = new GridPane(); 
	
	public RecipeScreen(final VBox bigBox){
		
		/////////////////////////////////////////////////////////////////
		//RS1: TOP
		HBox topBox = new HBox(100);
		ImageView logoHolder1 = new ImageView("SpoonSmall.png");
		ImageView logoHolder2 = new ImageView("eCookLogo.png");
		topBox.getChildren().add(logoHolder1);
		topBox.getChildren().add(logoHolder2);
		recipeGrid.addRow(0, topBox);
		
		
		/////////////////////////////////////////////////////////////////
		//RS2: MID LEFT: CHOICES (i.e. NEWEST, $5 MEALS, UNDER 5 MINUTES ETC)
		VBox midBoxLeft = new VBox(10);
		Button myRecipesBtn = new Button("My Recipes");
		Button newestBtn = new Button("Newest Meals");
		Button fifteenMinMealBtn = new Button("15 Min Meals");
		Button underFiveBtn = new Button("Under $5");
		Button inSeasonBtn = new Button("In Season");
		
		
		/////////////////////////////////////////////////////////////////
		//RS2.1: BUTTON LAYOUT
		myRecipesBtn.setId("b5");
		newestBtn.setId("b6");
		fifteenMinMealBtn.setId("b7");
		underFiveBtn.setId("b8");
		inSeasonBtn.setId("b9");
		
		myRecipesBtn.setMinSize(300, 68);
		newestBtn.setMinSize(300, 68);
		fifteenMinMealBtn.setMinSize(300, 68);
		underFiveBtn.setMinSize(300, 68);
		inSeasonBtn.setMinSize(300, 68);
		
		myRecipesBtn.getStylesheets().add("css.css");
		newestBtn.getStylesheets().add("css.css");
		fifteenMinMealBtn.getStylesheets().add("css.css");
		underFiveBtn.getStylesheets().add("css.css");
		inSeasonBtn.getStylesheets().add("css.css");

		midBoxLeft.getChildren().add(myRecipesBtn);
		midBoxLeft.getChildren().add(newestBtn);
		midBoxLeft.getChildren().add(fifteenMinMealBtn);
		midBoxLeft.getChildren().add(underFiveBtn);
		midBoxLeft.getChildren().add(inSeasonBtn);
		
		
		/////////////////////////////////////////////////////////////////
		//RS3: MID RIGHT: RECIPE LIST 
		ScrollPane recipeList = new ScrollPane(); //recipeList is the scroll panel
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setMinWidth(330);
		recipeList.setMaxHeight(380);
		
		VBox listContent = new VBox(10);  //content of the scroll panel
		listContent.setMinWidth(recipeList.getMinWidth() - 20);
		recipeList.setContent(listContent);
		
		
		/////////////////////////////////////////////////////////////////
		//RS3.1: CHECKING IF LABELS CAN BE MADE TO DO THINGS UPON CLICKING
		Label testLabel = new Label("Test"); 
		testLabel.setMinHeight(50);
	    testLabel.setMinWidth(listContent.getMinWidth());
	    testLabel.setStyle("-fx-border-color:black; -fx-background-color: white;");
		testLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				System.out.println("Label CLicked");
				bigBox.getChildren().remove(recipeGrid);
			}
			
		});
		listContent.getChildren().add(testLabel);
		
		
		////////////////////////////////////////////////////////////////
		//RS3.2: MAKING MULTIPLE LABELS
		for (int i = 0; i < 50; i++)
		{
		    Label tempList = new Label("Label " + i); //tempList = temporary list; recipes that make up the content
		    
		    tempList.setMinHeight(50);
		    tempList.setMinWidth(listContent.getMinWidth());
		    tempList.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    
		    listContent.setPrefHeight(recipeList.getPrefHeight() + tempList.getPrefHeight());
		    recipeList.setMinHeight(100);
		    listContent.getChildren().add(tempList);
		    
			
		    
		}
		
		HBox midBoxRight = new HBox();
		midBoxRight.getChildren().add(recipeList);
		
		
		/////////////////////////////////////////////////////////////////
		//RS4: MID BOX = MID BOX LEFT + MID BOX RIGHT
		HBox midBox = new HBox(20);
		midBox.getChildren().addAll(midBoxLeft,midBoxRight);
		recipeGrid.addRow(1, midBox);
		
		
		/////////////////////////////////////////////////////////////////
		//RS5: FADE EFFECT
		FadeTransition fadeTransition 
        = new FadeTransition(Duration.millis(1000), bigBox);
		 fadeTransition.setFromValue(0.0);
		 fadeTransition.setToValue(1.0);
		 fadeTransition.play();
		bigBox.getChildren().add(recipeGrid);
		bigBox.setVisible(true);
		
		
		/////////////////////////////////////////////////////////////////
		//RS6: GRID PANE ROW PROPERTIES
		RowConstraints row1 = new RowConstraints();
		RowConstraints row2 = new RowConstraints();
		row2.setMinHeight(recipeList.getHeight());
		row2.setVgrow(Priority.ALWAYS);
	    row2.setFillHeight(true);
	    recipeGrid.getRowConstraints().addAll(row1,row2);
	    recipeGrid.setVgap(10);
	    
	}
	 
}
