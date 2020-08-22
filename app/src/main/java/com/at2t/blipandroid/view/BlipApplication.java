package com.at2t.blipandroid.view;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;

public class BlipApplication extends Application {

    private static Context sApplicationContext;
    private static BlipApplication sLearnwiseApplication;

    public static BlipApplication getLearnWiseApplication() {
        return sLearnwiseApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
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
    public static Context getBlipApplicationContext() {
        return sApplicationContext;
    }
}
