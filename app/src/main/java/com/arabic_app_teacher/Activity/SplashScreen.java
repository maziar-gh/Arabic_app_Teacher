package com.arabic_app_teacher.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.arabic_app_teacher.R;

public class SplashScreen extends AppCompatActivity {

    private Thread mSplashThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this,Activity_Login.class));
                //startActivity(new Intent(SplashScreen.this,MainActivity.class));
                finish();
            }
        },1000);
    }

}
