package player;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	MediaControl mediaControl;
	MediaPlayer mediaPlayer;
	Media media;
	
	public VideoPlayerHandler(String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
        
        // Create a MediaPlayer which plays the URL provided
        media = new Media(pathLocation);
        mediaPlayer = new MediaPlayer(media);
        
        // Pass the mediaPlayer into the MediaControl class to have it's interface setup with the appropriate conditions 
        mediaControl = new MediaControl(mediaPlayer, width, height, loop, startTime, duration);
        
        // Set the Location of the MediaPlayer
        setMediaPlayerLocation(mediaControl.overallBox, xStart, yStart);
	}
	
	// Function that sets the Location of the MediaPlayer
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}