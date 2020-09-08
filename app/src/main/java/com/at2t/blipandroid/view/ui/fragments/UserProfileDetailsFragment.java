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
import com.at2t.blipandroid.utils.BlipUtility;

public class UserProfileDetailsFragment extends BaseFragment {
    public static final String TAG = "UserProfileDetailsFragment";
    private TextView tvUserFullName;
    private TextView tvAdmissionId;
    private TextView tvUserEmail;
    private TextView tvUserGender;
    private TextView tvUserPhone;

    private TextView tvUserDateOfBirth;
    private TextView tvUserFatherName;
    private TextView tvUserMotherName;
    private TextView tvUserYear;
    private TextView tvUserBranch;
    private TextView tvUserParentMobile;

    private ImageView ivUserFullName;
    private ImageView ivAdmissionId;
    private ImageView ivUserEmail;
    private ImageView ivUserGender;
    private ImageView ivUserPhone;
    private ImageView ivUserDateOfBirth;
    private ImageView ivUserFatherName;
    private ImageView ivUserMotherName;
    private ImageView ivUserYear;
    private ImageView ivUserBranch;
    private ImageView ivUserParentMobile;

    private String firstName;
    private String lastName;
    private String fullName;
    private String emailId;
    private String phoneNumber;
    private String userType;

    private String userAdmissionId;
    private String userFatherName;
    private String userMotherName;
    private String userParentMobileNumber;
    private int userYear;
    private String userBranch;
    private String userGender;
    private String userDateOfBirth;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_profile_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
    }

    private void initializeViews(View view) {
        tvAdmissionId = view.findViewById(R.id.tv_user_admission_id);
        tvUserBranch = view.findViewById(R.id.tv_user_branch);
        tvUserDateOfBirth = view.findViewById(R.id.tv_dateOfBirth);
        tvUserEmail = view.findViewById(R.id.tv_user_email);
        tvUserFatherName = view.findViewById(R.id.tv_user_father_name);
        tvUserFullName = view.findViewById(R.id.tv_user_full_name);
        tvUserGender = view.findViewById(R.id.tv_user_gender);
        tvUserMotherName = view.findViewById(R.id.tv_user_mother_name);
        tvUserParentMobile = view.findViewById(R.id.tv_parent_mobile_number);
        tvUserPhone = view.findViewById(R.id.tv_user_phone);
        tvUserYear = view.findViewById(R.id.tv_user_year);

        ivAdmissionId = view.findViewById(R.id.iv_user_admission_id);
        ivUserBranch = view.findViewById(R.id.iv_user_branch);
        ivUserDateOfBirth = view.findViewById(R.id.iv_dateOfBirth);
        ivUserEmail = view.findViewById(R.id.iv_user_email);
        ivUserFatherName = view.findViewById(R.id.iv_user_father_name);
        ivUserFullName = view.findViewById(R.id.iv_user_full_name);
        ivUserGender = view.findViewById(R.id.iv_user_gender);
        ivUserParentMobile = view.findViewById(R.id.iv_parent_mobile_number);
        ivUserMotherName = view.findViewById(R.id.iv_user_mother_name);
        ivUserYear = view.findViewById(R.id.iv_user_year);
        ivUserPhone = view.findViewById(R.id.iv_user_phone);

        retrieveData();
        setData();
    }

    private void setData() {
        tvUserFullName.setText(fullName);
        tvUserPhone.setText(phoneNumber);
        tvUserEmail.setText(emailId);
        tvUserParentMobile.setText(userParentMobileNumber);
        tvUserFatherName.setText(userFatherName);
        tvAdmissionId.setText(userAdmissionId);
    }

    private void retrieveData() {
        firstName = BlipUtility.getFirstName(getContext());
        lastName = BlipUtility.getLastName(getContext());

        fullName = firstName + " " + lastName;

        phoneNumber = BlipUtility.getPhoneNumber(getContext());
        emailId = BlipUtility.getEmailId(getContext());
        userParentMobileNumber = BlipUtility.getSecondaryParentPhone(getContext());
        userAdmissionId = BlipUtility.getAdmissionId(getContext());

        //TODO Branch, father name, mother name, date of birth, gender and year data

        userFatherName = BlipUtility.getSecondaryParentName(getContext());
    }

    public void showAllViews() {

    }

    public void hideViews() {

    }

}
