/*
 Programmers : Roger & Prakruti
 Date created: 13/2/2014
 Description: Test the User Interface
 Version : 1.0 13/2/2014 Description: slide creation and exit is working.
 */
import static org.junit.Assert.*;

import javax.swing.JButton;

import org.junit.Before;
import org.junit.Test;


public class UserInterfaceTest {
	private MainMenu mainMenu;
	private Slide slide;
	
	@Before
	public void setUp() throws Exception{
		mainMenu =  new MainMenu();
		slide = new Slide();
	}
	
	
	@Test
	public void  slideCreated(){
		JButton createButton = mainMenu.CreateSlide;
		createButton.doClick();
		assertTrue(slide.isVisible());
	}
	
	@Test
	public void  slideExit(){
		JButton exitButton = slide.ExitSlide;
		exitButton.doClick();
		assertFalse(slide.isVisible());
	}

}
