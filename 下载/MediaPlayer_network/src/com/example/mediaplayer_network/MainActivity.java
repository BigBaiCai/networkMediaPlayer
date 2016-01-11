package com.example.mediaplayer_network;


import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity{
	private ViewPager viewPager;
	private List<Fragment> listFragment;
	private FragmentPagerAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.main_activity);
		listFragment=new ArrayList<Fragment>();
		//Fragment networkActivity = new NetworkPlayerActivity();
	}
}
