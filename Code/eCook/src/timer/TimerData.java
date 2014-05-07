/*
 * Programmer: Steve Thorpe, Paul Mathema
 * Date Created: 07/05/2014
 * Description: Data structure class to get the seconds, minutes, hours and label from a timer to pass to the next slide so the timer can be recreated.
 * 
 */
package timer;

public class TimerData {
	
	private Integer seconds, minutes, hours;
	private String timerLabel;

	/*
	 * Sets the Seconds value of a timer from when the slide changed.
	 * @Param seconds: The seconds value of a timer
	 */
	
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
	/*
	 * Sets the Minutes value of a timer from when the slide changed.
	 * @Param minutes: The minutes value of a timer
	 */
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	/*
	 * Sets the Hours value of a timer from when the slide changed.
	 * @Param hours: The hours value of a timer
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	/*
	 * Sets the TimerLabel of a timer from when the slide changed.
	 * @Param timerLabel: The label string of a timer
	 */
	public void setLabel(String timerLabel) {
		this.timerLabel = timerLabel;
	}
	
	/*
	 * Returns the Seconds value of a timer from when the slide changed.
	 */
	public Integer getSeconds() {		
		return seconds;
	}
	
	/*
	 * Returns the Minutes value of a timer from when the slide changed.
	 */
	public Integer getMinutes() {		
		return minutes;
	}
	
	/*
	 * Returns the Hours value of a timer from when the slide changed.
	 */
	public Integer getHours() {	
		return hours;
	}
	
	/*
	 * Returns the Timer Label from when the slide changed.
	 */
	public String getLabel() {	
		return timerLabel;
	}

}
