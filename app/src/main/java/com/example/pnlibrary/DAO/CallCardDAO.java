package com.example.pnlibrary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlibrary.database.Helper;
import com.example.pnlibrary.model.CallCard;

import java.util.ArrayList;

public class CallCardDAO {

    private Helper helper;

    public CallCardDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<CallCard> getCallCard(){
        ArrayList<CallCard> list = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CALLCARD ORDER BY CALLCARD.id DESC",null);

        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new CallCard(cursor.getInt(0),cursor.getString(3),cursor.getString(4),cursor.getString(6), cursor.getInt(7),
                        cursor.getString(8)));

            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean addCallCard(int idBook, int idCustomer,String nameBook, String nameCustomer, int idUser, String date, int price){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idBook",idBook);
        contentValues.put("idCustomer",idCustomer);
        contentValues.put("nameBook",nameBook);
        contentValues.put("nameCustomer",nameCustomer);
        contentValues.put("idUser",idUser);
        contentValues.put("date",date);
        contentValues.put("dateRefund","chưa trả");
        contentValues.put("price",price);

        long check =sqLiteDatabase.insert("CALLCARD", null, contentValues);
        return (!(check == -1));
    }

    public boolean editCallCard(int id, int idBook, int idCustomer, int idUser, String date, int price){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("idBook",idBook);
        contentValues.put("idCustomer",idCustomer);
        contentValues.put("idUser",idUser);
        contentValues.put("date",date);
        contentValues.put("price",price);
        long check = sqLiteDatabase.update("CALLCARD", contentValues, "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }
    public boolean deleteCallCard(int id){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("CALLCARD", "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }
    public boolean changeRefunded(int isRefunded, int idCallCard, String dateRefund){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("refunded",isRefunded);
        contentValues.put("dateRefund",dateRefund);
        long check = sqLiteDatabase.update("CALLCARD", contentValues, "id= ?",new String[]{String.valueOf(idCallCard)});
        return (!(check == -1));
    }
    public int getDoanhThu(String dateStart,String dateEnd){
        dateStart = dateStart.replace("/","");
        dateEnd = dateEnd.replace("/","");
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(price) FROM CALLCARD WHERE substr(date,7)||substr(date,1,2) BETWEEN ? AND ?",new String[]{dateStart,dateEnd});
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        return 0;
    }
}
