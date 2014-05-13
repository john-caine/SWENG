package videohandler;

import javafx.scene.media.MediaPlayer;

public class VideoMediaControl extends eCook.MediaControl{

	public VideoMediaControl(MediaPlayer mp, Integer width, Integer height,
			Boolean loop, Integer startTime, Integer playDuration) {
		super(mp, width, height, loop, startTime, playDuration);
	}

}