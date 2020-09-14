package com.at2t.blipandroid.view;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.view.ui.LoginActivity;
import com.at2t.blipandroid.view.ui.MainDashboardActivity;

public class BlipBaseActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    public static boolean IS_USER_LOGGED_IN = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        IS_USER_LOGGED_IN = BlipUtility.getSharedPrefBoolean(getApplicationContext(), Constants.IS_LOGGED_IN);
        if (IS_USER_LOGGED_IN) {
            Intent intent = new Intent(this, MainDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }
}
