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
import kr.hs.dgsw.flow.application.FlowApplication;
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

        if (notification == null || data == null || data.isEmpty()) return;

        String type = data.get("type");

        Intent intent = null;
        switch (type) {
            case "go_out":
            case "sleep_out":
                // 렐름을 이용할 수 있는 서비스에 DB작업을 위임
                Intent realmIntent = new Intent(this, OutRealmService.class);
                realmIntent.putExtra("type", type);
                realmIntent.putExtra("serverIdx", Integer.parseInt(data.get("idx")));

                startService(realmIntent);

                // 외출/외박인 경우 외출/외박 액티비티로 이동
                intent = new Intent(this, TicketActivity.class);
                break;
            case "notice":
                // 푸시 알림 갯수를 업데이트
                FlowApplication.setPendingNotificationCount(
                        FlowApplication.getPendingNotificationCount() + 1, true);

                // MainActivity에있는 공지 Fragment로 이동
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("defaultItem", 2);
                break;
        }

        // 알림 띄움
        sendNotification(notification.getTitle(), notification.getBody(), intent);
    }

    private void sendNotification(String title, String body, Intent intent) {
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent
                .getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        String CHANNEL_ID = "flow_channel";
        String GROUP_KEY = "kr.hs.dgsw.flow";

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_notification_none)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setGroup(GROUP_KEY)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                            CHANNEL_ID, "Flow", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setShowBadge(true);

            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());
    }
}
