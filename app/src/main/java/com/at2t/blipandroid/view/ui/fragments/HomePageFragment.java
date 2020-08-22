package com.at2t.blipandroid.view.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageFragment extends BaseFragment {
    private static final String TAG = "HomePageFragment";
    RelativeLayout rlPosts;
    CardView cvProfileImage;
    private BottomNavigationView bottomNavigationView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rlPosts = view.findViewById(R.id.rlPosts);
        cvProfileImage = view.findViewById(R.id.cv_profile_img);

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
                switchToFragment(new UserProfileFragment(), R.id.container, TAG);
            }
        });
    }
}
