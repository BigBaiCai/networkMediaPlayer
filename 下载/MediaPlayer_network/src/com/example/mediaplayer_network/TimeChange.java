package com.example.mediaplayer_network;

import java.util.List;

import android.R.integer;
import android.widget.SeekBar;

public class TimeChange {
	private NetWorkAudioPlayer netWorkAudioPlayer;
	private SeekBar seekBar;
	private int part;
	public void setSeekBar(SeekBar seekBar){
		this.seekBar = seekBar;
	}
	public void setNetWorkAudioPlayer(NetWorkAudioPlayer netWorkAudioPlayer){
		this.netWorkAudioPlayer=netWorkAudioPlayer;
	}
	public void setPart(int part){
		this.part=part;
	}
	public int getPart(){
		return part;
	}
	public void timeChange(List<Time> list) {
		
		int seekBarMax = seekBar.getMax();
		int currentTime;
		int totalTime = netWorkAudioPlayer.getDuration();
		switch (part) {
		case 1:
			netWorkAudioPlayer.seekTo(list.get(0).getStartTime());
			currentTime = netWorkAudioPlayer.getCurrentPosition();
			if (currentTime == list.get(0).getEndTime()) {
				netWorkAudioPlayer.stop();
			}
			seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));
			break;
		case 2:
			netWorkAudioPlayer.seekTo(list.get(1).getStartTime());
			currentTime = netWorkAudioPlayer.getCurrentPosition();
			if (currentTime == list.get(1).getEndTime()) {
				netWorkAudioPlayer.stop();
			}
			seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));

			break;
		case 3:
			netWorkAudioPlayer.seekTo(list.get(2).getStartTime());
			currentTime = netWorkAudioPlayer.getCurrentPosition();
			if (currentTime == list.get(2).getEndTime()) {
				netWorkAudioPlayer.stop();
			}
			seekBar.setProgress((int) (seekBarMax * (float) currentTime / totalTime));
			break;
		}
	}

}

class Time{
	private int startTime,endTime;
	
	public Time(int startTime,int endTime) {
		// TODO Auto-generated constructor stub
		this.startTime=startTime;
		this.endTime=endTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	
}