package com.skAndroid.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.NotificationCompat;
import com.skAndroid.R;
import com.skAndroid.activity.NotificationActivity;
import com.skAndroid.dbHelper.DatabaseUtil;
import com.skAndroid.dto.Notification;

/**
 * Created by khangtnse60992 on 11/19/2014.
 */
public class AlarmReceive extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
         this.context = context;
         startNotification();
    }

    public void startNotification() {
        Notification notification = getRandomNotification();
        if(notification==null) {
            Intent intent = new Intent(context, AlarmReceive.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    context, 234324243, intent, PendingIntent.FLAG_NO_CREATE);
            pendingIntent.cancel();
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            context.stopService(new Intent(context, TTSService.class));
        } else {
            // Open NotificationView Class on Notification Click
            Intent intent = new Intent(context, NotificationActivity.class);
            // Send data to NotificationView Class
            intent.putExtra("notification", notification);
            // Open NotificationView.java Activity
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(notification.sentence).setContentText(notification.sentenceTrans).setTicker(notification.sentence)
                    .setSmallIcon(R.drawable.logonotifi)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true);
            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            // Build Notification with Notification Manager
            notificationmanager.notify(0, builder.build());
            Vibrator vibrator = (Vibrator) context
                    .getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);
            if(TTSService.autoSpeak==1) {
                TTSService.ttobj.speak(notification.sentence, TextToSpeech.QUEUE_FLUSH, null);
            }
        }
    }

    public Notification getRandomNotification() {
        Notification notification = null;
        DatabaseUtil databaseUtil = new DatabaseUtil(context);
        notification = databaseUtil.getRandomNotification();
        return notification;
    }


}

