/*
 Programmers : Roger & Prakruti
 Date created: 13/2/2014
 Description: Create the main menu of the UserInterface
 Version : 1.0 13/2/2014
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;




public class MainMenu extends JFrame {
		
	private JButton CreateSlide;
	private JTextField SlideAmount;
	
	public MainMenu(){
		super("MainMenu"); //Access your super class and add title

		
		CreateSlide =  new JButton();
		CreateSlide.setIcon(new ImageIcon("slideshow.png"));
		CreateSlide.setBorder(null);
		CreateSlide.setToolTipText("Create Slide");
		add(CreateSlide);
		
		SlideAmount = new JTextField("Key in the amount of slides to be created", 30);
		
		JPanel Panel = new JPanel();
		Panel.setLayout(new FlowLayout());
		Panel.add(SlideAmount);
		Panel.add(CreateSlide);
		
		add(Panel, BorderLayout.CENTER);
		
		CreateSlide.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String inputSlideAmount = SlideAmount.getText();
				new Slide(Integer.parseInt(inputSlideAmount));
			}
		});
	}
	
}
