package imageDisplay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class ExampleClass {
	
	ImageView iv1;
	
	public ExampleClass(String path, Integer xStart, Integer yStart, Integer width, Integer height){
		/*Display the original Image in ImageView*/
    	iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        if(width != null && height != null){
        	resizeImage(iv1, width, height);
        }
        resizeImage(iv1, width, height);
        setImageLocation(iv1, xStart, yStart);	
	}
	
	public void resizeImage(ImageView imageView, Integer widthSize, Integer heightSize){
		if(widthSize ==  null && heightSize != null){
			iv1.setPreserveRatio(true);
			imageView.setFitHeight(heightSize);
		}
		else if (widthSize !=  null && heightSize == null){
			iv1.setPreserveRatio(true);
			imageView.setFitWidth(widthSize);
		}
		else{
    	imageView.setFitWidth(widthSize);
    	imageView.setFitHeight(heightSize);
		}
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
    
    public void setImageLocation(ImageView imageview, int xLocation, int yLocation){
    	imageview.setLayoutX(xLocation);
    	imageview.setLayoutY(yLocation);
    }
    
    public ImageView getImage(){
		return  iv1; 	
    }
    
}
