package com.apm.block.utils;

import android.content.Context;


/**
 * Created by Hyman on 2018/5/5.
 */
public class NotificationUtil {
    private int notificationId;
    private volatile static NotificationUtil instance = null;
    private static Context context;

    private NotificationUtil() {
    }

    public static NotificationUtil getInstance(Context c) {
        synchronized (NotificationUtil.class) {
            if (instance == null) {
                synchronized (NotificationUtil.class) {
                    instance = new NotificationUtil();
                    context = c;
                }
            }
        }
        return instance;
    }

    public static void sendNotification(Context context, String title, String content) {
        /*NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.slow)
                .setContentIntent(PendingIntent.getActivity(context,0,new Intent(context, DisplayActivity.class),0));
        Notification notification = builder.build();
        notificationManager.notify(1,notification);*/
    }
}
