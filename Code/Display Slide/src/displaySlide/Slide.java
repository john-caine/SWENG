package displaySlide;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Slide {
	public Stage stage;
	public Button exitSlide;
	
	public Slide() {
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		
		BorderPane border = new BorderPane();
		Scene scene = new Scene(border);
		stage = new Stage();
        
        exitSlide = new Button("Exit Slide");
        HBox hbox = new HBox();
        hbox.getChildren().add(exitSlide);
        hbox.setAlignment(Pos.CENTER);
        
        border.setBottom(hbox);
		
		stage.setTitle("Slide");
		stage.setScene(scene);
		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
        stage.setFullScreen(true);
        stage.show();
        
        /*Exit Slide when exit slide button is pressed*/
        exitSlide.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	stage.close();
            	event.consume();
            }
        });
        
    	/*Exit Slide when ESC key is pressed*/
		stage.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			    @Override
			    public void handle(KeyEvent event){
				    if(event.getCode() == KeyCode.ESCAPE){
					    stage.close();
					    event.consume();    
				    }	                                 
			    }
		});
	}
}
