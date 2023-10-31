package com.example.pnlibrary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlibrary.DAO.CustomerDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.model.Customer;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder>{

    private ArrayList<Customer> list;
    private Context context;
    private CustomerDAO customerDAO;

    public CustomerAdapter(ArrayList<Customer> list, Context context, CustomerDAO customerDAO) {
        this.list = list;
        this.context = context;
        this.customerDAO = customerDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_customer,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.edtCustomerName.setText(list.get(position).getName());
        holder.yearOfBirth.setText("Năm sinh: "+ list.get(position).getYearOfBirth());
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogEdit(list.get(position));
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = customerDAO.deleteCustomer(list.get(position).getId());
                switch (check){
                    case 1:
                        Toast.makeText(context, "xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "trùng mã thành viên trong phiếu mượn, không thể xóa", Toast.LENGTH_SHORT).show();
                        break;
                    default: break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView edtCustomerName,yearOfBirth;
        ImageView imgAvatar;
        Button btnEdit,btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtCustomerName = itemView.findViewById(R.id.NameCustomer);
            yearOfBirth = itemView.findViewById(R.id.yearOfBirth);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
    private void showDialogEdit(Customer customer){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.diaglog_edit_customer,null);
        builder.setView(view);

        TextView tvID = view.findViewById(R.id.ID);
        EditText edName = view.findViewById(R.id.edName);
        EditText edYearOfBirth = view.findViewById(R.id.edDateOfBirth);

        tvID.setText(customer.getId()+"");
        edName.setText(customer.getName());
        edYearOfBirth.setText(customer.getYearOfBirth());

        builder.setNegativeButton("lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameEdit = edName.getText().toString();
                String yearOfBirthEdit = edYearOfBirth.getText().toString();
                int id = customer.getId();
                boolean check = customerDAO.editCustomer(id,nameEdit,yearOfBirthEdit);
                if(nameEdit.length() > 0 && yearOfBirthEdit.length() > 0){
                    if(check){
                        Toast.makeText(context, "cập nhật thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(context, "có lỗi, thử lại sau", Toast.LENGTH_SHORT).show();
                    } 
                }else{
                    Toast.makeText(context, "vui lòng không bỏ trống", Toast.LENGTH_SHORT).show();
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
        list = customerDAO.getListCustomer();
        notifyDataSetChanged();
    }
}
