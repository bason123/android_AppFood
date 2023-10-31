package com.example.pnlibrary.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pnlibrary.DAO.CallCardDAO;
import com.example.pnlibrary.R;
import com.example.pnlibrary.activity.MainActivity;
import com.example.pnlibrary.fragment.FragmentListCallCard;
import com.example.pnlibrary.model.CallCard;
import com.example.pnlibrary.model.ItemClickCallCard;

import java.util.ArrayList;
import java.util.Calendar;

public class CallCardAdapter extends RecyclerView.Adapter<CallCardAdapter.ViewHolder> {

    private ArrayList<CallCard> list;
    private Context context;
    private CallCardDAO callCardDAO;
    private Calendar calendar;
    private String dateRefund;
    private ItemClickCallCard itemClickCallCard;

    public CallCardAdapter(ArrayList<CallCard> list, Context context,CallCardDAO callCardDAO,ItemClickCallCard itemClickCallCard) {
        this.list = list;
        this.context = context;
        this.callCardDAO = callCardDAO;
        this.itemClickCallCard = itemClickCallCard;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_callcard,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.edtBookName.setText(list.get(position).getNameBook());
        holder.edtCustomerName.setText(list.get(position).getNameCustomer());
        holder.edtDate.setText(list.get(position).getDate());
        holder.chkCheck.setText(list.get(position).getDateRefund());
        if(list.get(position).getRefunded() == 1){
            holder.chkCheck.setChecked(true);

            holder.chkCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(callCardDAO.changeRefunded(0,list.get(position).getId(),"chưa trả")){
                        Toast.makeText(context, "cập nhật chưa trả", Toast.LENGTH_SHORT).show();
                        loadData();
                    }else{
                        Toast.makeText(context, "lỗi ! thử lại sau", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            holder.chkCheck.setChecked(false);
            holder.chkCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    calendar = Calendar.getInstance();
                    dateRefund = calendar.get(Calendar.DAY_OF_MONTH)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.YEAR);
                    DatePickerDialog dialog = new DatePickerDialog(
                            context,
                            new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                    int month1 = month + 1;
                                    dateRefund = year + "/" + month1 + "/" + dayOfMonth;
                                    if(callCardDAO.changeRefunded(1,list.get(position).getId(),dateRefund)){
                                        Toast.makeText(context, "cập nhật đã trả", Toast.LENGTH_SHORT).show();
                                        loadData();
                                    }else{
                                        Toast.makeText(context, "lỗi ! thử lại sau", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH)
                    );
                    Toast.makeText(context, dateRefund, Toast.LENGTH_SHORT).show();
                    dialog.show();


                }
            });
        }

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                itemClickCallCard.onClickCallCard(list.get(position));
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView edtBookName,edtCustomerName,edtDate;
        CheckBox chkCheck;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            edtBookName = itemView.findViewById(R.id.tvBookName);
            edtCustomerName = itemView.findViewById(R.id.tvCustomerName);
            edtDate = itemView.findViewById(R.id.tvDate);
            chkCheck = itemView.findViewById(R.id.chkCheck);
            cardView = itemView.findViewById(R.id.one_item);
            cardView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            ((Activity)context).getMenuInflater().inflate(R.menu.long_click_item,menu);
        }
    }
    private void loadData(){
        list = callCardDAO.getCallCard();
        notifyDataSetChanged();
    }
    public void removeItem(int position){
        callCardDAO.deleteCallCard(position);
        loadData();;
    }


}
