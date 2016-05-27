package com.zzhn.zhongzhenhuaneng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class WodeActivity extends Activity {

	RadioGroup group;
	RadioButton faxian ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_wode);
        
        group = (RadioGroup) findViewById(R.id.main_home_btn_group);
		faxian = (RadioButton) findViewById (R.id.main_home_btn_faxian);
		faxian.setOnClickListener(l);
        
    }
    
    OnClickListener l = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.main_home_btn_faxian:
				Intent intent_faxian = new Intent();
				intent_faxian.setClass(WodeActivity.this,FindActivity.class);
				startActivity(intent_faxian);
				break;

			default:
				break;
			}
		}
	};
    
}
