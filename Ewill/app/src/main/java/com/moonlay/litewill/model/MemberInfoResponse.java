package com.moonlay.litewill.model;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 7/11/2018.
 */

public class MemberInfoResponse {
    private boolean success;
    private ArrayList<MemberInfo> result;
    private String version;
    private String timestamp;

    public MemberInfoResponse(boolean success, ArrayList<MemberInfo> result, String version, String timestamp) {
        this.success = success;
        this.result = result;
        this.version = version;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<MemberInfo> getResult() {
        return result;
    }

    public void setResult(ArrayList<MemberInfo> result) {
        this.result = result;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
