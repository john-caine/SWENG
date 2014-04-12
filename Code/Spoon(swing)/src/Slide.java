/*
 Programmers : Roger & Prakruti
 Date created: 13/2/2014
 Description: Create a slide via a Jframe with various controls
 Version : 1.0 13/2/2014
 		   1.1 14/2/2014 Description : Manage to change picture and title. However, ingredient list not displaying properly
 		   1.2 15/2/2014 Description : Manage to display ingredient list and notes. Store and display notes. Open text file to print.
		   1.3 16/2/2014 Description : Manage to convert the .txt file to .pdf and invoke print directly.
		   1.4 17/2/2014 Description : Manage to highlight whatever ingredients into shopping list.
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Formatter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import com.itextpdf.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Slide<Int>{
	
	private JButton exitSlide;
	private JButton storeNotes;
	private JButton printNotes;
	private JButton	storeShoppingList;
	private JButton printShoppingList;
	private JLabel slideAmountLabel;
	private int slideNumber = 1;
	public int totalSlideAmount;
	BufferedImage image;
	Toolkit ToolKit;
	Dimension Dimension;
	private JLabel titleLabel;
	private JLabel ingredientLabel;
	private JLabel notesLabel;
	private JLabel shoppingListLabel;
	private JTextArea ingredientList;
	private JTextArea notes;
	private JTextArea shoppingList;
	private String text;
	public JFrame frame;
	private Formatter x;
	CountdownTimer countdownTimer;
	
	
	public Slide(final Int slideAmount){
		
		JButton timer = new JButton();
		timer.setIcon(new ImageIcon("timer.png"));
		timer.setBorder(null);
		timer.setToolTipText("Set Timer");
		
		frame = new JFrame("Slide");
		//super("Slide" + slideAmount.toString()); //Access your super class and add title
		totalSlideAmount = (Integer) slideAmount;
		
		ToolKit = Toolkit.getDefaultToolkit();
		Dimension = ToolKit.getScreenSize();
		
		exitSlide =  new JButton("Return to Main Menu");
		
		storeNotes = new JButton();
		storeNotes.setIcon(new ImageIcon("notes.png"));
		storeNotes.setBorder(null);
		storeNotes.setToolTipText("Store Notes");
		
		printNotes = new JButton();
		printNotes.setIcon(new ImageIcon("printer.png"));
		printNotes.setBorder(null);
		printNotes.setToolTipText("Print Notes");
		
		storeShoppingList = new JButton();
		storeShoppingList.setIcon(new ImageIcon("shoppinglist.png"));
		storeShoppingList.setBorder(null);
		storeShoppingList.setToolTipText("Store Shopping List");
		
		printShoppingList = new JButton();
		printShoppingList.setIcon(new ImageIcon("printer.png"));
		printShoppingList.setBorder(null);
		printShoppingList.setToolTipText("Print Shopping List");
		
		slideAmountLabel = new JLabel();
		slideAmountLabel.setText(String.valueOf(slideNumber));
		
		titleLabel = new JLabel();
		titleLabel.setFont(new Font("Verdana", Font.BOLD, 35)); 
		titleLabel.setForeground(Color.ORANGE);
		
		ingredientLabel = new JLabel();
		ingredientLabel.setFont(new Font("Verdana", Font.BOLD, 20)); 
		ingredientLabel.setForeground(Color.BLACK);
		ingredientLabel.setText("Ingredients");
		
		ingredientList =  new JTextArea(text,5,40);
		ingredientList.setEditable(false);
		
		notesLabel = new JLabel();
		notesLabel.setFont(new Font("Verdana", Font.BOLD, 20)); 
		notesLabel.setForeground(Color.BLACK);
		notesLabel.setText("Notes");
		
		notes =  new JTextArea(text,5,40);
		notes.setEditable(true);
		
		shoppingListLabel = new JLabel();
		shoppingListLabel.setFont(new Font("Verdana", Font.BOLD, 20)); 
		shoppingListLabel.setForeground(Color.BLACK);
		shoppingListLabel.setText("Shopping List");
		
		shoppingList = new JTextArea(text,5,40);
		shoppingList.setEditable(true);
		
		JPanel imageLabel = new JPanel() {
			@Override
			public void paint(Graphics g){
				if(slideNumber == 1){
					text = "1 onion, roughly chopped\n" +
							   "2 carrots, roughly chopped\n" + 
							   "1 free range chicken, about 1½ kg/3lb 5oz\n" +
							   "1 lemon, halved\n" +
							   "small bunch thyme\n";
					ingredientList.setText(text);
					titleLabel.setText("Garlic Roast Chicken");
					writeToNote(titleLabel.getText() +" Notes.txt", notes);	
					retrieveImage(titleLabel.getText());
				}
				else if (slideNumber == 2){
					text = "160 g of spaghetti\n"+
							"1 fresh garlic\n"+
							"2 fresh chili\n"+
							"Olive oil\n"+
							"Fresh Basil, fresh oregano, fresh thyme\n"+
							"Breadcrumb\n";
					ingredientList.setText(text);
					titleLabel.setText("Aglio Olio");
					writeToNote(titleLabel.getText() + " Notes.txt", notes);
					retrieveImage(titleLabel.getText());
				}
				writeToNote("Shopping List.txt", shoppingList);
				super.paint(g);
				g.drawImage(image, 150, 0, null);
			}
		};
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
		controlPanel.add(exitSlide);
		controlPanel.add(slideAmountLabel);
		
		/* Ingredient title and List Panel*/
		JPanel ingredientLabelPanel = new JPanel();
		ingredientLabelPanel.setLayout(new FlowLayout());
		ingredientLabelPanel.add(ingredientLabel);
		
		JPanel ingredienttitleandList = new JPanel();
		ingredienttitleandList.setLayout(new BorderLayout());
		ingredienttitleandList.add(ingredientLabelPanel, BorderLayout.NORTH);
		ingredienttitleandList.add(ingredientList, BorderLayout.SOUTH);
		
		
		/* Notes title and note Panel*/
		JPanel notestitleandbutton = new JPanel();
		notestitleandbutton.setLayout(new FlowLayout());
		notestitleandbutton.add(notesLabel);
		notestitleandbutton.add(storeNotes);
		notestitleandbutton.add(printNotes);
		
		JPanel notestitleandList = new JPanel();
		notestitleandList.setLayout(new BorderLayout());
		notestitleandList.add(notestitleandbutton, BorderLayout.NORTH);
		notestitleandList.add(notes, BorderLayout.SOUTH);
		
		/* Shopping List title and List Panel*/
		JPanel shoppingListLabelPanel = new JPanel();
		shoppingListLabelPanel.setLayout(new FlowLayout());
		shoppingListLabelPanel.add(shoppingListLabel);
		shoppingListLabelPanel.add(storeShoppingList);
		shoppingListLabelPanel.add(printShoppingList);
		shoppingListLabelPanel.add(timer);
				
		JPanel shoppingLabelandList = new JPanel();
		shoppingLabelandList.setLayout(new BorderLayout());
		shoppingLabelandList.add(shoppingListLabelPanel, BorderLayout.NORTH);
		shoppingLabelandList.add(shoppingList, BorderLayout.SOUTH);
		
		/*Ingredient,Shopping List and Notes Panel*/		
		JPanel ingredientandNotes = new JPanel();
		ingredientandNotes.setLayout(new BorderLayout());
		ingredientandNotes.add(notestitleandList, BorderLayout.SOUTH);
		ingredientandNotes.add(ingredienttitleandList, BorderLayout.NORTH);
		
		JPanel titleandList = new JPanel();
		titleandList.setLayout(new BorderLayout());
		titleandList.add(ingredientandNotes, BorderLayout.WEST);
		titleandList.add(titleLabel, BorderLayout.NORTH);
		
		JPanel shoppingPanel = new JPanel();
		shoppingPanel.setLayout(new BorderLayout());
		shoppingPanel.add(shoppingLabelandList, BorderLayout.NORTH);
		
		frame.add(titleandList, BorderLayout.WEST);
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.add(shoppingPanel, BorderLayout.EAST);
		frame.add(imageLabel);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(Dimension.width, Dimension.height);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setFocusable(true);
		
		
		
		frame.addKeyListener(new KeyAdapter() {
             public void keyPressed(KeyEvent ev) {
            	 if(ev.getKeyCode() == KeyEvent.VK_ESCAPE){
            		 frame.setVisible(false);
            		 frame.dispose();           
            	 }
            	 else if(ev.getKeyCode() == KeyEvent.VK_LEFT){
            			if(slideNumber <= 1){
        					slideAmountLabel.setText("1");
        				}
        				else{
        					slideNumber --;
        					slideAmountLabel.setText(String.valueOf(slideNumber));
        				}
            			frame.setFocusable(true);
						frame.repaint();
            	 }
            	 else if(ev.getKeyCode() == KeyEvent.VK_RIGHT){
                		if(slideNumber >= totalSlideAmount){
        					slideAmountLabel.setText(slideAmount.toString());
        				}
        				else{
        					slideNumber ++;
        					slideAmountLabel.setText(String.valueOf(slideNumber));
        				}
                		frame.setFocusable(true);
                		frame.repaint();
        			}       
             	}
		 });	
		
		exitSlide.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent e){
				frame.dispose();
			}
		});
		
		/*notes.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ev) {
           	 if(ev.getKeyCode() == KeyEvent.VK_ESCAPE){     
           		 frame.requestFocus();
           	 }
			}
		});*/
		
		storeNotes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					if(slideNumber == 1){
					x = new Formatter(titleLabel.getText()+" Notes.txt");
					}
					else if (slideNumber == 2){
					x = new Formatter(titleLabel.getText()+" Notes.txt");	
					}
					x.format("%s", notes.getText().replaceAll("\n", System.getProperty("line.separator")));
					x.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try{
					String text;
					Document document = new Document(PageSize.A4, 50, 50, 50, 50);
				    PdfWriter.getInstance(document,new FileOutputStream(titleLabel.getText()+" Notes.pdf"));
				    document.open();
				    document.add(new Paragraph("Notes for " + titleLabel.getText()));
					FileReader fr = new FileReader(titleLabel.getText()+" Notes.txt");
					BufferedReader reader = new BufferedReader(fr);
			        while((text = reader.readLine())!= null){
			        	document.add(new Paragraph(text));
				    }
				    document.close();
				    }catch(Exception e1){}				
			}
		});
		
		storeShoppingList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					x = new Formatter("Shopping List.txt");
					x.format("%s", shoppingList.getText().replaceAll("\n", System.getProperty("line.separator")));
					x.close();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try{
					String text;
					Document document = new Document(PageSize.A4, 50, 50, 50, 50);
				    PdfWriter.getInstance(document,new FileOutputStream("Shopping List.pdf"));
				    document.open();
				    document.add(new Paragraph("Shopping List"));
					FileReader fr = new FileReader("Shopping List.txt");
					BufferedReader reader = new BufferedReader(fr);
			        while((text = reader.readLine())!= null){
			        	document.add(new Paragraph(text));
				    }
				    document.close();
				    }catch(Exception e1){}				
			}
		});
		
		printShoppingList.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				openUpTextFile("Shopping List.pdf");
			}
		});
		printNotes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				openUpTextFile(titleLabel.getText()+" Notes.pdf");
			}
		});
		
		timer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				countdownTimer = new CountdownTimer();
				countdownTimer.jframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				countdownTimer.jframe.pack();
				countdownTimer.jframe.setSize(400,100);
				countdownTimer.jframe.setVisible(true);
				countdownTimer.jframe.toFront();
				countdownTimer.jframe.setAlwaysOnTop(true);
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {
				frame.requestFocus();
				//countdownTimer.jframe.setAlwaysOnTop(true);
			}
		});
		
		ingredientList.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent me) {
				if (ingredientList.getSelectedText() != null){ //see if they selected something 
			        String s = ingredientList.getSelectedText();
			        shoppingList.append(s + "\n");
			        System.out.printf(s);
				}
			}
		});
	}
	
	public void writeToNote(String titleLabel, JTextArea Jtextarea){
		try {
			String textLine;
			FileReader fr = new FileReader(titleLabel);
			BufferedReader reader = new BufferedReader(fr);
		    do {Jtextarea.read(reader, null);}    
		    while((textLine=reader.readLine())!=null);
		}
		catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void retrieveImage(String titleLabel){
		try {
			image = ImageIO.read(new File("C:/Users/R T/workspace/Spoon/" + titleLabel + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openUpTextFile(String titleLabel){
		File file = new File(titleLabel);
		try {
			java.awt.Desktop.getDesktop().open(file);
			java.awt.Desktop.getDesktop().print(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
