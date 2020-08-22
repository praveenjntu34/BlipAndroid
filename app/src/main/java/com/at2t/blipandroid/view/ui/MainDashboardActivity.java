package com.at2t.blipandroid.view.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.view.ui.fragments.AllPostsFragment;
import com.at2t.blipandroid.view.ui.fragments.ContactUsFragment;
import com.at2t.blipandroid.view.ui.fragments.HomePageFragment;
import com.at2t.blipandroid.view.ui.fragments.UserProfileFragment;
import com.at2t.blipandroid.viewmodel.DashBoardViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainDashboardActivity extends AppCompatActivity {
    private static final String TAG = "MainDashboardActivity";
    private FragmentManager fragmentManager;
    private BottomNavigationView bottomNavigationView;
    private DashBoardViewModel viewModel;
    private Integer sectionId;
    private Bundle bundle;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);

        sectionId = getIntent().getIntExtra(Constants.SECTION_ID, 0);

        DashBoardViewModel dashboardViewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
//        dashboardViewModel.init(getApplication());

        fragmentManager = getSupportFragmentManager();
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        bundle = new Bundle();
        bundle.putInt(Constants.SECTION_ID, sectionId);

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
                                fragment.setArguments(bundle);
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
