package com.example.samsung.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by SAMSUNG on 2017-12-01.
 */

public class DialogActivity extends Activity {
  //  private static final int DIALOG_YES_NO_MESSAGE = 1;

    /*@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_YES_NO_MESSAGE:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("종료 확인 대화 상자")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton){
                                        DialogActivity.this.finish();
                                    }
                        })
                        .setNegativeButton("No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                        });
                AlertDialog alert = builder.create();
                return alert;

        }
        return null;
    }*/


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
       /* Button b = (Button) findViewById(R.id.altdlg_b1);
        b.setOnClickListener(v -> {
         //   showDialog(DIALOG_YES_NO_MESSAGE);
            switch (v.getId()) {
                case R.id.altdlg_b1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("종료 확인 대화 상자")
                            .setMessage("앱을 종료하시겠습니까?")
                            .setCancelable(false)
                            .setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton){
                                            DialogActivity.this.finish();
                                        }
                                    })
                            .setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    break;

            }
        });*/

    }

    public int year, month, day, hour, minute;
    private int mYear, mMonth, mMonth2, mDay, mHour, mMinute;

    public DialogActivity() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mMonth2 = (c.get(Calendar.MONTH)+1);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

    }


    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int yearSelected, int monthOfYear, int dayOfMonth) {
            year = yearSelected;
            month = monthOfYear;
            day = dayOfMonth;
            Toast.makeText(getApplicationContext(), "날짜: " + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int min) {
            hour = hourOfDay;
            minute = min;
            Toast.makeText(getApplicationContext(), "시간: " + hour + "-" + minute, Toast.LENGTH_SHORT).show();

        }
    };

    public void dialogOnClick (View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        AlertDialog alert;

        switch (v.getId()) {
            case R.id.altdlg_b1:
                builder.setTitle("삭제 확인 대화 상자")
                        .setMessage("해당 알바기록을 삭제 하시겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton){
                                        // 데이터베이스 삭제

                                        DialogActivity.this.finish();

                                    }
                                })
                        .setNegativeButton("취소",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dialog.cancel();
                                    }
                                });
                alert = builder.create();
                alert.show();
                break;
            case R.id.altdlg_b2:
                final CharSequence[] items = {"빨강", "초록", "파랑"};

                builder.setTitle("색상 선택")
                        .setSingleChoiceItems(items, -1,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item){
                                        Toast.makeText(getApplicationContext(), items[item],Toast.LENGTH_SHORT).show();
                                    }
                        });
                alert = builder.create();
                alert.show();
                break;
            case R.id.altdlg_b3:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
                datePickerDialog.show();
                break;
            case R.id.altdlg_b4:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, mTimeSetListener, mHour, mMinute,false);
                timePickerDialog.show();
                break;
            case R.id.altdlg_b5:

                final Dialog cstDialog = new Dialog(this);
                cstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cstDialog.setContentView(R.layout.custom_dialog);

                // 대화상자 크기 조정
               /* WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.copyFrom(cstDialog.getWindow().getAttributes());
                params.width =1500;
                params.height = 900;
                cstDialog.getWindow().setAttributes(params);*/



                final EditText a_Name = (EditText) cstDialog.findViewById(R.id.cstdig_edit1);
                final EditText a_Money = (EditText) cstDialog.findViewById(R.id.cstdig_edit2);
                final EditText a_TIme = (EditText) cstDialog.findViewById(R.id.cstdig_edit3);

                Button cancel = (Button) cstDialog.findViewById(R.id.cstdig_b1);
                Button save = (Button) cstDialog.findViewById(R.id.cstdig_b2);

                save.setOnClickListener(view ->{
                    // 데이터 베이스 insert 문

                    Toast.makeText(getApplication(), "알바이름: "+ a_Name.getText().toString() + " 시급: " + a_Money.getText().toString() + " 시간: " + a_TIme.getText().toString()
                            , Toast.LENGTH_LONG ).show();
                });

                cancel.setOnClickListener(view -> {
                    cstDialog.dismiss();
                });

                cstDialog.show();

                break;
            case R.id.altdlg_b6:
                final Dialog cstDialog2 = new Dialog(this);
                cstDialog2.requestWindowFeature(Window.FEATURE_NO_TITLE);
                cstDialog2.setContentView(R.layout.custom_dialog2);

                Button plusBtn = (Button) cstDialog2.findViewById(R.id.cstdig2_b2);
                Button minBtn = (Button) cstDialog2.findViewById(R.id.cstdig2_b1);
                Button cancel2 = (Button) cstDialog2.findViewById(R.id.cstdig2_b3);
                Button checked = (Button) cstDialog2.findViewById(R.id.cstdig2_b4);

                TextView title = (TextView) cstDialog2.findViewById(R.id.cstdig2_title);
                TextView dayText = (TextView) cstDialog2.findViewById(R.id.cstdig2_t1);
                TextView timeText = (TextView) cstDialog2.findViewById(R.id.cstdig2_t2);

                title.setText(mMonth2 + "월 알바 ");
                dayText.setText(mDay + "일");

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
                    if(checked.getText().equals("알바 완료")) {
                        Toast.makeText(getApplicationContext(), mMonth2 + "월 " + mDay + "일 알바 " + timeText.getText().toString() + "시간 완료", Toast.LENGTH_LONG).show();
                        checked.setText("알바 미완료");
                    }else {
                        Toast.makeText(getApplicationContext(), mMonth2 + "월 " + mDay + "일 알바 취소", Toast.LENGTH_LONG).show();
                        checked.setText("알바 완료");
                    }
                });

                cancel2.setOnClickListener(view -> {
                    cstDialog2.dismiss();
                });

                cstDialog2.show();

                break;

        }
    }
}
