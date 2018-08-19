package com.moonlay.litewill.model.db;

public class DBContract {
    public static class NotificationPayloadEntry
    {
        public static final String TABLE_NAME = "NotificationPayload";
        public static final String COLUMN_CODE = "Code";
        public static final String COLUMN_ROLE = "Role";
        public static final String COLUMN_NOTIFICATION_TIME = "NotificationTime";
        public static final String COLUMN_RATE = "Rate";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_STATUS = "Status";
        public static final String COLUMN_IS_UNREAD = "IsUnread";
        public static final String COLUMN_IS_NEW = "IsNew";
    }
}
