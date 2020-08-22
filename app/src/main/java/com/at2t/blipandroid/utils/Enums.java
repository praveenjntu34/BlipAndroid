package com.at2t.blipandroid.utils;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Enums {

    @IntDef({LoginStatus.INSTRUCTOR_LOGIN_SUCCESSFULL,
            LoginStatus.INSTRUCTOR_LOGIN_FAILED,
            LoginStatus.INSTRUCTOR_DOES_NOT_EXIST,
            LoginStatus.NO_INTERNET_CONNECTION,
            LoginStatus.USER_LOGIN_SUCCESSFULL,
            LoginStatus.USER_LOGIN_FAILED,
            LoginStatus.USER_DOES_NOT_EXIST})
    @Retention(RetentionPolicy.SOURCE)

    public @interface LoginStatus {
        int INSTRUCTOR_LOGIN_SUCCESSFULL = 1;
        int INSTRUCTOR_LOGIN_FAILED = 2;
        int INSTRUCTOR_DOES_NOT_EXIST = 3;
        int USER_LOGIN_SUCCESSFULL = 4;
        int USER_LOGIN_FAILED = 5;
        int USER_DOES_NOT_EXIST = 6;
        int NO_INTERNET_CONNECTION = 7;
    }
}