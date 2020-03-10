package com.at2t.blipandroid.viewmodel;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.view.View;

import com.at2t.blipandroid.R;
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
