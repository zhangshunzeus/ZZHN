package com.zzhn.zhongzhenhuaneng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zzhn.fragment.VpSimpleFragment;
import com.zzhn.view.ViewPagerIndicator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FindActivity extends FragmentActivity {
	
	RadioGroup group;
	RadioButton wode ;
	
	private ViewPager mViewPager;
	private ViewPagerIndicator mIndicator;
	
	private List<String> mTitles = Arrays.asList("项目发布","供求","招标公告","太阳能热水品牌排行榜");
	private List<VpSimpleFragment> mContents = new ArrayList<VpSimpleFragment>();
	private FragmentPagerAdapter mAdapter;
	

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_find);
		
		group = (RadioGroup) findViewById(R.id.main_home_btn_group);
		wode = (RadioButton) findViewById (R.id.main_home_btn_wode);
		wode.setOnClickListener(l);
		
		initViews();
		initDatas();
		
		mIndicator.setVisibleTabCount(3);
		mIndicator.setTabItemTitles(mTitles);
		
		mViewPager.setAdapter(mAdapter);
		mIndicator.setViewPager(mViewPager, 0);
		
		
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
				// TODO Auto-generated method stub
				
				//tabWidth*positionOffset + position*tabWidth
				mIndicator.scroll(position, positionOffset);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.main_home_btn_wode:
				Intent intent_wode = new Intent();
				intent_wode.setClass(FindActivity.this,WodeActivity.class);
				startActivity(intent_wode);
				break;

			default:
				break;
			}
		}
	};
	
	private void initDatas() {
		// TODO Auto-generated method stub
		
		for(String title:mTitles){
			VpSimpleFragment fragment = VpSimpleFragment.newInstance(title);
			mContents.add(fragment);
		}
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mContents.size();
			}
			
			@Override
			public Fragment getItem(int position) {
				// TODO Auto-generated method stub
				return mContents.get(position);
			}
		};
		
	}


	private void initViews() {
		// TODO Auto-generated method stub
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		mIndicator = (ViewPagerIndicator) findViewById(R.id.id_indicator);
	}
	
}
