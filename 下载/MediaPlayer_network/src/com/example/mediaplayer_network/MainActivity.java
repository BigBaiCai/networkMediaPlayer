package com.example.mediaplayer_network;


import java.util.ArrayList;
import java.util.List;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

public class MainActivity extends FragmentActivity implements OnClickListener{
	private String TAG="MainActivity";
	private ViewPager viewPager;
	private List<Fragment> listFragment;
	private FragmentPagerAdapter adapter;
	private ImageButton back;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main_activity);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		back=(ImageButton)this.findViewById(R.id.back);
		back.setOnClickListener(this);
		viewPager = (ViewPager) this.findViewById(R.id.viewPager);
		listFragment=new ArrayList<Fragment>();
		//Log.i(TAG, "init");
		Classification classificationFragment=new Classification();
		Fragment exerciseFragment = new Exercise();
		//Log.i(TAG, "NetworkPlayer");
		Fragment networkPlayerFragment = new NetworkPlayer();
		//Log.i(TAG, "afterinitNetworkPlayer");
		classificationFragment.setViewPager(viewPager);
		listFragment.add(classificationFragment);
		listFragment.add(networkPlayerFragment);
		listFragment.add(exerciseFragment);
		
		adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return listFragment.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return listFragment.get(arg0);
			}
		};
		
		viewPager.setAdapter(adapter);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(MainActivity.this,Home.class);
		startActivity(intent);
	}
}
