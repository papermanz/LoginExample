package com.minhhieu.loginexample.utils.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.data.Database;
import com.minhhieu.loginexample.model.User;

public class CheckUserTask extends AsyncTask<Context, String, User> {
    private SQLiteDatabase db;
    private GetUserListener mListener;
    String User;
    private final static String TABLE_NAME = "User";
    private final static String KEY_USER = "Username";


    public CheckUserTask(Context context, String userName) {
        db = new Database(context).getReadableDatabase();
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
