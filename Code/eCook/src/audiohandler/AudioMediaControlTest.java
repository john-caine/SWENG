/*
 * Programmer: Roger Tan
 * Date Created: 30/05/2014
 * Junit test for the Audio Media Control Panel
 */
package audiohandler;

import static org.junit.Assert.*;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.media.MediaView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import eCook.SlideShow;
import xmlparser.Audio;
import xmlparser.XMLReader;


public class AudioMediaControlTest {
	
	private AudioHandler audioHandler;
	private XMLReader reader;
	private SlideShow parent;
	private List<Audio> audioList;
	@Rule public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();
	
	//Read XML playlist and create a new audioHandler object based on the first audio found
	@Before
	public void setUp() throws Exception {
		reader = new XMLReader("PWSExamplePlaylist_4.xml");
		audioList = reader.getRecipe().getSlide(2).getContent().getAudios();
		audioHandler = new AudioHandler(parent, audioList.get(0).getUrlName(), audioList.get(0).getStartTime(), 
				audioList.get(0).getDuration(), audioList.get(0).getLoop());
	}

	@Test
	public void test() {
		/* mediaView contains a MediaView */
		assertTrue(audioHandler.mediaControl.mediaView instanceof MediaView);
		
		/* mediaBar contains the Play/Pause Button */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(0) instanceof Button);
		
		/* mediaBar contains the Stop Button */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(1) instanceof Button);
		
		/* mediaBar contains the Fullscreen Button */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(2) instanceof Button);
		
		/* mediaBar contains the Time Label */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(3) instanceof Label);
		
		/* mediaBar contains the Time Slider */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(4) instanceof Slider);
		
		/* mediaBar contains the Play Time Label */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(5) instanceof Label);
		
		/* mediaBar contains the Volume Label */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(6) instanceof Label);
		
		/* mediaBar contains the Volume Slider */
		assertTrue(audioHandler.mediaControl.mediaBar.getChildren().get(7) instanceof Slider);
		
		/* MediaControl Class Contains the mediaView and mediaBar */
		assertTrue(audioHandler.mediaControl.overallBox.getChildren().get(0) instanceof MediaView);
		assertTrue(audioHandler.mediaControl.overallBox.getChildren().get(1) instanceof HBox);	
		
		/* Detect if VideoPlayer is set to be on repeat. 
		 * Return -1 for Loop = true & 1 for Loop = false */
		assertEquals(1, audioHandler.mediaControl.mp.getCycleCount(), 0.01);
	}

}
