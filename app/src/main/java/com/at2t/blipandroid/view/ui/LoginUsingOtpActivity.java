package com.at2t.blipandroid.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.CustomButton;
import com.msg91.sendotpandroid.library.internal.SendOTP;

import in.aabhasjindal.otptextview.OtpTextView;

public class LoginUsingOtpActivity extends AppCompatActivity {
    String value;
    boolean isParentSelected;
    boolean isInstructorSelected;
    private OtpTextView otpTextView;
    private CustomButton customButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            value = bundle.getString("userData");
            isParentSelected = bundle.getBoolean("isParentSelected");
            isInstructorSelected = bundle.getBoolean("isInstructorSelected");
        }
        initializeViews();
        onLogin();
    }

    private void initializeViews() {
        otpTextView = findViewById(R.id.et_otp);
        customButton = findViewById(R.id.btnLogin);
    }

    public void onLogin(){
        customButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateOtp(otpTextView.getOTP());
            }
        });
    }

    public void validateOtp(String otp){

        otpTextView.showSuccess();
        Toast.makeText(getApplicationContext(),"OTP verified",Toast.LENGTH_SHORT).show();
        if(otp != null && otp.length() == 4){
            startActivity(new Intent(this, DashboardActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendOTP.getInstance().getTrigger().stop();
    }
}
