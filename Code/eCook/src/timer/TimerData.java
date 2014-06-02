/*
 * Programmer: Steve Thorpe, Paul Mathema
 * Date Created: 07/05/2014
 * Description: Data structure class to get the seconds, minutes, hours and label from a timer to pass to the next slide so the timer can be recreated.
 * 
 */
package timer;

public class TimerData {
	
	private Integer seconds, minutes, hours, startSeconds, startMinutes, startHours;
	private String timerLabel;
	private int timerID;

	/*
	 * Sets the Seconds value of a timer from when the slide changed.
	 * @Param seconds: The seconds value of a timer
	 */
	
	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}
	
	/*
	 * Sets the startSeconds value of a timer from when the slide changed.
	 * @Param startSeconds: The seconds value timer the timer was started from.
	 */
	public void setSecondStart(Integer startSeconds){
		this.startSeconds = startSeconds;
	}
	
	/*
	 * Sets the Minutes value of a timer from when the slide changed.
	 * @Param minutes: The minutes value of a timer
	 */
	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}
	
	/*
	 * Sets the start Minutes value of a timer from when the slide changed.
	 * @Param startMinutes: The minutes value the timer was started from.
	 */
	public void setMinutesStart(Integer startMinutes){
		this.startMinutes = startMinutes;
	}
	
	/*
	 * Sets the Hours value of a timer from when the slide changed.
	 * @Param hours: The hours value of a timer
	 */
	public void setHours(Integer hours) {
		this.hours = hours;
	}
	
	/*
	 * Sets the startHours value of a timer from when the slide changed.
	 * @Param startHours: The hours value the timer was started from.
	 */
	public void setHoursStart(Integer startHours){
		this.startHours = startHours;
	}
	
	/*
	 * Sets the TimerLabel of a timer from when the slide changed.
	 * @Param timerLabel: The label string of a timer
	 */
	public void setLabel(String timerLabel) {
		this.timerLabel = timerLabel;
	}
	
	public void setTimerID(int timerID){
		this.timerID = timerID;
	}
	/*
	 * Returns the Seconds value of a timer from when the slide changed.
	 */
	public Integer getSeconds() {		
		return seconds;
	}
	/*
	 * Returns the startsSeconds value of the timer.
	 */
	public Integer getStartSeconds(){
		return startSeconds;
	}
	
	/*
	 * Returns the Minutes value of a timer from when the slide changed.
	 */
	public Integer getMinutes() {		
		return minutes;
	}
	
	/*
	 * Returns the startMinutes value of the timer.
	 */
	public Integer getStartMinutes(){
		return startMinutes;
	}
	/*
	 * Returns the Hours value of a timer from when the slide changed.
	 */
	public Integer getHours() {	
		return hours;
	}
	
	/*
	 * Returns the startHours value of the timer.
	 */
	public Integer getStartHours(){
		return startHours;
	}
	/*
	 * Returns the Timer Label from when the slide changed.
	 */
	public String getLabel() {	
		return timerLabel;
	}
	
	public int getTimerID(){
		return timerID;
	}

}
