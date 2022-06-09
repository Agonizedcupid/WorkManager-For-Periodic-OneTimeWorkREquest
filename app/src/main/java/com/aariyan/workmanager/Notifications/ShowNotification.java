package com.aariyan.workmanager.Notifications;

import static com.aariyan.workmanager.Constant.Constant.CHANNEL_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aariyan.workmanager.MainActivity;
import com.aariyan.workmanager.R;

import java.util.Random;

public class ShowNotification {

    private Context context;
    private static ShowNotification showNotification;

    public static ShowNotification getInstance (Context context) {
        if (showNotification == null) {
            showNotification = new ShowNotification(context);
        }
        return showNotification;
    }
    public ShowNotification(Context context) {
        this.context = context;
    }

    //Notification channel is only needed for above Oreo:
    private void createNotificationChannel() {
        //Checking the device OS version:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //App name
            String appName = context.getString(R.string.app_name);
            //creating the notification channel here and adding all the information:
            NotificationChannel serviceChannel = new NotificationChannel(
                    //Channel id. that could be anything but same package name is recommended:
                    CHANNEL_ID,
                    //Putting the app name to show
                    appName,
                    //This is the importance on notification showing:
                    //For now we are setting as Default:
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            //Instantiating the Notification Manager:
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            //Finally creating the notification channel and passing as parameter of manager:
            manager.createNotificationChannel(serviceChannel);
        }
    }

    //It will show the notification to aware the user that, Supervisor is running in the Foreground, Chill!
    public void showNotification(String title, int counting) {
        //This intent will be used as pending intent; means when user will click on notification tab it will open this activity:
        Intent notificationIntent = new Intent(context, MainActivity.class);
        //Attaching the pending intent:
        //PendingIntent.FLAG_IMMUTABLE is used for >= android 11:
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                //this is the notification title:
                .setContentTitle(title)
                //Notification sub-title:
                .setContentText("Counting : "+counting)
                //notification icon:
                .setSmallIcon(R.mipmap.ic_launcher)
                //setting the pending intent on the notification:
                .setContentIntent(pendingIntent)
                //set the background color of intent
                .setColor(context.getResources().getColor(R.color.teal_700))
                //Finally build the notification to show:
                .build();
        /**
         * A started service can use the startForeground API to put the service in a foreground state,
         * where the system considers it to be something the user is actively aware of and thus not
         * a candidate for killing when low on memory.
         */
        // Show the notification
        NotificationManagerCompat.from(context).notify(1, notification);
    }
}
