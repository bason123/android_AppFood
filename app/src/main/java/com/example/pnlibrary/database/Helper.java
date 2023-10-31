package com.example.pnlibrary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    public Helper(@Nullable Context context) {
        super(context, "quanlythuvien", null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(id integer primary key autoincrement, userName text, passWord text, classify integer)");
        db.execSQL("CREATE TABLE CUSTOMER(id integer primary key autoincrement, name text, count integer, yearOfBirth integer)");
        db.execSQL("CREATE TABLE CLASSIFYBOOK(id integer primary key autoincrement, name text)");
        db.execSQL("CREATE TABLE BOOK(id integer primary key autoincrement, name text, idClassifyBook integer, author text, price integer, FOREIGN KEY (idClassifyBook) REFERENCES CLASSIFYBOOK(id))");
        db.execSQL("CREATE TABLE CALLCARD(id integer primary key autoincrement, idBook integer, idCustomer integer, nameBook text, nameCustomer text, idUser integer, date text, refunded integer, dateRefund String, price integer, " +
                "FOREIGN KEY (idCustomer) REFERENCES CUSTOMER(id), FOREIGN KEY (idBook) REFERENCES BOOK(id)," +
                " FOREIGN KEY (idUser) REFERENCES User(id))");

        db.execSQL("INSERT INTO USER VALUES(1,'admin','1234',1),(2,'librarian','1234',0)");
        db.execSQL("INSERT INTO CUSTOMER VALUES(1,'thanh hoa',0,1996),(2,'duc hoang',0,1996)");
        db.execSQL("INSERT INTO CLASSIFYBOOK VALUES(1,'CNTT'),(2,'Kinh TE')");
        db.execSQL("INSERT INTO BOOK VALUES(1,'android nang cao',1,'nguyen ba son',30000),(2,'warren buffett',2,'truong minh thi',48000)");
        db.execSQL("INSERT INTO CALLCARD VALUES(1,1,1,'android nang cao','thanh hoa',1,'2023/05/03',0,'',30000),(2,2,2,'warren buffett','duc hoang',2,'2023/03/05',0,'',48000)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(newVersion != oldVersion){
            db.execSQL("DROP TABLE IF EXISTS USER");
            db.execSQL("DROP TABLE IF EXISTS CUSTOMER");
            db.execSQL("DROP TABLE IF EXISTS CLASSIFYBOOK");
            db.execSQL("DROP TABLE IF EXISTS BOOK");
            db.execSQL("DROP TABLE IF EXISTS CALLCARD");
            onCreate(db);
        }
    }
}
