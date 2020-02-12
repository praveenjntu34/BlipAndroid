package com.at2t.blipandroid.model;

import android.util.Patterns;

import static com.at2t.blipandroid.utils.Constants.MOBILE_NUMBER_LENGTH;

public class LoginUser {

    private String phoneNumber;

    public LoginUser(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isPhoneNumberValid() {
        return Patterns.PHONE.matcher(getPhoneNumber()).matches()
                && getPhoneNumber().length() == MOBILE_NUMBER_LENGTH;
    }
}
