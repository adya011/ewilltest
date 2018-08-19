package com.moonlay.litewill.model;

import java.util.ArrayList;

public class WillNotify {
    private ArrayList<EmailReceivers> emailReceivers;
    private String flag;

    public WillNotify(ArrayList<EmailReceivers> emailReceivers, String flag) {
        this.emailReceivers = emailReceivers;
        this.flag = flag;
    }

    public ArrayList<EmailReceivers> getEmailReceivers() {
        return emailReceivers;
    }

    public void setEmailReceivers(ArrayList<EmailReceivers> emailReceivers) {
        this.emailReceivers = emailReceivers;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
