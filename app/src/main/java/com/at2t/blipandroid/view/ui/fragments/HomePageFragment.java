package com.at2t.blipandroid.view.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.BannerDetailsDataModel;
import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.view.adapters.CustomSwipeAdapter;
import com.at2t.blipandroid.viewmodel.LoginViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomePageFragment extends BaseFragment {
    private static final String TAG = "HomePageFragment";
    RelativeLayout rlPosts;
    CardView cvProfileImage;
    private TextView tvUserName;
    private Button btnKnowMore;
    private RelativeLayout rlNotification;
    private String userFirstName;
    private List<BannerDetailsDataModel> slidesList = new ArrayList<>();
    private ViewPager mViewPager;
    private CustomSwipeAdapter mAdapter;
    private Timer timer;
    private int curr_position = 0;
    private LinearLayout dots_layout;
    private RelativeLayout rlBannerSlider;
    private int custom_position = 0;
    private LiveData<Integer> liveData;
    private LoginViewModel loginViewModel;
    private List<BannerDetailsDataModel> bannerDetailsDataModelList = new ArrayList<>();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setUpBannerImage(List<BannerDetailsDataModel> bannerDetailsDataModelList) {
        for (int i = 0; i < bannerDetailsDataModelList.size(); i++) {
            slidesList.add(bannerDetailsDataModelList.get(i));
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
            loginViewModel.init(getActivity().getApplication());
            loginViewModel.getBannerDetails(BlipUtility.getInstituteId(getContext()));

            loginViewModel.getResponseLiveData().observe(this, new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    if (integer != null) {
                        switch (integer) {
                            case Enums.LoginStatus.GET_BANNER_DETAILS_SUCCESSFULLY:
                                Log.d(TAG, "Got the banner details successfully");
                                bannerDetailsDataModelList = loginViewModel.getBannerDetails();
                                createBannerSlider(bannerDetailsDataModelList);
                                break;
                            case Enums.LoginStatus.GET_BANNER_DETAILS_FAILED:
                                Log.d(TAG, "Getting the banner details failed");
                                break;
                            case Enums.LoginStatus.NO_INTERNET_CONNECTION:
                                Toast.makeText(getActivity(), "No internet connection, please try again", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_home, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        int parentId = BlipUtility.getParentId(getContext());
        int instructorId = BlipUtility.getInstructorId(getContext());

        if(parentId != 0) {
            userFirstName = BlipUtility.getChildrenName(getContext());
            if (userFirstName != null) {
                tvUserName.setText("Hello" + " " + userFirstName);
            }
        } else if (instructorId != 0) {
            userFirstName = BlipUtility.getFirstName(getContext());
            tvUserName.setText("Hello" + " " + userFirstName);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        rlPosts = view.findViewById(R.id.rlPosts);
        cvProfileImage = view.findViewById(R.id.cv_profile_img);
        userFirstName = BlipUtility.getChildrenName(getContext());

        tvUserName = view.findViewById(R.id.tvUserName);
        btnKnowMore = view.findViewById(R.id.btnKnowMore);
        rlNotification = view.findViewById(R.id.rlNotifications);

        dots_layout = view.findViewById(R.id.dots_container);
        mViewPager = view.findViewById(R.id.view_pager);
        rlBannerSlider = view.findViewById(R.id.rl_topWelcomeHeader);

        if (bannerDetailsDataModelList.size() > 0) {
            rlBannerSlider.setVisibility(View.GONE);
        } else {
            rlBannerSlider.setVisibility(View.VISIBLE);
        }

        rlPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllPostsFragment allPostsFragment = new AllPostsFragment();
                switchToFragment(allPostsFragment, R.id.container, TAG);
            }
        });

        cvProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new UserProfileDetailsFragment(), R.id.container, TAG);
            }
        });

        rlNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToFragment(new NotificationsListFragment(), R.id.container, TAG);
            }
        });
    }

    public void createBannerSlider(final List<BannerDetailsDataModel> bannerDetailsDataModelList) {
        prepareSlide();
        mAdapter = new CustomSwipeAdapter(bannerDetailsDataModelList, getContext());
        mViewPager.setAdapter(mAdapter);
        prepareDots(custom_position++);
        createSlideShow();

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (custom_position > bannerDetailsDataModelList.size() - 1)
                    custom_position = 0;
                prepareDots(custom_position++);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


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
        }, 250, 2500);
    }

    public void prepareDots(int currentSlidePosition) {
        if (dots_layout.getChildCount() > 0)
            dots_layout.removeAllViews();

        ImageView dots[] = new ImageView[bannerDetailsDataModelList.size() + 1];

        for (int i = 0; i < bannerDetailsDataModelList.size(); i++) {
            if (getActivity() != null) {
                dots[i] = new ImageView(getActivity());
                if (i == currentSlidePosition)
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dot));
                else
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.inactive_dot));

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup
                        .LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(4, 0, 4, 0);
                dots_layout.addView(dots[i], layoutParams);
            }
        }
    }

    public void prepareSlide() {
        bannerDetailsDataModelList = loginViewModel.getBannerDetails();
        setUpBannerImage(bannerDetailsDataModelList);

    }
}
