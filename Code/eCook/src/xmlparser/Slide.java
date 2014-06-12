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
 *  
 */

public class Slide {
	// declare variables
	Integer id;
	Integer duration = 0;
	Boolean lastSlide = false;
	Content content;

	// getters
	/**
	 * Get the Slide ID
	 * @return id: The slideID
	 */
	public Integer getID() {
		return id;
	}

	/**
	 * Get the slide duration in seconds
	 * @return: The slide duration
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * Get if the slide is the last slide in the SlideShow
	 * @return lastSlide: True if the slide is the last slide
	 */
	public Boolean getLastSlide() {
		return lastSlide;
	}
	
	// setters
	/**
	 * Set the slide ID
	 * @param id: The slide ID
	 */
	public void setID(Object id) {
		if (id != null) {
			this.id = Integer.valueOf((String) id);
		}
	}
	
	/**
	 * Set the slide duration in seconds
	 * @param duration: The slide duration
	 */
	public void setDuration(Object duration) {
		if (duration != null) {
			this.duration = Integer.valueOf((String) duration);
		}
	}

	/**
	 * Set if the slide is the last slide in the SlideShow
	 * @param lastSlide: Set true if the slide is the last slide
	 */
	public void setLastSlide(Object lastSlide) {
		if (lastSlide != null) {
			this.lastSlide = Boolean.valueOf((String) lastSlide);
		}
	}

	/**
	 * Get the slide content
	 * @return content: The slide content object, contains information about all media types 
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * Set the slide content
	 * @param content: Slide content parsed from XML
	 */
	public void setContent(Content content) {
		if (content != null) {
			this.content = content;
		}
	}
}
