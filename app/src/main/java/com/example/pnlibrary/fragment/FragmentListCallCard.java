package com.example.pnlibrary.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlibrary.DAO.BookDAO;
import com.example.pnlibrary.DAO.CallCardDAO;
import com.example.pnlibrary.DAO.CustomerDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.adapter.CallCardAdapter;
import com.example.pnlibrary.adapter.CustomSpinnerAdapterBook;
import com.example.pnlibrary.adapter.CustomSpinnerAdapterCustomer;
import com.example.pnlibrary.model.Book;
import com.example.pnlibrary.model.CallCard;
import com.example.pnlibrary.model.Customer;
import com.example.pnlibrary.model.ItemClickCallCard;
import com.example.pnlibrary.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class FragmentListCallCard extends Fragment {

    private RecyclerView rcView;
    private FloatingActionButton fltAdd;
    private CallCardDAO callCardDAO;
    private BookDAO bookDAO;
    private CustomerDAO customerDAO;
    User userCurrent;
    private Calendar calendar;
    private int idBookAdd,idCustomerAdd;
    private String nameBookAdd, nameCustomerAdd;
    private int priceAdd;
    CallCardAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    ArrayList<CallCard> list;
    CallCard callCardClick = null;

    public FragmentListCallCard(User userCurrent) {
        this.userCurrent = userCurrent;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listcallcard_fragment_layout,container,false);
        rcView = view.findViewById(R.id.rcView);
        fltAdd = view.findViewById(R.id.fltAdd);
        callCardDAO = new CallCardDAO(getContext());
        bookDAO = new BookDAO(getContext());
        customerDAO = new CustomerDAO(getContext());

        loadData();

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAdd();
            }
        });


        return view;
    }
    private void showDialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_callcard,null);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
        // xu ly code trong dialog
        //set date hien tai cho tvdate
        TextView tvDatePicker = view.findViewById(R.id.DatePicker);
        calendar = Calendar.getInstance();
        String current = calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
        tvDatePicker.setText(current);
        // su kien chon ngay datepicker
        tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tvDatePicker);
            }
        });
        //load data len spinner sách và spinner khách hàng
        Spinner spnBookName = view.findViewById(R.id.spnBookName);
        Spinner spnCustomer = view.findViewById(R.id.spnCustomerName);
        loadDataSpinner(spnCustomer, spnBookName);
        //su kien chon tren spinner
        idBookAdd = -1;
        nameBookAdd = ""; nameCustomerAdd = "";priceAdd=0;
        spnBookName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idBookAdd = ((Book)parent.getAdapter().getItem(position)).getId();
                nameBookAdd = ((Book) parent.getAdapter().getItem(position)).getName();
                priceAdd = ((Book) parent.getAdapter().getItem(position)).getPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        idCustomerAdd = -1;
        spnCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idCustomerAdd = ((Customer)parent.getAdapter().getItem(position)).getId();
                nameCustomerAdd = ((Customer) parent.getAdapter().getItem(position)).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        // set text default cho edt user thu thu
        TextView tvuser = view.findViewById(R.id.user);
        tvuser.setText(userCurrent.getUserName());
        //
        Button btnAdd = view.findViewById(R.id.btnSave);
        Button btnCancel = view.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String date = tvDatePicker.getText().toString();
                boolean check = callCardDAO.addCallCard(idBookAdd,idCustomerAdd,nameBookAdd,nameCustomerAdd,userCurrent.getId(),date,priceAdd);
                if(check){
                    Toast.makeText(getContext(), "thêm thành công", Toast.LENGTH_SHORT).show();
                    alertDialog.dismiss();
                    loadData();
                }else{
                    Toast.makeText(getContext(), "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
    private void loadData(){
        list = callCardDAO.getCallCard();

        linearLayoutManager = new LinearLayoutManager(getContext());
        rcView.setLayoutManager(linearLayoutManager);
        adapter = new CallCardAdapter(callCardDAO.getCallCard(), getContext(), callCardDAO, new ItemClickCallCard() {
            @Override
            public void onClickCallCard(CallCard callCard) {
                callCardClick = callCard;
            }
        });
        rcView.setAdapter(adapter);
    }
    private void showDateDialog(TextView edtNgay){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String ngay = "";
                        String thang = "";
                        if(dayOfMonth < 10){
                            ngay = "0"+dayOfMonth;
                        }else{
                            ngay = String.valueOf(dayOfMonth);
                        }
                        if((month+1)<10){
                            thang = "0" + (month+1);
                        }else{
                            thang = String.valueOf(month+1);
                        }
                        edtNgay.setText(year + "/" + thang + "/" + ngay);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }

    private void loadDataSpinner(Spinner spnCustomer, Spinner spnBook){
        ArrayList<Customer> listCustomer = customerDAO.getListCustomer();
        ArrayList<Book> listBook = bookDAO.getListBook();
        CustomSpinnerAdapterBook adapterBook = new CustomSpinnerAdapterBook(getContext(),listBook);
        spnBook.setAdapter(adapterBook);
        CustomSpinnerAdapterCustomer adapterCustomer = new CustomSpinnerAdapterCustomer(getContext(),listCustomer);
        spnCustomer.setAdapter(adapterCustomer);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.meuEdit:
                Toast.makeText(getContext(), "menu edit", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menuDelete:
                if(callCardClick!=null){
                    boolean check = callCardDAO.deleteCallCard(callCardClick.getId());
                    if(check){
                        Toast.makeText(getContext(), "xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(getContext(), "có lõi, thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
}
