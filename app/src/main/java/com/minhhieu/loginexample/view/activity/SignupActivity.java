package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputLayout;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.utils.asynctask.CheckUserTask;
import com.minhhieu.loginexample.data.Database;
import com.minhhieu.loginexample.utils.asynctask.SetUserTask;
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

// toi uu tranh duplicate
    private void inputUser(){
        user.setUserName(getStringInput(edtuserName));
        user.setPassWord(getStringInput(edtpassword));
        user.setFullName(getStringInput(edtfullName));
        user.setEmail(getStringInput(edtemail));
        user.setPhone(getStringInput(edtphone));
    }

    //Toi uu code tranh duplicate
    private String getStringInput(TextInputLayout textInputLayout){
           EditText txt = textInputLayout.getEditText();
           if(txt == null){
               return "";
           }else {
               return txt.getText().toString().trim();
           }
    }

    /**************************
     * Bắt sự kiện nút Signup *
     **************************/

    private void setBtnsignUp() {
        if (validate()) {
            final String UserName = getStringInput(edtuserName);



//            if(!database.checkUser(UserName)){
//                user = new User();
//                inputUser();
//                database.addUser(user);
//                Toast.makeText(SignupActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
//            }else {
//                Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại, vui lòng nhập tài khoản khác", Toast.LENGTH_SHORT).show();
//                Log.d("ERR", "lỗi");
//            }
            user = new User();
            inputUser();
            CheckUserTask task = new CheckUserTask(this, UserName);
            task.setGetUserListener(new CheckUserTask.GetUserListener() {
                @Override
                public void onSuccess(User user) {
                    if(user.getUserName()!=null){
                        Toast.makeText(SignupActivity.this, "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        AddUser();
                    }
                }
            });
            task.execute();
        }
    }

    private void AddUser(){
        final String UserName = getStringInput(edtuserName);
        final String PassWord = getStringInput(edtpassword);
        final String FullName = getStringInput(edtfullName);
        final String Email = getStringInput(edtemail);
        final String Phone = getStringInput(edtphone);
        inputUser();
        SetUserTask setUserTask = new SetUserTask(this);
        setUserTask.execute("add_user",UserName,PassWord,FullName,Email,Phone);
        finish();
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
        String FullName = getStringInput(edtfullName);
        String UserName = getStringInput(edtuserName);
        String Phone = getStringInput(edtphone);
        String Email = getStringInput(edtemail);
        String Password = getStringInput(edtpassword);

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
        btnsignUp = findViewById(R.id.go_signup);
        btnbackLogin =  findViewById(R.id.back_login);
        edtfullName = findViewById(R.id.name);
        edtuserName = findViewById(R.id.username);
        edtemail = findViewById(R.id.email);
        edtphone = findViewById(R.id.phone);
        edtpassword = findViewById(R.id.password);
        logoLogin = findViewById(R.id.logo_login);
        tvSignup = findViewById(R.id.tv_signup);
        tvSloganLogin = findViewById(R.id.tv_slogan_login);
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
