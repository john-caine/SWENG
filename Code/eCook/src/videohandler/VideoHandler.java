/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * A VideoHandler that passes the relevant attributes to the VideoMiedaControl Class to appropriate controls
 * for the videoplayer.
 */

package videohandler;

import java.io.File;

import eCook.SlideShow;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
public class VideoHandler {
	
	public VideoMediaControl mediaControl;
	protected Media media;
	
	public VideoHandler(SlideShow parent, String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
		
        // create media player
        media = new Media(pathLocation);
        final MediaPlayer mediaPlayer = new MediaPlayer(media);
        
        // pass attributes into the VideoMediaControl class to create a mediaplayer based on those attributes
        mediaControl = new VideoMediaControl(mediaPlayer, width, height, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.overallBox, xStart, yStart);
	}
	
	// retrieve the path of the video
	public String retrieveVideo(String videoLocationPath) {	
		File file = new File(videoLocationPath);
		String path  = file.toURI().toASCIIString();
		return path;
    }
	
	// set x and y location of the whole mediaplayer inclusive of the mediacontrol
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}
