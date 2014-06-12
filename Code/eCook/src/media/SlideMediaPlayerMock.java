/*
 * Programmer: Steve Thorpe, Jonathan Caine, Roger Tan
 * Date Created: 04/06/2014
 * Description: SlideMediaPlayerMock class, to be used for testing purposes only NOT TO BE INSTANTIATED WITHIN SOURCE CODE
 */
package media;

import eCook.SlideShow;

public class SlideMediaPlayerMock extends SlideMediaPlayer {
	

	/**
	 * SlideMediaPlayerMock Constructor
	 * 
	 * @param parent: Slideshow which called the constructor.
	 * @param pathLocation: The media file path.
	 * @param xStart: The x co ordinate of the top left corner of the media player.
	 * @param yStart: The y co ordinate of the top right corner of the media player.
	 * @param startTime: The time in seconds the media is to start.
	 * @param duration: The time in seconds the media is to stop.
	 * @param loop: When true the media is set to repeat indefinitely.
	 */
	public SlideMediaPlayerMock(SlideShow parent, String pathLocation,  Integer xStart, Integer yStart, Integer startTime, Integer duration, Boolean loop) {
		super(parent, pathLocation,  xStart, yStart, startTime, duration, loop);

	}
}