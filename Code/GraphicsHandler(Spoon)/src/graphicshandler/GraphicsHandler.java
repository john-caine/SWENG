package graphicshandler;

import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class GraphicsHandler {
	
	public HBox	graphicsBox;
	private Canvas canvas;
	private GraphicsContext graphicsContext;
	private Integer duration;
	private Integer startTime;
	protected Integer branchID;

	public GraphicsHandler(int totalPoints, int width, int height, String fillColour, Integer startTime, Integer duration,
						   Integer layer, String lineColour, Integer branch){
		
		this.duration = duration; 
		this.startTime = startTime;
		this.branchID = branch;
		
		graphicsBox = new HBox();
		graphicsBox.setVisible(false);
		canvas = new Canvas(300, 250);
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
		
		//Draw the appropriate shapes
		//Circle
		if(totalPoints == 1){
			graphicsContext.fillOval(50, 50, width, height);
			graphicsContext.strokeOval(50, 50, width, height);
		}
		//Line
		else if(totalPoints == 2){
			graphicsContext.setLineWidth(5);
			graphicsContext.strokeLine(50, 50, 100, 100);
		}
		//Triangle
		else if(totalPoints == 3){
			graphicsContext.strokePolygon(new double[]{50, 75, 25},
	                   new double[]{20, 60, 60}, 3);
			graphicsContext.fillPolygon(new double[]{50, 75, 25},
	                   new double[]{20, 60, 60}, 3);
		}
		//Rectangle
		else if(totalPoints == 4){
			graphicsContext.strokePolygon(new double[]{50, 50 + width, 50 + width, 50},
	                   new double[]{50, 50, 50 + height, 50 + height }, 4);
			graphicsContext.fillPolygon(new double[]{50, 50 + width, 50 + width, 50},
	                   new double[]{50, 50, 50 + height, 50 + height }, 4);
		}
		//Pentagon
		else if(totalPoints == 5){
			graphicsContext.strokePolygon(new double[]{50, 50 + width/2, 50 + width/4, 50 - width/4, 50 - width/2},
	                   new double[]{50, 50 + height/2, 50 + height, 50 + height, 50 + height/2}, 5);
			graphicsContext.fillPolygon(new double[]{50, 50 + width/2, 50 + width/4, 50 - width/4, 50 - width/2},
	                   new double[]{50, 50 + height/2, 50 + height, 50 + height, 50 + height/2}, 5);
			
		}
		//Hexagon
		else if(totalPoints == 6){
			graphicsContext.strokePolygon(new double[]{50, 50 + width/2, 50 + 3* width/4, 50 + width/2, 50, 50 - width/4 },
	                   new double[]{50, 50, 50 + height/2, 50 + height, 50 + height, 50 + height/2}, 6);
			graphicsContext.fillPolygon(new double[]{50, 50 + width/2, 50 + 3* width/4, 50 + width/2, 50, 50 - width/4 },
	                   new double[]{50, 50, 50 + height/2, 50 + height, 50 + height, 50 + height/2}, 6);
			
		}
		graphicsBox.getChildren().add(canvas);
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
}
