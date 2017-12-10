package com.example.samsung.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by SAMSUNG on 2017-11-28.
 */

public class SingleTouchActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new SingleTouchView(this, null));
    }
}


