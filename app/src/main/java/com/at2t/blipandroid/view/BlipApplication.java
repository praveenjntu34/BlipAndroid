package com.at2t.blipandroid.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

public class BlipApplication extends MultiDexApplication {

    private static Context sApplicationContext;
    private static BlipApplication sLearnwiseApplication;

    public static BlipApplication getLearnWiseApplication() {
        return sLearnwiseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        MultiDex.install(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Configuration config = base.getResources().getConfiguration();
            base = base.createConfigurationContext(config);
        }
        super.attachBaseContext(base);

    }


    /**
     * Function returns the application context. Please use application context
     * in most of the network calls and activity independent task
     *
     * @return sApplicationContext application context
     */
    public static Context getLearnWiseApplicationContext() {
        return sApplicationContext;
    }
}
