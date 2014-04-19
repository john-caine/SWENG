package videohandler;

import java.io.File;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	public VideoMediaControl mediaControl;
	
	public VideoPlayerHandler( String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
		
        // create media player
        Media media = new Media(pathLocation);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        mediaControl = new VideoMediaControl(mediaPlayer, width, height, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.box, xStart, yStart);
	}
	
	public String retrieveVideo(String videoLocationPath) {	
		File file = new File(videoLocationPath);
		String path  = file.toURI().toASCIIString();
		return path;
    }
	
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}
