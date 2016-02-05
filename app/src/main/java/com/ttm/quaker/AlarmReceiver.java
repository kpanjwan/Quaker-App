package com.ttm.quaker;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by Kuldeeppanjwani on 2015-11-05.
 */
public class AlarmReceiver extends BroadcastReceiver {

    int notifyID= 1;
    @Override
    public void onReceive(Context context, Intent intent) {

      //  String notificationTitle = intent.getExtras().getString("notificationTitle");


        String soundResource = "android.resource://com.asdqwe.asd/raw/asd_tone";
        Uri soundUri = Uri.parse(soundResource);

        NotificationCompat.Builder mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)//
                .setSmallIcon(R.drawable.quaker_logo)//
                .setSound(soundUri)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.quaker_logo))
                .setContentTitle("Meal Reminder")
                .setContentText(intent.getExtras().getString("KEY"));//

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(context, MealReminder.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MealReminder.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(notifyID, mBuilder.build());
        mBuilder.setAutoCancel(true);

    }
}