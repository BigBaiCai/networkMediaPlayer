package com.example.mediaplayer_network;

public interface IAudioPlayer {
	public boolean isPrepared();
	public boolean isPaused();
	public void playPause();
	public void playPrevious();
	public void playNext();
	public void playPrepared();
	public void play();
	
}
