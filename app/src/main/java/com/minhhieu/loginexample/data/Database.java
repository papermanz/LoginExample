package com.minhhieu.loginexample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.minhhieu.loginexample.model.User;

public class Database extends SQLiteOpenHelper {
    //Data version
    private static final int DATABASE_VERSION = 1;
    //Database name
    private final static String DATABASE_NAME ="LGAPP.sqlite";
    //Table name
    private final static String TABLE_NAME = "User";

    // Login Table Columns names
    private final static String KEY_USER = "Username";
    private final static String KEY_PASS = "Password";
    private final static String KEY_NAME = "Fullname";
    private final static String KEY_EMAIL = "Email";
    private final static String KEY_PHONE = "Phone";


    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + KEY_USER + " TEXT PRIMARY KEY," + KEY_PASS + " TEXT,"
            + KEY_NAME + " TEXT," + KEY_EMAIL + " TEXT," + KEY_PHONE + " TEXT"+ ")";



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        Log.d("AAA", "Tạo Data thành công");
    }

    public void addUser(SQLiteDatabase db, String User, String Pass, String fullName, String Email, String Phone){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER,User);
        values.put(KEY_PASS,Pass);
        values.put(KEY_NAME,fullName);
        values.put(KEY_EMAIL,Email);
        values.put(KEY_PHONE,Phone);
        sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


//Check User login
//    public boolean checkUser(String userName, String passWord){
//
//        //Cột cần tìm
//        String[] columns = {
//                KEY_USER
//        };
//        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
//
//        //check
//        String selection = KEY_USER + " = ?" + " AND " + KEY_PASS + " = ?";
//
//        // đối số lựa chọn
//        String[] selectionArgs = {userName, passWord};
//
//        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,columns,                    //columns to return
//                selection,                  //columns for the WHERE clause
//                selectionArgs,              //The values for the WHERE clause
//                null,                       //group the rows
//                null,                       //filter by row groups
//                null);
//        int cursorCount = cursor.getCount();
//
//        cursor.close();
//        sqLiteDatabase.close();
//        if (cursorCount > 0) {
//            return true;
//        }
//        return false;
//    }


//    //check user tồn tại
//    public boolean checkUser(String user) {
//
//        //cột cần check
//        String[] columns = {
//                KEY_USER
//        };
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        // tiêu chí lựa chọn
//        String selection = KEY_USER + " = ?";
//
//        // đối số lựa chọn
//        String[] selectionArgs = {user};
//
//        // Truy vấn bảng dữ liệu với điều kiện
//        Cursor cursor = db.query(TABLE_NAME, //Table to query
//                columns,                    //columns to return
//                selection,                  //columns for the WHERE clause
//                selectionArgs,              //The values for the WHERE clause
//                null,                       //group the rows
//                null,                      //filter by row groups
//                null);                      //The sort order
//        int cursorCount = cursor.getCount();
//        cursor.close();
//        db.close();
//
//        if (cursorCount > 0) {
//            return true;
//        }
//
//        return false;
//    }


}
