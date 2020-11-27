package com.minhhieu.loginexample.view.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

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

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.data.Database;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Button btncallSignUp,btnLogin;
    ImageView image,tvFacebook,tvGoogle,tvYahoo;
    TextView Logo, slogan;
    TextInputLayout edtuserName,edtPassword;
    Database database;
    private GoogleApiClient googleApiClient;
    private  static final int SIGN_IN_GG = 1;
    private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        initLayout();
        database=new Database(Login.this,"LGAPP.sqlite",null,1);
        buttonLogin();
        buttonSignup();
        buttonLoginGG();



    }


    /*******************************
     *  Setting Login Google  *
     *******************************/

    private void settingGoogle(){
        //Yêu cầu người dùng cung cấp thông tin cơ bản + Email +
        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Kết nối googleAPI
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }



    /*******************************
     *  Xử lí button Login Google  *
     *******************************/
    private void buttonLoginGG(){
        tvGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingGoogle();
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,SIGN_IN_GG);
                Log.d("Success",googleApiClient.isConnected()+"");
            }
        });
    }

    /************************
     *  Xử lí button Login *
     ************************/
    private void buttonLogin(){
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
                        Toast.makeText(Login.this, "Đăng nhập sai, vui lòng kiểm tra lại tài khoản!", Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    /*************************
     *  Xử lí button Signup  *
     ************************/
    private void buttonSignup(){
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

    /******************
     * Xử lí sự kiện  *
     ******************/
    public void initLayout(){
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
        if(requestCode==SIGN_IN_GG){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);

        }
    }


    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }



    private void gotoProfile(){
        Intent intent = new Intent(Login.this,MainActivity2.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d("Failed",connectionResult+"");
    }

}