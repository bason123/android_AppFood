package com.example.pnlibrary.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pnlibrary.DAO.BookDAO;
import com.example.pnlibrary.DAO.ClassifyBookDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.adapter.BookGridViewAdapter;
import com.example.pnlibrary.model.ClassifyBook;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class BookFragment extends Fragment {

    private GridView gridView;
    private Spinner spnClassifyBook;
    private FloatingActionButton fltAdd;
    private BookDAO bookDAO;
    private ClassifyBookDAO classifyBookDAO;
    private ArrayList<ClassifyBook> listClassify;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book,container,false);
        gridView = view.findViewById(R.id.grView);
        fltAdd = view.findViewById(R.id.fltAdd);
        spnClassifyBook = view.findViewById(R.id.spnClassifyBook);
        bookDAO = new BookDAO(getContext());
        classifyBookDAO = new ClassifyBookDAO(getContext());
        listClassify = classifyBookDAO.getListClassifyBook();

        ArrayList<HashMap<String, Object>> listSpn = new ArrayList<>();

        for(ClassifyBook clb : listClassify){
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("id",clb.getId());
            hashMap.put("name",clb.getName());
            listSpn.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),listSpn,R.layout.item_spn_classify_book,new String[]{"id","name"},new int[]{R.id.idClassify,R.id.tvNameClassify});
        spnClassifyBook.setAdapter(simpleAdapter);
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });

        loadData();

        return view;
    }
    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_book,null);
        builder.setView(view);

        EditText edNameBookAdd = view.findViewById(R.id.edNameBookAdd);
        EditText edPriceAdd = view.findViewById(R.id.edPriceAdd);
        Spinner spnClassifyBook = view.findViewById(R.id.spnClassifyBook);

        SimpleAdapter simpleAdapter = new SimpleAdapter(getContext(),loadSpinner(), android.R.layout.simple_list_item_1,
                new String[]{"name"}, new int[]{android.R.id.text1});
        spnClassifyBook.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(edNameBookAdd.getText().toString().length()==0 && edPriceAdd.getText().toString().length()==0){
                    Toast.makeText(getContext(), "vui lòng nhập đủ", Toast.LENGTH_SHORT).show();
                }else{
                    String nameBookAdd = edNameBookAdd.getText().toString();
                    int priceAdd = Integer.parseInt(edPriceAdd.getText().toString());
                    HashMap<String, Object> hs = (HashMap<String, Object>) spnClassifyBook.getSelectedItem();
                    int idAdd = (int) hs.get("id");

                    boolean check = bookDAO.addBook(nameBookAdd,priceAdd,idAdd);
                    if(check){
                        Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
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
    private ArrayList<HashMap<String, Object>> loadSpinner(){
        ClassifyBookDAO classifyBookDAO = new ClassifyBookDAO(getContext());
        ArrayList<ClassifyBook> list = classifyBookDAO.getListClassifyBook();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
        for(ClassifyBook cl : list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("id",cl.getId());
            hs.put("name",cl.getName());
            listHM.add(hs);
        }

        return listHM;
    }
    private void loadData(){
        BookGridViewAdapter adapter = new BookGridViewAdapter(bookDAO.getListBook(),getContext(),loadSpinner(),bookDAO);
        gridView.setAdapter(adapter);
    }
}