package com.example.pnlibrary.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pnlibrary.DAO.CustomerDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.adapter.CustomerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CustomerFragment extends Fragment {

    private RecyclerView rcView;
    private FloatingActionButton fltAdd;
    private CustomerDAO customerDAO;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer,container,false);
        rcView = view.findViewById(R.id.rcView);
        fltAdd = view.findViewById(R.id.fltAdd);
        customerDAO = new CustomerDAO(getContext());

        loadData();

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }
    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_customer,null);
        builder.setView(view);

        EditText edName = view.findViewById(R.id.edName);
        EditText edDateOfBirth = view.findViewById(R.id.edDateOfBirth);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameAdd = edName.getText().toString();
                String dateOfBirthAdd = edDateOfBirth.getText().toString();
                
                boolean check = customerDAO.addCustomer(nameAdd,dateOfBirthAdd);
                if(check){
                    Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setPositiveButton("hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = builder.create();
        builder.show();
    }
    private void loadData(){
        linearLayoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(linearLayoutManager);
        CustomerAdapter adapter = new CustomerAdapter(customerDAO.getListCustomer(),getContext(),customerDAO);
        rcView.setAdapter(adapter);
    }
}