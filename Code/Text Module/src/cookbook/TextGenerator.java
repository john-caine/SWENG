/*
 * Programmer: Steve Thorpe, Sam Beedal
 * Date Created: 13/02/2014
 * Description: The text module class, sets the slide text attributes which have been retrieved
 * from the XML
 * Version History: 1.0 - Created.
 * 
 */

package cookbook;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TextGenerator extends JPanel {
	
	
	
	String textObject;

	
	
	
	public void setText(String string) {
		
		textObject = string;
		
	}

	public String getText() {
		
		return textObject;
	}
	
	
	public  void displayText(){
		
		JFrame frame = new JFrame();
		frame.getContentPane();
		JLabel label = new JLabel();
		label.setText(textObject);
		frame.add(label);
		frame.setSize(300,300);
		frame.setVisible(true);
		
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
		
	}
	

}
