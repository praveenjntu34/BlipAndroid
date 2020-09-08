package com.at2t.blipandroid.view.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.BlipUtility;

public class HomePageFragment extends BaseFragment {
    private static final String TAG = "HomePageFragment";
    RelativeLayout rlPosts;
    CardView cvProfileImage;
    private TextView tvUserName;
    private Button btnKnowMore;
    private RelativeLayout rlNotification;
    private String userFirstName;

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
        userFirstName = BlipUtility.getFirstName(getContext());
        if (userFirstName != null) {
            tvUserName.setText("Hello" + " " + userFirstName);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlPosts = view.findViewById(R.id.rlPosts);
        cvProfileImage = view.findViewById(R.id.cv_profile_img);
        userFirstName = BlipUtility.getFirstName(getContext());

        tvUserName = view.findViewById(R.id.tvUserName);
        btnKnowMore = view.findViewById(R.id.btnKnowMore);
        rlNotification = view.findViewById(R.id.rlNotifications);

        if (userFirstName != null) {
            tvUserName.setText("Hello" + " " + userFirstName);
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
}
