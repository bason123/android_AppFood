package com.example.pnlibrary.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pnlibrary.database.Helper;
import com.example.pnlibrary.model.ClassifyBook;

import java.util.ArrayList;

public class ClassifyBookDAO {

    private Helper helper;

    public ClassifyBookDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<ClassifyBook> getListClassifyBook(){
        ArrayList<ClassifyBook> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CLASSIFYBOOK",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new ClassifyBook(cursor.getInt(0),cursor.getString(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean addClassifyBook(String name){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);

        long check =sqLiteDatabase.insert("CLASSIFYBOOK", null, contentValues);
        return (!(check == -1));
    }
    public boolean editClassifyBook(int id, String name){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        long check = sqLiteDatabase.update("CLASSIFYBOOK", contentValues, "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }

    // xóa loại sách:
    // xóa thành công: 0, xóa thất bại: -1; có sách tồn tại thể loại sách đang muốn xóa: 1
    public int deleteClassifyBook(int id){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BOOK WHERE id = ?",new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("CLASSIFYBOOK", "id= ?",new String[]{String.valueOf(id)});
        if(check == -1)
            return 0;
        return 1;

    }
}
