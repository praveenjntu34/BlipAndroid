package com.at2t.blipandroid.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.view.ui.fragments.AllPostsFragment;
import com.at2t.blipandroid.view.ui.fragments.ContactUsFragment;
import com.at2t.blipandroid.view.ui.fragments.HomePageFragment;
import com.at2t.blipandroid.view.ui.fragments.UserProfileFragment;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashboardActivity extends AppCompatActivity {
    private static final String TAG = "MainDashboardActivity";
    public static boolean IS_USER_LOGGED_IN = true;
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        BlipUtility.setSharedPrefBoolean(getApplicationContext(), Constants.IS_LOGGED_IN, true);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(getApplication());

        loginViewModel.getResponseLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    if (integer == Enums.LoginStatus.GET_USER_PROFILE_DETAILS_SUCCESSFULLY){
                        Log.d(TAG, " Got parent profile details successfully");

                    } else if (integer == Enums.LoginStatus.GET_USER_PROFILE_DETAILS_FAILED) {
                        Log.d(TAG, "Got parent profile details failed");

                    }else if (integer == Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY) {
                        Log.d(TAG, "Got parent profile updated successfully");

                    } else if (integer == Enums.LoginStatus.PROFILE_UPDATE_FAILED) {
                        Log.d(TAG, "Got parent profile updated failed");

                    } else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initializeViews();
    }

    private void initializeViews() {
        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = new Fragment();
                        switch (item.getItemId()) {
                            case R.id.home:
                                fragment = new HomePageFragment();
                                break;
                            case R.id.posts:
                                fragment = new AllPostsFragment();
                                break;
                            case R.id.profile:
                                fragment = new UserProfileFragment();
                                break;
                            case R.id.contactUs:
                                fragment = new ContactUsFragment();
                                break;
                        }
                        MainDashboardActivity.this.clearBackStack();
                        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
                        return true;
                    }
                });
        // Set default selection
        bottomNavigationView.setSelectedItemId(R.id.home);
        int parentId = BlipUtility.getParentId(getApplicationContext());
        if(parentId != 0) {
            loginViewModel.getParentProfileDetails(parentId);
        }


    }

    private void clearBackStack() {
        FragmentManager fm = getSupportFragmentManager();
        if (fm.getBackStackEntryCount() > 0) {
            fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        if (bottomNavigationView.getSelectedItemId() != R.id.home) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        } else {
            super.onBackPressed();
        }
    }
}
