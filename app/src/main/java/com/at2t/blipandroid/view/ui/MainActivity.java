package com.at2t.blipandroid.view.ui;

import android.annotation.SuppressLint;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.databinding.ActivityMainBinding;
import com.at2t.blipandroid.model.LoginUser;
import com.at2t.blipandroid.viewmodel.LoginViewModel;
import com.msg91.sendotpandroid.library.internal.SendOTP;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);
        loginViewModel.getUser().observe(this, new Observer<LoginUser>() {
            @Override
            public void onChanged(@Nullable LoginUser loginUser) {
                validatePhoneNumber(loginUser);
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void validatePhoneNumber(LoginUser loginUser) {
        if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getPhoneNumber())) {
            binding.textInputPhoneNumber.setError("Enter your mobile number");
            binding.textInputPhoneNumber.requestFocus();
        }
        else if (!loginUser.isPhoneNumberValid()) {
            binding.textInputPhoneNumber.setError("Enter your valid mobile number");
            binding.textInputPhoneNumber.requestFocus();
        }
        else {
            binding.EtPhoneNumber.setText(loginUser.getPhoneNumber());
             if(loginUser.getPhoneNumber().length() == 10) {
                 binding.btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.enabled_login_button));
             }
            startActivity(new Intent(MainActivity.this, LoginUsingOtpActivity.class));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SendOTP.getInstance().getTrigger().stop();
    }
}