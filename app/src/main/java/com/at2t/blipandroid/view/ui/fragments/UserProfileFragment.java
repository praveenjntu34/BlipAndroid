package com.at2t.blipandroid.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;

public class UserProfileFragment extends BaseFragment {

    public static final String TAG = "UserProfileFragment";

    private TextView tvUserName;
    private TextView tvInstituteName;
    private TextView tvUserEmail;
    private TextView tvUserState;
    private TextView tvUserPhone;
    private ImageView ivUserImg;
    private ImageView ivInstituteImg;
    private ImageView ivUserEmail;
    private ImageView ivUserState;
    private ImageView ivUserPhone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        tvInstituteName = view.findViewById(R.id.tv_user_institute);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserEmail = view.findViewById(R.id.tv_email);
        tvUserPhone = view.findViewById(R.id.tv_user_mobile);
        tvUserState = view.findViewById(R.id.tv_user_state);
        ivInstituteImg = view.findViewById(R.id.iv_user_institute);
        ivUserEmail = view.findViewById(R.id.iv_user_email);
        ivUserImg = view.findViewById(R.id.iv_user_img);
        ivUserPhone = view.findViewById(R.id.iv_user_phone);
        ivUserState = view.findViewById(R.id.iv_user_state);

    }
}
