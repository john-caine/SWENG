package player;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoPlayerHandler {
	
	MediaControl mediaControl;
	String path;
	
	public VideoPlayerHandler(String pathLocation ,  int xStart, int yStart){
	 	File file = new File(pathLocation);
        path = file.toURI().toASCIIString(); 
       // System.out.println("Path Name = " + path);
        // create media player
        Media media = new Media(path);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        
        mediaControl = new MediaControl(mediaPlayer);
        mediaControl.box.setLayoutX(xStart);
        mediaControl.box.setLayoutY(yStart);
	}
}
