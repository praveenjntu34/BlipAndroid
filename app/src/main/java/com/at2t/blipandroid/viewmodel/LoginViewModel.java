package com.at2t.blipandroid.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.view.View;

import com.at2t.blipandroid.model.LoginUser;

public class LoginViewModel extends ViewModel {
    public MutableLiveData<String> phoneNumber = new MutableLiveData<>();

    private MutableLiveData<LoginUser> userMutableLiveData;

    public MutableLiveData<LoginUser> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onLoginClick(View view) {
        LoginUser loginUser = new LoginUser(phoneNumber.getValue());
        userMutableLiveData.setValue(loginUser);
    }
}
