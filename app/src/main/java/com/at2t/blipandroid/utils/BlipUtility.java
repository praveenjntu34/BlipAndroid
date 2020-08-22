package com.at2t.blipandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.model.ParentLoginData;

public class BlipUtility {

    public static void storeInstructorBasicInfoInSharedPref(Context context, InstructorLoginData user) {
        if (user != null) {
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.SECTION_ID, user.getSectionId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_FIRST_NAME, user.getFirstName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_LAST_NAME, user.getLastName());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTRUCTOR_ID, user.getInstructorUserId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.ROLE, user.getRole());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTITUTE_ID, user.getRelTenantInstitutionId());
        }
    }

    public static void storeUserBasicInfoInSharedPref(Context context, ParentLoginData user) {
        if (user != null) {
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.SECTION_ID, user.getSectionId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_FIRST_NAME, user.getFirstName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_LAST_NAME, user.getLastName());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTRUCTOR_ID, user.getParentId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.ROLE, user.getRole());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTITUTE_ID, user.getRelTenantInstitutionId());
        }
    }

    public static String getFirstName(Context context) {
        String firstName = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME).isEmpty()) {
            firstName = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME);
        }

        return firstName;
    }

    public static String getRole(Context context) {
        String role = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ROLE) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ROLE).isEmpty()) {
            role = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ROLE);
        }

        return role;
    }

    public static int getInstituteId(Context context) {
        int instituteId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTITUTE_ID) != 0) {
            instituteId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTITUTE_ID);
        }

        return instituteId;
    }

    public static int getSectionId(Context context) {
        int  sectionId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.SECTION_ID) != 0) {
            sectionId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.SECTION_ID);
        }

        return sectionId;
    }

    public static int getInstructorId(Context context) {
        int  instructorId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_ID) != 0) {
            instructorId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_ID);
        }

        return instructorId;
    }

    public static String getSharedPrefString(Context context, String key) {

        String prefString = "";
        if (context != null) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
            prefString = preference.getString(key, "");
        }

        return prefString;
    }
}
