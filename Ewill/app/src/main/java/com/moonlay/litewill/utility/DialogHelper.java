package com.moonlay.litewill.utility;

import android.app.Activity;
import android.support.v7.app.AlertDialog;

/**
 * Created by nandana.samudera on 5/11/2018.
 */

public class DialogHelper {

    public void alertDialog(Activity getActv, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActv);
        builder.setTitle("Alert");
        builder.setPositiveButton("OK", null);
        builder.setMessage(msg);

        AlertDialog theAlertDialog = builder.create();
        theAlertDialog.show();
    }
}
