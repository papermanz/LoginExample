package com.minhhieu.loginexample.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.model.User;

public class SetUserTask extends AsyncTask<Context, Void, User> {
    private final static String TABLE_NAME = "User";

    // Login Table Columns names
    private final static String KEY_USER = "Username";
    private final static String KEY_PASS = "Password";
    private final static String KEY_NAME = "Fullname";
    private final static String KEY_EMAIL = "Email";
    private final static String KEY_PHONE = "Phone";


    private SQLiteDatabase db;
    String User;
    private SetUserListener mListener;

    public SetUserTask(Context context, String userName) {
        db = new Database(context).getWritableDatabase();
        this.User = userName;
    }


    @Override
    protected User doInBackground(Context... contexts) {
        User user = new User();
        String query = "Select Username FROM " + TABLE_NAME
                + " WHERE " + KEY_USER + " = "
                + "'" + User + "'";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            cursor.close();
        }else{
                ContentValues values = new ContentValues();
                values.put(KEY_USER,user.getUserName());
                values.put(KEY_PASS,user.getPassWord());
                values.put(KEY_NAME,user.getFullName());
                values.put(KEY_EMAIL,user.getEmail());
                values.put(KEY_PHONE,user.getPhone());
                db.insert(TABLE_NAME,null,values);
        }
        db.close();


        //parse cursor to user

        //close db
        return user;

    }
    @Override
    protected void onPostExecute(User data) {
        super.onPostExecute(data);
        if (mListener != null){
            mListener.onSuccess(data);
        }
    }

    public void setGetUserListener(SetUserListener listener){
        mListener = listener;
    }



    public interface SetUserListener {
        void onSuccess(User user);
    }
}
