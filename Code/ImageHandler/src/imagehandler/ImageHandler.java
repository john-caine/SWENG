package imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ImageHandler extends Application {
	
	private HBox box;
	private Integer duration;
	private Group root;
	private Scene scene;
	private Stage stage;
	
	public ImageHandler(String path, int xStart, int yStart){
		/*Display the original Image in ImageView*/
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        box = new HBox();
        box.getChildren().add(iv1);
        setImageLocation(box, xStart, yStart);
        showImage();
	}
	
	public ImageHandler(String path, int xStart, int yStart, Integer width, Integer height, Integer startTime, Integer duration, Integer layer, Integer branch){
		/*Display the original Image in ImageView*/
		this.duration = duration;
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        if (width != null && height != null) 
        	resizeImage(iv1, width, height);
        box = new HBox();
        box.getChildren().add(iv1);
        setImageLocation(box, xStart, yStart);	
	}
	
	public void resizeImage(ImageView imageView, Integer width, Integer height){
    	imageView.setFitWidth(width);
    	imageView.setFitHeight(height);
    	imageView.setSmooth(true);
    	imageView.setCache(true);
    }
    
    private Image retrieveImage(String imageLocationPath){
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
    
    private void setImageLocation(HBox hbox, int xLocation, int yLocation){
    	hbox.setLayoutX(xLocation);
    	hbox.setLayoutY(yLocation);
    }
    
	 public void showImage() {
		 root = new Group();
	     scene = new Scene(root);
	     scene.setFill(Color.BLACK);
	     //ImageHandler image = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 0, 0);
	     ImageHandler image1 = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 300, 300, 500, 500, null, null, null, null);
	     //root.getChildren().add(image.box);
	     root.getChildren().add(image1.box);
	     stage.setTitle("ImageView");
	     stage.setFullScreen(true);
	     stage.setScene(scene); 
	     stage.sizeToScene(); 
	     stage.show(); 
	 }

	 Thread timerThread = new Thread("timer") {
		 public void run() {
			 int count=0;
			 while (count <= duration)
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 count++;
				 showImage();
		 }
	 };
}
