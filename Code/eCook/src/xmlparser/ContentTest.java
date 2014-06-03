/*
 * Programmer: Max
 * Created: 03/06/2014
 * Description: Simple Unit Tests for Content Class
 */

package xmlparser;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ContentTest {
	Content content;
	
	// check that setting attributes works correctly
	@Test
	public void canSetAttributes() {
		// instantiate a new content class
		content = new Content();
		
		// check that everything is null or reset to start with
		assertNull(content.width);
		assertNull(content.height);
		assertNull(content.xStart);
		assertNull(content.yStart);
		assertNull(content.urlName);
		assertFalse(content.loop);
		assertNull(content.layer);
		assertNull(content.startTime);
		assertNull(content.branch);
		assertNull(content.duration);
		assertEquals(0, content.orientation.intValue());
		assertNotNull(content.texts);
		assertNotNull(content.shapes);
		assertNotNull(content.audios);
		assertNotNull(content.images);
		assertNotNull(content.videos);
		assertEquals(0, content.texts.size());
		assertEquals(0, content.shapes.size());
		assertEquals(0, content.audios.size());
		assertEquals(0, content.images.size());
		assertEquals(0, content.videos.size());
		
		// set arbitrary values
		content.setWidth("200");
		content.setHeight("100");
		content.setXStart("300");
		content.setYStart("400");
		content.setURLName("http://example.com");
		content.setLoop("true");
		content.setLayer("12");
		content.setStartTime("30");
		content.setBranch("3");
		content.setDuration("25");
		content.setOrientation("47");
		
		// check that the fields have been set correctly
		assertEquals(200, content.width.intValue());
		assertEquals(100, content.height.intValue());
		assertEquals(300, content.xStart.intValue());
		assertEquals(400, content.yStart.intValue());
		assertEquals("http://example.com", content.urlName);
		assertTrue(content.loop);
		assertEquals(12, content.layer.intValue());
		assertEquals(30, content.startTime.intValue());
		assertEquals(3, content.branch.intValue());
		assertEquals(25, content.duration.intValue());
		assertEquals(47, content.orientation.intValue());
	}
	
	// check that getting attributes works correctly
	@Test
	public void canGetAttributes() {
		// instantiate a new content class
		content = new Content();
		
		// check that everything is null or reset to start with
		assertNull(content.width);
		assertNull(content.height);
		assertNull(content.xStart);
		assertNull(content.yStart);
		assertNull(content.urlName);
		assertFalse(content.loop);
		assertNull(content.layer);
		assertNull(content.startTime);
		assertNull(content.branch);
		assertNull(content.duration);
		assertEquals(0, content.orientation.intValue());
		assertNotNull(content.texts);
		assertNotNull(content.shapes);
		assertNotNull(content.audios);
		assertNotNull(content.images);
		assertNotNull(content.videos);
		assertEquals(0, content.texts.size());
		assertEquals(0, content.shapes.size());
		assertEquals(0, content.audios.size());
		assertEquals(0, content.images.size());
		assertEquals(0, content.videos.size());
		
		// set arbitrary values
		content.setWidth("200");
		content.setHeight("100");
		content.setXStart("300");
		content.setYStart("400");
		content.setURLName("http://example.com");
		content.setLoop("true");
		content.setLayer("12");
		content.setStartTime("30");
		content.setBranch("3");
		content.setDuration("25");
		content.setOrientation("400");
		
		// check that the fields have been set correctly
		assertEquals(200, content.getWidth().intValue());
		assertEquals(100, content.getHeight().intValue());
		assertEquals(300, content.getXStart().intValue());
		assertEquals(400, content.getYStart().intValue());
		assertEquals("http://example.com", content.getUrlName());
		assertTrue(content.getLoop());
		assertEquals(12, content.getLayer().intValue());
		assertEquals(30, content.getStartTime().intValue());
		assertEquals(3, content.getBranch().intValue());
		assertEquals(25, content.getDuration().intValue());
		assertEquals(180, content.getOrientation().intValue());
	}
	
	// check that the list operations work correctly
	@Test
	public void canSetListAttributes() {
		// instantiate a new content class
		content = new Content();

		// check that empty lists are instantiated to start with
		assertNotNull(content.texts);
		assertNotNull(content.shapes);
		assertNotNull(content.audios);
		assertNotNull(content.images);
		assertNotNull(content.videos);
		assertEquals(0, content.texts.size());
		assertEquals(0, content.shapes.size());
		assertEquals(0, content.audios.size());
		assertEquals(0, content.images.size());
		assertEquals(0, content.videos.size());
		
		// set arbitrary things to lists
		TextBody textbody1 = new TextBody();
		TextBody textbody2 = new TextBody();
		content.addText(textbody1);
		content.addText(textbody2);
		content.addText(null);
		
		Audio audio = new Audio();
		content.addAudio(audio);
		
		Shape shape = new Shape();
		content.addShape(shape);

		Image image1 = new Image();
		Image image2 = new Image();
		content.addImage(image1);
		content.addImage(image2);
		
		Video video = new Video();
		content.addVideo(video);
		
		// check the size of the lists now
		assertEquals(2, content.getNumberOfTexts().intValue());
		assertEquals(1, content.getNumberOfAudios().intValue());
		assertEquals(1, content.getNumberOfShapes().intValue());
		assertEquals(2, content.getNumberOfImages().intValue());
		assertEquals(1, content.getNumberOfVideos().intValue());
		
		// get individual elements
		assertEquals(textbody1, content.getText(0));
		assertEquals(textbody2, content.getText(1));
		assertEquals(null, content.getText(2));
		assertEquals(shape, content.getShape(0));
		assertEquals(null, content.getShape(9));
		assertEquals(image1, content.getImage(0));
		assertEquals(image2, content.getImage(1));
		assertEquals(video, content.getVideo(0));
		
		// check the list objects
		List<TextBody> testTexts = new ArrayList<TextBody>();
		testTexts.add(textbody1);
		testTexts.add(textbody2);
		assertEquals(testTexts, content.getTexts());
		
		List<Audio> testAudios = new ArrayList<Audio>();
		testAudios.add(audio);
		assertEquals(testAudios, content.getAudios());
		
		List<Shape> testShapes = new ArrayList<Shape>();
		testShapes.add(shape);
		assertEquals(testShapes, content.getShapes());
		
		List<Image> testImages = new ArrayList<Image>();
		testImages.add(image1);
		testImages.add(image2);
		assertEquals(testImages, content.getImages());
		
		List<Video> testVideos = new ArrayList<Video>();
		testVideos.add(video);
		assertEquals(testVideos, content.getVideos());
	}
}