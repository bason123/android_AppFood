package com.example.pnlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.pnlibrary.R;
import com.example.pnlibrary.model.ClassifyBook;

import java.util.ArrayList;

public class ClassifyBookGridViewAdapter extends BaseAdapter {

    private ArrayList<ClassifyBook> list;
    private Context context;

    public ClassifyBookGridViewAdapter(ArrayList<ClassifyBook> list, Context context) {
        this.list = list;
        this.context = context;
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

        return convertView;
    }
}
