package com.minhhieu.loginexample.utils.asynctask;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import com.minhhieu.loginexample.data.Database;


public class SetUserTask extends AsyncTask<String, Void, String> {
    Context context;

    public SetUserTask(Context context){
        this.context = context;
    }


    @Override
    protected String doInBackground(String... strings) {
        Database database = new Database(context);
        String method = strings[0];
        if(method.equals("add_user")){
            String User = strings[1];
            String Pass = strings[2];
            String fullName = strings[3];
            String Email = strings[4];
            String Phone = strings[5];
            SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
            database.addUser(sqLiteDatabase,User,Pass,fullName,Email,Phone);
            sqLiteDatabase.close();
            return "Thêm thành công";
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
