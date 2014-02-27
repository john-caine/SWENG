package imageDisplay;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene; 
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage; 

public class ImageDisplay extends Application {
	
	public HBox box;
	public Group root;
	public Scene scene;
    @Override 
    public void start(Stage stage) {
    	
        root = new Group();
        scene = new Scene(root);
        scene.setFill(Color.BLACK);

        /*Display the original Image in ImageView*/
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage("C:/Users/R T/workspace/ExampleProject/src/DOGE.png"));
        
        /*Display the resized Image in ImageView while preserving the ratio*/
        ImageView iv2 = new ImageView();
        iv2.setImage(retrieveImage("C:/Users/R T/workspace/ExampleProject/src/DOGE.png"));
        resizeImage(iv2, 200);

        /*Display the rotated and zoomed image in ImageView*/
        ImageView iv3 = new ImageView();
        iv3.setImage(retrieveImage("C:/Users/R T/workspace/ExampleProject/src/DOGE.png"));
        Rectangle2D viewportRect = new Rectangle2D(40, 35, 110, 110);
        iv3.setViewport(viewportRect);
        iv3.setRotate(90);
       
        box = new HBox();
        box.getChildren().add(iv1);
        box.getChildren().add(iv2);
        box.getChildren().add(iv3);
        setImageLocation(box, 200, 200);
        root.getChildren().add(box);
        
        stage.setTitle("ImageView");
        stage.setFullScreen(true);
        stage.setScene(scene); 
        stage.sizeToScene(); 
        stage.show(); 
    }
    
    public void resizeImage(ImageView imageView, int widthSize){
    	imageView.setFitWidth(widthSize);
    	imageView.setPreserveRatio(true);
    	imageView.setSmooth(true);
    	imageView.setCache(true);
    }
    
    public Image retrieveImage(String imageLocationPath){
    	InputStream inputStream = null;
    	Image image;
    	try {
    		inputStream = new FileInputStream(imageLocationPath);
		} catch (FileNotFoundException e) {
			System.out.println("Image cannot be located!");
		}
    	image = new Image(inputStream);
    	return image;
    }
    
    public void setImageLocation(HBox hbox, int xLocation, int yLocation){
    	hbox.setLayoutX(xLocation);
    	hbox.setLayoutY(yLocation);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}