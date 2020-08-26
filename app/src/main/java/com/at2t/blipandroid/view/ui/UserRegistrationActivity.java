package com.at2t.blipandroid.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.viewmodel.LoginViewModel;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "UserRegistration";
    private LoginViewModel loginViewModel;
    private EditText firstName;
    private EditText lastName;
    private EditText emailId;
    private EditText mobileNumber;
    private String firstNameStr;
    private String lastNameStr;
    private String emailIdStr;
    private String mobileNumberStr;

    private String firstNameUpdatedStr;
    private String lastNameUpdatedStr;
    private String emailIdUpdatedStr;
    private String mobileNumberUpdatedStr;

    private Button saveBtn;
    String admissionId;
    Integer childId;
    String childrenName;
    Integer parentId;
    Integer personId;
    Integer relTenantInstitutionId;
    String secondaryParentName;
    String secondaryPhoneNumber;
    Integer sectionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_registration);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(getApplication());

        loginViewModel.getResponseLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    if (integer == Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY) {
//                        loginViewModel.getParentProfileDetails(parentId);
                        Toast.makeText(getApplicationContext(), "Profile details saved successfully.", Toast.LENGTH_SHORT).show();

                    } else if (integer == Enums.LoginStatus.PROFILE_UPDATE_FAILED) {
                        Toast.makeText(getApplicationContext(), "Something went wrong, please try again", Toast.LENGTH_SHORT).show();
                    } else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initializeViews();
    }

    private void updateUserDetails(String firstNameUpdatedStr, String lastNameUpdatedStr, String mobileNumberUpdatedStr, String emailIdUpdatedStr) {
        loginViewModel.updateUserProfileDetails(admissionId, childId, childrenName, emailIdUpdatedStr,
                firstNameUpdatedStr, lastNameUpdatedStr, parentId, personId, mobileNumberUpdatedStr, relTenantInstitutionId,
                secondaryParentName, secondaryPhoneNumber, sectionId);
    }

    private void initializeViews() {
        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        emailId = findViewById(R.id.email);
        mobileNumber = findViewById(R.id.mobile_number);
        saveBtn = findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(this);

        firstNameStr = BlipUtility.getFirstName(getApplicationContext());
        lastNameStr = BlipUtility.getLastName(getApplicationContext());
        emailIdStr = BlipUtility.getEmailId(getApplicationContext());
        mobileNumberStr = BlipUtility.getPhoneNumber(getApplicationContext());

        admissionId = BlipUtility.getAdmissionId(getApplicationContext());
        childId = BlipUtility.getChildId(getApplicationContext());
        childrenName = BlipUtility.getChildrenName(getApplicationContext());
        parentId = BlipUtility.getParentId(getApplicationContext());
        personId = BlipUtility.getPersonId(getApplicationContext());
        relTenantInstitutionId = BlipUtility.getInstituteId(getApplicationContext());
        secondaryParentName = BlipUtility.getSecondaryParentName(getApplicationContext());
        secondaryPhoneNumber = BlipUtility.getSecondaryParentPhone(getApplicationContext());
        sectionId = BlipUtility.getSectionId(getApplicationContext());

        if (firstNameStr != null) {
            firstName.setText(firstNameStr);
        }
        if (emailIdStr != null) {
            emailId.setText(emailIdStr);
        }
        if (mobileNumberStr != null) {
            mobileNumber.setText(mobileNumberStr);
        }
        if (lastNameStr != null) {
            lastName.setText(lastNameStr);
        }



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveBtn) {
            firstNameUpdatedStr = firstName.getText().toString();
            lastNameUpdatedStr = lastName.getText().toString();
            mobileNumberUpdatedStr = mobileNumber.getText().toString();
            emailIdUpdatedStr = emailId.getText().toString();

            updateUserDetails(firstNameUpdatedStr, lastNameUpdatedStr, mobileNumberUpdatedStr, emailIdUpdatedStr);

            Intent intent = new Intent(this, MainDashboardActivity.class);
            startActivity(intent);
        }

    }
}
