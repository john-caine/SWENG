/*
 * Programmer: Roger Tan & Zayyad Tagwai
 * 
 * Date Created: 01/03/2014
 * 
 * Description: Handler class for Videos. Creates a MediaPlayer object to play the content,
 * 				and overlays a MediaControl bar to interface with the MediaPlayer object.
 */

package player;

import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class VideoPlayerHandler {
	
	protected MediaControl mediaControl;
	private MediaPlayer mediaPlayer;
	protected Media media;
	
	/* 
	 * Constructor for VideoHandler. Accepts both required and optional parameters from PWS.
	 * Creates a MediaPlayer object and MediaControl object, before setting the location 
	 * and adding both to the scene.
	 * 
	 * @param pathLocation 
	 * @param xStart
	 * @param yStart
	 * @param width
	 * @param height
	 * @param loop
	 * @param startTime
	 * @param duration
	 */
	public VideoPlayerHandler(String pathLocation, int xStart, int yStart, Integer width, Integer height, Boolean loop, Integer startTime, Integer duration){
        
        // Create a MediaPlayer which plays the URL provided
        media = new Media(pathLocation);
        mediaPlayer = new MediaPlayer(media);
        
        // Pass the mediaPlayer into the MediaControl class to have it's interface setup with the appropriate conditions 
        mediaControl = new MediaControl(mediaPlayer, width, height, loop, startTime, duration);
        
        // Set the Location of the MediaPlayer
        setMediaPlayerLocation(mediaControl.overallBox, xStart, yStart);
	}
	
	/* 
	 * Set the location of the VBox containing the MediaPlayer and MediaControl objects
	 * 
	 * @param vbox
	 * @param xLocation
	 * @param yLocation
	 */
	private void setMediaPlayerLocation(VBox vbox, int xLocation, int yLocation){
		vbox.setLayoutX(xLocation);
		vbox.setLayoutY(yLocation);
	 }
}