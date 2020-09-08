package com.at2t.blipandroid.view.ui;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.data.repositories.UserLoginRepository;
import com.at2t.blipandroid.model.BranchSectionData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.DatePickerFragment;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.utils.MyTextWatcher;
import com.at2t.blipandroid.view.ui.fragments.AllPostsFragment;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static String TAG = "UserRegistrationActivity";
    private LoginViewModel loginViewModel;
    private EditText etUserFullName;
    private EditText etUserMobileNumber;
    private EditText etParentMobileNumber;
    private EditText etUserEmailId;
    private EditText etUserFatherName;
    private EditText etUserMotherName;
    private EditText etUserAdmissionId;
    private EditText etUserDob;

    private TextInputLayout userFullNameInputLayout;
    private TextInputLayout userMobileNumberInputLayout;
    private TextInputLayout parentMobileNumberInputLayout;
    private TextInputLayout userEmailIdInputLayout;
    private TextInputLayout userDobInputLayout;
    private TextInputLayout userFatherNameInputLayout;
    private TextInputLayout userMotherNameInputLayout;
    private TextInputLayout userAdmissionIdInputLayout;

    private Spinner spinnerGender;
    private Spinner spinnerYear;
    private Spinner spinnerBranch;

    private CheckBox checkBoxTerms;

    private TextView tvUserYear;
    private TextView tvUserBranch;
    private TextView tvUserGender;

    private String firstNameStr;
    private String lastNameStr;
    private String userFullNameStr;
    private String userMobileNumberStr;
    private String emailIdStr;
    private String userType;
    private String userAdmissionIdStr;


    private List<BranchSectionData> branchSectionDataList = new ArrayList<>();
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

    String branchName = null;
    String branchSectionName = null;
    int branchId = 0;
    private List<String> yearList;
    private List<String> genderList;
    String genderStr;
    String currentDateString;
    String instituteName;
    Calendar myCalendar;
    UserLoginRepository userLoginRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_registration);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(getApplication());
        loginViewModel.getBranchDetails(BlipUtility.getInstituteId(getApplicationContext()));
        loginViewModel.getParentProfileDetails(BlipUtility.getParentId(getApplicationContext()));

        loginViewModel.getResponseLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    if (integer == Enums.LoginStatus.PROFILE_UPDATED_SUCCESSFULLY) {
                        Toast.makeText(getApplicationContext(), "Profile details saved successfully.", Toast.LENGTH_SHORT).show();
                    } else if (integer == Enums.LoginStatus.PROFILE_UPDATE_FAILED) {
                        Toast.makeText(getApplicationContext(), "Failed saving the profile details", Toast.LENGTH_SHORT).show();
                    }
                    if (integer == Enums.LoginStatus.GET_USER_PROFILE_DETAILS_SUCCESSFULLY) {
                        setProfilePreFilledDate();
                        Toast.makeText(getApplicationContext(), "Get user profile details successfully.", Toast.LENGTH_SHORT).show();
                    } else if (integer == Enums.LoginStatus.GET_USER_PROFILE_DETAILS_FAILED) {
                        Toast.makeText(getApplicationContext(), "Failed getting the user profile details", Toast.LENGTH_SHORT).show();
                    } else if (integer == Enums.LoginStatus.GET_BRANCH_DETAILS_SUCCESSFULLY) {
                        Toast.makeText(getApplicationContext(), "Fetched the branch details successfully", Toast.LENGTH_SHORT).show();
                        fetchBranchSectionDetails();
                    } else if (integer == Enums.LoginStatus.GET_BRANCH_DETAILS_FAILED) {
                        Toast.makeText(getApplicationContext(), "Failed fetching the branch details", Toast.LENGTH_SHORT).show();
                    } else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initializeViews();
    }

    private void setProfilePreFilledDate() {
        childId = BlipUtility.getChildId(getApplicationContext());
        childrenName = BlipUtility.getChildrenName(getApplicationContext());
        parentId = BlipUtility.getParentId(getApplicationContext());
        personId = BlipUtility.getPersonId(getApplicationContext());
        relTenantInstitutionId = BlipUtility.getInstituteId(getApplicationContext());

        etUserMobileNumber.setText(BlipUtility.getPhoneNumber(getApplicationContext()));
        etUserAdmissionId.setText(BlipUtility.getAdmissionId(getApplicationContext()));
        etUserDob.setText(BlipUtility.getUserDob(getApplicationContext()));

        secondaryParentName = BlipUtility.getSecondaryParentName(getApplicationContext());
        etUserFatherName.setText(secondaryParentName);

        secondaryPhoneNumber = BlipUtility.getSecondaryParentPhone(getApplicationContext());
        etParentMobileNumber.setText(secondaryPhoneNumber);

        sectionId = BlipUtility.getParentSectionId(getApplicationContext());

        instituteName = BlipUtility.getUserInstituteName(getApplicationContext());
    }

    private void fetchBranchSectionDetails() {
        branchSectionDataList = loginViewModel.getBranchData();
        setUpYearDropDown(branchSectionDataList);
    }

    private void setUpYearDropDown(final List<BranchSectionData> branchSectionDataList) {
        yearList = new ArrayList<>();

        if (branchSectionDataList != null) {
            for (int i = 0; i < branchSectionDataList.size(); i++) {
                branchName = branchSectionDataList.get(i).getBranchName();
                branchId = branchSectionDataList.get(i).getBranchId();
                yearList.add(branchName);
            }
        }

        spinnerYear = (Spinner) findViewById(R.id.sp_year_selector);
        tvUserYear = (TextView) findViewById(R.id.tv_user_year);
        if (branchSectionDataList != null && branchSectionDataList.size() > 1) {
            spinnerYear.setOnTouchListener(spinnerOnTouch);
        }
    }

    private View.OnTouchListener spinnerOnTouch = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout
                        .item_year_spinner, yearList) {
                    @Override
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = super.getView(position, convertView, parent);
                        view.setPadding(view.getPaddingLeft(), 0, view.getPaddingRight(), 0);
                        return view;
                    }
                };

                spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

                spinnerYear.setAdapter(spinnerArrayAdapter);
                tvUserYear.setVisibility(View.GONE);
                spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                        getValueOfBranch(position, branchSectionDataList);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parentView) {

                    }
                });
            }
            return false;
        }
    };

    private void getValueOfBranch(int position, List<BranchSectionData> branchSectionDataList) {
        List<String> branchSectionNameList = new ArrayList<>();
        for (int j = 0; j < branchSectionDataList.get(position).getSections().size(); j++) {
            branchSectionName = branchSectionDataList.get(position).getSections().get(j).getSectionName();
            branchSectionNameList.add(branchSectionName);
        }
        branchSectionNameList.add(0, "Select branch");

        spinnerBranch = (Spinner) findViewById(R.id.sp_branch_selector);
        tvUserBranch = (TextView) findViewById(R.id.tv_user_branch);

        if (branchSectionNameList != null) {
            tvUserBranch.setVisibility(View.GONE);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout
                .item_branch_spinner, branchSectionNameList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(view.getPaddingLeft(), 0, view.getPaddingRight(), 0);
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        spinnerBranch.setAdapter(spinnerArrayAdapter);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                TextView selectedTextView = (TextView) selectedItemView;
                if (selectedItemView != null && selectedItemView instanceof TextView) {
                    ((TextView) selectedItemView).setTextColor(ContextCompat.getColor(getApplication(), R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    private void updateUserDetails(String userMobileNumber, String admissionId, String userFullName, String emailIdUpdatedStr, String userDateOfBirthStr, String userGenderStr, String userFatherName, String userMotherNameStr, String userParentMobileNumber, String userYear, String userBranchStr) {

        loginViewModel.updateUserProfileDetails(admissionId, childId, userFullName, emailIdUpdatedStr,
                firstNameStr, lastNameStr, parentId, personId, userMobileNumber, relTenantInstitutionId,
                userFatherName, userParentMobileNumber, sectionId, instituteName, userGenderStr, userDateOfBirthStr);
    }

    private boolean isAllFieldsEntered(String mobileNumber, String admissionId, String fullName,
                                       String emailText, String userDob, String genderStr,
                                       String fatherName, String motherName, String userParentMobileNumber,
                                       String year, String branchName) {
        setNoError();
        boolean isFilled = true;
        if (fullName.length() < 1) {
            isFilled = false;
            userFullNameInputLayout.setError("Enter your full name");
        }
        if (mobileNumber.length() < 1) {
            isFilled = false;
            userMobileNumberInputLayout.setError("Enter your mobile number");
        }
        if (emailText == null) {
            if (emailText.length() < 1) {
                userEmailIdInputLayout.setError("Enter your email id");
                isFilled = false;
            } else {
                if (!isValid(emailText)) {
                    isFilled = false;
                    userEmailIdInputLayout.setError("Please enter your valid email");
                }
            }
        }
        if (userParentMobileNumber == null) {
            if (userParentMobileNumber.length() < 1) {
                parentMobileNumberInputLayout.setError("Enter your parent phone number ");
                isFilled = false;
            } else {
                if (userParentMobileNumber.length() != 10) {
                    isFilled = false;
                    parentMobileNumberInputLayout.setError("Enter your parent's valid phone number ");
                }
            }
        }
        if (genderStr.isEmpty()) {
            isFilled = false;
            Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();
        }
        if (year.isEmpty()) {
            isFilled = false;
            Toast.makeText(this, "Please select your year", Toast.LENGTH_SHORT).show();
        }
        if (branchName.isEmpty()) {
            isFilled = false;
            Toast.makeText(this, "Please select your branch", Toast.LENGTH_SHORT).show();
        }
        if (userDob.isEmpty()) {
            isFilled = false;
            userDobInputLayout.setError("Please enter your Dob");
        }
        if (!checkBoxTerms.isChecked()) {
            isFilled = false;
            Toast.makeText(this, "Please tick the box of terms and conditions", Toast.LENGTH_SHORT).show();
        }
        return isFilled;
    }

    private boolean isValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return email.matches(emailPattern);
    }

    private void setNoError() {
        userFullNameInputLayout.setError(null);
        userEmailIdInputLayout.setError(null);
        userDobInputLayout.setError(null);
        parentMobileNumberInputLayout.setError(null);
    }

    private void initializeViews() {
        saveBtn = findViewById(R.id.saveBtn);
        saveBtn.setOnClickListener(this);
        etUserAdmissionId = findViewById(R.id.etUser_admission_no);
        etUserDob = findViewById(R.id.etUser_dob);
        etUserFatherName = findViewById(R.id.etUser_father_name);
        etUserMotherName = findViewById(R.id.etUser_mother_name);
        etParentMobileNumber = findViewById(R.id.et_parent_phone_number);
        etUserEmailId = findViewById(R.id.etUser_email);
        etUserFullName = findViewById(R.id.etUser_full_name);
        etUserMobileNumber = findViewById(R.id.etUser_phone_number);

        userAdmissionIdInputLayout = findViewById(R.id.text_input_user_admission_no);
        userFullNameInputLayout = findViewById(R.id.text_input_user_full_name);
        userMobileNumberInputLayout = findViewById(R.id.text_input_user_phone_number);
        userDobInputLayout = findViewById(R.id.text_input_user_dob);
        userEmailIdInputLayout = findViewById(R.id.text_input_user_email);
        userFatherNameInputLayout = findViewById(R.id.text_input_user_father_name);
        userMotherNameInputLayout = findViewById(R.id.text_input_user_mother_name);
        parentMobileNumberInputLayout = findViewById(R.id.text_input_parent_phone_number);

        checkBoxTerms = findViewById(R.id.check_terms_and_conditions);

        etUserAdmissionId.addTextChangedListener(new MyTextWatcher(userAdmissionIdInputLayout));
        etUserDob.addTextChangedListener(new MyTextWatcher(userDobInputLayout));
        etUserFatherName.addTextChangedListener(new MyTextWatcher(userFatherNameInputLayout));
        etUserMotherName.addTextChangedListener(new MyTextWatcher(userMotherNameInputLayout));
        etParentMobileNumber.addTextChangedListener(new MyTextWatcher(parentMobileNumberInputLayout));
        etUserEmailId.addTextChangedListener(new MyTextWatcher(userEmailIdInputLayout));
        etUserFullName.addTextChangedListener(new MyTextWatcher(userFullNameInputLayout));
        etUserMobileNumber.addTextChangedListener(new MyTextWatcher(userMobileNumberInputLayout));

        setGenderDropDown();

        etUserDob.setOnClickListener(this);
        checkBoxTerms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Drawable d = getResources().getDrawable(R.drawable.green_btn_background);
                    saveBtn.setEnabled(true);
                    saveBtn.setBackground(d);
                } else {
                    saveBtn.setEnabled(false);
                    saveBtn.setBackgroundColor(Color.parseColor("#a9a9a9"));
                }
            }
        });

        setData();

    }

    private void setGenderDropDown() {
        genderList = new ArrayList<>();
        genderList.add("Female");
        genderList.add("Male");

        if (genderList != null) {
            for (int i = 0; i < genderList.size(); i++) {
                genderStr = genderList.get(i);
            }
        }

        spinnerGender = (Spinner) findViewById(R.id.sp_gender_selector);
        tvUserGender = (TextView) findViewById(R.id.tv_user_gender);

        if (genderList != null) {
            tvUserGender.setVisibility(View.GONE);
        }

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout
                .item_branch_spinner, genderList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                view.setPadding(view.getPaddingLeft(), 0, view.getPaddingRight(), 0);
                return view;
            }
        };

        spinnerArrayAdapter.setDropDownViewResource(R.layout.item_spinner);

        spinnerGender.setAdapter(spinnerArrayAdapter);
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                TextView selectedTextView = (TextView) selectedItemView;
                if (selectedItemView != null && selectedItemView instanceof TextView) {
                    ((TextView) selectedItemView).setTextColor(ContextCompat.getColor(getApplication(), R.color.black));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.saveBtn) {
            String userFatherName = etUserFatherName.getText().toString();
            String userMotherNameStr = etUserMotherName.getText().toString();
            String userParentMobileNumber = etParentMobileNumber.getText().toString();
            String userBranchStr = branchSectionName;
            String userYear = branchName;
            String userGenderStr = genderStr;
            if (genderStr.equals("Female")) {
                genderStr = "F";
            } else if (genderStr.equals("Male")) {
                genderStr = "M";
            }
            String userDateOfBirthStr;
            if (currentDateString != null) {
                userDateOfBirthStr = currentDateString;
            } else {
                userDateOfBirthStr = BlipUtility.getUserDob(getApplicationContext());
            }
            String userMobileNumber = etUserMobileNumber.getText().toString();
            String userFullName = etUserFullName.getText().toString();
            String emailIdUpdatedStr = etUserEmailId.getText().toString();

            if (isAllFieldsEntered(userMobileNumber, admissionId, userFullName, emailIdUpdatedStr,
                    userDateOfBirthStr, userGenderStr, userFatherName, userMotherNameStr, userParentMobileNumber,
                    userYear, userBranchStr)) {
                saveBtn.setEnabled(true);
                updateUserDetails(userMobileNumber, admissionId, userFullName, emailIdUpdatedStr,
                        userDateOfBirthStr, userGenderStr, userFatherName, userMotherNameStr, userParentMobileNumber,
                        userYear, userBranchStr);
            }

            Intent intent = new Intent(this, MainDashboardActivity.class);
            startActivity(intent);
            finish();
        } else if (view.getId() == R.id.etUser_dob) {
            myCalendar = Calendar.getInstance();
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    updateLabel(year, monthOfYear, dayOfMonth);
                }

            };

            new DatePickerDialog(UserRegistrationActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }

    private void updateLabel(int year, int monthOfYear, int dayOfMonth) {
        String myFormat = (year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        etUserDob.setText(sdf.format(myCalendar.getTime()));
    }

    private void setData() {
        firstNameStr = BlipUtility.getFirstName(getApplicationContext());
        lastNameStr = BlipUtility.getLastName(getApplicationContext());

        userFullNameStr = firstNameStr + " " + lastNameStr;
        etUserFullName.setText(userFullNameStr);

        userAdmissionIdStr = BlipUtility.getAdmissionId(getApplicationContext());
        etUserAdmissionId.setText(userAdmissionIdStr);

        userMobileNumberStr = BlipUtility.getPhoneNumber(getApplicationContext());
        etUserMobileNumber.setText(userMobileNumberStr);

        emailIdStr = BlipUtility.getEmailId(getApplicationContext());
        etUserEmailId.setText(emailIdStr);

        //TODO dob, gender, mother name
        etUserDob.setText(currentDateString);

        childId = BlipUtility.getChildId(getApplicationContext());
        childrenName = BlipUtility.getChildrenName(getApplicationContext());
        parentId = BlipUtility.getParentId(getApplicationContext());
        personId = BlipUtility.getPersonId(getApplicationContext());
        relTenantInstitutionId = BlipUtility.getInstituteId(getApplicationContext());

        secondaryParentName = BlipUtility.getSecondaryParentName(getApplicationContext());
        etUserFatherName.setText(secondaryParentName);

        secondaryPhoneNumber = BlipUtility.getSecondaryParentPhone(getApplicationContext());
        etParentMobileNumber.setText(secondaryPhoneNumber);

        sectionId = BlipUtility.getParentSectionId(getApplicationContext());

        instituteName = BlipUtility.getUserInstituteName(getApplicationContext());
    }
}
