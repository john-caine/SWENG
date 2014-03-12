/* Title: Content
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 05/03/14
 * 
 * Description: A class to contain common information and methods associated with all slide media.
 * 				Methods are provided for 'setting' and 'getting' common fields.
 * 				Contains lists for holding Text, Shape, Audio, Image and Video objects.
 */

import java.util.ArrayList;
import java.util.List;

public class Content {
	int xStart, yStart, layer, startTime, duration, width, height;
	String urlName;
	Boolean loop;
	List<Text> texts;
	List<Shape> shapes;
	List<Audio> audios;
	List<Image> images;
	List<Video> videos;
	
	public Content() {
		texts = new ArrayList<Text>();
		shapes = new ArrayList<Shape>();
		audios = new ArrayList<Audio>();
		images = new ArrayList<Image>();
		videos = new ArrayList<Video>();
	}

	// getters
	public int getXStart() {
		return xStart;
	}

	public int getYStart() {
		return yStart;
	}

	public int getLayer() {
		return layer;
	}
	
	public int getStartTime() {
		return startTime;
	}
	
	public int getDuration() {
		return duration;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public String getUrlName() {
		return urlName;
	}
	
	public Boolean getLoop() {
		return loop;
	}
	
	// setters
	public void setXStart(Object xStart) {
		this.xStart = Integer.valueOf((String) xStart);
	}
	
	public void setYStart(Object yStart) {
		this.yStart = Integer.valueOf((String) yStart);		
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
		this.urlName = (String) urlName;
	}
	
	public void setLoop(Object loop) {
		this.loop = Boolean.valueOf((String) loop);
	}
	
	/* LIST OPERATIONS
	 * methods for getting and setting list elements,
	 * getting lists and getting lists lengths
	 */
	// text
	public List<Text> getTexts() {
		return texts;
	}

	public Text getText(int textNumber) {
		return texts.get(textNumber);
	}

	public void addText(Text text) {
		texts.add(text);
	}

	public int getNumberOfTexts() {
		return texts.size();
	}
	
	// shape
	public List<Shape> getShapes() {
		return shapes;
	}

	public Shape getShape(int shapeNumber) {
		return shapes.get(shapeNumber);
	}

	public void addShape(Shape shape) {
		shapes.add(shape);
	}

	public int getNumberOfShapes() {
		return shapes.size();
	}
	
	// audio
	public List<Audio> getAudios() {
		return audios;
	}

	public Audio getAudio(int audioNumber) {
		return audios.get(audioNumber);
	}

	public void addAudio(Audio audio) {
		audios.add(audio);
	}

	public int getNumberOfAudios() {
		return audios.size();
	}
	
	// image
	public List<Image> getImages() {
		return images;
	}

	public Image getImage(int imageNumber) {
		return images.get(imageNumber);
	}

	public void addImage(Image image) {
		images.add(image);
	}

	public int getNumberOfImages() {
		return images.size();
	}
	
	// video
	public List<Video> getVideos() {
		return videos;
	}

	public Video getVideo(int videoNumber) {
		return videos.get(videoNumber);
	}

	public void addVideo(Video video) {
		videos.add(video);
	}

	public int getNumberOfVideos() {
		return videos.size();
	}
}