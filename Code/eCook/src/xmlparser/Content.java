package xmlparser;
/* Title: Content
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain common information and methods associated with all slide media.
 * 				Methods are provided for 'setting' and 'getting' common fields.
 * 				Contains lists for holding Text, Shape, Audio, Image and Video objects.
 * 
 */

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import eCook.eCook;

public class Content {

	Integer width = null, height = null, xStart = null, yStart = null;
	Integer layer, startTime, duration, branch, orientation = 0;
	String urlName = null;
	Boolean loop = false;
	List<TextBody> texts;
	List<Shape> shapes;
	List<Audio> audios;
	List<Image> images;
	List<Video> videos;
	private Logger logger;
	
	// constructor sets up arrays for all media types
	public Content() {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		texts = new ArrayList<TextBody>();
		shapes = new ArrayList<Shape>();
		audios = new ArrayList<Audio>();
		images = new ArrayList<Image>();
		videos = new ArrayList<Video>();
	}

	// getters
	/**
	 * Get the xStart value
	 * @return xStart: Starting x position of an object
	 */
	public Integer getXStart() {
		return xStart;
	}

	/**
	 * Get the yStart value
	 * @return yStart: Start y position of an object
	 */
	public Integer getYStart() {
		return yStart;
	}

	
	/**
	 * Get the slide layer the object is to be added to
	 * @return layer: The objects slide layer
	 */
	public Integer getLayer() {
		return layer;
	}
	
	/**
	 * Get the startTime
	 * @return startTime: The time in seconds the object is to appear on the slide
	 */
	public Integer getStartTime() {
		return startTime;
	}
	
	/**
	 * Get the duration of the object
	 * @return duration: The time in seconds the object is to be removed from the slide
	 */
	public Integer getDuration() {
		return duration;
	}
	
	
	/**
	 * Get the width of 
	 * @return
	 */
	public Integer getWidth() {
		return width;
	}
	
	public Integer getHeight() {
		return height;
	}
	
	public String getUrlName() {
		return urlName;
	}
	
	public Boolean getLoop() {
		return loop;
	}
	
	public Integer getBranch() {
		return branch;
	}
	
	public Integer getOrientation() {
		return orientation;
	}
	
	// setters
	public void setXStart(Object xStart) {
		if (xStart != null) {
			this.xStart = Integer.valueOf((String) xStart);
		}
	}
	
	public void setYStart(Object yStart) {
		if (yStart != null) {
			this.yStart = Integer.valueOf((String) yStart);
		}
	}

	public void setLayer(Object layer) {
		if (layer != null) {
			this.layer = Integer.valueOf((String) layer);
		}
	}
	
	public void setStartTime(Object startTime) {
		if (startTime != null) {
			this.startTime = Integer.valueOf((String) startTime);	
		}
	}
	
	public void setDuration(Object duration) {
		if (duration != null) {
			this.duration = Integer.valueOf((String) duration);
		}
	}
	
	public void setWidth(Object width) {
		if (width != null) {
			this.width = Integer.valueOf((String) width);
		}
	}
	
	public void setHeight(Object height) {
		if (height != null) {
			this.height = Integer.valueOf((String) height);
		}
	}
	
	public void setURLName(Object urlName) {
		if (urlName != null && urlName != "") {
			this.urlName = (String) urlName;
		}
	}
	
	public void setLoop(Object loop) {
		if (loop != null) {
			this.loop = Boolean.valueOf((String) loop);
		}
	}
	
	public void setBranch(Object branch) {
		if (branch != null) {
			this.branch = Integer.valueOf((String) branch);
		}
	}
	
	public void setOrientation(Object orientation) {
		if (orientation != null) {
			this.orientation = Integer.valueOf((String) orientation);
			if (this.orientation < 0 || this.orientation > 360) {
				this.orientation = 180;
			}
		}
	}
	
	/* LIST OPERATIONS
	 * methods for getting and setting list elements,
	 * getting lists and getting lists lengths
	 */
	// text
	public List<TextBody> getTexts() {
		return texts;
	}

	public TextBody getText(Integer textNumber) {
		if (textNumber >= 0 && textNumber < this.getNumberOfTexts()) {
			return texts.get(textNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting text: index out of range");
			return null;
		}
	}

	public void addText(TextBody text) {
		if (text != null) {
			texts.add(text);
		}
	}

	public Integer getNumberOfTexts() {
		return texts.size();
	}
	
	// shape
	public List<Shape> getShapes() {
		return shapes;
	}

	public Shape getShape(Integer shapeNumber) {
		if (shapeNumber >= 0 && shapeNumber < this.getNumberOfShapes()) {
			return shapes.get(shapeNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting shape: index out of range");
			return null;
		}
	}

	public void addShape(Shape shape) {
		if (shape != null) {
			shapes.add(shape);
		}
	}

	public Integer getNumberOfShapes() {
		return shapes.size();
	}
	
	// audio
	public List<Audio> getAudios() {
		return audios;
	}

	public Audio getAudio(Integer audioNumber) {
		if (audioNumber >= 0 && audioNumber < this.getNumberOfAudios()) {
			return audios.get(audioNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting audio: index out of range");
			return null;
		}
	}

	public void addAudio(Audio audio) {
		if (audio != null) {
			audios.add(audio);
		}
	}

	public Integer getNumberOfAudios() {
		return audios.size();
	}
	
	// image
	public List<Image> getImages() {
		return images;
	}

	public Image getImage(Integer imageNumber) {
		if (imageNumber >= 0 && imageNumber < this.getNumberOfImages()) {
			return images.get(imageNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting image: index out of range");
			return null;
		}
	}

	public void addImage(Image image) {
		if (image != null) {
			images.add(image);
		}
	}

	public Integer getNumberOfImages() {
		return images.size();
	}
	
	// video
	public List<Video> getVideos() {
		return videos;
	}

	public Video getVideo(Integer videoNumber) {
		if (videoNumber >= 0 && videoNumber < this.getNumberOfVideos()) {
			return videos.get(videoNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting video: index out of range");
			return null;
		}
	}

	public void addVideo(Video video) {
		if (video != null) {
			videos.add(video);
		}
	}

	public Integer getNumberOfVideos() {
		return videos.size();
	}
}