package com.minhhieu.loginexample.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.fragment.AddBookFragment;
import com.minhhieu.loginexample.fragment.HomeFragment;
import com.minhhieu.loginexample.fragment.ListBookFragment;


public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{


    private static BottomNavigationView navigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        navigationView = (BottomNavigationView)findViewById(R.id.navigation_bottom);
        navigationView.setItemIconTintList(null);

        navigationView.setSelectedItemId(R.id.homeActivity);
        navigationView.setOnNavigationItemSelectedListener(this);
        loadFragment(new HomeFragment());
        setTitle(null);

    }

    public static void hideBottomNav(){

        navigationView.setVisibility(View.GONE);
    }

    public static void showBottomNav(){

        navigationView.setVisibility(View.VISIBLE);
    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_homebook, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    HomeFragment homeFragment = new HomeFragment();
    ListBookFragment listBookFragment = new ListBookFragment();
    AddBookFragment addBookFragment = new AddBookFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeActivity:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_homebook,homeFragment).commit();
                return true;

            case R.id.listBookActivity:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_homebook,listBookFragment).commit();

                return true;

            case R.id.personalActivity:
                Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();

                return true;

            case R.id.settings:
                Toast.makeText(this, "comming soon", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.addbookActivity:
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_container_homebook,addBookFragment).commit();


                return true;
        }

        return false;
    }

    @Override
    public void onBackPressed() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("")
                .setMessage("Bạn có muốn thoát tài khoản không?")
                .setPositiveButton("Có", (dialogInterface, i) -> {
                    Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Không", (dialogInterface, i) -> {

                })
                .show();

    }



}