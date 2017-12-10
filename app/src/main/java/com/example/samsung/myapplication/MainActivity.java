package com.example.samsung.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

   /* public void onClicked(View v){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:010-2907-4348"));
                startActivity(intent);
    }*/
   public void mainClick(View v){
       Intent intent;
        switch(v.getId()){
            case R.id.M_01:
                intent = new Intent(getApplicationContext(), Calculator.class);
                startActivity(intent);
                break;
            case R.id.M_02:
                intent = new Intent(getApplicationContext(), Calculator2.class);
                startActivity(intent);
                break;
            case R.id.M_03:
                intent = new Intent(getApplicationContext(), EventDriven.class);
                startActivity(intent);
                break;
            case R.id.M_04:
                intent = new Intent(getApplicationContext(), SingleTouchActivity.class);
                startActivity(intent);
                break;
            case R.id.M_05:
                intent = new Intent(getApplicationContext(), MultiTouchActivty.class);
                startActivity(intent);
                break;
            case R.id.M_06:
                intent = new Intent(getApplicationContext(), Survey.class);
                startActivity(intent);
                break;
            case R.id.M_07:
                intent = new Intent(getApplicationContext(), DialogActivity.class);
                startActivity(intent);
                break;
            case R.id.M_08:
                intent = new Intent(getApplicationContext(), DatabaseP.class);
                startActivity(intent);
                break;
            case R.id.M_09:
                intent = new Intent(getApplicationContext(), ListViewActivity.class);
                startActivity(intent);
                break;
            case R.id.M_10:
                intent = new Intent(getApplicationContext(), Practice.class);
                startActivity(intent);
                break;
        }
   }

}
