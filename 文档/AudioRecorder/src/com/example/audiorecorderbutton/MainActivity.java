package com.example.audiorecorderbutton;

import java.util.ArrayList;
import java.util.List;

import com.example.audiorecorderbutton.view.AudioRecorderButton;
import com.example.audiorecorderbutton.view.AudioRecorderButton.AudioRecorderFinishListener;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;


public class MainActivity extends Activity {
	private ListView listView;
	private ArrayAdapter<Recorder> arrayAdapter;
	private List<Recorder> data = new ArrayList<MainActivity.Recorder>();
	private AudioRecorderButton audioRecorderButton;
	private View animView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        audioRecorderButton = (AudioRecorderButton) findViewById(R.id.audioRecorderButton);
       audioRecorderButton.setAudioRecorderFinishListener(new AudioRecorderFinishListener() {
		
		@Override
		public void onFinish(Float seconds, String filePath) {
			// TODO Auto-generated method stub
			Recorder recorder = new Recorder(seconds, filePath);
			data.add(recorder);
			//更新数据
			arrayAdapter.notifyDataSetChanged();
			listView.setSelection(data.size()-1);
		}
	});
	
       arrayAdapter = new RecorderAdapter(this, data);
       listView.setAdapter(arrayAdapter);
       listView.setOnItemClickListener(new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View view, int position,
				long arg3) {
			// TODO Auto-generated method stub
			if(animView!=null){
				animView.setBackgroundResource(R.drawable.adj);
				animView=null;
			}
			//播放动画
			 animView = view.findViewById(R.id.id_recorder_anim);
			animView.setBackgroundResource(R.drawable.play);
			AnimationDrawable anim = (AnimationDrawable) animView.getBackground();
			anim.start();
			//播放音频
			MediaManager.playSound(data.get(position).filePath,new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					animView.setBackgroundResource(R.drawable.adj);
				}
			});
		}
	});
    }
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	super.onPause();
    	MediaManager.pause();
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	MediaManager.resume();
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	MediaManager.release();;
    }
    
    class Recorder{
    	float time;
    	String filePath;
		public Recorder(float time, String filePath) {
			super();
			this.time = time;
			this.filePath = filePath;
		}
		public float getTime() {
			return time;
		}
		public void setTime(float time) {
			this.time = time;
		}
		public String getFilePath() {
			return filePath;
		}
		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		
    	
    }
}
 