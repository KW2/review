package com.example.samsung.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SAMSUNG on 2017-12-07.
 */

public class Practice extends Activity implements CustomList.ListBtnClickListener2 {
    DBHelper2 helper;
    SQLiteDatabase db;
    ArrayList<ListViewBtnItem> items;
    CustomList adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.practice);


        items = new ArrayList<ListViewBtnItem>() ;
        ListView listView;

        helper = new DBHelper2(this);

        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException ex){
            db = helper.getReadableDatabase();
        }

        loadDB(db, items);

        adapter = new CustomList(this, R.layout.pra_list_item, items, this) ;

        listView = (ListView) findViewById(R.id.pra_listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // 알바 선택 -> 달력 페이지
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PtjDate.class);
                intent.putExtra("ptjId", items.get(position).get_id());
                intent.putExtra("ptjName", items.get(position).getNameText());
                startActivity(intent);
            }
        }) ;

        Button plsBtn = (Button) findViewById(R.id.pra_plsBtn);
        plsBtn.setOnClickListener(v ->{
            // 알바 추가 대화상자
            final Dialog cstDialog = new Dialog(this);
            cstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            cstDialog.setContentView(R.layout.custom_dialog);

            final EditText a_Name = (EditText) cstDialog.findViewById(R.id.cstdig_edit1);
            final EditText a_Money = (EditText) cstDialog.findViewById(R.id.cstdig_edit2);
            final EditText a_TIme = (EditText) cstDialog.findViewById(R.id.cstdig_edit3);

            Button cancel = (Button) cstDialog.findViewById(R.id.cstdig_b1);
            Button save = (Button) cstDialog.findViewById(R.id.cstdig_b2);

            save.setOnClickListener(view ->{
                // 데이터 베이스 insert 문
                String name = a_Name.getText().toString();
                Integer money = Integer.parseInt(a_Money.getText().toString());
                Integer time = Integer.parseInt(a_TIme.getText().toString());
                db.execSQL("INSERT INTO ptjob VALUES (null, '" + name + "', '" + money + "', '" + time + "');");
                Toast.makeText(getApplication(), "추가성공",Toast.LENGTH_SHORT).show();
                cstDialog.dismiss();

                items = new ArrayList<ListViewBtnItem>() ;
                loadDB(db, items);
              //  adapter = new CustomList(this, R.layout.pra_list_item, items, this) ;
                adapter.notifyDataSetChanged();
            });

            cancel.setOnClickListener(view -> {
                cstDialog.dismiss();
            });

            cstDialog.show();
        });

    }

    public boolean loadDB(SQLiteDatabase db, ArrayList<ListViewBtnItem> list){

        ListViewBtnItem item ;


        if (list == null) {
            list = new ArrayList<ListViewBtnItem>() ;
        }

        Cursor cursor = db.rawQuery("SELECT * FROM ptjob",null);
        while(cursor.moveToNext()) {
            item = new ListViewBtnItem();
            item.set_id(cursor.getInt(0));
            item.setNameText(cursor.getString(1));
            item.setMoneyText(cursor.getInt(2));
            item.setTime(cursor.getInt(3));
            list.add(item);

        }

        return true;
    }

    @Override
    public void onListBtnClick(int id, int option) {

        if(option == 0) {
            // 수정 작업
            final Dialog cstDialog = new Dialog(this);
            cstDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            cstDialog.setContentView(R.layout.custom_dialog);

            final EditText a_Name = (EditText) cstDialog.findViewById(R.id.cstdig_edit1);
            final EditText a_Money = (EditText) cstDialog.findViewById(R.id.cstdig_edit2);
            final EditText a_TIme = (EditText) cstDialog.findViewById(R.id.cstdig_edit3);

            Button cancel = (Button) cstDialog.findViewById(R.id.cstdig_b1);
            Button save = (Button) cstDialog.findViewById(R.id.cstdig_b2);

            ListViewBtnItem listViewItem = helper.select(id);

            save.setText("알바 수정");
            a_Name.setText(listViewItem.getNameText());
            a_Money.setText(listViewItem.getMoneyText());
            a_TIme.setText(String.valueOf(listViewItem.getTime()));

            save.setOnClickListener(view ->{
                // 데이터 베이스 update 문
                helper.update(listViewItem.get_id(), a_Name.getText().toString(), Integer.parseInt(a_Money.getText().toString()), Integer.parseInt(a_TIme.getText().toString()));
                Toast.makeText(getApplicationContext(), "알바 수정 완료", Toast.LENGTH_SHORT ).show();
                cstDialog.dismiss();
            });

            cancel.setOnClickListener(view -> {
                cstDialog.dismiss();
            });

            cstDialog.show();

        }else if (option == 1){
            // 삭제 작업
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog alert;
            builder.setTitle("삭제 확인 대화 상자")
                    .setMessage("해당 알바기록을 삭제 하시겠습니까?")
                    .setCancelable(false)
                    .setPositiveButton("확인",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton){
                                    // 데이터베이스 삭제
                                    helper.delete(id);
                                    Toast.makeText(getApplication(), "삭제 완료",Toast.LENGTH_SHORT).show();
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
        }
    }



}


class DBHelper2 extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "ptjDB.db";
    private static final int DATABASE_VERSION = 2;
    SQLiteDatabase db = this.getWritableDatabase();

    public DBHelper2(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ptjob ( _id INTEGER PRIMARY KEY" + " AUTOINCREMENT, ptjName TEXT, ptjMoney INTEGER, ptjTime INTEGER );");
        db.execSQL("CREATE TABLE timeMoney ( ptjId INTEGER , dYear INTEGER, dMonth INTEGER, dDay INTEGER, dTime INTEGER );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS ptjob");
        db.execSQL("DROP TABLE IF EXISTS timeMoney");
        onCreate(db);
    }

    public void delete(int id){

        db.delete("ptjob","_id = ? ", new String[] { Integer.toString(id) });

    }

    public void insert(String name, Integer money, Integer time){

    }

    public void update(int id, String name, int money, int time){

        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("ptjName", name);
        contentValues.put("ptjMoney", money);
        contentValues.put("ptjTime", time);
        db.update("ptjob", contentValues, "_id = ? ", new String[] { Integer.toString(id)});
    }

    public ListViewBtnItem select(int id){
        ListViewBtnItem item = new ListViewBtnItem();
        Cursor cursor = db.rawQuery("select * from ptjob where _id=" + id + "", null);
        while(cursor.moveToNext()) {
            item.set_id(id);
            item.setNameText(cursor.getString(1));
            item.setMoneyText(cursor.getInt(2));
            item.setTime(cursor.getInt(3));
        }
        return item;
    }

    public void insertDate(int ptjId, int dYear, int dMonth, int dDay, int dTime){
        db.execSQL("INSERT INTO timeMoney VALUES ('" + ptjId + "', '" + dYear + "', '" + dMonth + "', '" + dDay + "', '" + dTime + "');");
    }

    public void deleteDate(int ptjId, int dYear, int dMonth, int dDay, int dTime){
        String str[] = { Integer.toString(ptjId), Integer.toString(dYear), Integer.toString(dMonth), Integer.toString(dDay), Integer.toString(dTime)};
        db.delete("timeMoney","ptjId = ? and dYear = ? and dMonth = ? and dDay = ? and dTIme = ? ", str);

    }


    public String imsiSelect(){
        String str = "1";
        Cursor cursor = db.rawQuery("select * from timeMoney", null);
        while(cursor.moveToNext()) {
            str = str +Integer.toString(cursor.getInt(0)) + "달: "+Integer.toString(cursor.getInt(1)) + " 일: "+ Integer.toString(cursor.getInt(2)) + " 돈: "+ Integer.toString(cursor.getInt(3));
        }
        return str;
    }

    public boolean checked(int ptjId, int dMonth, int dDay){
        boolean checked = false;
        Cursor cursor = db.rawQuery("select * from timeMoney where ptjId=" + ptjId + " and dMonth=" + dMonth + " and dDay="+ dDay + " " , null);

        if( cursor.getCount() != 0) {
            checked = true;
        }

        return checked;
    }

    public int getdTime(int ptjId, int dYear, int dMonth, int dDay){
        int dTIme = 0;
        Cursor cursor = db.rawQuery("select dTime from timeMoney where ptjId=" + ptjId + " and dYear=" + dYear + " and dMonth=" + dMonth + " and dDay="+ dDay , null);
        while(cursor.moveToNext()) {
            dTIme = cursor.getInt(0);
        }
        return dTIme;
    }

    public int getPtjTime(int ptjId){
        int ptjTime = 0;
        Cursor cursor = db.rawQuery("select ptjTime from ptjob where _id=" + ptjId + "", null);
        while(cursor.moveToNext()) {
            ptjTime = cursor.getInt(0);
        }
        return ptjTime;
    }

    public ArrayList<CalendarDay> getDates(int ptjId){
        ArrayList<CalendarDay> dates = new ArrayList<>();
        Cursor cursor = db.rawQuery("select dYear, dMonth, dDay from timeMoney where ptjId=" + ptjId + "", null);
        Calendar calendar = Calendar.getInstance();

        while(cursor.moveToNext()) {
            calendar.add(Calendar.YEAR, calendar.get(Calendar.YEAR) - cursor.getInt(0));
            calendar.add(Calendar.MONTH, (cursor.getInt(1) - 1 ) - calendar.get(Calendar.MONTH));
            calendar.add(Calendar.DAY_OF_MONTH, cursor.getInt(2) - calendar.get(Calendar.DAY_OF_MONTH));

            dates.add(CalendarDay.from(calendar));
            calendar = Calendar.getInstance();
        }



        return dates;
    }

    public String getMonthMoney(int ptjId, int dYear,int dMonth){
        String str = "";
        Cursor cursor = db.rawQuery("select sum(ptjob.ptjMoney * timeMoney.dTime ) " +
                                    "from ptjob INNER JOIN timeMoney ON ptjob._id = timeMoney.ptjId " +
                                    "where ptjob._id=" + ptjId + " and timeMoney.dYear =" + dYear + " and timeMoney.dMonth =" + dMonth, null);
        while(cursor.moveToNext()) {
            str = String.valueOf(dMonth) + "달 월급 : " + cursor.getInt(0) + "원";
        }

        return str;
    }

    public String getAllMoney(int ptjId){
        String str = "";
        Cursor cursor = db.rawQuery("select sum(ptjob.ptjMoney * timeMoney.dTime ) " +
                "from ptjob INNER JOIN timeMoney ON ptjob._id = timeMoney.ptjId " +
                "where ptjob._id=" + ptjId , null);
        while(cursor.moveToNext()) {
            str = "총급여: " + cursor.getInt(0) + "원";
        }
        return str;
    }

}
