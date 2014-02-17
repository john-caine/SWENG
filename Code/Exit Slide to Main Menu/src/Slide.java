/*
 Programmers : Roger & Prakruti
 Date created: 13/2/2014
 Description: Create a slide via a Jframe with various controls
 Version : 1.0 13/2/2014
 */
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Slide extends JFrame{
	
	public JButton ExitSlide;

	public Slide(){
		super("Slide"); //Access your super class and add title
		
		ExitSlide =  new JButton("Return to Main Menu");	
		
		JPanel ControlPanel = new JPanel();
		ControlPanel.setLayout(new FlowLayout());
		ControlPanel.add(ExitSlide);
		add(ControlPanel, BorderLayout.SOUTH);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(dimension.width, dimension.height);
		setUndecorated(true);
		setVisible(true);
		setFocusable(true);
		
		addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent ev) {
            	 if(ev.getKeyCode() == KeyEvent.VK_ESCAPE){
            		 setVisible(false);
            		 dispose();           
            	 }
             }
		 });	
		
		ExitSlide.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
	}
}
