package audiohandler;

import java.util.concurrent.TimeUnit;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaException;

public class AudioHandler {

	/**
	 * Audio Handler
	 * 
	 * @author Theo Hussey - TrueT
	 */

	private AudioClip audio;
	private Integer duration;
	private Integer startDelay;

	private boolean stopped = true;
	private boolean repeat = false;

	/**
	 * Creates an Audio handler with the specified path and delay.
	 * 
	 * @param path
	 *            - Location of the audio file
	 * @param startDelay
	 *            - Time in seconds until audio starts
	 * @param volume
	 *            - Volume of audio 0.0 - 1.0
	 */
	public AudioHandler(String path, int startDelay, double volume) {
		this(path, startDelay, 0, volume);
	}

	/**
	 * Creates an Audio handler with the specified path specified path, time and
	 * duration
	 * 
	 * @param path
	 *            - Location of the audio file
	 * @param startDelay
	 *            - Time in seconds until audio starts
	 * @param duration
	 *            - Time in seconds that the audio will play for
	 * @param volume
	 *            - Volume of audio 0.0 - 1.0
	 * 
	 */
	public AudioHandler(String path, int startDelay, int duration, double volume) {
		loadAudio(path);
		audio.setVolume(volume);

		this.startDelay = startDelay;
		this.duration = duration;
	}

	private void loadAudio(String path) {
		try {
			audio = new AudioClip(path);
		} catch (IllegalArgumentException i) {
			System.out.println("Audio File not found at " + path);
		} catch (MediaException m) {
			System.out.println("Audio File " + path + " was of an unexpected format");
		}
	}

	/**
	 * Audio will begin to play after specified delay has elapsed.
	 */
	public void begin() {
		audio.stop();
		startTimerThread.start();
		stopped = false;
	}

	private Thread startTimerThread = new Thread("startTimer") {
		public void run() {
			int timer = startDelay;
			while (timer > 0) {
				if (stopped)
					return;

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// Restore the interrupted status
					Thread.currentThread().interrupt();
				}
				timer--;
			}

			audio.play();

			if (duration > 0)
				durationTimerThread.start();
		}
	};

	private Thread durationTimerThread = new Thread("durationTimer") {
		public void run() {
			int timer = duration;
			while (timer > 0) {
				if (!audio.isPlaying() || stopped)
					return;

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// Restore the interrupted status
					Thread.currentThread().interrupt();
				}
				timer--;
			}
			audio.stop();
		}
	};

	/**
	 * Stops any audio that is playing
	 */
	public void stop() {
		audio.stop();
		stopped = true;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Integer getStartDelay() {
		return startDelay;
	}

	public void setStartDelay(Integer startDelay) {
		this.startDelay = startDelay;
	}

	public boolean isStopped() {
		return stopped;
	}

	public boolean isRepeat() {
		return repeat;
	}

	/**
	 * When set the audio will play continuously until the duration is over or
	 * it is stopped.
	 * 
	 * @param repeat
	 */
	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
		if (repeat)
			audio.setCycleCount(AudioClip.INDEFINITE);
		else
			audio.setCycleCount(1);
	}

	/**
	 * Set the volume of the AudioHandler The new setting will only take effect
	 * on subsequent plays.
	 * 
	 * @param volume
	 *            0.0 - 1.0
	 */
	public void setVolume(double volume) {
		audio.setVolume(volume);
	}

	/**
	 * Returns the current volume of the AudioHandler.
	 * 
	 * 
	 * @return volume 0.0 - 1.0
	 */
	public double getVolume() {
		return audio.getVolume();
	}
	
	public boolean isPlaying(){
		return audio.isPlaying();
	}

}
