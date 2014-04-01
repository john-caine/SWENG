/* Title: XMLReader
 * 
 * Programmers: Ankita, Max
 * 
 * Date Created: 14/02/14
 * 
 * Description: A simple XML reader which extracts strings from XML elements,
 * 				creates Recipe instances as appropriate, writing element values to the correct attributes.
 * 				Once all 'Recipes' have been created, an instance of 'Cookbook' is created, which all recipes are stored in.
 * 
 * Notes: Print statements within methods have been commented out but remain for future debugging purposes.
 * 
 * Version History: v1.01 (27/02/14) - Class modified to include functionality to extract 'ImageFileName' fields
 * 					v2.0  (05/03/14) - Class modified to extract all information required as specified in the PWS standard XML file
 * 									 - New recipe, slide and text processing element flags are included to track current scanning position
 * 					v2.1  (27/03/14) - Class modified to meet PWS version 0.9 standards.
 * 									 - See PWS version documentation for details of changes made.
 * 					v2.2  (01/04/14) - Alterations to remove the CookBook creation when reading in recipes
 * 									 - Changed Class constructor to accept a String of the XML filename
 */

package xmlparser;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

// enumerated type for keeping track of where we need to store content
enum ProcessingElement {
	NONE,
	DOCUMENTINFO, INFOAUTHOR, INFOVERSION, INFOTITLE, INFOCOMMENT, INFOWIDTH, INFOHEIGHT,
	DEFAULTS, DEFAULTSBACKGROUNDCOLOR, DEFAULTSFONT, DEFAULTSFONTSIZE, DEFAULTSFONTCOLOR, DEFAULTSFILLCOLOR,
	SLIDE,
	TEXT,
	TEXTTEXTBODY, TEXTTEXTBODYTEXT,
	SHAPE, SHAPEPOINT,
	AUDIO, AUDIOURLNAME, AUDIOSTARTTIME, AUDIOLOOP, AUDIODURATION,
	IMAGE, IMAGEURLNAME, IMAGEXSTART, IMAGEYSTART, IMAGEWIDTH, IMAGEHEIGHT, IMAGEDURATION, IMAGELAYER,
	VIDEO, VIDEOURLNAME, VIDEOXSTART, VIDEOYSTART, VIDEODURATION, VIDEOLAYER, VIDEOSTARTTIME, VIDEOLOOP, VIDEOWIDTH, VIDEOHEIGHT
};

public class XMLReader extends DefaultHandler {
	private Recipe recipe;
	private Slide slide;
	private Content content;
	private Info info;
	private Defaults defaults;
	private TextBody text;
	private TextString textString;
	private Shape shape;
	private Audio audio;
	private Image image;
	private Video video;
	
	private ProcessingElement currentElement = ProcessingElement.NONE;
	private ProcessingElement recipeElement = ProcessingElement.NONE;
	private ProcessingElement slideElement = ProcessingElement.NONE;
	private ProcessingElement textElement = ProcessingElement.NONE;

	public XMLReader(String inputFile) {
		readXMLFile(inputFile);
	}
	
	public Recipe getRecipe() {
		return this.recipe;
	}

	public void readXMLFile(String inputFile) {
		try {
			// use the default parser
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

	// called by the parser when it encounters any start element tag
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if (elementName == null || elementName.isEmpty()) {
			elementName = qName;
		}
		
		/* create classes for each element as required
		 * add attributes directly if present
		 * set recipe element and current element flags
		 */
		if (elementName.equals("slideshow")) {
			recipe = new Recipe();
		}
			
		// set recipe element flags
		else if (elementName.equals("slide")) {
			slide = new Slide();
			slide.setID(attributes.getValue("ID"));
			slide.setDuration(attributes.getValue("duration"));
			slide.setLastSlide(attributes.getValue("lastSlide"));
			content = new Content();
			recipeElement = ProcessingElement.SLIDE;
		}
		else if (elementName.equals("documentinfo")) {
			info = new Info();
			recipeElement = ProcessingElement.DOCUMENTINFO;
		}
		else if (elementName.equals("defaults")) {
			defaults = new Defaults();
			recipeElement = ProcessingElement.DEFAULTS;
		}
		
		/* 
		 * set sub element flags for each recipe element
		 */
		// documentinfo
		if (recipeElement.equals(ProcessingElement.DOCUMENTINFO)) {
			if (elementName.equals("author")) {
				currentElement = ProcessingElement.INFOAUTHOR;
			} else if (elementName.equals("version")) {
				currentElement = ProcessingElement.INFOVERSION;
			} else if (elementName.equals("title")) {
				currentElement = ProcessingElement.INFOTITLE;
			} else if (elementName.equals("comment")) {
				currentElement = ProcessingElement.INFOCOMMENT;
			} else if (elementName.equals("width")) {
				currentElement = ProcessingElement.INFOWIDTH;
			} else if (elementName.equals("height")) {
				currentElement = ProcessingElement.INFOHEIGHT;
			}
		}

		// defaults
		if (recipeElement.equals(ProcessingElement.DEFAULTS)) {
			if (elementName.equals("backgroundcolor")) {
				currentElement = ProcessingElement.DEFAULTSBACKGROUNDCOLOR;
			} else if (elementName.equals("font")) {
				currentElement = ProcessingElement.DEFAULTSFONT;
			} else if (elementName.equals("fontsize")) {
				currentElement = ProcessingElement.DEFAULTSFONTSIZE;
			} else if (elementName.equals("fontcolor")) {
				currentElement = ProcessingElement.DEFAULTSFONTCOLOR;
			} else if (elementName.equals("fillcolor")) {
				currentElement = ProcessingElement.DEFAULTSFILLCOLOR;
			}
		}
		
		// slide
		if (recipeElement.equals(ProcessingElement.SLIDE)) {
			if (elementName.equals("text")) {
				text = new TextBody();
				// check that optional attributes actually have a value before setting
				try {
					text.setXStart(attributes.getValue("xstart"));
					text.setYStart(attributes.getValue("ystart"));
					text.setXEnd(attributes.getValue("xend"));
					text.setYEnd((attributes).getValue("yend"));

					if (!(attributes.getValue("font") == null)) {
						text.setFont(attributes.getValue("font"));
					}
					if (!(attributes.getValue("fontsize") == null)) {
						text.setFontSize(attributes.getValue("fontsize"));
					}
					if (!(attributes.getValue("fontcolor") == null)) {
						text.setFontColor(attributes.getValue("fontcolor"));
					}
					if (!(attributes.getValue("starttime") == null)) {
						text.setStartTime(attributes.getValue("starttime"));
					}
					if (!(attributes.getValue("duration") == null)) {
						text.setDuration(attributes.getValue("duration"));
					}
					if (!(attributes.getValue("layer") == null)) {
						text.setLayer(attributes.getValue("layer"));
					}
				} catch (Exception e) {
					System.out.println("text attribute setting issue");
					e.printStackTrace();
				}
				slideElement = ProcessingElement.TEXT;
			}
			else if (elementName.equals("shape")) {
				shape = new Shape();
				// check that attributes actually have a value before setting
				try {
					shape.setTotalPoints(attributes.getValue("totalpoints"));
					shape.setWidth(attributes.getValue("width"));
					shape.setHeight(attributes.getValue("height"));
					if (!(attributes.getValue("fillcolor") == null)) {
						shape.setFillColor(attributes.getValue("fillcolor"));
					}
					if (!(attributes.getValue("linecolor") == null)) {
						shape.setLineColor(attributes.getValue("linecolor"));
					}
					if (!(attributes.getValue("starttime") == null)) {
						shape.setStartTime(attributes.getValue("starttime"));
					}
					if (!(attributes.getValue("duration") == null)) {
						shape.setDuration(attributes.getValue("duration"));
					}
					if (!(attributes.getValue("layer") == null)) {
						shape.setLayer(attributes.getValue("layer"));
					}
				} catch (Exception e) {
					System.out.println("shape attribute setting issue");
					e.printStackTrace();
				}
				slideElement = ProcessingElement.SHAPE;
			}
			else if (elementName.equals("audio")) {
				audio = new Audio();
				slideElement = ProcessingElement.AUDIO;
			}
			else if (elementName.equals("image")) {
				image = new Image();
				slideElement = ProcessingElement.IMAGE;
			}
			else if (elementName.equals("video")) {
				video = new Video();	
				slideElement = ProcessingElement.VIDEO;
			}
			/* 
			 * now check the slide element and set the current element appropriately
			 */
			// text
			if (slideElement.equals(ProcessingElement.TEXT)) {
				if (elementName.equals("textbody")) {
					textString = new TextString();
					
					// set bold/italic/underline attributes
					try {
						if (attributes.getValue("bold") != null) {
							textString.setBold(attributes.getValue("bold"));
						}
						else {
							textString.setBold("false");
						}
						if (attributes.getValue("italic") != null) {
							textString.setItalic(attributes.getValue("italic"));
						}
						else {
							textString.setItalic("false");
						}
						if (attributes.getValue("underlined") != null) {
							textString.setUnderline(attributes.getValue("underlined"));
						}
						else {
							textString.setUnderline("false");
						}
						if (attributes.getValue("branch") != null) {
							textString.setBranch(attributes.getValue("branch"));
						}
					} catch (Exception e) {
						System.out.println("textString (textBody in XML) attribute setting issue");
						e.printStackTrace();
					}
					
					textElement = ProcessingElement.TEXTTEXTBODY;
				}
				// textBody
				if (textElement.equals(ProcessingElement.TEXTTEXTBODY)) {
					if (elementName.equals("textstring")) {
						currentElement = ProcessingElement.TEXTTEXTBODYTEXT;
					}
				}
			}
			// shape
			else if (slideElement.equals(ProcessingElement.SHAPE)) {
				if (elementName.equals("point")) {
					currentElement = ProcessingElement.SHAPEPOINT;
					int num = 0, x = 0, y = 0;
					if (!(attributes.getValue("num") == null)) {
						num = Integer.valueOf(attributes.getValue("num"));
					}
					if (!(attributes.getValue("x") == null)) {
						x = Integer.valueOf(attributes.getValue("x"));
					}
					if (!(attributes.getValue("y") == null)) {
						y = Integer.valueOf(attributes.getValue("y"));
					}
					Integer[] point = {num, x, y};
					shape.addPoint(point);
				}
			}
			// audio
			if (slideElement.equals(ProcessingElement.AUDIO)) {
				if (elementName.equals("urlname")) {
					currentElement = ProcessingElement.AUDIOURLNAME;
				} else if (elementName.equals("starttime")) {
					currentElement = ProcessingElement.AUDIOSTARTTIME;
				} else if (elementName.equals("loop")) {
					currentElement = ProcessingElement.AUDIOLOOP;
				} else if (elementName.equals("duration")) {
					currentElement = ProcessingElement.AUDIODURATION;
				}
			}
			// image
			if (slideElement.equals(ProcessingElement.IMAGE)) {
				if (elementName.equals("urlname")) {
					currentElement = ProcessingElement.IMAGEURLNAME;
				} else if (elementName.equals("xstart")) {
					currentElement = ProcessingElement.IMAGEXSTART;
				} else if (elementName.equals("ystart")) {
					currentElement = ProcessingElement.IMAGEYSTART;
				} else if (elementName.equals("width")) {
					currentElement = ProcessingElement.IMAGEWIDTH;
				} else if (elementName.equals("height")) {
					currentElement = ProcessingElement.IMAGEHEIGHT;
				} else if (elementName.equals("duration")) {
					currentElement = ProcessingElement.IMAGEDURATION;
				} else if (elementName.equals("layer")) {
					currentElement = ProcessingElement.IMAGELAYER;
				}
			}
			// video
			if (slideElement.equals(ProcessingElement.VIDEO)) {
				if (elementName.equals("urlname")) {
					currentElement = ProcessingElement.VIDEOURLNAME;
				} else if (elementName.equals("xstart")) {
					currentElement = ProcessingElement.VIDEOXSTART;
				} else if (elementName.equals("ystart")) {
					currentElement = ProcessingElement.VIDEOYSTART;
				} else if (elementName.equals("duration")) {
					currentElement = ProcessingElement.VIDEODURATION;
				} else if (elementName.equals("layer")) {
					currentElement = ProcessingElement.VIDEOLAYER;
				} else if (elementName.equals("width")) {
					currentElement = ProcessingElement.VIDEOWIDTH;
				} else if (elementName.equals("height")) {
					currentElement = ProcessingElement.VIDEOHEIGHT;
				} else if (elementName.equals("starttime")) {
					currentElement = ProcessingElement.VIDEOSTARTTIME;
				} else if (elementName.equals("loop")) {
					currentElement = ProcessingElement.VIDEOLOOP;
				}
			}
		}
	}

	// called by the parser when it encounters characters in the main body of an element
	public void characters(char[] ch, int start, int length) throws SAXException {		
		String elementValue = new String(ch,start,length);
		switch (currentElement) {
		
		// Info class
		case INFOAUTHOR:
			info.setAuthor(elementValue);
			break;
		case INFOVERSION:
			info.setVersion(elementValue);
			break;
		case INFOTITLE:
			info.setTitle(elementValue);
			break;
		case INFOCOMMENT:
			info.setComment(elementValue);		
			break;
		case INFOWIDTH:
			info.setWidth(elementValue);			
			break;
		case INFOHEIGHT:
			info.setHeight(elementValue);			
			break;
		
		// Defaults class
		case DEFAULTSBACKGROUNDCOLOR:
			defaults.setBackgroundColor(elementValue);
			break;
		case DEFAULTSFONT:
			defaults.setFont(elementValue);
			break;
		case DEFAULTSFONTSIZE:
			defaults.setFontSize(elementValue);		
			break;
		case DEFAULTSFONTCOLOR:
			defaults.setFontColor(elementValue);			
			break;
		case DEFAULTSFILLCOLOR:
			defaults.setFillColor(elementValue);			
			break;
			
		// TextBody class
		case TEXTTEXTBODYTEXT:
			textString.setText(elementValue);
			break;
				
		// Audio class
		case AUDIOURLNAME:
			audio.setURLName(elementValue);			
			break;
		case AUDIOSTARTTIME:
			audio.setStartTime(elementValue);			
			break;
		case AUDIOLOOP:
			audio.setLoop(elementValue);
			break;
		case AUDIODURATION:
			audio.setDuration(elementValue);
			break;
			
		// Image class
		case IMAGEURLNAME:
			image.setURLName(elementValue);
			break;
		case IMAGEXSTART:
			image.setXStart(elementValue);
			break;
		case IMAGEYSTART:
			image.setYStart(elementValue);		
			break;
		case IMAGEWIDTH:
			image.setWidth(elementValue);		
			break;
		case IMAGEHEIGHT:
			image.setHeight(elementValue);		
			break;
		case IMAGEDURATION:
			image.setDuration(elementValue);			
			break;
		case IMAGELAYER:
			image.setLayer(elementValue);		
			break;
			
		// Video class
		case VIDEOURLNAME:
			video.setURLName(elementValue);
			break;
		case VIDEOXSTART:
			video.setXStart(elementValue);
			break;
		case VIDEOYSTART:
			video.setYStart(elementValue);		
			break;
		case VIDEODURATION:
			video.setDuration(elementValue);		
			break;
		case VIDEOLAYER:
			video.setLayer(elementValue);		
			break;
		case VIDEOWIDTH:
			video.setWidth(elementValue);			
			break;
		case VIDEOHEIGHT:
			video.setHeight(elementValue);
			break;
		case VIDEOSTARTTIME:
			video.setStartTime(elementValue);		
			break;
		case VIDEOLOOP:
			video.setLoop(elementValue);		
			break;

		// catch all case
		default:
			break;
		}
	}

	// called by the parser when it encounters any end element tag
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		// sort out element name if (no) namespace in use
		String elementName = localName;
		if ("".equals(elementName)) {
			elementName = qName;
		}

		/*
		 * this part adds the relevant classes to the containing superclasses
		 * when the parser completes a slide element or recipe element
		 */
		// If textString contains something then add it to the text class
		if (elementName.equals("textstring")) {
			if (!textString.getText().equals("")) {
				text.addTextString(textString);
			}
		}
		// reset the text element when scanner reaches the end of the textbody element
		else if (elementName.equals("textbody")) {
			textElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Text, so add text to content
		else if (elementName.equals("text")) {
			content.addText(text);
			slideElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Shape, so add shape to content
		else if (elementName.equals("shape")) {
			content.addShape(shape);
			slideElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Audio, so add audio to content
		else if (elementName.equals("audio")) {
			content.addAudio(audio);
			slideElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Image, so add image to content
		else if (elementName.equals("image")) {
			content.addImage(image);
			slideElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Video, so add video to content
		else if (elementName.equals("video")) {
			content.addVideo(video);
			slideElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Slide, so add content to slide and slide to recipe
		else if (elementName.equals("slide")) {
			slide.setContent(content);
			recipe.slides.add(slide);
			recipeElement = ProcessingElement.NONE;
		}
		// finished adding stuff to current Recipe, so add recipe to cookbook
		else if (elementName.equals("slideshow")) {
			recipe.info = info;
			recipe.defaults = defaults;
		}
		
		currentElement = ProcessingElement.NONE;
	}

	// called by the parser when it encounters the end of the XML file.
	public void endDocument() throws SAXException {
//		System.out.println("XML Parser: finished processing document: " + inputFile);
	}
}