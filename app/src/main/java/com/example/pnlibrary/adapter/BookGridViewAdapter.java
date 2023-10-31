package com.example.pnlibrary.adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnlibrary.DAO.BookDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.model.Book;
import com.example.pnlibrary.model.ClassifyBook;

import java.util.ArrayList;
import java.util.HashMap;

public class BookGridViewAdapter extends BaseAdapter {

    private ArrayList<Book> list;
    private Context context;
    private ArrayList<HashMap<String, Object>> listClassify;
    private BookDAO bookDAO;

    public BookGridViewAdapter(ArrayList<Book> list, Context context, ArrayList<HashMap<String, Object>> listClassify, BookDAO bookDAO) {
        this.list = list;
        this.context = context;
        this.listClassify = listClassify;
        this.bookDAO = bookDAO;
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.item_book,parent,false);
        }

        ImageView imageView = convertView.findViewById(R.id.imgBook);
        TextView tvNameBook = convertView.findViewById(R.id.tvBookName);
        TextView tvClassifyBookName = convertView.findViewById(R.id.tvClassifyBookName);
        TextView tvPrice = convertView.findViewById(R.id.tvPrice);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        tvNameBook.setText(list.get(position).getName());
        tvClassifyBookName.setText("thể loại: "+list.get(position).getClassifyName());
        tvPrice.setText("giá thuê "+list.get(position).getPrice()+" VNĐ");

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEdit(list.get(position));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = bookDAO.deleteBook(list.get(position).getId());
                switch (check){
                    case 1:
                        Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "trùng sách trong phiếu mượn, không thể xóa", Toast.LENGTH_SHORT).show();
                        break;
                    default: break;
                }
            }
        });
        return convertView;
    }
    private void showDialogEdit(Book book){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit_book,null);
        builder.setView(view);

        TextView tvID = view.findViewById(R.id.ID);
        EditText edNameBookEdit = view.findViewById(R.id.edNameBookAdd);
        EditText edPrice = view.findViewById(R.id.edPriceAdd);
        Spinner spnClassifyBook = view.findViewById(R.id.spnClassifyBook);

        tvID.setText(book.getId()+"");
        edNameBookEdit.setText(book.getName());
        edPrice.setText(book.getPrice()+"");

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listClassify,
                android.R.layout.simple_list_item_1,
                new String[]{"name"},
                new int[]{android.R.id.text1}
        );
        spnClassifyBook.setAdapter(simpleAdapter);

        for(int i = 0; i < listClassify.size(); i++){
            if(((int)listClassify.get(i).get("id"))==book.getId()){
                spnClassifyBook.setSelection(i);
                break;
            }
        }
        builder.setNegativeButton("Sửa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edNameBookEdit.getText().toString().length()==0 && edPrice.getText().toString().length()==0){
                    Toast.makeText(context, "vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
                }else{
                    String nameBookAdd = edNameBookEdit.getText().toString();
                    int priceAdd = Integer.parseInt(edPrice.getText().toString());
                    HashMap<String, Object> hs = (HashMap<String, Object>) spnClassifyBook.getSelectedItem();
                    int idAdd = (int) hs.get("id");

                    boolean check = bookDAO.editBook(book.getId(),nameBookAdd,priceAdd,idAdd);
                    if(check){
                        Toast.makeText(context, "sửa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(context, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setPositiveButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void loadData(){
        list.clear();
        list = bookDAO.getListBook();
        notifyDataSetChanged();
    }
}
