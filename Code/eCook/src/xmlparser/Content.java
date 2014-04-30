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
 * Version History: v1.1 (01/04/14) - Changed int fields to Integers.
 * 									- Added validation to getting and setting lists.
 *  								- Added method to report errors (Console print for now but will extend in future).
 *  				v1.2 (10/04/14) - Changed type of xStart and yStart from Integer to int as these fields are compulsory.
 *  								- Added validation to setXStart and setYStart to test for null objects.
 *  								- Added validation to URLName to ensure that the object is not null/empty before setting.
 */

import java.util.ArrayList;
import java.util.List;

public class Content {
	int xStart, yStart;
	Integer layer, startTime, duration, width, height, branch, orientation;
	String urlName;
	Boolean loop;
	List<TextBody> texts;
	List<Shape> shapes;
	List<Audio> audios;
	List<Image> images;
	List<Video> videos;
	
	public Content() {
		texts = new ArrayList<TextBody>();
		shapes = new ArrayList<Shape>();
		audios = new ArrayList<Audio>();
		images = new ArrayList<Image>();
		videos = new ArrayList<Video>();
	}
	
	// method to report errors when setting fields
	public void reportError(String errorMessage) {
		System.out.println(errorMessage);
	}

	// getters
	public int getXStart() {
		return xStart;
	}

	public int getYStart() {
		return yStart;
	}

	public Integer getLayer() {
		return layer;
	}
	
	public Integer getStartTime() {
		return startTime;
	}
	
	public Integer getDuration() {
		return duration;
	}
	
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
	
	public Integer getOrintation() {
		return orientation;
	}
	
	// setters
	public void setXStart(Object xStart) {
		if (xStart != null) {
			this.xStart = Integer.valueOf((String) xStart);
		}
		else {
			reportError("xStart must be specified for this media");
		}
	}
	
	public void setYStart(Object yStart) {
		if (yStart != null) {
			this.yStart = Integer.valueOf((String) yStart);
		}
		else {
			reportError("yStart must be specified for this media");
		}
	}

	public void setLayer(Object layer) {
		this.layer = Integer.valueOf((String) layer);		
	}
	
	public void setStartTime(Object startTime) {
		this.startTime = Integer.valueOf((String) startTime);	
	}
	
	public void setDuration(Object duration) {
		this.duration = Integer.valueOf((String) duration);	
	}
	
	public void setWidth(Object width) {
		this.width = Integer.valueOf((String) width);
	}
	
	public void setHeight(Object height) {
		this.height = Integer.valueOf((String) height);
	}
	
	public void setURLName(Object urlName) {
		if (urlName != null && urlName != "") {
			this.urlName = (String) urlName;
		}
		else {
			reportError("URL name must be specified for this media (and must not be empty)");
		}
	}
	
	public void setLoop(Object loop) {
		this.loop = Boolean.valueOf((String) loop);
	}
	
	public void setBranch(Object branch) {
		this.branch = Integer.valueOf((String) branch);
	}
	
	public void setOrientation(Object orientation) {
		int or = Integer.valueOf((String) orientation);
		if (or >= 0 && or <= 360) {
			this.orientation = or;
		}
		else {
			System.out.println("Orientation must be between 0 and 360");
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

	public TextBody getText(int textNumber) {
		if (textNumber >= 0 && textNumber < this.getNumberOfTexts()) {
			return texts.get(textNumber);
		}
		else {
			reportError("Error getting text: index out of range");
			return null;
		}
	}

	public void addText(TextBody text) {
		if (text != null) {
			texts.add(text);
		}
		else {
			reportError("Error adding text: object received from parser is null");
		}
	}

	public int getNumberOfTexts() {
		return texts.size();
	}
	
	// shape
	public List<Shape> getShapes() {
		return shapes;
	}

	public Shape getShape(int shapeNumber) {
		if (shapeNumber >= 0 && shapeNumber < this.getNumberOfShapes()) {
			return shapes.get(shapeNumber);
		}
		else {
			reportError("Error getting shape: index out of range");
			return null;
		}
	}

	public void addShape(Shape shape) {
		if (shape != null) {
			shapes.add(shape);
		}
		else {
			reportError("Error adding shape: object received from parser is null");
		}
	}

	public int getNumberOfShapes() {
		return shapes.size();
	}
	
	// audio
	public List<Audio> getAudios() {
		return audios;
	}

	public Audio getAudio(int audioNumber) {
		if (audioNumber >= 0 && audioNumber < this.getNumberOfAudios()) {
			return audios.get(audioNumber);
		}
		else {
			reportError("Error getting audio: index out of range");
			return null;
		}
	}

	public void addAudio(Audio audio) {
		if (audio != null) {
			audios.add(audio);
		}
		else {
			reportError("Error adding audio: object received from parser is null");
		}
	}

	public int getNumberOfAudios() {
		return audios.size();
	}
	
	// image
	public List<Image> getImages() {
		return images;
	}

	public Image getImage(int imageNumber) {
		if (imageNumber >= 0 && imageNumber < this.getNumberOfImages()) {
			return images.get(imageNumber);
		}
		else {
			reportError("Error getting image: index out of range");
			return null;
		}
	}

	public void addImage(Image image) {
		if (image != null) {
			images.add(image);
		}
		else {
			reportError("Error adding image: object received from parser is null");
		}
	}

	public int getNumberOfImages() {
		return images.size();
	}
	
	// video
	public List<Video> getVideos() {
		return videos;
	}

	public Video getVideo(int videoNumber) {
		if (videoNumber >= 0 && videoNumber < this.getNumberOfVideos()) {
			return videos.get(videoNumber);
		}
		else {
			reportError("Error getting video: index out of range");
			return null;
		}
	}

	public void addVideo(Video video) {
		if (video != null) {
			videos.add(video);
		}
		else {
			reportError("Error adding video: object received from parser is null");
		}
	}

	public int getNumberOfVideos() {
		return videos.size();
	}
}