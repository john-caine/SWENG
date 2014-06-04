/*
 * Programmer: Steve Thorpe & Jonathan Caine
 * Date Created: 04/06/2014
 * Description: A mock class of abstract class for SlideMedia to be instantiated for testing purposes. NOT TO BE USED IN ECOOK!
 */

package media;

public class SlideMediaMock extends SlideMedia{

	/*
	 *SlideMediaMock constructor
	 * @Param xStart: The x co-ordinate of the HBox
	 * @Param yStart: The y co-ordinate of the HBox
	 * @Param startTime: The value the start time Timeline is to count from.
	 */ 
	 
	public SlideMediaMock(int xStart, int yStart, Integer startTime) {
		super(xStart, yStart, startTime);
		 
	}

	/*
	 * Over rides are required for inherited abstract methods. Mocking class does not require an implementation for these methods.
	 */
	@Override
	protected void setTimeLineOnFinish() {
	}

	@Override
	protected void tearDown() {
	}

	@Override
	protected void setTimingValues() {
	}

}
