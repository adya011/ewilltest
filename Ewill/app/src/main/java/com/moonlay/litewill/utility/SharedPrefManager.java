package com.moonlay.litewill.utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.moonlay.litewill.config.Constants;

/**
 * Created by nandana.samudera on 5/9/2018.
 */

public class SharedPrefManager {
    public static final String EWILL_SHAREDPREF = "ewillSp";
    SharedPreferences sp;
    SharedPreferences.Editor spEditor;

    public SharedPrefManager(Context context) {
        sp = context.getSharedPreferences(EWILL_SHAREDPREF, Context.MODE_PRIVATE);
        spEditor = sp.edit();
    }

    public void saveSPString(String keySP, String value) {
        spEditor.putString(keySP, value);
        spEditor.commit();
    }

    //Logged In Username
    public void setUserLoggedIn(String username) {
        spEditor.putString(Constants.LOGGEDIN_USERNAME, username).commit();
    }

    public String getUserLoggedIn() {
        return sp.getString(Constants.LOGGEDIN_USERNAME, null);
    }

    //Logged In Member type
    public void setUserMemberType(int memberType) {
        spEditor.putInt("member_type", memberType).commit();
    }

    public int getUserMemberType() {
        return sp.getInt("member_type", 0);
    }

    public void clearUserMemberType() {
        spEditor.remove("member_type").commit();
    }

    //Logged In email
    public void setUserEmail(String userEmail) {
        spEditor.putString("user_email", userEmail).commit();
    }

    public String getUserEmail() {
        return sp.getString("user_email", null);
    }

    public void clearUserEmail() {
        spEditor.remove("user_email").commit();
    }

    //Last Logged In User
    public void setUserLastLoggedIn(String username) {
        spEditor.putString(Constants.LAST_LOGGEDIN_USERNAME, username).commit();
    }

    public String getUserLastLoggedIn() {
        return sp.getString(Constants.LAST_LOGGEDIN_USERNAME, null);
    }

    //Reg Status
    public void setRegStatus(String setStatus) {
        spEditor.putString(Constants.REG_STATUS, setStatus).commit();
    }

    public String getRegStatus() {
        return sp.getString(Constants.REG_STATUS, null);
    }

    public void removeRegStatus() {
        spEditor.remove(Constants.REG_STATUS).commit();
    }

    //Reg Username
    public void setRegUsername(String username) {
        spEditor.putString(Constants.REG_USERNAME, username).commit();
    }

    public String getRegUsername() {
        return sp.getString(Constants.REG_USERNAME, null);
    }

    public void removeRegUsername() {
        spEditor.remove(Constants.REG_USERNAME).commit();
    }

    //Login Token
    public void setLoginToken(String token) {
        spEditor.putString(Constants.LOGGEDIN_TOKEN, token).commit();
    }

    public String getLoginToken() {
        return sp.getString(Constants.LOGGEDIN_TOKEN, null);
    }

    public void removeLoginToken() {
        spEditor.remove(Constants.LOGGEDIN_TOKEN).commit();
    }

    public int getNotificationCount() {
        return sp.getInt("notif_count", 0);
    }

    public void setNotificationCount(int addCount){
        spEditor.putInt("notif_count", addCount);
    }

    public void addNotificationCount(int addCount) {
        int notifCount = getNotificationCount();
        spEditor.putInt("notif_count", notifCount + addCount);
    }

    public void clearNotificationCount() {
        spEditor.remove("notif_count").commit();
    }
}
