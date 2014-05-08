package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class IngredientPickerScreen {

	public IngredientPickerScreen(final VBox bigBox, final double height, final double width) {
		
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		VBox rightTopBox = new VBox();
		Label recipeInfoLabel = new Label();
		VBox ingredientsListBox = new VBox();
		Button addToList = new Button();
		HBox horizontalBox = new HBox(20);
		
		rightTopBox.setAlignment(Pos.TOP_RIGHT);
		addToList.setAlignment(Pos.CENTER);
		
		horizontalBox.setMaxSize(width-10, height-10);
		horizontalBox.setPrefSize(width-10, height-10);
		leftBox.setPrefSize(horizontalBox.getPrefWidth()/3,horizontalBox.getPrefHeight());
		rightBox.setPrefSize(horizontalBox.getPrefWidth()*2/3 ,horizontalBox.getPrefHeight());
		
		recipeInfoLabel.setWrapText(true);
		recipeInfoLabel.setStyle("-fx-border-color:red; -fx-background-color: beige;");
		String recipeInfo;
		recipeInfo ="Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here Recipe Information goes here";
		recipeInfoLabel.setText(recipeInfo);
		
		recipeInfoLabel.setPrefSize(rightBox.getPrefWidth(), rightBox.getPrefHeight()*1/3);
		
		ingredientsListBox.setPrefSize(rightBox.getPrefWidth(), rightBox.getPrefHeight()*1/3);
		
		
		ScrollPane recipeList = new ScrollPane(); //recipeList is the scroll panel
		recipeList.setStyle("-fx-background: lightgrey;");
		recipeList.setMinSize(leftBox.getPrefWidth() , height*0.5);
		recipeList.setHbarPolicy(ScrollBarPolicy.NEVER);
		recipeList.setVbarPolicy(ScrollBarPolicy.ALWAYS);
	
		//VBox ingredientsListBox = new VBox(10);  //content of the scroll panel
		ingredientsListBox.setPrefWidth(recipeList.getPrefWidth() - 20);
		recipeList.setContent(ingredientsListBox);
		
		
		
		Label testLabel = new Label("Test"); 
		testLabel.setMinSize(recipeList.getMinWidth(), leftBox.getPrefHeight()/4.5 );
	    testLabel.setStyle("-fx-border-color:black; -fx-background-color: white;");
		testLabel.setOnMouseClicked(new EventHandler<MouseEvent>(){

			public void handle(MouseEvent event) {
				System.out.println("Label CLicked");
				bigBox.getChildren().clear();
				new IngredientsScreen(bigBox, height, width);
			}
			
		});
		ingredientsListBox.getChildren().add(testLabel);
		
		
		for (int i = 0; i < 50; i++)
		{
		    Label tempList = new Label("Label " + i); //tempList = temporary list; recipes that make up the content
		    
		    tempList.setMinSize(leftBox.getPrefWidth(), leftBox.getPrefHeight()/4.5);
		    tempList.setStyle("-fx-border-color:pink; -fx-background-color: lightblue;");
		    
		    ingredientsListBox.setPrefHeight(recipeList.getPrefHeight() + tempList.getPrefHeight());
		    recipeList.setPrefHeight(100);
		    ingredientsListBox.getChildren().add(tempList);
	
		    
		}
		bigBox.setPadding(new Insets(40,0,70,0));
		leftBox.getChildren().add(ingredientsListBox);
		horizontalBox.getChildren().addAll(leftBox,rightBox);
		bigBox.getChildren().add(horizontalBox);
	}


}
