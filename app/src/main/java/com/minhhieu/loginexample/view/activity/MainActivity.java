package com.minhhieu.loginexample.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;

import android.os.Bundle;




import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.fragment.ListBookFragment;


public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        settingGoogle();


        loadFragment(new ListBookFragment());
        setTitle(null);



    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


    private void settingGoogle(){
        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();
    }




    /*************************
     *  Xử lí button LogOut  *
     *************************/
//    private void buttonLogOut(){
//        btnLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
//                        new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(Status status) {
//                                if (status.isSuccess()) {
//                                    gotoLoginActivity();
//                                } else {
//                                    Toast.makeText(getApplicationContext(), "Session not close", Toast.LENGTH_LONG).show();
//                                }
//                            }
//                        });
//            }
//        });
//    }
//    private void gotoLoginActivity(){
//       Intent intent = new Intent(this,LoginActivity.class);
//       startActivity(intent);
//       finish();
//    }

//    private void setBtnAdd(){
//        btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddBookActivity.class);
//                startActivity(intent);
//            }
//        });
//
//    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    //check button cung back
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);
    }



}

