package imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ImageHandler {
	
	HBox box;
	
	public ImageHandler(String path, int xStart, int yStart, int width){
		/*Display the original Image in ImageView*/
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        resizeImage(iv1, width);
        box = new HBox();
        box.getChildren().add(iv1);
        setImageLocation(box, xStart, yStart);	
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
}
