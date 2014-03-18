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
 */

public class Slide {
	String ID;
	int duration;
	Boolean lastSlide;
	Content content;
		
	public Slide() {
	}

	// getters
	public String getID() {
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
		this.ID = (String) ID;
	}
	
	public void setDuration(Object duration) {
		this.duration = Integer.valueOf((String) duration);		
	}

	public void setLastSlide(Object lastSlide) {
		this.lastSlide = Boolean.valueOf((String) lastSlide);		
	}
}
