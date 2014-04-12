import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 
 
public class App extends Application {
 
    public static void main(String[] args) {
        launch(args);
    }
 
    @Override
    public void start(Stage primaryStage) throws Exception {
         
        Group root = new Group();
        Scene scene = new Scene(root, 600, 400);
        
        Text  text  =  new  Text();
        text.setX(100);
        text.setY(200);
        text.setFont(new Font(25));
        text.setText("Welcome JavaFX!");
        root.getChildren().add(text);
   
        Button button2 = new Button("Accept");
        HBox hbox1 = new HBox();
        hbox1.getChildren().add(button2);
        hbox1.setAlignment(Pos.BOTTOM_CENTER);
        hbox1.setLayoutX(300);
        hbox1.setLayoutY(300);
        root.getChildren().add(hbox1);
        
         
        primaryStage.setScene(scene);
        primaryStage.setTitle("Hello World!");
        primaryStage.show();
    }
 
}