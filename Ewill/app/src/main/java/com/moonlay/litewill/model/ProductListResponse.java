package com.moonlay.litewill.model;

import java.util.ArrayList;

public class ProductListResponse {
    private boolean success;
    private ArrayList<ProductList> result;
    private String version;
    private String timestamp;

    public ProductListResponse(boolean success, ArrayList<ProductList> result, String version, String timestamp) {
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

    public ArrayList<ProductList> getResult() {
        return result;
    }

    public void setResult(ArrayList<ProductList> result) {
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
