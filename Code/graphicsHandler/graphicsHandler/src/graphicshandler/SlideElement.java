/**
* SmartTrolley
*
* A DESCRIPTION OF THE FILE
*
* @author Name1
* @author Name2
*
* @author Checked By: Checker(s) fill here
*
* @version version of this file [Date Created: 1 May 2014]
*/

/*YOUR CODE HERE*/

/**************End of SlideElement.java**************/
package graphicshandler;

/** 
 * Workspace_Name
 * 
 * A DESCRIPTION OF THE CLASS
 *
 * @author Matthew Wells
 * @author Alasdair Munday
 *
 * @author [Checked By:] [Checker(s) fill here]
 *
 * @version [version of this class] [Date Created: DD/MM/YY]
 */

public interface SlideElement {
	
	// sets the time that the product appears for 
	// using the slideElementDuration class.
	public void setDuration(int seconds);
	
	// shows the element for the selected duration
	public void show();
	
	// sets the height in pixels of the element
	public void setHeight(int newHeight);
	
	// sets the width as above
	public void setWidth(int newWidth);
	
	//sets the delay before the product appears
	public void setStartTime(int seconds);
	
}
