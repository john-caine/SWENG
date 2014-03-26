package player;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	MediaControl mediaControl;
	
	public VideoPlayerHandler(String pathLocation ,  int xStart, int yStart){
	 	File file = new File(pathLocation);
        String path = file.toURI().toASCIIString();
 
        // create media player
        Media media = new Media(path);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        
        mediaControl = new MediaControl(mediaPlayer, xStart, yStart);
        mediaControl.box.setLayoutX(xStart);
        mediaControl.box.setLayoutY(yStart);
	}
}
