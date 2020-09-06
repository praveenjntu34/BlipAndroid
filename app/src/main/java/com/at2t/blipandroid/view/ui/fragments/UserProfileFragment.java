package com.at2t.blipandroid.view.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BaseFragment;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.view.BlipBaseActivity;
import com.at2t.blipandroid.view.ui.UserRegistrationActivity;
import com.at2t.blipandroid.viewmodel.LoginViewModel;

import java.util.Objects;

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
    private ImageView editIcon;

    private String firstName;
    private String lastName;
    private String fullName;
    private String emailId;
    private String phoneNumber;
    private String userType;
    private int institutionId;
    private LoginViewModel viewModel;

    private LinearLayout linearLayoutLogout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

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
        editIcon = view.findViewById(R.id.edit_icon);
        linearLayoutLogout = view.findViewById(R.id.ll_logout);

        firstName = BlipUtility.getFirstName(getContext());
        lastName = BlipUtility.getLastName(getContext());
        fullName = firstName + " " + lastName;
        tvUserName.setText(fullName);

        emailId = BlipUtility.getEmailId(getContext());
        tvUserEmail.setText(emailId);

        phoneNumber = BlipUtility.getPhoneNumber(getContext());
        tvUserPhone.setText(phoneNumber);

        userType = BlipUtility.getRole(getContext());

        if(userType.equals("Parent")) {
            editIcon.setVisibility(View.VISIBLE);
            editIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), UserRegistrationActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            tvUserEmail.setVisibility(View.INVISIBLE);
            tvUserPhone.setVisibility(View.INVISIBLE);
            ivUserEmail.setVisibility(View.INVISIBLE);
            ivUserPhone.setVisibility(View.INVISIBLE);
            editIcon.setVisibility(View.GONE);
        }

        linearLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearUserLocalInfo(getContext());
                BlipUtility.setSharedPrefBoolean(getContext(), Constants.IS_LOGGED_IN, false);
                Intent intent = new Intent(getActivity(), BlipBaseActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public static void clearUserLocalInfo(Context context) {
        BlipUtility.clearSharedPref(context, Constants.FCM_TOKEN);
        BlipUtility.clearSharedPref(context, Constants.USER_FIRST_NAME);
        BlipUtility.clearSharedPref(context, Constants.USER_LAST_NAME);
        BlipUtility.clearSharedPref(context, Constants.PARENT_ID);

        BlipUtility.clearSharedPref(context, Constants.PARENT_SECTION_ID);
        BlipUtility.clearSharedPref(context, Constants.FCM_PARENT_ID);
        BlipUtility.clearSharedPref(context, Constants.IS_PARENT_FIRST_LOGIN);
        BlipUtility.clearSharedPref(context, Constants.SECONDARY_PARENT_NAME);
        BlipUtility.clearSharedPref(context, Constants.SECONDARY_PHONE_NUMBER);
        BlipUtility.clearSharedPref(context, Constants.PHONE_NUMBER);
        BlipUtility.clearSharedPref(context, Constants.INSTITUTE_ID);
        BlipUtility.clearSharedPref(context, Constants.INSTRUCTOR_ID);
        BlipUtility.clearSharedPref(context, Constants.INSTRUCTOR_SECTION_ID);
        BlipUtility.clearSharedPref(context, Constants.FCM_INSTRUCTOR_ID);
        BlipUtility.clearSharedPref(context, Constants.EMAIL_ID);
        BlipUtility.clearSharedPref(context, Constants.ADMISSION_ID);
        BlipUtility.clearSharedPref(context, Constants.CHILD_ID);
        BlipUtility.clearSharedPref(context, Constants.CHILDREN_NAME);
        BlipUtility.clearSharedPref(context, Constants.ROLE);
    }
}
