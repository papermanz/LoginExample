package com.minhhieu.loginexample.utils.asynctask;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.minhhieu.loginexample.data.DatabaseBook;
import com.minhhieu.loginexample.model.Book;

public class CheckBookTask extends AsyncTask<Context,String, Book> {
    private SQLiteDatabase db;
    private CheckBookTask.GetBookListener mListener;
    String tenSach;
    private final static String TABLE_NAME = "Book";
    private final static String KEY_TENSACH = "TenSach";


    public CheckBookTask(Context context, String tenSach){
        db = new DatabaseBook(context).getReadableDatabase();
        this.tenSach = tenSach;
    }

    @Override
    protected Book doInBackground(Context... contexts) {
        Book book = new Book();

        String query = "Select TenSach FROM " + TABLE_NAME
                + " WHERE " + KEY_TENSACH + " = "
                + "'" + tenSach + "'";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                book.setTenSach(cursor.getString(0));
            }
        }else{
            cursor.close();

        }

        db.close();

        return book;

    }
    @Override
    protected void onPostExecute(Book data) {
        super.onPostExecute(data);
        if (mListener != null){
            mListener.onSuccess(data);
        }
    }
    public void setGetBookListener(CheckBookTask.GetBookListener listener){
        mListener = listener;
    }

    public interface GetBookListener {
        void onSuccess(Book book);
    }
}
