package com.at2t.blipandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.at2t.blipandroid.R;

public class SharedPreferencesActivtiy {

    public static boolean isValidPhoneNumber(String phone) {
        boolean isValidPhone = false;

        if (phone != null) {
            //matches 12-digit numbers only
            String regexStr = "^[0-9]{7,12}$";
            if (phone.matches(regexStr)) {
                isValidPhone = true;
            } else {
                isValidPhone = false;
            }
        }
        return isValidPhone;
    }

    public static void setSharedPrefString(Context context, String key, String value) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static int setSharedPrefInteger(Context context, String key, int value) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putInt(key, value);
        editor.apply();
        return value;
    }

    public static int getSharedPrefInteger(Context context, String key) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getInt(key, 0);

    }

    public static void setSharedPref(Context context, String type, boolean b) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.putBoolean(type, b);
        editor.commit();
        editor = null;
    }

    public static void clearSharedPref(Context context, String key) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preference.edit();
        editor.remove(key);
        editor.commit();
        editor = null;
    }

    public static String getSharedPrefString(Context context, String key) {

        String prefString = "";
        if (context != null) {
            SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
            prefString = preference.getString(key, "");
        }

        return prefString;
    }

    public static boolean getSharedPrefBool(Context context, String key) {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        return preference.getBoolean(key, false);

    }

    public static void showNoInternetMsg(Context context) {
        String msg = context.getResources().getString(R.string.no_internet_msg);
        showToast(context, msg);
    }

    public static void showToast(Context c, String msg) {
        LayoutInflater inflater = (LayoutInflater)
                c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Call toast.xml file for toast layout
        View vieew = inflater.inflate(R.layout.toast_popup, null);

        Toast toast = new Toast(c);

        // Set layout to toast
        toast.setView(vieew);
        toast.setGravity(Gravity.BOTTOM,
                0, 200);
        TextView text = (TextView) vieew.findViewById(R.id.tv_toast);
        text.setText(msg);
        /* toast.makeText(c, msg + "", toast.LENGTH_LONG).show();*/
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
}
