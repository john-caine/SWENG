package player;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	public MediaControl mediaControl;
	
	public VideoPlayerHandler(String pathLocation, int xStart, int yStart, int width, int height, Boolean loop, Integer startTime, Integer duration){
        
        // create media player
        Media media = new Media(pathLocation);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        mediaControl = new MediaControl(mediaPlayer,width, height, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.box, xStart, yStart);
	}
	
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}