package com.at2t.blipandroid.view.ui;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.at2t.blipandroid.utils.DatePickerFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.view.adapters.PostsAdapter;
import com.at2t.blipandroid.viewmodel.DashBoardViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class DashboardActivity extends AppCompatActivity implements
        DatePickerDialog.OnDateSetListener {

    DashboardActivity context;
    DashBoardViewModel viewModel;
    RecyclerView recyclerView;
    PostsAdapter postsRecyclerViewAdapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private Button btnNewPost;
    LinearLayout calendarView;
    TextView tvDateSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_home);

        intializeNavigationView();
        initializeUIView();
        recyclerView = findViewById(R.id.rv_posts);

        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel.class);
        viewModel.init(getApplication());
        observerViewModel(viewModel);

    }

    public void observerViewModel(DashBoardViewModel dashBoardViewModel){
        dashBoardViewModel.getPostLiveDataObservables().observe(this, new
                Observer<List<PostsData>>() {
                    @Override
                    public void onChanged(List<PostsData> postsDataList) {
                        if (postsDataList != null && postsDataList.size() > 0) {

                            if (recyclerView != null) {
                                postsRecyclerViewAdapter = (PostsAdapter) recyclerView.getAdapter();
                                if (postsRecyclerViewAdapter == null) {
                                    postsRecyclerViewAdapter = new PostsAdapter(getApplicationContext(), postsDataList);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                                    recyclerView.setLayoutManager(linearLayoutManager);
                                    recyclerView.setAdapter(postsRecyclerViewAdapter);
                                } else {
                                    postsRecyclerViewAdapter.setdata(postsDataList);
                                }
                            }
                        } else {
                            Log.d("Post failed: ", "Try again");
                        }
                    }
                });
    }

    private void initializeUIView() {
        btnNewPost = findViewById(R.id.btnNewPost);
        calendarView = findViewById(R.id.llDateSelector);
        tvDateSelected = findViewById(R.id.tvDate);

        calendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        btnNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddPostActivity();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.DEFAULT).format(c.getTime());

        tvDateSelected.setText(currentDateString);
    }

    private void goToAddPostActivity() {
        startActivity(new Intent(DashboardActivity.this, AddPostActivity.class));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    public void intializeNavigationView() {
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.Close);
        t.syncState();
        dl.addDrawerListener(t);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(DashboardActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.settings:
                        Toast.makeText(DashboardActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.mycart:
                        Toast.makeText(DashboardActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
        context = this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            dl.openDrawer(Gravity.LEFT);

        return super.onOptionsItemSelected(item);
    }
}
