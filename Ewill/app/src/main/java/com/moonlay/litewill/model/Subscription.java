package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/11/2018.
 */

public class Subscription {
    private int subscriptionId;
    private String username;
    private String subscribeDate;
    private String expiredDate;
    private String expiredSuspensionDate;
    private int willMemberId;
    private String userIdentityId;

    public Subscription(int subscriptionId, String username, String subscribeDate, String expiredDate, String expiredSuspensionDate, int willMemberId, String userIdentityId) {
        this.subscriptionId = subscriptionId;
        this.username = username;
        this.subscribeDate = subscribeDate;
        this.expiredDate = expiredDate;
        this.expiredSuspensionDate = expiredSuspensionDate;
        this.willMemberId = willMemberId;
        this.userIdentityId = userIdentityId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSubscribeDate() {
        return subscribeDate;
    }

    public void setSubscribeDate(String subscribeDate) {
        this.subscribeDate = subscribeDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getExpiredSuspensionDate() {
        return expiredSuspensionDate;
    }

    public void setExpiredSuspensionDate(String expiredSuspensionDate) {
        this.expiredSuspensionDate = expiredSuspensionDate;
    }

    public int getWillMemberId() {
        return willMemberId;
    }

    public void setWillMemberId(int willMemberId) {
        this.willMemberId = willMemberId;
    }

    public String getUserIdentityId() {
        return userIdentityId;
    }

    public void setUserIdentityId(String userIdentityId) {
        this.userIdentityId = userIdentityId;
    }
}

