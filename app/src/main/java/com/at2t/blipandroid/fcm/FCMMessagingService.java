package com.at2t.blipandroid.fcm;

import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.at2t.blipandroid.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FCMMessagingService extends FirebaseMessagingService {
    public final static String TAG = "FCMMessagingService";
    private static final String MESSAGE = "message";
    private static final String AUTO_REFRESH_BATCH_LISTING = "auto-refresh-batch-listing";
    public static final String NOTIFICATION_TYPE = "notification_type";
    public static final String CHANNEL_NAME = "channel_name";


    public FCMMessagingService() {
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sendNotification(Objects.requireNonNull(remoteMessage.getNotification()).getTitle(), remoteMessage.getNotification().getBody());
    }


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(token);
    }

    public void sendNotification(String title, String message) {
//
//        String message = remoteMessage.getNotification().getBody();
//        String title = remoteMessage.getNotification().getTitle();
//        String channelId = remoteMessage.getData().get(NOTIFICATION_TYPE);
//        String channelName = remoteMessage.getData().get(CHANNEL_NAME);
////        int notificationType = Integer.parseInt(channelId);
//
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "myNotification")
                .setSmallIcon(getNotificationIcon()).setTicker(getString(R.string.app_name)).setWhen(0)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSmallIcon(getNotificationIcon())
                .setContentText(message)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary));

//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//            FcmRegistrationIntentService fcmRegistrationIntentService = new FcmRegistrationIntentService();
//            fcmRegistrationIntentService.createNotificationChannels1(channelId, channelName);
//        }
//
//        notificationManager.notify(Constants.NOTIFICATION_ID /* ID of notification */, notificationBuilder.build());

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(999, notificationBuilder.build());
    }

    public int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.parent : R.mipmap.ic_launcher_round;
    }

}
