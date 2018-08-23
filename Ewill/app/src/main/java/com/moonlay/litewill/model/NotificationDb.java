package com.moonlay.litewill.model;

public class NotificationDb {
    public static final String TABLE_NAME = "notification";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_WILL_ID = "will_id";
    public static final String COLUMN_WILL_NAME = "will_name";
    public static final String COLUMN_FLAG = "flag";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_CREATOR_NAME = "creator_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private int id;
    private int will_id;
    private String will_name;
    private String flag;
    private boolean status;
    private String creator_name;
    private String email;
    private String timestamp;

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_WILL_ID + " TEXT,"
                    + COLUMN_WILL_NAME + " TEXT,"
                    + COLUMN_FLAG + " TEXT,"
                    + COLUMN_STATUS + " BOOLEAN,"
                    + COLUMN_CREATOR_NAME + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public NotificationDb() {
    }

    public NotificationDb(int id, int will_id, String will_name, String flag,
                          boolean status, String creator_name, String email, String timestamp) {
        this.id = id;
        this.will_id = will_id;
        this.will_name = will_name;
        this.flag = flag;
        this.status = status;
        this.creator_name = creator_name;
        this.email = email;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWill_id() {
        return will_id;
    }

    public void setWill_id(int will_id) {
        this.will_id = will_id;
    }

    public String getWill_name() {
        return will_name;
    }

    public void setWill_name(String will_name) {
        this.will_name = will_name;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCreator_name() {
        return creator_name;
    }

    public void setCreator_name(String creator_name) {
        this.creator_name = creator_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
