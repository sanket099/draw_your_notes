package com.example.paint;

import android.accounts.Account;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class database_helper extends SQLiteOpenHelper {
    public static  final  String table_name = "paint";
    public static final  String col1 = "ID";
    public static final  String col2 = "IMAGE";
    /*
    public   static final  String col3 = "CONTENT";
    public   static  final  String col4 = "DATE";*/

        public database_helper(@Nullable Context context) {
        super(context, table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = " CREATE TABLE " + table_name + "( ID INTEGER PRIMARY KEY AUTOINCREMENT , IMAGE BLOB )"  ;
        db.execSQL(create_table);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + table_name);
        onCreate(db);


    }
    public void add(byte[] bytes){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues values = new ContentValues();
// The column names here will depend on your schema, of course.
// This would work with:
// CREATE TABLE `signature_table` (`username` VARCHAR, `signature_image` BLOB);

        values.put("IMAGE", bytes);
        //String query = "INSERT INTO " + table_name + "VALUES" + values;
        db.insert(table_name, null, values);
        //Cursor cursor = db.rawQuery(query,null);
        //return cursor.getString(cursor.getColumnIndex("ID"));





    }

    public byte[] getCurrentAccount() {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM paint";
        byte[] h;
        Cursor cursor = db.rawQuery(sql, new String[] {});

        if(cursor.moveToFirst()){
           do {

               h = cursor.getBlob(1);
           }
           while (cursor.moveToNext());
            return h;
        }
        else{
            return null;
        }

    }
}
