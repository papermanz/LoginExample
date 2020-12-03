package com.minhhieu.loginexample.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.model.User;

public class GetUserTask extends AsyncTask<Context, Void, User> {
    private SQLiteDatabase db;
    private Database database;
    private GetUserListener mListener;




    public GetUserTask(Context context, String userName, String pass) {
        db = new Database(context).getReadableDatabase();
    }

    @Override
    protected User doInBackground(Context... contexts) {
        //run query
        User user = new User();
        //cursor get user from db

//            Cursor cursor = database.QueryData("SELECT Username, Password FROM User");
//        if (cursor != null && cursor.moveToFirst()) {
//            do {
//                user.setUserName(cursor.getString(0));
//                user.setPassWord(cursor.getString(1));
//
//            } while (cursor.moveToNext());
//
//        }
//        database.close();

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

    public void setGetUserListener(GetUserListener listener){
        mListener = listener;
    }

    public interface GetUserListener {
        void onSuccess(User user);
    }
}
