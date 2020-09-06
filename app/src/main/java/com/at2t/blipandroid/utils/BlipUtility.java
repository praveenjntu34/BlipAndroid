package com.at2t.blipandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.at2t.blipandroid.model.InstructorLoginData;
import com.at2t.blipandroid.model.ParentLoginData;
import com.at2t.blipandroid.model.UserProfileDetails;

public class BlipUtility {

    public static int instructorId;
    public static int userId;

    public static int instructorSectionId;
    public static int userSectionId;

    public static  SharedPreferences sharedPreferences;

    public static void storeInstructorBasicInfoInSharedPref(Context context, InstructorLoginData user) {
        if (user != null) {
            instructorSectionId = SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTRUCTOR_SECTION_ID, user.getSectionId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_FIRST_NAME, user.getFirstName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_LAST_NAME, user.getLastName());
            instructorId =  SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTRUCTOR_ID, user.getInstructorUserId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.ROLE, user.getRole());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTITUTE_ID, user.getRelTenantInstitutionId());
        }
    }

    public static void storeUserBasicInfoInSharedPref(Context context, ParentLoginData user) {
        if (user != null) {
            userSectionId = SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.PARENT_SECTION_ID, user.getSectionId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_FIRST_NAME, user.getFirstName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_LAST_NAME, user.getLastName());
            userId = SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.PARENT_ID, user.getParentId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.ROLE, user.getRole());
            SharedPreferencesActivtiy.setSharedPref(context, Constants.IS_PARENT_FIRST_LOGIN, user.isFirstLogin());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTITUTE_ID, user.getRelTenantInstitutionId());
        }
    }

    public static void storeParentProfileDetailsSharedPref(Context context, UserProfileDetails user) {
        if (user != null) {
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.ADMISSION_ID, user.getAdmissionId());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.CHILD_ID, user.getChildId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.CHILDREN_NAME, user.getChildrenName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.EMAIL_ID, user.getEmail());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_FIRST_NAME, user.getFirstName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.USER_LAST_NAME, user.getLastName());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.PARENT_ID, user.getParentId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.PHONE_NUMBER, user.getPhoneNumber());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.INSTITUTE_ID, user.getRelTenantInstitutionId());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.SECONDARY_PARENT_NAME, user.getSecondaryParentName());
            SharedPreferencesActivtiy.setSharedPrefString(context, Constants.SECONDARY_PHONE_NUMBER, user.getSecondaryPhoneNumber());
            SharedPreferencesActivtiy.setSharedPrefInteger(context, Constants.PERSON_ID, user.getPersonId());
        }
    }

    public static String getAdmissionId(Context context) {
        String admissionId = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ADMISSION_ID) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ADMISSION_ID).isEmpty()) {
            admissionId = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.ADMISSION_ID);
        }

        return admissionId;
    }

    public static int getChildId(Context context) {
        int childId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.CHILD_ID) != 0) {
            childId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.CHILD_ID);
        }

        return childId;
    }

    public static int getPersonId(Context context) {
        int personId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.PERSON_ID) != 0) {
            personId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.PERSON_ID);
        }

        return personId;
    }

    public static String getChildrenName(Context context) {
        String childrenName = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.CHILDREN_NAME) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.CHILDREN_NAME).isEmpty()) {
            childrenName = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.CHILDREN_NAME);
        }

        return childrenName;
    }
    public static String getSecondaryParentName(Context context) {
        String secondaryParentName = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PARENT_NAME) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PARENT_NAME).isEmpty()) {
            secondaryParentName = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PARENT_NAME);
        }

        return secondaryParentName;
    }
    public static String getSecondaryParentPhone(Context context) {
        String secondaryParentPhone = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PHONE_NUMBER
        ) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PHONE_NUMBER).isEmpty()) {
            secondaryParentPhone = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.SECONDARY_PHONE_NUMBER);
        }

        return secondaryParentPhone;
    }

    public static String getFirstName(Context context) {
        String firstName = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME).isEmpty()) {
            firstName = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_FIRST_NAME);
        }

        return firstName;
    }

    public static String getEmailId(Context context) {
        String emailId = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.EMAIL_ID) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.EMAIL_ID).isEmpty()) {
            emailId = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.EMAIL_ID);
        }

        return emailId;
    }

    public static String getPhoneNumber(Context context) {
        String phoneNumber = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.PHONE_NUMBER) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.PHONE_NUMBER).isEmpty()) {
            phoneNumber = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.PHONE_NUMBER);
        }

        return phoneNumber;
    }

    public static String getLastName(Context context) {
        String lastName = null;
        if (SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_LAST_NAME) != null && !SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_LAST_NAME).isEmpty()) {
            lastName = SharedPreferencesActivtiy.getSharedPrefString(context, Constants.USER_LAST_NAME);
        }

        return lastName;
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

    public static int getInstructorSectionId(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
//        if(instructorId != 0) {
//            if (sharedPreferences.getInt(Constants.INSTRUCTOR_SECTION_ID, 0)!= 0) {
                instructorSectionId = sharedPreferences.getInt(Constants.INSTRUCTOR_SECTION_ID, 0);
                Log.e("Blip util", "getInstructorSectionId: "+ instructorSectionId);
//            }
//        }

        return instructorSectionId;
    }

    public static int getParentSectionId(Context context) {
        if(userId != 0) {
            if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_SECTION_ID) != 0) {
                userSectionId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_SECTION_ID);
            }
        }

        return userSectionId;
    }

    public static boolean getIsParentFirstLoginId(Context context) {
        boolean isFirstLogin;
        isFirstLogin = SharedPreferencesActivtiy.getSharedPrefBool(context, Constants.IS_PARENT_FIRST_LOGIN);

        return isFirstLogin;
    }

    public static int getInstructorId(Context context) {
        int instructorId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_ID) != 0) {
            instructorId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.INSTRUCTOR_ID);
        }

        return instructorId;
    }

    public static int getParentId(Context context) {
        int parentId = 0;
        if (SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.PARENT_ID) != 0) {
            parentId = SharedPreferencesActivtiy.getSharedPrefInteger(context, Constants.PARENT_ID);
        }

        return parentId;
    }

    public static String getSharedPrefString(Context context, String key) {

        String prefString = "";
        if (context != null) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
            prefString = preference.getString(key, "");
        }

        return prefString;
    }

    public static void setSharedPrefString(Context context, String key, String value) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setSharedPrefBoolean(Context context, String key, boolean value) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void setSharedPrefInteger(Context context, String key, int value) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static boolean getSharedPrefBoolean(Context context, String key) {

        boolean prefValue = false;
        if (context != null) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
            prefValue = preference.getBoolean(key, false);
        }

        return prefValue;
    }

    public static void clearSharedPref(Context context, String key) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(key);
        editor.clear();
        editor.commit();
    }

}
