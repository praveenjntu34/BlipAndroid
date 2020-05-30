package com.at2t.blipandroid.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.at2t.blipandroid.R;

public class MainDashboardActivity extends AppCompatActivity {

    RelativeLayout rlPosts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        rlPosts = findViewById(R.id.rlPosts);

        rlPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainDashboardActivity.this, DashboardActivity.class);
                startActivity(i);
            }
        });
    }
}
