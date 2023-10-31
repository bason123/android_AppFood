package com.example.pnlibrary.DAO;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.pnlibrary.database.Helper;
import com.example.pnlibrary.model.Book;
import com.example.pnlibrary.model.Customer;

import java.util.ArrayList;

public class BookDAO {
    private Helper helper;

    public BookDAO(Context context){
        helper = new Helper(context);
    }

    public ArrayList<Book> getListBook(){
        ArrayList<Book> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase =helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT b.id, b.name, b.price, b.idClassifyBook, c.name FROM BOOK b, CLASSIFYBOOK c WHERE b.idClassifyBook = c.id",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3),cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean addBook(String name, int price, int idClassifyBook){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("idClassifyBook",idClassifyBook);
        contentValues.put("price",price);

        long check =sqLiteDatabase.insert("BOOK", null, contentValues);
        return (!(check == -1));
    }

    public boolean editBook(int id, String name,int price, int idClassifyBook){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("idClassifyBook",idClassifyBook);
        contentValues.put("price",price);
        long check = sqLiteDatabase.update("BOOK", contentValues, "id= ?",new String[]{String.valueOf(id)});
        return (!(check == -1));
    }
    //xóa thành công: 0
    //thất bại: -1
    // trùng khóa ngoại: 1
    public int deleteBook(int id){
        SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CALLCARD WHERE id = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount()>0){
            return -1;
        }
        long check = sqLiteDatabase.delete("BOOK", "id= ?",new String[]{String.valueOf(id)});
        if(check == -1){
            return 0;
        }
        return 1;
    }
    public ArrayList<Book> getTop10(){
        ArrayList<Book> list = new ArrayList<>();
//       Toast.makeText(getApplicationContext(), ""+list.size(), Toast.LENGTH_SHORT).show();
        SQLiteDatabase sqLiteDatabase = helper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT cc.idBook, b.name, COUNT(cc.idBook) FROM CALLCARD cc, BOOK b WHERE cc.idBook = b.id GROUP BY " +
                "cc.idBook, b.id ORDER BY COUNT(cc.idBook) DESC LIMIT 10",null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
            do{
                list.add(new Book(cursor.getInt(0),cursor.getString(1),cursor.getInt(2)));
            }while (cursor.moveToNext());
        }

        return list;

    }
}
