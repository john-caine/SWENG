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
 * Version History: v1.1 (01/04/14) - Changed Integer fields to Integers.
 * 									- Added validation to getting and setting lists.
 *  								- Added method to report errors (Console prInteger for now but will extend in future).
 *  				v1.2 (10/04/14) - Changed type of xStart and yStart from Integer to Integer as these fields are compulsory.
 *  								- Added validation to setXStart and setYStart to test for null objects.
 *  								- Added validation to URLName to ensure that the object is not null/empty before setting.
 */

import java.util.ArrayList;
import java.util.List;

public class Content {
	Integer width, height, xStart, yStart = null;
	Integer layer, startTime, duration, branch, orientation = 0;
	String urlName = null;
	Boolean loop = false;
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
	public Integer getXStart() {
		return xStart;
	}

	public Integer getYStart() {
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
			reportError("Error getting text: index out of range");
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
			reportError("Error getting shape: index out of range");
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
			reportError("Error getting audio: index out of range");
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
		System.out.println(getNumberOfImages());
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
			reportError("Error getting video: index out of range");
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