/*
 * Programmer: Steve Thorpe, Paul Mathema
 * Date Created: 07/05/2014
 * Description: Test that the TimerData class can be set and read from correctly.
 * 
 */

package timer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TimerDataTest {

	private Integer timeSeconds, timeMinutes, timeHours;
	private TimerData timerData;
	private String timerLabel;
	
	/*
	 * Create TimerData object and set the seconds, minutes, hours and timer label.
	 */
	@Before
	public void setUp(){
		timerData = new TimerData();
		timerData.setSeconds(3);
		timerData.setMinutes(2);
		timerData.setHours(0);
		timerData.setLabel("Chicken");
	}

	/*
	 * Test that the seconds have been correctly set
	 */
	@Test
	public void getSeconds() {
		
		timeSeconds = timerData.getSeconds();
		assertEquals(3, timeSeconds, 0.1);
	}
	
	/*
	 * Test that the minutes have been correctly set
	 */
	
	@Test
	public void getMinutes(){
		
		timeMinutes = timerData.getMinutes();
		assertEquals(2, timeMinutes, 0.1);
		
	}
	
	/*
	 * Test that the hours have been correctly set
	 */
	
	@Test
	public void getHours(){
		
		timeHours = timerData.getHours();
		assertEquals(0, timeHours, 0.1);
	}
	
	/*
	 * Test that the timer label has been correctly set
	 */
	
	@Test 
	public void getTimeLabel(){
		
		timerLabel = timerData.getLabel();
		assertEquals("Chicken", timerLabel);
		
	}
	
	

}
