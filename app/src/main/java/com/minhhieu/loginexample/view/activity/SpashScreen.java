package com.minhhieu.loginexample.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.minhhieu.loginexample.R;

public class SpashScreen extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME = 5000;


    Animation topAnimation, bottomAnimation;
    ImageView image;
    TextView logo;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Ánh xạ
        image = (ImageView) findViewById(R.id.icon_games);
        logo = (TextView) findViewById(R.id.logo);
        slogan = (TextView) findViewById(R.id.slogan);

        image.setAnimation(topAnimation);
        logo.setAnimation(bottomAnimation);
        slogan.setAnimation(bottomAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SpashScreen.this,LoginActivity.class);
                Pair[] pairs = new Pair[2];

                //anim chuyển activity
                pairs[0] = new Pair<View,String>(image,"logo_image");
                pairs[1] = new Pair<View,String>(logo,"logo_text");
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SpashScreen.this,pairs);
                startActivity(intent,options.toBundle());
                finish();
            }
        },SPLASH_SCREEN_TIME);

    }
}