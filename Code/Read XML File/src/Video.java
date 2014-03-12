/* Title: Video
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with slide video.
 * 				Extends common methods and variables from Content class.
 * 				Methods are provided for 'setting' and 'getting' unique fields for this class.
 *  
 */

public class Video extends Content {
	private int playTime;
	
	public Video() {
		super();
	}
	
	// getter
	public int getPlayTime() {
		return playTime;
	}
	
	// setter
	public void setPlayTime(Object playTime) {
		this.playTime = Integer.valueOf((String) playTime);
	}
}
