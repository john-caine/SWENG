/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: Image Handler, generates an ImageView with image from the set attributes.
 */
package media;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import eCook.SlideShow;
import eCook.eCook;
import errorhandler.ErrorHandler;

public class ImageHandler extends SubSlideMedia {
	
	private Logger logger;
	private ImageView iv1;

	/**
	 * Image Handler Constructor
	 * @Param parent: The slideshow that called the handler
	 * @Param path: The file path of the image
	 * @Param xStart: The x co ordinate of the top left of the hBox
	 * @Param yStart: The y co ordnate of the top left of the hBox
	 * @Param width: The width of the image
	 * @Param height: The height of the image
	 * @Param startTime: The time the image is to appear on the screen
	 * @Param duration: The time the image is to be removed from the screen
	 * @Param branchID: The branch the image is to branch to
	 * @Param orientation: The orientation of the image in degrees
	 */

	public ImageHandler(SlideShow parent, String path, int xStart, int yStart, Integer width, Integer height, Integer startTime, Integer duration, Integer branchID, Integer orientation) {
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);

		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		//Create a new Image View
    	iv1 = new ImageView();
    	
    	//Set the image from the file path
        iv1.setImage(retrieveImage(path));
        
        //If a width and height has been given resize the image
        if (width != null && height != null) 
        	resizeImage( width, height);
        logger.log(Level.INFO, "Image attributes set");
        
        //Add the image to hbox
        hbox.getChildren().add(iv1);
        
        setTimingValues();
	}
	
	/**
	 *Resizes the image to a given width and height
	 *@Param imageView: The image to be resized
	 *@Param width: The new width of the image
	 *@Param height: The new height of the image 
	 */
	protected void resizeImage( Integer width, Integer height){
    	iv1.setFitWidth(width);
    	iv1.setFitHeight(height);
    	iv1.setSmooth(true);
    	iv1.setCache(true);
    }
	
	/**
	 * Gets the image from a given path
	 * @Param imageLocationPath: The image file location.
	 */
	protected Image retrieveImage(String imageLocationPath) {	
		Image image = null;
		try {
			image = new Image(imageLocationPath);
		}
		catch (NullPointerException | IllegalArgumentException e) {
			new ErrorHandler("Fatal error displaying media");
		}
		
		return image;
    }
	
	/**
	 * Gets the ImageView object
	 * @return The created ImageView oject
	 */
	protected ImageView getImageView(){
		return iv1;
	}
}
