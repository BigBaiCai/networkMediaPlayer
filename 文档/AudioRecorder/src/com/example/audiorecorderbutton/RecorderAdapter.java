package com.example.audiorecorderbutton;

import java.util.List;

import com.example.audiorecorderbutton.MainActivity.Recorder;

import android.R.integer;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RecorderAdapter extends ArrayAdapter<Recorder>{
	private List<Recorder> data;
	private Context context;
	
	private int minItemWidth;
	private int maxItemWidth;
	
	private LayoutInflater layoutInflater;
	
	public RecorderAdapter(Context context,List<Recorder> data) {
		super(context, -1,data);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.data=data;
		layoutInflater = LayoutInflater.from(context);
		//获取屏幕宽度
		WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		
		maxItemWidth = (int) (outMetrics.widthPixels*0.7);
		minItemWidth = (int) (outMetrics.widthPixels*0.15);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder =null;
		if(convertView==null){
			convertView = layoutInflater.inflate(R.layout.item_recorder, parent,false);
			viewHolder = new ViewHolder();
			viewHolder.seconds =(TextView) convertView.findViewById(R.id.textView_recorder);
			viewHolder.length = convertView.findViewById(R.id.item_recorder_length);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		//Math.round表示四舍五入
		viewHolder.seconds.setText(Math.round(getItem(position).time)+"/");
		ViewGroup.LayoutParams lp = viewHolder.length.getLayoutParams();
		lp.width=(int) (minItemWidth+(maxItemWidth/60f*getItem(position).time));
		return convertView;
	}
	private class ViewHolder{
		TextView seconds;
		View length;
	}
}
