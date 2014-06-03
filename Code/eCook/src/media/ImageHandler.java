package media;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import eCook.SlideShow;

public class ImageHandler extends SubMedia {

	public ImageHandler(SlideShow parent, String path, int xStart, int yStart, Integer width, Integer height, Integer startTime, Integer duration, Integer layer, Integer branchID, Integer orientation) {
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);
		
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        
        if (width != null && height != null) 
        	resizeImage(iv1, width, height);
        
        hbox.getChildren().add(iv1);
        
        setTimeLines();
	}
	
	/*
	 *Resizes the image to a given width and height
	 *@Param imageView: The image to be resized
	 *@Param width: The new width of the image
	 *@Param height: The new height of the image 
	 */
	public void resizeImage(ImageView imageView, Integer width, Integer height){
    	imageView.setFitWidth(width);
    	imageView.setFitHeight(height);
    	imageView.setSmooth(true);
    	imageView.setCache(true);
    }
	
	/*
	 * Gets the image from a given path
	 * @Param imageLocationPath: The image file location.
	 */
	public Image retrieveImage(String imageLocationPath) {	
		Image image = new Image(imageLocationPath);
		return image;
    }
}