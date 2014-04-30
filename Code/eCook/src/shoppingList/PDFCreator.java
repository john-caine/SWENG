/* Title: PDFCreator
 * 
 * Programmers: Max, Ankita
 * 
 * Date Created: 15/04/14
 * 
 * Description: A simple class that uses the Apache PDFBox open source java PDF library to
 * 				create a PDF file containing a user-defined shopping list.
 * 				Example code sourced from http://pdfbox.apache.org/cookbook/documentcreation.html
 * 
 * 				Pages are added dynamically based on the number of lines used up by printing the items
 * 				There is no limit to the number of pages that can be created by the system,
 * 				just change the size of the shoppingList array.
 */

package shoppingList;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDJpeg;

public class PDFCreator {
	PDDocument shoppingListDocument;
	PDPage currentPage;
	PDPageContentStream currentContentStream;
	PDFont headerFont, bodyFont;
	PDJpeg logo;
	int numberOfLinesUsed;
	
	public PDFCreator(ArrayList<String> shoppingList) {
		// constructor takes a list of strings (shopping list items) and turns them into a single PDF
		if (shoppingList != null && shoppingList.size() != 0) {
			// make a document and set up fonts and logo
			headerFont = PDType1Font.COURIER_BOLD;
			bodyFont = PDType1Font.COURIER;
			InputStream in;
			try {
				shoppingListDocument = new PDDocument();
				in = new FileInputStream(new File("../Resources/spoon.jpg"));
				logo = new PDJpeg(shoppingListDocument, in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// run the PDF generator
			createPDFFromList(shoppingList);
		}
		else {
			System.out.println("shopping list is empty! PDF file will not be created.");
		}
	}
	
	// method to add a new page to the PDF document
	private void addPage() throws IOException {
		currentPage = new PDPage(PDPage.PAGE_SIZE_A4);
		shoppingListDocument.addPage(currentPage);

		// Start a new content stream which will "hold" the to be created content
		currentContentStream = new PDPageContentStream(shoppingListDocument, currentPage);

		// ensure that the logo image appears in the centre at the very top
		currentContentStream.drawImage(logo, currentPage.getMediaBox().getWidth()/2-logo.getWidth()/2, currentPage.getMediaBox().getHeight()-logo.getHeight());

		// Add a title underneath the logo
		currentContentStream.beginText();
		currentContentStream.setFont(headerFont, 18);
		currentContentStream.moveTextPositionByAmount(50, currentPage.getMediaBox().getHeight()-logo.getHeight());
		currentContentStream.drawString("Shopping List:");

		// change to body text font and go to main text area
		currentContentStream.moveTextPositionByAmount(0, -50);
		currentContentStream.setFont(bodyFont, 12);
	}
	
	// simple method to drop to a new line on the page
	private void newLine(PDPageContentStream contentStream) throws IOException {
		contentStream.moveTextPositionByAmount(0, -20);
		numberOfLinesUsed++;
		// if page is full, tidy up and make new page
		if (numberOfLinesUsed == 28) {
			currentContentStream.endText();
			// Make sure that the content stream is closed:
			currentContentStream.close();
			addPage();
			// reset the line counter flag
			numberOfLinesUsed = 0;
		}
	}
	
	// main method to create a PDF file from an array list
	private void createPDFFromList(ArrayList<String> shoppingList) {
		// initialise the number of lines used flag
		numberOfLinesUsed = 0;
		
		try {
			// add an A4 page to the document
			addPage();
			
			// loop through shopping list, adding each item in turn
			for (int i=0; i<shoppingList.size(); i++) {
				// if the string is longer than the acceptable text area width of the page
				// split the string up onto two lines
				if (shoppingList.get(i).length() > 68) {
					String firstLine = shoppingList.get(i).substring(0, 68);
					String newLine = shoppingList.get(i).substring(68, shoppingList.get(i).length());
					currentContentStream.drawString(firstLine);
					newLine(currentContentStream);
					currentContentStream.drawString(newLine);
					newLine(currentContentStream);
				}
				// else just add one ingredient per line
				else {
					currentContentStream.drawString(shoppingList.get(i));
					newLine(currentContentStream);
				}
			}
			currentContentStream.endText();
	
			// Make sure that the content stream is closed:
			currentContentStream.close();
	
			// Save the results and ensure that the document is properly closed:
			shoppingListDocument.save("Your Shopping List.pdf");
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
		catch (COSVisitorException e) {
			e.printStackTrace();
		}

	}
}