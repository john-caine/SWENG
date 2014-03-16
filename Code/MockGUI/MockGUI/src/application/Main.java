package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/*
 * If there is any chance of renaming the class, as Main as a class name is not a good idea if possible in Java.
 */
public class Main extends Application {

	/* You were making 2 VBoxs called buttonPanel so i'm amazed you managed to get it to work at all. 1 in the main method
	 * and 1 in buttonstobox(). I've moved it here so its a global that can be referenced by both functions.
	 */
	VBox buttonPanel = new VBox();
	GridPane grid = new GridPane();

	/* Its really bad practice to hard code any values into methods. They should ideally be in header files, #define(d) values,
	 * or set as globals so as to be easily changed.
	 * 
	 * Also i don't understand what these values are (should be named appropiately not value1 ...) or why exactly you need them?
	 * Also why do they need to be doubles (decimal point)? This takes up twice as much space in memory as an int.
	 */
	final static double value1 = 5;
	final static double value2 = 13.8;
	final static double value3 = 1.2;
	final static double value4 = 10.1;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		/* there is no need for these to have been globals, globals should be avoided if at all possible (they are a last
		 * resort!). They can be local variables and passed between methods as per below.
		 */
		double sceneX = 640;
		double sceneY = 480;
		double buttonWidth;
		double buttonHeight;
		
		Scene scene = new Scene(grid, sceneX, sceneY);
		/* No idea what the line below is supposed to do mate?? */
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		scene.setFill(Color.BEIGE);
		
		/* you need a variable to return the results into so you can use them, otherwise why bother returning anything? */
		buttonHeight = buttonHeightCalc(sceneY);
		buttonWidth = buttonWidthCalc(sceneX);
		
	    //CHECKS
		System.out.println("Output Width:" + buttonWidth);
		System.out.println("Output Height:" + buttonHeight);
		System.out.println("Finished buttonSize \n");			
		//END CHECKS
		 
		buttonsToBox(buttonHeight, buttonWidth);
		
		//CHECKS
		System.out.println("Finished button Instances \n");
		//END CHECKS
		
		//grid.add(buttonPanel, 2, 4);
		grid.getChildren().add(buttonPanel);
		grid.setVisible(true);
		
		//CHECKS
		System.out.println("\n AND AFTER THE GRID \n");
		System.out.printf("\n buttonHeight is %.0f",buttonHeight);
        System.out.printf("\n buttonWidth is %.0f",buttonWidth);
        System.out.printf("\n stageHeight is %.0f",sceneY);
        System.out.printf("\n stageWidth is %.0f \n",sceneX);
		//END CHECKS
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main GUI Test");
		primaryStage.show();			
	}
	
	public double buttonHeightCalc(double sceneY) {
		/* just return the result of the calculation - why bother with a variable? */
		return sceneY*(value1/value2);
	}
	
	public double buttonWidthCalc(double sceneX) {
		/* just return the result of the calculation - why bother with a variable? */
		return sceneX*(value3/value4);
	}
	
	//Making and placing the buttons in a VBox
	public void buttonsToBox(double buttonHeight, double buttonWidth) {
		/* 
		 * You had this method being of type VBox and returning the buttonPanel at the end. There was no need for this
		 * as again you never returned the object into any variables and didn't need to return as you didn't do anything 
		 * with the object. Therefore i've made this method of type void so it doesn't require a return statement.
		 */
		buttonPanel.setSnapToPixel(true);
		buttonPanel.setStyle("-fx-base: #b6e7c9;");

		/* make 4 new buttons - do you want them to say something? (you need to add labels etc to them) */
		Button button1 = new Button();
		Button button2 = new Button();
		Button button3 = new Button();
		Button button4 = new Button();
		
		/* I understand what you are trying to do here, but using the fxml application thing to draw the
		 * GUI is a bad idea as you've seen the large amounts of junk code it produces below. I mean that all means 
		 * absolutely nothing what so ever to me which is saying something ... :-p
		 */
		button1.setStyle("-fx-padding: 8 15 15 15;-fx-background-insets: 0,0 0 5 0, 0 0 6 0, 0 0 7 0;-fx-background-radius: 8; -fx-background-color:linear-gradient(from 0% 93% to 0% 100%, #a34313 0%, #903b12 100%), #9d4024,#d86e3a,radial-gradient(center 50% 50%, radius 100%, #d86e3a, #c54e2c);-fx-effect: dropshadow( gaussian , rgba(0,0,0,0.75) , 4,0,0,1 );-fx-font-weight: bold;-fx-font-size: 1.1em;");
		button2.setStyle("-fx-background-color: rgba(0,0,0,0.08), linear-gradient(#9a9a9a, #909090), linear-gradient(white 0%, #f3f3f3 50%, #ececec 51%, #f2f2f2 100%); -fx-background-insets: 0 0 -1 0,0,1;-fx-background-radius: 5,5,4;-fx-padding: 3 30 3 30;-fx-text-fill: #242d35;-fx-font-size: 14px;");
		button3.setStyle("-fx-background-color: #3c7fb1,linear-gradient(#fafdfe, #e8f5fc),linear-gradient(#eaf6fd 0%, #d9f0fc 49%, #bee6fd 50%, #a7d9f5 100%); -fx-background-insets: 0 0 -1 0,0,1;-fx-background-radius: 5,5,4;-fx-padding: 3 30 3 30;-fx-text-fill: #242d35;-fx-font-size: 14px;");
		button4.setStyle("-fx-font: 22 gadugi; -fx-base: #b6e7c9;");
		button4.setLayoutX(buttonWidth);
		button4.setLayoutY(buttonHeight);
		
		/* Add all buttons to gridpane in numerical order 
		 * 
		 * Also you had addAll(new Button()); which only adds a new blank button to the gridpane not any of
		 * the buttons you created a few lines above.
		 */ 
		buttonPanel.getChildren().addAll(button1, button2, button3, button4);

		/* In order to add the buttons to different locations on the gridPane you need to change where you are adding them on
		 * the gridPane not on the vBox! You can just add the button directly to a gridPane rather than to a VBox then the VBox
		 * to the gridPane. Thats why it wasn't working when you adjusted the columns and rows values.
		 * 
		 * In order to do this i had to make gridPane a global as well. You should only keep gridPane as a global if you need
		 * to do the below, otherwise you should move it back to being a local variable for the Start() method.
		 * 
		 * To make this work you need to comment the 4 lines below as you can only have line 124 OR the lines below!!
		 */
		grid.add(button1, 2, 3);
		grid.add(button2, 1, 4);
		grid.add(button3, 1, 1);
		grid.add(button4, 3, 2);
	}
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
	