package gui;
/*
 * Programmer: Ankita Gangotra
 * Date Created: 06/05/2014
 * Makes an abstract menu class for close, minimise and home buttons.
 */
import eCook.RecipeCollection;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class menu {
	private Rectangle2D screenBounds;
	private ImageView homeHolder, closeBtnHolder, minimiseBtnHolder;
	private Image closeIcon, minimiseIcon, homeIcon;
	protected double width;
	protected double height;
	protected Tooltip m,h,c;
	private HBox topBoxLeft, topBoxRight;
	public HBox topBox;
	protected VBox bigBox;

	/*
	 * Constructor for abstract class menu
	 */
	public menu (final RecipeCollection recipeCollection) {

		//Get screenbounds to set width and height
		screenBounds = Screen.getPrimary().getBounds();
		width =  screenBounds.getWidth();
		height = screenBounds.getHeight();

		//Creating buttons for the menu i.e home, minimise and close
		//Add images for the buttons	
		homeIcon = new Image("home1.png");
		closeIcon = new Image("redx.png");
		minimiseIcon = new Image("minimise.png");

		//Set images for buttons
		minimiseBtnHolder = new ImageView(); 
		minimiseBtnHolder.setImage(minimiseIcon);
		closeBtnHolder = new ImageView();
		closeBtnHolder.setImage(closeIcon);
		homeHolder = new ImageView();
		homeHolder.setImage(homeIcon);

		//Add tool tips to the buttons
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
				Node  source = (Node)  mouseEvent.getSource();
				Stage stage  = (Stage) source.getScene().getWindow();
				stage.close();
			}
		});


		//Sets the event to happen when the minimise icon is clicked
		//Gets the node before closing the stage
		minimiseBtnHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node  source = (Node)  mouseEvent.getSource();
				Stage stage  = (Stage) source.getScene().getWindow(); 
				stage.setIconified(true);
			}
		});

		//Sets the event to happen when the home icon is clicked
		//Gets the node before closing the stage and returning to the main menu
		homeHolder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent mouseEvent) {
				Node  source = (Node)  mouseEvent.getSource();
				Stage stage  = (Stage) source.getScene().getWindow();
				Group root = (Group) source.getScene().getRoot();
				root.getChildren().clear();
				root.getChildren().add(new MainMenuContent(stage, recipeCollection).bigBox);
				stage.show();
			}
		});

		//Defining an HBox to hold the buttons 
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
	}

} 

