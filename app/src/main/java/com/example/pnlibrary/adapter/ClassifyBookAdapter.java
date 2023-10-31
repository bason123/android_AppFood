package com.example.pnlibrary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlibrary.DAO.ClassifyBookDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.model.ClassifyBook;
import com.example.pnlibrary.model.ItemClick;

import java.util.ArrayList;

public class ClassifyBookAdapter extends RecyclerView.Adapter<ClassifyBookAdapter.ViewHolder> {

    private ArrayList<ClassifyBook> list;
    private Context context;
    private ClassifyBookDAO classifyBookDAO;
    private ItemClick itemClick;

    public ClassifyBookAdapter(ArrayList<ClassifyBook> list, Context context,ClassifyBookDAO classifyBookDAO, ItemClick itemClick) {
        this.list = list;
        this.context = context;
        this.classifyBookDAO = classifyBookDAO;
        this.itemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_classify_book,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvIdClassify.setText("mã loại: "+list.get(position).getId()+"");
        holder.tvNameClassify.setText("tên loại sách: "+list.get(position).getName()+"");

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = classifyBookDAO.deleteClassifyBook(list.get(position).getId());
                switch (check){
                    case 1:
                        loadData();
                        break;
                    case -1:
                        Toast.makeText(context, "có sách trùng loại sách này\n không thể xóa", Toast.LENGTH_SHORT).show();
                        break;
                    case 0:
                        Toast.makeText(context, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onClick(list.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView tvIdClassify,tvNameClassify;
        CardView cardView;
        Button btnDelete,btnEdit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdClassify = itemView.findViewById(R.id.tvIdClassify);
            tvNameClassify = itemView.findViewById(R.id.tvNameClassify);
            cardView = itemView.findViewById(R.id.cardView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            cardView.setOnCreateContextMenuListener(this);
        }
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ((Activity)context).getMenuInflater().inflate(R.menu.long_click_item,menu);
        }
    }
    private void loadData(){
        list = classifyBookDAO.getListClassifyBook();
        notifyDataSetChanged();
    }
    public void removeItem(int position){
        classifyBookDAO.deleteClassifyBook(position);
        loadData();;
    }
    public void editItem(int idEdit, String nameEdit){
        classifyBookDAO.editClassifyBook(idEdit,nameEdit);
        loadData();
    }
}
