/*
 * Programmer: Zayyad Tagwai & Roger Tan
 * Date Created: 01/03/2014
 * Create an instance of MediaControl class
 */

package videohandler;

import javafx.scene.media.MediaPlayer;

public class VideoMediaControl extends eCook.MediaControl{

	public VideoMediaControl(MediaPlayer mp, Integer width, Integer height,
			Boolean loop, Integer startTime, Integer playDuration) {
		super(mp, width, height, loop, startTime, playDuration);
	}

}