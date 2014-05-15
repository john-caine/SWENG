package imagehandler;



import eCook.SlideShow;
import javafx.animation.KeyFrame;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public class ImageHandler {
	
	public HBox imageBox;
	private Integer duration, startTime, branchID;
	private SlideShow parent;
	private Timeline timeLineDuration, timeLineStart;
	private final Integer runDuration;
	
	
	//public ImageHandler(String path, int xStart, int yStart){
		///*Display the original Image in ImageView*/
    	//ImageView iv1 = new ImageView();
        //iv1.setImage(retrieveImage(path));
        //imageBox = new HBox();
        //imageBox.getChildren().add(iv1);
        //setImageLocation(imageBox, xStart, yStart);
       // showImage();
	//}
	
	public ImageHandler(SlideShow parent, String path, int xStart, int yStart, Integer width, Integer height, Integer startTime, Integer duration, Integer layer, Integer branch, Integer orientation) {
		this.duration = duration;
		this.startTime = startTime;
		this.branchID = branch;
		this.parent = parent;
		this.runDuration = duration;
		
		
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        
        if (width != null && height != null) 
        	resizeImage(iv1, width, height);
        if (orientation != null)
        	rotateImage(iv1, orientation);
       
        imageBox = new HBox();
        imageBox.setVisible(false);
        imageBox.getChildren().add(iv1);
        setImageLocation(imageBox, xStart, yStart); 
        
        if (branchID != null && branchID != 0){
        doBranch();
        }
        
      //Create the startTime timeline
      		timeLineStart = new Timeline();
      		
      		//When startTime timeline has finished show the image and if there is a duration begin the duration timeline
      		timeLineStart.setOnFinished(new EventHandler<ActionEvent>(){

      			@Override
      			public void handle(ActionEvent arg0) {
      				showImage();	
      				
      				if(runDuration != null)
      				timeLineDuration.playFromStart();
      			}	
      		});
      		
      		//Create duration timeline
      		timeLineDuration = new Timeline();
      		
      		//When duration timeline has finished remove the image. 
      		timeLineDuration.setOnFinished(new EventHandler<ActionEvent>(){

      			@Override
      			public void handle(ActionEvent arg0) {
      				removeImage();
      			}
      		});

      		createKeyFrame();
      		//Begin start time timeline if the image has a start time.
      		if(startTime != null){
      			System.out.println("Starttime timeline running");
      			timeLineStart.setCycleCount(this.startTime);
      			if(this.duration == null){
      				this.duration = 0;
      			}
      			timeLineDuration.setCycleCount(this.duration);
      			timeLineStart.playFromStart();
      		}
      		//Show image and begin duration timeline 
      		else if(duration != null){
      		 showImage();
      		 System.out.println("Duration timeline running");
      		 timeLineDuration.setCycleCount(this.duration);
      		 timeLineDuration.playFromStart();
      		}
      		else{
      		showImage();
      		}  
      
	}
	
	/*
	 * Rotates the image 
	 * @Param imageView: The image to be rotated
	 * @Param orientation: The new orientation of the image in degrees
	 */
	public void rotateImage(ImageView imageView, Integer orientation) {
		imageView.setRotate(orientation);
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
  /*
   * Sets the image hbox x and y position
   * @Param hbox: HBox containing the image
   * @Param xLocation: x position hbox is to be set to
   * @Param yLocation y position hbox is to be set to
   */
    private void setImageLocation(HBox hbox, int xLocation, int yLocation){
    	hbox.setLayoutX(xLocation);
    	hbox.setLayoutY(yLocation);
    }
    
     /*
	  * Sets the image visibility to true, surrounded by a runnable to be performed on the JavaFX thread
	  */
	 public void showImage() {
	     Platform.runLater (new Runnable() {
				public void run(){
					imageBox.setVisible(true); 
				}
			});	
	 }
	 
	 /*
	  * Sets the image visibility to false, surrounded by a runnable to be performed on the JavaFX thread
	  */
	 public void removeImage() {
		 Platform.runLater (new Runnable() {
				public void run(){
					imageBox.setVisible(false); 
				}
			});	
	 }
	 
	 /*
	  * Sets the event handler to create the branch slide when the image is clicked on.
	  */
	 private void doBranch() {
		 imageBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
		{
			public void handle(MouseEvent e) {
				parent.newSlide(branchID, true, null);
			}
		});
	 }
	 
	 /*
	  * Adds 1 second keyframes to timeLineStart and timeLineDuration
	  */
	 private void createKeyFrame(){
			
			timeLineStart.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event) {
					startTime--;	
				}
			} ));
			timeLineDuration.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){	
				@Override
				public void handle(ActionEvent event) {
					duration--;	
				}
			} ));	
	}
	 
	 /*
	  * Pauses the timeLineStart and timeLineDuration timelines
	  */
	 public void pause(){
		 timeLineStart.pause();
		 timeLineDuration.pause();
		
	 }
	 
	 /*
	  * Resumes the appropriate TimeLine to either remove or show the image if the TimeLines are not stopped
	  */
	 public void resume(){
		 if(imageBox.isVisible() == true && timeLineDuration.getStatus() != Status.STOPPED){
			 timeLineDuration.play();
		 }else if (imageBox.isVisible() == false && timeLineStart.getStatus() != Status.STOPPED){
			 timeLineStart.play();
		 System.out.println(timeLineDuration.getCycleCount());
		 }
	 }
}
