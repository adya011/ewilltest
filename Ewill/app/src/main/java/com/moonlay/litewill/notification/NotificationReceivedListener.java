package com.moonlay.litewill.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.moonlay.litewill.DashboardActivity;
import com.moonlay.litewill.model.NotificationDb;
import com.moonlay.litewill.utility.DatabaseHelper;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationReceivedListener implements OneSignal.NotificationReceivedHandler {
    private static final String TAG = "mydebug_notif_receive";
    Context context;
    DatabaseHelper db;
    List<NotificationDb> notificationDbList = new ArrayList<>();
    SharedPrefManager sharedPrefManager;

    public NotificationReceivedListener(Context context) {
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {
        JSONObject data = notification.payload.additionalData;
        String customKey;
        sharedPrefManager = new SharedPrefManager(context);

        if (data != null) {
            Log.d(TAG, "Notification received. Data not null");
            try {
                Log.d(TAG, "int will id: " + data.getInt("willId"));
                Log.d(TAG, "flag: " + data.getString("flag"));

                int willId = data.getInt("willId");
                String flag = data.getString("flag");
                String creatorName = data.getString("creatorName");

                String userEmail = sharedPrefManager.getUserEmail();
                Log.d(TAG, "user emaik: " + String.valueOf(userEmail));

                insertNotificationDb(willId, creatorName, flag, userEmail);
                /*sharedPrefManager.addNotificationCount(1);*/

                addNotificationCount();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            customKey = data.optString("customkey", null);
            if (customKey != null) {
                Log.d(TAG, "customkey set with value: " + customKey);
            }
        } else {
            Log.d(TAG, "Notification received. Data null");
        }
    }

    private void addNotificationCount(){
        SharedPreferences saved_values = context.getSharedPreferences("ewillSp", Context.MODE_PRIVATE);
        int count = saved_values.getInt("notif_count", 0);
        SharedPreferences.Editor editor = saved_values.edit();
        editor.putInt("notif_count", count + 1);
        editor.commit();

        Log.d(TAG, "shared pref count: " + saved_values.getInt("notif_count", 0));
    }

    private void insertNotificationDb(int willId, String creatorName, String flag, String userEmail) {
        Log.d(TAG, "insert to notification db");

        db = new DatabaseHelper(context);
        sharedPrefManager = new SharedPrefManager(context);

        Log.d(TAG, "user email: " + String.valueOf(userEmail));

        long id = db.insertNotification(willId, creatorName, flag, userEmail);

        NotificationDb n = db.getNotification(id);
        if (n != null) {
            notificationDbList.add(0, n);
        }
    }

    private void addNotification() {

    }
}
