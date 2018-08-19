package com.moonlay.litewill.model;

import java.util.ArrayList;

/**
 * Created by nandana.samudera on 7/3/2018.
 */

public class MyWillDetail {
    private int id;
    private String name;
    private String willOwnerId;
    private boolean isLFStatus;
    private boolean isLFShared;
    private boolean isLFLocationShared;
    private boolean isLFDocumentShared;
    private boolean isPAStatus;
    private boolean isPAShared;
    private boolean isPALocationShared;
    private boolean isPADocumentShared;
    private boolean isUserStatus;
    private boolean isUserShared;
    private boolean isUserLocationShared;
    private boolean isUserDocumentShared;
    private String fullName;
    private String createdUtc;
    private ArrayList<Document> documents;
    private ArrayList<Shared> shareds;
    private Address[] addresses;

    public MyWillDetail(int id, String name, String willOwnerId, boolean isLFStatus, boolean isLFShared, boolean isLFLocationShared, boolean isLFDocumentShared, boolean isPAStatus, boolean isPAShared, boolean isPALocationShared, boolean isPADocumentShared, boolean isUserStatus, boolean isUserShared, boolean isUserLocationShared, boolean isUserDocumentShared, String fullName, String createdUtc, ArrayList<Document> documents, ArrayList<Shared> shareds, Address[] addresses) {
        this.id = id;
        this.name = name;
        this.willOwnerId = willOwnerId;
        this.isLFStatus = isLFStatus;
        this.isLFShared = isLFShared;
        this.isLFLocationShared = isLFLocationShared;
        this.isLFDocumentShared = isLFDocumentShared;
        this.isPAStatus = isPAStatus;
        this.isPAShared = isPAShared;
        this.isPALocationShared = isPALocationShared;
        this.isPADocumentShared = isPADocumentShared;
        this.isUserStatus = isUserStatus;
        this.isUserShared = isUserShared;
        this.isUserLocationShared = isUserLocationShared;
        this.isUserDocumentShared = isUserDocumentShared;
        this.fullName = fullName;
        this.createdUtc = createdUtc;
        this.documents = documents;
        this.shareds = shareds;
        this.addresses = addresses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWillOwnerId() {
        return willOwnerId;
    }

    public void setWillOwnerId(String willOwnerId) {
        this.willOwnerId = willOwnerId;
    }

    public boolean isLFStatus() {
        return isLFStatus;
    }

    public void setLFStatus(boolean LFStatus) {
        isLFStatus = LFStatus;
    }

    public boolean isLFShared() {
        return isLFShared;
    }

    public void setLFShared(boolean LFShared) {
        isLFShared = LFShared;
    }

    public boolean isLFLocationShared() {
        return isLFLocationShared;
    }

    public void setLFLocationShared(boolean LFLocationShared) {
        isLFLocationShared = LFLocationShared;
    }

    public boolean isLFDocumentShared() {
        return isLFDocumentShared;
    }

    public void setLFDocumentShared(boolean LFDocumentShared) {
        isLFDocumentShared = LFDocumentShared;
    }

    public boolean isPAStatus() {
        return isPAStatus;
    }

    public void setPAStatus(boolean PAStatus) {
        isPAStatus = PAStatus;
    }

    public boolean isPAShared() {
        return isPAShared;
    }

    public void setPAShared(boolean PAShared) {
        isPAShared = PAShared;
    }

    public boolean isPALocationShared() {
        return isPALocationShared;
    }

    public void setPALocationShared(boolean PALocationShared) {
        isPALocationShared = PALocationShared;
    }

    public boolean isPADocumentShared() {
        return isPADocumentShared;
    }

    public void setPADocumentShared(boolean PADocumentShared) {
        isPADocumentShared = PADocumentShared;
    }

    public boolean isUserStatus() {
        return isUserStatus;
    }

    public void setUserStatus(boolean userStatus) {
        isUserStatus = userStatus;
    }

    public boolean isUserShared() {
        return isUserShared;
    }

    public void setUserShared(boolean userShared) {
        isUserShared = userShared;
    }

    public boolean isUserLocationShared() {
        return isUserLocationShared;
    }

    public void setUserLocationShared(boolean userLocationShared) {
        isUserLocationShared = userLocationShared;
    }

    public boolean isUserDocumentShared() {
        return isUserDocumentShared;
    }

    public void setUserDocumentShared(boolean userDocumentShared) {
        isUserDocumentShared = userDocumentShared;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(String createdUtc) {
        this.createdUtc = createdUtc;
    }

    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }

    public ArrayList<Shared> getShareds() {
        return shareds;
    }

    public void setShareds(ArrayList<Shared> shareds) {
        this.shareds = shareds;
    }

    public Address[] getWillAddresses() {
        return addresses;
    }

    public void setWillAddresses(Address[] addresses) {
        this.addresses = addresses;
    }
}
