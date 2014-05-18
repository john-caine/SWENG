package eCook;

import graphicshandler.GraphicsHandler;
import imagehandler.ImageHandler;

import java.util.ArrayList;

import audiohandler.AudioHandler;
import texthandler.TextHandler;
import videohandler.VideoPlayerHandler;

public class HandlerCollection {
	
	private ArrayList<TextHandler> textHandlerList;
	private ArrayList<ImageHandler> imageHandlerList;
	private ArrayList<GraphicsHandler> graphicsHandlerList;
	private ArrayList<VideoPlayerHandler> videoHandlerList;
	private ArrayList<AudioHandler> audioHandlerList;

	public HandlerCollection(){
		textHandlerList = new ArrayList<TextHandler>();
		imageHandlerList = new ArrayList<ImageHandler>();
		graphicsHandlerList = new ArrayList<GraphicsHandler>();
		videoHandlerList = new ArrayList<VideoPlayerHandler>();
		audioHandlerList = new ArrayList<AudioHandler>();
		
	}
	
	public void addTextHandler(TextHandler textHandler){
		textHandlerList.add(textHandler);
	}
	
	public ArrayList<TextHandler> getTextHandlerList(){
		return textHandlerList;
	}
	
	public void addImageHandler(ImageHandler imageHandler){
		imageHandlerList.add(imageHandler);
	}
	
	public ArrayList<ImageHandler> getImageHandlerList(){
		return imageHandlerList;
	}
	
	public void addGraphicsHandler(GraphicsHandler graphicsHandler){
		graphicsHandlerList.add(graphicsHandler);
	}
	
	public ArrayList<GraphicsHandler> getGraphicsHandlerList(){
		return graphicsHandlerList;
	}
	
	public void addVideoPlayerHandler(VideoPlayerHandler videoPlayerHandler){
		videoHandlerList.add(videoPlayerHandler);
	}
	
	public ArrayList<VideoPlayerHandler> getVideoHandlerList(){
		return videoHandlerList;
	}
	
	public void addAudioHandler(AudioHandler audioHandler){
		audioHandlerList.add(audioHandler);
	}
	
	public ArrayList<AudioHandler> getAudioHandlerList(){
		return audioHandlerList;
	}

}
