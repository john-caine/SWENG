/*
 Programmers : Roger Tan, Jonathan Caine, Zayyad Tagwai
 Date created: 06/03/2014
 Description: Image Display Test
 Version : 1.2 

 Program Description : This program is designed to display any amount of images on any location of the window. 
 					   The program is capable of resizing the image while preserving the image's ratio if either 
 					   width or height is only stated. 
 		
 Program Setup : The image to be displayed is being retrieved via this location path;
 				 ("C:/Users/R T/workspace/ExampleProject/src/DOGE.png")
 				 The image is set to display at X location of 200 and Y location of 200 of the window.
 				
 Manual Test Results : The image displayed on the window matches the image being setup.
 					   Multiple images are being displayed according to their specified location and size.
 */

package imageDisplay;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ImageHandlerTest {
	ImageHandler imageHandler;
	
	@Before
	public void setup() {
		imageHandler = new ImageHandler("C:/Users/R T/workspace/ExampleProject/src/DOGE.png", 300, 300, 500, 500);
	}
	
	/*
	 * Ensure that the ImageHandler class manage to locate the image file
	 */
	@Test
	public void retrieveImageFromLocationPath(){
		assertNotNull(imageHandler.retrieveImage("C:/Users/R T/workspace/ExampleProject/src/DOGE.png"));
	}
	
	/*
	 * Retrieve the xStart and yStart of the Image
	 */
	@Test
	public void retrieveImageLocation(){
		System.out.println("Image's x Location = " + imageHandler.returnImage().getLayoutX());
		System.out.println("Image's y Location = " + imageHandler.returnImage().getLayoutY());
		assertEquals(300,imageHandler.returnImage().getLayoutX(), 0.01);
		assertEquals(300,imageHandler.returnImage().getLayoutY(), 0.01);
	}
	
	/*
	 * Retrieve the size of the Image
	 */
	@Test
	public void retrieveImageSize(){
		System.out.println("Image's Width = " + imageHandler.returnImage().getFitWidth());
		System.out.println("Image's Height = " + imageHandler.returnImage().getFitHeight());
		assertEquals(500,imageHandler.returnImage().getFitWidth(), 0.01);
		assertEquals(500,imageHandler.returnImage().getFitHeight(), 0.01);
	}
	
	/*
	 * Retrieve modified Image from ImageHandler Class
	 */
	@Test
	public void retrieveImage(){
		assertNotNull(imageHandler.returnImage());
	}
	
	/*
	 * Ensure that the image is being displayed
	 */
	@Test
	public void ImageIsShowing(){
		assertTrue(imageHandler.returnImage().isVisible());
	}

}