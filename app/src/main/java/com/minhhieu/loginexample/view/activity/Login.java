package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.Database;

public class Login extends AppCompatActivity {
    Button btncallSignUp,btnLogin;
    ImageView image;
    TextView Logo, slogan;
    TextInputLayout edtuserName,edtPassword;
    Database database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initLayout();
        database=new Database(Login.this,"LGAPP.sqlite",null,1);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtuserName.getEditText().getText().toString().trim().equals("")||edtPassword.getEditText().getText().toString().trim().equals("")){
                    Toast.makeText(Login.this, "Enter your email or password!", Toast.LENGTH_LONG).show();
                }else {
                    Cursor user = database.GetData("SELECT * FROM User");
                    Cursor c = database.GetData("select * from User where Username='" + edtuserName.getEditText().getText().toString().trim() + "' and Password='" + edtPassword.getEditText().getText().toString().trim() + "'");
                    c.moveToFirst();
                    if (c.getCount() > 0) {
                        Intent intent= new Intent(Login.this,MainActivity2.class);
                        //intent.putExtra("namee",edtuserName.getEditText().getText().toString().trim());
                        Log.d("LOGIN","SUCCESS ");
                        startActivity(intent);
                        database.close();
                    } else
                        Toast.makeText(Login.this, "Đăng nhập sai, vui lòng kiểm tra lại tài khoản !", Toast.LENGTH_SHORT).show();
                }

            }
        });

        //Sự kiện button Signup
        btncallSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Signup.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(Logo,"logo_text");
                pairs[2] = new Pair<View,String>(slogan,"logo_text");
                pairs[3] = new Pair<View,String>(edtuserName,"username_trans");
                pairs[4] = new Pair<View,String>(edtPassword,"password_trans");
                pairs[5] = new Pair<View,String>(btnLogin,"login_trans");
                pairs[6] = new Pair<View,String>(btncallSignUp,"signup_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });
    }

    //bắt sự kiện
    public void initLayout(){
        btncallSignUp = (Button) findViewById(R.id.signup);
        btnLogin = (Button) findViewById(R.id.login);
        image = (ImageView) findViewById(R.id.logo_login);
        Logo = (TextView) findViewById(R.id.logo_text);
        slogan = (TextView) findViewById(R.id.slogan_login);
        edtuserName = (TextInputLayout) findViewById(R.id.username);
        edtPassword = (TextInputLayout) findViewById(R.id.password);
    }
}