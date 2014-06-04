/*
 * Programmer: Steve Thorpe & Jonathan Caine
 * Date Created: 04/06/2014
 * Description: A mock class of abstract class for SubSlideMedia to be instantiated for testing purposes. NOT TO BE USED IN ECOOK!
 */

package media;

import eCook.SlideShow;

public class SubSlideMediaMock extends SubSlideMedia {
	
	protected SlideShow parent;
	protected Integer branchID;

	public SubSlideMediaMock(SlideShow parent, int xStart, int yStart, Integer startTime, Integer duration, Integer branchID, Integer orientation){
		super(parent, xStart, yStart, startTime, duration, branchID, orientation);
		this.parent = parent;
		this.branchID = branchID;
		
	}
	
	@Override
	public void doBranch(){
		parent.newSlide(branchID, true, null);
	}

}
