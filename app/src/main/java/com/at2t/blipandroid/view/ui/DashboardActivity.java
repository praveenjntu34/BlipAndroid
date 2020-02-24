package com.at2t.blipandroid.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        context = this;
        recyclerView = findViewById(R.id.rv_posts);
        viewModel = ViewModelProviders.of(context).get(DashBoardViewModel.class);
        viewModel.getUserMutableLiveData().observe(context, userListUpdateObserver);
        initializeViews();
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
        int[] imgSlide = {R.drawable.rect1, R.drawable.rect3, R.drawable.rect4, R.drawable.rect5};

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
        }, 250, 1500);
    }
}
