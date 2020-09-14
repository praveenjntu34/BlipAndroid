package com.at2t.blipandroid.view.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.view.BlipBaseActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            final SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("app-pref", Context.MODE_PRIVATE);

                    Intent intent = new Intent(SplashScreenActivity.this, BlipBaseActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }


    };
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        handler = new Handler();
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }
}