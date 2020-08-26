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
            LoginStatus.USER_DOES_NOT_EXIST,
            LoginStatus.FCM_TOKEN_SAVED_FOR_INSTRUCTOR,
            LoginStatus.FCM_TOKEN_SAVED_FOR_PARENT,
            LoginStatus.FCM_TOKEN_SAVING_FAILED,
            LoginStatus.PROFILE_UPDATED_SUCCESSFULLY,
            LoginStatus.PROFILE_UPDATE_FAILED,
            LoginStatus.GET_USER_PROFILE_DETAILS_SUCCESSFULLY,
            LoginStatus.GET_USER_PROFILE_DETAILS_FAILED,
            LoginStatus.GET_ALL_POSTS_SUCCESSFULLY,
            LoginStatus.GET_ALL_POSTS_FAILED,

    })
    @Retention(RetentionPolicy.SOURCE)

    public @interface LoginStatus {
        int INSTRUCTOR_LOGIN_SUCCESSFULL = 1;
        int INSTRUCTOR_LOGIN_FAILED = 2;
        int INSTRUCTOR_DOES_NOT_EXIST = 3;
        int USER_LOGIN_SUCCESSFULL = 4;
        int USER_LOGIN_FAILED = 5;
        int USER_DOES_NOT_EXIST = 6;
        int NO_INTERNET_CONNECTION = 7;
        int FCM_TOKEN_SAVED_FOR_PARENT = 8;
        int FCM_TOKEN_SAVING_FAILED = 9;
        int FCM_TOKEN_SAVED_FOR_INSTRUCTOR = 10;
        int PROFILE_UPDATED_SUCCESSFULLY = 11;
        int PROFILE_UPDATE_FAILED = 12;
        int GET_USER_PROFILE_DETAILS_SUCCESSFULLY = 13;
        int GET_USER_PROFILE_DETAILS_FAILED = 14;
        int GET_ALL_POSTS_SUCCESSFULLY = 15;
        int GET_ALL_POSTS_FAILED = 16;
    }
}