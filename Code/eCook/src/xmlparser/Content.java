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
	
	/**
	 * constructor sets up arrays for all media types
	 */
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
	 * Get the width of a content object
	 * @return width: the content object width
	 */
	public Integer getWidth() {
		return width;
	}
	
	/**
	 * Gets the height  of a content object
	 * @return height:  the content object height
	 */
	public Integer getHeight() {
		return height;
	}
	
	/**
	 * Gets the content object URL
	 * @return urlName: the content URL string
	 */
	public String getUrlName() {
		return urlName;
	}
	
	/**
	 * Gets if the content is set to loop
	 * @return loop: set to true if set to loop
	 */
	public Boolean getLoop() {
		return loop;
	}
	
	/**
	 * Gets the branch slide the content is to branch to
	 * @return branch: the branch slide ID
	 */
	public Integer getBranch() {
		return branch;
	}
	/**
	 * Gets the orientation of the content
	 * @return orientation: the orientation in degrees
	 */
	public Integer getOrientation() {
		return orientation;
	}
	
	// setters
	
	/**
	 * Sets the content xStart value
	 * @param xStart: The xStart integer
	 */
	public void setXStart(Object xStart) {
		if (xStart != null) {
			this.xStart = Integer.valueOf((String) xStart);
		}
	}
	
	/**
	 * Sets the content yStart value
	 * @param yStart
	 */
	public void setYStart(Object yStart) {
		if (yStart != null) {
			this.yStart = Integer.valueOf((String) yStart);
		}
	}

	/**
	 * Sets the layer of the content
	 * @param layer: the layer to set to
	 */
	public void setLayer(Object layer) {
		if (layer != null) {
			this.layer = Integer.valueOf((String) layer);
		}
	}
	
	/**
	 * Sets the time the content is to appear on screen
	 * @param startTime: The time before appearing in seconds
	 */
	public void setStartTime(Object startTime) {
		if (startTime != null) {
			this.startTime = Integer.valueOf((String) startTime);	
		}
	}
	/**
	 * Sets the time the content is to be removed from the screen
	 * @param duration: The before being removed in seconds
	 */
	public void setDuration(Object duration) {
		if (duration != null) {
			this.duration = Integer.valueOf((String) duration);
		}
	}
	/**
	 * Sets the width value of the content 
	 * @param width: The content width
	 */
	public void setWidth(Object width) {
		if (width != null) {
			this.width = Integer.valueOf((String) width);
		}
	}
	
	/**
	 * Sets the height value of the content
	 * @param height: The content height
	 */
	public void setHeight(Object height) {
		if (height != null) {
			this.height = Integer.valueOf((String) height);
		}
	}
	
	/**
	 * Sets the content URL
	 * @param urlName: The content URL name
	 */
	public void setURLName(Object urlName) {
		if (urlName != null && urlName != "") {
			this.urlName = (String) urlName;
		}
	}
	
	/**
	 * Sets if the content is to loop
	 * @param loop: Set true to loop repeatedly
	 */
	public void setLoop(Object loop) {
		if (loop != null) {
			this.loop = Boolean.valueOf((String) loop);
		}
	}
	
	/**
	 * Set the branch slide the content is to branch to
	 * @param branch: The branch slide ID
	 */
	public void setBranch(Object branch) {
		if (branch != null) {
			this.branch = Integer.valueOf((String) branch);
		}
	}
	/**
	 * Set the content orientation
	 * @param orientation: The orientation in degrees
	 */
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
	/**
	 * Get the List of TextBody objects
	 * @return texts: List of TextBody
	 */
	public List<TextBody> getTexts() {
		return texts;
	}

	/**
	 * Get a specific Text object, returns null and logs an error if the index is greater than the size of the TextBody Array
	 * @param textNumber: The TextBody index
	 * @return The TextBody at the textNumber index
	 */
	public TextBody getText(Integer textNumber) {
		if (textNumber >= 0 && textNumber < this.getNumberOfTexts()) {
			return texts.get(textNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting text: index out of range");
			return null;
		}
	}

	/**
	 * Add a TextBody to the TextBody Array
	 * @param text: The TextBody to be added
	 */
	public void addText(TextBody text) {
		if (text != null) {
			texts.add(text);
		}
	}

	/**
	 * Gets the total number of TextBody objects
	 * @return the TextBody Array size
	 */
	public Integer getNumberOfTexts() {
		return texts.size();
	}
	
	// shape
	/**
	 * Get the list of Shape objects
	 * @return shapes: The ArrayList of shapes
	 */
	public List<Shape> getShapes() {
		return shapes;
	}

	/**
	 * Get a shape from the Shape List Index, returns null if shapeNumber is greater than the size of the array
	 * @param shapeNumber: The Shape ArrayList index
	 * @return The shape at the index shapeNumber
	 */
	public Shape getShape(Integer shapeNumber) {
		if (shapeNumber >= 0 && shapeNumber < this.getNumberOfShapes()) {
			return shapes.get(shapeNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting shape: index out of range");
			return null;
		}
	}

	/**
	 * Add a shape to the Shape Array
	 * @param shape: The shape to be added to the array
	 */
	public void addShape(Shape shape) {
		if (shape != null) {
			shapes.add(shape);
		}
	}

	/**
	 * Get the total number of Shape objects
	 * @return The Shape ArrayList size
	 */
	public Integer getNumberOfShapes() {
		return shapes.size();
	}
	
	
	// audio
	/**
	 * Get the List of Audio Objects
	 * @return The Audio Object ArrayList
	 */
	public List<Audio> getAudios() {
		return audios;
	}

	/**
	 * Get an Audio Object from an Audio ArrayList Index
	 * @param audioNumber: The Audio ArrayList Index
	 * @return The Audio object from index audioNumber
	 */
	public Audio getAudio(Integer audioNumber) {
		if (audioNumber >= 0 && audioNumber < this.getNumberOfAudios()) {
			return audios.get(audioNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting audio: index out of range");
			return null;
		}
	}

	/**
	 * Add an Audio Object to the Audio ArrayList
	 * @param audio: The Audio object to be added
	 */
	public void addAudio(Audio audio) {
		if (audio != null) {
			audios.add(audio);
		}
	}

	/**
	 * Get the total number of Audio Objects
	 * @return The size of the Audio ArrayList
	 */
	public Integer getNumberOfAudios() {
		return audios.size();
	}
	
	// image
	/**
	 * Get the ArrayList of Image objects
	 * @return images: The Image ArrayList
	 */
	public List<Image> getImages() {
		return images;
	}

	/**
	 * Get an Image Object from an Image ArrayList index, returns null if the index is greater than the Image ArrayList size
	 * @param imageNumber: The Image ArrayList index
	 * @return The Image object at index imageNumber
	 */
	public Image getImage(Integer imageNumber) {
		if (imageNumber >= 0 && imageNumber < this.getNumberOfImages()) {
			return images.get(imageNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting image: index out of range");
			return null;
		}
	}

	/**
	 * Add an Image object to the Image ArrayList
	 * @param image: The Image object 
	 */
	public void addImage(Image image) {
		if (image != null) {
			images.add(image);
		}
	}

	/**
	 * Get the total number of Image objects
	 * @return: The Image ArrayList size
	 */
	public Integer getNumberOfImages() {
		return images.size();
	}
	
	// video
	/**
	 * Get the Video ArrayList
	 * @return videos: ArrayList of Video Objects
	 */
	public List<Video> getVideos() {
		return videos;
	}

	/**
	 * Get a Video object at a Video ArrayList index, returns null if index is greater than the ArrayList size
	 * @param videoNumber: The Video ArrayList index
	 * @return The Video object at index videoNumber
	 */
	public Video getVideo(Integer videoNumber) {
		if (videoNumber >= 0 && videoNumber < this.getNumberOfVideos()) {
			return videos.get(videoNumber);
		}
		else {
			logger.log(Level.SEVERE, "Error getting video: index out of range");
			return null;
		}
	}

	/**
	 * Add a Video object to the Video ArrayList
	 * @param video: The Video object to be added
	 */
	public void addVideo(Video video) {
		if (video != null) {
			videos.add(video);
		}
	}

	/**
	 * Gets the total number of Video objects
	 * @return The size of the Video Arraylist
	 */
	public Integer getNumberOfVideos() {
		return videos.size();
	}
}