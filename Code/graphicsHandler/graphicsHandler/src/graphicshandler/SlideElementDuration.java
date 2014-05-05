/**
 * Spikes
 *
 * A DESCRIPTION OF THE FILE
 *
 * @author Name1
 * @author Name2
 *
 * @author Checked By: Checker(s) fill here
 *
 * @version version of this file [Date Created: 28 Apr 2014]
 */

/*YOUR CODE HERE*/

/**************End of SlideElementDuration.java**************/
package graphicshandler;


import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.Node;


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

public class SlideElementDuration {

	private Timer timer;
	
	private Node node;

	private int duration = 0;

	private int startTime = 0;
	
	private boolean started;

	public SlideElementDuration(Node node) {
		this.node = node;
		
	}

	public void show(){
		
		if(duration == 0){
			node.setVisible(true);
		}else{
		
		timer = new Timer();
		
		started = true;
		
		TimerTask appear = new ShowTask();
		timer.schedule(appear, this.startTime);
		}
	}
	

	public void setStartTime(int milliseconds) {
		startTime = milliseconds;
	}
	
	public void setDuration(int milliseconds){
		this.duration = milliseconds;
	}
	
	private class ShowTask extends TimerTask{
		@Override
		public void run() {
				if (started){
					//after start time has finished
					node.setVisible(true);
					started = false;
					
					//0 duration keeps shape visible
					if(duration != 0){
						timer.schedule( new ShowTask(), duration);
					}
					
				}else{
					//after duration has finished
					node.setVisible(false);
				}
		}
		
	}

}
