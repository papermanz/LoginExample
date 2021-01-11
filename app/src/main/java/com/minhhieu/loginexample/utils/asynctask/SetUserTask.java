package com.minhhieu.loginexample.utils.asynctask;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.minhhieu.loginexample.data.DatabaseLogin;
import com.minhhieu.loginexample.model.User;


public class SetUserTask extends AsyncTask<String, Void, String> {

    Context context;
    User user;

    public SetUserTask(Context context,User user){
        this.context = context;
        this.user = user;
    }


    @Override
    protected String doInBackground(String... strings) {
            DatabaseLogin databaseLogin = new DatabaseLogin(context);
            databaseLogin.addUser(user);
            return "Thêm thành công";

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
