package com.example.mediaplayer_network;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Classification extends Fragment implements OnClickListener {

	private ImageButton part1;
	private ImageButton star1;
	private ImageButton part2;
	private ImageButton star2;
	private ImageButton part3;
	private ImageButton star3;
	private View view;
	boolean light = false;
	private ViewPager viewPager;
	private NetWorkAudioPlayer netWorkAudioPlayer ;
	private TimeChange timeChange = new TimeChange();
	private int totalTime;
	private List<Time> list;
	
	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.tab_classification, null);
		netWorkAudioPlayer= new NetWorkAudioPlayer(getActivity());
		totalTime = netWorkAudioPlayer.getDuration();
		list=new ArrayList<Time>();
		list.add(new Time(0, totalTime/3));
		list.add(new Time(totalTime/3,totalTime*2/3));
		list.add(new Time(totalTime*2/3, totalTime));
		timeChange.timeChange(list);
		initView();
		initEvent();
		return view;
	}

	private void initEvent() {
		// TODO Auto-generated method stub
		part1.setOnClickListener(this);
		part2.setOnClickListener(this);
		part3.setOnClickListener(this);
		star1.setOnClickListener(this);
		star2.setOnClickListener(this);
		star3.setOnClickListener(this);

	}

	private void initView() {
		// TODO Auto-generated method stub
		part1 = (ImageButton) view.findViewById(R.id.part1);
		part2 = (ImageButton) view.findViewById(R.id.part2);
		part3 = (ImageButton) view.findViewById(R.id.part3);

		star1 = (ImageButton) view.findViewById(R.id.star1);
		star2 = (ImageButton) view.findViewById(R.id.star2);
		star3 = (ImageButton) view.findViewById(R.id.star3);
		 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch (v.getId()) {
		case R.id.part1:
			viewPager.setCurrentItem(2);
			timeChange.setPart(1);
			timeChange.timeChange(list);
			break;
		case R.id.part2:
			viewPager.setCurrentItem(2);
			break;
		case R.id.part3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.star1:
			if (light) {
				star1.setImageResource(R.drawable.star_dark);
				light = false;
			} else {
				star1.setImageResource(R.drawable.star_light);
				light = true;
			}
			break;
		case R.id.star2:
			if (light) {
				star2.setImageResource(R.drawable.star_dark);
				light = false;
			} else {
				star2.setImageResource(R.drawable.star_light);
				light = true;
			}
			break;
		case R.id.star3:
			if (light) {
				star3.setImageResource(R.drawable.star_dark);
				light = false;
			} else {
				star3.setImageResource(R.drawable.star_light);
				light = true;
			}
			break;

		}
	}
	

}
