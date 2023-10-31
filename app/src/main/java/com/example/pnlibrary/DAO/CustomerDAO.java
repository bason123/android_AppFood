package com.example.pnlibrary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlibrary.database.Helper;
import com.example.pnlibrary.model.Customer;
import com.example.pnlibrary.model.User;

import java.util.ArrayList;

public class CustomerDAO {
    private Helper helper;

    public CustomerDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Customer> getListCustomer(){
        ArrayList<Customer> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CUSTOMER",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Customer(cursor.getInt(0),cursor.getString(1),cursor.getString(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addCustomer(String name, String yearOfBirth){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("yearOfBirth",yearOfBirth);

        long check =sqLiteDatabase.insert("CUSTOMER", null, contentValues);
        return (!(check == -1));
    }

    public boolean editCustomer(int id, String name, String yearOfBirth){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("yearOfBirth",yearOfBirth);
        long check = sqLiteDatabase.update("CUSTOMER", contentValues, "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }

    //xóa thành công: 1; thất bại: 0; trùng mã trong phiếu mượn -1
    public int deleteCustomer(int id){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CALLCARD WHERE idCustomer = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount()>0){
            return -1;
        }
        long check = sqLiteDatabase.delete("CUSTOMER", "id= ?",new String[]{String.valueOf(id)});
        if(check == -1)
            return 0;
        return 1;
    }
}
