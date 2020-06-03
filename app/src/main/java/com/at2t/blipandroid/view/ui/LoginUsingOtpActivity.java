package com.at2t.blipandroid.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.CustomButton;
import com.msg91.sendotpandroid.library.internal.SendOTP;

import in.aabhasjindal.otptextview.OtpTextView;

public class LoginUsingOtpActivity extends AppCompatActivity {

    private OtpTextView otpTextView;
    private CustomButton customButton;
    private LinearLayout llLoginTopHeader;
    private TextView tvResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);

        initializeViews();
        onLogin();
        changeOtpScreenColor();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SendOTP.getInstance().getTrigger().stop();
    }

    private void changeOtpScreenColor() {
        String role = BlipUtility.getRole(this);
        if(role != null && role.equals("Instructor")) {
            customButton.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            llLoginTopHeader.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
            tvResend.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }
    }

    private void initializeViews() {
        otpTextView = findViewById(R.id.et_otp);
        customButton = findViewById(R.id.btnLogin);
        llLoginTopHeader = findViewById(R.id.llLogin);
        tvResend = findViewById(R.id.tvResend);
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
            startActivity(new Intent(this, MainDashboardActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendOTP.getInstance().getTrigger().stop();
    }
}
