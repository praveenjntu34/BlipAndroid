package com.at2t.blipandroid.view.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private TextView tvInstituteName;

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
    private ImageView ivInstitute;

    private LinearLayout llUserDateOfBirth;
    private LinearLayout llUserFatherName;
    private LinearLayout llUserMotherName;
    private LinearLayout llUserYear;
    private LinearLayout llUserBranch;
    private LinearLayout llUserParentMobile;
    private LinearLayout llAdmissionId;
    private LinearLayout llGender;

    private String firstName;
    private String lastName;
    private String fullName;
    private String emailId;
    private String phoneNumber;
    private String year;
    private String branch;

    private String userAdmissionId;
    private String userFatherName;
    private String userMotherName;
    private String userParentMobileNumber;
    private String instituteName;
    private String gender;
    private String dateOfBirth;

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
        tvInstituteName = view.findViewById(R.id.tv_user_institute);

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
        ivInstitute = view.findViewById(R.id.iv_user_institute);

        llAdmissionId = view.findViewById(R.id.llAdmissionId);
        llGender = view.findViewById(R.id.ll_gender);
        llUserBranch = view.findViewById(R.id.ll_user_branch);
        llUserDateOfBirth = view.findViewById(R.id.ll_dateOfBirth);
        llUserFatherName = view.findViewById(R.id.ll_user_father);
        llUserMotherName = view.findViewById(R.id.ll_user_mother);
        llUserParentMobile = view.findViewById(R.id.ll_parent_mobile);
        llUserYear = view.findViewById(R.id.ll_user_year);

        retrieveData();
        int instructorId = BlipUtility.getInstructorId(getContext());
        if (instructorId != 0) {
            setDataForInstructor();
        } else {
            setDataForParent();
        }
    }

    private void setDataForParent() {

        tvUserPhone.setText(phoneNumber);
        tvUserEmail.setText(emailId);
        tvUserParentMobile.setText(userParentMobileNumber);
        tvAdmissionId.setText(userAdmissionId);
        tvUserDateOfBirth.setText(dateOfBirth);
        tvUserYear.setText(year);
        tvInstituteName.setText(instituteName);
        tvUserBranch.setText(branch);
        tvUserMotherName.setText(userMotherName);

        llUserParentMobile.setVisibility(View.VISIBLE);
        llUserMotherName.setVisibility(View.VISIBLE);
        llUserFatherName.setVisibility(View.VISIBLE);
        llUserDateOfBirth.setVisibility(View.VISIBLE);
        llUserBranch.setVisibility(View.VISIBLE);
        llGender.setVisibility(View.VISIBLE);
        llAdmissionId.setVisibility(View.VISIBLE);
        llUserYear.setVisibility(View.VISIBLE);

    }

    private void setDataForInstructor() {
        tvUserFullName.setText(fullName);
        tvUserPhone.setText(phoneNumber);
        tvUserEmail.setText(emailId);
        tvInstituteName.setText(instituteName);
    }

    private void retrieveData() {
        firstName = BlipUtility.getFirstName(getContext());
        lastName = BlipUtility.getLastName(getContext());

        fullName = firstName + " " + lastName;

        int parentId = BlipUtility.getParentId(getContext());

        if(parentId != 0) {
            String fullChildName = BlipUtility.getChildrenName(getContext());
            tvUserFullName.setText(fullChildName);
            tvUserFatherName.setText(fullName);
        } else {
            tvUserFullName.setText(fullName);
        }

        phoneNumber = BlipUtility.getPhoneNumber(getContext());
        emailId = BlipUtility.getEmailId(getContext());
        instituteName = BlipUtility.getUserInstituteName(getContext());

        userParentMobileNumber = BlipUtility.getSecondaryParentPhone(getContext());
        userAdmissionId = BlipUtility.getAdmissionId(getContext());

        dateOfBirth = BlipUtility.getUserDob(getContext());
        gender = BlipUtility.getUserGender(getContext());
        if(gender.equals("M")) {
            tvUserGender.setText(R.string.male_str);
        } else if(gender.equals("F")){
            tvUserGender.setText(R.string.female);
        } else {
            tvUserGender.setText(R.string.notAvailable);
        }
        dateOfBirth = BlipUtility.getUserDob(getContext());
        year = BlipUtility.getUserSectionName(getContext());
        branch = BlipUtility.getUserYear(getContext());

        userMotherName = BlipUtility.getSecondaryParentName(getContext());
    }

}
