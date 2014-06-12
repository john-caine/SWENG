/*
 * Programmers: James Oatley
 * Date: 08/06/2014
 * Description: Is able to verify both local and HTTP URLs to test if they are valid, and can retrieve media
 * 				if the URL is over HTTP. If media is downloaded, then the paths on the XML files are updated
 * 				to point at the local copies of the media.
 */

package xmlfilepathhandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import eCook.eCook;
import xmlparser.Recipe;
import xmlparser.XMLReader;

public class XMLFilepathHandler {

	private File filepath;
	private String title;
	protected Boolean broken;
	protected Boolean existsLocally;
	private Logger logger;
	
	/**
	 * Constructs a file path handler for XML files. Sets the file path ready, and creates a logger
	 */
	public XMLFilepathHandler() {
		// Create a new logger instance with the package and class name
		logger = Logger.getLogger(eCook.class.getName());
		
		broken = false;
		existsLocally = true;
		filepath = new File(System.getenv("localappdata") + "/eCook/Recipes");
	}
	
	/**
	 * Update references to media elements in reader object
	 * @param reader An XML reader object for the recipes to update
	 * @return reader an XML reader object with the recipes updated
	 */
	public XMLReader setMediaPaths(XMLReader reader) {
		// Get the title from the recipe, by convention this is the filename for content
		title = reader.getRecipe().getInfo().getTitle();
		
		// Loop through the slideshow updating elements filepaths in the reader
		loopThroughMedia(reader, false);
		return reader;
	}
	
	/**
	 * This method will check that all media paths EXIST and WORK (including URLS)
	 * It will NOT download any data.
	 * @param filename The filename of the recipe to check the URLs of
	 * @return existsLocally True if the URLs are valid, otherwise false
	 */
	public Boolean checkMediaPathsExistOffline(String filename) {
		XMLReader reader = new XMLReader(filepath + "/" + filename);
		title = reader.getRecipe().getInfo().getTitle();
		loopThroughMedia(reader, true);
		return existsLocally;
	}
	
	/**
	 * This method will return a boolean specifying if the media paths are all OK.
	 * @return broken Boolean for if the paths are broken or not
	 */
	public Boolean mediaPathsAreBroken() {
		return broken;
	}
	
	/**
	 * Downloads the image, video, and audio media for a recipe.
	 * @param recipe The recipe for which media will be downloaded
	 */
	public void downloadRecipeMedia(Recipe recipe) {
		title = recipe.getInfo().getTitle();
		
		// Iterate over the total number of slides INCLUDING branches
		for (int i = 0; i < recipe.getNumberOfSlidesIncBranchSlides(); i++) {
			// Image paths
			for (int j = 0; j < recipe.getSlide(i).getContent().getImages().size(); j++) {
				download(recipe.getSlide(i).getContent().getImages().get(j).getUrlName());
			}
			// Video paths
			for (int j = 0; j < recipe.getSlide(i).getContent().getVideos().size(); j++) {
				download(recipe.getSlide(i).getContent().getVideos().get(j).getUrlName());
			}
			// Audio paths
			for (int j = 0; j < recipe.getSlide(i).getContent().getAudios().size(); j++) {
				download(recipe.getSlide(i).getContent().getAudios().get(j).getUrlName());
			}
		}
	}
	
	/**
	 * Method to download media from the given URL provided it is accessible
	 * @param mediaAddress Address of the media to download
	 */
	private void download(String mediaAddress) {
		Boolean validAddress;
		try {
			// Try and test the HTTP connection and look for a valid response
			HttpURLConnection.setFollowRedirects(false);
			HttpURLConnection con = (HttpURLConnection) new URL(mediaAddress).openConnection();
			con.setRequestMethod("HEAD");
			validAddress = (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			validAddress = false;
		}
		// If we have a valid online address...
		if (validAddress) {
			// Find the name of the media file
			String mediaElementName = new File(mediaAddress).getName();
			
			// If we have a file...
			if (mediaElementName.contains(".")) {
				// Check whether we have the local content for this file already
				if (!(new File(filepath + "/" + title + "/" + mediaElementName).exists())) {
					try {
						// URL things
						URL url = new URL(mediaAddress);
						URLConnection connection = url.openConnection();
						InputStream inputStream = connection.getInputStream();
						
						// Create a storage directory for the file if it does not exist
						File storage = new File(filepath + "/" + title + "/");
						if (!storage.exists()) {
							storage.mkdir();
						}
						
						// This is where the file will be saved
						FileOutputStream fileOutputStream = new FileOutputStream(filepath + "/" + title + "/" + mediaElementName);
						
						// Define a new buffer to write data to
						byte[] buffer = new byte[512];
						int length;
						while (true) {
							// Length of data left to be read
							length = inputStream.read(buffer);
							if (length == -1) {
								// Data read
								break;
							}
							// Write to the output media file
							fileOutputStream.write(buffer, 0, length);
						}
						// Close things up
						inputStream.close();
						fileOutputStream.flush();
						fileOutputStream.close();
					} catch (MalformedURLException e) {
						// Error currently picked up when user runs slideshow
					} catch (FileNotFoundException e) {
					} catch (IOException e) {
						// If we have an IO exception then if the file exists delete it
						// It may be corrupt!
						if (new File(filepath + "/" + title + "/" + mediaElementName).exists()) {
							// Delete the file because there has been an exception
							new File(filepath + "/" + title + "/" + mediaElementName).delete();
						}
					}
				}
			}
		}
	}
	
	/**
	 * Loop through media and update filepaths
	 * @param reader An XML reader object for the required recipe
	 * @param localCheck Boolean for whether to update local paths or not
	 */
	private void loopThroughMedia(XMLReader reader, Boolean localCheck) {
		existsLocally = true;
		broken = false;
		// Loop through each slide in turn
		for (int i = 0; i < reader.getRecipe().getNumberOfSlidesIncBranchSlides(); i++) {
			// Image paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getImages().size(); j++) {
				if (!broken) {
					reader.getRecipe().getSlide(i).getContent().getImages().get(j).setURLName(handleMediaPathsFor(reader.getRecipe().getSlide(i).getContent().getImages().get(j).getUrlName(), localCheck));
				}
			}
			// Video paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getVideos().size(); j++) {
				if (!broken) {
					reader.getRecipe().getSlide(i).getContent().getVideos().get(j).setURLName(handleMediaPathsFor(reader.getRecipe().getSlide(i).getContent().getVideos().get(j).getUrlName(), localCheck));
				}
			}
			// Audio paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getAudios().size(); j++) {
				if (!broken) {
					reader.getRecipe().getSlide(i).getContent().getAudios().get(j).setURLName(handleMediaPathsFor(reader.getRecipe().getSlide(i).getContent().getAudios().get(j).getUrlName(), localCheck));
				}
			}
		}
	}
	
	/**
	 * This method will be called on all media elements when a recipe is opened
	 * It will update all references within the recipe file to absolute paths
	 * 1)	If the content exists locally in the <title> named folder, get absolute local path
	 * 2)	If the content is specified as online but has been downloaded locally, get absolute local path
	 * 3)	If the content is online then check that the file exists online
	 * 4)	If none of these criteria are met then return "null"
	 * 
	 * @param mediaAddress Address of the media to deal with the path for.
	 * @param localCheck Boolean for whether to check local file paths or not.
	 * @return mediaAddress or null The new address of tye media, unless the checks failed then null.
	 */
	private String handleMediaPathsFor(String mediaAddress, Boolean localCheck) {
		if (localCheck && !existsLocally) {
			// We're doing a local filepath check but some files already do not exist locally
			return mediaAddress;
		} else {
			// Generate absolute paths for local content
			if (mediaAddress.startsWith(title)) {
				StringBuilder tempURL = new StringBuilder();
				tempURL.append(filepath);
				tempURL.append("/");
				tempURL.append(mediaAddress);
				mediaAddress = tempURL.toString();
				// Does the filepath exist?
				// If not we are dealing with online content or an invalid link to content/broken content
				File potentialWinner = new File(mediaAddress);
				if (potentialWinner.exists()) {
					if (!localCheck) {
						// Make sure the mediaAddress meets the correct standards
						mediaAddress = potentialWinner.toURI().toASCIIString();
					}
					// We're doing a local filepath check but some files already do not exist locally
					return mediaAddress;
				}
			}
			// If we have local content for the online links we can use this content
			// Determine what the path should be for the local version of the content and check whether
			// any content exists at this location
			String mediaElementName = new File(mediaAddress).getName();
			if (mediaElementName.contains(".")) {
				File potentialWinner = new File(filepath + "/" + title + "/" + mediaElementName);
				if (potentialWinner.exists()) {
					if (!localCheck) {
						// Make sure the mediaAddress meets the correct standards
						mediaAddress = filepath + "/" + title + "/" + mediaElementName;
						mediaAddress = (new File(mediaAddress)).toURI().toASCIIString();
					}
					return mediaAddress;
				}
			}
			if (localCheck) {
				// If we've reached this stage then some content does not exist locally
				existsLocally = false;
				// System.out.println("Address does not exist locally: " + mediaAddress);
				return mediaAddress;
			}
			else {
				// If we've got to this stage we're dealing with a URL or invalid local path
				// We need to check if the URL contains valid content
				try {
					HttpURLConnection.setFollowRedirects(false);
					HttpURLConnection con = (HttpURLConnection) new URL(mediaAddress).openConnection();
					con.setRequestMethod("HEAD");
					broken = !(con.getResponseCode() == HttpURLConnection.HTTP_OK);
					existsLocally = false;
				}
				catch (Exception e) {
					logger.log(Level.WARNING, "file broken at: " + mediaAddress);
					broken = true;
				}
				// If the media file exists online it is hopefully OK so re-return the online address
				if (!broken) {
					//mediaAddress = (new File(mediaAddress)).toURI().toASCIIString();
					return mediaAddress;
				}
				else {
					logger.log(Level.WARNING, "Broken at: " + mediaAddress);
					broken = true;
					// eCook will not function correctly with this filepath
					return null;
				}
			}
		}
	}
}
