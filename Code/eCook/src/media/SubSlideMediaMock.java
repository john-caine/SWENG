/*
 * Programmer: Steve Thorpe, Jonathan Caine 
 * Date Created: 04/06/2014
 * Description: SubSlideMediaMock class for testing purposes. NOT TO BE INSTANTIATED IN SOURCE CODE
 */

package media;

import eCook.SlideShow;

public class SubSlideMediaMock extends SubSlideMedia{
	
	 /**
	  * SubSlideMediaMock Constructor
	  * 
	 * @param parent: Slideshow which called the handler.
	 * @param xStart: The x co ordinate of the top left of the created HBox.
	 * @param yStart: The y co ordinate of the top left of the created HBox.
	 * @param startTime: The time in seconds the HBox is to appear on the slide.
	 * @param duration: The time in seconds the HBox is to to be removed from the slide.
	 * @param branchID: The ID of the branch the HBox is to branch to.
	 * @param orientation: The orientation of the HBox in degrees.
	 */
	public SubSlideMediaMock(SlideShow parent, int xStart, int yStart, Integer startTime, Integer duration, Integer branchID, Integer orientation){
		 super(parent,  xStart, yStart, startTime,  duration,  branchID,  orientation);
		 
	 }

}
