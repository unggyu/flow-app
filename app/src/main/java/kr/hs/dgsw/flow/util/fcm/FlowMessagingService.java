package kr.hs.dgsw.flow.util.fcm;

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
import kr.hs.dgsw.flow.application.Foreground;
import kr.hs.dgsw.flow.util.service.OutRealmService;
import kr.hs.dgsw.flow.view.main.MainActivity;
import kr.hs.dgsw.flow.view.ticket.TicketActivity;

public class FlowMessagingService extends FirebaseMessagingService {

    /**
     * 포그라운드일 때만 작동함
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();
        if (notification != null && data != null && !data.isEmpty()) {
            sendNotification(notification.getTitle(), notification.getBody(), data);
        }
    }

    private void sendNotification(String title, String body, Map<String, String> data) {
        Intent intent = null;
        String type = data.get("type");
        if (type.equals("go_out") || type.equals("sleep_out")) {
            // 외출/외박인 경우 외출/외박 액티비티로 이동
            intent = new Intent(Foreground.get().getNowActivity(), TicketActivity.class);

            Intent realmIntent = new Intent(this, OutRealmService.class);
            realmIntent.putExtra("type", type);
            realmIntent.putExtra("serverIdx", Integer.parseInt(data.get("idx")));
            startService(realmIntent);
        } else if (type.equals("notice")) {
            // MainActivity에있는 공지 Fragment로 이동
            intent = new Intent(Foreground.get().getNowActivity(), MainActivity.class);
            intent.putExtra("defaultItemId", R.id.navigation_notifications);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String channelId = "channelId";
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_launcher_background)
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
