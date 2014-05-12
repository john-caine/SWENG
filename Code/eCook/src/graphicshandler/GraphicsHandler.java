package graphicshandler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import eCook.SlideShow;
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
	private SlideShow parent;
	protected Integer branchID;
	double[] xCoordinates;
	double[] yCoordinates;
	private List<Point> pointsList;
	private Point point;


	public GraphicsHandler(SlideShow parent, int totalPoints, int width, int height, Integer startTime, Integer duration,
						   Integer layer,  String fillColour, String lineColour, Integer branch, List<Point> points){
		
		this.duration = duration; 
		this.startTime = startTime;
		this.branchID = branch;
		this.parent = parent;
		this.pointsList = points;
		
		graphicsBox = new HBox();
		graphicsBox.setVisible(false);
		canvas = new Canvas(400, 400);
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
		//Line
		else if(totalPoints == 2){
			xCoordinates = new double[pointsList.size()];
			yCoordinates = new double[pointsList.size()];
			
			for(int i = 0 ;  i < pointsList.size(); i++){
				point = pointsList.get(i);
				xCoordinates [i] = point.getX();
				yCoordinates [i] = point.getY();
			}
			
			graphicsContext.setLineWidth(2);
			graphicsContext.strokeLine(xCoordinates [0], xCoordinates [0], yCoordinates [1], yCoordinates [1]);
		}
		//Anything else
		else{
			xCoordinates = new double[pointsList.size()];
			yCoordinates = new double[pointsList.size()];
		
			for (int i = 0 ; i < pointsList.size(); i++){
				point = pointsList.get(i);
				xCoordinates [i] = point.getX();
				//System.out.println(xCoordinates[i]);
				yCoordinates [i] = point.getY();
				//System.out.println(yCoordinates[i]);
			}
			graphicsContext.strokePolygon(xCoordinates, yCoordinates, pointsList.size());
			graphicsContext.fillPolygon(xCoordinates, yCoordinates, pointsList.size());
		}
		graphicsBox.getChildren().add(canvas);
		if (startTime == null) {
        	this.startTime = 0;
        	new Thread(startTimerThread).start();
	    }
	    else{ 
	        new Thread(startTimerThread).start();
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
		 

	 private void doBranch() {
		 graphicsBox.setOnMouseClicked(new EventHandler<MouseEvent> ()
		{
			public void handle(MouseEvent e) {
				parent.newSlide(branchID, true, null);
			}
		});
	 }
	 
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
