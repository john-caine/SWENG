package player;

import java.io.File;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	public MediaControl mediaControl;
	
	public VideoPlayerHandler(String pathLocation, int xStart, int yStart, int width, int height, Boolean loop, Integer startTime, Integer duration){
        
        // create media player
        Media media = new Media(retrieveImage(pathLocation));
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        //mediaPlayer.setAutoPlay(true);
        
        mediaControl = new MediaControl(mediaPlayer,width, height, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.box, xStart, yStart);
	}
	
	public String retrieveImage(String videoLocationPath) {	
		File file = new File(videoLocationPath);
		String path  = file.toURI().toASCIIString();
		return path;
    }
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}
