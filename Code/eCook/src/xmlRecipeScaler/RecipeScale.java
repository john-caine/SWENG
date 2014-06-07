package xmlRecipeScaler;

import java.awt.GraphicsEnvironment;
import xmlparser.Recipe;

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
 * 					v0.3 (24/05/14)	- Rough code added for entire scaling of slideshow elements
 * 					v1.0 (25/05/14)	- Major bug fixes and re-factoring of code
 * 					v1.1 (07/06/14) - Mathematical bug found and fixed, background "stretch" applied
 */
public class RecipeScale {
	// Holding variables
	private double tempX;
	private double tempY;
	private double tempFontSize;
	// Screen size variables
	private int xmlWidth;
	private int xmlHeight;
	private int userWidth;
	private int userHeight;
	// Shift variables
	private double xShift;
	private double yShift;
	// Scaling variables
	private double scaleFactor;
	// Fix resolution
	private boolean test;

	public RecipeScale() {
		test = false;
	}
	
	public void setTest(Integer x, Integer y) {
		test = true;
		userWidth = x;
		userHeight = y;
	}

	public Recipe scaleRecipe(Recipe recipe) {
		if (!test) {
			// Figure out user screen size values
			userWidth = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
			userHeight = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
		}
		xmlWidth = recipe.getInfo().getWidth();
		xmlHeight = recipe.getInfo().getHeight();
		// If scaling is required
		if ((userWidth != xmlWidth) || (userHeight != xmlHeight)) {
			// Figure out scaling values to scale content to screen bounds
			// We need to figure out how to fit all of the content on the screen while retaining correct aspect ratios
			if ((double)userWidth/(double)xmlWidth < (double)userHeight/(double)xmlHeight) {
				// Scale the content to fit on by width
				scaleFactor = (double)userWidth/(double)xmlWidth;
				// Center the content by height
				xShift = 0;
				yShift = (userHeight-xmlHeight*scaleFactor)/2;
			} else {
				scaleFactor = (double)userHeight/(double)xmlHeight;
				// Center the content by width
				xShift = (userWidth-xmlWidth*scaleFactor)/2;
				yShift = 0;
			}
			// Shift and scale Recipe elements
			scaleRecipeElements(recipe);
		}
		return recipe;
	}

	/*
	 * Scaling method:
	 * Scales Recipe elements to user screen size
	 * - James and Pakruti
	 */
	private void scaleRecipeElements(Recipe recipe) {
		// Loop through each slide in turn
		// For each slide get the number of object X elements and update their content
		// to match users native resolution
		for (int i = 0; i < recipe.getNumberOfSlidesIncBranchSlides(); i++) {
			// Text scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getTexts().size(); j++) {
				// Get text start values
				tempX = recipe.getSlide(i).getContent().getTexts().get(j).getXStart();
				tempY = recipe.getSlide(i).getContent().getTexts().get(j).getYStart();
				// Scale start values
				recipe.getSlide(i).getContent().getTexts().get(j).setXStart(String.valueOf((int)(Math.floor(tempX*scaleFactor+xShift))));
				recipe.getSlide(i).getContent().getTexts().get(j).setYStart(String.valueOf((int)(Math.floor(tempY*scaleFactor+yShift))));
				// Get text end values
				tempX = recipe.getSlide(i).getContent().getTexts().get(j).getXEnd();
				tempY = recipe.getSlide(i).getContent().getTexts().get(j).getYEnd();
				//Scale end values
				recipe.getSlide(i).getContent().getTexts().get(j).setXEnd(String.valueOf((int)(Math.floor(tempX*scaleFactor+xShift))));
				recipe.getSlide(i).getContent().getTexts().get(j).setYEnd(String.valueOf((int)(Math.floor(tempY*scaleFactor+yShift))));
				// Font size is "implied", may equal null
				if (recipe.getSlide(i).getContent().getTexts().get(j).getFontSize() != null) {
					// Get font size
					tempFontSize = recipe.getSlide(i).getContent().getTexts().get(j).getFontSize();
					// Scale font size
					recipe.getSlide(i).getContent().getTexts().get(j).setFontSize(String.valueOf((int)(Math.floor(tempFontSize*scaleFactor))));
				}
			}
			// Shape scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getShapes().size(); j++) {
				// Get shape width and height
				tempX = recipe.getSlide(i).getContent().getShapes().get(j).getWidth();
				tempY = recipe.getSlide(i).getContent().getShapes().get(j).getHeight();
				// Scale width and height values
				recipe.getSlide(i).getContent().getShapes().get(j).setWidth(String.valueOf((int)(Math.floor(tempX*scaleFactor))));
				recipe.getSlide(i).getContent().getShapes().get(j).setHeight(String.valueOf((int)(Math.floor(tempY*scaleFactor))));
				// Find total number of points in shape j and loop through them
				for (int k = 0; k < recipe.getSlide(i).getContent().getShapes().get(j).getPoints().size(); k++) {
					// Get xy point values
					tempX = recipe.getSlide(i).getContent().getShapes().get(j).getPoints().get(k).getX();
					tempY = recipe.getSlide(i).getContent().getShapes().get(j).getPoints().get(k).getY();
					// Scale xy point values
					recipe.getSlide(i).getContent().getShapes().get(j).getPoints().get(k).setX(String.valueOf((int)(Math.floor(tempX*scaleFactor+xShift))));
					recipe.getSlide(i).getContent().getShapes().get(j).getPoints().get(k).setY(String.valueOf((int)(Math.floor(tempY*scaleFactor+yShift))));	
				}
			}
			// Image scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getImages().size(); j++) {
				// Image width and height are "implied", may equal null
				// Firstly check if we are dealing with a background image
				if ((recipe.getSlide(i).getContent().getImages().get(j).getHeight() == xmlHeight) && (recipe.getSlide(i).getContent().getImages().get(j).getWidth() == xmlWidth) && (recipe.getSlide(i).getContent().getImages().get(j).getLayer() == 0) && (recipe.getSlide(i).getContent().getImages().get(j).getXStart() == 0) && (recipe.getSlide(i).getContent().getImages().get(j).getYStart() == 0)) {
					// If background image "stretch" to fill in both directions
					recipe.getSlide(i).getContent().getImages().get(j).setWidth(String.valueOf((int)(Math.floor(userWidth))));
					recipe.getSlide(i).getContent().getImages().get(j).setHeight(String.valueOf((int)(Math.floor(userHeight))));
				}
				// If we're not dealing with a background image continue as normal
				else {
					if (recipe.getSlide(i).getContent().getImages().get(j).getWidth() != null) {
						// Get image width
						tempX = recipe.getSlide(i).getContent().getImages().get(j).getWidth();
						// Scale image width
						recipe.getSlide(i).getContent().getImages().get(j).setWidth(String.valueOf((int)(Math.floor(tempX*scaleFactor))));
					}
					if (recipe.getSlide(i).getContent().getImages().get(j).getHeight() != null) {
						// Get image height
						tempY = recipe.getSlide(i).getContent().getImages().get(j).getHeight();
						// Scale image height
						recipe.getSlide(i).getContent().getImages().get(j).setHeight(String.valueOf((int)(Math.floor(tempY*scaleFactor))));
					}
					// Get image start values
					tempX = recipe.getSlide(i).getContent().getImages().get(j).getXStart();
					tempY = recipe.getSlide(i).getContent().getImages().get(j).getYStart();
					// Scale start values
					recipe.getSlide(i).getContent().getImages().get(j).setXStart(String.valueOf((int)(Math.floor(tempX*scaleFactor+xShift))));
					recipe.getSlide(i).getContent().getImages().get(j).setYStart(String.valueOf((int)(Math.floor(tempY*scaleFactor+yShift))));
				}
			}
			// Video scaling
			for (int j = 0; j < recipe.getSlide(i).getContent().getVideos().size(); j++) {
				// Video width and height are "implied", may equal null
				if (recipe.getSlide(i).getContent().getVideos().get(j).getWidth() != null) {
					// Get image width
					tempX = recipe.getSlide(i).getContent().getVideos().get(j).getWidth();
					// Scale image width
					recipe.getSlide(i).getContent().getVideos().get(j).setWidth(String.valueOf((int)(Math.floor(tempX*scaleFactor))));
				}
				if (recipe.getSlide(i).getContent().getVideos().get(j).getHeight() != null) {
					// Get image height
					tempY = recipe.getSlide(i).getContent().getVideos().get(j).getHeight();
					// Scale image height
					recipe.getSlide(i).getContent().getVideos().get(j).setHeight(String.valueOf((int)(Math.floor(tempY*scaleFactor))));
				}
				// Get video start values
				tempX = recipe.getSlide(i).getContent().getVideos().get(j).getYStart();
				tempY = recipe.getSlide(i).getContent().getVideos().get(j).getXStart();
				// Scale start values
				recipe.getSlide(i).getContent().getVideos().get(j).setXStart(String.valueOf((int)(Math.floor(tempX*scaleFactor+xShift))));
				recipe.getSlide(i).getContent().getVideos().get(j).setYStart(String.valueOf((int)(Math.floor(tempY*scaleFactor+yShift))));
			}
		}
	}
}