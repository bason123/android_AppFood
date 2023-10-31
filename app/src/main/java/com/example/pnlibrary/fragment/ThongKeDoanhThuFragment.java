package com.example.pnlibrary.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pnlibrary.DAO.CallCardDAO;
import com.example.pnlibrary.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongKeDoanhThuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongKeDoanhThuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Calendar calendar;

    public ThongKeDoanhThuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongKeDoanhThuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongKeDoanhThuFragment newInstance(String param1, String param2) {
        ThongKeDoanhThuFragment fragment = new ThongKeDoanhThuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_ke_doanh_thu, container, false);
        TextView edStart = view.findViewById(R.id.edStart);
        TextView edEnd = view.findViewById(R.id.edEnd);
        Button btnThongKe = view.findViewById(R.id.btnThongKe);
        TextView tvDisplay = view.findViewById(R.id.tvDisplay);

        calendar = Calendar.getInstance();

        edStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(edStart);
            }
        });
        edEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(edEnd);
            }
        });
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallCardDAO callCardDAO = new CallCardDAO(getContext());
                String dateStart = edStart.getText().toString();
                String dateEnd = edEnd.getText().toString();
                if(dateStart.length()>0 && dateEnd.length()>0){
                    int doanhThu = callCardDAO.getDoanhThu(dateStart,dateEnd);
                    tvDisplay.setText("tổng doanh thu: "+doanhThu+" VNĐ");
                }else{
                    Toast.makeText(getContext(), "chọn ngày thống kê", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }
    public void setDate(TextView textView){
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
                        textView.setText(year + "/" + thang + "/" + ngay);
                    }
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)

        );
        datePickerDialog.show();
    }
}