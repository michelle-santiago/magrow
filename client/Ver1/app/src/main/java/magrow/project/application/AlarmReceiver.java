package magrow.project.application;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import magrow.project.application.R;

import java.util.Random;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {

        String temp_event=intent.getStringExtra("event_id");

        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.putExtra("event123",temp_event);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);




        PendingIntent pendingIntent = stackBuilder.getPendingIntent(1, 0);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID);

        Notification notification = builder.setContentTitle("You have new Activity!")
                .setContentText(intent.getStringExtra("param"))
                .setTicker("New Message Alert!")
                .setSmallIcon(R.drawable.notif_icon)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.rice_onion))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        Random random = new Random();
        int m = random.nextInt(9999 - 1000) + 1000;
        notificationManager.notify(m, notification);

    }
}
