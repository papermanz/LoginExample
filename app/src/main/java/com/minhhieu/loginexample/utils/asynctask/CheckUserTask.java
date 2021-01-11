package com.minhhieu.loginexample.utils.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.data.DatabaseLogin;
import com.minhhieu.loginexample.model.User;

public class CheckUserTask extends AsyncTask<Context, String, User> {
    private SQLiteDatabase db;
    private GetUserListener mListener;
    String userName;
    private final static String TABLE_NAME = "User";
    private final static String KEY_USER = "Username";


    public CheckUserTask(Context context, String userName) {
        db = new DatabaseLogin(context).getReadableDatabase();
        this.userName = userName;

    }
    @Override
    protected User doInBackground(Context... contexts) {
       User user = new User();

        String query = "Select Username FROM " + TABLE_NAME
                + " WHERE " + KEY_USER + " = "
                + "'" + userName + "'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                user.setUserName(cursor.getString(0));
            }
        }else{
            cursor.close();

        }
        db.close();

        return user;
    }

    @Override
    protected void onPostExecute(User data) {
        super.onPostExecute(data);
        if (mListener != null){
            mListener.onSuccess(data);
        }
    }
    public void setGetUserListener(GetUserListener listener){
        mListener = listener;
    }

    public interface GetUserListener {
        void onSuccess(User user);
    }
}
