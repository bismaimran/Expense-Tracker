package com.example.expensemanage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context,"Productsdatabase.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {

        DB.execSQL("Create Table ProductDetails(prodname TEXT primary key,price Text,qty TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int oldVersion, int newVersion) {
        DB.execSQL("drop Table if exists ProductDetails");

    }
    public Boolean insertproductdata(String prodname,String price, String qty){
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("prodname",prodname);
        contentValues.put("price",price);
        contentValues.put("qty",qty);
        long result=DB.insert("ProductDetails",null,contentValues);
        if (result==-1){
            return false;
        }else {
            return true;
        }

    }
    public Cursor getdata(){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor=DB.rawQuery("Select * from ProductDetails",null);
        return cursor;
    }
}
