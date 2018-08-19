package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 6/26/2018.
 */

public class Document {
    private int id;
    private String documentRemark;
    private String documentPath;

    public Document(String documentRemark, String documentPath) {
        this.documentRemark = documentRemark;
        this.documentPath = documentPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDocumentRemark() {
        return documentRemark;
    }

    public void setDocumentRemark(String documentRemark) {
        this.documentRemark = documentRemark;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath;
    }
}
