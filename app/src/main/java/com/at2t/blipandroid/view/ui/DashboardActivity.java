package com.at2t.blipandroid.view.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;

import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.EventSlides;
import com.at2t.blipandroid.model.PostsData;
import com.at2t.blipandroid.view.adapters.CustomSwipeAdapter;
import com.at2t.blipandroid.view.adapters.PostsAdapter;
import com.at2t.blipandroid.viewmodel.DashBoardViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DashboardActivity extends AppCompatActivity {

    private List<EventSlides> slidesList = new ArrayList<>();
    private ViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private Timer timer;
    private int curr_position = 0;
    private LinearLayout dots_layout;
    private int custom_position = 0;
    DashboardActivity context;
    DashBoardViewModel viewModel;
    RecyclerView recyclerView;
    PostsAdapter postsRecyclerViewAdapter;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_home);

        intializeNavigationView();
        recyclerView = findViewById(R.id.rv_posts);
        viewModel = ViewModelProviders.of(context).get(DashBoardViewModel.class);
        viewModel.getUserMutableLiveData().observe(context, userListUpdateObserver);
        initializeViews();
    }

    public void intializeNavigationView() {
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        t = new ActionBarDrawerToggle(this, dl, toolbar, R.string.app_name, R.string.Close);
        t.syncState();
        dl.addDrawerListener(t);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    Observer<ArrayList<PostsData>> userListUpdateObserver = new Observer<ArrayList<PostsData>>() {
        @Override
        public void onChanged(ArrayList<PostsData> userArrayList) {
            postsRecyclerViewAdapter = new PostsAdapter(context, userArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(postsRecyclerViewAdapter);
        }
    };

    public void prepareDots(int curr_slide_position) {
        if (dots_layout.getChildCount() > 0)
            dots_layout.removeAllViews();

        ImageView dots[] = new ImageView[4];

        for (int i = 0; i < 4; i++) {
            dots[i] = new ImageView(this);
            if (i == curr_slide_position)
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            else
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.inactive_dot));

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup
                    .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(4, 0, 4, 0);
            dots_layout.addView(dots[i], layoutParams);
        }
    }

    public void prepareSlide() {
        int[] imgSlide = {R.drawable.rect1, R.drawable.rect3, R.drawable.rect4, R.drawable.event1};

        for (int i = 0; i < imgSlide.length; i++) {
            slidesList.add(new EventSlides(imgSlide[i]));
        }
    }

    private void initializeViews() {
        dots_layout = findViewById(R.id.dots_container);
        mViewPager = findViewById(R.id.view_pager);
        prepareSlide();
        mAdapter = new CustomSwipeAdapter(slidesList, this);
        mViewPager.setAdapter(mAdapter);
        prepareDots(custom_position++);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (custom_position > 3)
                    custom_position = 0;
                prepareDots(custom_position++);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        createSlideShow();
    }

    public void createSlideShow() {
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (curr_position == Integer.MAX_VALUE)
                    curr_position = 0;

                mViewPager.setCurrentItem(curr_position++, true);

            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 250, 3000);
    }
}
