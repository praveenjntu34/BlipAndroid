package com.at2t.blipandroid.view.ui;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.utils.Enums;
import com.at2t.blipandroid.utils.MyTextWatcher;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER;
import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER_LENGTH;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int INSTRUCTOR = 0;
    public static final int PARENT = 1;
    public static final String TAG = "LoginActivity";

    private LoginViewModel loginViewModel;
    public boolean isInstructorSelected;
    public boolean isParentSelected;

    private CardView parentView;
    private CardView instructorView;
    private Button btnLogin;
    private Button btnWhatsApp;
    private RelativeLayout rlParent;
    private RelativeLayout rlInstructor;
    private ImageView parentPanel;
    private ImageView instructorPanel;
    private TextInputLayout textInputLayoutPhone;
    private EditText etPhoneNumber;
    private String mobileNumber;
    private LiveData<Integer> liveData;
    private int type = PARENT;
    String fcmToken = "";
    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.8F);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        checkMobileNumber();

                    } else if (integer == Enums.LoginStatus.USER_LOGIN_SUCCESSFULL) {
                        Log.d("Login PhoneNumber : ", "Failed login");
                        validatePhoneNumber();
                    } else if (integer == Enums.LoginStatus.USER_DOES_NOT_EXIST) {
                       checkMobileNumber();

                    } else if (integer == Enums.LoginStatus.USER_LOGIN_FAILED) {
                        Log.d("Login PhoneNumber : ", "Failed login");

                    } else if (integer == Enums.LoginStatus.FCM_TOKEN_SAVED_FOR_PARENT) {
                        Log.d(TAG, "Fcm token saved successfully for parent");

                    } else if (integer == Enums.LoginStatus.FCM_TOKEN_SAVED_FOR_INSTRUCTOR) {
                        Log.d(TAG, "Fcm token saved successfully for instructor");

                    }
                    else if (integer == Enums.LoginStatus.FCM_TOKEN_SAVING_FAILED) {
                        Log.d(TAG, "Fcm token saving failed");

                    }
                    else if (integer == Enums.LoginStatus.NO_INTERNET_CONNECTION) {
                        Toast.makeText(getApplicationContext(), "Please check internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        initializeViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
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

        etPhoneNumber.addTextChangedListener(new MyTextWatcher(textInputLayoutPhone));
        btnLogin.setOnClickListener(this);
        rlParent.setOnClickListener(this);
        rlInstructor.setOnClickListener(this);
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

        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(
                new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        if (task.getResult() != null) {
                            fcmToken = task.getResult().getToken();
                        }
                        Log.d(TAG, "FCM token : " + fcmToken);
                        BlipUtility.setSharedPrefString(getApplicationContext(), Constants.FCM_TOKEN, fcmToken);
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
        } else {
            textInputLayoutPhone.setError("Mobile number is not registered, please contact the admin!");
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
            textInputLayoutPhone.setError(null);

            Intent intent = new Intent(LoginActivity.this, LoginUsingOtpActivity.class);
            intent.putExtra(MOBILE_NUMBER, etPhoneNumber.getText().toString());
            startActivity(intent);

        }
    }

    public boolean isPhoneNumberValid() {
        return Patterns.PHONE.matcher(mobileNumber).matches()
                && mobileNumber.length() == MOBILE_NUMBER_LENGTH;
    }

    public void goToOtpScreen() {
        if (type == INSTRUCTOR) {
            loginViewModel.loginUserUsingMobileNumber(mobileNumber);
            int instructorId = BlipUtility.getInstructorId(getApplicationContext());
            if(instructorId != 0) {
                loginViewModel.saveFcmTokenInstructor(instructorId, fcmToken);
            }
        } else if (type == PARENT) {
            loginViewModel.loginParentUsingMobileNumber(mobileNumber);
            int parentId = BlipUtility.getParentId(getApplicationContext());
            if(parentId != 0) {
                loginViewModel.saveFcmTokenParent(parentId, fcmToken);
            }
        }
    }

    public void connectWithWhatsApp() {
        boolean installed = checkWhatsAppInstalledOrNot("com.whatsapp");
        if(installed) {
            String phone = "+91 8125125895";
            String message = "Hi there, how may I help you?";
            Intent sendIntent = new Intent("android.intent.action.MAIN");
            sendIntent.setAction(Intent.ACTION_VIEW);
            sendIntent.setPackage("com.whatsapp");
            String url = "https://api.whatsapp.com/send?phone=" + phone + "&text=" + message;
            sendIntent.setData(Uri.parse(url));
            if (sendIntent.resolveActivity(getApplication().getPackageManager()) != null) {
                startActivity(sendIntent);
            }
        } else {
            Toast.makeText(this, "WhatsApp is not installed on your device.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkWhatsAppInstalledOrNot(String url){
        PackageManager packageManager = getPackageManager();
        boolean appInstalled;
        try{
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            appInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            appInstalled = false;
        }
        return appInstalled;
    }

    public void changeUIColorParent(View view) {
        view.startAnimation(buttonClick);
        type = PARENT;
        view.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGreen));
        btnLogin.setBackground(ContextCompat.getDrawable(getApplication(), R.drawable.enabled_login_button));
        rlParent.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorGreen));
        rlInstructor.setBackgroundColor(Color.WHITE);
        parentPanel.setImageDrawable(getDrawable(R.drawable.parent_login_icon));
        instructorPanel.setImageDrawable(getDrawable(R.drawable.instructor_login_icon));
    }

    public void changeUIColorInstructor(View view) {
        view.startAnimation(buttonClick);
        type = INSTRUCTOR;
        view.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
        btnLogin.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.colorPrimary));
        rlParent.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.white));
        parentPanel.setImageDrawable(getDrawable(R.drawable.rsz_parent_white));
        instructorPanel.setImageDrawable(getDrawable(R.drawable.bluebook));
        instructorPanel.setBackgroundColor(ContextCompat.getColor(getApplication(), R.color.white));
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnLogin) {
            view.startAnimation(buttonClick);
            mobileNumber = etPhoneNumber.getText().toString();
            goToOtpScreen();
        } else if (view.getId() == R.id.rl_parent) {
            changeUIColorParent(view);
        } else if (view.getId() == R.id.rl_instructor) {
            changeUIColorInstructor(view);
        }
    }
}