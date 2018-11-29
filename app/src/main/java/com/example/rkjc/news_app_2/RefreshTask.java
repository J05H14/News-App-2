package com.example.rkjc.news_app_2;

import android.content.Context;
import android.util.Log;

public class RefreshTask {

    public static final String ACTION_NOTIFICATION = "action-notification";
    public static final String ACTION_DISMISS = "dismiss-notification";
    public static final String TAG = "ReminderTask";

    public static void executeTask(Context context, String action) {
        if (ACTION_NOTIFICATION.equals(action)) {
            Log.d(TAG, "Synced");
            syncAndNotify(context);
        }
        else if (ACTION_DISMISS.equals(action)) {
            Log.d(TAG, "Notification Cleared");
            NotificationUtils.clearNotifications(context);
        }
    }

    private static void syncAndNotify(Context context) {
        ScheduleUtilities.scheduleRefresh(context);
        NotificationUtils.notifyUser(context);
    }
}
