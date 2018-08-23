package com.moonlay.litewill.utility;

import android.app.Notification;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.moonlay.litewill.model.NotificationDb;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "notification_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(NotificationDb.CREATE_TABLE);
        Log.d("mydebug", "create table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NotificationDb.TABLE_NAME);
        onCreate(db);
    }

    public long insertNotification(int willId, String creatorName, String flag, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(NotificationDb.COLUMN_WILL_ID, willId);
        values.put(NotificationDb.COLUMN_CREATOR_NAME, creatorName);
        values.put(NotificationDb.COLUMN_FLAG, flag);
        values.put(NotificationDb.COLUMN_EMAIL, email);

        long id = db.insert(NotificationDb.TABLE_NAME, null, values);
        db.close();

        return id;
    }

    public NotificationDb getNotification(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(NotificationDb.TABLE_NAME,
                new String[]{NotificationDb.COLUMN_ID, NotificationDb.COLUMN_WILL_ID,
                            NotificationDb.COLUMN_WILL_NAME, NotificationDb.COLUMN_FLAG,
                            NotificationDb.COLUMN_STATUS, NotificationDb.COLUMN_CREATOR_NAME,
                            NotificationDb.COLUMN_EMAIL, NotificationDb.COLUMN_TIMESTAMP},
                NotificationDb.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        NotificationDb notificationDb = new NotificationDb(
                cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_ID)),
                cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_WILL_ID)),
                cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_WILL_NAME)),
                cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_FLAG)),
                cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_STATUS)) > 0,
                cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_CREATOR_NAME)),
                cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_EMAIL)),
                cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_TIMESTAMP)));
        cursor.close();

        return notificationDb;
    }

    public List<NotificationDb> getAllNotification() {
        List<NotificationDb> notificationDbList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + NotificationDb.TABLE_NAME + " ORDER BY "
                + NotificationDb.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                NotificationDb notificationDb = new NotificationDb();
                notificationDb.setId(cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_ID)));
                notificationDb.setWill_id(cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_WILL_ID)));
                notificationDb.setWill_name(cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_WILL_NAME)));
                notificationDb.setFlag(cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_FLAG)));
                notificationDb.setStatus(cursor.getInt(cursor.getColumnIndex(NotificationDb.COLUMN_STATUS)) > 0);
                notificationDb.setCreator_name(cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_CREATOR_NAME)));
                notificationDb.setEmail(cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_EMAIL)));
                notificationDb.setTimestamp(cursor.getString(cursor.getColumnIndex(NotificationDb.COLUMN_TIMESTAMP)));

                notificationDbList.add(notificationDb);
            } while (cursor.moveToNext());
        }

        //Log.d("mydebug", "willname: " + notificationDbList.get(0).getWill_name());

        db.close();

        return notificationDbList;
    }

    public int getNotificationCount() {
        String countQuery = "SELECT * FROM " + NotificationDb.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        return count;
    }
}
