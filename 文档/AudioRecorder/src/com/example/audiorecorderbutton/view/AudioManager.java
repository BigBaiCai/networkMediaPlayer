package com.example.audiorecorderbutton.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import android.R.integer;
import android.media.MediaRecorder;

public class AudioManager {
	private MediaRecorder mediaRecorder;
	private String mdir;//存放的目录文件夹，外部存储的位置
	private String currentFilePath;
	private boolean isPrepared;
	private static AudioManager instance;
	public AudioManager(String dir){
		mdir=dir;
	}
	//接口！告诉button,prepare完毕！回调准备完毕
	public interface AudioStateListener{
		void wellPrepared();
	}
	
	public AudioStateListener listener;
	public void setOnAudioStateListener(AudioStateListener listener){
		this.listener = listener;
	}
	public static AudioManager getInstance(String dir){
		if(instance==null){
			//同步
			synchronized (AudioManager.class) {
				if(instance==null){
					instance = new AudioManager(dir);
				}
			}
		}
		return instance;
	}
	//准备
	public void prepareAudio(){
		try {
			isPrepared =false;
			File dir = new File(mdir);
			if(!dir.exists())
				dir.mkdirs();//若文件夹不存在，则创建一个
			String fileName =generateFileName();
			File file = new File(dir,fileName);
			currentFilePath=file.getAbsolutePath();
			mediaRecorder = new MediaRecorder();
			//设置输出文件
			mediaRecorder.setOutputFile(file.getAbsolutePath());
			//设置MediaRecorder的音频源为麦克风
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//设置音频格式
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			//设置音频的编码为amr
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.prepare();
			mediaRecorder.start();
			//准备结束
			isPrepared = true;
			if(listener!=null){
				listener.wellPrepared();
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//随机生成文件的名称
	private String generateFileName() {
		// TODO Auto-generated method stub
		
		return UUID.randomUUID().toString()+".amr";
	}
	//音量等级
	public int getVoiceLevel(int maxLevel){
		if(isPrepared){
			try {
				//mediaRecorder.getMaxAmplitude()振幅 在0-32767
				return maxLevel*mediaRecorder.getMaxAmplitude()/32768+1;//即1-7
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
		
	}
	//释放
	public void release(){
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder=null;
	}
	//取消
	public void cancel(){
		release();
		if(currentFilePath!=null){
			File file = new File(currentFilePath);
			file.delete();
			currentFilePath=null;
		}
	}
	public String getCurrentFilePath() {
		// TODO Auto-generated method stub
		return currentFilePath;
	}
	
}
