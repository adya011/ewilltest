package com.moonlay.litewill.notification;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.moonlay.litewill.OpenWillActivity;
import com.moonlay.litewill.model.NotificationDb;
import com.moonlay.litewill.utility.DatabaseHelper;
import com.moonlay.litewill.utility.SharedPrefManager;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationOpenedListener implements OneSignal.NotificationOpenedHandler {
    private static final String TAG = "mydebug_notif_opened";
    //private Application application;

    Context context;
    DatabaseHelper db;
    List<NotificationDb> notificationDbList = new ArrayList<>();

    public NotificationOpenedListener(Context context) {
        this.context = context;
    }

    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
        OSNotificationAction.ActionType actionType = result.action.type;
        JSONObject data = result.notification.payload.additionalData;
        String customKey;

        Log.d(TAG, "Notification opened");

        if (data != null) {
            Log.d(TAG, "Data not null");
            try {
                int willDetailId = data.getInt("willId");
                Log.d(TAG, "will id: " + willDetailId);
                goToWillDetail(willDetailId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            customKey = data.optString("customkey", null);
            if (customKey != null)
                Log.d(TAG, "customkey set with value: " + customKey);
        } else {
            Log.d(TAG, "data null");
        }

        if (actionType == OSNotificationAction.ActionType.ActionTaken) {
            Log.d(TAG, "Button pressed with id: " + result.action.actionID);
        } else {
            Log.d(TAG, "action type null");
        }
    }

    private void goToWillDetail(int willDetailId) {
        Intent intent = new Intent(context, OpenWillActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putInt("will_id", willDetailId);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
}
