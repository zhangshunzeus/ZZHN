package com.zzhn.zhongzhenhuaneng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MainActivity extends Activity {

	 Button login , home_page , wode , edit ;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.activity_main);

	        login = (Button) findViewById(R.id.login);
	        home_page = (Button) findViewById(R.id.home_page);
	        wode = (Button) findViewById(R.id.personal_wode);
	        edit = (Button) findViewById(R.id.personal_edit);

	        login.setOnClickListener(l);
	        home_page.setOnClickListener(l);
	        wode.setOnClickListener(l);
	        edit.setOnClickListener(l);
	    }
	    
	    OnClickListener l = new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {

	            switch (v.getId()){

	                case R.id.login:
	                    Intent intent_login = new Intent();
	                    intent_login.setClass(MainActivity.this, LoginActivity.class);
	                    startActivity(intent_login);
	                    break;
	                case R.id.personal_wode:
	                    Intent intent_wode = new Intent();
	                    intent_wode.setClass(MainActivity.this, WodeActivity.class);
	                    startActivity(intent_wode);
	                    break;
	                case R.id.personal_edit:
	                    Intent intent_edit = new Intent();
	                    intent_edit.setClass(MainActivity.this,EditActivity.class);
	                    startActivity(intent_edit);
	                    break;

	            }
	        }
	    };

}
