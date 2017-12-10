package com.example.samsung.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-12-08.
 */

public class PtjDate extends Activity {

    MaterialCalendarView dateView;
    TextView nameTxt, moneyTxt;
    int ptjId;
    String ptjName;
    EventDecorator decorator;
    DBHelper2 helper2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ptjdate);

        helper2 = new DBHelper2(getApplicationContext());
        Intent intent = getIntent();
        ptjId = intent.getExtras().getInt("ptjId");
        ptjName = intent.getExtras().getString("ptjName");

        dateView = (MaterialCalendarView) findViewById(R.id.pra_date);
        nameTxt = (TextView) findViewById(R.id.date_ptjName);
        moneyTxt = (TextView) findViewById(R.id.date_ptjMoney);

        nameTxt.setText(ptjName);
        setTextMoney();

        // 토,일,오늘 색
        dateView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                new OneDayDecorator());


       //빨간점 스레드 실행
        new ApiSimulator().execute();

        dateView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                setTextMoney();
            }
        });


        dateView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                dateView.clearSelection();
                final Dialog cstDialog2 = new Dialog(PtjDate.this);
                cstDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cstDialog2.setContentView(R.layout.custom_dialog2);

                Button plusBtn = (Button) cstDialog2.findViewById(R.id.cstdig2_b2);
                Button minBtn = (Button) cstDialog2.findViewById(R.id.cstdig2_b1);
                Button cancel2 = (Button) cstDialog2.findViewById(R.id.cstdig2_b3);
                Button checked = (Button) cstDialog2.findViewById(R.id.cstdig2_b4);

                TextView title = (TextView) cstDialog2.findViewById(R.id.cstdig2_title);
                TextView dayText = (TextView) cstDialog2.findViewById(R.id.cstdig2_t1);
                TextView timeText = (TextView) cstDialog2.findViewById(R.id.cstdig2_t2);

                title.setText(date.getMonth()+1 + "월 알바 ");
                dayText.setText(date.getDay() + "일");


                if( helper2.checked(ptjId, date.getMonth()+1, date.getDay()) ) {
                    int dTime = helper2.getdTime(ptjId, date.getYear(), date.getMonth()+1, date.getDay());
                    timeText.setText(String.valueOf(dTime)+"시간");
                    checked.setText("알바 취소");
                    plusBtn.setEnabled(false);
                    minBtn.setEnabled(false);
                }else{
                    int ptjTime = helper2.getPtjTime(ptjId);
                    timeText.setText(String.valueOf(ptjTime)+"시간");
                    checked.setText("알바 완료");
                }

                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int timeInt = 0;

                        switch (view.getId()){
                            case R.id.cstdig2_b1:
                                timeInt = Integer.parseInt(timeText.getText().toString().substring(0,1));
                                timeInt = timeInt - 1 ;
                                timeText.setText(String.valueOf(timeInt)+"시간");
                                break;
                            case R.id.cstdig2_b2:
                                timeInt = Integer.parseInt(timeText.getText().toString().substring(0,1));
                                timeInt = timeInt + 1 ;
                                timeText.setText(String.valueOf(timeInt)+"시간");
                                break;
                        }
                    }
                };

                plusBtn.setOnClickListener(listener);
                minBtn.setOnClickListener(listener);


                checked.setOnClickListener(view -> {
                    // 달력에 체크 확인/해제

                    ApiSimulator a = new ApiSimulator();
                    if(checked.getText().equals("알바 완료")) {
                        helper2.insertDate(ptjId, date.getYear(), date.getMonth()+1, date.getDay(), Integer.parseInt(timeText.getText().toString().substring(0,1)));
                        dateView.removeDecorator(decorator);
                        a.execute();
                        cstDialog2.dismiss();
                        setTextMoney();
                    }else {
                        helper2.deleteDate(ptjId, date.getYear(), date.getMonth() + 1, date.getDay(), Integer.parseInt(timeText.getText().toString().substring(0, 1)));
                        dateView.removeDecorator(decorator);
                        a.execute();
                        cstDialog2.dismiss();
                        setTextMoney();
                    }
                });

                cancel2.setOnClickListener(view -> {
                    cstDialog2.dismiss();
                    dateView.clearSelection();
                });


                cstDialog2.show();


            }
        });

    }

    public void setTextMoney(){
        moneyTxt.setText(helper2.getMonthMoney(ptjId, dateView.getCurrentDate().getYear(), dateView.getCurrentDate().getMonth()+1));
    }

    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ArrayList<CalendarDay> dates = helper2.getDates(ptjId);
            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            decorator = new EventDecorator(Color.RED, calendarDays);
            dateView.addDecorator(decorator);
        }
    }
}

