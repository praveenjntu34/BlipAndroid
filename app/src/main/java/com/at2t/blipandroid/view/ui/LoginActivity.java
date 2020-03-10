package com.at2t.blipandroid.view.ui;

import android.annotation.SuppressLint;

import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.databinding.ActivityMainBinding;
import com.at2t.blipandroid.model.LoginUser;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.msg91.sendotpandroid.library.internal.SendOTP;
import com.msg91.sendotpandroid.library.listners.VerificationListener;

import com.msg91.sendotpandroid.library.roots.SendOTPConfigBuilder;
import com.msg91.sendotpandroid.library.roots.SendOTPResponseCode;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity implements VerificationListener {

    private LoginViewModel loginViewModel;
    private ClickHandler handler;
    private ParentClickHandler parentClickHandler;
    private ActivityMainBinding binding;
    public boolean isInstructorSelected;
    public boolean isParentSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SendOTP.initializeApp(getApplication());
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.activity_main);
        ((ViewDataBinding) binding).setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                validatePhoneNumber(loginUser);
            }
        });

        handler = new ClickHandler(this);
        binding.setClickHandler(handler);

        parentClickHandler = new ParentClickHandler(this);
        binding.setParentClickHandler(parentClickHandler);

    }


    @SuppressLint("ResourceAsColor")
    public void validatePhoneNumber(LoginUser loginUser) {
        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPhoneNumber())) {
            binding.textInputPhoneNumber.setError("Enter your mobile number");
            binding.textInputPhoneNumber.requestFocus();
        } else if (!loginUser.isPhoneNumberValid()) {
            binding.textInputPhoneNumber.setError("Enter your valid mobile number");
            binding.textInputPhoneNumber.requestFocus();
        } else {
            binding.EtPhoneNumber.setText(loginUser.getPhoneNumber());
            Bundle bundle = new Bundle();
            bundle.putString("userData", loginUser.getPhoneNumber());
            bundle.putBoolean("isInstructorSelected", isInstructorSelected);
            bundle.putBoolean("isParentSelected", isParentSelected);
            Intent intent = new Intent(LoginActivity.this, LoginUsingOtpActivity.class);
            intent.putExtras(bundle);
            new SendOTPConfigBuilder()
                    .setCountryCode(+91)
                    .setMobileNumber(loginUser.getPhoneNumber())
                    .setSenderId("ABCDEF")
                    .setMessage("##OTP## is Your verification digits.")
                    .setOtpLength(4)
                    .setVerificationCallBack(this).build();

            SendOTP.getInstance().getTrigger().initiate();
            binding.progressCircular.setVisibility(View.VISIBLE);
            startActivity(new Intent(LoginActivity.this, LoginUsingOtpActivity.class));
        }
    }

    @Override
    public void onSendOtpResponse(final SendOTPResponseCode responseCode, final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("OTP TESTING", "onSendOtpResponse: " + responseCode.getCode() + "=======" + message);
                if (responseCode == SendOTPResponseCode.DIRECT_VERIFICATION_SUCCESSFUL_FOR_NUMBER || responseCode == SendOTPResponseCode.OTP_VERIFIED) {
                    //otp verified OR direct verified by send otp 2.O
                    Toast.makeText(getApplicationContext(), "OTP verified", Toast.LENGTH_SHORT).show();
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


    public class ClickHandler {

        private Context context;

        public ClickHandler(Context context) {
            this.context = context;
        }

        public void onInstructorClick(View v) {
            isInstructorSelected = true;
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            binding.btnLogin.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            binding.rlParent.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            binding.parentPanel.setImageDrawable(getDrawable(R.drawable.rsz_parent_white));
            binding.instructorPanel.setImageDrawable(getDrawable(R.drawable.instructor_blue));
            binding.instructorPanel.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        }
    }

    public class ParentClickHandler {

        private Context context;

        public ParentClickHandler(Context context) {
            this.context = context;
        }

        public void onParentClick(View v) {
            isParentSelected = true;
            v.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
            binding.btnLogin.setBackground(ContextCompat.getDrawable(context, R.drawable.enabled_login_button));
            binding.rlParent.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
            binding.rlInstructor.setBackgroundColor(Color.WHITE);
            binding.parentPanel.setImageDrawable(getDrawable(R.drawable.parent_login_icon));
            binding.instructorPanel.setImageDrawable(getDrawable(R.drawable.instructor_login_icon));
        }
    }
}