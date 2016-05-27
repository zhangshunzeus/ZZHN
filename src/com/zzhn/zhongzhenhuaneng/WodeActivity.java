package com.zzhn.zhongzhenhuaneng;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;


public class WodeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.personal_wode);
    }
}
