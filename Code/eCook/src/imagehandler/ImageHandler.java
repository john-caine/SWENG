package imagehandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import mainmenu.SlideMain;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ImageHandler {
	
	public HBox box;
	private Integer duration;
	private Integer startTime;
	private Integer branchID;
	
	public ImageHandler(String path, int xStart, int yStart){
		/*Display the original Image in ImageView*/
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        box = new HBox();
        box.getChildren().add(iv1);
        setImageLocation(box, xStart, yStart);
        showImage();
	}
	
	public ImageHandler(String path, int xStart, int yStart, Integer width, Integer height, Integer startTime, Integer duration, Integer layer, Integer branch, Integer orientation){
		/*Display the original Image in ImageView*/
		this.duration = duration;
		this.startTime = startTime;
		this.branchID = branch;
    	ImageView iv1 = new ImageView();
        iv1.setImage(retrieveImage(path));
        if (width != null && height != null) 
        	resizeImage(iv1, width, height);
        if (orientation != null)
        	rotateImage(iv1, orientation);
        box = new HBox();
        box.setVisible(false);
        box.getChildren().add(iv1);
        setImageLocation(box, xStart, yStart); 
        if (startTime == null) {
        	this.startTime = 0;
        	startTimerThread.start();
        }
        else 
        	startTimerThread.start();
	}
	
	public void rotateImage(ImageView imageView, Integer orientation) {
		imageView.setRotate(orientation);
	}
	
	public void resizeImage(ImageView imageView, Integer width, Integer height){
    	imageView.setFitWidth(width);
    	imageView.setFitHeight(height);
    	imageView.setSmooth(true);
    	imageView.setCache(true);
    }
	
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
    
    private void setImageLocation(HBox hbox, int xLocation, int yLocation){
    	hbox.setLayoutX(xLocation);
    	hbox.setLayoutY(yLocation);
    }
    
	 public void showImage() {
	     box.setVisible(true); 
	 }
	 
	 public void removeImage() {
		 box.setVisible(false);
	 }

	 Thread startTimerThread = new Thread("startTimer") {
		 public void run() {
			 int count=0;
			 while (count <= startTime && startTime != 0) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				count++;
		 	}
		 
		 showImage();
		 if (branchID != null && branchID != 0)
			 doBranch();
		 if (duration != null && duration != 0)
			 durationTimerThread.start();
		 }
	 };
	 
	 Thread durationTimerThread = new Thread("durationTimer") {
		 public void run() {
			 int count=0;
			 while (count <= duration) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 count++;
			 }
			 removeImage();
		 }
	 };
	 
	 private void doBranch() {
		 box.setOnMouseClicked(new EventHandler<MouseEvent> ()
		{
			public void handle(MouseEvent e) {
				//newSlide(branchID, true);
			}
		});
	 }
}

/*
	private void newSlide(Integer slideID, Boolean isBranch) {
		Node  source = (Node)  event.getSource();
    	Stage stage  = (Stage) source.getScene().getWindow();
		
		root.setVisible(false);
		
		Group root2 = new Group();
    	Scene scene2 = new SlideMain(root2).scene;
    	
    	//root.getChildren().add()
    	
    	stage.setScene(scene2);
    	stage.sizeToScene();
    	stage.setFullScreen(false);
    	stage.setFullScreen(true);
    	stage.show();
	}
*/