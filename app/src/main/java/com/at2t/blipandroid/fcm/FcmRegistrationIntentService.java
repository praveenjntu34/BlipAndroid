package com.at2t.blipandroid.fcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * This class is used to make API calls for updating the device token and create
 * notificationChannels for Oreo and above version.*/
public class FcmRegistrationIntentService extends IntentService {
    private static final String TAG = "FcmRegistrationService";
    private static final String[] TOPICS = {"global"};
    private NotificationManager manager;

    public FcmRegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String token = BlipUtility.getSharedPrefString(getApplicationContext(), Constants.FCM_TOKEN);

        if (token != null && !token.isEmpty() ) {
            performDeviceTokenUpdateApi(token);
            subscribeTopics();
        }
    }

    /**
     * Api request to store fcm token in server
     *
     * @param token FCM refreshed token
     */
    private void performDeviceTokenUpdateApi(String token) {
//        ApiRequest request = new ApiRequest();
//        Map<String, String> postParams = new HashMap<>();
//        postParams.put(Constants.DEVICETOKEN_PARAMS, token);
//
//        request.setmRequestUrl(UrlCreator.getUrl(UrlType.FCM_REGISTRATION));
//        request.setmRequestMethod(Request.Method.POST);
//        request.setmNetworkModel(new ChannelsNetworkModel());
//        request.setmRequestParams(postParams);
//        request.setmAsyncResponseListener(new FeedDownloader.AsyncResponseListener() {
//            @Override
//            public void onResponseLoaded(ApiRequest apiRequest) {
//                onTokenUpdateApiResult(apiRequest);
//            }
//        });
//
//        FeedDownloader feedDownloader = new FeedDownloader();
//        feedDownloader.makeApiRequest(request);
    }

    /**
     * Api request success response is checked.
     *
     * @param apiRequest ApiRequest object
     */
//    private void onTokenUpdateApiResult(ApiRequest apiRequest) {
//        ChannelsNetworkModel channelsNetworkModel = (ChannelsNetworkModel) apiRequest
//                .getmNetworkModel();
//
//        if (apiRequest.getmStatusCode() != ApiRequest.DOWNLOAD_SUCCESS) {
//            Utils.log(apiRequest.getmErrorMsg());
//        }  else if (channelsNetworkModel.getData() != null ) {
//            checkChannelsSize(channelsNetworkModel.getData().getChannels());
//        } else {
//            Timber.tag(TAG);
//        }
//    }

    /**
     * Subscribe to any FCM topics of interest, as defined by the TOPICS constant.
     */
    private void subscribeTopics() {
        for (String topic : TOPICS) {
            FirebaseApp.initializeApp(this);
            FirebaseMessaging.getInstance().subscribeToTopic(topic);
        }
    }

    /**
     * Checks the channels size and compares it with getNotificationChannels size.
     *
     * @param channels List of Channels
     */
//    public void checkChannelsSize(List<Channels> channels) {
//        String id = null;
//        String name = null;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            if (channels.size() > getManager().getNotificationChannels().size()) {
//                for (int i = 0; i < channels.size(); i++) {
//                    id = channels.get(i).getId();
//                    name = channels.get(i).getName();
//                    createNotificationChannels1(id, name);
//                }
//            }
//        }
//    }

    /**
     * Create multiple notification channels after receiving the channelId and
     * channelName.
     *
     * @param channelId IDs from backend for each channel
     * @param channelName Names from backend for each channel
     */
    public void createNotificationChannels1(String channelId, String channelName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

            try {
                getManager().createNotificationChannel(notificationChannel);
            } catch (NullPointerException exception) {
                Log.e(TAG, "Oreo creation of notification channel is not successful"
                        + exception);
            }
        }
    }

    /**
     * Returns Notification manager which is used to get System Services for the Context.
     *
     * @return manager
     */
    private NotificationManager getManager() {
        if (manager == null && getApplication() != null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}