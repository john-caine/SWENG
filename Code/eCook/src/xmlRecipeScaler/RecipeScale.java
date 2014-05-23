package xmlRecipeScaler;

import java.awt.GraphicsEnvironment;

import javafx.stage.Screen;
import xmlparser.Info;
import xmlparser.Recipe;
import xmlparser.XMLReader;

/* Title: RecipeScaler
 * 
 * Programmers: James, Prakruti
 * 
 * Date Created: 22/05/14
 * 
 * Description: Class to scale all elements of a Recipe to fit screen bounds
 * 
 * Version History: v0.1 (22/05/14) - Created skeleton code for looping through Recipe attributes and
 * 									  obtaining correct attributes to be used in scaling content.
 * 					v0.2 (23/05/14) - Dummy code added for scaling elements from retrieved element attributes
 */
public class RecipeScale {

	// Input information
	private Recipe recipe;
	// Holding variables
	private Integer tempX;
	private Integer tempY;
	private Integer tempFontSize;
	// Screen size variables
	private Integer xmlWidth;
	private Integer xmlHeight;
	private Integer userWidth;
	private Integer userHeight;
	// Shift variables
	private Integer xShift;
	private Integer yShift;
	// Scaling variables
	private double scaleFactor;
	
	/*
	 * Constructor method
	 * Grab the input information
	 * Determine scaling factors
	 * Scale the information
	 * - James and Prakruti
	 */
	public RecipeScale() {

	}
	
	public Recipe scaleRecipe(Recipe recipe, Info info) {
		// Grab the XML reader in preparation for scaling
		this.recipe = recipe;
		// Figure out screen size values
		userWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
		userHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		xmlWidth = info.getWidth();
		xmlHeight = info.getHeight();
		// If scaling is required
		if ((userWidth != xmlWidth) || (userHeight != xmlHeight)) {
			// Center the content on the screen
			xShift = (userWidth - xmlWidth)/2;
			yShift = (userHeight - xmlHeight)/2;
			// Figure out scaling values to scale content to screen bounds
			// We need to figure out how to fit all of the content on the screen while retaining correct aspect ratios
			if (userWidth/xmlWidth > userHeight/xmlHeight) {
				scaleFactor = userWidth/xmlWidth;
			} else {
				scaleFactor = userHeight/xmlHeight;
			}
			// Shift and scale Recipe elements
			scaleRecipeElements();
		}
		return this.recipe;
	}

	/*
	 * Scaling method:
	 * Scales Recipe elements to user screen size
	 * - James and Pakruti
	 */
	private void scaleRecipeElements() {
		// Loop through each slide in turn
		// For each slide get the number of object X elements and update their content
		// to match users native resolution
		for (int i = 0; i < recipe.getNumberOfSlides(); i++) {
			// Text scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getTexts().size(); j++) {
				// Get text start values
				tempX = recipe.getSlide(i).getContent().getTexts().get(j).getXStart();
				tempY = recipe.getSlide(i).getContent().getTexts().get(j).getYStart();
				// Scale start values
				recipe.getSlide(i).getContent().getTexts().get(j).setXStart((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getTexts().get(j).setYStart((tempY+yShift)*scaleFactor);
				// Get text end values
				tempX = recipe.getSlide(i).getContent().getTexts().get(j).getXEnd();
				tempY = recipe.getSlide(i).getContent().getTexts().get(j).getYEnd();
				//Scale end values
				recipe.getSlide(i).getContent().getTexts().get(j).setXEnd((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getTexts().get(j).setYEnd((tempY+yShift)*scaleFactor);
				// Get font size
				tempFontSize = recipe.getSlide(i).getContent().getTexts().get(j).getFontSize();
				// Scale font size
				recipe.getSlide(i).getContent().getTexts().get(j).setFontSize(tempFontSize*scaleFactor);
			}
			// Shape scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getShapes().size(); j++) {
				// Get shape width and height
				tempX = recipe.getSlide(i).getContent().getShapes().get(j).getWidth();
				tempY = recipe.getSlide(i).getContent().getShapes().get(j).getHeight();
				// Scale width and height values
				recipe.getSlide(i).getContent().getShapes().get(j).setWidth((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getShapes().get(j).setWidth((tempY+yShift)*scaleFactor);
				// Get shape start values
				tempX = recipe.getSlide(i).getContent().getShapes().get(j).getXStart();
				tempY = recipe.getSlide(i).getContent().getShapes().get(j).getYStart();
				// Scale start values
				recipe.getSlide(i).getContent().getShapes().get(j).setXStart((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getShapes().get(j).setYStart((tempY+yShift)*scaleFactor);
			}
			// Image scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getImages().size(); j++) {
				// Get image width and height
				tempX = recipe.getSlide(j).getContent().getImages().get(j).getWidth();
				tempY = recipe.getSlide(j).getContent().getImages().get(j).getHeight();
				// Scale width and height values
				recipe.getSlide(i).getContent().getImages().get(j).setWidth((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getImages().get(j).setWidth((tempY+yShift)*scaleFactor);
				// Get image start values
				tempX = recipe.getSlide(i).getContent().getImages().get(j).getXStart();
				tempY = recipe.getSlide(i).getContent().getImages().get(j).getYStart();
				// Scale start values
				recipe.getSlide(i).getContent().getImages().get(j).setXStart((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getImages().get(j).setYStart((tempY+yShift)*scaleFactor);
			}
			// Video scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getVideos().size(); j++) {
				// Get video width and height
				tempX = recipe.getSlide(j).getContent().getVideos().get(j).getWidth();
				tempY = recipe.getSlide(j).getContent().getVideos().get(j).getHeight();
				// Scale width and height values
				recipe.getSlide(i).getContent().getVideos().get(j).setWidth((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getVideos().get(j).setWidth((tempY+yShift)*scaleFactor);
				// Get video start values
				tempX = recipe.getSlide(i).getContent().getVideos().get(j).getYStart();
				tempY = recipe.getSlide(i).getContent().getVideos().get(j).getXStart();
				// Scale start values
				recipe.getSlide(i).getContent().getVideos().get(j).setXStart((tempX+xShift)*scaleFactor);
				recipe.getSlide(i).getContent().getVideos().get(j).setYStart((tempY+yShift)*scaleFactor);
			}
		}
	}
}