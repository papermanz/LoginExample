package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;

import com.minhhieu.loginexample.R;
import com.minhhieu.loginexample.fragment.AddBookFragment;

public class AddBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        loadFragment(new AddBookFragment());
        setTitle(null);

    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container_addbook, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    //check button cung back
    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0){
            getFragmentManager().popBackStack();
        }else{
            finish();
        }

    }

}