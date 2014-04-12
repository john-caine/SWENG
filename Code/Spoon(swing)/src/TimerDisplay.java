import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
 
 
public class TimerDisplay extends JLabel implements ActionListener {
    private long count;
    private int hours;
    private int minutes;
    private int seconds;
    private DateFormat df;
 
    public TimerDisplay(int hours, int minutes, int seconds) {
	// suppose to show as in 01 HR 30 MIN 30 SEC.
	super("Remaining Time: 0 secs", JLabel.CENTER);
 
	Calendar cal = Calendar.getInstance();
	cal.set(Calendar.HOUR, 0);
	cal.set(Calendar.MINUTE, minutes);
	cal.set(Calendar.SECOND, seconds);
	count = cal.getTime().getTime();
	System.out.println(count);
	df = new SimpleDateFormat("hh:mm:ss");
 
	javax.swing.Timer t = new javax.swing.Timer(1000, this);
	t.start();
    }
 
    public void actionPerformed(ActionEvent e) {
	// suppose to countdown till OO HR 00 MIN 00 SEC
	setText(df.format(count));
	count -= 1000;
    }
 
    public static void main(String[] arg) {
	JFrame f = new JFrame();
	f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	f.setSize(300, 100);
 
	TimerDisplay c = new TimerDisplay(0, 0, 30);
	f.getContentPane().add(c);
	f.setVisible(true);
    }
}