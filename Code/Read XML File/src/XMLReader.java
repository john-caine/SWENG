import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * enum type for keeping track of when we need to store content in certain elements
 * 
 */
enum ProcessingElement {
	NONE, ID, TITLE, PEOPLE, TIME, CHEF, DESCRIPTION
};

public class XMLReader extends DefaultHandler {
	private Recipe recipe;
	private ProcessingElement currentElement = ProcessingElement.NONE;
	private String inputFile = "exampleSoups.xml";
	private CookBook cookBook; 

	public XMLReader() {
		readXMLFile(inputFile);
	}
	
	public CookBook getCookBook(){
		return this.cookBook;
	}

	public void readXMLFile(String inputFile) {

		try {
			// use the default parser
			cookBook = new CookBook(inputFile);
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			// parse the input
			saxParser.parse(inputFile, this);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException saxe) {
			saxe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * Called by the parser when it encounters the start of the XML file.
	 */
	public void startDocument() throws SAXException {
//		System.out.println("\nXML Parser: starting to process document: " + inputFile);
//		System.out.println("-----------------------------------------------------");
	}

	/**
	 * Called by the parser when it encounters any start element tag.
	 */
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
		
//		System.out.println("\tStart of element: " + elementName);
		
		if (elementName.equals("CookBook")) {
			cookBook = new CookBook(attributes.getValue(0));
		} else if (elementName.equals("recipe")) {
			recipe = new Recipe();
			recipe.setID(attributes.getValue(0));
		} else if (elementName.equals("title")) {
			currentElement = ProcessingElement.TITLE;
		} else if (elementName.equals("people")) {
			currentElement = ProcessingElement.PEOPLE;
		} else if (elementName.equals("time")) {
			currentElement = ProcessingElement.TIME;
		} else if (elementName.equals("chef")) {
			currentElement = ProcessingElement.CHEF;
		} else if (elementName.equals("description")) {
			currentElement = ProcessingElement.DESCRIPTION;
		}

	}

	/**
	 * Called by the parser when it encounters characters in the main body of an
	 * element.
	 */
	public void characters(char[] ch, int start, int length) throws SAXException {		
		String elementValue = new String(ch,start,length);
		switch (currentElement) {
		
		case TITLE:
			recipe.setTitle(elementValue);
//			System.out.println("\tValue of element " + currentElement + ": " + recipe.getTitle());
			break;
		case PEOPLE:
			recipe.setPeople(elementValue);
//			System.out.println("\tValue of element " + currentElement + ": " + recipe.getPeople());			
			break;
		case TIME:
			recipe.setTime(elementValue);
//			System.out.println("\tValue of element " + currentElement + ": " + recipe.getTime());			
			break;
		case CHEF:
			recipe.setChef(elementValue);
//			System.out.println("\tValue of element " + currentElement + ": " + recipe.getChef());			
			break;
		case DESCRIPTION:
			recipe.setDescription(elementValue);
//			System.out.println("\tValue of element " + currentElement + ": " + recipe.getDescription());			
			break;
		default:
			break;
		}
	}

	/**
	 * Called by the parser when it encounters any end element tag.
	 */
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}
//		System.out.println("\tEnd of element: " + elementName);

		// finished adding stuff to current VideoFile, so add video to videoList
		if (elementName.equals("recipe")) {
			cookBook.addRecipe(recipe);
		}
		// finished adding content from various sub-elements of video
		else if (elementName.equals("title")) {
			currentElement = ProcessingElement.NONE;
		} else if (elementName.equals("people")) {
			currentElement = ProcessingElement.NONE;
		} else if (elementName.equals("time")) {
			currentElement = ProcessingElement.NONE;
		} else if (elementName.equals("chef")) {
			currentElement = ProcessingElement.NONE;
		} else if (elementName.equals("description")) {
			currentElement = ProcessingElement.NONE;
		}
		
	}

	/**
	 * Called by the parser when it encounters the end of the XML file.
	 */
	public void endDocument() throws SAXException {
//		System.out.println("XML Parser: finished processing document: " + inputFile);
//		System.out.println("-----------------------------------------------------");
	}


	public static void main(String[] args) {
		DefaultHandler handler = new XMLReader();
	}

}