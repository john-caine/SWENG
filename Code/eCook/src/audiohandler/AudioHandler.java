
/*Programmer: P.Mathema, S.Beedell,
 * Date Created: 14/03/2014
 * Description: 

*/


package audiohandler;

import java.io.File;
import java.util.concurrent.TimeUnit;

import videohandler.VideoMediaControl;
import eCook.SlideShow;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

public class AudioHandler {
	
	public AudioMediaControl mediaControl;
	private SlideShow parent;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	public AudioHandler(SlideShow parent, String pathLocation, Integer startTime, Integer duration, Boolean loop){
		
		this.parent = parent;
		
        // create media player
        media = new Media(pathLocation);
         mediaPlayer = new MediaPlayer(media);
        
        mediaControl = new AudioMediaControl(mediaPlayer, null /*height*/, null /*width*/, loop, startTime, duration);
        setMediaPlayerLocation(mediaControl.overallBox, 100, 100);
        
//        if(parent.newSlide(parent.nextSlideID, false)) {
//        	
//        }

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
	


//	public AudioClip audio;
//	private Integer duration;
//	private Integer startTime;
//	private SlideShow parent;
//
//	/**
//	 * Creates an Audio handler with the specified urlname specified urlname, time and
//	 * Loop
//	 * 
//	 * @param urlname
//	 *            - Location of the audio file
//	 * @param startTime
//	 *            - Time in seconds until audio starts
//	 * @param Loop
//	 *            - Time in seconds that the audio will play for
//	 * @param volume
//	 *            - Volume of audio 0.0 - 1.0
//	 * 
//	 */
//	public AudioHandler(SlideShow parent, String urlname, Integer startTime, boolean loop, Integer duration) {
//        this.duration = duration;
//		this.startTime = startTime;
//		this.parent = parent;
//		
//		loadAudio(urlname);
//		
//		setLoop(loop);
//		
//		audio.setVolume(1);
//		
//		if (startTime == null) {
//        	this.startTime = 0;
//        	new Thread(startTimerThread).start();
//        }
//        else 
//        	new Thread(startTimerThread).start();
//	}
//
//	private void loadAudio(String urlname) {
//		try {
//			audio = new AudioClip(urlname);
//		} catch (IllegalArgumentException i) {
//			System.out.println("Audio File not found at " + urlname);
//		} catch (MediaException m) {
//			System.out.println("Audio File " + urlname + " was of an unexpected format");
//		}
//	}
//	
//	// Thread waits until count = startTime then sets the visibility of the image to true.
//		 Task<Object> startTimerThread = new Task<Object>() {
//		
//				@Override
//				protected Object call() throws Exception {
//					 int count=0;
//					 while (count <= startTime && startTime != 0) {
//						try {
//							TimeUnit.SECONDS.sleep(1);
//						} catch (InterruptedException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						count++;
//				 	}
//				 
//					Platform.runLater (new Runnable() {
//						public void run(){
//							playAudio();
//						}
//					});	 
//				 
//					if (duration != null && duration != 0)
//						new Thread(durationTimerThread).start();
//				 
//					return null;
//				}
//		 };
//
//		 // Thread waits until duration and then sets the image visibility to false once duration = count.
//		 Task<Object> durationTimerThread = new Task<Object>() {
//			
//			 @Override
//			protected Object call() throws Exception {
//				int count=0;
//				while (count <= duration) {
//					try {
//						TimeUnit.SECONDS.sleep(1);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					count++;
//				}
//					 
//				Platform.runLater( new Runnable(){
//					public void run(){
//						stopAudio();
//					}
//				});	 
//				
//				return null;
//			}
//		};
//
	
//	
	
//
//	/**
//	 * When set the audio will play continuously until the Duration is over or
//	 * it is stopped.
//	 * 
//	 * @param loop
//	 */
//	public void setLoop(boolean loop) {
//		if (loop)
//			audio.setCycleCount(AudioClip.INDEFINITE);
//		else
//			audio.setCycleCount(1);
//	}
//
//}
