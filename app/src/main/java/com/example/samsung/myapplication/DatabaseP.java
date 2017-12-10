package com.example.samsung.myapplication;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SAMSUNG on 2017-12-06.
 */

class DBHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "mydb.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE contacts ( _id INTEGER PRIMARY KEY" + " AUTOINCREMENT, name TEXT, tel TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }
}



public class DatabaseP extends Activity {

    DBHelper helper;
    SQLiteDatabase db;
    EditText edit_name, edit_tel;
    TextView txt_result;
    LinearLayout container;
    List<Button> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database);
        helper = new DBHelper(this);

        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException ex){
            db = helper.getReadableDatabase();
        }
        edit_name = (EditText) findViewById(R.id.db_e1);
        edit_tel = (EditText) findViewById(R.id.db_e2);
        txt_result = (TextView) findViewById(R.id.db_t3);
        container = (LinearLayout) findViewById(R.id.db_c);
        buttonMake();
    }

    public void insert(View target) {
        String name = edit_name.getText().toString();
        String tel = edit_tel.getText().toString();
        db.execSQL("INSERT INTO contacts VALUES (null, '" + name + "', '" + tel + "');");
        Toast.makeText(getApplication(), "추가성공",Toast.LENGTH_SHORT).show();
        edit_name.setText("");
        edit_tel.setText("");
        buttonMake();
    }

    public void search(View target){
        String name = edit_name.getText().toString();
        Cursor cursor = db.rawQuery("SELECT name, tel FROM contacts;", null);
        String str = "";
        while (cursor.moveToNext()) {
            String tel = cursor.getString(1);
            str = str + " 이름/전화번호: " + cursor.getString(0) + cursor.getString(1);
        }
        txt_result.setText(str);
    }

    public void clear(View target){
        txt_result.setText("탐색창");
    }

    public void delete(View target) {
        db.delete("contacts", null, null);
        buttonMake();
    }

    public void buttonMake() {
        Cursor cursor = db.rawQuery("SELECT * FROM contacts",null);
        Button btn;
        list.clear();
        container.removeAllViews();
        while(cursor.moveToNext()) {
            btn = new Button(this);
            btn.setText(cursor.getString(1));
            list.add(btn);
        }
        for(int i = 0; i<list.size(); i++){
            container.addView(list.get(i));


        }

    }



}
