import java.awt.event.*;
import java.awt.*;
import java.text.SimpleDateFormat;

import javax.swing.*;


public class CountdownTimer {

	JLabel hourLabel, mintuesLabel, secondsLabel, timerLabel;
	int secondsCount, mintuesCount, hoursCount;
	JTextField hourstf, mintuestf, secondstf;
	JButton button;
	Timer timer;
	SimpleDateFormat df;
	JFrame jframe ;
	
	public CountdownTimer(){
		jframe =  new JFrame();
		jframe.setLayout(new FlowLayout());
		
		hourstf = new JTextField(5);
		jframe.add(hourstf);
		
		hourLabel = new JLabel("Hours");
		jframe.add(hourLabel);
		
		mintuestf = new JTextField(5);
		jframe.add(mintuestf);
		
		mintuesLabel = new JLabel("Mintues");
		jframe.add(mintuesLabel);
		
		secondstf = new JTextField(5);
		jframe.add(secondstf);
		
		secondsLabel = new JLabel("Seconds");
		jframe.add(secondsLabel);
		
		button = new JButton("Start");
		jframe.add(button);
		
		timerLabel = new JLabel("Waiting");
		jframe.add(timerLabel);
		
		
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			
			if(secondstf.getText().equals("")){
				secondsCount = 0;
			}
			else{
				secondsCount  = Integer.parseInt(secondstf.getText());
			}
			if(mintuestf.getText().equals("")){
				mintuesCount = 0;
			}
			else{
				mintuesCount  = Integer.parseInt(mintuestf.getText());
			}
			if(hourstf.getText().equals("")){
				hoursCount = 0;
			}
			else{
				hoursCount  = Integer.parseInt(hourstf.getText());
			}
			timer = new Timer(1000, new ActionListener() {
	            public void actionPerformed(ActionEvent event) {
        
	    			if (secondsCount >= 1){
	    				secondsCount -- ;
	    				timerLabel.setText("Time left : " + String.format("%02d", hoursCount) +":"
	    						+ String.format("%02d", mintuesCount) + ":" + String.format("%02d", secondsCount));
	    			}
	    			else if((secondsCount == 0)&(mintuesCount >= 1)){
	    				secondsCount = 59;
	    				mintuesCount --;
	    				timerLabel.setText("Time left : " + String.format("%02d", hoursCount) +":"
	    						+ String.format("%02d", mintuesCount) + ":" + String.format("%02d", secondsCount));
	    			}
	    			else if((mintuesCount == 0)&(hoursCount >= 1)){
	    				mintuesCount = 59;
	    				secondsCount = 59;
	    				hoursCount --;
	    				timerLabel.setText("Time left : " + String.format("%02d", hoursCount) +":"
	    						+ String.format("%02d", mintuesCount) + ":" + String.format("%02d", secondsCount));
	    			}
	    			else if ((secondsCount == 0)&(mintuesCount == 0)&(hoursCount == 0)){
	    				timer.stop();
	    				jframe.toFront();
	    				jframe.setVisible(true);
	    				timerLabel.setText("Done!");
	    				Toolkit.getDefaultToolkit().beep();
	    				FrameUtils.vibrate(jframe);
	    			}
	            }
			});
			timer.start();
			}
		});
	}

	
	public static void main(String args[]){
		CountdownTimer CDTimer = new CountdownTimer();
		CDTimer.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		CDTimer.jframe.pack();
		CDTimer.jframe .setSize(400,100);
		CDTimer.jframe .setVisible(true);
	}
}
