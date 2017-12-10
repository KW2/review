package com.example.samsung.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by SAMSUNG on 2017-11-24.
 */

public class EventDriven extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventdriven);

        MyListenerClass myListenerClass = new MyListenerClass();

        Button event_btn1 = (Button) findViewById(R.id.event_btn1);
        Button event_btn2 = (Button) findViewById(R.id.event_btn2);
        Button event_btn3 = (Button) findViewById(R.id.event_btn3);

        event_btn1.setOnClickListener(myListenerClass);
        /*event_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "2 버튼 클릭 수신", Toast.LENGTH_SHORT).show();
            }
        });*/

        //람다식
        event_btn2.setOnClickListener((v) -> {
            Toast.makeText(getApplicationContext(), "2 람다 버튼 클릭 수신", Toast.LENGTH_SHORT).show();
        });

        event_btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getApplicationContext(), "3 버튼 클릭 수신", Toast.LENGTH_SHORT).show();
    }

    class MyListenerClass implements View.OnClickListener {
        public void onClick(View v){
            Toast.makeText(getApplicationContext(), "1 버튼 클릭 수신", Toast.LENGTH_SHORT).show();
        }
    }

    public void eventChkC (View v){
        boolean checked = ((CheckBox) v).isChecked();

        switch(v.getId()){
            case R.id.event_chk1:
                if(checked)
                    Toast.makeText(getApplicationContext(),"학생 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"학생 선택 취소",Toast.LENGTH_SHORT).show();

                break;
            case R.id.event_chk2:
                if(checked)
                    Toast.makeText(getApplicationContext(),"남자 선택",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getApplicationContext(),"남자 선택 취소",Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void eventRaC(View v){
        boolean checked = ((RadioButton) v).isChecked();

        switch (v.getId()){
            case R.id.event_RaR:
                if (checked)
                    Toast.makeText(getApplicationContext(),((RadioButton) v).getText(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.event_RaB:
                if (checked)
                    Toast.makeText(getApplicationContext(),((RadioButton) v).getText(), Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
