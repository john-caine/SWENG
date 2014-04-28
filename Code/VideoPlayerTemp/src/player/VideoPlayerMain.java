package player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class VideoPlayerMain extends Application {
	
	Button button;
	
    public static void main(String[] args) {
        launch(args);
    }
     
    @Override
    public void start(final Stage primaryStage) {
    	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setTitle("Video Player");
        Group root = new Group();
        
        Scene scene = new Scene(root, bounds.getWidth(), bounds.getHeight());
        scene.setFill(Color.BLACK);
        
        button = new Button("Next");
        button.setLayoutX(600);
        button.setLayoutY(200);
        
        VideoPlayerHandler videoPlayerHandler = new VideoPlayerHandler("http://download.oracle.com/otndocs/products/javafx/oow2010-2.flv", 300, 300, 400, 400, true, 0, 5);
        root.getChildren().add(videoPlayerHandler.mediaControl.box);
//        root.getChildren().add(button);
        
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show(); 
        
       /* button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
            	Group root = new Group();
            	Scene newscene = new Scene(root, bounds.getWidth(), bounds.getHeight());
                newscene.setFill(Color.BLACK);
                
                Node  source = (Node)  event.getSource();
            	Group root1  = (Group) source.getScene().getRoot();
            	root1.getChildren().getClass();       	
            	
            }
        });*/
    }
    
    
 
}
