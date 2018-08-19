package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 7/11/2018.
 */

public class UpgradeSearcher {
    private String fullName;
    private String address;
    private int memberTypeId;
    private boolean isSingaporean;

    public UpgradeSearcher(String fullName, String address, int memberTypeId, boolean isSingaporean) {
        this.fullName = fullName;
        this.address = address;
        this.memberTypeId = memberTypeId;
        this.isSingaporean = isSingaporean;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMemberTypeId() {
        return memberTypeId;
    }

    public void setMemberTypeId(int memberTypeId) {
        this.memberTypeId = memberTypeId;
    }

    public boolean getIsSingaporean() {
        return isSingaporean;
    }

    public void setIsSingaporean(boolean isSingaporean) {
        this.isSingaporean = isSingaporean;
    }
}
