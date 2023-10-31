package com.example.pnlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.pnlibrary.model.Customer;

import java.util.ArrayList;

public class CustomSpinnerAdapterCustomer  extends BaseAdapter {

    private Context context;
    private ArrayList<Customer> list;

    public CustomSpinnerAdapterCustomer(Context context, ArrayList<Customer> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        convertView = inflater.inflate(android.R.layout.simple_spinner_item, parent, false);

        TextView txtName = convertView.findViewById(android.R.id.text1);

        txtName.setText(((Customer)(list.get(position))).getName());

        return convertView;
    }
}

