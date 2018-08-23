package com.moonlay.litewill.notification;

import android.app.Application;

import com.onesignal.OneSignal;

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .setNotificationReceivedHandler(new NotificationReceivedListener(getApplicationContext()))
                .setNotificationOpenedHandler(new NotificationOpenedListener(this))
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        OneSignal.sendTag("email", "ewilltst@gmail.com");
    }
}
