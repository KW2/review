package com.example.samsung.myapplication;

/**
 * Created by SAMSUNG on 2017-12-07.
 */

public class ListViewBtnItem {
    private String textStr, nameText, moneyText;
    private int _id, time;

    public void setText(String str){
        textStr = str;
    }

    public String getText(){
        return this.textStr;
    }

    public void setNameText(String str) { nameText = str;}
    public void setMoneyText(int str) { moneyText = String.valueOf(str);}

    public String getNameText() { return  this.nameText;};
    public String getMoneyText() { return  this.moneyText;};

    public void set_id(int id) { _id = id;};
    public int get_id() { return this._id;};

    public void setTime(int t) { time = t;};
    public int getTime(){ return time; };
}

