/**
 * 
 */
/**
 * @author James Oately, Paul Mathema
 *
 */
package DisplaySlide;
import java.awt.Frame;

import javax.swing.JFrame;
	
	@SuppressWarnings("serial")
	public class Slide extends JFrame {
		
		public static void main(String[]args) {
			new Slide();
		}
		
		public Slide() {
			JFrame slide = new JFrame("Blank Slide");
			slide.setSize(400,400);
			slide.setLocationRelativeTo(null);
			slide.setResizable(false);
			slide.setVisible(true);
			setFullscreen(slide);
		}
		
		public void setFullscreen(JFrame myFrame) {
		    myFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		}
		
		public void setNormal(JFrame myFrame, int x, int y) {
			myFrame.setExtendedState(Frame.NORMAL);
			myFrame.setSize(x, y);
		}
	}

