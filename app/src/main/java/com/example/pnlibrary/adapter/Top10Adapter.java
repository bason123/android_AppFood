package com.example.pnlibrary.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlibrary.R;
import com.example.pnlibrary.model.Book;

import java.util.ArrayList;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.ViewHolder>{

    private ArrayList<Book> list;
    private Context context;

    public Top10Adapter(ArrayList<Book> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_rcv_top10,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvIdBook.setText("mã sách: "+list.get(position).getId());
        holder.tvNameBook.setText("tên sách: "+list.get(position).getName());
        holder.tvCount.setText("tổng mượn: "+list.get(position).getQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvIdBook,tvNameBook,tvCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBook = itemView.findViewById(R.id.tvIdBook);
            tvNameBook = itemView.findViewById(R.id.tvNameBook);
            tvCount = itemView.findViewById(R.id.tvCount);
        }
    }
}
