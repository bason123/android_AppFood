package com.example.pnlibrary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlibrary.database.Helper;
import com.example.pnlibrary.model.User;

import java.util.ArrayList;

public class UserDAO {

    private Helper helper;

    public UserDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<User> getListUser(){
        ArrayList<User> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor  cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getInt(3)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addUser(String userName, String passWord, int classify){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userName",userName);
        contentValues.put("passWord",passWord);
        contentValues.put("classify",classify);

        long check =sqLiteDatabase.insert("USER", null, contentValues);
        return (!(check == -1));
    }

    public boolean editUser(int id, String userName, String passWord, int classify){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("userName", userName);
        contentValues.put("passWord", passWord);
        contentValues.put("classify", classify);

        long check = sqLiteDatabase.update("USER", contentValues, "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }
    public boolean deleteUser(int id){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        long check = sqLiteDatabase.delete("USER", "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }
    public int changePassword(String userName, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER WHERE userName= ? AND passWord = ?", new String[]{userName,oldPass});
        if (cursor.getCount()>0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("passWord",newPass);
            long check = sqLiteDatabase.update("USER",contentValues,"userName = ?",new String[]{userName});
            if (check == -1)return -1;

                return 1;
            }
            return 0;
        }

}
