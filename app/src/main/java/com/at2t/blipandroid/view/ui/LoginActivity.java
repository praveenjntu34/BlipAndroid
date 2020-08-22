package com.at2t.blipandroid.view.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;
import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;

import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;

import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER;
import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER_LENGTH;
import static com.at2t.blipandroid.utils.Constants.SECTION_ID;

public class LoginActivity extends AppCompatActivity implements VerificationListener {

    public static final int INSTRUCTOR = 0;
    public static final int PARENT = 1;

    private LoginViewModel loginViewModel;
    public boolean isInstructorSelected;
    public boolean isParentSelected;

    private CardView parentView;
    private CardView instructorView;
    private Button btnLogin;
    private RelativeLayout rlParent;
    private RelativeLayout rlInstructor;
    private ImageView parentPanel;
    private ImageView instructorPanel;
    private TextInputLayout textInputLayoutPhone;
    private EditText etPhoneNumber;
    private String mobileNumber;
    private LiveData<Integer> liveData;
    private int type = PARENT;

    private Observer<Integer> observer = new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            if (integer != null) {
                if (integer == Enums.LoginStatus.INSTRUCTOR_LOGIN_SUCCESSFULL) {
                    isInstructorSelected = true;
                    isParentSelected = false;
                    validatePhoneNumber();
                    Log.d("Login PhoneNumber : ", "Success login");

                } else if (integer == Enums.LoginStatus.INSTRUCTOR_LOGIN_FAILED) {
                    Log.d("Login PhoneNumber : ", "Failed login");
                    checkMobileNumber();
                } else if (integer == Enums.LoginStatus.INSTRUCTOR_DOES_NOT_EXIST) {
                    isInstructorSelected = true;
                    isParentSelected = false;
                    textInputLayoutPhone.setError("Mobile number not registered");
                    textInputLayoutPhone.requestFocus();

                } else if (integer == Enums.LoginStatus.USER_LOGIN_SUCCESSFULL) {
                    Log.d("Login PhoneNumber : ", "Failed login");
                    validatePhoneNumber();
                } else if (integer == Enums.LoginStatus.USER_DOES_NOT_EXIST) {
                    textInputLayoutPhone.setError("Mobile number not registered");
                    textInputLayoutPhone.requestFocus();
                } else if (integer == Enums.LoginStatus.USER_LOGIN_FAILED) {
                    Log.d("Login PhoneNumber : ", "Failed login");
                    checkMobileNumber();
                } else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                    Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SendOTP.initializeApp(getApplication());
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.init(getApplication());

        loginViewModel.getResponseLiveData().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer != null) {
                    if (integer == Enums.LoginStatus.INSTRUCTOR_LOGIN_SUCCESSFULL) {
                        isInstructorSelected = true;
                        isParentSelected = false;
                        validatePhoneNumber();
                        Log.d("Login PhoneNumber : ", "Success login");

                    } else if (integer == Enums.LoginStatus.INSTRUCTOR_LOGIN_FAILED) {
                        Log.d("Login PhoneNumber : ", "Failed login");
                        checkMobileNumber();
                    } else if (integer == Enums.LoginStatus.INSTRUCTOR_DOES_NOT_EXIST) {
                        isInstructorSelected = true;
                        isParentSelected = false;
                        textInputLayoutPhone.setError("Mobile number not registered");
                        textInputLayoutPhone.requestFocus();

                    } else if (integer == Enums.LoginStatus.USER_LOGIN_SUCCESSFULL) {
                        Log.d("Login PhoneNumber : ", "Failed login");
                        validatePhoneNumber();
                    } else if (integer == Enums.LoginStatus.USER_DOES_NOT_EXIST) {
                        textInputLayoutPhone.setError("Mobile number not registered");
                        textInputLayoutPhone.requestFocus();
                    } else if (integer == Enums.LoginStatus.USER_LOGIN_FAILED) {
                        Log.d("Login PhoneNumber : ", "Failed login");
                        checkMobileNumber();
                    } else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initializeViews();
    }

    private void initializeViews() {
        parentView = findViewById(R.id.view2);
        instructorView = findViewById(R.id.view3);
        btnLogin = findViewById(R.id.btnLogin);
        rlParent = findViewById(R.id.rl_parent);
        rlInstructor = findViewById(R.id.rl_instructor);
        parentPanel = findViewById(R.id.parentPanel);
        instructorPanel = findViewById(R.id.instructorPanel);
        textInputLayoutPhone = findViewById(R.id.text_input_phone_number);
        etPhoneNumber = findViewById(R.id.EtPhoneNumber);

        parentView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));
        instructorView.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white));

        setClickListenerChanges();
        fcmNotification();
    }

    public void fcmNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel notificationChannel = new NotificationChannel("myNotification", "myNotification", importance);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setClickListenerChanges() {
        rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = PARENT;
                view.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGreen));
                btnLogin.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.enabled_login_button));
                rlParent.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGreen));
                rlInstructor.setBackgroundColor(Color.WHITE);
                parentPanel.setImageDrawable(getDrawable(R.drawable.parent_login_icon));
                instructorPanel.setImageDrawable(getDrawable(R.drawable.instructor_login_icon));
            }
        });

        rlInstructor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = INSTRUCTOR;
                view.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                btnLogin.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
                rlParent.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.white));
                parentPanel.setImageDrawable(getDrawable(R.drawable.rsz_parent_white));
                instructorPanel.setImageDrawable(getDrawable(R.drawable.bluebook));
                instructorPanel.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.white));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type == INSTRUCTOR) {
                    loginViewModel.loginUserUsingMobileNumber(mobileNumber);
                } else {
                    loginViewModel.loginParentUsingMobileNumber(mobileNumber);
                }
            }
        });
    }

    public void checkMobileNumber() {
        if (etPhoneNumber.getText() != null)
            mobileNumber = etPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(mobileNumber)) {
            textInputLayoutPhone.setError("Enter your mobile number");
            textInputLayoutPhone.requestFocus();
        } else if (!isPhoneNumberValid()) {
            textInputLayoutPhone.setError("Enter your valid mobile number");
            textInputLayoutPhone.requestFocus();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void validatePhoneNumber() {
        if (etPhoneNumber.getText() != null)
            mobileNumber = etPhoneNumber.getText().toString();

        if (TextUtils.isEmpty(mobileNumber)) {
            textInputLayoutPhone.setError("Enter your mobile number");
            textInputLayoutPhone.requestFocus();
        } else if (!isPhoneNumberValid()) {
            textInputLayoutPhone.setError("Enter your valid mobile number");
            textInputLayoutPhone.requestFocus();
        } else {
            etPhoneNumber.setText(mobileNumber);

            new SendOTPConfigBuilder()
                    .setCountryCode(+91)
                    .setMobileNumber(mobileNumber)
                    .setSenderId("ABCDEF")
                    .setAutoVerification(this)
                    .setMessage("##OTP## is Your verification digits.")
                    .setOtpLength(4)
                    .setVerificationCallBack(this).build();

            SendOTP.getInstance().getTrigger().initiate();

            Intent intent = new Intent(LoginActivity.this, LoginUsingOtpActivity.class);
            intent.putExtra(MOBILE_NUMBER, etPhoneNumber.getText().toString());
            intent.putExtra(SECTION_ID, BlipUtility.getSectionId(this));
            startActivity(intent);

        }
    }

        @Override
        public void onSendOtpResponse ( final SendOTPResponseCode responseCode, final String message)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("OTP TESTING", "onSendOtpResponse: " + responseCode.getCode() + "=======" + message);
                    if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                        //otp verified OR direct verified by send otp 2.O
                        Toast.makeText(getApplicationContext(), "OTP verified", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (responseCode == SendOTPResponseCode.READ_OTP_SUCCESS) {
                        //Auto read otp from sms successfully
                        // you can get otp form message filled
                        Toast.makeText(getApplicationContext(), "OTP autoread", Toast.LENGTH_SHORT).show();
                    } else if (responseCode == SendOTPResponseCode.SMS_SUCCESSFUL_SEND_TO_NUMBER || responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_FAILED_SMS_SUCCESSFUL_SEND_TO_NUMBER) {
                        // Otp send to number successfully
                        Toast.makeText(getApplicationContext(), "OTP sent", Toast.LENGTH_SHORT).show();
                    } else {
                        //exception found
                        Toast.makeText(getApplicationContext(), "OTP failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    public boolean isPhoneNumberValid () {
        return Patterns.PHONE.matcher(mobileNumber).matches()
                && mobileNumber.length() == MOBILE_NUMBER_LENGTH;
    }
}