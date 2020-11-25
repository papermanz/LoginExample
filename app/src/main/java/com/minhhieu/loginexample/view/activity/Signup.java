package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class Signup extends AppCompatActivity {
    ImageView logoLogin;
    TextView tvSignup,tvSloganLogin;
    Button btnsignUp, btnbackLogin;
    TextInputLayout edtfullName, edtuserName, edtemail, edtphone, edtpassword;
    Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        initLayout();
        btnbackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup.this,Login.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logoLogin,"logo_image");
                pairs[1] = new Pair<View,String>(tvSignup,"logo_text");
                pairs[2] = new Pair<View,String>(tvSloganLogin,"logo_text");
                pairs[3] = new Pair<View,String>(edtuserName,"username_trans");
                pairs[4] = new Pair<View,String>(edtpassword,"password_trans");
                pairs[5] = new Pair<View,String>(btnsignUp,"login_trans");
                pairs[6] = new Pair<View,String>(btnbackLogin,"signup_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Signup.this,pairs);
                startActivity(intent,options.toBundle());
            }
        });

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtfullName.getEditText().getText().toString().trim().equals("")||edtuserName.getEditText().getText().toString().trim().equals("")||edtemail.getEditText().getText().toString().trim().equals("")||edtphone.getEditText().getText().toString().trim().equals("")||edtpassword.getEditText().getText().toString().trim().equals("")){
                    Toast.makeText(Signup.this,"Mời bạn nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }else{
                    database = new Database(Signup.this,"LGAPP.sqlite",null,1);
                    database.QueryData("CREATE TABLE IF NOT EXISTS User(Username Char(16) PRIMARY KEY, Password Char(20),Fullname Char(30), Email Char(20), Phone Char(12))");
                    Log.d("AAA", "Tạo Data thành công");
                }
                try{
                    database.QueryData("INSERT INTO User VALUES('"+edtuserName.getEditText().getText().toString().trim()+"','"+edtpassword.getEditText().getText().toString().trim()+"','"+edtfullName.getEditText().getText().toString().trim()+"','"+edtemail.getEditText().getText().toString().trim()+"','"+edtphone.getEditText().getText().toString().trim()+"')");
                    Toast.makeText(Signup.this, "Register Success !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Signup.this,Login.class));
                }catch (Exception ex){
                    Toast.makeText(Signup.this, "Try again with another user name !", Toast.LENGTH_SHORT).show();
                    Log.d("ERR",""+ex);
                }
            }
        });


    }

    public void initLayout(){
        btnsignUp = (Button) findViewById(R.id.go_signup);
        btnbackLogin = (Button) findViewById(R.id.back_login);
        edtfullName = (TextInputLayout) findViewById(R.id.name);
        edtuserName = (TextInputLayout) findViewById(R.id.username);
        edtemail = (TextInputLayout) findViewById(R.id.email);
        edtphone = (TextInputLayout) findViewById(R.id.phone);
        edtpassword = (TextInputLayout) findViewById(R.id.password);
        logoLogin = (ImageView) findViewById(R.id.logo_login);
        tvSignup = (TextView) findViewById(R.id.tv_signup);
        tvSloganLogin = (TextView) findViewById(R.id.tv_slogan_login);
    }



}