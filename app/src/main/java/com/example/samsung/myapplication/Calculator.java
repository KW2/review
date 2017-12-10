package com.example.samsung.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by SAMSUNG on 2017-11-23.
 */

public class Calculator extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);

    }

    public void calC(View v){
        EditText edit1 = (EditText) findViewById(R.id.edit1);
        EditText edit2 = (EditText) findViewById(R.id.edit2);
        TextView result = (TextView) findViewById(R.id.txt3);
        switch(v.getId()){
            case R.id.btnH:
                result.setText( String.valueOf(Integer.parseInt(edit1.getText().toString()) + Integer.parseInt(edit2.getText().toString())));
                break;
            case R.id.btnC:
                result.setText(String.valueOf(Integer.parseInt(edit1.getText().toString()) - Integer.parseInt(edit2.getText().toString())));
                break;
            case R.id.btnG:
                result.setText(String.valueOf(Integer.parseInt(edit1.getText().toString()) * Integer.parseInt(edit2.getText().toString())));
                break;
            case R.id.btnN:
                result.setText(String.valueOf(Integer.parseInt(edit1.getText().toString()) / Integer.parseInt(edit2.getText().toString())));
                break;
        }
    }
}
