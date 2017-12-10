package com.example.samsung.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by SAMSUNG on 2017-12-07.
 */

class CustomList extends ArrayAdapter<ListViewBtnItem> implements View.OnClickListener {

    public interface ListBtnClickListener2 {
        void onListBtnClick(int id, int option) ;
    }

    private ListBtnClickListener2 listBtnClickListener ;
    private final Activity context;
    int resourceId ;
    private DBHelper2 helper2;

    public CustomList(Activity context, int resource , ArrayList<ListViewBtnItem> list, ListBtnClickListener2 clickListener){
        super(context, resource, list);
        this.context = context;
        this.resourceId = resource;
        this.listBtnClickListener = clickListener;
        helper2 = new DBHelper2(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(this.resourceId, parent, false);
        }

        final TextView nameText = (TextView) convertView.findViewById(R.id.pra_item_text_name);
        final TextView moneyTextAll = (TextView) convertView.findViewById(R.id.pra_item_text_moneyAll);
        final TextView moneyTextMonth = (TextView) convertView.findViewById(R.id.pra_item_text_moneyMonth);
        final ListViewBtnItem listViewItem = (ListViewBtnItem) getItem(position);

        Calendar calendar = Calendar.getInstance();

        nameText.setText(listViewItem.getNameText());
        moneyTextAll.setText(helper2.getAllMoney(listViewItem.get_id()));
        moneyTextMonth.setText(helper2.getMonthMoney(listViewItem.get_id(), calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1));


        Button updaateBtn = (Button) convertView.findViewById(R.id.pra_item_btn_update);
        Button deleteBtn = (Button) convertView.findViewById(R.id.pra_item_btn_delete);

        updaateBtn.setTag(listViewItem.get_id());
        deleteBtn.setTag(listViewItem.get_id());

        updaateBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View view) {
        if (this.listBtnClickListener != null) {
            switch (view.getId()){
                case R.id.pra_item_btn_update:
                    this.listBtnClickListener.onListBtnClick((int)view.getTag(), 0) ;
                    break;
                case R.id.pra_item_btn_delete:
                    this.listBtnClickListener.onListBtnClick((int)view.getTag(), 1) ;
                    break;
            }

        }
    }

}
