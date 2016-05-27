package com.zzhn.zhongzhenhuaneng;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.zzhn.fragment.VpSimpleFragment;
import com.zzhn.view.ViewPagerIndicator;
import com.zzhn.view.ViewPagerIndicator.PageOnchangeListener;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Window;

public class FindActivity extends FragmentActivity {
	
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
