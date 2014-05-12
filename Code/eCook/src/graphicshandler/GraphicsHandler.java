package graphicshandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import xmlparser.Point;
import xmlparser.Shape;
import xmlparser.TextString;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class GraphicsHandler {
	
	public HBox	graphicsBox;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Integer duration;
	private Integer startTime;
	protected Integer branchID;
	double[] xCoordinates;
	double[] yCoordinates;
	private List<Point> pointsList;
	private Point point;


	public GraphicsHandler(int totalPoints, int width, int height, String fillColour, Integer startTime, Integer duration,
						   Integer layer, String lineColour, Integer branch, List<Point> points){
		
		this.duration = duration; 
		this.startTime = startTime;
		this.branchID = branch;
		this.pointsList = points;
		
		graphicsBox = new HBox();
		graphicsBox.setVisible(false);
		canvas = new Canvas(width, height);
		graphicsContext = canvas.getGraphicsContext2D();
		
		if (startTime == null) {
        	this.startTime = 0;
        	new Thread(startTimerThread).start();
	    }
	    else{ 
	        new Thread(startTimerThread).start();
		}
		
		//Set the fill colour
		if(fillColour == null){
			graphicsContext.setFill(Color.WHITE);
		}
		else{
			graphicsContext.setFill(Color.web(fillColour));
		}
		
		//Set the line colour
		if(lineColour == null){
			graphicsContext.setStroke(Color.BLACK);
		}
		else{
			graphicsContext.setStroke(Color.web(lineColour));
		}
		
		
		for (int i = 0 ; i < pointsList.size(); i++){
			point = pointsList.get(i);
			graphicsContext.strokePolyline(new double[]{point.getX()}, new double[]{point.getY()}, 1);
		}
	}
//		//Draw the appropriate shapes
//		//Circle
//		if(totalPoints == 1){
//			graphicsContext.fillOval(50, 50, width, height);
//			graphicsContext.strokeOval(50, 50, width, height);
//		}
//		//Line
//		else if(totalPoints == 2){
//			graphicsContext.setLineWidth(5);
//			graphicsContext.strokeLine(50, 50, 100, 100);
//		}
//		//Shapes with 3 or more sided
//		else if(totalPoints >= 3){
////			graphicsContext.strokePolyline(new double[]{50,100,50,50}, new double[]{50,100,100,50}, 4);
//			drawShapes(totalPoints, graphicsContext, width, height);
//		}
//		graphicsBox.getChildren().add(canvas);
//	}
	
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
				 
//					if (branchID != null && branchID != 0)
//						doBranch();
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
		 

//	 private void doBranch() {
//		 graphicsBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
//		{
//			public void handle(MouseEvent e) {
//				parent.newSlide(branchID, true, null);
//			}
//		});
//	 }
	private void drawShapes(int totalPoints, GraphicsContext graphicsContext, int width, int height){
		
		xCoordinates = new double[totalPoints];
		yCoordinates = new double[totalPoints];
		for (int i = 0; i < totalPoints; i++){
			xCoordinates [i] = (int)(width/2 + width/2 * Math.cos(i * 2 * Math.PI / totalPoints));
			yCoordinates [i] = (int)(height/2 + height/2 * Math.sin(i * 2 * Math.PI / totalPoints));
		}
		graphicsContext.strokePolygon(xCoordinates, yCoordinates, totalPoints);
		graphicsContext.fillPolygon(xCoordinates,yCoordinates, totalPoints);
	}
}
