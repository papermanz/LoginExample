package com.minhhieu.loginexample.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.model.User;


public class GetUserTask extends AsyncTask<Context, Void, User> {
    private SQLiteDatabase db;
    private GetUserListener mListener;
    String User, Pass;
    private final static String TABLE_NAME = "User";
    private final static String KEY_USER = "Username";
    private final static String KEY_PASS = "Password";

//    private Cursor cursor;

    public GetUserTask(Context context, String userName, String pass) {
        db = new Database(context).getReadableDatabase();
        this.User = userName;
        this.Pass = pass;
    }

    @Override
    protected User doInBackground(Context... contexts) {

        //run query
        User user = new User();
        //cursor get user from db
        String query = "Select Username, Password FROM " + TABLE_NAME
                + " WHERE " + KEY_USER + " = "
                + "'" + User + "' AND " + KEY_PASS + " = "
                + "'" + Pass + "'" ;

            Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                user.setUserName(cursor.getString(0));
                user.setPassWord(cursor.getString(1));
            }
        }else{

            cursor.close();

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

    public void setGetUserListener(GetUserListener listener){
        mListener = listener;
    }

    public interface GetUserListener {
        void onSuccess(User user);
    }
}
