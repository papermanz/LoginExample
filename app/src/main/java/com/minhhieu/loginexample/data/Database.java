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

    public Cursor QueryData(String sql){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL(sql);
        return null;
    }

    public Cursor GetData(String sql){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        return sqLiteDatabase.rawQuery(sql,null);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        Log.d("AAA", "Tạo Data thành công");
    }

    public void addUser(User user){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER,user.getUserName());
        values.put(KEY_PASS,user.getPassWord());
        values.put(KEY_NAME,user.getFullName());
        values.put(KEY_EMAIL,user.getEmail());
        values.put(KEY_PHONE,user.getPhone());

        sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


//Check User login
    public boolean checkUser(String userName, String passWord){

        //Cột cần tìm
        String[] columns = {
                KEY_USER
        };
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        //check
        String selection = KEY_USER + " = ?" + " AND " + KEY_PASS + " = ?";

        // đối số lựa chọn
        String[] selectionArgs = {userName, passWord};

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME,columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);
        int cursorCount = cursor.getCount();

        cursor.close();
        sqLiteDatabase.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    //check user tồn tại
    public boolean checkUser(String user) {

        //cột cần check
        String[] columns = {
                KEY_USER
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // tiêu chí lựa chọn
        String selection = KEY_USER + " = ?";

        // đối số lựa chọn
        String[] selectionArgs = {user};

        // Truy vấn bảng dữ liệu với điều kiện
        Cursor cursor = db.query(TABLE_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


}
