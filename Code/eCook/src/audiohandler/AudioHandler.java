
/*Programmer: P.Mathema, S.Beedell,
 * Date Created: 14/03/2014
 * Description: 

*/


package audiohandler;

import eCook.SlideShow;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AudioHandler {
	
	public AudioMediaControl mediaControl;
	protected Media media;
	protected MediaPlayer mediaPlayer;
	
	public AudioHandler(SlideShow parent, String pathLocation, Integer startTime, Integer duration, Boolean loop){
		
        // create media player
        media = new Media(pathLocation);
        mediaPlayer = new MediaPlayer(media);
        
        mediaControl = new AudioMediaControl(mediaPlayer, null /*height*/, null /*width*/, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.overallBox, 100, 100);
	}
	
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
	
	/**
	 * Stops any audio that is playing
	 */
	public void stopAudio() {
		mediaPlayer.stop();
	}
	// Plays any audio selected
	public void playAudio() {
		mediaPlayer.play();
	}
}