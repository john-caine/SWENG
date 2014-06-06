package xmlFilepathHandler;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import xmlparser.XMLReader;

public class XMLFilepathHandler {

	private String filename;
	private String filepath;
	private String title;
	private XMLReader reader;
	
	public XMLFilepathHandler() {
	}
	
	public XMLReader updateFilepaths(XMLReader reader) {
		this.reader = reader;
		// Set some default strings
		title = reader.getRecipe().getInfo().getTitle();
		filename = reader.getRecipe().getFileName();
		filepath = System.getProperty("user.dir") + "\\defaultRecipes_new\\";
		// Loop through the slideshow updating elements filepaths
		loopThroughMedia();
		return this.reader;
	}

	private void loopThroughMedia() {
		// Loop through each slide in turn
				// For each slide get the number of object X elements and update their content
				// to match users native resolution
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
					for (int j = 0; j < reader.getRecipe().getSlide(i).getContent().getVideos().size(); j++) {
						reader.getRecipe().getSlide(i).getContent().getAudios().get(j).setURLName(updateFilepath(reader.getRecipe().getSlide(i).getContent().getAudios().get(j).getUrlName()));
					}
				}
	}
	
	private String updateFilepath(String mediaAddress) {
		// If we have a relative filepath then make it non-relative
		if (mediaAddress.startsWith(title)) {
			StringBuilder tempURL = new StringBuilder();
			tempURL.append(filepath);
			tempURL.append(mediaAddress);
			return tempURL.toString();
		}
		/*
		 * TODO
		 */
		// 		obtain the name of the media file
		//		download the media file and place in filepath + title + "//" + title.xml
		//		return the string where the file was placed
		
		
		else {
			return mediaAddress;
		}
	}
	
	private void obtainMedia(String mediaAddress, String mediaDirectory) {
		String filename = null;
		// Obtain the filename
/*
 * TODO
 */
		try {
			URL url = new URL(mediaAddress);
			URLConnection connection = url.openConnection();
			InputStream inputStream = connection.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(mediaDirectory + "/" + filename);
			byte[] buffer = new byte[8192];

			while (true) {
				int length = inputStream.read(buffer);
				if (length == -1) {
					break;
				}
				fileOutputStream.write(buffer, 0, length);
			}

			inputStream.close();
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (MalformedURLException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}
