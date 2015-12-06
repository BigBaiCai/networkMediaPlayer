package com.example.audiorecorderbutton.view;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import android.R.integer;
import android.media.MediaRecorder;

public class AudioManager {
	private MediaRecorder mediaRecorder;
	private String mdir;//��ŵ�Ŀ¼�ļ��У��ⲿ�洢��λ��
	private String currentFilePath;
	private boolean isPrepared;
	private static AudioManager instance;
	public AudioManager(String dir){
		mdir=dir;
	}
	//�ӿڣ�����button,prepare��ϣ��ص�׼�����
	public interface AudioStateListener{
		void wellPrepared();
	}
	
	public AudioStateListener listener;
	public void setOnAudioStateListener(AudioStateListener listener){
		this.listener = listener;
	}
	public static AudioManager getInstance(String dir){
		if(instance==null){
			//ͬ��
			synchronized (AudioManager.class) {
				if(instance==null){
					instance = new AudioManager(dir);
				}
			}
		}
		return instance;
	}
	//׼��
	public void prepareAudio(){
		try {
			isPrepared =false;
			File dir = new File(mdir);
			if(!dir.exists())
				dir.mkdirs();//���ļ��в����ڣ��򴴽�һ��
			String fileName =generateFileName();
			File file = new File(dir,fileName);
			currentFilePath=file.getAbsolutePath();
			mediaRecorder = new MediaRecorder();
			//��������ļ�
			mediaRecorder.setOutputFile(file.getAbsolutePath());
			//����MediaRecorder����ƵԴΪ��˷�
			mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			//������Ƶ��ʽ
			mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			//������Ƶ�ı���Ϊamr
			mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			mediaRecorder.prepare();
			mediaRecorder.start();
			//׼������
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
	//��������ļ�������
	private String generateFileName() {
		// TODO Auto-generated method stub
		
		return UUID.randomUUID().toString()+".amr";
	}
	//�����ȼ�
	public int getVoiceLevel(int maxLevel){
		if(isPrepared){
			try {
				//mediaRecorder.getMaxAmplitude()��� ��0-32767
				return maxLevel*mediaRecorder.getMaxAmplitude()/32768+1;//��1-7
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 1;
		
	}
	//�ͷ�
	public void release(){
		mediaRecorder.stop();
		mediaRecorder.release();
		mediaRecorder=null;
	}
	//ȡ��
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
