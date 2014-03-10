/*
 Programmers : Roger & Prakruti
 Date created: 27/2/2014
 Description: Exit Slideshow to MainMenu Test
 Version : 1.0 27/02/2014
 		   1.1 04/03/2014 - Junit test for creation of main menu and slide's buttons.

 Program Description : This program is designed to create a slide from the main menu. 
					   The program is capable of exiting the slide and return to the main menu.
 					   
 Program Setup : This program is designed to create a fullscreen slide whenever the "Create Slide" button
 				 is being pressed. In the slide, there's a "Exit Slide" button to close the slide and return
 			     to main menu. This could also be done by pressing the "ESC" key.
 				
 Test Results :When "Create Slide" button is pressed, a separate fullscreen window (slide) pops up.
 			   When "Exit Slide" button is pressed, the slide window closes and return to main menu.
 			   When "ESC" key is pressed, the slide window closes and return to main menu.
 */

package mainmenu;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class MainMenuTest{
	
	private MainMenuButton mainMenuButton;
	private SlideButton slideButton;
	@Before
	public void setup(){
		mainMenuButton =  new MainMenuButton();
		slideButton = new SlideButton();
	}
	
	@Test
	public void mainMenuButton(){
		System.out.print("Main Menu Button's Text = " + mainMenuButton.createSlide.getText() + "\n");
		assertEquals("Create Slide",mainMenuButton.createSlide.getText());
		assertTrue(mainMenuButton.createSlide.isVisible());
	}
	
	@Test
	public void slideButton(){
		System.out.print("Slide Button's Text = " + slideButton.exitSlide.getText() + "\n");
		assertEquals("Exit Slide", slideButton.exitSlide.getText());
		assertTrue(slideButton.exitSlide.isVisible());
	}
	
}