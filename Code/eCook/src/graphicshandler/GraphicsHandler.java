/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 09/05/2014
 * Graphics Handler that draw shapes based on the XML playlist
 */
package graphicshandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import eCook.SlideShow;
import xmlparser.Point;
import javafx.animation.KeyFrame;
import javafx.animation.Animation.Status;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.util.Duration;

public class GraphicsHandler {
	
	public HBox	graphicsBox;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Integer duration;
	private Integer startTime;
	private SlideShow parent;
	protected Integer branchID;
	double[] xCoordinates;
	double[] yCoordinates;
	private List<Point> pointsList;
	private Point point;
	private Timeline timeLineStart, timeLineDuration;
	private final Integer runDuration; 
	


	public GraphicsHandler(SlideShow parent, int totalPoints, int width, int height, Integer startTime, Integer duration,
						   Integer layer,  String fillColour, String lineColour, Integer branch, List<Point> points){
		
		this.duration = duration; 
		this.startTime = startTime;
		this.branchID = branch;
		this.parent = parent;
		this.pointsList = points;
		this.runDuration = duration;
		
		//Get the size of the screen
		Rectangle2D bounds = Screen.getPrimary().getBounds();
		
		graphicsBox = new HBox();
		graphicsBox.setVisible(false);
		
		//Set the size of the canvas to be the size of the screen
		canvas = new Canvas(bounds.getWidth(), bounds.getHeight());
		graphicsContext = canvas.getGraphicsContext2D();
		
		//Set the fill colour
		graphicsContext.setFill(Color.web(fillColour));
		
		//Set the line colour
		graphicsContext.setStroke(Color.web(lineColour));
		
		//Circle
		if(totalPoints == 1){
			point = pointsList.get(0);
			graphicsContext.fillOval(point.getX(), point.getY(), width, height);
			graphicsContext.strokeOval(point.getX(), point.getY(), width, height);
		}
		// 2 or more points shapes
		else{
			// Declare the size of X and Y coordinates Arrays based on the totalPoints
			xCoordinates = new double[pointsList.size()];
			yCoordinates = new double[pointsList.size()];
		
			for (int i = 0 ; i < pointsList.size(); i++){
				//Retrieve the points in sequence
				point = pointsList.get(i);
				//Populate the appropriate array
				xCoordinates [i] = point.getX();
				yCoordinates [i] = point.getY();
			}
			//Draw the Shape based on the coordinates given
			graphicsContext.strokePolygon(xCoordinates, yCoordinates, pointsList.size());
			graphicsContext.fillPolygon(xCoordinates, yCoordinates, pointsList.size());
		}
		//Add the canvas into a HBox
		graphicsBox.getChildren().add(canvas);
		
		   //Create the startTime timeline
  		timeLineStart = new Timeline();
  		
  		//When startTime timeline has finished show the graphics and if there is a duration begin the duration timeline
  		timeLineStart.setOnFinished(new EventHandler<ActionEvent>(){

  			

			@Override
  			public void handle(ActionEvent arg0) {
  				showGraphics();	
  				
  				if(runDuration != null)
  				timeLineDuration.playFromStart();
  			}	
  		});
  		
  		//Create duration timeline
  		timeLineDuration = new Timeline();
  		
  		//When duration timeline has finished remove the graphics. 
  		timeLineDuration.setOnFinished(new EventHandler<ActionEvent>(){

  			@Override
  			public void handle(ActionEvent arg0) {
  				removeGraphics();
  			}
  		});

  		createKeyFrame();
  		//Begin start time timeline if the graphics has a start time.
  		if(startTime != null){
  			System.out.println("Starttime timeline running");
  			timeLineStart.setCycleCount(this.startTime);
  			if(this.duration == null){
  				this.duration = 0;
  			}
  			timeLineDuration.setCycleCount(this.duration);
  			timeLineStart.playFromStart();
  		}
  		//Show graphics and begin duration timeline 
  		else if(duration != null){
  		 showGraphics();
  		 System.out.println("Duration timeline running");
  		 timeLineDuration.setCycleCount(this.duration);
  		 timeLineDuration.playFromStart();
  		}
  		else{
  		showGraphics();
  		}  
	}
	
	// Thread waits until count = startTime then sets the visibility of the image to true.
		 Task<Object> startTimerThread = new Task<Object>() {
		
				@Override
				protected Object call() throws Exception {
					TimeUnit.SECONDS.sleep(startTime);
				 
					Platform.runLater (new Runnable() {
						public void run(){
							graphicsBox.setVisible(true);
						}
					});	 
				 
					if (branchID != null && branchID != 0)
						doBranch();
					if (duration != null && duration != 0)
						new Thread(durationTimerThread).start();
				 
					return null;
				}
		 };
		 
		 // Thread waits until duration and then sets the image visibility to false once duration = count.
		 Task<Object> durationTimerThread = new Task<Object>() {
			
			 @Override
			protected Object call() throws Exception {
				 TimeUnit.SECONDS.sleep(duration);
					 
				Platform.runLater( new Runnable(){
					public void run(){
						graphicsBox.setVisible(false);
					}
				});	 
				
				return null;
			}
		};

	private void showGraphics(){
		Platform.runLater( new Runnable(){
			public void run(){
				graphicsBox.setVisible(true);
			}
		});	 
	}
	
	private void removeGraphics(){
		Platform.runLater( new Runnable(){
			public void run(){
				graphicsBox.setVisible(true);
			}
		});	
		
	}

	 private void doBranch() {
		 graphicsBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
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
	  * Resumes the appropriate TimeLine to either remove or show the graphics if the TimeLines are not stopped
	  */
	 public void resume(){
		 if(graphicsBox.isVisible() == true && timeLineDuration.getStatus() != Status.STOPPED){
			 timeLineDuration.play();
		 }else if (graphicsBox.isVisible() == false && timeLineStart.getStatus() != Status.STOPPED){
			 timeLineStart.play();
		 System.out.println(timeLineDuration.getCycleCount());
		 }
	 }

	public void stop() {
		timeLineStart.stop();
		 timeLineDuration.stop();
		
		
	}
	 
}
