package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.Database;
import com.minhhieu.loginexample.model.User;

/*********************************************************
 * Email: - Bắt đầu bằng chữ cái.
 - Chỉ chứa chữ cái, chữ số và dấu gạch ngang (-).
 - Chứa một ký tự @, sau @ là tên miền.
 - Tên miền có thể là domain.xxx.yyy hoặc domain.xxx. Trong đó xxx và yyy là các chữ cái và có độ dài từ 2 trở lên.

 Pass:  - Phải chứa ít nhất 1 ký tự số từ 0 – 9
 - Phải chứa ít nhất 1 ký tự chữ thường
 - Phải chứa ít nhất 1 ký tự chữ hoa
 **********************************************************/

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{
    ImageView logoLogin;
    TextView tvSignup,tvSloganLogin;
    Button btnsignUp, btnbackLogin;
    TextInputLayout edtfullName, edtuserName, edtemail, edtphone, edtpassword;
    Database database;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_signup);
        initLayout();
        database = new Database(SignupActivity.this);
        initListeners();

    }


    private void inputUser(){
        user.setUserName(edtuserName.getEditText().getText().toString().trim());
        user.setPassWord(edtpassword.getEditText().getText().toString().trim());
        user.setFullName(edtfullName.getEditText().getText().toString().trim());
        user.setEmail(edtemail.getEditText().getText().toString().trim());
        user.setPhone(edtphone.getEditText().getText().toString().trim());
    }

    /**************************
     * Bắt sự kiện nút Signup *
     **************************/

    private void setBtnsignUp(){
        if(validate()) {
            String UserName = edtuserName.getEditText().getText().toString().trim();
            if(!database.checkUser(UserName)){
                user = new User();
                inputUser();
                database.addUser(user);
                Toast.makeText(SignupActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }else {
                Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại, vui lòng nhập tài khoản khác", Toast.LENGTH_SHORT).show();
                Log.d("ERR", "lỗi");
            }
        }
    }

    /************************
     * Bắt sự kiện nút Login*
     ************************/

    private void setBtnbackLogin(){

                Intent intent = new Intent(SignupActivity.this,LoginActivity.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View,String>(logoLogin,"logo_image");
                pairs[1] = new Pair<View,String>(tvSignup,"logo_text");
                pairs[2] = new Pair<View,String>(tvSloganLogin,"logo_text");
                pairs[3] = new Pair<View,String>(edtuserName,"username_trans");
                pairs[4] = new Pair<View,String>(edtpassword,"password_trans");
                pairs[5] = new Pair<View,String>(btnsignUp,"login_trans");
                pairs[6] = new Pair<View,String>(btnbackLogin,"signup_trans");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignupActivity.this,pairs);
                startActivity(intent,options.toBundle());
                finish();
    }


    /***********************
     * Check lỗi các trường*
     ***********************/

    public boolean validate() {
        boolean valid = true;
        String FullName = edtfullName.getEditText().getText().toString().trim();
        String UserName = edtuserName.getEditText().getText().toString().trim();
        String Phone = edtphone.getEditText().getText().toString().trim();
        String Email = edtemail.getEditText().getText().toString().trim();
        String Password = edtpassword.getEditText().getText().toString().trim();

        if (FullName.isEmpty()) {
            valid = false;
            edtfullName.setError("Bạn vui lòng nhập họ và tên!");
        } else {
            edtfullName.setError(null);
        }
        if (UserName.isEmpty()) {
            valid = false;
            edtuserName.setError("Bạn vui lòng nhập username!");
        } else {
            if (UserName.matches("[a-z0-9_-]{0,5}$")) {
                valid = false;
                edtuserName.setError("Usename không hợp lệ, Username phải có độ tài từ 6 đến 15 ký tự, không có khoảng trắng và không dấu!");
            } else {
                edtuserName.setError(null);
            }
        }

        if (Email.isEmpty()) {
            valid = false;
            edtemail.setError("Vui lòng nhập email!");
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                valid = false;
                edtemail.setError("Email không hợp lệ!");
            } else {
                edtemail.setError(null);
            }
        }

        if (Password.isEmpty()) {
            valid = false;
            edtpassword.setError("Vui lòng nhập password!");
        } else {
            if (Password.matches("(?!((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})).*$")){
                valid = false;
                edtpassword.setError("Password không hợp lệ, Password phải chứa ít nhất một kí tự viết hoa và một chữ số!");
            } else{
                edtpassword.setError(null);
            }
        }

        if (Phone.isEmpty()) {
            valid = false;
            edtphone.setError("Vui lòng nhập Phone!");
        } else {
            if (!Patterns.PHONE.matcher(Phone).matches()) {
                valid = false;
                edtphone.setError("Số điện thoại không hợp lệ!");
            } else {
                edtphone.setError(null);
            }
        }
        return valid;
    }


    private void initListeners() {
       btnsignUp.setOnClickListener(this);
       btnbackLogin.setOnClickListener(this);

    }

    /******************
     * Xử lí sự kiện  *
     ******************/

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_login:
                setBtnbackLogin();
                break;

            case R.id.go_signup:
                setBtnsignUp();
                break;
        }
    }
}
