package com.moonlay.litewill.model;

/**
 * Created by nandana.samudera on 5/21/2018.
 */

public class Questions {
    private Question questions;
    private String userName;

    public Questions(Question questions, String userName) {
        this.questions = questions;
        this.userName = userName;
    }

    public Question getQuestions() {
        return questions;
    }

    public void setQuestions(Question questions) {
        this.questions = questions;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
