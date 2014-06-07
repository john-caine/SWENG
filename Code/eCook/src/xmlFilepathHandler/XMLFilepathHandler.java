package xmlFilepathHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import xmlparser.XMLReader;

public class XMLFilepathHandler {

	private File filepath;
	private String title;
	private XMLReader reader;
	
	public XMLFilepathHandler() {
	}
	
	public XMLReader updateFilepaths(XMLReader reader) {
		this.reader = reader;
		// Set some default strings
		title = reader.getRecipe().getInfo().getTitle();
		URL defaultDirectory = getClass().getResource("/defaultRecipes_new");
		filepath = new File(defaultDirectory.getPath());
		// Loop through the slideshow updating elements filepaths
		loopThroughMedia();
		return this.reader;
	}

	private void loopThroughMedia() {
		// Loop through each slide in turn
		for (int i = 0; i < reader.getRecipe().getNumberOfSlidesIncBranchSlides(); i++) {
			// Image paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getImages().size(); j++) {
				reader.getRecipe().getSlide(i).getContent().getImages().get(j).setURLName(updateFilepath(reader.getRecipe().getSlide(i).getContent().getImages().get(j).getUrlName()));
			}
			// Video paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getVideos().size(); j++) {
				reader.getRecipe().getSlide(i).getContent().getVideos().get(j).setURLName(updateFilepath(reader.getRecipe().getSlide(i).getContent().getVideos().get(j).getUrlName()));
			}
			// Audio paths
			for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getAudios().size(); j++) {
				reader.getRecipe().getSlide(i).getContent().getAudios().get(j).setURLName(updateFilepath(reader.getRecipe().getSlide(i).getContent().getAudios().get(j).getUrlName()));
			}
		}
	}

	private String updateFilepath(String mediaAddress) {
		if (mediaAddress.contains("file:/")) {
			// Remove from beginning of string
			mediaAddress = mediaAddress.replace("file:/", "");
		}
		Boolean exists = true;
		/*
		 * Relative filepaths
		 * If the file path begins with the title of the XML slideshow
		 * we need to complete the path and turn it into a full path
		 * for the users machine upon loading the slideshow
		 * 
		 * Note: ONLY this type of relative filepath will actually work
		 * 		 - Not all can be accounted for reasonably
		 * 
		 */
		if (mediaAddress.startsWith(title)) {
			StringBuilder tempURL = new StringBuilder();
			tempURL.append(filepath);
			tempURL.append("\\");
			tempURL.append(mediaAddress);
			mediaAddress = tempURL.toString();
			System.out.println(mediaAddress);
		}
		else if (mediaAddress.startsWith("file:../")) {
			mediaAddress = mediaAddress.replace("file:../", "");
			StringBuilder tempURL = new StringBuilder();
			tempURL.append(System.getProperty("user.dir"));
			tempURL.append("\\..\\");
			tempURL.append(mediaAddress);
			mediaAddress = tempURL.toString();
			System.out.println(mediaAddress);
		}
		/*
		 * Existance of files
		 * If the updated or provided filepath does not exist on the local
		 * machine then try treating it as a URL
		 * 
		 * Firstly we will determine if the content has already been downloaded
		 * and a new filepath is only required
		 * 
		 * If it is not a valid URL then we can catch the exception and
		 * assume the file does not exist anywhere reasonable
		 * 
		 */
		if (!(new File(mediaAddress).exists())) {
			// Get the individual filename first and see if it is a filename
			String mediaElementName = new File(mediaAddress).getName();
			if (mediaElementName.contains(".")) {
				/*
				 * At this point we know that a relative working filepath has not been provided
				 * We can check if the file has already been downloaded on a previous running
				 * of eCook though and stored in a relative directory to the XML
				 * 
				 * If it hasn't already been downloaded we can try and download it.
				 * 
				 */
				if (!(new File(filepath + "\\" + title + "\\" + mediaElementName).exists())) {
					try {
						// URL things
						URL url = new URL(mediaAddress);
						URLConnection connection = url.openConnection();
						InputStream inputStream = connection.getInputStream();
						
						// Create a storage directory for the file if it does not exist
						File storage = new File(filepath + "\\" + title + "\\");
						if (!storage.exists()) {
							storage.mkdir();
						}
						
						// This is where the file will be saved
						FileOutputStream fileOutputStream = new FileOutputStream(filepath + "\\" + title + "\\" + mediaElementName);
						
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
						exists = false;
					} catch (FileNotFoundException e) {
						exists = false;
					} catch (IOException e) {
						// If we have an IO exception then if the file exists delete it
						// It may be corrupt!
						if (new File(filepath + "\\" + title + "\\" + mediaElementName).exists()) {
							// Delete the file because there has been an exception
							new File(filepath + "\\" + title + "\\" + mediaElementName).delete();
						}
						exists = false;
					}
				}
				else {
					// We already have a valid file, no need to download again
					// Determine the correct address for the file on the local machine
					mediaAddress = filepath + "\\" + title + "\\" + mediaElementName;
				}
			}
		}
		if (exists) {
			// Convert the filepath to something JavaFX understands
			mediaAddress = (new File(mediaAddress)).toURI().toASCIIString();
			return mediaAddress;
		}
		else {
			// Offline or content missing
			return null;
		}
	}
}
