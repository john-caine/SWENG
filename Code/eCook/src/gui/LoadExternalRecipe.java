package gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LoadExternalRecipe {
	
	protected Node optionBox;

	public LoadExternalRecipe(){

	
	VBox optionBox = new VBox(10);
	Button httpBtn = new Button();
	Button fileBrowserBtn = new Button();
	
	httpBtn.setPrefSize(150, 150);
	fileBrowserBtn.setPrefSize(150, 150);
	
	optionBox.getChildren().addAll(httpBtn,fileBrowserBtn);

 	
	
}
	
}
