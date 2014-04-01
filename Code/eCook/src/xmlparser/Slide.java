package xmlparser;
/* Title: Slide
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain all information associated with a slide.
 * 				Methods are provided for 'setting' and 'getting' fields for this class.
 * 
 * Version History: v1.01 (27/03/14) - Changed type of ID from String to int. Updated setter and getter.
 *  
 */

public class Slide {
	int ID, duration;
	Boolean lastSlide;
	private Content content;
		
	public Slide() {
	}

	// getters
	public int getID() {
		return ID;
	}

	public int getDuration() {
		return duration;
	}

	public Boolean getLastSlide() {
		return lastSlide;
	}
	
	// setters
	public void setID(Object ID) {
		this.ID = Integer.valueOf((String) ID);
	}
	
	public void setDuration(Object duration) {
		this.duration = Integer.valueOf((String) duration);		
	}

	public void setLastSlide(Object lastSlide) {
		this.lastSlide = Boolean.valueOf((String) lastSlide);		
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}
}
