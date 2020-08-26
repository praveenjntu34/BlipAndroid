package com.at2t.blipandroid.fcm;

import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.at2t.blipandroid.R;
import com.at2t.blipandroid.utils.BlipUtility;
import com.at2t.blipandroid.utils.Constants;
import com.at2t.blipandroid.view.ui.MainDashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FCMMessagingService extends FirebaseMessagingService {
    public final static String TAG = "FCMMessagingService";

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

        //Store token in shared preference
        if (token != null) {
            BlipUtility.setSharedPrefString(this, Constants.FCM_TOKEN, token);
        }
    }

    public void sendNotification(String title, String message) {
        Intent intent = new Intent(this, MainDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "myNotification")
                .setSmallIcon(getNotificationIcon()).setTicker(getString(R.string.app_name)).setWhen(0)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSmallIcon(getNotificationIcon())
                .setContentText(message)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setColor(ContextCompat.getColor(this, R.color.colorPrimary));

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        notificationManager.notify(999, notificationBuilder.build());
    }


    public int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.parent : R.mipmap.ic_launcher_round;
    }

}
