package com.at2t.blipandroid.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.view.ui.fragments.AllPostsFragment;
import com.at2t.blipandroid.view.ui.MainDashboardActivity;
import com.at2t.blipandroid.view.ui.fragments.UserProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent home  = new Intent(MainActivity.this,  MainDashboardActivity.class);
                        startActivity(home);
                        break;
                    case R.id.posts:
                        Intent posts = new Intent(MainActivity.this,  AllPostsFragment.class);
                        startActivity(posts);
                        break;
                    case R.id.profile:
                        Intent profile = new Intent(MainActivity.this, UserProfileFragment.class);
                        startActivity(profile);
                        break;
                    case R.id.contactUs:
                        Intent contactUs = new Intent(MainActivity.this,  UserProfileFragment.class);
                        startActivity(contactUs);
                        break;
                    default:
                        Intent defaultHome = new Intent(MainActivity.this, MainDashboardActivity.class);
                        startActivity(defaultHome);
                        break;
                }
                return false;
            }
        });
    }
}
