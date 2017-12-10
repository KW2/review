package com.example.samsung.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;

/**
 * Created by SAMSUNG on 2017-11-30.
 */

public class Survey extends Activity {
    ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.survey);
        image = (ImageView) findViewById(R.id.sur_img);
    }

    public void surClick (View view) {
        boolean checked = ((RadioButton) view).isChecked();
        if( checked) {
            switch (view.getId()) {
                case R.id.sur_ra1:
                    image.setImageResource(R.drawable.sur_swings);
                    break;
                case R.id.sur_ra2:
                    image.setImageResource(R.drawable.sur_billstacks);
                    break;
                case R.id.sur_ra3:
                    image.setImageResource(R.drawable.sur_blacknut);
                    break;
                case R.id.sur_ra4:
                    image.setImageResource(R.drawable.sur_giriboy);
                    break;
                case R.id.sur_ra5:
                    image.setImageResource(R.drawable.sur_cn);
                    break;
                case R.id.sur_ra6:
                    image.setImageResource(R.drawable.sur_cjam);
                    break;
                case R.id.sur_ra7:
                    image.setImageResource(R.drawable.sur_hyh);
                    break;
                case R.id.sur_ra8:
                    image.setImageResource(R.drawable.sur_gotexx);
                    break;
                case R.id.sur_ra9:
                    image.setImageResource(R.drawable.sur_ocengum);
                    break;
            }
        }
    }
}
