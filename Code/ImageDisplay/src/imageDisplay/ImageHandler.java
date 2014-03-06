/*
 * Programmers: Roger Tan, Jonathan Caine, Zayyad Tagwai
 * Date: 06/03/2014
 * Version: 2.0
 * Description: An image handler that takes X and Y coordinate start positions, and optionally width and 
 * 				height parameters to allow resizing of the image, and returns an ImageView object at the 
 * 				provided coordinates.
 */

package imageDisplay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class ImageHandler {
	
	private ImageView iv1;
	
	/*
	 * Constructor for ImageHandler class. Retrieve the image, resize it, then return the ImageVeiw object.
	 */
	public ImageHandler(String path, Integer xStart, Integer yStart, Integer width, Integer height) {
    	iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        // resize if applicable
        if (width != null || height != null)
        	resizeImage(iv1, width, height);
        setImageLocation(iv1, xStart, yStart);
	}
	
	/*
	 * Resize the image, preserving the aspect ratio if only the height or width is provided, otherwise scale 
	 * based on the height and width provided.
	 */
	private void resizeImage(ImageView imageView, Integer widthSize, Integer heightSize) {
		if(widthSize ==  null && heightSize != null) {
			iv1.setPreserveRatio(true);
			imageView.setFitHeight(heightSize);
		}
		else if (widthSize !=  null && heightSize == null) {
			iv1.setPreserveRatio(true);
			imageView.setFitWidth(widthSize);
		}
		else {
			imageView.setFitWidth(widthSize);
			imageView.setFitHeight(heightSize);
		}
    }
    
	/*
	 * Retrieve the image from its location via a input stream. Error if image not found.
	 */
    public Image retrieveImage(String imageLocationPath) {
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
    
    /*
     * Set the initial location of the image on the screen, based on X and Y coordinates.
     */
    private void setImageLocation(ImageView imageview, int xLocation, int yLocation) {
    	imageview.setLayoutX(xLocation);
    	imageview.setLayoutY(yLocation);
    }
    
    /*
     * Return an ImageView object.
     */
    public ImageView returnImage() {
    	return  iv1;
    }   
}
