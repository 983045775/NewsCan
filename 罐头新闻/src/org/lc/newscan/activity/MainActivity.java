package org.lc.newscan.activity;

import org.lc.newscan.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "进入了主页界面");
    }
}
