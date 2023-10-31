package com.example.pnlibrary.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.pnlibrary.DAO.ClassifyBookDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.adapter.ClassifyBookAdapter;
import com.example.pnlibrary.adapter.CustomerAdapter;
import com.example.pnlibrary.model.ClassifyBook;
import com.example.pnlibrary.model.ItemClick;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class ClassifyBookFragment extends Fragment {

    private RecyclerView rcView;
    private ClassifyBookDAO classifyBookDAO;
    private ArrayList<ClassifyBook> list;
    private EditText edtClassifyBookAdd;
    private Button btnAdd,btnEdit;
    private int idEdit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_classify_book,container,false);
        rcView = view.findViewById(R.id.rcView);
        classifyBookDAO = new ClassifyBookDAO(getContext());
        edtClassifyBookAdd = view.findViewById(R.id.edtClassifyBookAdd);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnEdit = view.findViewById(R.id.btnEdit);

        loadData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtClassifyBookAdd.getText().toString();
                if(name.length()==0){
                    Toast.makeText(getContext(), "vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                }else{
                    if(classifyBookDAO.addClassifyBook(name)){
                        Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        edtClassifyBookAdd.setText("");
                    }else{
                        Toast.makeText(getContext(), "thêm thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameAdd = edtClassifyBookAdd.getText().toString();
                if(classifyBookDAO.editClassifyBook(idEdit,nameAdd)){
                    loadData();
                    edtClassifyBookAdd.setText("");
                    btnEdit.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getContext(), "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return view;
    }
    private void loadData(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(linearLayoutManager);
        classifyBookDAO = new ClassifyBookDAO(getContext());
        list = classifyBookDAO.getListClassifyBook();
        ClassifyBookAdapter adapter = new ClassifyBookAdapter(list, getContext(), classifyBookDAO, new ItemClick() {
            @Override
            public void onClick(ClassifyBook classifyBook) {
                edtClassifyBookAdd.setText(classifyBook.getName());
                idEdit = classifyBook.getId();
                btnEdit.setVisibility(View.VISIBLE);
            }
        });
        rcView.setAdapter(adapter);
    }
}