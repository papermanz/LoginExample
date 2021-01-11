package com.minhhieu.loginexample.utils.asynctask;

import android.content.Context;

import android.os.AsyncTask;


import com.minhhieu.loginexample.data.DatabaseBook;

import com.minhhieu.loginexample.model.Book;

public class SetBookTask extends AsyncTask<Book, Void, String> {

    private SetBookTask.GetBookListener mListener;
    DatabaseBook databaseBook;
    Book book;

    public SetBookTask(Context context,Book book){
        databaseBook = new DatabaseBook(context) ;
        this.book = book;
    }

    @Override
    protected String doInBackground(Book... books) {
        databaseBook.addBook(book);
        return "Thêm sách thành công!";
    }

    @Override
    protected void  onPostExecute(String s) {
        super.onPostExecute(s);
        if (mListener != null){
            mListener.onSuccess(s);
        }
    }
    public void setGetBookListener(SetBookTask.GetBookListener listener){
        mListener = listener;
    }

    public interface GetBookListener {
        void onSuccess(String s);
    }
}
