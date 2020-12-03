package com.minhhieu.loginexample.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.textfield.TextInputLayout;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.Database;
import com.minhhieu.loginexample.data.GetUserTask;
import com.minhhieu.loginexample.model.User;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener {
    Button btncallSignUp, btnLogin;
    ImageView image, tvFacebook, tvGoogle, tvYahoo;
    TextView Logo, slogan;
    TextInputLayout edtuserName, edtPassword;
    Database database;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN_GG = 1;
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initLayout();
//        GetUserTask getUser = new GetUserTask();
//        getUser.execute();
        database = new Database(LoginActivity.this);
        initListeners();
    }



    /*******************************
     *  Setting Login Google  *
     *******************************/

    private void settingGoogle() {
        //Yêu cầu người dùng cung cấp thông tin cơ bản + Email +
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Kết nối googleAPI
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    /*******************************
     *  Xử lí button Login Google  *
     *******************************/
    private void buttonLoginGG() {
        settingGoogle();
        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(intent, SIGN_IN_GG);
        Log.d("Success", googleApiClient.isConnected() + "");
    }


    /*************************
     *  Xử lí button Signup  *
     ************************/
    private void buttonSignup() {

        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        Pair[] pairs = new Pair[7];
        pairs[0] = new Pair<View, String>(image, "logo_image");
        pairs[1] = new Pair<View, String>(Logo, "logo_text");
        pairs[2] = new Pair<View, String>(slogan, "logo_text");
        pairs[3] = new Pair<View, String>(edtuserName, "username_trans");
        pairs[4] = new Pair<View, String>(edtPassword, "password_trans");
        pairs[5] = new Pair<View, String>(btnLogin, "login_trans");
        pairs[6] = new Pair<View, String>(btncallSignUp, "signup_trans");
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
        startActivity(intent, options.toBundle());
    }

    /***********************
     *  Xử lí button Login *
     ***********************/
    private void setBtnLogin() {
        String userName = edtuserName.getEditText().getText().toString().trim();
        String password = edtPassword.getEditText().getText().toString().trim();
        GetUserTask task = new GetUserTask(this, userName, password);
        task.setGetUserListener(new GetUserTask.GetUserListener() {
            @Override
            public void onSuccess(User user) {
                if (user != null){
                     Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
                     Log.d("LOGIN", "SUCCESS ");
                     startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this, "Đăng nhập sai, vui lòng kiểm tra lại tài khoản!", Toast.LENGTH_LONG).show();
                }
            }
        });
        task.execute();
//        if (database.checkUser(UserName, Password)) {
//            Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
//            Log.d("LOGIN", "SUCCESS ");
//            startActivity(intent);
//        } else {
//            Toast.makeText(LoginActivity.this, "Đăng nhập sai, vui lòng kiểm tra lại tài khoản!", Toast.LENGTH_LONG).show();
//        }
    }

    /*****************
     * Xử lí sự kiện *
     *****************/
    public void initLayout() {
        btncallSignUp = (Button) findViewById(R.id.signup);
        btnLogin = (Button) findViewById(R.id.login);
        image = (ImageView) findViewById(R.id.logo_login);
        Logo = (TextView) findViewById(R.id.logo_text);
        slogan = (TextView) findViewById(R.id.slogan_login);
        edtuserName = (TextInputLayout) findViewById(R.id.username);
        edtPassword = (TextInputLayout) findViewById(R.id.password);
        tvGoogle = (ImageView) findViewById(R.id.tv_google);
        tvFacebook = (ImageView) findViewById(R.id.tv_fb);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGN_IN_GG) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            gotoProfile();
        } else {
            Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
        }
    }


    private void gotoProfile() {
        Intent intent = new Intent(LoginActivity.this, LogoutActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed", connectionResult + "");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                setBtnLogin();
                break;

            case R.id.signup:
                buttonSignup();
                break;

            case R.id.tv_google:
                buttonLoginGG();
                break;

        }
    }

    private void initListeners() {
        btnLogin.setOnClickListener(this);
        btncallSignUp.setOnClickListener(this);
        tvGoogle.setOnClickListener(this);

    }
}