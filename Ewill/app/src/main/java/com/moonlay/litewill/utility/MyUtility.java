package com.moonlay.litewill.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class MyUtility {
    public static void askForPermission(Activity activity, Context context, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    ActivityCompat.requestPermissions(activity, permissions, 1);
                } else {
                    ActivityCompat.requestPermissions(activity, permissions, 1);
                }
            }
        }
    }

    public static void alertDialogOK(Activity getActv, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActv);
        builder.setTitle("Alert");
        builder.setPositiveButton("OK", null);
        builder.setMessage(msg);

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }
}
