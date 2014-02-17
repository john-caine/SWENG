/*
 Programmers : Roger & Prakruti
 Date created: 13/2/2014
 Description: Create the UserInterface
 Version : 1.0 13/2/2014
 */
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;


public class UserInterface {
	public static void main(String[] args){
		
		MainMenu MainMenu = new MainMenu();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		MainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainMenu.setSize(dimension.width, dimension.height);
		//mainmenu.setUndecorated(true);
		MainMenu.setVisible(true);
	}
}
