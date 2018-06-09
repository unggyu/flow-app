package kr.hs.dgsw.flow.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import kr.hs.dgsw.flow.R;
import kr.hs.dgsw.flow.data.realm.loginhistory.LoginHistoryHelper;
import kr.hs.dgsw.flow.data.realm.out.OutHelper;
import kr.hs.dgsw.flow.data.realm.user.model.User;
import kr.hs.dgsw.flow.view.ticket.TicketActivity;

public class OutMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        if (notification != null && data != null) {
            sendNotification(notification.getTitle(), notification.getBody(), data.get("type"));
        }
    }

    private void sendNotification(String title, String body, String type) {
        Intent intent = null;
        if (type.equals("go_out") || type.equals("sleep_out")) {
            intent = new Intent(this, TicketActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isNeedDatabaseUpdate", true);
        } else if (type.equals("notice")) {
            intent = new Intent(this, TicketActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("isNeedDatabaseUpdate", true);
        }
        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "channelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "Channel",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
